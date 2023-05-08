package com.crio.warmup.stock.portfolio;

public class StockQuoteServiceException extends Exception{
    public StockQuoteServiceException(){};
    public StockQuoteServiceException(String message) {
        super(message);
    }
}
