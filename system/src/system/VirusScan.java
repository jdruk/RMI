package system;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

public class VirusScan extends UnicastRemoteObject implements ScannerServer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 817401378245261263L;
	private String nameService; 
	private Registry registry;
	
	public String getNameService() {
		return nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public static void main(String[] args) {
		
		String port = JOptionPane.showInputDialog("Porta Servidor Scanner");
		String nameServer = JOptionPane.showInputDialog("Nome Servidor Scanner");
		Registry registry;
		VirusScan service;
		try {
			registry = LocateRegistry.createRegistry(Integer.parseInt(port));
			service = new VirusScan(nameServer, registry);
			registry.bind(service.getNameService(), service);
			
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	protected VirusScan(String nameServer, Registry registry) throws RemoteException {
		super();
		setNameService(nameServer);
		this.registry = registry;
	}

	@Override
	public boolean ping() throws RemoteException {
		return true;
	}

	public boolean isVirus(String filename, byte[] data, int len) throws RemoteException {
		
		return false;
	}

	@Override
	public void shutdown() throws RemoteException {
		try {
			System.out.println("Shutdown...");
			this.registry.unbind(getNameService());
			System.out.println("Server "+ getNameService() + " OFF");
			System.exit(0);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isVirus(String filename, byte[] data) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
