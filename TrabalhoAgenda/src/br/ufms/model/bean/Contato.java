/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.bean;

import br.ufms.model.daolib.Bean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class Contato extends Bean<Integer> {

    private String nome;
    private LocalDate nascimento;
    private LocalDate criacao;
    private final List<Telefone> telefones;

    public Contato() {
        this(LocalDate.now());   
    }
    
    public Contato(LocalDate criacao) {
        this.criacao = criacao;
        this.telefones = new ArrayList<>();   
    }
  
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the nascimento
     */
    public LocalDate getNascimento() {
        return nascimento;
    }

    /**
     * @param nascimento the nascimento to set
     */
    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    /**
     * @return the criacao
     */
    public LocalDate getCriacao() {
        return criacao;
    }

    /**
     * @return the telefones
     */
    public List<Telefone> getTelefones() {
        return telefones;
    }
}
