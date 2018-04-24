package system;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface Service extends Remote, Uploaders {
    String getName() throws RemoteException;
    
    List<ScannerServer> getServes() throws RemoteException;
    
    void addServer(String serverName, String nameService, int port) throws RemoteException;
    
    void shutdownServer(String serverName) throws RemoteException;

	HashMap<String, String> statusServers() throws RemoteException;
}