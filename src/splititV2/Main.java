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
          + "\tDelete Project (D)\n"
          + "\tQuit (Q)\n");
      System.out.print("\tPlease choose an option: ");
      option = in.next().toLowerCase();

      // Handles the main menu choices.
      switch(option) {
        case "a":
          Submenus.about();
          break;
        case "c":
          Submenus.createProject();
          break;
        case "q":
          SaveToFile.export();
          programmeRunning = false;
          break;
        case "v":
          Submenus.enterVotes();
          break;
        case "s":
          Submenus.showVotes();
          break;
        case "d":
          Submenus.deleteProject();
          break;
        default:
          System.out.println("\tUnknown option, please try again.");
          break;
      }
    }
  }
}