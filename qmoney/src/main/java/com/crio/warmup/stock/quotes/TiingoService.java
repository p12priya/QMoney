
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

  private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  protected static String getToken() {
    String token = "cdbaaca3b1b99df3a608793cdf25658f96531eb8";
    return token;
}

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String apiKey = getToken();
       String uriTemplate = "https:api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate="+startDate+"&endDate="+endDate+"&token="+apiKey;
            return uriTemplate;
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)throws JsonProcessingException {
      List<Candle> listCandle = new ArrayList<Candle>();
      String url = buildUri(symbol, from, to);
      //System.out.println(url);
      String apiResult = restTemplate.getForObject(url, String.class);
      ObjectMapper mapper = getObjectMapper();
      TiingoCandle [] candlesArr = mapper.readValue(apiResult, TiingoCandle[].class);
      for (TiingoCandle tiingoCandle : candlesArr) {
        listCandle.add(tiingoCandle);
      }
   return listCandle;
}
  


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.

}
