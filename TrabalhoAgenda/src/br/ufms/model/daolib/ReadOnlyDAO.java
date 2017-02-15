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
import br.ufms.model.daolib.Bean;
import br.ufms.model.dao.DAOFactory;
import br.ufms.util.DatabaseManager;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Kleber Kruger
 *
 * @param <B>
 * @param <T>
 */
public abstract class ReadOnlyDAO<B extends Bean<T>, T extends Serializable> {

    protected final DatabaseManager db;
    protected final Class<B> beanClass;

    /**
     * Cria um ReadOnlyDAO para Beans do tipo B.
     *
     * @param clazz
     */
    public ReadOnlyDAO(Class<B> clazz) {
        this.db = DatabaseManager.getInstance();
        this.beanClass = clazz;
    }

    /**
     * Retorna o objeto gerenciador do banco de dados.
     *
     * @return the database manager
     */
    protected DatabaseManager getDatabaseManager() {
        return db;
    }
    
    /**
     * Retorna a fábrica de DAOs.
     *
     * @return the DAO factory
     */
    protected final DAOFactory getDAOFactory() {
        return DAOFactory.getInstance();
    }

    /**
     * @return the beanClass
     */
    public Class<B> getBeanClass() {
        return beanClass;
    }

    /**
     * Carrega do banco de dados as informações do objeto Bean.
     *
     * @param codigo
     * @return
     * @throws SQLException
     */
    public B get(T codigo) throws SQLException {
        B bean;
        try (Connection conn = db.getConnection()) {
            bean = get(conn, codigo);
        }
        return bean;
    }

    protected abstract B get(Connection conn, T codigo) throws SQLException;

    /**
     * Carrega do banco de dados a lista de todos os obhetos do tipo Bean.
     *
     * @return
     * @throws SQLException
     */
    public List<B> getAll() throws SQLException {
        List<B> beans;
        try (Connection conn = db.getConnection()) {
            beans = getAll(conn);
        }
        return beans;
    }

    protected abstract List<B> getAll(Connection conn) throws SQLException;

//    protected abstract B resultSetToBean(Connection conn, ResultSet rs) throws SQLException;
}
