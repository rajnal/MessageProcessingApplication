package com.jpmorgan.messaging.main;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jpmorgan.messaging.model.OperationType;
import com.jpmorgan.messaging.model.Product;
import com.jpmorgan.messaging.model.Sale;
import com.jpmorgan.messaging.util.MessageParser;
import com.jpmorgan.messaging.util.Predictors;
import com.jpmorgan.messaging.util.ReportGenerator;

public class MessageConsumer {
  private static List<Sale> sales = new ArrayList<>();

  public static void main( String[] args ) {
    try {
      final String messageSource = "SampleMessages/workingMessages.txt";
      MessageConsumer.run( messageSource );
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
  }

  public static void run( String messageSource ) {
    try (BufferedReader br = Files.newBufferedReader( Paths.get( messageSource ) )) {
      String message;
      long saleId = 1;
      while ( ( message = br.readLine() ) != null ) {

        Sale sale = onMessage( saleId, message );

        if ( sale != null ) {
          sales.add( sale );
          saleId++;
        }
        if ( saleId == 10 || saleId % 10 == 0 )
          ReportGenerator.printSalesReport( sales );

        if ( saleId == 50 ) {
          System.out
            .print( "\n\n * 50 messages processed successfully- Application Pausing now, New messages will not process \n" );
          ReportGenerator.printAdjustmentReport( sales );
          break;
        }
      }
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
  }

  public static Sale onMessage( long saleId, String message ) {
    Sale sale = null;
    Product product = MessageParser.parseMessage( message );
    if ( product != null ) {
      sale = new Sale();
      sale.setId( saleId );
      sale.setProduct( product );
      if ( product.getOperationType() == null ) {
        sale.setValue( product.getPrice().multiply( BigDecimal.valueOf( product.getQuantity() ) ) );
      } else {
        saleAdjustment( product, product.getOperationType() );
      }
    }
    return sale;
  }

  private static void saleAdjustment( Product product, OperationType operationType ) {
    if ( operationType == OperationType.ADD ) {
      sales.stream().filter( sale1 -> Predictors.saleDataAdjustmentFilter.test( product, sale1 ) ).forEach(
        sale1 -> sale1.getValue().add( product.getPrice() ) );
    }
    if ( operationType == OperationType.SUBTRACT ) {
      sales.stream().filter( sale1 -> Predictors.saleDataAdjustmentFilter.test( product, sale1 ) ).forEach(
        sale1 -> sale1.getValue().subtract( product.getPrice() ) );
    }
    if ( operationType == OperationType.MULTIPLY ) {
      sales.stream().filter( sale1 -> Predictors.saleDataAdjustmentFilter.test( product, sale1 ) ).forEach(
        sale1 -> sale1.getValue().multiply( product.getPrice() ) );
    }
  }
}
