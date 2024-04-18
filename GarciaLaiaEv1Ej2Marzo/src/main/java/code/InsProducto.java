package code;

import entities.GamaProductoEntity;
import entities.ProductoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import libs.Leer;

public class InsProducto {
    static EntityManager em = EmfSingleton.getInstance().getEmf().createEntityManager();
    static EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();

    public static void crearProducto() {

        // VARIABLES -------------
        String nombre;
        String gama;
        double precioVenta;

        System.out.println("\n*****[ CREAR NUEVO PRODUCTO ]*****");

        nombre = Leer.pedirCadena("Introduzca nombre del producto: ");
        gama = Leer.pedirCadena("Introduzca gama: ");
        precioVenta = Leer.pedirDouble("Introduzca precio de venta: ");

        try {
            EntityTransaction em2Transaction = em.getTransaction();
            em2Transaction.begin();

            Query obtenerID = em.createQuery("from ProductoEntity where nombre = ?1");
            obtenerID.setParameter(1, nombre);
            ProductoEntity producto = (ProductoEntity) obtenerID.getSingleResult();

            if (producto != null) {

                Query obtenerGama = em.createQuery("from GamaProductoEntity where gama = ?1");
                obtenerGama.setParameter(1, gama);
                GamaProductoEntity gamaProd = (GamaProductoEntity) obtenerID.getSingleResult();

                if (gamaProd != null) {

                    try {
                        EntityTransaction transaction = em.getTransaction();

                        // Comenzar a crear el contexto de persistencia.
                        transaction.begin();

                        // Crear la nueva obra.
                        ProductoEntity nuevoProd = new ProductoEntity();

                        nuevoProd.setNombre(nombre);
                        nuevoProd.setGama(gama);
                        nuevoProd.setPrecioVenta(precioVenta);

                        em.persist(nuevoProd);   // Objeto persistido.

                        // Al hacer el commit los cambios se pasan a la base de datos.
                        transaction.commit();

                        System.out.println("\n>>> *****{ PRODUCTO CREADO CORRECTAMENTE }*****\n");

                    } catch (Exception e) {
                        System.err.println(">>> ERROR EN LA CREACIÓN DEL PRODUCTO: " + e.getMessage());
                    }

                } else {
                    String descripcion = Leer.pedirCadena("\n>>> La gama no existe. Introduza una descripción para crearla:");

                    try {
                        EntityTransaction transaction3 = em.getTransaction();

                        // Comenzar a crear el contexto de persistencia.
                        transaction3.begin();

                        // Crear la nueva obra.
                        GamaProductoEntity nuevaGama = new GamaProductoEntity();

                        nuevaGama.setGama(gama);
                        nuevaGama.setDescripcionTexto(descripcion);

                        em.persist(nuevaGama);   // Objeto persistido.

                        // Al hacer el commit los cambios se pasan a la base de datos.
                        transaction3.commit();

                        System.out.println("\n>>> *****{ GAMA CREADA CORRECTAMENTE }*****\n");

                    } catch (Exception e) {
                        System.err.println(">>> ERROR EN LA CREACIÓN DE LA GAMA: " + e.getMessage());
                    } finally {
                        // Asegurar que la conexión se cierra y el contexto de persistencia termina.
                        em.close();
                    }
                }
            } else {
                System.err.println("\n>>>ERROR: No se encontró ninguna producto con ese nombre.");
            }

            em2Transaction.commit();

        } catch (Exception e) {
            System.err.println("\n>>>ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
