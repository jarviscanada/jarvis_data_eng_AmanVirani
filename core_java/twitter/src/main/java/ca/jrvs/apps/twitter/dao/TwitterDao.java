package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.JsonParser;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  //URI symbol
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }


  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;
    int status = response.getStatusLine().getStatusCode();
    if(status != expectedStatusCode){
      try{
        System.out.println(EntityUtils.toString(response.getEntity()));
      }catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("UnExpected HTTP status:"+status);
    }
    if (response.getEntity() == null){
      throw new RuntimeException("Empty response body");
    }
    //converting response entity to str
    String jsonStr;
    try{
      jsonStr = EntityUtils.toString(response.getEntity());
    }catch (IOException e){
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    //converting json str to object
    try{
      tweet = JsonParser.toObjectFromJSON(jsonStr, Tweet.class);
    }catch (IOException e){
      throw new RuntimeException("Unable to convert JSON str to object", e);
    }
    return tweet;
  }

  private URI getPostUri(Tweet tweet) throws URISyntaxException {
    PercentEscaper percentEscaper = new PercentEscaper("",false);
    URI uri = new URI(API_BASE_URI+POST_PATH+QUERY_SYM+"status"+EQUAL+percentEscaper.escape(tweet.getText())
    +AMPERSAND+"long"+EQUAL+tweet.getCoordinates().getCoordinates().get(0).toString()
    +AMPERSAND+"lat"+EQUAL+tweet.getCoordinates().getCoordinates().get(1).toString());
    return uri;
  }

  @Override
  public Tweet create(Tweet tweet) {
    URI uri;
    try{
      uri = getPostUri(tweet);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input", e);

    }

    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }
  @Override
  public Tweet findById(String id) {
    URI uri;
    try{
      uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM
          + "id" + EQUAL + id);
    }catch (URISyntaxException e){
      throw new RuntimeException("Unable to find URI", e);
    }
    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponseBody(response, HTTP_OK);  }

  @Override
  public Tweet deleteById(String id) {
  URI uri;
  try{
    uri = new URI(API_BASE_URI+DELETE_PATH+"/"+id+".json");
  } catch (URISyntaxException e){
    throw new RuntimeException("Unable to delete tweet", e);
  }
  HttpResponse response = httpHelper.httpGet(uri);
  return parseResponseBody(response,HTTP_OK);
  }
}
