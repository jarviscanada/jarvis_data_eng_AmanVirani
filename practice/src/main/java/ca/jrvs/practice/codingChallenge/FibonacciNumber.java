package ca.jrvs.practice.codingChallenge;

public class FibonacciNumber {

  public int fibRecursively(int n){
    if(n==1 || n==2){
      return 1;
    }else if(n<1){
      return 0;
    }
    else {
      return fibRecursively(n-1) + fibRecursively(n-2);
    }
  }

  public int dynamicBottomUpFib(int n){
    int[] arrayFib = new int[n+2];
    arrayFib[0] = 0;
    arrayFib[1] = 1;
    for (int i=2;i<=n;i++){
      arrayFib[i] = arrayFib[i-1]+arrayFib[i-2];
    }
    return arrayFib[n];
  }

}
