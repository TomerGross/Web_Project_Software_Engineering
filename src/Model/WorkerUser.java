package Model;

class WorkerUser extends User{

    /*
    *
    *  Privileges:
    *       - get info from db - denied
    *       - set info to db - self changes
    *       - view self activity - ok
    *       - view other's activity - denied
    *       - create/change usernames and passwords - denied
    *
    * */
    public int key = -1;


    public void startWorking(){

        DBConnect dbconnection = new DBConnect();
        this.key = dbconnection.startWorking(this.userName);

    }

    public void endWorking(){

        DBConnect dbconnection = new DBConnect();
        if (this.key != -1){
            dbconnection.endWorking(this.key);
        }

    }


}
