package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import org.apache.commons.io.FileUtils;

import Design.Metodos;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CrearGrafica {
	
	
	public static void creaGraf(ObjetoGraph graf){	
					
		JFileChooser jf1= new JFileChooser(); 
		//jf1.setCurrentDirectory(new java.io.File("."));
		jf1.getCurrentDirectory();
		jf1.setDialogTitle("No se q coño poner aun");
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
								rellenaCarpeta(rutaDestino, graf);
								
								
							}else{
								
								creaGraf(graf);
							}
											
					}else
						rutaDestino.mkdir();
						rellenaCarpeta(rutaDestino, graf);
					}
			
				}catch (ClassNotFoundException e) { e.printStackTrace();
				} catch (InstantiationException e) { e.printStackTrace();
				} catch (IllegalAccessException e) { e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
	}
	
	
	public static void rellenaCarpeta(File dirDestino, ObjetoGraph graf){
		
		File res= new File("res");
		
		if(res.exists()){
			if(res.isDirectory()){
				
				try {
					FileUtils.copyDirectory(res, dirDestino);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*for(File f: res.listFiles()){ 
					if(f.isDirectory()){
						File dir= new File(dirDestino.getPath()+"\\"+f.getName());
						dir.mkdir();
						for(File f2: f.listFiles()){
							
							copiaPega(f2, dir , f2.getName());
						}
					
					}else copiaPega(f, dirDestino, f.getName());
				}*/
				
				String xml= CreahtmlYxml.creaXML(dirDestino.getAbsolutePath()+"\\"+dirDestino.getName(), graf);
				CreahtmlYxml.creaHtml(dirDestino.getAbsolutePath()+"\\"+dirDestino.getName(), xml, graf.getColores()[0]);
				
			}
		}
	}
	
	public static void copiaPega(File origen, File destino, String nuevo){
		
		File f= new File(destino.getAbsolutePath()+"\\"+nuevo);
		
		if(!f.exists()){
			
			try {
				FileUtils.copyDirectory(origen, f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*try {
				
				BufferedReader br= new BufferedReader(new FileReader(origen));
				BufferedWriter fw= new BufferedWriter(new FileWriter(f));
				String s;
				
				while((s=br.readLine())!=null) fw.write(s);
				
				fw.flush();
				fw.close();
				br.close();
			
			} catch (FileNotFoundException e) { e.printStackTrace();
			} catch(IOException e){ e.printStackTrace(); }*/
		}
	}
	
	
}
