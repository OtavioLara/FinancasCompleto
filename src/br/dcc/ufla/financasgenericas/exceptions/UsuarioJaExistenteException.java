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
public class UsuarioJaExistenteException extends Exception{
    private String nome;
    public UsuarioJaExistenteException(String nome) {
        super("Username "+nome+" já foi cadastrado no sistema.");
        this.nome = nome;
    }
    
}
