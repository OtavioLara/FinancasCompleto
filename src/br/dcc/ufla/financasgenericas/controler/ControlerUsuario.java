/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.controler;

import br.dcc.ufla.financasgenericas.repositorio.RepositorioUsuario;
import br.dcc.ufla.financasgenericas.model.Usuario;
import br.dcc.ufla.financasgenericas.exceptions.UsuarioJaExistenteException;

/**
 * @author Otavio
 */
public class ControlerUsuario {
    public static boolean logar(String username, String senha){
        return RepositorioUsuario.logar(username,senha);
    }
    public static void cadastrarUsuario(String nome, String email, String userName, String senha) throws UsuarioJaExistenteException {
        RepositorioUsuario.adicionarUsuario(new Usuario(nome, email, userName, senha));
    }
    public static Usuario getUsuario(String username){
        return RepositorioUsuario.getUsuario(username);
    }
}
