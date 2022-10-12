package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibonacciNumberTest {

  FibonacciNumber fn;

  @Before
  public void setUp() throws Exception {
    fn= new FibonacciNumber();
  }

  @Test
  public void fibRecursively() {
    int input = 3;
    int expected = 2;
    int actual = fn.fibRecursively(input);

    assertEquals(expected,actual);
  }

  @Test
  public void dynamicBottomUpFib(){
    int input = 3;
    int expected = 2;
    int actual = fn.dynamicBottomUpFib(input);

    assertEquals(expected,actual);
  }
}