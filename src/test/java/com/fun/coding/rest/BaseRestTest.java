package com.fun.coding.rest;

import java.io.IOException;
import java.nio.charset.Charset;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by nizar on 1/15/18.
 */
public class BaseRestTest {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
    Charset.forName("utf8"));

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  protected MockMvc mockMvc;

  protected void setup() throws Exception {
  }

  /**
   * Returns json representation of the object.
   * @param o instance
   * @return json
   * @throws IOException
   */
  protected String json(Object o) throws IOException {

    return objectMapper.writeValueAsString(o);
  }

}
