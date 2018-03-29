package splititV2;

public class PressEnterToExit {
  public static void pressEnterToExit() {
    System.out.print("\nPress Enter to return to the main menu ");
    try {
      System.in.read();
    }
    catch(Exception e) {}
  }
}
