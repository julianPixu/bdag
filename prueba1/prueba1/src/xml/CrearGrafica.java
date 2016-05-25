package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import Design.Metodos;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CrearGrafica {
	
	
	public static void creaGraf(){	
					
		JFileChooser jf1= new JFileChooser(); 
		//jf1.setCurrentDirectory(new java.io.File("."));
		jf1.getCurrentDirectory();
		jf1.setDialogTitle("No se q co�o poner aun");
	    jf1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String ruta = ""; 
		File rutaDestino;
		
		try {
			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 UIManager.put("OptionPane.background", new ColorUIResource(255,215,0));
				
			 UIManager.put("Panel.background", new ColorUIResource(255,215,0));
			 
			if(jf1.showSaveDialog(null)==jf1.APPROVE_OPTION){ 
			ruta = jf1.getSelectedFile().getAbsolutePath()+""; 
			//Aqui ya tiens la ruta,,,ahora puedes crear un fichero n esa ruta y escribir lo k kieras...
			rutaDestino= new File(ruta);
			
					if(new File(ruta).exists()){
						
						Object [] botones= { Metodos.creaBoton("acept.jpg"),
								 Metodos.creaBoton("cancel.jpg"),
			                     };
						
						 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						int seleccion = JOptionPane.showOptionDialog(
								   null,
								   "Ya existe una carpeta con ese nombre, desea reemplazarlo?", 
								   "Aviso",
								   JOptionPane.YES_NO_CANCEL_OPTION,
								   JOptionPane.QUESTION_MESSAGE,
								  new ImageIcon("imagenes/warning.jpg"),    // null para icono por defecto.
								   botones,   // null para YES, NO y CANCEL
								   "opcion 1");
							
							if(JOptionPane.OK_OPTION==seleccion){
								
							//BufferedWriter out = new BufferedWriter(new FileWriter(ruta));
								rutaDestino.mkdir();
								
							}else{
								creaGraf();
							}
											
					}else
						rutaDestino.mkdir();
			
						}
			
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
