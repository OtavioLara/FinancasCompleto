/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.beans;

import financasgenericas.beans.DespesaBeans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class DespesaGrupoBeans extends DespesaBeans implements Serializable {

    private Date dataAlerta;
    private ArrayList<ItemDespesaGrupoBeans> itens;
    private HashMap<String, DividaDespesaBeans> integrantes;
    private long idGrupo;

    /**
     * @return the dataAlerta
     */
    public Date getDataAlerta() {
        return dataAlerta;
    }

    /**
     * @param dataAlerta the dataAlerta to set
     */
    public void setDataAlerta(Date dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    /**
     * @return the itens
     */
    public ArrayList<ItemDespesaGrupoBeans> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(ArrayList<ItemDespesaGrupoBeans> itens) {
        this.itens = itens;
    }

    /**
     * @return the integrantes
     */
    public HashMap<String, DividaDespesaBeans> getIntegrantes() {
        return integrantes;
    }

    /**
     * @param integrantes the integrantes to set
     */
    public void setIntegrantes(HashMap<String, DividaDespesaBeans> integrantes) {
        this.integrantes = integrantes;
    }

    /**
     * @return the idGrupo
     */
    public long getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }

}
