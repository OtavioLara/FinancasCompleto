/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.repositorio;

import br.dcc.ufla.financasgenericas.model.Grupo;
import br.dcc.ufla.financasgenericas.model.Usuario;
import br.dcc.ufla.financasgenericas.model.UsuarioLogado;
import br.dcc.ufla.financasgenericas.beans.GrupoBeans;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Otavio
 */
public class RepositorioGrupo {

    private static HashMap<Long, Grupo> grupos;
    private static long id;

    public static void adicionarGrupo(Grupo grupo) {
        carregarGrupoBeans();
        id++;
        grupos.put(id, grupo);
        gravarGrupos(gruposToGruposBeans());
    }

    private static void gravarGrupos(HashMap<Long, GrupoBeans> gruposBeans) {
        try {
            FileOutputStream fo = new FileOutputStream("grupos.fgd");
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            oo.writeObject(gruposBeans);
            oo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter("grupoId.fgdi"))) {
            buffWrite.write(Long.toString(id));
        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static HashMap<Long, GrupoBeans> gruposToGruposBeans() {
        HashMap<Long, GrupoBeans> gruposBeans = new HashMap<>();
        for (Map.Entry<Long, Grupo> entry : grupos.entrySet()) {
            Long key = entry.getKey();
            Grupo grupo = entry.getValue();
            GrupoBeans grupoBeans = new GrupoBeans();
            grupoBeans.setId(key);
            grupoBeans.setNome(grupo.getNome());
            grupoBeans.setParticipantes(participanteToParticipantesBeans(grupo.getParticipantes()));
            gruposBeans.put(key, grupoBeans);
        }
        return gruposBeans;
    }

    private static HashMap<String, Boolean> participanteToParticipantesBeans(HashMap<Usuario, Boolean> participantes) {
        HashMap<String, Boolean> participantesBeans = new HashMap<>();
        for (Map.Entry<Usuario, Boolean> entry : participantes.entrySet()) {
            Usuario key = entry.getKey();
            Boolean value = entry.getValue();
            participantesBeans.put(key.getUserName(), value);
        }
        return participantesBeans;
    }

    private static void carregarGrupoBeans() {
        try {
            FileInputStream fi = new FileInputStream("grupos.fgd");
            ObjectInputStream oi = new ObjectInputStream(fi);
            carregarGrupos((HashMap<Long, GrupoBeans>) oi.readObject());
            oi.close();
        } catch (FileNotFoundException ex) {
            grupos = new HashMap<>();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader("grupoId.fgdi"));
            id = Long.parseLong(buffRead.readLine());
            buffRead.close();
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(RepositorioGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void carregarGrupos(HashMap<Long, GrupoBeans> gruposBeans) {
        grupos = new HashMap<>();
        long i = 0;
        for (Map.Entry<Long, GrupoBeans> entry : gruposBeans.entrySet()) {
            i++;
            Long key = entry.getKey();
            GrupoBeans grupoBeans = entry.getValue();
            Grupo g = new Grupo(grupoBeans.getNome(), participantesBeansToParticipantes(grupoBeans.getParticipantes()));
            g.setId(i);
            grupos.put(key, g);
        }
    }

    private static HashMap<Usuario, Boolean> participantesBeansToParticipantes(HashMap<String, Boolean> participantesBeans) {
        HashMap<Usuario, Boolean> participantes = new HashMap<>();
        for (Map.Entry<String, Boolean> entry : participantesBeans.entrySet()) {
            String username = entry.getKey();
            Boolean value = entry.getValue();
            Usuario usuario = RepositorioUsuario.getUsuario(username);
            participantes.put(usuario, value);
        }
        return participantes;
    }

    public static void removerGrupo(Long idGrupo) {
        carregarGrupoBeans();
        if (grupos.containsKey(idGrupo)) {
            grupos.remove(idGrupo);
        }
    }

    public static Grupo getGrupoById(long idGrupo) {
        carregarGrupoBeans();
        return grupos.get(idGrupo);
    }

    public static void removerUsuarioDoGrupo(Usuario usuario, long idGrupoAserRemovido) {
        carregarGrupoBeans();
        grupos.get(idGrupoAserRemovido).removeParticipante(UsuarioLogado.getInstance().getUsuario(), usuario.getUserName());
        gravarGrupos(gruposToGruposBeans());
    }

    public static HashMap<Long, Grupo> gruposDoUsuarioLogado() {
        carregarGrupoBeans();
        HashMap<Long, Grupo> gruposDoUsuario = new HashMap<>();
        for (Map.Entry<Long, Grupo> entry : grupos.entrySet()) {
            long key = entry.getKey();
            Grupo grupo = entry.getValue();
            if (grupo.isParticipante(UsuarioLogado.getInstance().getUsuario())) {
                gruposDoUsuario.put(key, grupo);
            }
        }
        return gruposDoUsuario;
    }
}
