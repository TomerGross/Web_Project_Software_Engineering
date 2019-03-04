package Model;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

public class Meeting {
    private Date date;
    private User[] users;

    public Meeting(Date date,User[] users){
        this.date=date;
        this.users= users;
    }

    



}
