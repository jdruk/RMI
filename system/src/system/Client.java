package system;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

public class Client {

	private Client() {
	}

	public static void main(String[] args) {
		

	}

	public static Service getConnectionServer(String host) {
		Service stub = null;
		try {
			Registry registry = LocateRegistry.getRegistry(host, 20000);
			stub = (Service) registry.lookup("Hello");
			//String response = stub.getName();
			//stub.addServer("localhost", "pequeno", 8000);
			//System.out.println(response);
			//stub.getServes().add(e)
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return stub;
	}

	public static File selectFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		File selectedFile = null;
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
		}
		return selectedFile;
	}

	public static byte[] convertToBytes(File selectedFile) {
		byte[] data = null;
		try {
			data = Files.readAllBytes(selectedFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void statusFile(JTextArea status) {
		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(7777);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true) {
			try {
				Socket clientServer = servidor.accept();
				DataInputStream fluxo_entrada = new DataInputStream(clientServer.getInputStream());
				String mensagem_texto = fluxo_entrada.readUTF();
				status.append("\n" + mensagem_texto);
				clientServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Windows extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3962047165737977733L;
	private JTextArea status = new JTextArea(400, 400);
	public JTextArea getStatus() {
		return status;
	}
	public void setStatus(JTextArea status) {
		this.status = status;
	}
	public Windows(String data) {
		super("Cliente");
		add(status);
		status.setText("Cliente inicializado!");
		status.setEditable(false);
		this.setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}