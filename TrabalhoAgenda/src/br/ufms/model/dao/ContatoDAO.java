/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.daolib.ReadWriteDAO;
import br.ufms.model.bean.Contato;
import br.ufms.model.bean.Telefone;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class ContatoDAO extends ReadWriteDAO<Contato, Integer> {

    protected ContatoDAO() {
        super(Contato.class);
    }

    @Override
    protected void insert(Connection conn, Contato bean, Serializable... dependencies) throws SQLException {
        final String sql = "INSERT INTO agenda.contatos (nome, data_nascimento, data_criacao) VALUES(?,?,?)";
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bean.getNome());
            ps.setObject(2, bean.getNascimento() != null ? Date.valueOf(bean.getNascimento()) : null);
            ps.setObject(3, bean.getCriacao() != null ? Date.valueOf(bean.getCriacao()) : LocalDate.now());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.first()) {
                    bean.setID(rs.getInt(1));

                    TelefoneDAO dao = DAOFactory.getInstance().getTelefoneDAO();
                    for (Telefone tel : bean.getTelefones()) {
                        dao.insert(conn, tel, bean.getID());
                    }
                    conn.commit();
                }
            }
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }
    }

    @Override
    protected void update(Connection conn, Contato bean) throws SQLException {
        final String sql = "UPDATE agenda.contatos SET nome = ?, data_nascimento = ?, data_criacao = ? WHERE id = ?";
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bean.getNome());
            ps.setObject(2, bean.getNascimento() != null ? Date.valueOf(bean.getNascimento()) : null);
            ps.setObject(3, bean.getCriacao() != null ? Date.valueOf(bean.getCriacao()) : null);
            ps.setInt(4, bean.getID());
            ps.executeUpdate();

            TelefoneDAO dao = DAOFactory.getInstance().getTelefoneDAO();
            for (Telefone tel : bean.getTelefones()) {
                dao.save(tel, bean.getID());
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }
    }

    @Override
    protected void delete(Connection conn, Integer id) throws SQLException {
        final String sql = "DELETE FROM agenda.contatos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    protected Contato get(Connection conn, Integer id) throws SQLException {
        final String sql = "SELECT * FROM agenda.contatos WHERE id = ?";
        Contato c = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.first()) {
                    c = resultSetToBean(conn, rs);
                }
            }
        }
        return c;
    }

    @Override
    protected List<Contato> getAll(Connection conn) throws SQLException {
        final String sql = "SELECT * FROM agenda.contatos";
        List<Contato> contatos = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    contatos.add(resultSetToBean(conn, rs));
                }
            }
        }
        return contatos;
    }
    

    private Contato resultSetToBean(Connection conn, ResultSet rs) throws SQLException {
        TelefoneDAO dao = DAOFactory.getInstance().getTelefoneDAO();
        Contato contato = new Contato(rs.getDate("data_criacao").toLocalDate());
        setGeneratedKey(contato, rs.getInt("id"));
        Collections.copy(contato.getTelefones(), dao.findByContatoID(conn, contato.getID()));
        contato.setNome(rs.getString("nome"));
        contato.setNascimento(rs.getDate("data_nascimento") != null ? rs.getDate("data_nascimento").toLocalDate() : null);
        return contato;
    }

}
