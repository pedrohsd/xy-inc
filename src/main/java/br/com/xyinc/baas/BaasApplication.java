package br.com.xyinc.baas;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BaasApplication {

  public static final String SERVICE_NAME = "baas";

  public static final String BASE_PACKAGE = BaasApplication.class.getPackage().toString();

  public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  public static void main(String[] args) {
    SpringApplication.run(BaasApplication.class, args);
  }


}
