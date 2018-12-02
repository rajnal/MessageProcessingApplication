package com.jpmorgan.messaging.util;

import java.math.BigDecimal;

import com.jpmorgan.messaging.model.OperationType;
import com.jpmorgan.messaging.model.Product;
import com.jpmorgan.messaging.model.ProductType;

public class MessageParser {

  private MessageParser() {
    throw new IllegalStateException( "Utility class" );
  }

  public static Product parseMessage( String message ) {
    Product product = null;

    try {
      if ( message != null && !message.isEmpty() ) {
        String[] splitMsg = message.trim().split( "\\s+" );
        if ( Predictors.messageType1.test( splitMsg ) ) {
          product = prepareMessageType1( splitMsg );
        } else if ( Predictors.messageType2.test( splitMsg ) ) {
          product = prepareMessageType2( splitMsg );
        } else if ( Predictors.messageType3.test( splitMsg ) ) {
          product = prepareMessageType3( splitMsg );
        } else {
          System.out.println( "This message type is not supported-" + message );
        }
      } else {
        System.out.println( "Message can not be blank or empty" );
      }
    } catch( Exception e ) {
      System.out.println( "This message type is not supported - " + message );
    }

    return product;
  }

  private static Product prepareMessageType1( String[] splitMsg ) {
    Product product = new Product();
    product.setProductType( ProductType.getProductType( ( splitMsg[0] ) ) );
    product.setPrice( parseSalePrice( splitMsg[2] ) );
    product.setQuantity( 1 );
    return product;
  }

  private static Product prepareMessageType2( String[] splitMsg ) {
    Product product = new Product();
    product.setProductType( ProductType.getProductTypeByPlural( splitMsg[3] ) );
    product.setPrice( parseSalePrice( splitMsg[5] ) );
    product.setQuantity( Long.parseLong( splitMsg[0] ) );
    return product;
  }

  private static Product prepareMessageType3( String[] splitMsg ) {
    Product product = new Product();
    product.setOperationType( OperationType.getOpType( splitMsg[0] ) );
    product.setProductType( ProductType.getProductTypeByPlural( splitMsg[2] ) );
    product.setPrice( parseSalePrice( splitMsg[1] ) );
    return product;
  }

  public static BigDecimal parseSalePrice( String salePrice ) {
    return new BigDecimal( salePrice.replaceFirst( "p", "" ) );
  }
}