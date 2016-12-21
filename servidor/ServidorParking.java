import java.rmi.*;
import java.rmi.server.*;

class ServidorParking {
    static public void main (String args[]) {
       if (args.length!=2) {
            System.err.println("Uso: ServidorParking hostname numPuertoRegistro");
        }else{

            // Evita tener que establecer la política de seguridad por línea de comandos.
            System.setProperty("java.security.policy", "./servidor.permisos");

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
            try {
                // Asociación con la ip "externa" Ej: 192.168.111.1
                System.setProperty("java.rmi.server.hostname", args[0]);
                ParkingImpl srv = new ParkingImpl();
                Naming.rebind("rmi://"+ args[0] +":" + args[1] + "/Parking", srv);
            }
            catch (RemoteException e) {
                System.err.println("Error de comunicacion: " + e.toString());
                System.exit(1);
            }
            catch (Exception e) {
                System.err.println("Excepcion en ServidorParking:" + e.toString());
                System.exit(1);
            }
        }
    }
}
