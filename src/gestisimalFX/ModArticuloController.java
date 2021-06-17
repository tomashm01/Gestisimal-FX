package gestisimalFX;

import java.net.URL;
import java.util.ResourceBundle;

import gestisimal.Almacen;
import gestisimal.Articulo;
import gestisimal.ArticuloNoExisteException;
import gestisimal.UnidadesNegativasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModArticuloController implements Initializable {

  Almacen almacen;
  Articulo artModificar;
  @FXML
  private TextField ModCodigo;
  @FXML
  private TextArea ModDesc;
  @FXML
  private TextField ModPC;
  @FXML
  private TextField ModPV;
  @FXML
  private TextField ModUds;
  @FXML
  private Button ModAceptar;
  
  static int currentId;
  
  @FXML
  public void modifica(ActionEvent e) throws NumberFormatException, UnidadesNegativasException, ArticuloNoExisteException {
    almacen.modificar(
        currentId,
        ModDesc.getText(),
        Double.parseDouble(ModPC.getText()),
        Double.parseDouble(ModPV.getText()),
        Integer.parseInt(ModUds.getText())
        );
    GestisimalController.setAlmacen(almacen);
    GestisimalController.hecho(2);
    Stage stage = (Stage) ModCodigo.getScene().getWindow();
    stage.close();
  }
  
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    almacen = GestisimalController.getAlmacen();
    currentId = GestisimalController.getCurrentId();
    try {
      artModificar = almacen.getArticulo(currentId);
    } catch (ArticuloNoExisteException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      alert.setTitle("Error");
      alert.setContentText("No existe articulo a modificar");
      alert.showAndWait();
    }
    ModCodigo.setText(Integer.toString(currentId));
    ModDesc.setText(artModificar.getDescripcion());
    ModPC.setText(Double.toString(artModificar.getPrecioCompra()));
    ModPV.setText(Double.toString(artModificar.getPrecioVenta()));
    ModUds.setText(Integer.toString(artModificar.getUnidades()));
  }

}
