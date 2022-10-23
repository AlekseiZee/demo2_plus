package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// package roseindia.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	String driverClassName = "com.mysql.cj.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost/geocontrole?";
	String dbUser = "Aleksey";
	String dbPwd = "";

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
			System.out.println("Connected");
		}
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}

/*public class LoadDriver {
	public static void load() {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/geocontrole?" + "user=Aleksey&password=");
			System.out.println("Connected");
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	public void viewTable(Connection con) {
		String query = "select * from point";
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String 	Id = rs.getString("Id");
				Long Idobject = rs.getLong("Id_object");
				Double distance = rs.getDouble("distance");
				Double hangle = rs.getDouble("hangle");
				Double vangle = rs.getDouble("vangle");
				System.out.println(Id + ", " + Idobject + ", " + distance + ", " + hangle + ", " + vangle);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
	}
	public void findPointById(Connection con, String Id) {
		String query = "select * from point where Id=?";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, Id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String 	id = rs.getString("Id");
				Long Idobject = rs.getLong("Id_object");
				Double distance = rs.getDouble("distance");
				Double hangle = rs.getDouble("hangle");
				Double vangle = rs.getDouble("vangle");
				System.out.println(Id + ", " + Idobject + ", " + distance + ", " + hangle + ", " + vangle);
				}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
	}
}*/
