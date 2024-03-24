
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class DatabaseConnection {
    Connection con;
    public DatabaseConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/routine","root","jkkniu");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return con;
    }
}
