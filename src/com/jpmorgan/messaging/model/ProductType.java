package com.jpmorgan.messaging.model;

import java.util.EnumSet;

public enum ProductType {
  COCONUT( "coconut", "coconuts" ),
  APPLE( "apple", "apples" ),
  APRICOT( "apricot", "apricots" ),
  BLACKBERRY( "blackberry", "blackberries" ),
  GRAPE( "grapes", "grapes" ),
  BANANA( "banana", "bananas" ),
  MANGO( "mango", "mangoes" ),
  ORANGE( "orange", "oranges" ),
  STRAWBERRY( "strawberry", "strawberries" ),
  CHERRY( "cherry", "cherries" ),
  WATERMELON( "watermelon", "watermelons" ),
  PINEAPPLE( "pineapple", "pineapples" );

  private final String prodType;
  private final String pluralForm;

  ProductType( String prodType, String pluralForm ) {
    this.prodType = prodType;
    this.pluralForm = pluralForm;
  }

  public static ProductType getProductType( String prodType ) {
    return EnumSet.allOf( ProductType.class )
      .stream()
      .filter( operation -> prodType.equalsIgnoreCase( operation.getProdType() ) )
      .findAny()
      .orElse( null );
  }

  public static ProductType getProductTypeByPlural( String prodTypePlural ) {
    return EnumSet.allOf( ProductType.class )
      .stream()
      .filter( operation -> prodTypePlural.equalsIgnoreCase( operation.getPluralForm() ) )
      .findAny()
      .orElse( null );
  }

  public String getProdType() {
    return prodType;
  }

  public String getPluralForm() {
    return pluralForm;
  }

}
