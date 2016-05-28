package prueba1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Design.Estilo;
import Design.Metodos;
import excel.ConsultasExcel;
import sql.Consultas;
import xml.CrearGrafica;
import xml.ObjetoGraph;

public class VentanaConsultas {

	
	
	
	static JTextField nombreX= new JTextField();
	static JTextField nombreY= new JTextField();
	public static boolean bfecha= false;
	
	public static void creaVentana(final String path[],final JTable tabla, final JButton[] botones) {
		
		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		final JFrame frame= Metodos.creaVentana("CONSULTA", d.width*5/8, d.height-40);
			frame.setBounds(d.width*3/16, 0,  d.width*5/8, d.height-40);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
		ButtonGroup bg= new ButtonGroup();
		
		JLabel config_c= new JLabel("CONFIGURACION DATOS _____________________");
			config_c.setBounds(10,5,frame.getWidth(), 30);
			config_c.setFont(Estilo.f_titulo);
			config_c.setForeground(Estilo.verde_oscuro);
			frame.add(config_c);
		
		JLabel tipGraph= new JLabel("TIPO DE GRÁFICA:");
			tipGraph.setBounds(20,40,150,30);
			tipGraph.setFont(Estilo.f_medio);
			tipGraph.setForeground(Color.YELLOW);
			frame.add(tipGraph);
			
		final JRadioButton[] rbGraph= new JRadioButton[3];
			rbGraph[0]= new JRadioButton("BARRAS");
			rbGraph[1]= new JRadioButton("TARTA");
			rbGraph[2]= new JRadioButton("LINEAS");
			
		for(int i=0; i<rbGraph.length;i++){
			rbGraph[i].setBounds((i*170)+180,40,150,30);
			rbGraph[i].setFont(Estilo.f_medio);
			rbGraph[i].setContentAreaFilled(false);
			bg.add(rbGraph[i]);
			frame.add(rbGraph[i]);
		}
		
		JLabel l= new JLabel("Selecciona el campo por el que se va ha hacer estadística.");
			l.setBounds(20,75,400,30);
			l.setForeground(Estilo.blanco);
			l.setFont(Estilo.f_medio);
			frame.add(l);
			
		JLabel x= new JLabel("Eje X ->");
			x.setBounds(20,110,80,30);
			x.setForeground(Estilo.blanco);
			x.setFont(Estilo.f_medio);
			frame.add(x);
			
		JLabel xtab= new JLabel("TABLA:");
			xtab.setBounds(160,110,80,30);
			xtab.setForeground(Estilo.blanco);
			xtab.setFont(Estilo.f_medio);
			frame.add(xtab);
			
			
		JLabel xcamp= new JLabel("CAMPO:");
			xcamp.setBounds(480,110,80,30);
			xcamp.setForeground(Estilo.blanco);
			xcamp.setFont(Estilo.f_medio);
			frame.add(xcamp);
			
		JLabel lfech= new JLabel("* Si el campo es fecha, marcalo y escoge formato:");
			lfech.setBounds(20,150,300,30);
			lfech.setForeground(Estilo.amarillo);
			frame.add(lfech);
			
		final JRadioButton rfech= new JRadioButton("FECHA");
			rfech.setBounds(310, 150, 80, 30);
			rfech.setFont(Estilo.f_medio);
			rfech.setForeground(Estilo.blanco);
			rfech.setOpaque(false);
			frame.add(rfech);
			
		final JComboBox boxFecha= new JComboBox();
			boxFecha.addItem("DIAS"); boxFecha.addItem("MESES"); boxFecha.addItem("AÑOS");
			boxFecha.setBounds(410,155,60,25);
			boxFecha.setVisible(false);
			frame.add(boxFecha);
			
			rfech.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if(rfech.isSelected()){ boxFecha.setVisible(true);  bfecha=true; }
					else{ boxFecha.setVisible(false);   bfecha=false; }	
				}		
			});
		
		JLabel l2= new JLabel("Selecciona el campo por el que se va ha hacer agrupación");
			l2.setBounds(20,190,d.width/2,25);
			l2.setForeground(Color.WHITE);
			l2.setFont(Estilo.f_medio);
			frame.add(l2);
		JLabel l3= new JLabel("(Campo numérico si escoge por VALOR o SUMATORIO)");
			l3.setBounds(20,210,d.width/2,25);
			l3.setForeground(Color.WHITE);
			l3.setFont(Estilo.f_medio);
			frame.add(l3);
			
		JLabel y= new JLabel("Eje Y:");
			y.setBounds(20,250,80,30);
			y.setForeground(Color.WHITE);
			y.setFont(Estilo.f_medio);
			frame.add(y);
			
		JLabel ytab= new JLabel("TABLA:");
			ytab.setBounds(160,250,80,30);
			ytab.setForeground(Estilo.blanco);
			ytab.setFont(Estilo.f_medio);
			frame.add(ytab);
		JLabel ycamp= new JLabel("CAMPO:");
			ycamp.setBounds(480,250,80,30);
			ycamp.setForeground(Estilo.blanco);
			ycamp.setFont(Estilo.f_medio);
			frame.add(ycamp);
			
		final JComboBox[] box= new JComboBox[4];
		
		for(int i=0; i<4; i=i+2){
			box[i]= new JComboBox();
			box[i+1]= new JComboBox();
			System.out.println(path[0]);
			if(path[0].endsWith("sql")){
				Consultas.campos(box[i], "SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema='"+path[0].replace(".sql", "")+"';");
			}
			box[i].setBounds(260, ((i/2)*140)+110, 200, 30);
			box[i+1].setBounds(560, ((i/2)*140)+110, 200, 30);
			box[i].setFont(Estilo.f_medio);
			box[i+1].setFont(Estilo.f_medio);
			frame.add(box[i]);
			frame.add(box[i+1]);
			
			final int I=i;
			box[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					
					if(box[I].hasFocus()){
						
						if(path[0].endsWith("sql")) Consultas.campos(box[I+1],"SHOW COLUMNS FROM "+(String)box[I].getSelectedItem()+";");
						
					}
				}
				
			});
		}
		
		/*	else box[i]=ConsultasExcel.campos(botones[i].getText());
					box[i].setBounds(100, 110, 200, 30);
					frame.add(box[i]);
					
		if(path[0].endsWith("sql"))box[1]=Consultas.campos(botones[i].getText());
				else box[1]=ConsultasExcel.campos(botones[i].getText());
					box[1].setBounds(100, 250, 200, 30);
					frame.add(box[1]);*/
				
		
		
		JLabel tip= new JLabel("Valorar campos por su :");
			tip.setBounds(20,315,180,30);
			tip.setForeground(Color.WHITE);
			tip.setFont(Estilo.f_medio);
			frame.add(tip);
			
		ButtonGroup bg2= new ButtonGroup();
		final JRadioButton[] tipos= new JRadioButton[3];
			tipos[0]= new JRadioButton("VALOR");
			tipos[1]= new JRadioButton("SUMATORIO");
			tipos[2]= new JRadioButton("CANTIDAD");
			
			for(int i=0; i<tipos.length;i++){
				tipos[i].setBounds((i*130)+220,315,130,30);
				if(i==0)tipos[i].setSelected(true);
				if(i==2)tipos[i].setBounds(500,315,130,30);
				tipos[i].setFont(Estilo.f_pequeño);
				tipos[i].setForeground(Estilo.blanco);
				tipos[i].setOpaque(false);
				bg2.add(tipos[i]);
				frame.add(tipos[i]);
			}
			
		JLabel config_d= new JLabel("CONFIGURACION DISEÑO ____________________");
			config_d.setBounds(10,350,frame.getWidth(), 30);
			config_d.setFont(Estilo.f_titulo);
			config_d.setForeground(Estilo.verde_oscuro);
			frame.add(config_d);
			
		
		JLabel nomX= new JLabel("Nombre para eje X:");
			nomX.setBounds(20, 400, 150, 30);
			nomX.setFont(Estilo.f_medio);
			nomX.setForeground(Estilo.blanco);
			nombreX.setBounds(160,405,220,25);
			frame.add(nomX);
			frame.add(nombreX);
			
		JLabel nomY= new JLabel("Nombre para eje Y:");
			nomY.setBounds(420, 400, 150, 30);
			nomY.setFont(Estilo.f_medio);
			nomY.setForeground(Estilo.blanco);
			nombreY.setBounds(560,405,220,25);
			frame.add(nomY);
			frame.add(nombreY);
			
		final JLabel[] palet= new JLabel[4];
			palet[0]= new JLabel("Fondo");
		final JLabel [] cololes= new JLabel[4];
		
		for(int i=0;i<cololes.length;i++){
			if(i!=0) palet[i]= new JLabel("Color "+i);
			palet[i].setBounds((i*130)+170, 440, 130, 30);
			palet[i].setFont(Estilo.f_medio);
			palet[i].setForeground(Estilo.blanco);
			
			cololes[i]= new JLabel();
			cololes[i].setBounds((i*130)+170, 480, 40, 40);
			cololes[i].setOpaque(true);
			cololes[i].setBackground(Estilo.negro);
			
			
			frame.add(cololes[i]);
			frame.add(palet[i]);
			
			final int I=i;
			cololes[i].addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent arg0) { ventanaColor(cololes[I]);}
			});
		}
		
		JLabel lsize= new JLabel("Escoge anchura de barras/lineas:");
			lsize.setBounds(20, 545, 230, 20);
			lsize.setFont(Estilo.f_medio);
			lsize.setForeground(Estilo.blanco);
			frame.add(lsize);
		final JSlider slider= new JSlider();
			slider.setBounds(280, 545, 300, 40);
			slider.setMinimum(0);
			slider.setMaximum(100);
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintTrack(true);
			slider.setPaintLabels(true);
			slider.setValue(20);
			slider.setOpaque(false);
			slider.setFont(Estilo.f_pequeño);
			slider.setForeground(Estilo.amarillo);
			
		final JLabel size= new JLabel("20 px");
			size.setBounds(610,555, 50,30);
			size.setFont(Estilo.f_titulo);
			size.setOpaque(true);
			size.setBackground(Estilo.blanco);
			frame.add(size);
			
			frame.add(slider);
		
			slider.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e){  size.setText(Integer.toString(slider.getValue())+" px");	}
			});
		
		
		JButton [] options= new JButton[3];
			options[0]= new JButton("CONSULTAR");
			options[1]= new JButton("CREAR GRÁFICA");
			options[2]= new JButton("CANCELAR");
		
			for(int i=0; i<options.length; i++){
				options[i].setBounds((i*260)+70,d.height-140,200,30);
				frame.add(options[i]);
				
				options[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						
						switch(e.getActionCommand()){
						
							case "CONSULTAR":
								for(int i=0; i<botones.length; i++){
									if(botones[i].getForeground()==Color.GREEN){
										if(path[0].endsWith("sql")){
											
											Object[][] datos= Consultas.creaConsulta(box,  bfecha,(String)boxFecha.getSelectedItem(), tipos);
											Consultas.rellenaTabla(tabla, datos,(String)box[1].getSelectedItem(), (String)box[3].getSelectedItem());
										}
																	
										else{
											Object[][] datos= ConsultasExcel.creaConsulta(box, botones[i].getText(), bfecha,(String)boxFecha.getSelectedItem(), tipos);
											ConsultasExcel.rellenaTabla(tabla, datos, (String)box[0].getSelectedItem(), (String)box[1].getSelectedItem());
										}
									}
								}
								break;
							
							
							case "CREAR GRÁFICA": 
								
								int tip=0;
								if(rbGraph[1].isSelected()) tip=1;
								if(rbGraph[2].isSelected()) tip=2;
								
								String[] colores= new String[4];
								for(int i=0; i<cololes.length; i++){
									String hex = Integer.toHexString(cololes[i].getBackground().getRGB()).substring(2);
									System.out.println(hex);
									colores[i]=hex;
								}
								for(int i=0; i<botones.length; i++){
									if(botones[i].getForeground()==Color.GREEN){
										Object[][] datos= Consultas.creaConsulta(box, bfecha,(String)boxFecha.getSelectedItem(), tipos);
										ObjetoGraph graf= new ObjetoGraph(tip, nombreX.getText(),nombreY.getText(), colores, size.getText(), datos);
										CrearGrafica.creaGraf(graf);
									}
								}
								
								
								break;
								
							case "CANCELAR":	 frame.dispose();  break;
						}
				}});	
				
			}
			
		
		
		
			
		frame.setVisible(true);	
	}
	
	
	public static void ventanaColor(final JLabel color){
		
		final JFrame frame2= new JFrame();
			frame2.setBounds(0,0,500,300);
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.setLocationRelativeTo(null);
			frame2.setUndecorated(true);
			frame2.setResizable(false);
			frame2.setLayout(null);
		JPanel p= new JPanel();
			p.setBounds(0, 0, 500, 300);
			p.setLayout(null);
			p.setBackground(Color.BLACK);
			frame2.add(p);
		final JColorChooser jcc= new JColorChooser();
			jcc.setBorder(BorderFactory.createTitledBorder("ESCOGE UN COLOR"));
			jcc.setBounds(0, 0, 500, 200);
		
		final JLabel prueba= new JLabel("COLOR ESCOGIDO");
			prueba.setFont(new Font("Serif",Font.BOLD, 30));
			jcc.setBackground(Color.GREEN);
			jcc.setPreviewPanel(prueba);
				p.add(jcc);
		JButton ok = new JButton("OK");
			ok.setBounds(100, 250, 100, 40);
			p.add(ok);
			
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					color.setBackground(jcc.getColor());
					frame2.dispose();
				}
				
			});
		JButton cancel = new JButton("CANCEL");
			cancel.setBounds(280, 250, 100, 40);
			p.add(cancel);
			cancel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame2.dispose();	
				}
			});
			
		frame2.setVisible(true);
	
	}

}
