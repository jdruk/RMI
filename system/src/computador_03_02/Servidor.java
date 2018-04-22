package computador_03_02;



import computador_03_01.*;
import computador_02.*;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.management.Query.lt;

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
                ServerSocket servidor = new ServerSocket(3399);
        
                
          // System.out.println("Estou escutando");
              
           
                Socket misocket = servidor.accept();
                DataInputStream fluxo_entrada = new DataInputStream(misocket.getInputStream());
                
                
                 FileOutputStream file = new FileOutputStream("c://pc31//virus.zip");
         
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
               
                
                String CaminhoSignature = "C:\\pc31";
		//int tamanho = 1258;
                //String CaminhoScarnning = "C:\\VirusScanning\\virusFolderToScan/";
                String destino = "C:\\pc31/virus.zip";
                
                Componente_01_Descompactador descompactador = new Componente_01_Descompactador(destino,CaminhoSignature);
                
                areatexto.append("\n"+"Descompactação concluida");
                
                 areatexto.append("\n"+" Conexão encerrada!");
                   
		int tamanho1 = 1258;
                String CaminhoScarnning = "C:\\pc31/";
              //  String destino = "C:\\pc2/virus.zip";
  Componente_02_VirusSignaturesCreator signatures = new Componente_02_VirusSignaturesCreator(CaminhoSignature,tamanho1);              
  Componente_03_VirusScanningServer virusScanner = new Componente_03_VirusScanningServer(signatures.getSignatureDB(),CaminhoScarnning,tamanho1);
      		long startTime = System.currentTimeMillis();
		int result = virusScanner.localScanFolder();
		String resultado = "Number of viruses found: " + result;
		String tempo = "Time: " + (System.currentTimeMillis() - startTime) + " ms";

                ClienteEnvia(resultado, tempo);
                              
                
                //Thread.currentThread().stop();
                dispose();
                
            
       
               
                
                
  
                
            } catch (IOException ex) {
                Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
               
               
    
    }

 public String nome(){
  //  String r = "diarreia";
     UUID uuid = UUID.randomUUID();
String myRandom = uuid.toString();
System.out.println(myRandom.substring(0,20));
 return myRandom.substring(0,20);
 
 }
   public void ClienteEnvia(String resultado, String tempo ) throws IOException{
        Socket misochet = new Socket("192.168.1.5",5555);
                System.out.println("Conexão Estabelecida");
                DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());
                fluxo_saida.writeUTF(resultado+"\n"+tempo);
                fluxo_saida.close();
   } 
}
