package splititV2;

/*
* The purpose of this class is to create an object type Project which we can use to store our relevant project information.
*/

import java.util.ArrayList;

public class Project {

  protected String projectName;
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


}