/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuário
 */
public class DatabaseManager {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    /**
     * Cria um objeto Database.
     *
     * Este construtor é privado, pois esta classe segue o padrão de projeto
     * "Singleton".
     *
     * Na criação deste objeto, o driver do banco de dados é prontamente
     * carregado. Caso ocorra uma exceção neste momento, verifique se a
     * biblioteca do MySQL foi adicionada a este projeto.
     */
    private DatabaseManager() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    /**
     * Retorna a única instância desta classe.
     *
     * @return the instance
     */
    public static DatabaseManager getInstance() {
        return DatabaseManagerHolder.INSTANCE;
    }

    /**
     * Estabelece uma conexão com o banco de dados.
     *
     * @return a conexão com o banco de dados.
     *
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Classe privada que armazena a única instância de DatabaseManager.
     */
    private static class DatabaseManagerHolder {

        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }
}
