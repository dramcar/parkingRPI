import java.util.*;
import java.rmi.*;             
import java.rmi.server.*;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.GpioPinListener;

import java.util.Random;
import java.math.BigInteger;

class Salida implements GpioPinListenerDigital {

    private Parking parking;

    private GpioController gpio;
    private GpioPinDigitalInput boton;
    private GpioPinDigitalOutput barrera;

    Salida(Parking parking){
        this.parking = parking;

        // Crea controlador gpio
        gpio = GpioFactory.getInstance();
        // Botón de entrada asociado a gpio #3
        boton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);

        boton.addListener(this);

        if(!provisioned(RaspiPin.GPIO_01))
            barrera = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);

    }


    @Override
    public synchronized void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if (event.getState() == PinState.LOW ) {

            try {
                // Salida de un coche
                System.out.println("\tUn coche quiere salir");
                boolean ok = false; // Bandera para salir del bucle de intentos
                Scanner sc = new Scanner(System.in);

                for (int i=0; i<3 && !ok; i++) { // 3 intentos
                    System.out.print("Matricula: ");
                    String matricula = sc.nextLine();

                    /****************************/
                    if (parking.getMatriculaRegistro(matricula)){
                    /****************************/
                        System.out.print("Codigo de la tarjeta: ");
                        String codigoTarjeta = sc.nextLine();

                        // Se busca el coche en el registro
                        Coche coche = new Coche (matricula, codigoTarjeta);
                        Registro r = parking.getRegistro(coche);
                        if (r != null) {
                            ok = true;
                            r.setFechaSal();
                            r.setImporte();
                            while(!r.comprobarPagado()){
                                System.out.println("Importe a pagar: "+r.getImporte());
                                System.out.print("Introduzca el pago: ");
                                double pago = Double.parseDouble("0"+sc.nextLine());
                                r.pagar(pago);
                            }
                            System.out.println("\tBarrera abierta");
                            barrera.pulse(5000, true); //Espera de 5 segundos. (Bloqueante)
                            System.out.println("\tBarrera cerrada");
                            parking.delRegistro(coche);
                            System.out.println("\tEl coche ha salido");
                        } else {
                            System.err.println("\tEl coche con esa matrícula no se corresponde con ese código de tarjeta");
                        }
                    } else {
                        System.err.println("\tEl coche con matrícula " + matricula + " no se encuentra en el interior del parking");
                    }
                }
                if (!ok){
                    System.err.println("Ha alcanzado el máximo número de intentos disponibles");
                }
            }
            catch (RemoteException e) {
                System.err.println("Error de comunicacion: " + e.toString());
            }
            catch (Exception e) {
                System.err.println("Excepcion en Salida: " + e.toString());
            }

        }
    }

    private boolean provisioned(Pin pin) {
        boolean res = false;
        for(GpioPin gpiopin : gpio.getProvisionedPins()) {
            if(gpiopin.getPin().equals(pin))
                return true;
        }

        return res;
    }

}
