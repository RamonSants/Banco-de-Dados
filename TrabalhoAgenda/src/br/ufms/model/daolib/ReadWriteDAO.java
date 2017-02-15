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
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Kleber Kruger
 *
 * @param <B>
 * @param <T>
 */
public abstract class ReadWriteDAO<B extends Bean<T>, T extends Serializable> extends ReadOnlyDAO<B, T> {

    public ReadWriteDAO(Class<B> clazz) {
        super(clazz);
    }
    
    protected void setGeneratedKey(B bean, T id) {
        bean.setID(id);
    }

    /**
     * Insere o objeto Bean no banco de dados.
     *
     * @param bean
     * @param dependencies
     * @throws SQLException
     */
    public void insert(B bean, Serializable... dependencies) throws SQLException {
        try (Connection conn = db.getConnection()) {
            insert(conn, bean, dependencies);
        }
    }

    /**
     * Insere o objeto Bean no banco de dados.
     *
     * @param conn
     * @param bean
     * @param dependencies
     * @throws SQLException
     */
    protected abstract void insert(Connection conn, B bean, Serializable... dependencies) throws SQLException;

    /**
     * Atualiza o objeto Bean no banco de dados.
     *
     * @param bean
     * @throws SQLException
     */
    public void update(B bean) throws SQLException {
        try (Connection conn = db.getConnection()) {
            update(conn, bean);
        }
    }

    /**
     * Atualiza o objeto Bean no banco de dados.
     *
     * @param conn
     * @param bean
     * @throws SQLException
     */
    protected abstract void update(Connection conn, B bean) throws SQLException;

    /**
     * Salva (insere/atualiza) o objeto Bean no banco de dados.
     *
     * Para decidir se o objeto deve ser inserido ou atualizado, verifica-se o retorno do método
     * getCodigo(). Caso nulo, assume-se que o bean não existe no banco de dados, e portanto, deve
     * ser inserido. Caso contrário (já possui um código), apenas o atualiza.
     *
     * @param bean
     * @param dependencies
     *
     * @throws SQLException
     */
    public void save(B bean, Serializable... dependencies) throws SQLException {
        try (Connection conn = db.getConnection()) {
            save(conn, bean, dependencies);
        }
    }

    /**
     * Salva (insere/atualiza) o objeto Bean no banco de dados.
     *
     * Para decidir se o objeto deve ser inserido ou atualizado, verifica-se o retorno do método
     * getCodigo(). Caso nulo, assume-se que o bean não existe no banco de dados, e portanto, deve
     * ser inserido. Caso contrário (já possui um código), apenas o atualiza.
     *
     * @param conn
     * @param bean
     * @param dependencies
     *
     * @throws SQLException
     */
    public void save(Connection conn, B bean, Serializable... dependencies) throws SQLException {
        if (bean.getID() == null) {
            insert(conn, bean, dependencies);
        } else {
            update(conn, bean);
        }
    }

    /**
     * Deleta o objeto Bean do banco de dados.
     *
     * @param bean
     * @throws SQLException
     */
    public void delete(B bean) throws SQLException {
        delete(bean.getID());
    }

    /**
     * Deleta o objeto Bean do banco de dados.
     *
     * @param codigo
     * @throws SQLException
     */
    public void delete(T codigo) throws SQLException {
        try (Connection conn = db.getConnection()) {
            delete(conn, codigo);
        }
    }

    protected void delete(Connection conn, B bean) throws SQLException {
        delete(conn, bean.getID());
    }

    /**
     * Deleta o objeto Bean do banco de dados.
     *
     * @param conn
     * @param id
     * @throws SQLException
     */
    protected abstract void delete(Connection conn, T id) throws SQLException;

}
