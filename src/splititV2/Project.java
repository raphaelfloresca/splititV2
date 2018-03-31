package splititV2;

/*
* The purpose of this class is to create an object type Project which we can use to store our relevant project information.
* As of now Votes is a separate class as we were fiddling with ways to store voting / voter information (meaning that
* the Vote class used to contain more information types) thus we will work on improving this in the next deliverable
*/

import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

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

    while (programmeRunning) {
      boolean optionChecker = false;

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

      // This initialises an int array where all scores = 0. This is to prevent a null pointer exception if the user
      // quits before entering their scores.
      int[] newVotesForAGivenMember = new int[newNumberTeamMembers - 1];

      // This creates blank vote lists for each index of newListOfVoteLists
      for (int newVoteListCounter = 0; newVoteListCounter < newListOfVoteLists.length; newVoteListCounter++) {
        newListOfVoteLists[newVoteListCounter] = new Votes(newVotesForAGivenMember);
      }

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
  public static boolean containsProjectName(final ArrayList<Project> list, final String aProjectName) {
    return list.stream().anyMatch(o -> o.getProjectName().equals(aProjectName));
  }

  // This method allows the user to enter votes for a given project.
  public static void enterVotes() {

    final int MAX_SCORE = 100;

    String existingProjectName;
    String voteAgain;
    String nameOfVoter;
    int voteCounter;
    int voteChecker;
    int numberOfProgrammeUsages = 0;
    boolean programmeRunning = true;


    while (programmeRunning) {
      boolean optionChecker = false;
      boolean nameFound = false;
      // This prompts the user to go back to the main menu if no projects have already been created.
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        PressEnterToExit.pressEnterToExit();
        return;
      }

      // This produces a list of stored projects
      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println(existingProject.getProjectName());
      }

      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to enter the votes for a project? (y/n) ");
        String enterVotes = in.next().toLowerCase();
        switch (enterVotes) {
          case "y":
            break;
          case "n":
            PressEnterToExit.pressEnterToExit();
            return;
          default:
            System.out.print("\nUnknown command, please try again.");
            break;
        }
      }

      System.out.print("\nEnter the project name: ");
      existingProjectName = in.next();

      // If the name is not found, the user is prompted to enter a valid name.
      while (!nameFound) {
        if (!containsProjectName(masterListOfProjects, existingProjectName)) {
          System.out.println("There is no project with that name.");
          System.out.print("\nEnter the project name: ");
          existingProjectName = in.next();
        } else if (containsProjectName(masterListOfProjects, existingProjectName)) {
          nameFound = true;
        }
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
            System.out.println("\nEnter " + nameOfVoter + "'s votes, points must add up to 100:\n");

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
              } else {
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
            numberOfProgrammeUsages++;
            programmeRunning = true;
            optionChecker = true;
            break;
          case "n":
            numberOfProgrammeUsages = 0;
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

  // This method shows the votes for a given project.
  public static void showVotes() {
    DecimalFormat df = new DecimalFormat("#0");

    boolean nameFound = false;
    boolean programmeRunning = true;
    int numberOfProgrammeUsages = 0;
    String existingProjectName;

    while (programmeRunning) {
      boolean optionChecker = false;
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        PressEnterToExit.pressEnterToExit();
        return;
      }

      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println(existingProject.getProjectName());
      }

      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to show the votes for a project? (y/n) ");
        String showVotes = in.next().toLowerCase();
        switch (showVotes) {
          case "y":
            break;
          case "n":
            PressEnterToExit.pressEnterToExit();
            return;
          default:
            System.out.print("\nUnknown command, please try again.");
            break;
        }
      }

      System.out.print("\nEnter the project name: ");
      existingProjectName = in.next();
      while (!nameFound) {
        if (!containsProjectName(masterListOfProjects, existingProjectName)) {
          System.out.println("There is no project with that name.");
          System.out.print("\nEnter the project name: ");
          existingProjectName = in.next();
        } else if (containsProjectName(masterListOfProjects, existingProjectName)) {
          nameFound = true;
        }
      }

      for (Project existingProject : masterListOfProjects) {
        if (existingProjectName.equals(existingProject.getProjectName())) {
          // This will only work with teams of three as the steps to calculate the vote share varies across team members.
          int teamMember1VoteForTeamMember2 = existingProject.getListOfVoteLists()[0].getVoteAtIndex(0);
          int teamMember1VoteForTeamMember3 = existingProject.getListOfVoteLists()[0].getVoteAtIndex(1);
          int teamMember2VoteForTeamMember1 = existingProject.getListOfVoteLists()[1].getVoteAtIndex(0);
          int teamMember2VoteForTeamMember3 = existingProject.getListOfVoteLists()[1].getVoteAtIndex(1);
          int teamMember3VoteForTeamMember1 = existingProject.getListOfVoteLists()[2].getVoteAtIndex(0);
          int teamMember3VoteForTeamMember2 = existingProject.getListOfVoteLists()[2].getVoteAtIndex(1);

          // The ratios for calculating the vote share are as follows:
          // Team member 2’s vote for the effort of Team member 3 compared with Team member 1.
          double r231 = (double) teamMember2VoteForTeamMember3 / teamMember2VoteForTeamMember1;
          // Team member 3’s vote for the effort of Team member 2 compared with Team member 1.
          double r321 = (double) teamMember3VoteForTeamMember2 / teamMember3VoteForTeamMember1;
          // Team member 1’s vote for the effort of Team member 3 compared with Team member 2.
          double r132 = (double) teamMember1VoteForTeamMember3 / teamMember1VoteForTeamMember2;
          // Team member 3’s vote for the effort of Team member 1 compared with Team member 2.
          double r312 = (double) teamMember3VoteForTeamMember1 / teamMember3VoteForTeamMember2;
          // Team member 1’s vote for the effort of Team member 2 compared with Team member 3.
          double r123 = (double) teamMember1VoteForTeamMember2 / teamMember1VoteForTeamMember3;
          // Team member 2’s vote for the effort of Team member 1 compared with Team member 3.
          double r213 = (double) teamMember2VoteForTeamMember1 / teamMember2VoteForTeamMember3;

          // This calculates the share for each team member
          double shareForTeamMember1 = ((1 / (1 + r231 + r321)) * 100);
          double shareForTeamMember2 = ((1 / (1 + r132 + r312)) * 100);
          double shareForTeamMember3 = ((1 / (1 + r123 + r213)) * 100);

          System.out.println("\nThere are " + existingProject.getNumberTeamMembers() + " members.");
          System.out.println("\nThe point allocation based on votes is: \n");

          for (int teamMemberCounter = 0; teamMemberCounter < existingProject.getNumberTeamMembers(); teamMemberCounter++) {
            System.out.print("\t" + existingProject.getListOfTeamMembers()[teamMemberCounter] + ": ");

            if (teamMemberCounter == 0) {
              System.out.println(df.format(shareForTeamMember1));
            } else if (teamMemberCounter == 1) {
              System.out.println(df.format(shareForTeamMember2));
            } else if (teamMemberCounter == 2) {
              System.out.println(df.format(shareForTeamMember3));
            }
          }
        }
      }

      optionChecker = false;

      while (!optionChecker) { // same as above
        System.out.print("\nWould you like to show the scores for another project? (y/n) ");
        String deleteAgain = in.next().toLowerCase();
        switch (deleteAgain) {
          case "y":
            numberOfProgrammeUsages++;
            programmeRunning = true;
            optionChecker = true;
            break;
          case "n":
            numberOfProgrammeUsages = 0;
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

  // This allows the user to delete a project
  public static void deleteProject() {
    boolean programmeRunning = true;

    boolean nameFound = false;
    int numberOfProgrammeUsages = 0;

    while (programmeRunning) {
      boolean optionChecker = false;
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        PressEnterToExit.pressEnterToExit();
        return;
      }

      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println(existingProject.getProjectName());
      }


      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to delete a project? (y/n) ");
        String deleteProject = in.next().toLowerCase();
        switch (deleteProject) {
          case "y":
            break;
          case "n":
            PressEnterToExit.pressEnterToExit();
            return;
          default:
            System.out.print("\nUnknown command, please try again.");
            break;
        }
      }

      System.out.print("\nEnter the project name you would like to delete: ");
      String existingProjectToDelete = in.next();

      while (!nameFound) {
        if (!containsProjectName(masterListOfProjects, existingProjectToDelete)) {
          System.out.println("There is no project with that name.");
          System.out.print("\nEnter the project name: ");
          existingProjectToDelete = in.next();
        } else if (containsProjectName(masterListOfProjects, existingProjectToDelete)) {
          nameFound = true;
        }
      }

      Iterator<Project> itr = masterListOfProjects.iterator();

      while (itr.hasNext()) {
        String projectNameToDelete = itr.next().projectName;

        if (projectNameToDelete.contains(existingProjectToDelete)) {
          System.out.print("\nDelete " + projectNameToDelete + " (y/n)? ");
          String deleteYesNo = in.next().toLowerCase();

          switch (deleteYesNo) {
            case "y":
              itr.remove();
              System.out.println(projectNameToDelete + " was deleted successfully.");
              break;
            case "n":
              System.out.println(projectNameToDelete + " was not deleted.");
              numberOfProgrammeUsages = 0;
              break;
            default:
              System.out.print("\nUnknown command, please try again.");
              break;
          }
        }
      }

      while (!optionChecker) { // same as above
        System.out.print("\nWould you like to delete another project? (y/n) ");
        String deleteAgain = in.next().toLowerCase();
        switch (deleteAgain) {
          case "y":
            numberOfProgrammeUsages++;
            programmeRunning = true;
            optionChecker = true;
            break;
          case "n":
            numberOfProgrammeUsages = 0;
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