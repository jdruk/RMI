package viruscanningpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoCliente mimarco = new MarcoCliente();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoCliente extends JFrame {

	public MarcoCliente() {

		setBounds(600, 300, 280, 350);

		LaminaMarcoCliente milamina = new LaminaMarcoCliente();

		add(milamina);

		setVisible(true);
	}

}

class LaminaMarcoCliente extends JPanel {

	public LaminaMarcoCliente() {

		JLabel texto = new JLabel("CLIENTE");

		add(texto);

		campo1 = new JTextField(20);

		add(campo1);

		miboton = new JButton("Enviar");
		EnviaTexto mievento = new EnviaTexto();
		miboton.addActionListener(mievento);
		add(miboton);

	}

	private class EnviaTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				// System.out.println(campo1.getText());

				// 192.168.1.5
				System.out.println("Iniciando Cliente.");
				System.out.println("Iniciando conexão com o servidor.");
				Socket misochet = new Socket("192.168.1.5", 9999);
				System.out.println("Conexão Estabelecida");
				DataOutputStream fluxo_saida = new DataOutputStream(misochet.getOutputStream());
				fluxo_saida.writeUTF(campo1.getText());
				fluxo_saida.close();

			} catch (IOException ex) {
				// Logger.getLogger(LaminaMarcoCliente.class.getName()).log(Level.SEVERE, null,
				// ex);
				System.err.println(ex.getMessage());

			}
		}

	}

	private JTextField campo1;

	private JButton miboton;

}
