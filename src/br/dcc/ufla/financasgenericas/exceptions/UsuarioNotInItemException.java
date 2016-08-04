/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.exceptions;

/**
 *
 * @author Otavio
 */
public class UsuarioNotInItemException extends RuntimeException{
    private String nomeItem;
    private String pessoa;

    public UsuarioNotInItemException(String nomeItem, String pessoa) {
        super("A pessoa ("+pessoa+") não esta no item ("+nomeItem+").");
        this.nomeItem = nomeItem;
        this.pessoa = pessoa;
    }
    
}
