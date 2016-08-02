/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.controler;

import financasgenericas.model.Despesa;
import financasgenericas.model.DespesaGrupo;
import financasgenericas.model.DespesaIndividual;
import financasgenericas.model.DividaDespesa;
import financasgenericas.model.ItemDespesaGrupo;
import financasgenericas.model.ItemDespesaIndividual;
import financasgenericas.repositorio.RepositorioDespesa;
import static financasgenericas.repositorio.RepositorioDespesa.getAReceberTotalUsuarioLogado;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class ControlerDespesa {

    public static boolean cadastrarDespesaIndividual(String nome, double valor, String descricao, Date data, String userName, ArrayList<ItemDespesaIndividual> itens) {
        return RepositorioDespesa.adicionarDespesa(new DespesaIndividual(nome, valor, descricao, data, userName, itens));
    }

    public static void cadastrarDespesaGrupo(String nome, double valor, String descricao, Date data, Date dataAlerta, long idGrupo, HashMap<String, DividaDespesa> integrantes, ArrayList<ItemDespesaGrupo> itens) {
        RepositorioDespesa.adicionarDespesa(new DespesaGrupo(nome, valor, descricao, data, dataAlerta, idGrupo, integrantes, itens));
    }

    public static HashMap<Long,Despesa> getTotalDespesasDoUsuarioLogado() {
        return RepositorioDespesa.getDespesasDoUsuarioLogado();
    }
    public static double getDividaUsuarioLogado(){
        return RepositorioDespesa.getDividaTotalUsuarioLogado();
    }
    public static double getAReceberUsuarioLogado(){
        return getAReceberTotalUsuarioLogado();
    }
}
