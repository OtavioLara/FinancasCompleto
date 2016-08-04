/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.repositorio;

import br.dcc.ufla.financasgenericas.exceptions.UsuarioJaExistenteException;
import br.dcc.ufla.financasgenericas.model.Usuario;
import br.dcc.ufla.financasgenericas.model.UsuarioLogado;
import br.dcc.ufla.financasgenericas.beans.UsuarioBeans;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Otavio
 */
public final class RepositorioUsuario {

    private static HashMap<String, Usuario> usuarios;

    public static void adicionarUsuario(Usuario usuario) throws UsuarioJaExistenteException {
        carregarUsuariosBeans();
        if (!usuarios.containsKey(usuario.getUserName())) {
            usuarios.put(usuario.getUserName(), usuario);
            gravarUsuario(usuarioToUsuariobean());
        } else {
            throw new UsuarioJaExistenteException(usuario.getUserName());
        }
    }

    private static void gravarUsuario(ArrayList<UsuarioBeans> usuarios) {
        try {
            FileOutputStream fo = new FileOutputStream("usuario.fgd");
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(usuarios);
            oo.close();
        } catch (Exception e) {

        }
    }

    private static ArrayList<UsuarioBeans> usuarioToUsuariobean() {
        ArrayList<UsuarioBeans> usuariosBeans = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            UsuarioBeans usuarioBeans = new UsuarioBeans();
            usuarioBeans.setUserName(usuario.getUserName());
            usuarioBeans.setEmail(usuario.getEmail());
            usuarioBeans.setNome(usuario.getNome());
            usuarioBeans.setSenha(usuario.getSenha());
            usuariosBeans.add(usuarioBeans);
        }
        return usuariosBeans;
    }

    private static void carregarUsuariosBeans() {
        try {
            FileInputStream fi = new FileInputStream("usuario.fgd");
            ObjectInputStream oi = new ObjectInputStream(fi);
            carregarUsuarios((ArrayList<UsuarioBeans>) oi.readObject());
            oi.close();
        } catch (FileNotFoundException ex) {
            usuarios = new HashMap<>();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void carregarUsuarios(ArrayList<UsuarioBeans> usuariosBeans) {
        usuarios = new HashMap<>();
        for (UsuarioBeans usuariosBean : usuariosBeans) {
            usuarios.put(usuariosBean.getUserName(),
                    new Usuario(usuariosBean.getNome(), usuariosBean.getEmail(),
                            usuariosBean.getUserName(), usuariosBean.getSenha()));
        }
    }

    public static Usuario getUsuario(String username) {
        carregarUsuariosBeans();
        return usuarios.get(username);
    }

    public static boolean logar(String username, String senha) {
        carregarUsuariosBeans();

        if (usuarios.containsKey(username)) {
            if ((usuarios.get(username).logar(username, senha))) {
                UsuarioLogado.getInstance().setUsuario(username);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
