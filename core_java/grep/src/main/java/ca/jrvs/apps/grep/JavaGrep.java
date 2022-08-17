package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   *
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverese a given directory and return all files
   * @param rootDir input directory
   * @return file under root directory
   */

  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   * @param inputFiles file to be read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  List<String> readLines(File inputFiles);

  /**
   * check if line contains regex pattern (passed by user)
   * @param line input string
   * @return true if there is a match
   */

  boolean containsPattern(String line);

  void writeToFile(List<String> lines) throws IOException;
  String getRootPath();
  void setRootPath(String rootPath);
  String getRegex();
  void setRegex(String regex);
  String getOutFile();
  void setOutFile(String outFile);


}
