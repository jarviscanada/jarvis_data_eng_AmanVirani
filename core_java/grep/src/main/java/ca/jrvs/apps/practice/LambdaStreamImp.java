package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc{

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    Stream<String> stream = createStrStream(strings);
    return stream.map(value -> value.toUpperCase());
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(character -> !character.contains(pattern));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    IntStream intStream = Arrays.stream(arr);
    return intStream;
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    List<Integer> toList = intStream.boxed().collect(Collectors.toList());
    return toList;
  }
/**rangeClosed(int start, int end) ?
 * Returns a sequential ordered int stream from
 * startInclusive (inclusive) to endInclusive (inclusive)
 * by an incremental step of 1.
 * example 1,2,3,4,5 if 1 and 5 id start and end
 */
  @Override
  public IntStream createIntStream(int start, int end) {
    IntStream intStream = IntStream.rangeClosed(start, end);
    return intStream;
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    DoubleStream doubleStream = intStream.asDoubleStream();
    return doubleStream.map(value->Math.sqrt(value));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(value->value % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return value -> System.out.println(prefix + value + suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    String[] strings = getOdd(intStream).mapToObj(v->String.valueOf(v)).toArray(String[]::new);
    printMessages(strings,printer);
  }

  public static void main(String[] args) {
    LambdaStreamExc lse = new LambdaStreamImp();
    Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
    printer.accept("Message body");
    String[] messages = {"a","b", "c"};
    lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
    lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
  }
}
