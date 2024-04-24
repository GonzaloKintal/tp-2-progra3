package utils;

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
}