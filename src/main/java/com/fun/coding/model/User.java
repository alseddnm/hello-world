package com.fun.coding.model;

import java.util.Objects;

/**
 * Created by nizar on 1/13/18.
 */
public class User implements Comparable<User>{

  private int userId;

  private int id;

  private String title;

  private String body;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userId == user.userId &&
      id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, id);
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public int compareTo(User o) {
    return this.getId() - o.getId();
  }
}
