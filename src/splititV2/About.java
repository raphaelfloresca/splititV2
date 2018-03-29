package splititV2;

public class About {

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
        + "\tmagazine for students.\n\n");
    PressEnterToExit.pressEnterToExit();
  }
}
