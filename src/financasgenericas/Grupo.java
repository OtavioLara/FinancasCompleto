/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas;

import financasgenericas.exceptions.UsuarioNotInGrupoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Otavio
 */
public class Grupo {

    /**
     * HashMap onde a chave (key) é um usuário e o valor (value) é um boolean
     * representando se o usuário é administrador ou não.
     */
    private HashMap<Usuario, Boolean> participantes;
    private String nome;
    private long id;

    public Grupo(String nome, Usuario usuarioAdministrador) {
        this.nome = nome;
        participantes = new HashMap<>();
        addParticipante(usuarioAdministrador, true);
    }

    public Grupo(String nome, Usuario usuarioAdministrador, ArrayList<Usuario> usuarios) {
        this(nome, usuarioAdministrador);
        addParticipantes(usuarios);
    }

    public Grupo(String nome, HashMap<Usuario, Boolean> participantes) {
        this.nome = nome;
        this.participantes = participantes;
    }

    public void addParticipantes(ArrayList<Usuario> participantes) {
        for (Usuario participante : participantes) {
            addParticipante(participante, false);
        }
    }

    public void addParticipante(Usuario usuario) {
        if (usuario != null) {
            getParticipantes().put(usuario, false);
        }
    }

    public void addParticipante(Usuario usuario, boolean isAdm) {
        if (usuario != null) {
            getParticipantes().put(usuario, isAdm);
        }
    }

    public void setAdministrador(Usuario u, boolean newAdmState) throws UsuarioNotInGrupoException {
        if (getParticipantes().containsKey(u)) {
            getParticipantes().put(u, newAdmState);
        } else {
            throw new UsuarioNotInGrupoException(u.getNome(), nome);
        }
    }

    public boolean isAdministrador(String username) {
        if (isParticipante(username)) {
            for (Map.Entry<Usuario, Boolean> entry : participantes.entrySet()) {
                Usuario key = entry.getKey();
                Boolean value = entry.getValue();
                if (key.getUserName().equals(username) && value == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isParticipante(String username) {
        Set<Usuario> usuarios = participantes.keySet();
        for (Usuario usuario : usuarios) {
            if (usuario.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void removeParticipante(String removedor, String aRemover) {
        if (isAdministrador(removedor)) {
            if (isParticipante(aRemover)) {
                for (Map.Entry<Usuario, Boolean> entry : participantes.entrySet()) {
                    Usuario key = entry.getKey();
                    Boolean value = entry.getValue();
                    if (key.getUserName().equals(aRemover)) {
                        participantes.remove(key);
                    }
                }
            }
        }
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Grupo)) {
            return false;
        }
        
        return (obj == this) || (getId() == (((Grupo)obj).getId()));
    }

    /**
     * @return the participantes
     */
    public HashMap<Usuario, Boolean> getParticipantes() {
        return participantes;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
