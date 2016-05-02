package prueba1;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class VentanaPrincipal {

	private JFrame frame;
	private JLabel background;
	private static JTable tabla;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal(args[0]);
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(String s) {
		
		initialize(s);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String s) {
		frame = new JFrame();
		Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0,0, pantalla.width, (pantalla.height-35));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DB2C: "+s);
		
		background= Metodos.CreaFondo("fondo.jpg",pantalla.width, (pantalla.height-35));
		frame.setContentPane(background);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.BLACK);
		
		Font fuente =new Font("Serif",Font.BOLD,16);
		JLabel logo= Metodos.CreaFondo("logoicon.jpg", 60, 45);
		toolBar.add(logo, 0);
		JLabel archivo= new JLabel("Archivo");
		archivo.setForeground(Color.WHITE);
		archivo.setFont(fuente);
		archivo.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				JOptionPane.showMessageDialog(null, "Ahora veras la tablaaa!!!");
			}
		});
		//JLabel espacio= new JLabel("       ");
		//toolBar.add(espacio,1);
		toolBar.addSeparator();
		toolBar.add(archivo,2);
		
		 
		JScrollPane scrollRamas = new JScrollPane();
		scrollRamas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollTabla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 1344, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollRamas, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollTabla, GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollTabla)
						.addComponent(scrollRamas, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(119, Short.MAX_VALUE))
		);
		
		tabla = new JTable();
		tabla.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				" ", " ", " ", " ", " "
			}
		));
		scrollTabla.setViewportView(tabla);
		
		int columnas= tabla.getColumnCount();
		tabla.setRowHeight(40);	
		for(int i=0; i<columnas;i++) tabla.getColumnModel().getColumn(i).setMinWidth(200);
		tabla.setVisible(false);
		//tabla.setBackground(Color.decode("#A7FF4F"));
		frame.getContentPane().setLayout(groupLayout);
	}
}
