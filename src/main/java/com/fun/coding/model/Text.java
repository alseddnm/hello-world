package com.fun.coding.model;

import java.util.Objects;

/**
 * Created by nizar on 1/12/18.
 */
public class Text {

  String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Text text = (Text) o;
    return Objects.equals(content, text.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content);
  }

}
