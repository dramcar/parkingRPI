import java.rmi.*;

interface Rotulo extends Remote {
    void alteraPlazas(int plazasLibres) throws RemoteException;
}
