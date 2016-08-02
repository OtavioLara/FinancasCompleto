/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.repositorio;

import financasgenericas.Despesa;
import financasgenericas.DespesaGrupo;
import financasgenericas.DespesaIndividual;
import financasgenericas.DividaDespesa;
import financasgenericas.DividaItem;
import financasgenericas.Item;
import financasgenericas.ItemDespesaGrupo;
import financasgenericas.ItemDespesaIndividual;
import financasgenericas.UsuarioLogado;
import financasgenericas.beans.DespesaBeans;
import financasgenericas.beans.DespesaGrupoBeans;
import financasgenericas.beans.DespesaIndividualBeans;
import financasgenericas.beans.DividaDespesaBeans;
import financasgenericas.beans.DividaItemBeans;
import financasgenericas.beans.ItemDespesaGrupoBeans;
import financasgenericas.beans.ItemDespesaIndividualBeans;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Otavio
 */
public class RepositorioDespesa {

    private static HashMap<Long, Despesa> despesas;
    private static long id;

    public static boolean adicionarDespesa(Despesa despesa) {
        carregarDespesasBeans();
        id++;
        despesas.put(id, despesa);
        gravarDespesa(despesasToDespesasBeans());
        return true;
    }

    private static void gravarDespesa(HashMap<Long, DespesaBeans> despesasToDespesasBeans) {
        try {
            FileOutputStream fo = new FileOutputStream("despesas.fgd");
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(despesasToDespesasBeans);
            oo.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter("despesaId.fgd"))) {
            buffWrite.write(Long.toString(id));
        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static HashMap<Long, DespesaBeans> despesasToDespesasBeans() {
        HashMap<Long, DespesaBeans> despesasBeans = new HashMap<>();
        for (Map.Entry<Long, Despesa> entry : despesas.entrySet()) {
            Long key = entry.getKey();
            Despesa value = entry.getValue();
            if (value instanceof DespesaIndividual) {
                DespesaIndividualBeans despesaIdividualBeans = new DespesaIndividualBeans();
                DespesaIndividual despesaIndividual = (DespesaIndividual) value;
                despesaIdividualBeans.setData(despesaIndividual.getData());
                despesaIdividualBeans.setDescricao(despesaIndividual.getDescricao());
                despesaIdividualBeans.setItens(itensDespesaIndividualToItensBeans(despesaIndividual.getItens()));
                despesaIdividualBeans.setNome(despesaIndividual.getNome());
                despesaIdividualBeans.setUsuario(despesaIndividual.getUsuario());
                despesaIdividualBeans.setValor(despesaIndividual.getValor());

                despesasBeans.put(key, despesaIdividualBeans);
            }
            if (value instanceof DespesaGrupo) {
                DespesaGrupoBeans despesaGrupoBeans = new DespesaGrupoBeans();
                DespesaGrupo despesaGrupo = (DespesaGrupo) value;
                despesaGrupoBeans.setData(despesaGrupo.getData());
                despesaGrupoBeans.setDataAlerta(despesaGrupo.getDataAlerta());
                despesaGrupoBeans.setIdGrupo(despesaGrupo.getIdGrupo());
                despesaGrupoBeans.setIntegrantes(dividaDespesaToDividaDespesaBeans(despesaGrupo.getIntegrantes()));
                despesaGrupoBeans.setItens(itensDespesaGrupoToItensBeans(despesaGrupo.getItens()));
                despesaGrupoBeans.setDescricao(despesaGrupo.getDescricao());
                despesaGrupoBeans.setNome(despesaGrupo.getNome());
                despesaGrupoBeans.setValor(despesaGrupo.getValor());

                despesasBeans.put(key, despesaGrupoBeans);
            }
        }
        return despesasBeans;
    }

    private static HashMap<String, DividaDespesaBeans> dividaDespesaToDividaDespesaBeans(HashMap<String, DividaDespesa> integrantes) {
        HashMap<String, DividaDespesaBeans> dividaDespesaBeans = new HashMap<>();

        for (Map.Entry<String, DividaDespesa> entry : integrantes.entrySet()) {
            String key = entry.getKey();
            DividaDespesa value = entry.getValue();
            DividaDespesaBeans dividaBeans = new DividaDespesaBeans();
            dividaBeans.setValor(value.getValor());
            dividaBeans.setValorPago(value.getValorPago());

            dividaDespesaBeans.put(key, dividaBeans);
        }
        return dividaDespesaBeans;
    }

    private static ArrayList<ItemDespesaIndividualBeans> itensDespesaIndividualToItensBeans(ArrayList<ItemDespesaIndividual> itens) {
        ArrayList<ItemDespesaIndividualBeans> itensBeans = new ArrayList<>();
        for (Item item : itens) {
            ItemDespesaIndividualBeans itemBeans = new ItemDespesaIndividualBeans();
            itemBeans.setNome(item.getNome());
            itemBeans.setValor(item.getValor());

            itensBeans.add(itemBeans);
        }
        return itensBeans;
    }

    private static ArrayList<ItemDespesaGrupoBeans> itensDespesaGrupoToItensBeans(ArrayList<ItemDespesaGrupo> itens) {
        ArrayList<ItemDespesaGrupoBeans> itensBeans = new ArrayList<>();
        for (Item item : itens) {
            ItemDespesaGrupoBeans itemBeans = new ItemDespesaGrupoBeans();
            itemBeans.setNome(item.getNome());
            itemBeans.setValor(item.getValor());
            itemBeans.setDividaItem(dividaItemToDividaItemBeans(((ItemDespesaGrupo) item).getDividaItem()));

            itensBeans.add(itemBeans);
        }
        return itensBeans;
    }

    private static HashMap<String, DividaItemBeans> dividaItemToDividaItemBeans(HashMap<String, DividaItem> dividaItem) {
        HashMap<String, DividaItemBeans> dividasItemBeans = new HashMap<>();
        for (Map.Entry<String, DividaItem> entry : dividaItem.entrySet()) {
            String key = entry.getKey();
            DividaItem value = entry.getValue();
            DividaItemBeans dividaItemBeans = new DividaItemBeans();
            dividaItemBeans.setValor(value.getValor());

            dividasItemBeans.put(key, dividaItemBeans);
        }
        return dividasItemBeans;
    }

    private static void carregarDespesasBeans() {
        try {
            FileInputStream fi = new FileInputStream("despesas.fgd");
            ObjectInputStream oi = new ObjectInputStream(fi);
            carregarDespesas((HashMap<Long, DespesaBeans>) oi.readObject());
            oi.close();
        } catch (FileNotFoundException ex) {
            despesas = new HashMap<>();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader("despesaId.fgd"));
            id = Long.parseLong(buffRead.readLine());
            buffRead.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void carregarDespesas(HashMap<Long, DespesaBeans> hashMap) {
        despesas = new HashMap<>();
        for (Map.Entry<Long, DespesaBeans> entry : hashMap.entrySet()) {
            Long key = entry.getKey();
            DespesaBeans value = entry.getValue();
            if (value instanceof DespesaIndividualBeans) {
                despesas.put(key, new DespesaIndividual(value.getNome(), value.getValor(), value.getDescricao(),
                        value.getData(), ((DespesaIndividualBeans) value).getUsuario(),
                        beansToItemDespesaIndividual(((DespesaIndividualBeans) value).getItens())));
            } else if (value instanceof DespesaGrupoBeans) {
                despesas.put(key, new DespesaGrupo(value.getNome(), value.getValor(), value.getDescricao(), value.getData(), ((DespesaGrupoBeans) value).getDataAlerta(),
                        ((DespesaGrupoBeans) value).getIdGrupo(), dividaDespesaBeansToDividaDespesa((((DespesaGrupoBeans) value).getIntegrantes())), beansToItemDespesaGrupo(((DespesaGrupoBeans) value).getItens())));
            }
        }
    }

    private static HashMap<String, DividaDespesa> dividaDespesaBeansToDividaDespesa(HashMap<String, DividaDespesaBeans> participantesBeans) {
        HashMap<String, DividaDespesa> participantes = new HashMap<>();
        for (Map.Entry<String, DividaDespesaBeans> entry : participantesBeans.entrySet()) {
            String key = entry.getKey();
            DividaDespesaBeans value = entry.getValue();
            DividaDespesa participante = new DividaDespesa(value.getValor(), value.getValorPago());

            participantes.put(key, participante);
        }
        return participantes;
    }

    private static ArrayList<ItemDespesaGrupo> beansToItemDespesaGrupo(ArrayList<ItemDespesaGrupoBeans> itensBeans) {
        ArrayList<ItemDespesaGrupo> itens = new ArrayList<>();
        for (ItemDespesaGrupoBeans itemBeans : itensBeans) {
            ItemDespesaGrupo item = new ItemDespesaGrupo(itemBeans.getNome(), itemBeans.getValor(), dividaBeansToDivida(itemBeans.getDividaItem()));

            itens.add(item);
        }
        return itens;
    }

    private static HashMap<String, DividaItem> dividaBeansToDivida(HashMap<String, DividaItemBeans> dividaItem) {
        HashMap<String, DividaItem> dividas = new HashMap<>();
        for (Map.Entry<String, DividaItemBeans> entry : dividaItem.entrySet()) {
            String key = entry.getKey();
            DividaItemBeans value = entry.getValue();
            DividaItem divida = new DividaItem(value.getValor());

            dividas.put(key, divida);
        }
        return dividas;
    }

    private static ArrayList<ItemDespesaIndividual> beansToItemDespesaIndividual(ArrayList<ItemDespesaIndividualBeans> itensBeans) {
        ArrayList<ItemDespesaIndividual> itens = new ArrayList<>();
        for (ItemDespesaIndividualBeans itemBeans : itensBeans) {
            ItemDespesaIndividual item = new ItemDespesaIndividual(itemBeans.getNome(), itemBeans.getValor());
            itens.add(item);
        }
        return itens;
    }

    public static void removerDespesa(Despesa despesa) {
        if (despesas.containsKey(despesa)) {
            despesas.remove(despesa);
        }
    }

    public static HashMap<Long, Despesa> getDespesasDoUsuarioLogado() {
        carregarDespesasBeans();
        HashMap<Long, Despesa> despesasDoUsuario = new HashMap<>();
        for (Map.Entry<Long, Despesa> entry : despesas.entrySet()) {
            Long key = entry.getKey();
            Despesa value = entry.getValue();
            if (value.isParticipante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()))) {
                despesasDoUsuario.put(key, value);
            }
        }
        return despesasDoUsuario;
    }

    public static double getDividaTotalUsuarioLogado() {
        HashMap<Long, Despesa> myDesp = getDespesasDoUsuarioLogado();
        double valorTotal = 0;
        for (Map.Entry<Long, Despesa> entry : myDesp.entrySet()) {
            Long key = entry.getKey();
            Despesa value = entry.getValue();
            if (value instanceof DespesaGrupo) {
                valorTotal += ((DespesaGrupo) value).valorAPagarPeloIntegrante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()));
            }
        }
        return valorTotal;
    }

    public static double getAReceberTotalUsuarioLogado() {
        HashMap<Long, Despesa> myDesp = getDespesasDoUsuarioLogado();
        double valorTotal = 0;
        for (Map.Entry<Long, Despesa> entry : myDesp.entrySet()) {
            Long key = entry.getKey();
            Despesa value = entry.getValue();
            if (value instanceof DespesaGrupo) {
                valorTotal += ((DespesaGrupo) value).valorAReceberPeloIntegrante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()));
            }
        }
        return valorTotal;
    }
}
