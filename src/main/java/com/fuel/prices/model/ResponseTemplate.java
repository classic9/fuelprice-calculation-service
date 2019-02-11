package com.fuel.prices.model;

import lombok.Data;

@Data
public class ResponseTemplate<T> {

  private T data;
  private String error;
}
