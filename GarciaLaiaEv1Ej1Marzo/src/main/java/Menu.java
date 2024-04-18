import code.InsPedidoUsuario;
import code.InsPedidos;
import code.ListarPedidos;

import java.sql.Date;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        // DECLARACIONES
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        String opcion = "";

        // BUCLE PARA MOSTRAR EL MENÚ DE OPCIONES
        while (!salir) {

            System.out.println("""
                    \n-----------------------------------------------------
                    1. Listar pedidos de un comercial anteriores al 2018
                    2. Añadir nuevos pedidos
                    3. Insertar por consola
                    0. Salir
                    -----------------------------------------------------""");

            opcion = sc.nextLine();                                                                         // Leer y guardar la opción del usuario.

            // ESTRUCTURA PARA LA LLAMADA A LOS MÉTODOS
            switch (opcion) {
                case "0" ->
                        salir = true;                                                                   // Fin de la ejecución del menú.

                case "1" -> ListarPedidos.listarPedidos();

                case "2" -> InsPedidos.leerXML();
                case "3" -> InsPedidoUsuario.pedirDatos();

                default ->
                        System.out.println("\n>>>OPCIÓN NO VÁLIDA: Introduzca una opción del menú");        // Informar al usuario de un error cometido.
            }
        }
    }
}

