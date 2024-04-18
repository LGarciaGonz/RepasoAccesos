package code;

import connection.ConexionBD;
import libs.Leer;

import java.sql.*;
import java.util.Calendar;

public class InsPedidoUsuario {

    public static void pedirDatos() {

        double total = Leer.pedirDouble("Introduce el total:");
        Date fecha = Leer.pedirDateSQL("Introduce la fecha del pedido:");
        int idcliente = Leer.pedirEntero("Introdude el id de cliente:");
        int idcomercial = Leer.pedirEntero("Introduce el id del comercial:");

        insertar(total, fecha, idcliente, idcomercial);

    }

    private static void insertar(double total, Date fecha, int idcliente, int idcomercial) {

        try (Connection miCon = ConexionBD.conectar()) {

            // COMPROBACIONES ANTES DE AÑADIR A LA BASE DE DATOS --------------------
            if (!comprobarComercial(idcomercial)) {

                System.err.println("\n>>> El comercial indicado (id = " + idcomercial + ") no trabaja en esta empresa.");
                System.err.println("El pedido de fecha " + fecha + " no será insertado.\n");

            } else if (!comprobarFecha(fecha)) {

                System.err.println("\n>>> La fecha " + fecha + " no puede ser posterior a hoy.");
                System.err.println("El pedido de fecha " + fecha + " no será insertado.\n");

            } else {

                //Consulta para introducir los datos en la BBDD.
                PreparedStatement pstmt2 = miCon.prepareStatement("INSERT INTO pedido (total, fecha, id_cliente, id_comercial) values (?,?,?,?)");
                pstmt2.setDouble(1, total);
                pstmt2.setDate(2, fecha);
                pstmt2.setInt(3, idcliente);
                pstmt2.setInt(4, idcomercial);

                pstmt2.executeUpdate();

                // Informar al usuario
                System.out.println("\n>>> El pedido de fecha " + fecha + " se insertará en la base de datos.");

            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            System.err.println("\n>>> No se cumple una condición de integridad de la base de datos");
        } catch (SQLSyntaxErrorException ex) {
            System.err.println("\n>>> Hay un error de sintaxis" + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("\n>>> Error en la comprobación del pedido" + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static boolean comprobarFecha(Date fecha) {

        // Variables.
        boolean fechaValida = false;
        Date fechaHoy = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        // Comprobar si la fecha es posterior a la actual.
        if (fecha.after(fechaHoy)) {
            fechaValida = false;
        } else {
            fechaValida = true;
        }

        return fechaValida;
    }

    public static boolean comprobarComercial(int idComercial) {

        boolean encontrado = false;

        // BUSCAR EN LA BASE DE DATOS SI YA EXISTE LA OBRA ------------------
        try (Connection miCon = ConexionBD.conectar()) {
            PreparedStatement pstmt = miCon.prepareStatement("SELECT nombre FROM comercial WHERE id = ?");
            pstmt.setInt(1, idComercial);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                encontrado = true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("\n>>> Error: No se cumple una condición de integridad de la base de datos");
            ;
        } catch (SQLSyntaxErrorException e) {
            System.err.println("\n>>> Error: Hay un error de sintaxis" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("\n>>> Error en la comprobación de la obra");
        }

        return encontrado;
    }

}
