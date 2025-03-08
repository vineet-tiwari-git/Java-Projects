package com.vineet.datafetcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class DataBaseConnPool {

    String connString = "";
    String user = "";
    String pwd = "";

    static final int INITIAL_CAPACITY = 10;
    LinkedList<Connection> pool = new LinkedList<Connection>();

    public String getConnString() {
        return connString;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUser() {
        return user;
    }

    public DataBaseConnPool() throws SQLException {

    	
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            pool.add(DriverManager.getConnection(connString, user, pwd));
        }
        System.out.println("Pool Established with size: " + pool.size());
    }

    public DataBaseConnPool(String connString, String user, String pwd, int poolSize) throws SQLException {
       
    	 System.out.println("Creating DB Pool : " + connString);
    	 
    	this.connString = connString;
    	this.user = user;
        this.pwd = pwd;
        
        for (int i = 0; i < poolSize; i++) {
            pool.add(DriverManager.getConnection(connString, user, pwd));
        }
        System.out.println("Pool Established with size: " + pool.size());
    }

    public synchronized Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            pool.add(DriverManager.getConnection(connString, user, pwd));
        }
        return pool.pop();
    }

    public synchronized void returnConnection(Connection connection) {
        pool.push(connection);
    }
}
