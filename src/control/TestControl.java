/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dao.TestDAO;

/**
 *
 * @author AG SETO GALIH D
 */
public class TestControl {

    private TestDAO testDao = new TestDAO();

    public void testSelectQuery(String tableName) {
        testDao.testSelectQuery(tableName);
    }

}
