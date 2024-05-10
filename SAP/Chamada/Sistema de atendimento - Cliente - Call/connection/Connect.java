package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private Connection conn;
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_de_atendimento";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    public static final int SALVO = 1;
    public static final int NAO_SALVO = 0;
    public static final int ERRO = -1;

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
        }
    }

    public java.sql.Connection getConnection() {
        conn = null;
        try {
            Class.forName(DRIVER_CLASS);
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException ex) {
            conn = null;
        } catch (SQLException ex) {
            conn = null;
        }
        return conn;
    }
}
