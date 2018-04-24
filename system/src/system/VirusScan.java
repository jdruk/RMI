package system;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import computador_01.EnviarZIP;
import computador_01.FolderZiper;

public class VirusScan extends UnicastRemoteObject implements ScannerServer {

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
			System.out.println("Servidor scanner ativado");

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
		System.out.println("Shutdown...");
		System.out.println("Server " + getNameService() + " OFF");
		System.exit(0);
	}

	@Override
	public String isVirus(String filename, byte[] data) throws RemoteException {
		String virose = null;
		Path path = Paths.get("scanone/" + filename);
		try {
			Files.write(path, data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileInputStream file = null;
		try {
			FolderZiper.zipador();
			file = new FileInputStream("scanone/" + filename);
			DataInputStream in = new DataInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte buffer[] = new byte[2024];

		Socket misochet;
		try {
			misochet = new Socket("192.168.1.107", 9999);
			DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());
			byte[] buf = new byte[4096];

			while (true) {
				int len = file.read(buf);
				if (len == -1)
					break;
				fluxo_saida.write(buf, 0, len);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return virose;
	}

	@Override
	public String getName() throws RemoteException {
		return getNameService();
	}

}
