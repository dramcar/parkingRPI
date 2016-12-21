import java.rmi.*;
import java.util.Date;


interface Registro extends Remote {
    Coche   getCoche () throws RemoteException;
    Date    getFechaEnt () throws RemoteException;
    Date    getFechaSal () throws RemoteException;
    double  getImporte () throws RemoteException;

    void    setImporte() throws RemoteException;
    void    setFechaSal () throws RemoteException;

    void    pagar (double pago) throws RemoteException;
    boolean comprobarPagado () throws RemoteException;
}
