package system;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = JOptionPane.showInputDialog("Ip servidor principal");

		File file = Client.selectFile();
		byte[] data = Client.convertToBytes(file);
        try {
			Client.getConnectionServer(host).isVirus(file.getName(), data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
    }
    
    public static Service getConnectionServer(String host) {
    	Service stub = null;
    	try {
            Registry registry = LocateRegistry.getRegistry(host,20000);
            stub = (Service) registry.lookup("Hello");
            String response = stub.getName();
            System.out.println(response);
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
}