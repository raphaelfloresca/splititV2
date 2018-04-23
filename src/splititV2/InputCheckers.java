package splititV2;

import java.util.Scanner;

public class InputCheckers {
  private static Scanner in = new Scanner(System.in);

  // Allows users to return to the main menu after pressing enter, and doesn't actually do anything
  public static void pressEnterToExit() {
    System.out.print("\nPress Enter to return to the main menu ");
    try {
      System.in.read();
    }
    catch(Exception e) {}
  }

  // Below is a reused method for y/n options
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
  public static int validInt() {
    int output = 0;
    boolean validInput = false;

    while (!validInput) {
      try {
        output = in.nextInt();
      } catch (Exception e) {
        in.next();
        System.out.print("\tPlease enter a valid integer: ");
        continue;
      }
      if (output<0) {
        System.out.print("\tPlease enter a positive number: ");
        continue;
      }
      validInput = true;
      break;
    }
    return output;
  }

  public static String validString() { // code adapted from user Bhesh Gurung at https://stackoverflow.com/questions/14278170/how-to-check-whether-a-string-contains-at-least-one-alphabet-in-java
    boolean validInput = false;
    String input = null;
    while (!validInput) {
      input = in.next();
      if(input.matches(".*[a-zA-Z0-9]+.*")){
        validInput = true;
        break;
      }
      else {
        System.out.print("\tName must contain alphanumeric characters: ");
        continue;
      }
    }
    return input;
  }

  public static String validName() { // same as above, with extra input from http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
    boolean validInput = false;
    String input = null;
    while (!validInput) {
      input = in.next();
      if(input.matches("[a-zA-Z]+")){
        validInput = true;
        break;
      }
      else {
        System.out.print("\tName must consist of alphabets. This is not a gamertag: ");
        continue;
      }
    }
    return input;
  }
}
