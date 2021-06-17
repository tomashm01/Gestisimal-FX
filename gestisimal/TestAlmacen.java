package gestisimal;

import utiles.Menu;
import utiles.Teclado;

public class TestAlmacen {

  private static Almacen almacen = new Almacen();

  /**
   * Pide el código de un artículo.
   * 
   * @return int
   */
  private static int pedirCodigo() {
    return Teclado.leerEntero("Introduce el código del artículo: ");
  }

  /**
   * Pide la descripción de un artículo.
   * 
   * @return String
   */
  private static String pedirDescripcion() {
    return Teclado.leerCadena("Introduce la descripción: ");
  }

  /**
   * Pide el precio de compra de un artículo.
   * 
   * @return double
   */
  private static double pedirPrecioCompra() {
    return Teclado.leerDouble("Introduce el precio de compra: ");
  }

  /**
   * Pide el precio de venta de un artículo.
   * 
   * @return double
   */
  private static double pedirPrecioVenta() {
    return Teclado.leerDouble("Introduce el precio de venta: ");
  }

  /**
   * Pide las unidades de un artículo.
   * 
   * @return double
   */
  private static int pedirUnidades() {
    return Teclado.leerEntero("Introduce las unidades: ");
  }

  // Tareas
  /**
   * Da de alta un artículo.
   * 
   * @return Articulo
   */
  private static void alta() {
    try {
      almacen.anadir(pedirDescripcion(), pedirPrecioCompra(), pedirPrecioVenta(), pedirUnidades());
      System.out.println(almacen.getUltimo());
    } catch (UnidadesNegativasException e) {
      System.err.println("Error en el alta. " + e.getMessage());
    }
  }

  /**
   * Da de baja un artículo.
   */
  private static void baja() {
    if (almacen.eliminar(pedirCodigo())) {
      System.out.println("Artículo eliminado.");
    } else {
      System.err.println("Artículo no encontrado.");
    }

  }

  /**
   * Modifica un artículo
   */
  private static void modificacion() {
    try {
      almacen.modificar(pedirCodigo(), pedirDescripcion(), pedirPrecioCompra(), pedirPrecioVenta(),
          pedirUnidades());
      System.out.println("Artículo modificado.");
    } catch (UnidadesNegativasException e) {
      System.err.println(e.getMessage());
    } catch (ArticuloNoExisteException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Incrementa las existencias de un artículo
   */
  private static void incrementarStock() {
    try {
      almacen.incrementarStock(pedirCodigo(), Teclado.leerEntero("¿Cuántas existencias entran? "));
      System.out.println("Unidades incrementadas.");
    } catch (UnidadesNegativasException | ArticuloNoExisteException e) {
      System.err.println("Error al incrementar " + e.getMessage());
    }
  }

  /**
   * Decrementa las existencias de un artículo
   */
  private static void decrementarStock() {
    try {
      almacen.decrementarStock(pedirCodigo(), Teclado.leerEntero("¿Cuántas existencias salen? "));
      System.out.println("Unidades decrementadas.");
    } catch (UnidadesNegativasException | ArticuloNoExisteException e) {
      System.err.println("Error al decrementar: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    // Crea un objeto de la clase Menu
    String[] opciones = new String[] {"Listado", "Alta", "Baja", "Modificación",
        "Entrada de mercancía", "Salida de Mercancia", "Salir"};
    Menu menu = new Menu("GESTISIMAL", opciones);

    do {
      switch (menu.gestiona()) {
        case 1:
          System.out.println(almacen);
          break;

        case 2:
          alta();
          break;

        case 3:
          baja();
          break;

        case 4:
          modificacion();
          break;

        case 5:
          incrementarStock();
          break;

        case 6:
          decrementarStock();
          break;

        case 7:
          System.out.print("\nSaliendo...");
          System.exit(0);
          break;
      }
    } while (true);
  }

}
