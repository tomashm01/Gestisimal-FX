package gestisimalFX;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import gestisimal.ArticuloNoExisteException;
import gestisimal.Almacen;
import gestisimal.UnidadesNegativasException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GestisimalController implements Initializable {

  static Almacen almacen = new Almacen();

  @FXML
  private Menu menuListado;
  @FXML
  private Menu menuAlta;
  @FXML
  private Menu menuBaja;
  @FXML
  private Menu menuModificacion;
  @FXML
  private Menu menuentMerc;
  @FXML
  private Menu menusalMerc;
  @FXML
  private Menu menuUtils;

  @FXML
  private Label quehace; // Etiqueta inferior con detalles de los menú


  @FXML
  private MenuItem exitmenubtn; // Botón Utilidades > Salir (Ctrl+X)

  boolean finalizado = false; // Para controlar bucles
  int tmpInt; // Entero temporal para return
  double tmpDouble; // Double temporal para return
  String tmpStr; // Cadena temporal para return

  // Menú contextual
  ContextMenu contextMenu;

  @FXML
  ImageView background;

  // Modificacion
  static int currentId; // ID artículo a modificar

  public static Almacen getAlmacen() {
    return almacen;
  }

  public static void setAlmacen(Almacen almacenxt) {
    almacen = almacenxt;
  }

  public static int getCurrentId() {
    return currentId;
  }

  /**
   * Método para pedir el código del artículo
   */
  public int pideId() {
    do {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Gestisimal");
      dialog.setHeaderText("Gestión de artículos");
      dialog.setContentText("Introduce el ID:");

      Optional<String> result = dialog.showAndWait();

      try {
        tmpInt = Integer.parseInt(result.get());
        finalizado = true;
      } catch (NumberFormatException exc) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Gestisimal");
        alert.setHeaderText("Debes introducir el código del artículo");
        alert.setContentText("Recuerda que los códigos siempre son numéricos.");
        alert.showAndWait();
      }
    } while (!finalizado);
    finalizado = false;
    return tmpInt;
  }

  /**
   * Método para pedir la mercancía en entrada y salida
   */
  private int pideMerc() {
    do {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Gestisimal");
      dialog.setHeaderText("Gestión de artículos");
      dialog.setContentText("¿Cuánta mercancía?:");

      Optional<String> result = dialog.showAndWait();

      try {
        tmpInt = Integer.parseInt(result.get());
        finalizado = true;
      } catch (NumberFormatException exc) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Gestisimal");
        alert.setHeaderText("Debes introducir la mercancía.");
        alert.setContentText("Recuerda que lo que debes introducir es un número entero.");
        alert.showAndWait();
      }
    } while (!finalizado);
    finalizado = false;
    return tmpInt;
  }

  public static void cambiarVista(boolean aTabla) throws IOException {
    if (aTabla) {
      verArticulos();
    } else if (!aTabla) {
      listarInd();
    }
  }

  /**
   * Método para ver los artículos en una tabla
   * 
   * @param e
   * @throws IOException
   */
  @FXML
  private void verArticulos(ActionEvent e) throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Vista de artículos");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("VistaArticulos.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para ver los artículos en una tabla
   * 
   * @throws IOException
   */
  public static void verArticulos() throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Vista de artículos");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("VistaArticulos.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para ver un artículo individual
   * 
   * @throws IOException
   * @throws ArticuloNoExisteException
   */
  @FXML
  private void listarInd(ActionEvent e) throws IOException, ArticuloNoExisteException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Vista individual");
    FXMLLoader fxml =
        new FXMLLoader(GestisimalController.class.getResource("VistaIndividual.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para ver un artículo en concreto
   * 
   * @throws IOException
   */
  private static void listarInd() throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Vista individual");
    FXMLLoader fxml =
        new FXMLLoader(GestisimalController.class.getResource("VistaIndividual.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para dar de alta un artículo
   * 
   * @param e
   * @throws IOException
   */
  @FXML
  private void darAlta(ActionEvent e) throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Alta");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("AltaArticulo.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para dar de baja un artículo
   */
  @FXML
  private void darBaja(ActionEvent e) {
    currentId = pideId();
    almacen.eliminar(currentId);
    hecho(1);
  }

  /**
   * Método para modificar un artículo
   * 
   * @throws IOException
   */
  @FXML
  private void modificart(ActionEvent e) throws IOException {
    currentId = pideId();
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Modificar");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("ModArticulo.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void modificart(int id) throws IOException {
    currentId = id;
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Modificar");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("ModArticulo.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Método para la entrada de mercancía
   */
  @FXML
  private void entradaM(ActionEvent e) {
    try {
      almacen.incrementarStock(pideId(), pideMerc());
      hecho(3);
      System.out.println("Unidades incrementadas.");
      System.out.println(almacen.toString()); // TODO: Para pruebas sólo, borrar
    } catch (UnidadesNegativasException | ArticuloNoExisteException exc) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Gestisimal");
      alert.setHeaderText("Error");
      alert.setContentText("El artículo no existe o las unidades son negativas");
      alert.showAndWait();
    }
  }

  /**
   * Método para la salida de mercancía
   */
  @FXML
  private void salidaM(ActionEvent e) {
    try {
      almacen.decrementarStock(pideId(), pideMerc());
      hecho(4);
    } catch (UnidadesNegativasException | ArticuloNoExisteException exc) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Gestisimal");
      alert.setHeaderText("Error");
      alert.setContentText("El artículo no existe o las unidades son negativas");
      alert.showAndWait();
    }
  }

  /**
   * Método para mostrar un mensaje de hecho según la tarea realizada
   * 
   * @param tarea
   */
  public static void hecho(int tarea) {
    Alert alert2 = new Alert(AlertType.INFORMATION);
    alert2.setTitle("Tarea realizadaa");
    alert2.setHeaderText(null);
    switch (tarea) {
      case 0:
        alert2.setContentText("Alta realizada");
        break;
      case 1:
        alert2.setContentText("Baja realizada");
        break;
      case 2:
        alert2.setContentText("Modificación realizada");
        break;
      case 3:
        alert2.setContentText("Entrada registrada");
        break;
      case 4:
        alert2.setContentText("Salida registrada");
        break;
    }
    alert2.showAndWait();
  }

  @FXML
  private void darInfo(Event e) {
    String menuUso = ((Menu) e.getSource()).getId();

    if (menuUso.equals(menuListado.getId())) {
      quehace.setText("Puedes listar todos los artículos o ver una vista individual.");
    } else if (menuUso.equals(menuAlta.getId())) {
      quehace.setText("Dar de alta un nuevo artículo en el almacén.");
    } else if (menuUso.equals(menuBaja.getId())) {
      quehace.setText("Dar de baja un artículo del almacén.");
    } else if (menuUso.equals(menuModificacion.getId())) {
      quehace.setText("Modificar un artículo del almacén.");
    } else if (menuUso.equals(menuentMerc.getId())) {
      quehace.setText("Registrar entradas de mercancía del almacén.");
    } else if (menuUso.equals(menusalMerc.getId())) {
      quehace.setText("Registrar salidas de mercancía del almacén.");
    } else if (menuUso.equals(menuUtils.getId())) {
      quehace.setText("Utilidades de la aplicación.");
    } else {
      quehace.setText("GESTISIMAL");
    }
  }

  @FXML
  private void exporta(ActionEvent e) throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Exportar");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("Exportar.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @FXML
  private void importa(ActionEvent e) throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Gestisimal: Importar");
    FXMLLoader fxml = new FXMLLoader(GestisimalController.class.getResource("Importar.fxml"));
    GridPane root = fxml.<GridPane>load();
    stage.setScene(new Scene(root));
    stage.show();
  }
 
  @FXML
  private void quitaInfo(Event e) {
    quehace.setText("GESTISIMAL");
  }

  @FXML
  private void salir(ActionEvent e) {
    System.exit(0);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
   
    Stage stgGH = new Stage();
    background.setOnContextMenuRequested(
        e -> contextMenu.show(background, e.getScreenX(), e.getScreenY()));
  }
}
