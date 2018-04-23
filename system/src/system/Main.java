package system;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelListener;

import java.awt.Color;

public class Main {

	private JFrame frmCliente;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	private String hostServer;
	private Service stub;
	
	private void initialize() {
		frmCliente = new JFrame();
		frmCliente.setTitle("Cliente");
		frmCliente.setAlwaysOnTop(true);
		frmCliente.setBounds(100, 100, 450, 300);
		frmCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCliente.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cliente");
		lblNewLabel.setBounds(0, 0, 434, 14);
		frmCliente.getContentPane().add(lblNewLabel);
		
		
		table = new JTable();
		table.setEnabled(false);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		table.setBounds(117, 11, 200, 250);
		frmCliente.getContentPane().add(table);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String host = JOptionPane.showInputDialog("Ip servidor principal");
				setHostServer(host);
				stub = Client.getConnectionServer(getHostServer());
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						while (true) {
							try {
								List<ScannerServer> servers = stub.getServes();
								String[] nameColumns = {"Servidor", "status"};
								Object[][] list = new Object[servers.size()][2];
								for (int i = 0; i < servers.size(); i++) {
									list[i][0] = servers.get(i).getName();
									list[i][1] = "ativo";
								}
								table.setModel(new DefaultTableModel(list, nameColumns));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
						
					}
				}).start();
				
			}
		});
		btnConectar.setBounds(0, 14, 77, 37);
		frmCliente.getContentPane().add(btnConectar);
		
		JButton btnAdicionarSserver = new JButton("Adicionar Servidor");
		btnAdicionarSserver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stub == null) {
					JOptionPane.showMessageDialog(null, "Conecte ao servidor antes");
				} else {
					String serverName = JOptionPane.showInputDialog(null, "ip do servidor scanner");
					String nameService = JOptionPane.showInputDialog(null, "nome do serviço");
					int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Porta que servidor estar operando!"));
					try {
						stub.addServer(serverName, nameService, port);
						JOptionPane.showMessageDialog(null, "Servidor adicionado com sucesso!");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdicionarSserver.setBounds(317, 14, 117, 23);
		frmCliente.getContentPane().add(btnAdicionarSserver);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(317, 48, 89, 23);
		frmCliente.getContentPane().add(btnExcluir);
		
		JButton btnEnviarArquivo = new JButton("Enviar Arquivo");
		btnEnviarArquivo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = Client.selectFile();
				byte[] data = Client.convertToBytes(file);
				try {
					String response = Client.getConnectionServer(getHostServer()).isVirus(file.getName(), data);
				} catch (RemoteException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnEnviarArquivo.setBounds(-12, 62, 119, 23);
		frmCliente.getContentPane().add(btnEnviarArquivo);
	}

	public String getHostServer() {
		return hostServer;
	}

	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}
	
	
}
