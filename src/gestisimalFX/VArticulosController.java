package gestisimalFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gestisimal.Almacen;
import gestisimal.Articulo;
import gestisimal.ArticuloNoExisteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VArticulosController implements Initializable {

  Almacen almacen;

  @FXML
  private TableView<Articulo> tablaVA;
  @FXML
  private TableColumn<Articulo, Integer> codigoVA;
  @FXML
  private TableColumn<Articulo, String> descVA;
  @FXML
  private TableColumn<Articulo, Double> preComVA;
  @FXML
  private TableColumn<Articulo, Double> preVenVA;
  @FXML
  private TableColumn<Articulo, Integer> udsVA;
  @FXML
  private Button changeView;

  public void reloadTb() {
    codigoVA.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    descVA.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    preComVA.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
    preVenVA.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
    udsVA.setCellValueFactory(new PropertyValueFactory<>("unidades"));

    for (Articulo art : almacen.almacen) {
      tablaVA.getItems().add(art);
    }
  }

  @FXML
  public void modificar(ActionEvent e) throws IOException, ArticuloNoExisteException {
    Articulo articulo = tablaVA.getSelectionModel().getSelectedItem();
    GestisimalController.modificart(articulo.getCodigo());
    Stage stage = (Stage) tablaVA.getScene().getWindow();
    stage.close();
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    almacen = GestisimalController.getAlmacen();

    reloadTb();

    changeView.setOnAction(e -> {
      try {
        
        GestisimalController.cambiarVista(false);
        
        Stage stage = (Stage) tablaVA.getScene().getWindow();
        stage.close();
      } catch (IOException e1) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Problema en el cambio de vista");
        alert.showAndWait();
      }
    });

  }

}
