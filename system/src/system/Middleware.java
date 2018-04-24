package system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class Middleware extends UnicastRemoteObject implements Service{

	private static final long serialVersionUID = 4731292783677162913L;
	List<ScannerServer> servers = new ArrayList<>();
	//HashMap<String, String> statusServers = new HashMap<>();
	LinkedList <String> fifo = new LinkedList<String>();
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
        if(stub != null) {
        	servers.add(stub);
        	fifo.add(hostName+";"+stub.getName());
        }
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
	public void shutdownServer(String server) throws RemoteException {
		for (int i = 0; i < servers.size(); i++) {
			System.out.println(server.split(";")[1] );
			try {
				if(servers.get(i).getName().equals(server.split(";")[1])) {
					servers.get(i).shutdown();
					servers.remove(i);
					System.out.println(server);
				}
			} catch (Exception e) {
			}
		}
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
		
		if(isSendParaleloActive()) {
			if (getServes().size() >0 ) {
				CountDownLatch c = new CountDownLatch(getServes().size());
				for (int i = 0; i < getServes().size(); i++) 
					new Thread(new SendFile(getServes().get(i), c, filename, data));
				try {
					c.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			for (int i = 0; i < getServes().size(); i++) {
				virose = getServes().get(i).isVirus(filename, data);
			}
		}
		
		return virose;
	}

	private boolean isSendParaleloActive() {
		Properties props = new Properties();
		try {
			FileInputStream file = new FileInputStream(
					"dados.properties");
			props.load(file);
			boolean returnVal = Boolean.parseBoolean(props.getProperty("enviar.paralelo"));
			return returnVal;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public HashMap<String, String> statusServers() {
		//Iterator<String> it = statusServers.keySet().iterator();
		HashMap<String, String> list = new HashMap<>();
		LinkedList list2 = (LinkedList) fifo.clone();
		if (servers.size() > 0) {
			CountDownLatch count = new CountDownLatch(servers.size());
			ArrayList<Process> child = new ArrayList<>();
			for (int i = 0; i < servers.size(); i++) {
				child.add(new Process(servers.get(i),(String) list2.remove(), count));
				new Thread(child.get(i)).start();
			}
			try {
				count.await();
				for (int i = 0; i < child.size(); i++) {
					list.put(child.get(i).getNameServer(), child.get(i).getStatus());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
}

class SendFile implements Runnable {

	ScannerServer s;
	CountDownLatch c;
	String filename;
	byte[] data;
	
	public SendFile (ScannerServer s,CountDownLatch count, String filename, byte[] data) {
		this.s = s;
		c = count;
	}
	
	@Override
	public void run() {
		c.countDown();
		try {
			s.isVirus(filename, data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}

class Process implements Runnable{
	
	private ScannerServer s;
	private String nameServer;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
	
	public synchronized String getNameServer() {
		return nameServer;
	}

	public synchronized void setNameServer(String nameServer) {
		this.nameServer = nameServer;
	}

	CountDownLatch  c;
	public  Process(ScannerServer s, String name, CountDownLatch count) {
		this.s =s;
		c = count;
		nameServer = name;
	}

	@Override
	public synchronized void run() {
		try {
			c.countDown();
			s.ping();
			this.setStatus("Online");
		} catch (RemoteException e) {
			this.setStatus("OFF");
		}
	}
}


