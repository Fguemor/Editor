package actividad.tres;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Editor extends JFrame implements ActionListener {

	//Instancia

	JTextArea area;
	JScrollPane panelDesplazamiento;
	JSpinner tamanioFuente;
	JLabel fuente;
	JButton color;
	JComboBox cuadro;

	JMenuBar menuBar;
	JMenu menu;
	JMenu barraHerramientas;
	JMenuItem abrir;
	JMenuItem crear;
	JMenuItem guardar;
	JMenuItem numeroLinea;
	JMenuItem palabrasEscritas;
	JMenuItem salir;
	JMenuItem recientemente;
	JMenu barraEstado;





	//Constructor
	public Editor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Editor de Texto:FGM");
		this.setSize(600,600);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);


		

		area=new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		//tamaño y tipo de letras
		area.setFont(new Font("Arial",Font.PLAIN,20));

		panelDesplazamiento=new JScrollPane(area);
		panelDesplazamiento.setPreferredSize(new Dimension(450,450));
		panelDesplazamiento.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		fuente=new JLabel("Fuente: ");

		tamanioFuente=new JSpinner();
		tamanioFuente.setPreferredSize(new Dimension(50,25));
		tamanioFuente.setValue(20);
		tamanioFuente.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int) tamanioFuente.getValue()));

			}
		});
		color =new JButton("Color: ");
		color.addActionListener(this);

		String[] fuentes=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		cuadro=new JComboBox(fuentes);
		cuadro.addActionListener(this);
		cuadro.setSelectedItem("Arial");
		
		
		

		//---------------------MENUBAR
		menuBar =new JMenuBar();
		menu =new JMenu("Menu");
		barraHerramientas=new JMenu("Barra de herramientas");
		abrir= new JMenuItem("Abrir");
		crear= new JMenuItem("Crear");
		guardar= new JMenuItem("Guardar");
		numeroLinea= new JMenuItem("Número de línea edición");
		palabrasEscritas= new JMenuItem("Palabras escritas");
		salir=new JMenuItem("Salir");
		recientemente=new JMenuItem("Abrir archivo más reciente");


		menuBar.add(barraHerramientas);
		barraHerramientas.add(salir);
		barraHerramientas.add(abrir);
		barraHerramientas.add(crear);
		barraHerramientas.add(guardar);

		salir.addActionListener(this);
		abrir.addActionListener(this);
		crear.addActionListener(this);
		guardar.addActionListener(this);
		
		//--------------------------------------

		this.setJMenuBar(menuBar);
		this.add(color);
		this.add(fuente);
		this.add(tamanioFuente);
		this.add(cuadro);
		this.add(panelDesplazamiento);
		
		
		JPanel statusPanel = new JPanel(); 
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED)); 
		this.add(statusPanel, BorderLayout.SOUTH); 
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16)); 
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("Barra de estado: "); 
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT); 
		statusPanel.add(statusLabel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==color) {
			JColorChooser colorElegido=new JColorChooser();
			Color color=JColorChooser.showDialog(null, "Elige un color: ", Color.black);
			area.setForeground(color);
		}
		if (e.getSource()==cuadro) {
			area.setFont(new Font((String)cuadro.getSelectedItem(),Font.PLAIN, area.getFont().getSize()));
		}
		if (e.getSource()==abrir) {
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter =new FileNameExtensionFilter("Archivo de texto","txt");
			fileChooser.setFileFilter(filter);

			int response=fileChooser.showOpenDialog(null);
			if (response==JFileChooser.APPROVE_OPTION) {
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					Scanner entrada=new Scanner(file);
					if (file.isFile()) {
						while(entrada.hasNextLine()) {
							String line=entrada.nextLine()+"\n";
							area.append(line);

						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getSource()==crear) {

		}
			if (e.getSource()==guardar) {
				JFileChooser  fileChooser =new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				int response =fileChooser.showSaveDialog(null);
				if (response ==JFileChooser.APPROVE_OPTION) {
					File file;
					PrintWriter fileOut = null;
					file=new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						fileOut=new PrintWriter(file);
						fileOut.print(area.getText());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					finally {
						fileOut.close();
					}
				}

			}
			
		if (e.getSource()==salir) {
			System.exit(0);
		}
		}
}

		
		