/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computador_02;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author esdra
 */
public class Middleware extends JFrame implements Runnable{
	
	public Middleware(){
		
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
                ServerSocket servidor = new ServerSocket(5555);
                 Socket misocket = servidor.accept();
                DataInputStream fluxo_entrada = new DataInputStream(misocket.getInputStream()); 
                 areatexto.append("\n Middleware");
                 
                 String mensagem_texto = "resultados: \n";
                while(true){  
               
                areatexto.append("\n");
               
                 mensagem_texto += fluxo_entrada.readUTF();
                 mensagem_texto += "\n";
                     System.out.println(mensagem_texto);
                misocket.close();
            ClienteEnvia(mensagem_texto);
            areatexto.append("enviando mensagem!");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Middleware.class.getName()).log(Level.SEVERE, null, ex);
            }
        
                
    }     
     public void ClienteEnvia(String mensagem ) throws IOException{
        Socket misochet = new Socket("10.0.0.101",7777);
                System.out.println("Conexão Estabelecida");
                DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());
                fluxo_saida.writeUTF(mensagem);
                fluxo_saida.close();
   } 
}