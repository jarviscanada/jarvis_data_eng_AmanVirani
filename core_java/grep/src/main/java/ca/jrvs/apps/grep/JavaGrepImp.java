package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public void process() throws IOException {
    List<String> insertLines = new ArrayList<>();
    for(File showFiles: listFiles(getRootPath())){
      for(String readFileLines : readLines(showFiles)){
        if(containsPattern(readFileLines)){
          insertLines.add(readFileLines);
        }
      }
    }
    writeToFile(insertLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> allFilesArrayList = new ArrayList<File>();
    File fileDirectory = new File(rootDir);
    File[] fileList = fileDirectory.listFiles();
    if (fileList != null){
      for (File insertFile : fileList){
        if (insertFile.isFile()){
          allFilesArrayList.add(insertFile);
        }else if (insertFile.isDirectory()){
          allFilesArrayList.addAll(listFiles(insertFile.getAbsolutePath()));
        }

      }
    }
    return allFilesArrayList;
  }

  @Override
  public List<String> readLines(File inputFiles) {

    List<String> readFile = new ArrayList<>();
    try{
      FileReader fileReader = new FileReader(inputFiles);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String lines;
      while ((lines=bufferedReader.readLine())!=null){
        readFile.add(lines);
      }
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      logger.error("ERROR: File not found",e);
    } catch (IOException e) {
      logger.error("ERROR: It is not a file",e);
    }
    return readFile;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(),line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {

      FileWriter fileWriter = new FileWriter(new File(getOutFile()));
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      for(String line: lines){
        bufferedWriter.write(line);
        bufferedWriter.newLine();
      }
      bufferedWriter.close();

  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) {
    if(args.length !=3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //default logger config
    BasicConfigurator.configure();

    JavaGrepImp JavaGrepImp = new JavaGrepImp();
    JavaGrepImp.setRegex(args[0]);
    JavaGrepImp.setRootPath(args[1]);
    JavaGrepImp.setOutFile(args[2]);

    try{
      JavaGrepImp.process();
    } catch (Exception ex) {
      JavaGrepImp.logger.error("ERROR: Unable to process",ex);
    }

  }
}
