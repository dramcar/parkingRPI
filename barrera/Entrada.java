import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.GpioPinListener;

import java.util.Random;
import java.math.BigInteger;

class Entrada implements GpioPinListenerDigital {

    private Parking parking;

    private GpioController gpio;
    private GpioPinDigitalInput boton;
    private GpioPinDigitalOutput barrera;

    Entrada(Parking parking){
        this.parking = parking;

        // Crea controlador gpio
        gpio = GpioFactory.getInstance();
        // Botón de entrada asociado a gpio #2
        boton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        boton.addListener(this);


        if(!provisioned(RaspiPin.GPIO_00))
            barrera = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);


    }

    @Override
    public synchronized void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if (event.getState() == PinState.LOW ) {

            try {
                // Entrada de un coche
                System.out.println("\tUn coche quiere entrar");
                if (parking.getPlazasLibres() > 0) {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Matricula: ");
                    String matricula = sc.nextLine();
                    if(!parking.getMatriculaRegistro(matricula)) {

                        String codigoTarjeta = new BigInteger(3*5, new Random()).toString(32);

                        Coche coche = new Coche (matricula, codigoTarjeta);

                        Registro r = parking.addRegistro(coche);

                        System.out.println(coche); // Funciona por el toString de coche

                        System.out.println("\tBarrera abierta");
                        barrera.pulse(5000, true); //Espera de 5 segundos. (Bloqueante)
                        System.out.println("\tBarrera cerrada");
                        System.out.println("\tEl coche ha entrado");
                    } else {
                        System.err.println("\tEl coche con matrícula " + matricula + " ya se encuentra en el interior del parking");
                    }
                }
                else{
                    System.out.println("Parking Completo");
                    System.out.println("\tEl coche no puede entrar");
                }
            }
            catch (RemoteException e) {
                System.err.println("Error de comunicacion: " + e.toString());
            }
            catch (Exception e) {
                System.err.println("Excepcion en Entrada: " + e.toString());
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
