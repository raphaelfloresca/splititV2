package splititV2;

public class Votes {
  private int[] listOfVotes;

  public Votes(int[] aListOfVotes) {
    this.listOfVotes = aListOfVotes;
  }

  public int[] getListOfVotes() {
    return listOfVotes;
  }

  public void setListOfVotes(int[] listOfVotes) {
    this.listOfVotes = listOfVotes;
  }

  public int getVoteAtIndex(int index) {
    return listOfVotes[index];
  }

  public void setVoteAtIndex(int index, int newValue) { this.listOfVotes[index] = newValue; }
}
