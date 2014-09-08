package com.shiv.nammi.powermock.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: sn1
 * Date: 4/28/14
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBConnection {
    private static DBConnection _dbConnectionSingleton = null;
    private static Connection _conn = null;
    private boolean _flag = true;

    /** A private Constructor prevents any other class from instantiating. */
    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (InstantiationException e) {
            _flag = false;
        }
        catch (IllegalAccessException e) {
            _flag = false;
        }
        catch (ClassNotFoundException e) {
            _flag = false;
        }
        if (_flag) {
            openConnection();
        }
    }

    public Connection openConnection() {
        if (_conn == null) {
            try {
                _conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fdp_doc", "shiva", "password123");
            }
            catch (SQLException e) {
                _flag = false;
            }
        }
        return _conn;
    }

    /** Static 'instance' method */
    public static DBConnection getInstance() {
        if (_dbConnectionSingleton == null) {
            _dbConnectionSingleton = new DBConnection();
        }
        return _dbConnectionSingleton;
    }

    public boolean getConnectionStatus() {
        return _flag;
    }

    public void closeConnection(){
        try {
            _conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}