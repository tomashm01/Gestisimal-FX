package gestisimalFX;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.google.gson.*;

import gestisimal.Almacen;
import gestisimal.Articulo;
import gestisimal.ArticuloNoExisteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ExportarController implements Initializable {

  Almacen almacen;
  Articulo articulo;
  @FXML
  private TextArea caja;
  
  private String tmpString;
  
  private FileChooser fileSaver;
  private ExtensionFilter filtro;
  private File ficheroGuardar;
  private BufferedWriter ficheroGuardador;
  private String formato = ".txt";

  @FXML
  public void csv(ActionEvent e) throws ArticuloNoExisteException {
    tmpString = "codigo,descripcion,precioCompra,precioVenta,unidades";
    for (int i=1;i<=almacen.almacen.size();i++) {
      articulo = almacen.getArticulo(i);
      tmpString += ("\n" + articulo.getCodigo() + ","
          + articulo.getDescripcion() + ","
          + articulo.getPrecioCompra() + ","
          + articulo.getPrecioVenta() + ","
          + articulo.getUnidades());
    }
    caja.setText(tmpString);
    formato = ".csv";
  }
  
  @FXML
  public void xml(ActionEvent e) throws ParserConfigurationException, ArticuloNoExisteException, TransformerFactoryConfigurationError, TransformerException {
    
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
    DocumentBuilder builder = factory.newDocumentBuilder();
    
    DOMImplementation implementation = builder.getDOMImplementation();
    
   
    Document documento = implementation.createDocument(null, "almacen", null);
    documento.setXmlVersion("1.0");
    
    Element articulos = documento.createElement("articulos"); 
    
    for (int i=1;i<=almacen.almacen.size();i++) {
      articulo = almacen.getArticulo(i);
      String codart = Integer.toString(articulo.getCodigo());
      String descart = articulo.getDescripcion();
      String pcart = Double.toString(articulo.getPrecioCompra());
      String pvart = Double.toString(articulo.getPrecioVenta());
      String udsart = Integer.toString(articulo.getUnidades());
      
      Element articulo = documento.createElement("articulo");
      
      Element codigo = documento.createElement("codigo");
      Text codigotxt = documento.createTextNode(codart);
      codigo.appendChild(codigotxt);
      articulo.appendChild(codigo);
      
      Element descripcion = documento.createElement("descripcion");
      Text descripciontxt = documento.createTextNode(descart);
      descripcion.appendChild(descripciontxt);
      articulo.appendChild(descripcion);
      
      Element precioCompra = documento.createElement("precioCompra");
      Text precioCompratxt = documento.createTextNode(pcart);
      precioCompra.appendChild(precioCompratxt);
      articulo.appendChild(precioCompra);
      
      
      Element precioVenta = documento.createElement("precioVenta");
      Text precioVentatxt = documento.createTextNode(pvart);
      precioVenta.appendChild(precioVentatxt);
      articulo.appendChild(precioVenta);
      
      Element unidades = documento.createElement("unidades");
      Text unidadestxt = documento.createTextNode(udsart);
      unidades.appendChild(unidadestxt);
      articulo.appendChild(unidades);
      
      articulos.appendChild(articulo);
    }
    
    documento.getDocumentElement().appendChild(articulos);
    
    Source source = new DOMSource(documento);
    
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform(source, result);
    StringBuffer sb = sw.getBuffer();
    tmpString = sb.toString();
    
    caja.setText(tmpString);
    
    formato = ".xml";
  }
  
  @FXML
  public void json(ActionEvent e) {
    Gson gson = new Gson();
    String json = gson.toJson(almacen);
    caja.setText(json);
    formato = ".json";
  }
  
  @FXML
  public void exportar(ActionEvent e) throws IOException {
    fileSaver = new FileChooser();
    fileSaver.setInitialFileName("almacen" + formato);
    
    if (formato.equals(".csv")) {
      filtro = new ExtensionFilter("Archivos CSV", "*.csv");
    } else if (formato.equals(".xml")) {
      filtro = new ExtensionFilter("Archivos XML", "*.xml");
    } else if (formato.equals(".json")) {
      filtro = new ExtensionFilter("Archivos JSON", "*.json");
    } else {
      filtro = new ExtensionFilter("Archivos de texto", "*.txt");
    }
    fileSaver.getExtensionFilters().addAll(filtro);
    ficheroGuardar = fileSaver.showSaveDialog(new Stage());
    ficheroGuardador = new BufferedWriter(new FileWriter(ficheroGuardar.getAbsolutePath()));
    ficheroGuardador.write(caja.getText());
    ficheroGuardador.close();
  }
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    almacen = GestisimalController.getAlmacen();
    
  }

}
