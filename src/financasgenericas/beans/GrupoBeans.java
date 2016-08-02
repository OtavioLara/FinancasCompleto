/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.beans;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class GrupoBeans implements Serializable {

    private HashMap<String, Boolean> participantes;
    private String nome;
    private static long totalGrupos;
    private long id;

    /**
     * @return the totalGrupos
     */
    public static long getTotalGrupos() {
        return totalGrupos;
    }

    /**
     * @param aTotalGrupos the totalGrupos to set
     */
    public static void setTotalGrupos(long aTotalGrupos) {
        totalGrupos = aTotalGrupos;
    }

    /**
     * @return the participantes
     */
    public HashMap<String, Boolean> getParticipantes() {
        return participantes;
    }

    /**
     * @param participantes the participantes to set
     */
    public void setParticipantes(HashMap<String, Boolean> participantes) {
        this.participantes = participantes;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
