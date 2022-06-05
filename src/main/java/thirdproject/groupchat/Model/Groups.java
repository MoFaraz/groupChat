package thirdproject.groupchat.Model;


public class Groups {

    private String groupID;
    private String users;
    private String admin;
    private int groupMembers;

    public Groups(String groupID, String users, String admin, int groupMembers) {
        this.groupID = groupID;
        this.users = users;
        this.admin = admin;
        this.groupMembers = groupMembers;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(int groupMembers) {
        this.groupMembers = groupMembers;
    }
}
