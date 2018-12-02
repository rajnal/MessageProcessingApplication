package com.jpmorgan.messaging.test;

import java.math.BigDecimal;

import com.jpmorgan.messaging.main.MessageConsumer;
import com.jpmorgan.messaging.model.Sale;

import junit.framework.TestCase;

public class MessageConsumerTest
  extends TestCase {
  public void setUp()
    throws Exception {
    super.setUp();
  }

  public void testOnMessage()
    throws Exception {
    String message = "apple at 10p";
    Sale sale = MessageConsumer.onMessage( 40L, message );
    assertEquals( 40L, sale.getId() );
    assertEquals( 1L, sale.getProduct().getQuantity() );
    assertEquals( new BigDecimal( "10" ), sale.getProduct().getPrice() );
    assertEquals( null, sale.getProduct().getOperationType() );
    assertEquals( new BigDecimal( "10" ), sale.getValue() );
  }

  public void testOnMessageFail()
    throws Exception {
    String message = "apple at price 10p";
    Sale sale = MessageConsumer.onMessage( 40L, message );
    assertEquals( null, sale );
  }

}
