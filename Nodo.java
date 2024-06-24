package atividade;

public class Nodo {
    private int valor, altura, fatorBalanceamento;
    private Nodo esquerdo, direito, pai;

    public Nodo() {
    	setValor(0);
    	setAltura(0);
    	setFatorBalanceamento(0);
    	setEsquerdo(null);
    	setDireito(null);
    	setPai(null);
    }
    
    public Nodo(int valor) {
        setValor(valor);
        setAltura(0);
        setFatorBalanceamento(0);
        setEsquerdo(null);
        setDireito(null);
        setPai(null);
    }

    // Getters e Setters
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }

    public Nodo getEsquerdo() {
        return esquerdo;
    }

    public void setEsquerdo(Nodo esquerdo) {
        this.esquerdo = esquerdo;
    }

    public Nodo getDireito() {
        return direito;
    }

    public void setDireito(Nodo direito) {
        this.direito = direito;
    }

    public Nodo getPai() {
        return pai;
    }

    public void setPai(Nodo pai) {
        this.pai = pai;
    }
}