# Introduction
Implemented a grep app in Java. This app searches for a text pattern recursively in a given directory, and store the matched lines to a file. Use **slf4j** library for log, and regex library to search the pattern. Re-implemented the methods that return a list of all files in a given directory and a list of all strings in a file using Lambda and Stream APIs. Dockerize the grep app by docker and push the image to Docker Hub for easier distribution.
# Quick Start
```
regex_pattern=".*Romeo.*Juliet.*"
src_dir="./data"
outfile=grep_$(date +%F_%T).txt
docker run --rm \
-v `pwd`/data:/data -v `pwd`/out:/out jrvs/grep \
${regex_pattern} ${src_dir} /out/${outfile}
```
# Implemenation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
## Performance Issue
While the app is performing complex directory or file, the JVM can run out of memory. To solve this problem, we can set higher heap memory.
# Test
- Tested `listFiles` method by passing different root directory which can be complex, simple, empty to test the method.
- Tested `readLines` method by passing empty file, or directory instead of file to valid check. 
# Deployment
- Register docker hub account and get docker id(if don't have already)
- Create dockerfile
- Package java app
- Build a new docker image locally
- Verify your image
- Run docker container
- Push the image to Docker Hub

# Improvement
1. Provide feature to exclude file or directory from the given root directory.
2. Improvement in the performance issue by calling grabase collector multiple time and even changing the memory.
3. Provide other option to user instead of just storing the output to file. 