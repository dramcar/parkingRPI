import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.GpioPinListener;

import java.util.Random;
import java.math.BigInteger;

class Barrera {
    static public void main (String args[]) {
        if (args.length!=3) {
            System.err.println("Uso: Barrera ifLocal hostServidor numPuertoRegistro");
        }else{

            // Asociación con la ip "externa" Ej: 192.168.111.2
            System.setProperty("java.rmi.server.hostname", args[0]);

            // Evita tener que establecer la política de seguridad por línea de comandos.
            System.setProperty("java.security.policy", "./barrera.permisos");

            if (System.getSecurityManager() == null)
                System.setSecurityManager(new SecurityManager());


            try {
                Parking parking = (Parking) Naming.lookup("//" + args[1] + ":" + args[2] + "/Parking");

                // Instancias de los sistemas de entrada y salida del párking
                Entrada entrada = new Entrada (parking);
                Salida  salida  = new Salida  (parking);

                // Bucle infinito de espera
                while(true){
                    Thread.sleep(500);
                }

            }
            catch (RemoteException e) {
                System.err.println("Error de comunicacion: " + e.toString());
            }
            catch (Exception e) {
                System.err.println("Excepcion en Barrera: " + e.toString());
            }

        }
    }
}
