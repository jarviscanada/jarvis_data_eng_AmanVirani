package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  TwoSum s;

  @Before
  public void setUp() {
    s = new TwoSum();
  }

  @Test
  public void testTwoSum() {
    int[] nums={2,7,11,15};
    int target = 9;
    int[] expected = new int[]{0,1};
    int [] result = s.twoSum(nums,target);

    assertArrayEquals(expected,result);
  }

  @Test
  public void testOptimisedTwoSum(){
    int[] nums={2,7,11,15};
    int target = 9;
    int[] expected = new int[]{0,1};
    int [] result = s.optimisedTwoSum(nums,target);

    assertArrayEquals(expected,result);
  }
}