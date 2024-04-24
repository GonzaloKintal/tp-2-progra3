package logica;

public class Arista{
	
    private boolean existeArista;
    private int peso;

    public Arista() {
    	this.existeArista = false;
    	this.peso = 0;
    }

	public boolean existeArista() {
		return existeArista;
	}

	public void agregarArista() {
		this.existeArista = true;
	}
	
	public void eliminarArista() {
		this.existeArista = false;
		this.peso = 0;
	}

	public int obtenerPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
}
