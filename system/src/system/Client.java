package system;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = JOptionPane.showInputDialog("Ip servidor principal");
        try {
            Registry registry = LocateRegistry.getRegistry(host,20000);
            Service stub = (Service) registry.lookup("Hello");
            String response = stub.getName();
            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}