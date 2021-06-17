package gestisimal;

import java.util.ArrayList;

public class Almacen {
  public ArrayList<Articulo> almacen = new ArrayList<Articulo>();

  /**
   * Método para añadir artículos al almacén.
   * 
   * @param descripcion
   * @param precioCompra
   * @param precioVenta
   * @param unidades
   * @throws UnidadesNegativasException
   */
  public void anadir(String descripcion, double precioCompra, double precioVenta, int unidades)
      throws UnidadesNegativasException {
    almacen.add(new Articulo(descripcion, precioCompra, precioVenta, unidades));
  }

  /**
   * Método para eliminar artículos del almacén.
   * 
   * @param code
   * @return boolean
   */
  public boolean eliminar(int code) {
    return almacen.remove(new Articulo(code));
  }

  /**
   * Método para modificar un artículo del almacén.
   * 
   * @param code
   * @param descripcion
   * @param precioCompra
   * @param precioVenta
   * @param unidades
   * @return
   * @throws UnidadesNegativasException
   * @throws ArticuloNoExisteException
   */
  public void modificar(int code, String descripcion, double precioCompra, double precioVenta,
      int unidades) throws UnidadesNegativasException, ArticuloNoExisteException {
    getArticulo(code).modificar(descripcion, precioCompra, precioVenta, unidades);
  }

  /**
   * Método para incrementar las existencias de un artículo del almacén.
   * 
   * @param code
   * @param cantidad
   * @throws UnidadesNegativasException
   * @throws ArticuloNoExisteException
   */
  public void incrementarStock(int code, int cantidad)
      throws UnidadesNegativasException, ArticuloNoExisteException {
    getArticulo(code).incrementarStock(cantidad);
  }

  /**
   * Método para decrementar las existencias de un artículo del almacén.
   * 
   * @param code
   * @param cantidad
   * @throws UnidadesNegativasException
   * @throws ArticuloNoExisteException
   */
  public void decrementarStock(int code, int cantidad)
      throws UnidadesNegativasException, ArticuloNoExisteException {
    getArticulo(code).decrementarStock(cantidad);
  }

  /**
   * Método para ver un artículo en concreto del almacén.
   * 
   * @param code
   * @return
   * @throws ArticuloNoExisteException
   */
  public Articulo getArticulo(int code) throws ArticuloNoExisteException {
    try {
      return almacen.get(almacen.indexOf(new Articulo(code)));
    } catch (IndexOutOfBoundsException e) {
      throw new ArticuloNoExisteException("El artículo con código " + code + " no existe.");
    }

  }

  Articulo getUltimo() {
    return almacen.get(almacen.size() - 1);
  }

  @Override
  public String toString() {
    StringBuilder txtBuilder = new StringBuilder();
    for (int i = 0; i <= almacen.size() - 1; i++) {
      txtBuilder.append(almacen.get(i) + "\n");
    }
    return "\n" + (txtBuilder.toString());
  }


}
