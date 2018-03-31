package splititV2;

/*
* The purpose of this class is to create an object type Project which we can use to store our relevant project information.
* As of now Votes is a separate class as we were fiddling with ways to store voting / voter information (meaning that
* the Vote class used to contain more information types) thus we will work on improving this in the next deliverable
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Project {
  private static Scanner in = new Scanner(System.in);

  private String projectName;
  private int numberTeamMembers;
  private String[] listOfTeamMembers;
  private Votes[] listOfVoteLists;

  // This stores the master list of projects
  protected static ArrayList<Project> masterListOfProjects = new ArrayList<>(10);

  // Constructor for the Project class
  public Project(String aProjectName, int aNumberOfTeamMembers, String[] aListOfTeamMembers, Votes[] aListOfVoteLists) {
    this.projectName = aProjectName;
    this.numberTeamMembers = aNumberOfTeamMembers;
    this.listOfTeamMembers = aListOfTeamMembers;
    this.listOfVoteLists = aListOfVoteLists;
  }

  public String getProjectName() {
    return projectName;
  }

  public int getNumberTeamMembers() {
    return numberTeamMembers;
  }

  public String[] getListOfTeamMembers() {
    return listOfTeamMembers;
  }

  public Votes[] getListOfVoteLists() {
    return listOfVoteLists;
  }

  public void setListOfVoteLists(Votes[] newListOfVoteLists) {
    this.listOfVoteLists = newListOfVoteLists;
  }

  public static ArrayList<Project> getMasterListOfProjects() {
    return masterListOfProjects;
  }

  // This method creates a blank project from which the user will enter the votes for their team.
  public static void createProject() {
    String newProjectName;
    String createAnotherProject;
    String[] newListOfTeamMembers;
    int newNumberTeamMembers = 0;
    boolean programmeRunning = true;
    boolean optionChecker = false;

    while (programmeRunning) {
      System.out.print("\nEnter the project name: ");
      newProjectName = in.next();

      System.out.print("\nEnter the number of team members: ");

      // Following included to ensure that only integer inputs are accepted

      boolean validInput = false;
      while (!validInput) {
        try {
          newNumberTeamMembers = in.nextInt();
          validInput = true;
        } catch (Exception e) {
          in.next();
          System.out.print("\tPlease enter a valid integer: ");
        }
      }

       newListOfTeamMembers = new String[newNumberTeamMembers];

      // Receives as many team member names as there are team members
      for (int numberOfTeamCounter = 0; numberOfTeamCounter < newNumberTeamMembers; numberOfTeamCounter++) {
        System.out.print("\n\tEnter the first name of team member " + (numberOfTeamCounter + 1) + ": ");
        newListOfTeamMembers[numberOfTeamCounter] = in.next();
      }

      // This creates a list of vote lists
      Votes[] newListOfVoteLists = new Votes[newNumberTeamMembers];

      // Appends all this information to the masterListOfProjects ArrayList
      masterListOfProjects.add(new Project(newProjectName, newNumberTeamMembers, newListOfTeamMembers, newListOfVoteLists));

      // Checks if the user wants to create another project and only accepts 'y' or 'n'
      while (!optionChecker) {
        System.out.print("\nWould you like to create a new project? (y/n) ");
        createAnotherProject = in.next().toLowerCase();

        switch (createAnotherProject) {
          case "y":
            programmeRunning = true;
            optionChecker = true;
            break;
          case "n":
            PressEnterToExit.pressEnterToExit();
            optionChecker = true;
            programmeRunning = false;
            break;
          default:
            System.out.print("\nUnknown command, please try again.");
            break;
        }
      }
    }
  }

  // Following method searches through the list of projects to see whether it contains a project of a certain name
  // Method adapted from user Josh M at:
  // https://stackoverflow.com/questions/18852059/java-list-containsobject-with-field-value-equal-to-x
  public static boolean containsProjectName(final ArrayList<Project> list, final String aProjectName){
    return list.stream().anyMatch(o -> o.getProjectName().equals(aProjectName));
  }

  // This method allows the user to enter votes for a given project.
  public static void enterVotes() {
    Scanner in = new Scanner(System.in);

    final int MAX_SCORE = 100;

    String existingProjectName;
    String voteAgain;
    String nameOfVoter;
    int voteCounter;
    int votesListCounter;
    int voteChecker;
    boolean programmeRunning = true;

    while (programmeRunning) {
      boolean optionChecker = false;

      System.out.print("\nEnter the project name: ");
      existingProjectName = in.next();

      if (!containsProjectName(masterListOfProjects, existingProjectName)) {
        System.out.println("There is no project with that name.");
      }

      // This for loop will run through every project that has been created and check if the entered project name exists
      for (Project existingProject : masterListOfProjects) {
        if (existingProjectName.equals(existingProject.getProjectName())) {
          String[] nameOfCurrentMember = existingProject.getListOfTeamMembers();

          Votes[] completedListOfVoteLists = new Votes[existingProject.getNumberTeamMembers()];

          System.out.println("\nThere are " + existingProject.getNumberTeamMembers() + " members.");

          // This for loop enables us to enter the scores that one member gave the other members
          for (int nameCounter = 0; nameCounter < existingProject.getNumberTeamMembers(); nameCounter++) {
            nameOfVoter = nameOfCurrentMember[nameCounter];
            boolean maxVoteChecker = false;
            System.out.println("\n\nEnter " + nameOfVoter + "'s votes, points must add up to 100:\n\n");

            // This while loop with a boolean maxVoteChecker forces the user to enter votes that total 100
            while (!maxVoteChecker) {
              voteChecker = 0;
              voteCounter = 0;
              int[] votesForGivenTeamMember = new int[((existingProject.getNumberTeamMembers()) - 1)];

              // This for loop allows the user to input the votes for all the members.
              // The votes for a single team member is stored in an int array - votesForAGivenTeamMember
              // which is as long as the remaining number of team members excluding the scoring team member themselves.
              // Each team member stores their scores in their own int array. All these int arrays are then put into
              // a Votes array (which takes int arrays as parameters) storing the completed set of votes for the project.

              for (int teamMemberCounter = 0; teamMemberCounter < existingProject.getNumberTeamMembers(); teamMemberCounter++) {
                if (!nameOfVoter.equals(nameOfCurrentMember[teamMemberCounter])) {
                  System.out.print("\tEnter " + nameOfVoter + "'s points for ");
                  System.out.print(nameOfCurrentMember[teamMemberCounter] + ": ");
                  boolean validInput = false;
                  while (!validInput) {
                    try {
                      votesForGivenTeamMember[voteCounter] = in.nextInt();
                      validInput = true;
                    } catch (Exception e) {
                      in.next();
                      System.out.print("\tPlease enter a valid integer: ");
                    }
                  }
                  voteChecker += votesForGivenTeamMember[voteCounter];
                  voteCounter++;
                }
              }
              if (voteChecker == MAX_SCORE) {
                maxVoteChecker = true;
              }
              else {
                System.out.println("\nVotes do not add up to 100. Please enter again.\n");
              }
              completedListOfVoteLists[nameCounter] = new Votes(votesForGivenTeamMember);
            }
          }
          // Writes the completed list of vote lists to the existing project
          existingProject.setListOfVoteLists(completedListOfVoteLists);
        }
      }

      while (!optionChecker) { // same as above
        System.out.print("\nWould you like to enter votes for another project? (y/n) ");
        voteAgain = in.next().toLowerCase();
        switch (voteAgain) {
          case "y":
            programmeRunning = true;
            optionChecker = true;
            break;
          case "n":
            PressEnterToExit.pressEnterToExit();
            programmeRunning = false;
            optionChecker = true;
            break;
          default:
            System.out.print("\nUnknown command, please try again.");
            break;
        }
      }
    }
  }
}