/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Otavio
 */
public class DespesaIndividualBeans extends DespesaBeans implements Serializable {

    private String usuario;
    private ArrayList<ItemDespesaIndividualBeans> itens;

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the itens
     */
    public ArrayList<ItemDespesaIndividualBeans> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(ArrayList<ItemDespesaIndividualBeans> itens) {
        this.itens = itens;
    }

}
