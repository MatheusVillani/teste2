package com.example.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${bezkoder.openapi.dev-url}")
  private String devUrl;

  @Value("${bezkoder.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Projeto Desenvolvimento de Sistemas, API");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Projeto Desenvolvimento de Sistemas, API");

    Contact contact = new Contact();
    contact.setEmail("3232323232@mackenzista.com");
    contact.setName("Matheus e Vitor");
    

  

    Info info = new Info()
        .title("Documentação API")
        .version("1.0")
        .contact(contact)
        .description("Documentamos a API do nosso Projeto.");
    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}