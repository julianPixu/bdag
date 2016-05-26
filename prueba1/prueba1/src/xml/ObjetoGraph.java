package xml;

public class ObjetoGraph {

	private int tipoGraph;
	private String nombreEjex;
	private String nombreEjey;
	private String[] colores;
	private String anchura;
	private Object[][] datos;
	
	public ObjetoGraph(int t, String nx, String ny, String[] colors, String anchura, Object[][] datos){
		this.tipoGraph = t;
		this.nombreEjex = nx;
		this.nombreEjey = ny;
		this.colores = colors;
		this.anchura = anchura;
		this.datos = datos;
	}

	

	public int getTipoGraph() {
		return tipoGraph;
	}

	public void setTipoGraph(int tipoGraph) {
		this.tipoGraph = tipoGraph;
	}

	public String getNombreEjex() {
		return nombreEjex;
	}

	public void setNombreEjex(String nombreEjex) {
		this.nombreEjex = nombreEjex;
	}

	public String getNombreEjey() {
		return nombreEjey;
	}

	public void setNombreEjey(String nombreEjey) {
		this.nombreEjey = nombreEjey;
	}

	public String[] getColores() {
		return colores;
	}

	public void setColores(String[] colores) {
		this.colores = colores;
	}

	public String getAnchura() {
		return anchura;
	}

	public void setAnchura(String anchura) {
		this.anchura = anchura;
	}
	
	public Object[][] getDatos() {
		return datos;
	}

	public void setDatos(Object[][] datos) {
		this.datos = datos;
	}
}
