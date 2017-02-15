/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.app;

import br.ufms.model.bean.Contato;
import br.ufms.model.dao.ContatoDAO;
import br.ufms.model.dao.DAOFactory;
import br.ufms.model.dao.TelefoneDAO;
import br.ufms.util.DatabaseManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class AgendaApp {

    /**
     * @param args the command line arguments
     */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        Connection conn = DatabaseManager.getInstance().getConnection();
        while (true) {
            System.out.println("♥♥♥♥♥♥♥♥AGENDA DE CONTATOS♥♥♥♥♥♥♥♥\n"
                    + "Digite a opção desejada:\n"
                    + "1 - Inserir novo contato\n"
                    + "2 - Buscar contato\n"
                    + "0 - Sair\n"
                    + "♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥");

            int opc = Integer.parseInt(br.readLine());
            Contato c = new Contato();
            switch (opc) {
                case 1:
                    criarContato(c);
                    break;
                case 2:
                    System.out.println("Digite o nome do contato desejado: ");
                    c = buscarContato(br.readLine());
                    System.out.println(c.getNome());
                    System.out.println(c.getNascimento());
                    System.out.println(c.getCriacao());
                    System.out.println("3 - Remover contato\n"
                            + "4 - Atualizar contato\n"
                            + "0 - Sair");
                    int opc2 = Integer.parseInt(br.readLine());
                    switch (opc2) {
                        case 3:
                            removerContato(c);
                            break;
                        case 4:
                            criarContato(c);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Opção inválida!\n");
                    }
                    break;

                case 0:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!\n");
            }
        }

    }

    public static void imp(int opc) {
        switch (opc) {

        }
    }

    public static void criarContato(Contato c) throws IOException, SQLException {
//        Contato c = new Contato();
        System.out.println("Digite o nome ☺: ");
        c.setNome(br.readLine());

        System.out.print("Digite a data de nascimento, separada por '/'☺: ");
        String dataCriacao[] = br.readLine().split("/");
        c.setNascimento(LocalDate.of(Integer.parseInt(dataCriacao[2]), Integer.parseInt(dataCriacao[1]), Integer.parseInt(dataCriacao[0])));

        ContatoDAO cDao = DAOFactory.getInstance().getContatoDAO();
        cDao.save(c);

        System.out.println("Contato adicionado com sucesso!☺☺☺☺☺☺\n");
    }

    public static void removerContato(Contato c) throws SQLException {
        ContatoDAO cDao = DAOFactory.getInstance().getContatoDAO();
        cDao.delete(c);
        System.out.println("Contato excluido com sucesso!\n");
    }

    public static Contato buscarContato(String procurado) throws SQLException {

        ContatoDAO cDao = DAOFactory.getInstance().getContatoDAO();
        List<Contato> listaContatos = cDao.getAll();

        for (Contato c : listaContatos) {
            if (c.getNome().equals(procurado)) {
                return c;
            }
        }
        return null;
    }
}
