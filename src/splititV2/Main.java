package splititV2;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String option;
    boolean programmeRunning = true;

    ReadFromFile.read();

    while (programmeRunning) {
      System.out.println("\nWelcome to Split-it\n\n"
          + "\tAbout (A)\n"
          + "\tCreate Project (C)\n"
          + "\tEnter Votes (V)\n"
          + "\tShow Project (S)\n"
          + "\tQuit (Q)\n");
      System.out.print("\tPlease choose an option: ");
      option = in.next().toLowerCase();

      // Handles the main menu choices.
      switch(option) {
        case "a":
          About.about();
          break;
        case "c":
          Project.createProject();
          break;
        case "q":
          SaveToFile.export();
          programmeRunning = false;
          break;
        case "v":
          Project.enterVotes();
          break;
        case "s":
          Project.showVotes();
          break;
        default:
          System.out.println("\tUnknown option, please try again.");
          break;
      }
    }
  }
}