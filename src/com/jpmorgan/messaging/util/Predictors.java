package com.jpmorgan.messaging.util;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.jpmorgan.messaging.model.Product;
import com.jpmorgan.messaging.model.ProductType;
import com.jpmorgan.messaging.model.Sale;

public class Predictors {
  public static final BiPredicate<Product, Sale> saleDataAdjustmentFilter = ( product,
    sale1 ) -> sale1.getProduct().getProductType() == product.getProductType()
      && sale1.getProduct().getOperationType() == null;

  public static final Predicate<String[]> messageType1 = splitMsg -> splitMsg.length == 3
    && ProductType.getProductType( splitMsg[0] ) != null;

  public static final Predicate<String[]> messageType2 = splitMsg -> splitMsg[0].matches( "^\\d+" )
    && splitMsg.length == 7;

  public static final Predicate<String[]> messageType3 = splitMsg -> splitMsg[0].matches( "Add|Subtract|Multiply" )
    && splitMsg.length == 3;

}
