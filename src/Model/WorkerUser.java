package Model;

class WorkerUser extends User{

    /*
    *
    *  Privileges:
    *       - get info from db - denied
    *       - set info to db - self changes
    *       - view self activity - ok
    *       - view other's activity - denied
    *       - create/change user names and passwords - denied
    *
    * */
    public int key = -1;



}
