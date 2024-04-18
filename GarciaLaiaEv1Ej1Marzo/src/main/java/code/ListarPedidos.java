package code;

import connection.ConexionBD;
import libs.Leer;

import java.sql.*;

public class ListarPedidos {
    static Date FECHA_LIMITE = Date.valueOf("2018-01-01");
    public static void listarPedidos() {

        try (Connection miCon = ConexionBD.conectar()) {

            int idComercial;
            Date fecha = null;
            String nomCliente = "";
            String apeCliente = "";

            // Pedir nombre del comercial.
            String nomComercial = Leer.pedirCadena("\nIntroduzca el nombre del comercial:");
            String apellidoComercial = Leer.pedirCadena("\nIntroduzca el apellido del comercial:");

            PreparedStatement pstmt = miCon.prepareStatement("SELECT * FROM comercial where nombre like ? and apellido1 like ?");
            pstmt.setString(1, nomComercial);
            pstmt.setString(2, apellidoComercial);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                // Recoger el id del comercial.
                idComercial = rs.getInt("id");

                // Recoger los pedidos con el id del comercial.
                PreparedStatement pstmt2 = miCon.prepareStatement("SELECT * FROM pedido WHERE id_comercial = ?");
                pstmt2.setInt(1, idComercial);

                // OBTENER NOMBRE DEL COMERCIAL --------
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()) {
                    fecha = rs2.getDate("fecha");

                    if (fecha.before(FECHA_LIMITE)) {
                        // Recoger los pedidos con el id del comercial.
                        PreparedStatement pstmt3 = miCon.prepareStatement("SELECT * FROM cliente WHERE id = ?");
                        pstmt3.setInt(1, rs2.getInt("id_cliente"));

                        // OBTENER NOMBRE DEL CLIENTE --------
                        ResultSet rs3 = pstmt3.executeQuery();

                        while (rs3.next()) {
                            nomCliente = rs3.getString("nombre");
                            apeCliente = rs3.getString("apellido1");
                            System.out.println("\n- El pedido con fecha: " + fecha + " lo hizo el cliente " + nomCliente + " " + apeCliente);
                        }
                    }
                }

            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("\nNo se cumple una condición de integridad de la base de datos");
        } catch (SQLSyntaxErrorException e) {
            System.err.println("\nHay un error de sintaxis" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n>>> Error en la recogida de datos " + e.getMessage());
            e.printStackTrace();
        }

    }
}