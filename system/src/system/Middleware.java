package system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Middleware extends UnicastRemoteObject implements Service{

	private static final long serialVersionUID = 4731292783677162913L;
	List<ScannerServer> servers = new ArrayList<>();
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
	public String isVirus(String filename, byte[] data) throws RemoteException {
		String virose = "";
		Path path = Paths.get("servercenter/"+ filename); 
		try {
			Files.write(path, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < getServes().size(); i++) {
			virose = getServes().get(i).isVirus(filename, data);
		}
		return virose;
	}

}
