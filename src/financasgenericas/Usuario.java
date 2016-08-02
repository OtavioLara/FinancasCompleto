/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas;

import java.util.Objects;

/**
 * Usuário é uma especialização de uma pessoa para que ela possa logar no sistema.
 * @author Otavio
 */
public class Usuario extends Pessoa implements Comparable<Usuario> {

    private String userName;
    private String senha;

    /**
     * Cria um usuário com todos os dados obrigatórios
     * @param nome Nome do usuário
     * @param email Email a ser usado pelo usuário
     * @param userName Username do usuario
     * @param senha Senha do usuario
     */
    public Usuario(String nome, String email, String userName, String senha) {
        super(nome, email);
        this.userName = userName;
        this.senha = senha;
    }
    
    /**
     * Verifica se as requisição do usuário para logar no sistema é válida
     * @param userName username a ser comparado
     * @param senha senha a ser comparada
     * @return true: se o usuário pode logar no sistema. false: se o usuário não pode logar
     */
    public boolean logar(String userName, String senha) {
        return (this.userName.equals(userName)) && (this.senha.equals(senha));
    }

    /**
     * Altera a senha do usuário
     * @param senhaAntiga senha antiga para verificar a autenticidade do usuárop
     * @param novaSenha senha nova para a alteração
     * @return caso a senha antiga e a atual baterem, a senha nova é setada e retorna true, caso contrário false
     */
    public boolean alterarSenha(String senhaAntiga, String novaSenha) {
        if (senhaAntiga.equals(this.senha)) {
            this.senha = novaSenha;
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param novoUserName
     * @param senha
     * @return 
     */
    public boolean alterarUserName(String novoUserName, String senha) {
        if (senha.equals(this.senha)) {
            this.userName = novoUserName;
            return true;
        } else {
            return false;
        }

    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Usuario o) {
        return userName.compareTo(o.userName);
    }

    public String getSenha() {
        return senha;
    }

}
