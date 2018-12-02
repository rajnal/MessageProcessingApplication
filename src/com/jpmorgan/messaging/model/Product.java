package com.jpmorgan.messaging.model;

import java.math.BigDecimal;

public class Product {
  private ProductType productType;
  private OperationType operationType;
  private long quantity;
  private BigDecimal price;

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType( ProductType productType ) {
    this.productType = productType;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public void setOperationType( OperationType operationType ) {
    this.operationType = operationType;
  }

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity( long quantity ) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice( BigDecimal price ) {
    this.price = price;
  }
}
