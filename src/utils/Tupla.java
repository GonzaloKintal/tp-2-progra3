package utils;

import java.util.Objects;

public class Tupla<A, B> {
    private A primero;
    private B segundo;

    public Tupla(A primero, B segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public A getPrimero() {
        return primero;
    }

    public void setPrimero(A primero) {
        this.primero = primero;
    }

    public B getSegundo() {
        return segundo;
    }

    public void setSegundo(B segundo) {
        this.segundo = segundo;
    }

    @Override
    public String toString() {
        return "(" + primero + ", " + segundo + ")";
    }

	@Override
	public int hashCode() {
		return Objects.hash(primero, segundo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tupla other = (Tupla) obj;
		return Objects.equals(primero, other.primero) && Objects.equals(segundo, other.segundo);
	}
    
    
}