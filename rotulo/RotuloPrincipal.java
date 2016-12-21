import java.util.*;
import java.rmi.*;
import java.rmi.server.*;


class RotuloPrincipal {
    public static void main (String args []){
        if (args.length!=3) {
            System.err.println("Uso: RotuloPrincipal ifLocal hostServidor numPuertoRegistro");
        }else{

            // Asociación con la ip "externa" Ej: 192.168.111.1
            System.setProperty("java.rmi.server.hostname", args[0]);

            // Evita tener que establecer la política de seguridad por línea de comandos.
            System.setProperty("java.security.policy", "./rotulo.permisos");
            if (System.getSecurityManager() == null)
                System.setSecurityManager(new SecurityManager());


            try {

                Parking srv = (Parking) Naming.lookup("//" + args[1] + ":" + args[2] + "/Parking");
                RotuloImpl r = new RotuloImpl();
                srv.addRotulo(r);

                Scanner ent = new Scanner(System.in);
                while (ent.hasNextLine());

                srv.delRotulo(r);

                System.exit(0);
            }
            catch (RemoteException e) {
                System.err.println("Error de comunicacion: " + e.toString());
                System.exit(1);
            }
            catch (Exception e) {
                System.err.println("Excepcion en Rotulo:" + e.toString());
                System.exit(1);
            }
        }
    }
}
