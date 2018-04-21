package system;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ScannerServer extends Remote, Uploaders {
	
	boolean ping() throws RemoteException;
	
	void shutdown() throws RemoteException;
	
} 
