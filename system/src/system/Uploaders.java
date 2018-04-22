package system;

import java.rmi.RemoteException;

public interface Uploaders {
	//boolean isVirus(String filename, byte[] data, int len) throws RemoteException;
	boolean isVirus(String filename, byte[] data) throws RemoteException;
}
