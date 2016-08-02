/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas;

/**
 *
 * @author Otavio
 */
public abstract class Item {
    private String nome;
    private double valor;

    public Item (String nome, double valor){
        this.nome = nome;
        this.valor = valor;
    }
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }
    @Override
    public String toString(){
        return String.format("Nome: %s Valor: %.2f", nome, valor);
    }
}
