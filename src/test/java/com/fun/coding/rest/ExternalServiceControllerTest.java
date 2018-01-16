package com.fun.coding.rest;

import java.util.List;
import com.fun.coding.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by nizar on 1/15/18.
 */
public class ExternalServiceControllerTest extends BaseRestTest{

  TestRestTemplate restTemplate = new TestRestTemplate();

  HttpHeaders headers = new HttpHeaders();

  @Test
  public void listUsersTest() throws Exception {

    HttpEntity<List<User>> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Object> response = restTemplate.exchange(
      createURL("posts"),
      HttpMethod.GET, entity, Object.class);

    List<User> users = (List<User>)response.getBody();
    String expected = "";

    Assert.assertEquals(users.size(),100);
  }

  private String createURL(String uri) {
    return "https://jsonplaceholder.typicode.com/"  + uri;
  }

}
