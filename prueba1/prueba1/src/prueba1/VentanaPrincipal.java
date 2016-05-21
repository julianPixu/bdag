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

import Design.Metodos;
import excel.CInicialExcel;
import sql.CargaInicial;

import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;

public class VentanaPrincipal{

	private static JFrame frame;
	public static JTable table;
	public static JButton[] tablas;
	
	public static void main(final String[] args) {
		
		Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
		Font fuente =new Font("Serif",Font.BOLD,20);
		frame= Metodos.creaVentana("DB2C: "+args[0], pantalla.width, (pantalla.height-35));
		
		
		
		JScrollPane scrollRamas = new JScrollPane();
			scrollRamas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollRamas.setBounds(10,40, (int)(pantalla.width*0.25),600);
			scrollRamas.setBackground(Color.decode("#F2F2F2"));
			
			JPanel cont_ramas = new JPanel();
				cont_ramas.setLayout(null);
				cont_ramas.setBackground(Color.decode("#F2F2F2"));
				scrollRamas.setViewportView(cont_ramas);
				
				JLabel labelBbdd;
				if(args[0].endsWith("sql")) labelBbdd = new JLabel(CargaInicial.nomBbdd(args));
				else  labelBbdd = new JLabel(args[0]);
					labelBbdd.setFont(new Font("SansSerif", Font.BOLD, 15));
					labelBbdd.setBounds(10, 10, 232, 28);
					cont_ramas.add(labelBbdd);
					
				tablas=new JButton[1]; JLabel[] flechas= new JLabel[1];
				
				if(args[0].endsWith("sql"))tablas= CargaInicial.rellenaPanelTablas(args, cont_ramas, tablas, flechas);
				else if(args[0].endsWith("xlsx")|| args[0].endsWith("xls")) tablas=CInicialExcel.connect(args, cont_ramas, tablas,flechas);
			
		JScrollPane scrollTabla = new JScrollPane();
			scrollTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollTabla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollTabla.setBounds((int)(pantalla.width*0.27),40, (int)(pantalla.width*0.71),600);
			//scrollTabla.setBackground(Color.decode("#E5F3D0"));
			
			 table=new JTable();
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
				
				table.setGridColor(Color.decode("#295300"));
				table.setBackground(Color.decode("#E5F3D0"));
				table.setFont(new Font("SansSerif", Font.PLAIN, 14));
				//table.setEnabled(false);
				table.setCellSelectionEnabled(false);
				table.setVisible(true);
				
				table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			              Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			              JLabel label = (JLabel)cell;
			              String text = label.getText();
			              Color c= cell.getBackground();
			              if (hasFocus) {
			                  cell.setBackground(Color.decode("#FFD700"));
			                  cell.setForeground(Color.BLACK);
			                  cell.setFont(new Font("SansSerif", Font.BOLD, 16));
			              }else if(isSelected){
			            	  cell.setBackground(Color.decode("#FFFF82"));
			            	  cell.setForeground(Color.BLACK);
			            	  
			              }else{
			            	  cell.setBackground(Color.decode("#E5F3D0"));
			              }
			               
			              return cell;
			          }
			      });
		
		
		frame.add(scrollRamas);
		frame.add(scrollTabla);
		frame.setVisible(true);
		
		if(args[0].endsWith("sql")) CargaInicial.rellenaTablas(table, tablas);
		else if(args[0].endsWith("xls")||args[0].endsWith("xlsx")) CInicialExcel.rellenaTablas(args, table, tablas);
		
		
			//INICIALIZAR TOOLBAR DESPUES DE LOS QUE LOS DEMAS OBJETOS ESTÉN INICIALIZADOS
			//PARA EVITAR NULLPOINTERS
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, pantalla.width, 30);
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setBackground(Color.BLACK);
		
		JLabel logo= Metodos.creaImagen("logoicon.jpg", 50, 45);
		toolBar.add(logo, 0);
		JLabel archivo= new JLabel("  Archivo  ");
		archivo.setForeground(Color.WHITE);
		archivo.setFont(fuente);
		Metodos.muestraMenu(archivo, false, args,table, tablas);
		JLabel ayuda= new JLabel("  Ayuda  ");
		ayuda.setForeground(Color.WHITE);
		ayuda.setFont(fuente);
		Metodos.muestraMenu(ayuda, true, args,table, tablas);
		
	
		toolBar.addSeparator();
		
		toolBar.add(archivo,2);
		toolBar.addSeparator();
		toolBar.add(ayuda,4);
		frame.add(toolBar);
	}
	
}
