/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.controler;

import br.dcc.ufla.financasgenericas.model.Despesa;
import br.dcc.ufla.financasgenericas.model.DespesaGrupo;
import br.dcc.ufla.financasgenericas.model.DespesaIndividual;
import br.dcc.ufla.financasgenericas.model.DividaDespesa;
import br.dcc.ufla.financasgenericas.model.ItemDespesaGrupo;
import br.dcc.ufla.financasgenericas.model.ItemDespesaIndividual;
import br.dcc.ufla.financasgenericas.repositorio.RepositorioDespesa;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class ControlerDespesa {

    public static void cadastrarDespesaGrupo(String nome, double valor, String descricao, Date data, Date dataAlerta, long idGrupo, HashMap<String, DividaDespesa> integrantes, ArrayList<ItemDespesaGrupo> itens) {
        RepositorioDespesa.adicionarDespesa(new DespesaGrupo(nome, valor, descricao, data, dataAlerta, idGrupo, integrantes, itens));
    }
    public static boolean cadastrarDespesaIndividual(String nome, double valor, String descricao, Date data, String userName, ArrayList<ItemDespesaIndividual> itens) {
        
        return RepositorioDespesa.adicionarDespesa(new DespesaIndividual(nome, valor, descricao, data, userName, itens));
    }
    public static HashMap<Long,Despesa> getTotalDespesasDoUsuarioLogado() {
        return RepositorioDespesa.getDespesasDoUsuarioLogado();
    }

    
    public static double getDividaUsuarioLogado(){
        return RepositorioDespesa.getDividaTotalUsuarioLogado();
    }
    public static double getAReceberUsuarioLogado(){
        return RepositorioDespesa.getAReceberTotalUsuarioLogado();
    }
    public static HashMap<Long,DespesaGrupo> getDepesaEntreUsuario(String username){
        return RepositorioDespesa.getDespesasEntreUsuarios(username);
    }
    public static boolean atualizarDespesa(long idDesp, DespesaGrupo despesa){
        return RepositorioDespesa.atualizarDespesa(idDesp, despesa);
    }
}
