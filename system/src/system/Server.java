package system;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import javax.swing.JOptionPane;
        
public class Server  {
	
    public static void main(String args[]) {
        int reply = JOptionPane.showConfirmDialog(null, "Iniciar como Middleware?","Tipo servidor", JOptionPane.YES_NO_OPTION);
        initialize(reply == JOptionPane.YES_OPTION);
    }
    
    public static void initialize(boolean middleware) {
    	Service service = null;
    	if (middleware) {
    		Server.middleware(service);
    	} else {
    		Server.rapidmd(service);
    	}
    	
    }
    
    private static void rapidmd(Service service) {
	}

	private static void middleware(Service service) {
    	try {
            service = new Middleware("localhost");

            Registry registry = LocateRegistry.createRegistry(20000);
            registry.bind("Hello", service);

            System.err.println("Server middleware online");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    } 
}