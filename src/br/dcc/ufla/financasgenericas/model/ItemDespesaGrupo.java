/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.model;

import financasgenericas.exceptions.UsuarioNotInItemException;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class ItemDespesaGrupo extends Item{
    private HashMap<String,DividaItem> dividaItem;
    public ItemDespesaGrupo(String nome, double valor) {
        super(nome, valor);
        dividaItem = new HashMap<>();
    }
    public ItemDespesaGrupo(String nome, double valor, HashMap<String,DividaItem> dividaItem) {
        super(nome, valor);
        this.dividaItem = dividaItem;
    }
    public boolean isPessoaInItens(Pessoa p){
        return dividaItem.containsKey(p);
    }
    public void removerPessoa(Pessoa p){
        if(isPessoaInItens(p)){
            this.dividaItem.remove(p);
        }else{
            throw new UsuarioNotInItemException(p.getNome(), getNome());
        }
    }
    public void adicionarPessoa(String username,DividaItem dividaItem){
        this.dividaItem.put(username,dividaItem);
    }
    public void alterarValorItem(String username, double novoValor){
        this.dividaItem.put(username, new DividaItem(novoValor));
    }

    public HashMap<String, DividaItem> getDividaItem() {
        return dividaItem;
    }
}
