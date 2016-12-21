import java.rmi.*;
import java.rmi.server.*;


class RotuloImpl extends UnicastRemoteObject implements Rotulo {

	private Display disp;

	RotuloImpl() throws RemoteException {
		disp = new Display();
    }
    public void alteraPlazas(int plazasLibres) throws RemoteException {
		System.out.println("Número de plazas disponibles: " + plazasLibres);
		disp.setNumero(plazasLibres);
    }
}
