/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.exceptions;

/**
 *
 * @author Otavio
 */
public class UsuarioNotInGrupoException extends RuntimeException{
    private String nomeUsuario;
    private String nomeGrupo;
    public UsuarioNotInGrupoException(String nomeUsuario, String nomeGrupo) {
        super("Usuário "+ nomeUsuario +" não faz parte do grupo "+nomeGrupo+".");
        this.nomeGrupo = nomeGrupo;
        this.nomeUsuario = nomeGrupo;
    }

    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @return the nomeGrupo
     */
    public String getNomeGrupo() {
        return nomeGrupo;
    }
    
}
