import java.rmi.*;
import java.util.*;

interface Parking extends Remote {
    Registro addRegistro (Coche c) throws RemoteException;
    Registro getRegistro (Coche c) throws RemoteException;
    void     delRegistro (Coche c) throws RemoteException;

    void     addRotulo (Rotulo r) throws RemoteException;
	void     delRotulo (Rotulo r) throws RemoteException;

    int      getPlazasLibres () throws RemoteException;
    boolean	 getMatriculaRegistro (String matricula) throws RemoteException;
}
