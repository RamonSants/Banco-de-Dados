/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.bean;

import br.ufms.model.daolib.Bean;

/**
 *
 * @author Usuário
 */
public class Telefone extends Bean<Integer> {

    private TipoTelefone tipo;
    private String numero;
    private Boolean principal;

    /**
     * @return the tipo
     */
    public TipoTelefone getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the principal
     */
    public Boolean getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
