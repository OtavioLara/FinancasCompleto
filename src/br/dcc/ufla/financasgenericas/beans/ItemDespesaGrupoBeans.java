/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.beans;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class ItemDespesaGrupoBeans extends ItemBeans implements Serializable {
    private HashMap<String,DividaItemBeans> dividaItem;

    /**
     * @return the dividaItem
     */
    public HashMap<String,DividaItemBeans> getDividaItem() {
        return dividaItem;
    }

    /**
     * @param dividaItem the dividaItem to set
     */
    public void setDividaItem(HashMap<String,DividaItemBeans> dividaItem) {
        this.dividaItem = dividaItem;
    }
    
}
