package connection;

import java.lang.module.InvalidModuleDescriptorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    /**
     * Completar los datos con la base de datos en cada caso, puerto y jdbc.
     */

    //definimos como constantes los datos de la conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3707"; //conector y localización
    private static final String USUARIO = "root";
    private static final String CLAVE = "admin";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection((URL + "/pedidos"), USUARIO, CLAVE);
            // System.out.println("Conexión OK BD.");
        } catch (SQLException e) {
            System.out.println("Error en la conexión BD");
            e.getMessage();
        } catch (InvalidModuleDescriptorException e) {
            System.out.println("Error PAM BD");
        }
        return conexion;
    }

}
