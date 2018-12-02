package com.jpmorgan.messaging.test;

import java.math.BigDecimal;

import com.jpmorgan.messaging.model.OperationType;
import com.jpmorgan.messaging.model.Product;
import com.jpmorgan.messaging.model.ProductType;
import com.jpmorgan.messaging.util.MessageParser;

import junit.framework.TestCase;

public class MessageParserTest
  extends TestCase {

  public void setUp()
    throws Exception {
    super.setUp();
  }

  public void testPrepareMessageType1()
    throws Exception {
    String message = "apple at 10p";
    Product product = MessageParser.parseMessage( message );
    assertEquals( ProductType.APPLE, product.getProductType() );
    assertEquals( 1L, product.getQuantity() );
    assertEquals( new BigDecimal( "10" ), product.getPrice() );
    assertEquals( null, product.getOperationType() );
  }

  public void testPrepareMessageType2()
    throws Exception {
    String message = "20 sales of apples at 10p each.";
    Product product = MessageParser.parseMessage( message );
    assertEquals( ProductType.APPLE, product.getProductType() );
    assertEquals( 20L, product.getQuantity() );
    assertEquals( new BigDecimal( "10" ), product.getPrice() );
    assertEquals( null, product.getOperationType() );
  }

  public void testPrepareMessageType3()
    throws Exception {
    String message = "Add 20p cherries";
    Product product = MessageParser.parseMessage( message );
    assertEquals( ProductType.CHERRY, product.getProductType() );
    assertEquals( 0L, product.getQuantity() );
    assertEquals( new BigDecimal( "20" ), product.getPrice() );
    assertEquals( OperationType.ADD, product.getOperationType() );
  }

  public void testParseMessageForWrongType()
    throws Exception {
    String message = "Many potato sales at 20p each";
    Product product = MessageParser.parseMessage( message );
    assertEquals( null, product );
  }

}