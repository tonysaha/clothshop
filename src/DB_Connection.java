
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class DB_Connection {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author DELL
 */

    Connection connection;
     public void get_connection(){
         try {
             
             
             Class.forName("com.mysql.jdbc.Driver");
             //connection=DriverManager.getConnection("jdbc:mysql://db4free.net:3306/tony_cloth","tonysaha","14193169");
             connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tony_cloth","root","");
             Statement st = connection.createStatement();
             JOptionPane.showMessageDialog(null, "Connection ok");
         
	}catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Connection not ok Check Again");
	}

     }
}

