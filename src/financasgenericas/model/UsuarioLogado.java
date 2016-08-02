/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.model;

/**
 *
 * @author Otavio
 */
public class UsuarioLogado {

    private String username;

    private UsuarioLogado() {
    }

    public static UsuarioLogado getInstance() {
        return UsuarioLogadoHolder.INSTANCE;
    }

    public String getUsuario() {
        return username;
    }

    public void setUsuario(String username) {
        this.username = username;
    }

    private static class UsuarioLogadoHolder {

        private static final UsuarioLogado INSTANCE = new UsuarioLogado();
    }

}
