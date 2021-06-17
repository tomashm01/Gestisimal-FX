package utiles;

public class Menu {
  
  // Propiedades Menu //////
  private String tituloMenu;
  private String[] opciones;
  
  // Constructor //////
  public Menu(String tituloMenu, String[] opciones) {
    setTituloMenu(tituloMenu);
    setOpciones(opciones);
  }

  // Getters & Setters //////
  private String getTituloMenu() {
    return tituloMenu;
  }

  private void setTituloMenu(String tituloMenu) {
    this.tituloMenu = tituloMenu;
  }

  private String[] getOpciones() {
    return opciones;
  }

  private void setOpciones(String[] opciones) {
    this.opciones = opciones;
  }
  
  
  /**
   * Método que muestra el menú y pide la opción
   * 
   * @return opción, llamando al método escoger
   */
  public int gestiona() {
    muestraMenu();
    return escoger();
  }
  
  
  /**
   * Muestra el menú por pantalla
   */
  private void muestraMenu() {
    System.out.println("\n" + getTituloMenu() + "\n");
    for (int i=0;i<getOpciones().length;i++) {
      System.out.println(i+1 + ". " + getOpciones()[i]);
    }
  }
  
  /**
   * Escoge una opción del menú
   * @return
   */
  private int escoger() {
    int eleccion;
    do {
      eleccion = Teclado.leerEntero("Escoge una opción: ");
    } while (!(eleccion >= 1 && eleccion <= getOpciones().length));
    return eleccion;
  }
  
}
