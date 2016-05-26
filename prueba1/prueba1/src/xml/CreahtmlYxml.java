package xml;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JRadioButton;

public class CreahtmlYxml {

	
	
	public static String creaXML(String fich, ObjetoGraph obGraph){
		
		File f= new File(fich+".xml");
		try {
			
			FileWriter fw= new FileWriter(f);
		
			String text= "<chart>\n\n	<chart_type>";
				if(obGraph.getTipoGraph()==0) text+= "column";
				else if(obGraph.getTipoGraph()==1) text+="3D pie";
				else text+="line";
			text+= "</chart_type>\n\n	<series transfer='true' />\n\n  <series_color>\n";
			
			for(int i=1; i<obGraph.getColores().length; i++) text+= "		<color>"+obGraph.getColores()[i]+"</color>\n";
			
			text+= "  </series_color>\n\n	<chart_data>\n\n";
			
			for(int i=0; i<obGraph.getDatos()[0].length;i++){
				text+= "	<row>\n";
				
				for(int j=-1; j<obGraph.getDatos().length; j++){
					if(j==-1) text+= "		<string></string>\n";
					else{
						if(i==0)text+= "		<string>"+obGraph.getDatos()[j][i].toString()+"</string>\n";
						else text+= "		<number>"+obGraph.getDatos()[j][i].toString()+"</number>\n";
					}
				}
					
				text+= "	</row>\n\n";
			}
			
			text+= "	</chart_data>\n\n";
		
			text+= "	<chart_rect x='40' y='50' width='300' height='180' />\n\n";
			
			text+=  "	<draw>\n		<rect layer='background' x='0' y='0' "
					+ "width='1000' height='650' fill_color='"+obGraph.getColores()[0]+"' />\n	</draw>\n\n";
			
			text+= "</chart>";
			
			//System.out.println(text);
			
			fw.write(text);
			fw.flush();
			fw.close();
			
		} catch (IOException e) { e.printStackTrace(); }
		return f.getName();
	}
	
	public static void creaHtml(String fich, String xml, String color){
		
		try{
			File f= new File(fich+".html");
			FileWriter fw= new FileWriter(f);
			
			String text= "<HTML>\n\n"
					+ "	<script language=\"javascript\">AC_FL_RunContent = 0;</script>\n"
					+ "	<script language=\"javascript\"> DetectFlashVer = 0; </script>\n"
					+ "	<script src=\"AC_RunActiveContent.js\" language=\"javascript\"></script>\n"
					+ "	<script language=\"JavaScript\" type=\"text/javascript\">\n";
					
			text+= "<!--\n"
					+"var requiredMajorVersion = 10;\n"
					+"var requiredMinorVersion = 0;\n"
					+"var requiredRevision = 45;\n"
					+"-->\n\n"
					+"	</script>\n\n";
							
			text+="<BODY bgcolor=\"#FFFFFF\">\n\n"
					+"<script language=\"JavaScript\" type=\"text/javascript\">\n\n";

			text+= "<!--\n"
					+"if (AC_FL_RunContent == 0 || DetectFlashVer == 0) {\n"
					+"	alert(\"This page requires AC_RunActiveContent.js.\");\n"
					+"} else {\n"
					+"	var hasRightVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);\n"
					+"	if(hasRightVersion) {\n" 
					+"		AC_FL_RunContent(\n"
					+"			'codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,45,2',\n";
			
			Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
					int width=	(int) (d.getWidth()-300);
					int height= (int) (d.getHeight()-300);
			text+= "			'width', '"+width+"',\n"
					+"			'height', '"+height+"',\n"
					+"			'salign', 'TL',\n"
					+ "			'bgcolor', '"+color+"',\n"
					+ "			'wmode', 'opaque',\n"
					+"			'movie', 'charts',\n"
					+"			'src', 'charts',\n"	
					+"			'FlashVars', 'library_path=charts_library&xml_source="+xml+"',\n" 
					+"			'id', 'my_chart',\n"
					+"			'name', 'my_chart',\n"
					+"			'menu', 'true',\n"
					+"			'allowFullScreen', 'true',\n"
					+"			'allowScriptAccess','sameDomain',\n"
					+"			'quality', 'high',\n"
					+"			'align', 'middle',\n"
					+"			'pluginspage', 'http://www.macromedia.com/go/getflashplayer',\n"
					+"			'play', 'true',\n"
					+"			'devicefont', 'false'\n"
					+"		);\n"
					+"	} else { \n"
					+"		var alternateContent = 'This content requires the Adobe Flash Player. '\n"
					+"		+ '<u><a href=http://www.macromedia.com/go/getflash/>Get Flash</a></u>.';\n"
					+"		document.write(alternateContent); \n\n"
					+"	}\n"
					+"}\n"
					+"// -->\n\n";
			
			text+="	</script>\n"
					+ "	<noscript>\n"
					+ "		<P>This content requires JavaScript.</P>\n"
					+ "	</noscript>\n\n"
					+ "</BODY>\n"
					+ "</HTML>\n";
			
			//System.out.println(text);
			
			fw.write(text);
			fw.flush();
			fw.close();
		
		}catch(IOException e){e.printStackTrace();} 
		
		
	}
	
	
}
