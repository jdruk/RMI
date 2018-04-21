package system;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Middleware extends UnicastRemoteObject implements Service{

	private static final long serialVersionUID = 4731292783677162913L;
	List<ScannerServer> servers;
	private String hostName;
	
	public Middleware(String hostname) throws RemoteException {
		super();
		setHostName(hostname);
	}
	
	@Override
	public String getName() throws RemoteException {
		return getHostName();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostname) {
		this.hostName = hostname;
	}

	@Override
	public List<ScannerServer> getServes() throws RemoteException {
		return servers;
	}


	@Override
	public void addServer(String hostName, String nameService, int port) throws RemoteException {
		ScannerServer stub = getConnectionScannerServer(hostName, nameService, port);
        if(stub != null)
        	servers.add(stub);
	}
	
	private ScannerServer getConnectionScannerServer(String hostName, String nameService, int port) {
		ScannerServer stub = null;
		try {
			Registry registry = LocateRegistry.getRegistry(hostName,port);
			stub = (ScannerServer) registry.lookup(nameService);
		} catch (NotBoundException | RemoteException e) {
			e.printStackTrace();
		}
		return stub;
	}

	@Override
	public void shutdownServer(ScannerServer server) throws RemoteException {
		server.shutdown();
	}

	@Override
	public boolean isVirus(String filename, byte[] data, int len) throws RemoteException {
		return false;
	}

}
