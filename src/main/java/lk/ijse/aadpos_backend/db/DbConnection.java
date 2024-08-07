package lk.ijse.aadpos_backend.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private DataSource dataSource;
    private Connection connection;
    private static DbConnection instance;

    public DbConnection() throws ClassNotFoundException, SQLException {

        try {
           var InitialContext = new InitialContext();
           dataSource = (DataSource) InitialContext.lookup("java:comp/env/jdbc/aad_backend");
           this.connection = (Connection) dataSource.getConnection();

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    public Connection getConnection() {
        return connection;
    }
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null){
            instance = new DbConnection();
        }
        return instance;
    }
}
