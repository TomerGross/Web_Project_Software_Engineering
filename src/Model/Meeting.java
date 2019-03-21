package Model;

import java.util.Date;
import java.util.Vector;

public class Meeting {
    private Date date;
    private Vector<String> users;

    public Meeting(Date date, Vector<String> users){
        this.date=date;
        this.users = new Vector<String>() ;
        this.users = users;
    }

    public Date getDate(){
        return this.date;
    }

    public boolean addUserToMeeting(String id)
    {
       Scheduler sc =Scheduler.getInstance();
        if(sc.free(id,date))
        {
            this.users.add(id);
            return true;
        }
        return false;
    }

    public boolean removeUserFromMeeting(String id)
    {
        for (String iter : users) {
            if(iter.equals(id))
            {
                users.remove(id);
                return true;
            }
        }
        return false;
    }

    public boolean isUserInMeeting(String id)
    {
        for (String iter : users) {
            if(iter.equals(id))
            {
                return true;
            }
        }
        return false;
    }


    public Vector<String> getUsers() {
        return this.users;
    }
}
