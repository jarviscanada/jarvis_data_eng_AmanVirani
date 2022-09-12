package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TwitterApiTest {

  private static String CONSUMER_KEY = System.getenv("consumerKey");
  private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static String ACCESS_TOKEN = System.getenv("accessToken");
  private static String TOKEN_SECRET = System.getenv("tokenSecret");


  public static void main(String[] args) throws Exception {

    // setup oAuth
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

    consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

    String status = "Testing twitter api through java app";
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    HttpPost requests = new HttpPost(
        "https://api.twitter.com/1.1/statuses/update.json?status=" + percentEscaper.escape(status));

    consumer.sign(requests);

    System.out.println("Http Request Header: ");
    Arrays.stream(requests.getAllHeaders()).forEach(System.out::println);

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpResponse httpResponse = httpClient.execute(requests);
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));

//    Map<String, String> map = System.getenv();
//    map.entrySet().forEach(System.out::println);
  }
}