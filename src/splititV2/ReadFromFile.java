// code adapted from http://pages.cs.wisc.edu/~hasti/cs368/JavaTutorial/NOTES/JavaIO_Scanner.html
package splititV2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static splititV2.Project.masterListOfProjects;

public class ReadFromFile{
  public static void read () {
    String projectName = null, errorMessage = "File read error. Manually changing the content in projectinformation.txt might have caused this. Revert any changes or delete the file.";
    String [] listOfTeamMembers;
    int numberTeamMembers = 0;
    long lineCount = 0;
    final String delims = "[ ,]+"; // used later to parse data

    Scanner inputStream = null;

    try {
      inputStream = new Scanner(new FileInputStream("projectinformation.txt"));
      lineCount = Files.lines(Paths.get("projectinformation.txt")).count(); // code adapted from user superbob at https://stackoverflow.com/questions/26448352/counting-the-number-of-lines-in-a-text-file-java
      System.out.println((int) lineCount + " previous projects detected."); // tells us how many projects there are and if there are previous projects at all
    } catch (IOException e) {
        System.out.println("No previous projects created.");
    }

    if (inputStream != null) {
      int projectCounter = 0;
      while (inputStream.hasNextLine()) {
        String data = inputStream.nextLine(); // reads each line of saved info
        String [] parsedData = data.split(delims); // this parses the data into tokens for us to use
        try {
          projectName = parsedData[0];
        } catch (Exception e) {
          System.out.println(errorMessage + "\nError: Name of Project number " + (projectCounter+1) + " missing.");
          System.exit(0);
        }
        if (!projectName.matches(".*[a-zA-Z0-9]+.*")) { // same idea as in InputCheckers
          System.out.println(errorMessage + "\nError: Name of Project number " + (projectCounter+1) + " corrupted.");
          System.exit(0);
        }
        try {
          numberTeamMembers = Integer.parseInt(parsedData[1]);
        } catch (Exception e) {
          System.out.println(errorMessage + "\nError: Number of Team Members for Project number " + (projectCounter+1) + " missing or corrupted.");
          System.exit(0);
        }
        if (parsedData.length > ((numberTeamMembers*numberTeamMembers)*2) + 2) {
          System.out.println(errorMessage + "\nError: Data stored in single line; unable to read.");
          System.exit(0);
        }
        listOfTeamMembers = new String[numberTeamMembers];
        Votes[] listOfVotes = new Votes[numberTeamMembers];
        int [] votesForOneMember = new int[numberTeamMembers - 1];
        System.out.println(projectName + ": "+ numberTeamMembers + " members.");
        for (int nameCounter = 0; nameCounter < numberTeamMembers; nameCounter++) {
          try {
            listOfTeamMembers[nameCounter] = parsedData[nameCounter + 2];
          } catch (Exception e) {
            System.out.println(errorMessage + "\nError: Name of Member number " + (nameCounter+1) + " for Project number " + (projectCounter+1) + " missing.");
            System.exit(0);
          }
          if(!listOfTeamMembers[nameCounter].matches("[a-zA-Z]+")){ // same idea as in InputCheckers
            System.out.println(errorMessage + "\nError: Name of Member number " + (nameCounter+1) + " for Project number " + (projectCounter+1) + " corrupted.");
            System.exit(0);
          }
          int dataBlock = 1 + numberTeamMembers; // this lets us skip the indexes of the data at the start
          int votesBlock = (numberTeamMembers*2 - 1); // this informs us how many indexes to skip for each set of votes
          for (int teamMemberCounter = 0; teamMemberCounter < numberTeamMembers; teamMemberCounter++) {
            int skipBlock = dataBlock + teamMemberCounter*votesBlock; // lets us skip irrelevant or already accessed info
            for (int voteBlockCounter = 0; voteBlockCounter < (numberTeamMembers-1); voteBlockCounter++) {
              // this informs us which index to take the vote data from
              // adding 3 skips the first set of names while for each team member we skip to the next integer value
              int voteIndex = skipBlock + 3 + voteBlockCounter*2;
              try {
                votesForOneMember[voteBlockCounter] = Integer.parseInt(parsedData[voteIndex]);
              } catch (Exception e) {
                System.out.println(errorMessage+ "\nError: Votes for Project number " + (projectCounter+1) + " missing or corrupted.");
                System.exit(0);
              }
            }
            listOfVotes[teamMemberCounter] = new Votes(votesForOneMember);
          }
        }
          masterListOfProjects.add(new Project(projectName, numberTeamMembers, listOfTeamMembers, listOfVotes));
        projectCounter++;
      }
      inputStream.close();
    }
  }
}