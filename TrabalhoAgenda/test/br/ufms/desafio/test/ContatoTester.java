/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.desafio.test;

import br.ufms.model.bean.Contato;
import br.ufms.model.bean.Telefone;
import br.ufms.model.bean.TipoTelefone;
import br.ufms.model.dao.DAOFactory;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class ContatoTester extends DAOTester<Contato, Integer> {

    public ContatoTester() {
        super(DAOFactory.getInstance().getContatoDAO());
    }

    @Override
    protected void printBean(Contato bean) {
        StringBuilder output = new StringBuilder();
        output.append("Código: ").append(bean.getID()).append("\n");
        output.append("Nome: ").append(bean.getNome()).append("\n");
        output.append("Nacimento: ").append(bean.getNascimento()).append("\n");
        output.append("Criação: ").append(bean.getCriacao()).append("\n");
        output.append("Telefone: ").append(bean.getTelefones()).append("\n");
        System.out.println(output);
        System.out.println("Lista de Telefones:");
        bean.getTelefones().stream().forEach((t) -> {
            System.out.println(t.getNumero());
        });
    }

    @Override
    protected Contato createBean() {
        Contato contato = new Contato();
        contato.setNome("KLEBER");
        contato.setNascimento(LocalDate.of(1985, Month.MARCH, 24));

        List<Telefone> lista = new ArrayList<>();
        Telefone t1 = new Telefone();
        t1.setNumero("98984949");
        t1.setPrincipal(true);
        t1.setTipo(TipoTelefone.CELULAR);
        lista.add(t1);

        return contato;
    }

    @Override
    protected void updateBean(Contato bean) {
        bean.setNome("Irineu");
    }

    @Override
    protected void insertDependencyList(List<Serializable> dependencies) {
        dependencies.add(1);
    }

}
