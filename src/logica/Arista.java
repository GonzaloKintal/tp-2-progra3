package logica;

public class Arista{
    boolean existeArista;
    int peso;

    public Arista() {
    	this.existeArista = false;
    	this.peso = 0;
    }
    
    public Arista(boolean existeArista, int peso) {
        this.existeArista = existeArista;
        this.peso = peso;
    }

	public boolean isExisteArista() {
		return existeArista;
	}

	public void agregarArista() {
		this.existeArista = true;
	}
	
	public void eliminarArista() {
		this.existeArista = false;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

    
}
