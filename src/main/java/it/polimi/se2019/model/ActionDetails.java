package it.polimi.se2019.model;
public class ActionDetails {
    public ActionDetails() {
        this.userSelectedActionDetails = new UserSelectedActionDetails();
        this.fileSelectedActionDetails = new FileSelectedActionDetails();
    }



    public UserSelectedActionDetails getUserSelectedActionDetails() {
        return userSelectedActionDetails;
    }
    public FileSelectedActionDetails getFileSelectedActionDetails() {
        return fileSelectedActionDetails;
    }

    private UserSelectedActionDetails userSelectedActionDetails;
    private FileSelectedActionDetails fileSelectedActionDetails;

}