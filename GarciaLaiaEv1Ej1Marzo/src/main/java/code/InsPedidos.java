package code;

import classes.Pedido;
import classes.PedidoHandler;
import connection.ConexionBD;
import libs.CheckFiles;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class InsPedidos {

    public static void leerXML() {

        Path p = Path.of("src/main/resources/nuevosPedidos.xml");

        ArrayList<Pedido> pedidoXML = new ArrayList<Pedido>();

        //comprobamos si el archivo existe y se puede crear
        if (CheckFiles.ficheroLegible(p)) {

            //creamos las instancias de SAX
            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            PedidoHandler PedidoHandler = new PedidoHandler();

            try {
                SAXParser parser = saxPF.newSAXParser();
                parser.parse(p.toFile(), PedidoHandler);
                pedidoXML = PedidoHandler.getPedidos();

                //para comprobar que se han cargado bien los pedidos del XML en mis objetos
                for (Pedido ped : pedidoXML) {
                    try (Connection miCon = ConexionBD.conectar()) {

                        // COMPROBACIONES ANTES DE AÑADIR A LA BASE DE DATOS --------------------
                        if (!comprobarComercial(ped.getIdComercial())) {

                            System.err.println("\n>>> El comercial indicado (id = " + ped.getIdComercial() + ") no trabaja en esta empresa.");
                            System.err.println("El pedido de fecha " + ped.getFecha() + " no será insertado.\n");

                        } else if (!comprobarFecha(ped.getFecha())) {

                            System.err.println("\n>>> La fecha " + ped.getFecha() + " no puede ser posterior a hoy.");
                            System.err.println("El pedido de fecha " + ped.getFecha() + " no será insertado.\n");

                        } else {

                            //Consulta para introducir los datos en la BBDD.
                            PreparedStatement pstmt2 = miCon.prepareStatement("INSERT INTO pedido (total, fecha, id_cliente, id_comercial) values (?,?,?,?)");
                            pstmt2.setDouble(1, ped.getTotal());
                            pstmt2.setDate(2, ped.getFecha());
                            pstmt2.setInt(3, ped.getIdCliente());
                            pstmt2.setInt(4, ped.getIdComercial());

                            pstmt2.executeUpdate();

                            // Informar al usuario
                            System.out.println("\n>>> El pedido de fecha " + ped.getFecha() + " se insertará en la base de datos.");

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
            } catch (ParserConfigurationException e) {
                System.err.println("\n>>> ERROR: se ha producido un error al parsear los datos: " + e.getMessage());
            } catch (SAXException e) {
                System.out.println("\n>>> ERROR SAX: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("\n>>> ERROR: " + e.getMessage());
            }
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


    public static void insPedido() {


    }
}
