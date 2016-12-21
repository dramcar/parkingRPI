import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class ParkingImpl extends UnicastRemoteObject implements Parking {
    List<Registro> lRegistro;
    List<Rotulo>   lRotulo;
    int plazasLibres; // Contador de plazas libres
    static int numPlazas = 3; //Numero de plazas totales


    ParkingImpl() throws RemoteException {
        /* Constructor */
        lRegistro = new LinkedList<Registro>();
        lRotulo = new LinkedList<Rotulo>();
        plazasLibres = numPlazas;
    }

    public  Registro addRegistro(Coche c) throws RemoteException {

        Registro r=null;
        if (plazasLibres > 0){
            r = new RegistroImpl(c);
            lRegistro.add(r);
            plazasLibres--;
            System.out.println("Nuevo registro: " + c);

            for (Rotulo rot : lRotulo) {
                rot.alteraPlazas(plazasLibres);
            }

        }
        else{
            System.out.println("Parking Completo");
        }

        //Comprobar en el programa principal que el registro no sea null
        return r;
    }

    public Registro getRegistro(Coche c) throws RemoteException {
    
        Registro sr=null;
        for (Registro r : lRegistro) {
            System.err.println(r.getCoche());
            if (r.getCoche().getMatricula().equals(c.getMatricula())) {
                if (r.getCoche().getCodigoTarjeta().equals(c.getCodigoTarjeta())){
                    sr=r;
                }
            }
        }
    
    if (sr == null )
        System.err.println("No existe el registro");

    return sr;
    }

    public void delRegistro(Coche c) throws RemoteException {
        /* Elimina de la lista de registros el registro asociado al coche 
           indicado. En este caso el coche deja el parking*/
        boolean eliminado=false;

    // Busca en la lista el coche a eliminar el registro y decrementa el numero de plazas
    for (Registro r: lRegistro) { 
        if (r.getCoche().getMatricula().equals(c.getMatricula())){
        plazasLibres++;
        eliminado=true;
        lRegistro.remove(r);
        System.out.println("Registro eliminado: " + c);

        for (Rotulo rot : lRotulo) {
            rot.alteraPlazas(plazasLibres);
        }


        break;
        }       
    }

    if (eliminado==false)
        System.err.println("No existe el registro/No se ha introducido el importe");  
    }

    // Comprueba si el coche está en el parking a través de la matrícula
    public boolean getMatriculaRegistro (String matricula) throws RemoteException {
    boolean cocheEncontrado = false;
        // Busca en la lista la matrícula para saber si un coche está en el parking
        for (Registro r: lRegistro) { 
            if (r.getCoche().getMatricula().equals(matricula)) {
                cocheEncontrado=true;
            break;
            }       
        }
    return cocheEncontrado;
    }

    // Devuelve el número de plazas libres para que pueda ser leído por el rótulo
    public int getPlazasLibres() throws RemoteException { 
        return plazasLibres;
    }

    public void addRotulo  (Rotulo r) throws RemoteException {
        lRotulo.add(r);
        r.alteraPlazas(plazasLibres);
    }
    public void delRotulo  (Rotulo r) throws RemoteException {
        lRotulo.remove(lRotulo.indexOf(r));
    }
}
