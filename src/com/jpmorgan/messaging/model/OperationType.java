package com.jpmorgan.messaging.model;

import java.util.EnumSet;

public enum OperationType {
  ADD( "Add" ),
  SUBTRACT( "Subtract" ),
  MULTIPLY( "Multiply" );

  private final String opType;

  OperationType( String code ) {
    this.opType = code;
  }

  public static OperationType getOpType( String opType ) {
    return EnumSet.allOf( OperationType.class )
      .stream()
      .filter( operation -> opType.equals( operation.getOpType() ) )
      .findFirst()
      .orElse( null );
  }

  public String getOpType() {
    return opType;
  }

}
