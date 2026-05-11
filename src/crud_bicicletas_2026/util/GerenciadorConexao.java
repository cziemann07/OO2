/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_bicicletas_2026.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cadul
 */
public class GerenciadorConexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/bicicletaria";
    private static final String user = "laudosapp";
    private static final String pass = "laudosapp";
    
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, user, pass);
    }
    
    public static void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
