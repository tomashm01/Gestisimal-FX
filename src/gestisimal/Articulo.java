package gestisimal;

public class Articulo {

  private int codigo;
  private String descripcion;
  private double precioCompra;
  private double precioVenta;
  private int unidades;
  private static int contador = 1;

  // Constructor ////////
  Articulo(String descripcion, double precioCompra, double precioVenta, int unidades)
      throws UnidadesNegativasException {
    this.codigo = contador++;
    setDescripcion(descripcion);
    setPrecioCompra(precioCompra);
    setPrecioVenta(precioVenta);
    setUnidades(unidades);
  }


  Articulo(int code) {
    this.codigo = code;
  }

  // Getters & Setters ////////
  public int getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  private void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public double getPrecioCompra() {
    return precioCompra;
  }

  private void setPrecioCompra(double precioCompra) {
    this.precioCompra = precioCompra;
  }

  public double getPrecioVenta() {
    return precioVenta;
  }

  private void setPrecioVenta(double precioVenta) {
    this.precioVenta = precioVenta;
  }

  public int getUnidades() {
    return unidades;
  }

  private void setUnidades(int unidades) throws UnidadesNegativasException {
    if (unidades < 0) {
      throw new UnidadesNegativasException("Las unidades no pueden ser negativas.");
    }
    this.unidades = unidades;
  }

  /**
   * Método modificar para modificar las propiedades de Articulo
   * 
   * @param descripcion
   * @param precioCompra
   * @param precioVenta
   * @param unidades
   * @return
   * @throws UnidadesNegativasException
   */
  void modificar(String descripcion, double precioCompra, double precioVenta, int unidades)
      throws UnidadesNegativasException {
    setDescripcion(descripcion);
    setPrecioCompra(precioCompra);
    setPrecioVenta(precioVenta);
    setUnidades(unidades);
  }

  /**
   * Método incrementarStock para incrementar el stock de Articulo
   * 
   * @param unidades
   * @return
   * @throws UnidadesNegativasException
   */
  void incrementarStock(int unidades) throws UnidadesNegativasException {
    setUnidades(getUnidades() + unidades);
  }

  /**
   * Método decrementarStock para decrementar el stock de Articulo
   * 
   * @param unidades
   * @return
   * @throws UnidadesNegativasException
   */
  void decrementarStock(int unidades) throws UnidadesNegativasException {
    setUnidades(getUnidades() - unidades);
  }

  @Override
  public String toString() {
    return "COD → " + getCodigo() + ".\n Descripción → " + getDescripcion()
        + "\n Precio de compra → " + getPrecioCompra() + "€\n Precio de venta → " + getPrecioVenta()
        + "€\n Unidades → " + getUnidades();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + codigo;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Articulo other = (Articulo) obj;
    if (codigo != other.codigo)
      return false;
    return true;
  }

}
