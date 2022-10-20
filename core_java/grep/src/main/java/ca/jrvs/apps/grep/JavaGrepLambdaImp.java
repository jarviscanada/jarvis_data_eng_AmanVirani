package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    try(Stream<Path> stream = Files.walk(Paths.get(rootDir))){
      return stream.filter(file->!Files.isDirectory(file))
          .map(Path::getFileName)
          .map(Path::toFile)
          .collect(Collectors.toList());

    } catch (IOException ex) {
      return fileList;
    }
  }

  @Override
  public List<String> readLines(File inputFiles) {

    List<String> arrayList = new ArrayList<>();
    try(Stream<String> stream = Files.lines(inputFiles.toPath())){
      return stream.collect(Collectors.toList());
    } catch(Exception ex){
      return arrayList;
    }
  }

  public static void main(String[] args) {
    if (args.length != 3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    //creating JavaGrepLambdaImp instead of JavaGrepImp
    //JavaGrepLambdaImp inherits all methods except two override methods
    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try{
      //calling parent method
      //but it will call override method (in this class)
      javaGrepLambdaImp.process();;
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
