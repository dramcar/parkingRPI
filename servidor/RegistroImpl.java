import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;

class RegistroImpl extends UnicastRemoteObject implements Registro {
    public static double pps=0.05; // Precio por segundo del parking
    private Coche coche;
    private Date  fechaEnt;
    private Date  fechaSal;
    private double importe;
    private boolean estaPagado;
    
    RegistroImpl(Coche c) throws RemoteException {
        /* Constructor */
        coche = c;
        fechaEnt = new Date (); //Una vez que se crea el registro se crea la fecha de entrada
        estaPagado=false;
    }   

    public Coche getCoche() throws RemoteException {
        return coche;
    }

    public Date getFechaEnt() throws RemoteException {
        return fechaEnt;
    }

    public Date getFechaSal() throws RemoteException { 
        return fechaSal;
    }

    public void setFechaSal() throws RemoteException {
        fechaSal = new Date ();
    }

    public void setImporte() throws RemoteException {
        importe = (double)(fechaSal.getTime()-fechaEnt.getTime())*pps/1000;
        importe = Math.floor(importe * 100) / 100; // Truncar a 2 decimales
    }

    public double getImporte() throws RemoteException {
        return importe;
    }

    public void pagar(double pago) throws RemoteException{
        if(pago >= importe){
            estaPagado = true;
        }
        else{
            importe = importe - pago;
            importe = Math.floor(importe * 100) / 100; // Truncar a 2 decimales
        }
    }

    public boolean comprobarPagado() throws RemoteException{
        return estaPagado;
    }
        
}
