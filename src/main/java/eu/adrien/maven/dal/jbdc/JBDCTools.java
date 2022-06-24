package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JBDCTools {
    public static Connection getConnection() throws DALException{
        final String SERVER = Settings.getProperty("server");
        final String PORT = Settings.getProperty("port");
        final String DB = Settings.getProperty("database");
        final String USER = Settings.getProperty("user");
        final String MDP = Settings.getProperty("password");
        final String TYPE = Settings.getProperty("type");

        StringBuilder sb = new StringBuilder();
        sb.append(TYPE).append(SERVER).append(":").append(PORT);
        sb.append("/").append(DB).append("?");
        sb.append("user=").append(USER).append("&password=").append(MDP);
        Connection cnx;
        try{
            cnx = DriverManager.getConnection(sb.toString());
        } catch (SQLException e){
            throw new DALException("Erreur de connexion à la db", e.getCause());
        }
        return cnx;
    }
}
