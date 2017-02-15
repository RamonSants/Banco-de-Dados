/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.daolib;

/**
 *
 * @author Usuário
 */

import java.io.Serializable;

/**
 * Classe base para qualquer bean do banco de dados.
 *
 * @author Kleber Kruger
 * @param <T> tipo da chave primitiva
 */
public class Bean<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T id;

    /**
     * Cria um novo objeto Bean. O construtor desta classe é protegido porque um objeto Bean só
     * existirá por meio de classes derivadas. Estes "beans" têm a função de representar as
     * entidades do banco de dados.
     */
    protected Bean() {
        id = null;
    }

    /**
     * @return the id
     */
    public T getID() {
        return id;
    }

    /**
     * @param id the codigo to set
     */
    public void setID(T id) {
        this.id = id;
    }

}
