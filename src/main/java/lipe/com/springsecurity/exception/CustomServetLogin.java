package lipe.com.springsecurity.exception;

public class CustomServetLogin extends RuntimeException {

  public CustomServetLogin(String message) {
    super(message);
  }

  public CustomServetLogin(String message, Throwable cause) {
    super(message, cause);
  }

}
