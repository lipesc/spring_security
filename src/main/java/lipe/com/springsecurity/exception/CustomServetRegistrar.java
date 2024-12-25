package lipe.com.springsecurity.exception;

public class CustomServetRegistrar extends RuntimeException {

  

  public CustomServetRegistrar(String message) {
    super(message);
  }

  public CustomServetRegistrar(String message, Throwable cause){
    super(message, cause);
  }
}
