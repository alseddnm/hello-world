package com.fun.coding.rest;

import java.util.List;
import com.fun.coding.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nizar on 1/13/18.
 */
@RestController
@RequestMapping("/users")
public class ExternalServiceController {

  final String uri = "https://jsonplaceholder.typicode.com/posts";

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<User>> listUsers() {
    RestTemplate restTemplate = new RestTemplate();
    Object result = restTemplate.getForObject(uri, Object.class);
    if (result == null) {
      return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<>((List<User>) result, HttpStatus.OK);
  }
}
