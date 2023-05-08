
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.crio.warmup.stock.portfolio.StockQuoteServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.logging.log4j.ThreadContext;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



public class PortfolioManagerApplication {



  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.


  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
      // create object mapper instance
      ObjectMapper mapper = getObjectMapper();
      String filename = args[0];
      File file = resolveFileFromResources(filename);
  
      // List<PortfolioTrade> trades = Arrays.asList(mapper.readValue(file, PortfolioTrade[].class));
      PortfolioTrade[] trades=mapper.readValue(file, PortfolioTrade[].class);

      for(PortfolioTrade obj: trades){
        System.out.println(obj.toString());
      }

      List<String> listSymbols= new ArrayList<String>(); 
    
      for(PortfolioTrade obj: trades){
        listSymbols.add(obj.getSymbol());
      }

      return listSymbols;
    }
  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.

  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {
    int a=9;

    float b = a/2;
    
    System.out.println(b);
    
     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/pushpanjalipriya0007-ME_QMONEY_V2/qmoney/bin/main/trades.json";

     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@3fb1549b";
     String functionNameFromTestFileInStackTrace = "mainReadFile";
     String lineNumberFromTestFileInStackTrace = "";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    List<PortfolioTrade> listTrades = readTradesFromJson(args[0]);
    List<String> result = new ArrayList<String>();
    Map<String, Double> mapCandle = new HashMap<String, Double>();

    String token = getToken();
    for(int i = 0; i < listTrades.size(); i++)
    {
      PortfolioTrade trade = listTrades.get(i);
      LocalDate endDate = LocalDate.parse(args[1]);

      String url = prepareUrl(trade, endDate, token);
      //System.out.println(url);
      RestTemplate restTemplateObj = new RestTemplate();
      try{
      String apiResult = restTemplateObj.getForObject(url, String.class);
      ObjectMapper mapper = getObjectMapper();

      TiingoCandle [] candlesArr = mapper.readValue(apiResult, TiingoCandle[].class);

      TiingoCandle lastCandle = candlesArr[candlesArr.length-1];
      mapCandle.put(trade.getSymbol(),lastCandle.getClose());
     // System.out.println(apiResult);
    }catch(HttpClientErrorException e){System.out.println("Exceeds Number of request  limit");} }
    Map<String, Double> sortedByPrice = mapCandle.entrySet() .stream() .sorted(Map.Entry.<String, Double>comparingByValue()) .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    for (String key : sortedByPrice.keySet()) {
    result.add(key);
    }
 
           return result;
  }

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
   ObjectMapper mapper = getObjectMapper();
   File file = resolveFileFromResources(filename);

   // List<PortfolioTrade> trades = Arrays.asList(mapper.readValue(file, PortfolioTrade[].class));
   PortfolioTrade[] trades=mapper.readValue(file, PortfolioTrade[].class);
   List<PortfolioTrade> list = new ArrayList<PortfolioTrade>();
   for(PortfolioTrade obj: trades){
      list.add(obj);
   }
   return list;
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String url = "https://api.tiingo.com/tiingo/daily/"+trade.getSymbol()+"/prices?startDate="+trade.getPurchaseDate()+"&endDate="+endDate+"&token="+token; 

     return url;
  }

  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
     return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
     return candles.get(candles.size()-1).getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    List<Candle> listCandle = new ArrayList<Candle>();
    String url = prepareUrl(trade, endDate, token);
    //System.out.println(url);
    RestTemplate restTemplateObj = new RestTemplate();
    String apiResult = restTemplateObj.getForObject(url, String.class);
    ObjectMapper mapper = getObjectMapper();
    try{
    TiingoCandle [] candlesArr = mapper.readValue(apiResult, TiingoCandle[].class);
    for (TiingoCandle tiingoCandle : candlesArr) {
      listCandle.add(tiingoCandle);
    }
  }catch(JsonProcessingException  e){System.out.println("JSON Processing Exception");}
     return listCandle;
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
        List<AnnualizedReturn> listResult = new ArrayList<AnnualizedReturn>();
        List<PortfolioTrade> listTrades = readTradesFromJson(args[0]);
    
        String token = getToken();
        for(int i = 0; i < listTrades.size(); i++)
        {
          PortfolioTrade trade = listTrades.get(i);
          LocalDate endDate = LocalDate.parse(args[1]);
    
          List<Candle> listCandles = fetchCandles(trade, endDate, token);
          Double buyPrice = getOpeningPriceOnStartDate(listCandles);
          Double sellPrice = getClosingPriceOnEndDate(listCandles);
          AnnualizedReturn ret =  calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);
      
          listResult.add(ret);
        }
        //You should sort the listResult based on the annualizedReturn
        return listResult.stream()
        .sorted((a1, a2) -> Double.compare(a2.getAnnualizedReturn(), a1.getAnnualizedReturn())) //descending order
        .collect(Collectors.toList());
     
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
        double totalReturns= (sellPrice-buyPrice)/buyPrice;
        double years= ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate) / 365.2422;
        double annualized_return = 0.0;       
         if (years > 0.0){
        annualized_return = Math.pow((1.0+ totalReturns), 1.0/years) -1 ;
         }
      
        return new AnnualizedReturn(trade.getSymbol(),annualized_return, totalReturns);
  
      //   Double totalReturns = (sellPrice-buyPrice)/buyPrice;
      //   Double annualizedReturn  = 0.0;
    
      //   double years= ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate) / 365.2422;       
      //    if (years > 0){
      //   annualizedReturn = Math.pow(1+totalReturns, (1/years)-1);
      // }
      //   return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturns);
      }


  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(file);
       ObjectMapper objectMapper = getObjectMapper();
       PortfolioTrade[] portfolioTrades =  objectMapper.readValue(contents, PortfolioTrade[].class);
       RestTemplate restTemplate = new RestTemplate();
       PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
       List<AnnualizedReturn> lAnnualizedReturns = new ArrayList<>();
       try{
      lAnnualizedReturns =  portfolioManager.calculateAnnualizedReturn(Arrays.asList(portfolioTrades), endDate);
       }catch(StockQuoteServiceException e){};
      return lAnnualizedReturns;
  }


  private static String readFileAsString(String file) {
    return null;
  }

  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadFile(args));


    printJsonObject(mainReadQuotes(args));



    printJsonObject(mainCalculateSingleReturn(args));

  }


  public static String getToken() {
    String token = "cdbaaca3b1b99df3a608793cdf25658f96531eb8";
    return token;
}
}

