
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.quotes.StockQuoteServiceFactory;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

  private RestTemplate restTemplate;
  StockQuotesService stockQuotesService;
  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
  }




  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF

  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
    if (candles.size() > 0){
    return candles.get(0).getOpen();
    }
    return 0.0;
 }


 public static Double getClosingPriceOnEndDate(List<Candle> candles) {
  if (candles.size() > 0)
    return candles.get(candles.size()-1).getClose();
    return 0.0;
 }

 public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
 PortfolioTrade trade, Double buyPrice, Double sellPrice) {
   double totalReturns= (sellPrice-buyPrice)/buyPrice;
   double years= ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate) / 365.2422;
   double annualized_return = 0.0;       
    if (years > 0.0){
   annualized_return = Math.pow((1.0+ totalReturns), 1.0/years) -1 ;
    }
 
   return new AnnualizedReturn(trade.getSymbol(),annualized_return, totalReturns);
 }

  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
  LocalDate endDate) throws StockQuoteServiceException {
    List<AnnualizedReturn> listResult = new ArrayList<AnnualizedReturn>();
    for(int i = 0; i < portfolioTrades.size(); i++)
    {
      PortfolioTrade trade = portfolioTrades.get(i);
     try{
      List<Candle> listCandles = getStockQuote(trade.getSymbol(), trade.getPurchaseDate(), endDate);
      Double buyPrice = getOpeningPriceOnStartDate(listCandles);
      Double sellPrice = getClosingPriceOnEndDate(listCandles);
      AnnualizedReturn ret =  calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);
     
      listResult.add(ret);
    }catch(JsonProcessingException  e){e.printStackTrace(); throw new StockQuoteServiceException();}
    }
   


    //You should sort the listResult based on the annualizedReturn
    return listResult.stream()
    .sorted(getComparator()) //descending order
    .collect(Collectors.toList());
  }

  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  private Comparator<Candle> getComparator2() {
    return Comparator.comparing(Candle::getOpen).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
        List<Candle> listCandle = new ArrayList<>();
        try{
        listCandle = this.stockQuotesService.getStockQuote(symbol, from, to);
        }catch(JsonProcessingException|StockQuoteServiceException e){}
        // String url = buildUri(symbol, from, to);
        // //System.out.println(url);
        // String apiResult = restTemplate.getForObject(url, String.class);
        // ObjectMapper mapper = getObjectMapper();
        // TiingoCandle [] candlesArr = mapper.readValue(apiResult, TiingoCandle[].class);
        // for (TiingoCandle tiingoCandle : candlesArr) {
        //   listCandle.add(tiingoCandle);
        // }
     return listCandle;
  }

  public static String getToken() {
    String token = "cdbaaca3b1b99df3a608793cdf25658f96531eb8";
    return token;
}

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String apiKey = getToken();
       String uriTemplate = "https:api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate="+startDate+"&endDate="+endDate+"&token="+apiKey;
            return uriTemplate;
  }


  // ¶TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.






}
