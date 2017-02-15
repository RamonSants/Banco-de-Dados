/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.daolib.ReadWriteDAO;
import br.ufms.model.bean.Telefone;
import br.ufms.model.bean.TipoTelefone;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class TelefoneDAO extends ReadWriteDAO<Telefone, Integer> {

    protected TelefoneDAO() {
        super(Telefone.class);
    }

    @Override
    protected void insert(Connection conn, Telefone bean, Serializable... dependencies) throws SQLException {
        final String sql = "INSERT INTO agenda.telefones (tipo, numero, principal, contatos_id) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bean.getTipo().toString());
            ps.setString(2, bean.getNumero());
            ps.setBoolean(3, bean.getPrincipal());
            ps.setInt(4, (int) dependencies[0]);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.first()) {
                    bean.setID(rs.getInt(1));
                }
            }
        }
    }

    @Override
    protected void update(Connection conn, Telefone bean) throws SQLException {
        final String sql = "UPDATE agenda.telefones SET tipo = ?, numero = ?, principal = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bean.getTipo().toString());
            ps.setString(2, bean.getNumero());
            ps.setBoolean(3, bean.getPrincipal());
            ps.setInt(4, bean.getID());
            ps.executeUpdate();
        }
    }

    @Override
    protected void delete(Connection conn, Integer id) throws SQLException {
        final String sql = "DELETE FROM agenda.telefones WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    protected Telefone get(Connection conn, Integer id) throws SQLException {
        final String sql = "SELECT * FROM agenda.telefones WHERE id = ?";
        Telefone tel = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.first()) {
                    tel = resultSetToBean(conn, rs);
                }
            }
        }
        return tel;
    }

    @Override
    protected List<Telefone> getAll(Connection conn) throws SQLException {
        final String sql = "SELECT * FROM agenda.telefones";
        List<Telefone> telefones = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    telefones.add(resultSetToBean(conn, rs));
                }
            }
        }
        return telefones;
    }
    
    protected List<Telefone> findByContatoID(Connection conn, Integer contatoID) throws SQLException {
        final String sql = "SELECT * FROM agenda.telefones WHERE contatos_id = ?";
        List<Telefone> telefones = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contatoID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    telefones.add(resultSetToBean(conn, rs));
                }
            }
        }
        return telefones;
    }

    private Telefone resultSetToBean(Connection conn, ResultSet rs) throws SQLException {
        Telefone tel = new Telefone();
        
        setGeneratedKey(tel, rs.getInt(1));
        tel.setTipo(TipoTelefone.valueOf(rs.getString("tipo")));
        tel.setNumero(rs.getString("numero"));
        tel.setPrincipal(rs.getBoolean("principal"));
        return tel;
    }

}
