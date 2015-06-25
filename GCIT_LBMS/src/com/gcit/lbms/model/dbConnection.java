package com.gcit.lbms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class dbConnection {
	
	Connection conn;
	String s_url;
	String s_password;
	String s_user;
	boolean debuggingMode;
	
	public void debugError(SQLException e)
	{
		if (debuggingMode)
		{
			e.printStackTrace();
		}
	}
	
	public dbConnection(String url, String user, String password)
	{
		try {
			conn = DriverManager.getConnection(url, user, password);
			s_url = url;
			s_password = password;
			s_user = user;
			debuggingMode = true;
		}
		catch (SQLException e)
		{
			debugError(e);
		}
	}
	
	private void checkConnection()
	{
		try {
			if (conn.isClosed())
				conn = DriverManager.getConnection(s_url, s_user, s_password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String Query)
	{
		checkConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			return rs;
		} catch (SQLException e) {
			debugError(e);
			return null;
		}			
	}
	
	public void closeConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeUpdate(String Query)
	{
		checkConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(Query);
			conn.close();
		} catch (SQLException e) {
			debugError(e);
		}
	}
}
