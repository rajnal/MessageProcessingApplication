package com.jpmorgan.messaging.model;

import java.math.BigDecimal;

import com.jpmorgan.messaging.model.Product;

public class Sale {
  private long id;
  private Product product;
  private BigDecimal value;

  public Sale() {
  }

  public Sale( long id, Product product, BigDecimal value ) {
    this.id = id;
    this.product = product;
    this.value = value;
  }

  public long getId() {
    return id;
  }

  public void setId( long id ) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct( Product product ) {
    this.product = product;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue( BigDecimal value ) {
    this.value = value;
  }

}
