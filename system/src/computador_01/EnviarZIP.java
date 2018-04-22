/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computador_01;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
public class EnviarZIP {
	public static void main(String[] args) throws IOException, Exception {
		FolderZiper.zipador();
		FileInputStream file = new FileInputStream("C:\\VirusScanning\\virusDB\\arquivoZIP\\virus.zip");
		DataInputStream in = new DataInputStream(file);
		byte buffer[] = new byte[2024];

		Socket misochet = new Socket("10.0.0.104", 9999);
		DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());

		// ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());

		byte[] buf = new byte[4096];

		while (true) {
			int len = file.read(buf);
			if (len == -1)
				break;
			fluxo_saida.write(buf, 0, len);

		}

		Servidor_pc1 mimarco = new Servidor_pc1();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class Servidor_pc1 extends JFrame implements Runnable {

	public Servidor_pc1() {

		setBounds(1200, 300, 280, 350);

		JPanel milamina = new JPanel();

		milamina.setLayout(new BorderLayout());

		areatexto = new JTextArea();

		milamina.add(areatexto, BorderLayout.CENTER);

		add(milamina);

		setVisible(true);
		Thread mihilo = new Thread(this);
		mihilo.start();
	}

	private JTextArea areatexto;

	@Override
	public void run() {

		try {
			areatexto.append("\n" + "Estamos aguardadon a resposta");
			ServerSocket servidor = new ServerSocket(7777);
			while (true) {
				areatexto.append("\n recebendo resultado!");
				areatexto.append("\n");
				Socket misocket = servidor.accept();
				DataInputStream fluxo_entrada = new DataInputStream(misocket.getInputStream());
				String mensagem_texto = fluxo_entrada.readUTF();
				areatexto.append("\n" + mensagem_texto);
				misocket.close();
			}

		} catch (IOException ex) {
			Logger.getLogger(Servidor_pc1.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
