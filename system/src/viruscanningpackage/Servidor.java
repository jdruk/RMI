package viruscanningpackage;

import javax.swing.*;

import java.awt.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoServidor mimarco = new MarcoServidor();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class MarcoServidor extends JFrame implements Runnable {

	public MarcoServidor() {

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
			// System.out.println("Estou escutando");
			ServerSocket servidor = new ServerSocket(9999);

			while (true) {
				Socket misocket = servidor.accept();
				DataInputStream fluxo_entrada = new DataInputStream(misocket.getInputStream());

				FileOutputStream file = new FileOutputStream("c://pc2//virus.zip");

				byte[] buf = new byte[4096];

				while (true) {
					String mensagem_texto = "Iniciando transferencia";
					areatexto.append("\n" + mensagem_texto);
					int len = fluxo_entrada.read(buf);
					if (len == -1)
						break;
					file.write(buf, 0, len);
					areatexto.append("\n" + " Tranferencia finalizada");
				}

				misocket.close();
				areatexto.append("\n" + " Conexão encerrada!");
			}

		} catch (IOException ex) {
			Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
