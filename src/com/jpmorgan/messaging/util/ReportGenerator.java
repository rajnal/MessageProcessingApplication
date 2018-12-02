package com.jpmorgan.messaging.util;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jpmorgan.messaging.model.ProductType;
import com.jpmorgan.messaging.model.Sale;

public class ReportGenerator {

  private ReportGenerator() {
    throw new IllegalStateException( "Utility class" );
  }

  public static void printSalesReport( List<Sale> sales ) {
    Map<ProductType, List<Sale>> productWiseSales = getReportData( sales, "salesReport" );

    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    System.out.println( "                         Sales Report at " + ( sales.size() + 1 ) + " sales" );
    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    System.out.printf( "%-30s %-30s %5s %n", "Product", "Number Of Sales", "Total Value" );
    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    for( Entry<ProductType, List<Sale>> productSale : productWiseSales.entrySet() ) {
      System.out.printf(
        "%-30s %-30s %5s %n", productSale.getKey(), productSale.getValue().size(),
        productWiseTotalValue( productSale.getValue() ) );
    }
  }

  public static void printAdjustmentReport( List<Sale> sales ) {
    Map<ProductType, List<Sale>> productTypeWithSale = getReportData( sales, "adjustmentReport" );

    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    System.out.println( "                         Adjustment Report at " + ( sales.size() + 1 ) + " sales" );
    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    System.out.printf( "%-30s %-30s %5s %n", "Product", "Adjustment Type", "Adjustment Price" );
    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );

    for( Entry<ProductType, List<Sale>> productSale : productTypeWithSale.entrySet() ) {
      List<Sale> saleRec = productSale.getValue();
      for( Sale sale : saleRec ) {
        System.out.printf(
          "%-30s %-30s %5s %n", productSale.getKey(), sale.getProduct().getOperationType(),
          sale.getProduct().getPrice() );
      }
    }
    System.out.println(
      "-------------------------------------------------------------------------------------------------------------------" );
    System.out.println(
      "------------------------End of Adjustment Report-------------------------------------------------------------------" );
  }

  private static BigDecimal productWiseTotalValue( List<Sale> sales ) {
    return sales.stream().map( sale -> sale.getValue() ).reduce( BigDecimal::add ).get();

  }

  private static Map<ProductType, List<Sale>> getReportData( List<Sale> sales, String reportType ) {
    Map<ProductType, List<Sale>> productWiseSales = new HashMap<>();
    if ( "salesReport".equals( reportType ) ) {
      productWiseSales = sales.stream().filter( sale -> sale.getProduct().getOperationType() == null ).collect(
        groupingBy( sale -> sale.getProduct().getProductType() ) );
    } else if ( "adjustmentReport".equals( reportType ) ) {
      productWiseSales = sales.stream().filter( sale -> sale.getProduct().getOperationType() != null ).collect(
        groupingBy( sale -> sale.getProduct().getProductType() ) );
    }
    return productWiseSales;
  }

}