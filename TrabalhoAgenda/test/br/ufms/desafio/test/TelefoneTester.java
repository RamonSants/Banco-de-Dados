/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.desafio.test;

import br.ufms.model.bean.Telefone;
import br.ufms.model.bean.TipoTelefone;
import br.ufms.model.dao.DAOFactory;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class TelefoneTester extends DAOTester<Telefone, Integer> {

    public TelefoneTester() {
        super(DAOFactory.getInstance().getTelefoneDAO());
    }

    @Override
    protected void printBean(Telefone bean) {
        StringBuilder output = new StringBuilder();
        output.append("Código: ").append(bean.getID()).append("\n");
        output.append("Número: ").append(bean.getNumero()).append("\n");
        output.append("Tipo: ").append(bean.getTipo()).append("\n");
        output.append("Principal: ").append(bean.getPrincipal()).append("\n");
        System.out.println(output);
    }

    @Override
    protected Telefone createBean() {
        Telefone tel = new Telefone();

        tel.setNumero("67999999917");
        tel.setTipo(TipoTelefone.CELULAR);
        tel.setPrincipal(true);
        return tel;
    }

    @Override
    protected void updateBean(Telefone bean) {
        bean.setNumero("67999999907");
        bean.setTipo(TipoTelefone.RESIDENCIAL);
        bean.setPrincipal(true);
    }

    @Override
    protected void insertDependencyList(List<Serializable> dependencies) {
        dependencies.add(2);
    }
}