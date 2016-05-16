package prueba1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import excel.ConsultasExcel;
import sql.Consultas;

public class VentanaConsultas {

		
	public static void creaVentana(final String path[],final JTable tabla, final JButton[] botones) {
		
		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame= Metodos.creaVentana("CONSULTA", d.width/2, d.height-40);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ButtonGroup bg= new ButtonGroup();
		
		JLabel tipGraph= new JLabel("TIPO DE GRÁFICA:");
			tipGraph.setBounds(10,20,150,30);
			tipGraph.setFont(new Font("Serif",Font.BOLD,16));
			tipGraph.setForeground(Color.YELLOW);
			frame.add(tipGraph);
			
		final JRadioButton[] rbGraph= new JRadioButton[3];
			rbGraph[0]= new JRadioButton("BARRAS");
			rbGraph[1]= new JRadioButton("TARTA");
			rbGraph[2]= new JRadioButton("LINEAS");
		
		final JPanel panel= new JPanel();
			panel.setBounds(10, 55, frame.getWidth()-35, frame.getHeight()-105);
			panel.setOpaque(false);
			//panel.setBackground(Color.BLACK);
			panel.setLayout(null);
			frame.add(panel);
			
		for(int i=0; i<rbGraph.length;i++){
			rbGraph[i].setBounds((i*150)+160,20,150,30);
			rbGraph[i].setFont(new Font("Serif",Font.BOLD,16));
			rbGraph[i].setContentAreaFilled(false);
			bg.add(rbGraph[i]);
			frame.add(rbGraph[i]);
			
			rbGraph[i].addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					switch(e.getActionCommand()){
						case "BARRAS": panel.removeAll(); panelBarra(path,panel,tabla, botones);  panel.repaint();	break;
						case "TARTA":  panel.removeAll(); panelTarta(panel);  panel.repaint();	break;
						case "LINEAS": panel.removeAll(); panelLinea(panel);  panel.repaint();	break;
					}
				}
				
			});
		}
		
		frame.setVisible(true);	
	}
	
	public static void panelBarra(final String[] path, JPanel panel, final JTable tabla, final JButton[] botones){
		
		JLabel l= new JLabel("Selecciona el campo por el que se va ha hacer estadística.");
			l.setBounds(0,0,400,30);
			l.setForeground(Color.WHITE);
		JLabel x= new JLabel("Eje X:");
			x.setBounds(0,35,80,30);
			x.setForeground(Color.WHITE);		
		
		JLabel l2= new JLabel("Selecciona el campo por el que se va ha hacer agrupación.");
			l2.setBounds(0,70,400,30);
			l2.setForeground(Color.WHITE);
		JLabel y= new JLabel("Eje Y:");
			y.setBounds(0,105,80,30);
			y.setForeground(Color.WHITE);

			final JComboBox[] box= new JComboBox[2];
			for(int i=0; i<botones.length;i++){
				if(botones[i].getForeground()==Color.GREEN){
					if(path[0].endsWith("sql"))box[0]=Consultas.campos(botones[i].getText());
					else box[0]=ConsultasExcel.campos(botones[i].getText());
						box[0].setBounds(55, 35, 200, 30);
						panel.add(box[0]);
						
					if(path[0].endsWith("sql"))box[1]=Consultas.campos(botones[i].getText());
					else box[1]=ConsultasExcel.campos(botones[i].getText());
						box[1].setBounds(55, 105, 200, 30);
						panel.add(box[1]);
				}	
			}
			
		JButton consultar= new JButton("CONSULTAR");
			consultar.setBounds(30,500,150,30);
		panel.add(l);
		panel.add(x);	
		panel.add(l2);
		panel.add(y);
		
		panel.add(consultar);
		
		consultar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0; i<botones.length; i++){
					if(botones[i].getForeground()==Color.GREEN){
						if(path[0].endsWith("sql")) Consultas.rellenaTabla(tabla, (String)box[0].getSelectedItem(), (String)box[1].getSelectedItem(), botones[i].getText());
						else ConsultasExcel.rellenaTabla(tabla, (String)box[0].getSelectedItem(), (String)box[1].getSelectedItem(), botones[i].getText());
					}
				}
				
			}
			
		});
	}
	
	public static void panelTarta(JPanel panel){
		JLabel l= new JLabel("SOY CONSUL DE TARTAS");
		l.setBounds(0,0,300,30);
		panel.add(l);
	}
	
	public static void panelLinea(JPanel panel){
		
		JLabel l= new JLabel("Selecciona el campo por el que se va ha hacer estadística.");
			l.setBounds(0,0,400,30);
			l.setForeground(Color.WHITE);
		JLabel x= new JLabel("Eje X:");
			x.setBounds(0,35,80,30);
			x.setForeground(Color.WHITE);
		JTextField tfx= new JTextField();
			tfx.setBounds(55, 35, 200, 30);
			
		JLabel l2= new JLabel("Selecciona el campo por el que se va ha hacer agrupación.");
			l2.setBounds(0,70,400,30);
			l2.setForeground(Color.WHITE);
		JLabel y= new JLabel("Eje Y:");
			y.setBounds(0,105,80,30);
			y.setForeground(Color.WHITE);
		JTextField tfy= new JTextField();
			tfy.setBounds(55, 105, 200, 30);
		panel.add(l);
		panel.add(x);
		panel.add(tfx);
		panel.add(l2);
		panel.add(y);
		panel.add(tfy);
	}

}
