import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTPFile;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.commons.net.ftp.*;

/**
 * Clase que gestiona la conexion a un servidor y la de sus ficheros.
 * 
 * @author Jonay Garcia Gonzalez.
 *
 */
public class ClienteFTP extends JFrame {
	private JPanel contentPane;
	private JScrollPane scrollpane;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextField txtServer;
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JTextField txtRuta;
	private JTextField txtNombreFichero_1;
	private JTextField txtDirectorio;
	private JTextField txtNombreFichero;
	private JTextField txtDirectorio_1;
	private JTextField txtNombreFichero_2;

	// Atributos
	private static FTPClient cliente = new FTPClient();

	/**
	 * Constructor
	 */
	public ClienteFTP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Conect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conectar(txtServer.getText(), txtUsuario.getText(), txtContraseña.getText());
			}
		});
		btnNewButton.setBounds(20, 390, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Disconect");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				desconectar();
			}
		});
		btnNewButton_1.setBounds(473, 390, 89, 23);
		contentPane.add(btnNewButton_1);

		txtServer = new JTextField();
		txtServer.setText("...");
		txtServer.setBounds(130, 391, 86, 20);
		contentPane.add(txtServer);
		txtServer.setColumns(10);

		txtUsuario = new JTextField();
		txtUsuario.setText("formacion");
		txtUsuario.setBounds(244, 391, 86, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		txtContraseña = new JTextField();
		txtContraseña.setText("formacion");
		txtContraseña.setBounds(353, 391, 86, 20);
		contentPane.add(txtContraseña);
		txtContraseña.setColumns(10);

		textArea = new JTextArea();
		textArea.setBounds(137, 11, 388, 68);
		contentPane.add(textArea);

		JButton btnNewButton_2 = new JButton("Listar F.");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listarFicheros();
			}
		});
		btnNewButton_2.setBounds(20, 29, 89, 23);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Subir F.");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subirFichero(txtRuta.getText(), txtNombreFichero_1.getText());
			}
		});
		btnNewButton_3.setBounds(20, 91, 89, 23);
		contentPane.add(btnNewButton_3);

		txtRuta = new JTextField();
		txtRuta.setText("Ruta...");
		txtRuta.setBounds(137, 92, 310, 20);
		contentPane.add(txtRuta);
		txtRuta.setColumns(10);

		txtNombreFichero_1 = new JTextField();
		txtNombreFichero_1.setText("Nombre Fichero...");
		txtNombreFichero_1.setBounds(137, 123, 245, 20);
		contentPane.add(txtNombreFichero_1);
		txtNombreFichero_1.setColumns(10);

		JButton btnNewButton_4 = new JButton("Directorio actual");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				directorioActual();
			}
		});
		btnNewButton_4.setBounds(20, 187, 129, 23);
		contentPane.add(btnNewButton_4);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(159, 186, 228, 22);
		contentPane.add(textArea_1);

		JButton btnNewButton_5 = new JButton("Directorio Padre");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambiarADirectorioPadre();
			}
		});
		btnNewButton_5.setBounds(397, 206, 129, 23);
		contentPane.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("Cambiar Directorio");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cambiarDirectorio(txtDirectorio.getText());
			}
		});
		btnNewButton_6.setBounds(20, 227, 129, 23);
		contentPane.add(btnNewButton_6);

		txtDirectorio = new JTextField();
		txtDirectorio.setText("Directorio...");
		txtDirectorio.setToolTipText("");
		txtDirectorio.setBounds(159, 228, 228, 20);
		contentPane.add(txtDirectorio);
		txtDirectorio.setColumns(10);

		JButton btnNewButton_7 = new JButton("Borrar Fichero");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				borrarFichero(txtNombreFichero.getText());
			}
		});
		btnNewButton_7.setBounds(20, 272, 107, 23);
		contentPane.add(btnNewButton_7);

		txtNombreFichero = new JTextField();
		txtNombreFichero.setText("Nombre Fichero...");
		txtNombreFichero.setBounds(137, 273, 245, 20);
		contentPane.add(txtNombreFichero);
		txtNombreFichero.setColumns(10);

		JButton btnNewButton_8 = new JButton("Borrar Fichero");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				borrarFichero(txtDirectorio_1.getText(), txtNombreFichero_2.getText());
			}
		});
		btnNewButton_8.setBounds(20, 329, 107, 23);
		contentPane.add(btnNewButton_8);

		txtDirectorio_1 = new JTextField();
		txtDirectorio_1.setText("Directorio...");
		txtDirectorio_1.setBounds(140, 330, 164, 20);
		contentPane.add(txtDirectorio_1);
		txtDirectorio_1.setColumns(10);

		txtNombreFichero_2 = new JTextField();
		txtNombreFichero_2.setText("Nombre Fichero...");
		txtNombreFichero_2.setBounds(339, 330, 186, 20);
		contentPane.add(txtNombreFichero_2);
		txtNombreFichero_2.setColumns(10);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteFTP frame = new ClienteFTP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Establece una conexion con el servidor FTP
	 * 
	 * @param server Servidor al que nos queremos conectar
	 * @param user   Usuario para poder acceder
	 * @param pwd    Contrase�a para poder acceder
	 * @return True, si la conexion se establecio correctamente, False en caso
	 *         contrario
	 */
	public boolean conectar(String server, String user, String pwd) {

		try {
			// Conectarse y loguearse al servidor FTP
			cliente.connect(server);
			if (cliente.login(user, pwd)) {
				// Entrando en modo pasivo
				cliente.enterLocalPassiveMode();
				// Activar recibir/enviar cualquier tipo de archivo
				cliente.setFileType(FTP.BINARY_FILE_TYPE);

				// Obtener respuesta del servidor y acceder
				int respuesta = cliente.getReplyCode();
				if (FTPReply.isPositiveCompletion(respuesta) == true) {
					System.out.println("Conexion establecida con el servidor: " + server);
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("Usuario o contrase�a incorrectos.");
				return false;
			}

		} catch (IOException e) {
			System.out.println("Host del servidor incorrecto: " + server);
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Lista los ficheros del directorio actual del servidor FTP
	 */
	public void listarFicheros() {

		// lists files and directories in the current working directory
		FTPFile[] files;
		textArea.setText("");
		try {
			files = cliente.listFiles();

			// iterates over the files and prints details for each
			DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (FTPFile file : files) {
				String details = file.getName();
				if (file.isDirectory()) {
					details = "[" + details + "]";
				}
				details += "\t\t" + file.getSize();
				details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
				textArea.append("\n" + details);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sube un fichero al servidor FTP a un directorio especificado por parametro
	 * 
	 * @param rutaCompleta: Ruta absoluta del fichero a subir, incluido el nombre
	 *        del fichero, por ejemplo, "D:\\\dir1\\file1.pdf"
	 * @param nombreFichero: Nombre del fichero, incluida su extension, con el que
	 *        se guardara en el servidor FTP
	 * @return directorioFTP: Directoiro en el servidor FTP donde se guardara el
	 *         fichero
	 */
	public void subirFichero(String rutaCompleta, String nombreFichero) {

		InputStream input;
		try {
			input = new FileInputStream(new File(rutaCompleta));
			cliente.storeFile(nombreFichero, input);
			System.out.println("Archivo enviado");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Imprime el directorio actual del servidor FTP
	 */
	public void directorioActual() {

		textArea_1.setText("");
		try {
			String directorio = cliente.printWorkingDirectory();
			textArea_1.append(directorio);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Nos posicionamos en el directorio raiz del servidor FTP
	 */
	public void cambiarADirectorioPadre() {

		try {
			cliente.changeToParentDirectory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Nos posicionamos en un directorio en el servidor FTP especificado por
	 * parametro, esta ruta es a partir del directorio actual
	 * 
	 * @param directorio: Directorio en el servidor FTP en el que nos queremos
	 *        posicionar a partir del directorio actual
	 */
	public void cambiarDirectorio(String directorio) {

		try {
			String directorioActual = cliente.printWorkingDirectory();
			cliente.changeWorkingDirectory(directorioActual + "/" + directorio);
			System.out.println("Cambiando al directorio: " + directorio);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Borra un fichero en el directorio actual del servidor FTP
	 * 
	 * @param fichero: Fichero a borrar del directorio actual en el servidor FTP
	 */
	public void borrarFichero(String fichero) {

		try {
			boolean deleted = cliente.deleteFile(fichero);

			if (deleted == true) {
				System.out.println("Se ha borrado el fichero");
			} else {
				System.out.println("No se ha podido borrar el fichero");
			}
		} catch (Exception e) {
			System.err.println("Ha habido un problema " + e.getMessage());
		}

	}

	/**
	 * Borra un fichero del servidor FTP del directorio especificado por parametro
	 * 
	 * @param directorio: Directorio en el servidor FTP donde se encuentra el
	 *        fichero a borrar
	 * @param fichero: Fichero a borrar en el servidor FTP
	 */
	public void borrarFichero(String directorio, String fichero) {
		File direct = new File(directorio);
		if (!direct.isFile()) {
			try {
				if (cliente.deleteFile("/" + direct + "/" + fichero)) {
					System.out.println("Se ha borrado el fichero");
				} else {
					System.out.println("No se ha podido borrar el fichero");
				}
			} catch (Exception e) {
				System.err.println("Ha habido un problema " + e.getMessage());
			}
		} else {
			System.out.println("No hay fichero");
		}
	}

	/**
	 * Cerramos la sesion cno el servidor FTP
	 */
	public void desconectar() {

		if (cliente.isConnected()) {
			try {
				cliente.logout();
				cliente.disconnect();
				System.out.println("Desconectado del servidor FTP.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}