package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class climbStairsTest {

  climbStairs cb;

  @Before
  public void setup(){
    cb=new climbStairs();
  }

  @Test
  public void dynamicClimbStairs() {
    int input = 3;
    int expected = 3;
    int actual = cb.dynamicClimbStairs(input);

    assertEquals(expected,actual);
  }
}