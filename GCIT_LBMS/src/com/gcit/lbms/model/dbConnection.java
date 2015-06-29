package com.gcit.lbms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	

	public Connection getConnection()
	{
		checkConnection();
		return conn;
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
	
	public ResultSet executeQuery(PreparedStatement pstmt)
	{
		checkConnection();
		try {
			ResultSet rs = pstmt.executeQuery();
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
	
	public void executeUpdate(PreparedStatement pstmt)
	{
		checkConnection();
		try {
			pstmt.executeUpdate(	);
			conn.close();
		} catch (SQLException e) {
			debugError(e);
		}
	}
}
