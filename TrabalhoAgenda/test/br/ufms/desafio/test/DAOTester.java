/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.desafio.test;

import br.ufms.model.daolib.Bean;
import br.ufms.model.daolib.ReadOnlyDAO;
import br.ufms.model.daolib.ReadWriteDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuário
 * @param <B>
 * @param <T>
 */
public abstract class DAOTester<B extends Bean<T>, T extends Serializable> {

    private final ReadOnlyDAO<B, T> readOnlyDAO;
    private final ReadWriteDAO<B, T> readWriteDAO;
    private String msgError = "";

    public DAOTester(ReadOnlyDAO<B, T> dao) {
        this.readOnlyDAO = dao;
        this.readWriteDAO = (dao instanceof ReadWriteDAO) ? (ReadWriteDAO<B, T>) dao : null;
    }

    public void start() {
        if (readWriteDAO == null) {
            readOnlyRoutine();
        } else {
            readWriteRoutine();
        }
    }

    private void readOnlyRoutine() {
        try {
            List<B> list = testGetAll();
            if (list.size() > 0) {
                testGet(list.get(0).getID());
            } else {
                System.out.println("Elements not found.");
            }

        } catch (SQLException ex) {
            System.err.println(msgError + ex.getMessage());
        }
    }

    private void readWriteRoutine() {
        try {
            B bean = testInsert(createBean());
            bean = testGet(bean.getID());
            if (bean != null) {
                updateBean(bean);
            }
            testUpdate(bean);
            testGet(bean.getID());
            testDelete(bean.getID());
            testGetAll();

        } catch (SQLException ex) {
            System.err.println(msgError + ex.getMessage());
        }
    }

    private B testInsert(B bean) throws SQLException {
        print("Testing insert()...");
        msgError = "insert() Error: ";

        // insert dependencies if exists...
        List<Serializable> dependencies = new ArrayList<>();
        insertDependencyList(dependencies);

        readWriteDAO.insert(bean, dependencies.toArray(new Serializable[dependencies.size()]));
        return bean;
    }

    private B testUpdate(B bean) throws SQLException {
        print("Testing update()...");
        msgError = "update() Error: ";
        readWriteDAO.update(bean);
        return bean;
    }

    private void testDelete(T id) throws SQLException {
        print("Testing delete()...");
        msgError = "delete() Error: ";

        readWriteDAO.delete(id);
    }

    private B testGet(T id) throws SQLException {
        print("Testing get()...");
        msgError = "get() Error: ";

        B bean = readOnlyDAO.get(id);
        if (bean != null) {
            printBean(bean);
        } else {
            System.err.println("Element not found.");
        }
        return bean;
    }

    private List<B> testGetAll() throws SQLException {
        print("Testing getAll()...");
        msgError = "getAll() Error: ";

        List<B> list = readOnlyDAO.getAll();
        list.stream().forEach((b) -> {
            printBean(b);
        });
        return list;
    }

    protected abstract void printBean(B bean);

    protected abstract B createBean();

    protected abstract void updateBean(B bean);

    protected void insertDependencyList(List<Serializable> dependencies) {
        //
    }

    protected void print(String msg) {
        StringBuilder str = new StringBuilder();
        str.append("------------------------------------------------------------\n");
        str.append(msg).append("\n");
        str.append("------------------------------------------------------------\n");
        System.out.print(str);
    }

//    private abstract class DAOTest {
//
//        protected abstract String getTestName();
//
//        protected abstract void test();
//
//        public void run() {
//            StringBuilder str = new StringBuilder();
//            
//            str.append("------------------------------------------------------------\n");
//            str.append(getTestName()).append("\n");
//            str.append("------------------------------------------------------------\n");
//            
//            System.out.print(str);
//        }
//    }
//
//    private class InsertTest extends DAOTest {
//
//        @Override
//        protected String getTestName() {
//            return "InsertTest";
//        }
//
//        @Override
//        protected void test() {
//            
//        }
//    }
}
