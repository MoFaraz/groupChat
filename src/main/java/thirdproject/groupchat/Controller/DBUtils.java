package thirdproject.groupchat.Controller;


import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import thirdproject.groupchat.Exception.UserNotFound;
import thirdproject.groupchat.Model.Groups;
import thirdproject.groupchat.Model.MyMessage;
import thirdproject.groupchat.Model.User;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Class.forName;

public class DBUtils {


    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/groupChat";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "password";

    public static void signUp(ActionEvent event, String username, String password, String emailId) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            psCheckUserExist = connection.prepareStatement("SELECT * FROM data WHERE username = ?");
            psCheckUserExist.setString(1, username);
            resultSet = psCheckUserExist.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                password = PasswordToHash.toHash(password);
                psInsert = connection.prepareStatement("INSERT INTO data (username,password,email_id) VALUES (?,?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, emailId);
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psCheckUserExist != null) {
                try {
                    psCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT password FROM data WHERE  username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(PasswordToHash.toHash(password))) {
                        PreparedStatement update = connection.prepareStatement("UPDATE data SET status = ? WHERE username = ?");
                        update.setInt(1, 1);
                        update.setString(2, username);
                        update.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void logout() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            PreparedStatement update = connection.prepareStatement("UPDATE data SET status = ? WHERE ID = ?");
            update.setInt(1, 0);
            update.setInt(2, getOnlineID());
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> showUser() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<User> users = new ArrayList<>();

        User myUser;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT ID , username , password , email_id , status , friends , request , block FROM data");
            resultSet = preparedStatement.executeQuery();

            int id;
            String username;
            String password;
            String email_id;
            String status;
            String friends;
            String request;
            String block;

            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                email_id = resultSet.getString("email_id");
                status = resultSet.getString("status");
                friends = resultSet.getString("friends");
                request = resultSet.getString("request");
                block = resultSet.getString("block");
                myUser = new User(username, password, email_id, status, request, block, friends, id);
                users.add(myUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean findUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT password , email_id FROM data WHERE  username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addFriends(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT friends FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("friends");
                    if (!retrievedFriends.equals("0"))
                        retrievedFriends += " " + String.valueOf(id);
                    else
                        retrievedFriends = String.valueOf(id);
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET friends = ? WHERE status = 1");
                    update.setString(1, retrievedFriends);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getUserID(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT ID FROM data WHERE  username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next()) {
                    return resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFriends() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT friends FROM data WHERE  status = 1");

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next())
                    return resultSet.getString("friends");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getOnlineID() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT ID FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next()) {
                    return resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addBlockUser(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT block FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("block");
                    if (!retrievedFriends.equals("0"))
                        retrievedFriends += " " + String.valueOf(id);
                    else
                        retrievedFriends = String.valueOf(id);
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET block = ? WHERE status = 1");
                    update.setString(1, retrievedFriends);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRequestToOther(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getOnlineID();
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT request FROM data WHERE  username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("request");
                    if (!retrievedFriends.equals("0"))
                        retrievedFriends += " " + String.valueOf(id);
                    else
                        retrievedFriends = String.valueOf(id);
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET request = ? WHERE username = ?");
                    update.setString(1, retrievedFriends);
                    update.setString(2, username);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getRequest() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT request FROM data WHERE  status = 1");

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next())
                    return resultSet.getString("request");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void removeFromFriends(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT friends FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("friends");
                    retrievedFriends = retrievedFriends.replace(String.valueOf(id), "");
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET friends = ? WHERE status = 1");
                    update.setString(1, retrievedFriends);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromBlocklist(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT block FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("block");
                    retrievedFriends = retrievedFriends.replace(String.valueOf(id), "");
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET block = ? WHERE status = 1");
                    update.setString(1, retrievedFriends);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromRequestList(String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int id = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT request FROM data WHERE  status = 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedFriends = resultSet.getString("request");
                    retrievedFriends = retrievedFriends.replace(String.valueOf(id), "");
                    PreparedStatement update = connection.prepareStatement("UPDATE data SET request = ? WHERE status = 1");
                    update.setString(1, retrievedFriends);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getCompanyID(String groupID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT ID FROM company WHERE  username = ?");
            preparedStatement.setString(1, groupID);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next()) {
                    return resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getBlockList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT block FROM data WHERE  status = 1");

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next())
                    return resultSet.getString("block");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUsername(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT username FROM data WHERE  ID = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static void addCompanyUser(String groupID, String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int userID = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT users , groupMembers FROM company WHERE  groupID = ?");
            preparedStatement.setString(1, groupID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedUsers = resultSet.getString("users");
                    int groupMember = resultSet.getInt("groupMembers") + 1;
                    if (!retrievedUsers.equals("0"))
                        retrievedUsers += " " + String.valueOf(userID);
                    else
                        retrievedUsers = String.valueOf(userID);
                    PreparedStatement update = connection.prepareStatement("UPDATE company SET users = ? , groupMembers = ? WHERE groupID = ?");

                    update.setString(1, retrievedUsers);
                    update.setInt(2, groupMember);
                    update.setString(3, groupID);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCompanyUser(String groupID, String username) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int userID = getUserID(username);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT users , groupMembers FROM company WHERE  groupID = ?");
            preparedStatement.setString(1, groupID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedUsers = resultSet.getString("users");
                    int groupMember = resultSet.getInt("groupMembers") - 1;
                    retrievedUsers = retrievedUsers.replace(String.valueOf(userID), "");
                    PreparedStatement update = connection.prepareStatement("UPDATE company SET users = ? , groupMembers = ? WHERE groupID = ?");

                    update.setString(1, retrievedUsers);
                    update.setInt(2, groupMember);
                    update.setString(3, groupID);
                    update.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCompanyMembers(String groupID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT groupMembers FROM company WHERE  groupID = ?");
            preparedStatement.setString(1, groupID);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("UserNot Found");
            } else {
                while (resultSet.next()) {
                    return resultSet.getInt("groupMembers");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void createCompany(String groupID) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckGroupExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            psCheckGroupExist = connection.prepareStatement("SELECT * FROM company WHERE groupID = ?");
            psCheckGroupExist.setString(1, groupID);
            resultSet = psCheckGroupExist.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO company (groupID,admin,users) VALUES (?,?,?)");
                psInsert.setString(1, groupID);
                psInsert.setString(2, String.valueOf(getOnlineID()));
                psInsert.setString(3, String.valueOf(getOnlineID()));
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psCheckGroupExist != null) {
                try {
                    psCheckGroupExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<Groups> getGroup() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Groups> groups = new ArrayList<>();
        Groups group;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT groupID , users, groupAdmin , groupMembers FROM company");
            resultSet = preparedStatement.executeQuery();

            String groupID;
            String users;
            String groupAdmin;
            int groupMemebers;
            while (resultSet.next()) {
                groupID = resultSet.getString("groupID");
                users = resultSet.getString("users");
                groupAdmin = resultSet.getString("groupAdmin");
                groupMemebers = resultSet.getInt("groupMembers");
                group = new Groups(groupID, users, groupAdmin, groupMemebers);
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static void addMessage(String content, String sender, String date,String time) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            psInsert = connection.prepareStatement("INSERT INTO message (content,sender,tarikh,zaman) VALUES (?,?,?,?)");
            psInsert.setString(1, content);
            psInsert.setString(2, sender);
            psInsert.setString(3, date);
            psInsert.setString(4,time);
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MyMessage> showMessage() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<MyMessage> messages = new ArrayList<>();

        MyMessage myMessage;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT content , sender , tarikh , zaman FROM message");
            resultSet = preparedStatement.executeQuery();

            String content;
            String date;
            String send;
            String time;
            while (resultSet.next()) {
                content = resultSet.getString("content");
                date = resultSet.getString("tarikh");
                time = resultSet.getString("zaman");
                send = resultSet.getString("sender");
                myMessage = new MyMessage(send, content, date , time);
                messages.add(myMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

}