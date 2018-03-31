package splititV2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static splititV2.Project.masterListOfProjects;

public class ReadFromFile{
  public static void read () {
    String projectName;
    String [] listOfTeamMembers;
    int numberTeamMembers, projectCounter = 0;
    long lineCount = 0;
    final String delims = "[ ,]+"; // used later to parse data

    Scanner inputStream = null;

    try {
      inputStream = new Scanner(new FileInputStream("projectinformation.txt"));
      lineCount = Files.lines(Paths.get("projectinformation.txt")).count(); // code adapted from user superbob at https://stackoverflow.com/questions/26448352/counting-the-number-of-lines-in-a-text-file-java
      System.out.println((int) lineCount + " previous projects detected.");
    } catch (IOException e) {
        System.out.println("No previous projects created.");
    }

    if (inputStream != null) {
      while (inputStream.hasNextLine()) {
        String data = inputStream.nextLine(); // reads each line of saved info
        String [] parsedData = data.split(delims); // this parses the data into tokens for us to use
        projectName = parsedData[0];
        numberTeamMembers = Integer.parseInt(parsedData[1]);
        listOfTeamMembers = new String[numberTeamMembers];
        Votes[] listOfVotes = new Votes[numberTeamMembers];
        int [] votesForOneMember = new int[numberTeamMembers - 1];
        System.out.println(projectName + ": "+ numberTeamMembers + " members.");
        System.out.println(Arrays.toString(parsedData));
        for (int nameCounter = 0; nameCounter < numberTeamMembers; nameCounter++) {
          listOfTeamMembers[nameCounter] = parsedData[nameCounter + 2];
          int dataBlock = 1 + numberTeamMembers; // this lets us skip the indexes of the data at the start
          int votesBlock = (numberTeamMembers*2 - 1); // this informs us how many indexes to skip for each set of votes
          for (int teamMemberCounter = 0; teamMemberCounter < numberTeamMembers; teamMemberCounter++) {
            int skipBlock = dataBlock + teamMemberCounter*votesBlock; // lets us skip irrelevant or already accessed info
            for (int voteBlockCounter = 0; voteBlockCounter < (numberTeamMembers-1); voteBlockCounter++) {
              // this informs us which index to take the vote data from
              // adding 3 skips the first set of names while for each team member we skip to the next integer value
              int voteIndex = skipBlock + 3 + voteBlockCounter*2;
              votesForOneMember[voteBlockCounter] = Integer.parseInt(parsedData[voteIndex]);
            }
            listOfVotes[teamMemberCounter] = new Votes(votesForOneMember);
          }
        }
          masterListOfProjects.add(new Project(projectName, numberTeamMembers, listOfTeamMembers, listOfVotes));
      }
      inputStream.close();
    }
  }
}