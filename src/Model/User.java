package Model;

import java.util.Date;

class User{

    /*
    *   MULTIPLE USER KINDS:
    *       - REGULAR - WORKERS IN THE COMPANY
    *       - MANAGER - MANAGERS OF THE COMPANY
    *       - ADMIN - US
    *
    *   Characterizing:
    *       - id
    *       - first name
    *       - last name
    *       - birthday
    *       - user name, password
    *
    *
    *   Privileges:
    *       - get info from db
    *       - set info to db
    *       - view self activity
    *       - view other's activity
    *       - create/change usernames and passwords
    */

    public String userName, password;
    public String id, firstName, lastName;
    public Date birthday;

    public User(String id, String firstName, String lastName, String userName, String password, Date birthday){
        // parameterized c'tor for setting a new user
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
    }

    User(){

        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.password = "";
        this.birthday = new Date();
    }

    void changeUserAndPasswordById(String id,String userName,String password){
        // changing only username & password of exists user

        this.userName = userName;
        this.password = password;
    }

    void showActivityBetweenDates(){

    }





}
