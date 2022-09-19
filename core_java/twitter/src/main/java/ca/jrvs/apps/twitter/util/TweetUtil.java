package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TweetUtil {
  public static Tweet buildTweet(String text, double longitude, double latitude){
    Tweet tweet = new Tweet();

    Coordinates coordinates = new Coordinates();
    List<Double> cordArrayList = new ArrayList<Double>();
    Collections.addAll(cordArrayList, longitude, latitude);
    coordinates.setCoordinates(cordArrayList);
    coordinates.setType("Point");
    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }
}
