package gestisimalFX;

import java.net.URL;
import java.util.ResourceBundle;

import gestisimal.Almacen;
import gestisimal.ArticuloNoExisteException;
import gestisimal.UnidadesNegativasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class AltaArticuloController implements Initializable {

  Almacen almacen;
  @FXML
  private TextArea AltDesc;
  @FXML
  private TextField AltPC;
  @FXML
  private TextField AltPV;
  @FXML
  private TextField AltUds;
  @FXML
  private Button AltAceptar;

  static int currentId;

  @FXML
  public void alta(ActionEvent e)
      throws NumberFormatException, UnidadesNegativasException, ArticuloNoExisteException {
    almacen.anadir(AltDesc.getText(), Double.parseDouble(AltPC.getText()),
        Double.parseDouble(AltPV.getText()), Integer.parseInt(AltUds.getText()));
    GestisimalController.setAlmacen(almacen);
    GestisimalController.hecho(0);
    Stage stage = (Stage) AltDesc.getScene().getWindow();
    stage.close();
  }

  public boolean numerosEnteros(KeyEvent keyEvent) {
    char key = keyEvent.getCharacter().charAt(0);
    if (!Character.isDigit(key)) {
      keyEvent.consume();
      return false;
    }
    return true;
  }
  public boolean numerosFlotantes(KeyEvent keyEvent) {
    char key = keyEvent.getCharacter().charAt(0);
    if (!Character.isDigit(key) && key != '.') {
      keyEvent.consume();
      return false;
    }
    return true;
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    AltPC.addEventHandler(KeyEvent.KEY_TYPED, event -> numerosFlotantes(event));
    AltPV.addEventHandler(KeyEvent.KEY_TYPED, event -> numerosFlotantes(event));
    AltUds.addEventHandler(KeyEvent.KEY_TYPED, event -> numerosEnteros(event));
    almacen = GestisimalController.getAlmacen();
  }

}
