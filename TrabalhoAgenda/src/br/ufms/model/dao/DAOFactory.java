/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

/**
 *
 * @author Usuário
 */
public class DAOFactory {
    
    private final ContatoDAO contatoDAO;
    private final TelefoneDAO telefoneDAO;
    
    private DAOFactory(){
        contatoDAO = new ContatoDAO();
        telefoneDAO = new TelefoneDAO();
    }
    /**
     * Gets the single instance of DAOFactory class.
     *
     * @return the singleton instance
     */
    public static DAOFactory getInstance() {
        return DAOFactoryHolder.INSTANCE;
    }

    /**
     * @return the contatoDAO
     */
    public ContatoDAO getContatoDAO() {
        return contatoDAO;
    }

    /**
     * @return the telefoneDAO
     */
    public TelefoneDAO getTelefoneDAO() {
        return telefoneDAO;
    }

    /**
     * Classe privada que armazena a única instância de DAOFactory.
     */
    private static class DAOFactoryHolder {

        private static final DAOFactory INSTANCE = new DAOFactory();
    }
}
