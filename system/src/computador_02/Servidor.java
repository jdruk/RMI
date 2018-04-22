package computador_02;



import viruscanningpackage.*;
import javax.swing.*;

import java.awt.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
        
        
       
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
                Thread mihilo = new Thread(this);
		mihilo.start();
      
		}
	
	private	JTextArea areatexto;

    @Override
    public void run() {
       
            try {
                // System.out.println("Estou escutando");
                ServerSocket servidor = new ServerSocket(9999);
                
           
                Socket misocket = servidor.accept();
                DataInputStream fluxo_entrada = new DataInputStream(misocket.getInputStream());
                
                
                 FileOutputStream file = new FileOutputStream("c://pc2//virus.zip");
         
        byte[] buf = new byte[4096];
         int pare = 1;
        while(true){
           int len = fluxo_entrada.read(buf);
            String mensagem_texto = "Iniciando transferencia "+len;
            areatexto.append("\n"+mensagem_texto);
            
            if(pare == 0) {
                System.out.println("LulaNaCadeia");
                areatexto.append("\n"+" Conexão encerrada!");
                break;
            }
            file.write(buf, 0, len);
            areatexto.append("\n"+" Tranferencia finalizada "+len);
            if(len == 1517) {
                System.err.println("entrei!");
               // fluxo_entrada.close();
                //misocket.close();
                 break; 
            }
        }
                misocket.close();
                areatexto.append("\n"+" Conexão encerrada!");
                //Thread.currentThread().stop();
               
                
                System.out.println("LulaNaCadeia");
                 
                 areatexto.append("\n"+"Iniciando tranferencia de arquivos");
                System.err.println("Iniciando transferencia de arquivos");
                clienteEnviar();
                 dispose();
    Middleware n = new Middleware();
    n.run();
            } catch (IOException ex) {
                Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
               
               
    
    }

       
 public void clienteEnviar() throws FileNotFoundException, IOException{
 
      FileInputStream file = new FileInputStream ("C:\\pc2\\virus.zip");
         DataInputStream in = new DataInputStream(file);
        byte buffer[] = new byte[2024];
        
                Socket misochet = new Socket("10.0.0.101",3333);
                DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());
                 Socket misochet2 = new Socket("10.0.101",3399);
                DataOutputStream fluxo_saida2 = new DataOutputStream(misochet2.getOutputStream());
              
              //ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
         
       
         
        byte[] buf = new byte[4096];
         
        while(true){
            int len = file.read(buf);
            if(len == -1) break;
            fluxo_saida.write(buf, 0, len);
            fluxo_saida2.write(buf, 0, len);
                
    }
        
    
}

}