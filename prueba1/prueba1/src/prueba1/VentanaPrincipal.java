package prueba1;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;

public class VentanaPrincipal{

	private static JFrame frame;
	private JLabel background;
	private static JTable tabla;
	private JPanel cont_ramas;
	private JLabel labelBbdd;
	private JScrollPane scrollTabla;
	private JToolBar toolBar;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		
		Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
		Font fuente =new Font("Serif",Font.BOLD,20);
		frame= Metodos.creaVentana("DB2C: "+args[0], pantalla.width, (pantalla.height-35));
		
		
		
		JToolBar toolBar = new JToolBar();
			toolBar.setBounds(0, 0, pantalla.width, 30);
			toolBar.setFloatable(false);
			toolBar.setBackground(Color.BLACK);
			
			JLabel logo= Metodos.creaImagen("logoicon.jpg", 50, 45);
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
		
			toolBar.addSeparator();
			toolBar.add(archivo,2);
		
		JScrollPane scrollRamas = new JScrollPane();
			scrollRamas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollRamas.setBounds(10,40, (int)(pantalla.width*0.25),600);
			scrollRamas.setBackground(Color.decode("#F2F2F2"));
			
			JPanel cont_ramas = new JPanel();
				cont_ramas.setLayout(null);
				cont_ramas.setBackground(Color.decode("#F2F2F2"));
				scrollRamas.setViewportView(cont_ramas);
				
				JLabel labelBbdd = new JLabel("Nombre BBDD");
					labelBbdd.setFont(new Font("SansSerif", Font.BOLD, 15));
					labelBbdd.setBounds(10, 10, 232, 28);
					cont_ramas.add(labelBbdd);
			
				JLabel flecha1= Metodos.creaImagen("arrow.jpg", 30, 30);
					flecha1.setBounds(30,40,30,30);
				JLabel flecha2= Metodos.creaImagen("arrow.jpg", 30, 30);
					flecha2.setBounds(30,80,30,30);
				
				
				ButtonGroup bg= new ButtonGroup();
				final JButton[] tablas= new JButton[2];
				
				for(int i=0;i<tablas.length; i++){
					tablas[i]= new JButton("Tabla"+i);
					tablas[i].setBounds(65,(i+1)*40,120,30);
					tablas[i].setContentAreaFilled(false);
					tablas[i].setBorderPainted(false);
					
					bg.add(tablas[i]);
					cont_ramas.add(tablas[i]);
					tablas[i].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							
							for(int n=0; n<tablas.length;n++){
								if(tablas[n]==e.getSource())tablas[n].setForeground(Color.GREEN);
								else tablas[n].setForeground(Color.BLACK);
							}	
						}		
					});
				}
				
				cont_ramas.add(flecha1);
				cont_ramas.add(flecha2);
				//cont_ramas.add(bg);
				//cont_ramas.add(tablas);
				
				
			
		JScrollPane scrollTabla = new JScrollPane();
			scrollTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollTabla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollTabla.setBounds((int)(pantalla.width*0.27),40, (int)(pantalla.width*0.71),600);
			scrollTabla.setBackground(Color.decode("#E5F3D0"));
			
			JTable table=new JTable();
				scrollTabla.setViewportView(table);
				
				table.setModel(new DefaultTableModel(
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
				int columnas= table.getColumnCount();
				table.setRowHeight(40);	
				for(int i=0; i<columnas;i++) table.getColumnModel().getColumn(i).setMinWidth(200);
				table.setGridColor(Color.decode("#295300"));
				table.setBackground(Color.decode("#E5F3D0"));
				table.setVisible(true);
				
				table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			              Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			              JLabel label = (JLabel)cell;
			              String text = label.getText();
			              Color c= cell.getBackground();
			              if (hasFocus) {
			                  cell.setBackground(Color.decode("#FFD700"));
			              }else if(isSelected){
			            	  cell.setBackground(Color.decode("#FFFF82"));
			              }else{
			            	  cell.setBackground(Color.decode("#E5F3D0"));
			              }
			               
			              return cell;
			          }
			      });
		
		frame.add(toolBar);
		frame.add(scrollRamas);
		frame.add(scrollTabla);
		frame.setVisible(true);
		/*EventQueue.invokeLater(new Runnable() {
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

	
	public VentanaPrincipal(String s) {
		
		initialize(s);
	}


	private void initialize(String s) {
		
		
		
		frame = new JFrame();
		Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0,0, pantalla.width, (pantalla.height-35));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DB2C: "+s);
		
		//background= Metodos.CreaFondo("fondo.jpg",pantalla.width, (pantalla.height-35));
		//frame.setContentPane(background);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.BLACK);
		
		Font fuente =new Font("Serif",Font.BOLD,16);
		JLabel logo= Metodos.creaImagen("logoicon.jpg", 60, 45);
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
		
		scrollTabla = new JScrollPane();
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
		
		cont_ramas = new JPanel();
		scrollRamas.setViewportView(cont_ramas);
		cont_ramas.setLayout(null);
		
		labelBbdd = new JLabel("Nombre BBDD");
		labelBbdd.setFont(new Font("SansSerif", Font.BOLD, 15));
		labelBbdd.setBounds(10, 11, 232, 28);
		cont_ramas.add(labelBbdd);
		
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
		frame.getContentPane().setLayout(groupLayout);*/
	}
	
}
