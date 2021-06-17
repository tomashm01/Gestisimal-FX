package gestisimalFX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import gestisimal.Almacen;
import gestisimal.UnidadesNegativasException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportarController implements Initializable {

  @FXML
  private TextArea caja;

  Almacen almacen;

  private FileChooser fileChooser;
  private File fichero;
  private BufferedReader ficheroLeer;
  private String line;
  
  // XML
  String descxml;
  double precioCompraXml;
  double precioVentaXml;
  int unidadesXml;
  
  // CSV
  String descCsv;
  double precioCompraCsv;
  double precioVentaCsv;
  int unidadesCsv;

  public void cargar(ActionEvent e) throws IOException {
    fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Archivos CSV", "*.csv"),
        new ExtensionFilter("Archivos JSON", "*.json"), new ExtensionFilter("Archivos XML", "*.xml"));
    fichero = fileChooser.showOpenDialog(new Stage());
    ficheroLeer = new BufferedReader(new FileReader(fichero.getAbsolutePath()));

    line = ficheroLeer.readLine();
    while (line != null) {
      caja.setText(caja.getText() + line + "\n");
      line = ficheroLeer.readLine(); 
    }
  }

  @FXML
  public void importa(ActionEvent e) throws UnidadesNegativasException, FileNotFoundException, IOException {
    String extension = FilenameUtils.getExtension(fichero.getAbsolutePath());
    if (extension.equals("csv")) {
      csv();
    } else if (extension.equals("json")) {
      json();
    } else if (extension.equals("xml")) {
      xml();
    }
  }

  public void csv() throws FileNotFoundException, IOException, NumberFormatException, UnidadesNegativasException {
    CSVParser csvparser = CSVParser.parse(new FileReader(fichero.getAbsoluteFile()), CSVFormat.RFC4180);
    boolean primero = true;
    almacen = new Almacen();
    for (CSVRecord csv : csvparser) {
      if (!primero) {
        almacen.anadir(csv.get(1), Double.parseDouble(csv.get(2)), Double.parseDouble(csv.get(3)), Integer.parseInt(csv.get(4)));
      }
      primero = false;
    }
    cerrar();
  }

  public void xml() throws UnidadesNegativasException {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      Document documento = builder.parse(fichero.getAbsoluteFile());
      NodeList articulos = documento.getElementsByTagName("articulo");
      almacen = new Almacen();

      for (int i = 0; i < articulos.getLength(); i++) {
        Node node = articulos.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element e = (Element) node;
          NodeList hijos = e.getChildNodes();
          for (int j = 0; j < hijos.getLength(); j++) {
            Node hijo = hijos.item(j);
            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
              if (j == 1) {
                descxml = hijo.getTextContent();
              } else if (j == 2) {
                precioCompraXml = Double.parseDouble(hijo.getTextContent());
              } else if (j == 3) {
                precioVentaXml = Double.parseDouble(hijo.getTextContent());
              } else if (j == 4) {
                unidadesXml = Integer.parseInt(hijo.getTextContent());
              }
            }
          }
          almacen.anadir(descxml, precioCompraXml, precioVentaXml, unidadesXml);
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      alert.setTitle("Error");
      alert.setContentText("Error al importar fichero");
      alert.showAndWait();
    }
    cerrar();
  }

  public void json() {
    Gson gson = new Gson();
    almacen = gson.fromJson(caja.getText(), Almacen.class);
    cerrar();
  }

  public void cerrar() {
    GestisimalController.setAlmacen(almacen);
    Stage stage = (Stage) caja.getScene().getWindow();
    stage.close();
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

}
