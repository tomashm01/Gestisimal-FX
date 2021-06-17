package gestisimal;

@SuppressWarnings("serial")
public class ArticuloNoExisteException extends Exception {
  public ArticuloNoExisteException(String msg) {
    super(msg);
  }
}
