package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 *  A class that verifies the username from the database.
 */


public class LoginDao {

	public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
		boolean status = false;

		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://95.179.206.75:3306/sql_smart_care_surgery_database?useSSL=false", "root", "1qRqabnPy8FZQh1NsWmhZ");

		// Step 2:Create a statement using connection object
			PreparedStatement pst = connection.prepareStatement("select * from admins, doctors, nurses, patients where admins.username = ? OR doctors.username = ? OR nurses.username = ? OR patients.username = ?")) {
                        pst.setString(1, loginBean.getUsername());
                        pst.setString(2, loginBean.getUsername());
                        pst.setString(3, loginBean.getUsername());
                        pst.setString(4, loginBean.getUsername());
                        
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
			status = rs.next();
                        
                        //SELECT admins.username, doctors.username, nurses.username, patients.username

                        //FROM table1,table2

                        //WHERE "search-condition(s)"
                        
        
		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return status;
	}
        
        
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
