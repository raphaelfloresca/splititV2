package splititV2;

public class Votes {
  private int[] listOfVotes;

  public Votes(int[] aListOfVotes) {
    this.listOfVotes = aListOfVotes;
  }

  public int getVoteAtIndex(int index) {
    return listOfVotes[index];
  }
}
