/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.controler;

import br.dcc.ufla.financasgenericas.model.Grupo;
import br.dcc.ufla.financasgenericas.model.Usuario;
import br.dcc.ufla.financasgenericas.repositorio.RepositorioGrupo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class ControlerGrupo {

    public static void adicionarGrupo(String nome, Usuario usuarioAdministrador) {
        RepositorioGrupo.adicionarGrupo(new Grupo(nome, usuarioAdministrador));
    }
    public static void adicionarGrupo(String nome, Usuario usuarioAdministrador,ArrayList<Usuario> integrantes) {
        
        RepositorioGrupo.adicionarGrupo(new Grupo(nome, usuarioAdministrador,integrantes));
    }
    public static HashMap<Long,Grupo> gruposDoUsuarioLogado(){
        return RepositorioGrupo.gruposDoUsuarioLogado();
    }
    public static Grupo getGrupoById(long id){
        return RepositorioGrupo.getGrupoById(id);
    }
}
