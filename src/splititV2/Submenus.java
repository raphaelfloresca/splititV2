package splititV2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static splititV2.Project.containsProjectName;
import static splititV2.Project.masterListOfProjects;

public class Submenus {
  private static Scanner in = new Scanner(System.in);

  // Returns the "About" blurb when prompted from main menu
  public static void about() {
    System.out.println("\n\tThe purpose of the application is to help teams allocate\n"
        + "\tthe credit for a project fairly so that all parties are\n"
        + "\tsatisfied with the outcome. The idea is inspired by the\n"
        + "\twork of Ariel Procaccia, a professor, and Jonathan Goldman,\n"
        + "\ta student, who were both at Carnegie Mellon University in\n"
        + "\tthe USA (Jonathan now works for Facebook). They went on to\n"
        + "\tproduce an application called Spliddit which offers provably\n"
        + "\tfair solutions for a variety of division problems including\n"
        + "\trent payments, restaurant bills and shared tasks. If you are\n"
        + "\tinterested, more information about Spliddit can be found in\n"
        + "\ta recent article published in XRDS which is a Computer Science\n"
        + "\tmagazine for students.\n");
    InputCheckers.pressEnterToExit();
  }

  // This method creates a blank project from which the user will enter the votes for their team.
  public static void createProject() {
    String newProjectName = null;
    String[] newListOfTeamMembers;
    int newNumberTeamMembers = 0;
    int numberOfProgrammeUsages = 0;
    boolean programmeRunning = true;

    while (programmeRunning) {

      // Displays if there are no stored projects.
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects.");
      }

      // This produces a list of stored projects
      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println("\t" + existingProject.getProjectName());
      }

      // Prompts the user to create a new project upon opening the submenu. numberOfProgrammeUsages increments when
      // the user chooses to input a new project immediately after the initial project (and subsequent projects after).
      // However, when the user exits the submenu, this is re-initialised to 0 so that upon accessing the submenu
      // on a later occasion, the user is again prompted to create a project or exit upon first opening the submenu.

      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to create a new project? (y/n) ");
        if (InputCheckers.optionChecker()) {
          programmeRunning = true;
        }
        else {
          programmeRunning = false;
          break;
        }
      }

      boolean nameDuplicate = true;
      while (nameDuplicate){
        System.out.print("\nEnter the project name: ");
        newProjectName = InputCheckers.validString();
        if (containsProjectName(masterListOfProjects, newProjectName)) {
          System.out.print("\nThat project already exists!");
          continue;
        }
        else {
          nameDuplicate = false;
          break;
        }
      }

      System.out.print("\nEnter the number of team members: ");

      newNumberTeamMembers = InputCheckers.validInt();

      newListOfTeamMembers = new String[newNumberTeamMembers];

      // Receives as many team member names as there are team members
      for (int numberOfTeamCounter = 0; numberOfTeamCounter < newNumberTeamMembers; numberOfTeamCounter++) {
        System.out.print("\n\tEnter the first name of team member " + (numberOfTeamCounter + 1) + ": ");
        newListOfTeamMembers[numberOfTeamCounter] = InputCheckers.validName();
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
      System.out.print("\nWould you like to create a new project? (y/n) ");
      if (InputCheckers.optionChecker()) {
        programmeRunning = true;
        numberOfProgrammeUsages++;
      }
      else {
        programmeRunning = false;
        break;
      }
    }
  }

  // This method allows the user to enter votes for a given project.
  public static void enterVotes() {
    final int MAX_SCORE = 100;
    String existingProjectName = null;
    String nameOfVoter;
    int voteCounter;
    int voteChecker;
    int numberOfProgrammeUsages = 0;
    boolean programmeRunning = true;

    running:
    while (programmeRunning) {

      boolean nameFound = false;

      // This prompts the user to go back to the main menu if no projects have already been created.
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        InputCheckers.pressEnterToExit();
        programmeRunning = false;
        break;
      }

      // This produces a list of stored projects
      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println("\t" + existingProject.getProjectName());
      }

      // Same rationale as in createProject()
      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to enter the votes for a project? (y/n) ");
        if (InputCheckers.optionChecker()) {
          programmeRunning = true;
        }
        else {
          programmeRunning = false;
          break running;
        }
      }

      // If the name is not found, the user is prompted to enter a valid name.
      while (!nameFound) {
        System.out.print("\nEnter the project name: ");
        existingProjectName = in.next();
        if (!containsProjectName(masterListOfProjects, existingProjectName)) {
          System.out.println("There is no project with that name.");
          System.out.print("Continue? (y/n) ");
          if (InputCheckers.optionChecker()) {
            programmeRunning = true;
          }
          else {
            programmeRunning = false;
            break running;
          }
        }
        else if (containsProjectName(masterListOfProjects, existingProjectName)) {
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
                  votesForGivenTeamMember[voteCounter] = InputCheckers.validInt();
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
      numberOfProgrammeUsages++;
      System.out.print("\nWould you like to enter votes for another project? (y/n) ");
      programmeRunning = InputCheckers.optionChecker();
    }
  }

  // This method shows the votes for a given project.
  public static void showVotes() {
    DecimalFormat df = new DecimalFormat("#0");
    boolean programmeRunning = true;
    int numberOfProgrammeUsages = 0;

    running:
    while (programmeRunning) {
      boolean nameFound = false;
      String existingProjectName = null;

      // Same rationale as enterVotes()
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        InputCheckers.pressEnterToExit();
        break;
      }

      // Same rationale as enterVotes()
      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println("\t" + existingProject.getProjectName() + ": " + existingProject.getNumberTeamMembers() + " members");
      }

      // Same rationale as createProject()
      if (numberOfProgrammeUsages == 0) {

        System.out.print("\nWould you like to show the votes for a project? (y/n) ");
        if (InputCheckers.optionChecker()) {
          programmeRunning = true;
        } else {
          programmeRunning = false;
          break;
        }
      }

      name:
      while (!nameFound) {
        System.out.print("\nEnter the project name. \nNote that this function currently only works with groups of 3 people: ");
        existingProjectName = in.next();
        for (Project existingProject : masterListOfProjects) { // Checks to ensure if group has 3 people, if not the code will fail
          if (existingProjectName.equals(existingProject.getProjectName())) {
            if (existingProject.getNumberTeamMembers() < 3) {
              System.out.println("\nThat project has less than 3 members.");
              System.out.print("Continue? (y/n) ");
              if (InputCheckers.optionChecker()) {
                programmeRunning = true;
                continue name;
              } else {
                programmeRunning = false;
                break running;
              }
            }
            else if (existingProject.getNumberTeamMembers() > 3) {
              System.out.println("\nThat project has more than 3 members.");
              System.out.print("Continue? (y/n) ");
              if (InputCheckers.optionChecker()) {
                programmeRunning = true;
                continue name;
              } else {
                programmeRunning = false;
                break running;
              }
            }
          }
        }
        if (!containsProjectName(masterListOfProjects, existingProjectName)) {
          System.out.println("\nThere is no project with that name.");
          System.out.print("Continue? (y/n) ");
          if (InputCheckers.optionChecker()) {
            programmeRunning = true;
            continue name;
          }
          else {
            programmeRunning = false;
            break running;
          }
        }
        else if (containsProjectName(masterListOfProjects, existingProjectName)) {
          nameFound = true;
        }
      }

      // Goes through the calculation process for each project in the master project list.
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
    System.out.print("\nWould you like to show the scores for another project? (y/n) ");
    programmeRunning = InputCheckers.optionChecker();
    numberOfProgrammeUsages++;
    }
  }

  // This allows the user to delete a project. Adapted from ArrayLists lecture notes.
  public static void deleteProject() {
    boolean programmeRunning = true;
    int numberOfProgrammeUsages = 0;

    running:
    while (programmeRunning) {
      String existingProjectToDelete = null, projectNameToDelete = null;
      boolean optionChecker = false, nameFound = false;

      // Same rationale as enterVotes()
      if (masterListOfProjects.size() == 0) {
        System.out.println("\nThere are no stored projects. Please create a project. ");
        InputCheckers.pressEnterToExit();
        programmeRunning = false;
        break;
      }

      // Same rationale as enterVotes()
      System.out.println("\nList of stored projects:\n");
      for (Project existingProject : masterListOfProjects) {
        System.out.println("\t" + existingProject.getProjectName());
      }

      // Same rationale as createProject()
      if (numberOfProgrammeUsages == 0) {
        System.out.print("\nWould you like to delete a project? (y/n) ");
        if (InputCheckers.optionChecker()) {
          programmeRunning = true;
        }
        else {
          programmeRunning = false;
          break;
        }
      }

      while (!nameFound) {
        System.out.print("\nEnter the name of the project you would like to delete: ");
        existingProjectToDelete = in.next();
        if (!containsProjectName(masterListOfProjects, existingProjectToDelete)) {
          System.out.println("There is no project with that name.");
          System.out.print("Continue? (y/n) ");
          if (InputCheckers.optionChecker()) {
            programmeRunning = true;
          }
          else {
            programmeRunning = false;
            break running;
          }
        }
        else if (containsProjectName(masterListOfProjects, existingProjectToDelete)) {
          nameFound = true;
        }
      }

      Iterator<Project> itr = masterListOfProjects.iterator();

      while (itr.hasNext()) {
        projectNameToDelete = itr.next().projectName;

        if (projectNameToDelete.contains(existingProjectToDelete)) {
          System.out.print("\nDelete " + projectNameToDelete + " (y/n)? ");
          optionChecker = false;
          while (!optionChecker) {
            String deleteYesNo = in.next().toLowerCase();
            switch (deleteYesNo) {
              case "y":
                itr.remove();
                System.out.println(projectNameToDelete + " was deleted successfully.");
                optionChecker = true;
                break;
              case "n":
                System.out.println(projectNameToDelete + " was not deleted.");
                optionChecker = true;
                break;
              default:
                System.out.print("\nUnknown command, please try again.");
                break;
            }
          }
          numberOfProgrammeUsages++;
        }
      }
      System.out.print("\nWould you like to delete another project? (y/n) ");
      programmeRunning = InputCheckers.optionChecker();
    }
  }
}
