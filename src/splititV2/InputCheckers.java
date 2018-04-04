package splititV2;

import java.util.Scanner;

public class InputCheckers {
  private static Scanner in = new Scanner(System.in);

  public static void pressEnterToExit() {
    System.out.print("\nPress Enter to return to the main menu ");
    try {
      System.in.read();
    }
    catch(Exception e) {}
  }

  public static boolean optionChecker() {
    boolean programmeRunning = true;
    boolean validInput = false;
    while (!validInput) {
      String input = in.next().toLowerCase();
      switch (input) {
        case "y":
          validInput = true;
          break;
        case "n":
          validInput = true;
          programmeRunning = false;
          InputCheckers.pressEnterToExit();
          break;
        default:
          System.out.print("\nUnknown command, please try again.");
          break;
      }
    }
    return programmeRunning;
  }

  // Following included to ensure that only integer inputs are accepted
  public static int validInput() {
    int output = 0;
    boolean validInput = false;

    while (!validInput) {
      try {
        output = in.nextInt();
        validInput = true;
      } catch (Exception e) {
        in.next();
        System.out.print("\tPlease enter a valid integer: ");
      }
    }
    return output;
  }
}
