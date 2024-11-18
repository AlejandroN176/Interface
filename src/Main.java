import java.io.*;
import java.util.*;
import java.time.LocalTime;


// interface to describe the behavior of a Door with no Key
interface NoLockDoorBehavior {
    void open();
    void close();
    boolean isOpen();
}
// interface to describe the behavior of a Door that requires
// a key value of some kind to open
interface LockDoorBehavior {
    void open(String key);
    void close();
    boolean isOpen();
}
// this class implements the nolockdoorbehavior
class ManualDoorBehavior implements NoLockDoorBehavior{
    private boolean _open;
    public ManualDoorBehavior() {
        _open = false;
    }
    public ManualDoorBehavior(boolean b) {
        _open = b;
    }
    public void open(){
        _open = true;
    }
    public void close(){
        _open = false;
    }
    public boolean isOpen() {
        return _open;
    }
    public String toString() {
        String s = "" + _open + " ";
        return s;
    }
}
// this class implements the nolockdoorbehavior
class AutomaticDoorBehavior implements NoLockDoorBehavior{
    private boolean _open;
    public AutomaticDoorBehavior() {
        _open = false;
    }
    public AutomaticDoorBehavior(boolean b) {
        _open = b;
    }
    public void open(){
        //door opens at 8:00am
        // if the time of day is a certain value then open
        LocalTime l = LocalTime.now();
        if(l.getHour() >= 8 && l.getHour() < 22)
        _open = true;
    }
    public void close(){
        //door closes at 10:00pm
        // if the time of day is a certain value then close
        LocalTime l = LocalTime.now();
        if(l.getHour() >= 22 || l.getHour() < 8)
        _open = false;
    }
    public boolean isOpen() {
        return _open;
    }
    public String toString() {
        String s = "" + _open + " ";
        return s;
    }
}
// this class implements the lockdoorbehavior
class PasswordDoorBehavior implements LockDoorBehavior{
    private boolean _open;
    private String _key;
    public PasswordDoorBehavior() {
        _open = false;
        _key = "pwd";
    }
    public PasswordDoorBehavior(boolean b, String s){
        _open = b;
        _key = s;
    }
    public void open(String k){
        if(_key.equals(k))
            _open = true;
    }
    public void close(){
        _open = false;
    }
    public boolean isOpen() {
        return _open;
    }
    public String toString() {
        String s = _open + " " + _key + " ";
        return s;
    }
}
// this class implements the lockdoorbehavior
class CombinationDoorBehavior implements LockDoorBehavior{
    private boolean _open;
    private int _key;
    public CombinationDoorBehavior() {
        _open = false;
        _key = 1234;
    }
    public CombinationDoorBehavior(boolean b, int k) {
        _open = b;
        _key = k;
    }
    public void open(String k){
        try {
            int t = Integer.parseInt(k);
            if (t == _key) {
                _open = true;
            }
        }
            catch(NumberFormatException e){
                //do nothing
            }
    }
    public void close(){
        _open = false;
    }
    public boolean isOpen() {
        return _open;
    }
    public String toString() {
        String s = _open + " " + _key + " ";
        return s;
    }
}
class StandardDoor {
    private NoLockDoorBehavior _doorBehavior;
    private String _location;
    public StandardDoor(){ // defaults to manual
        _doorBehavior = new ManualDoorBehavior();
        _location = "";
    }
    public StandardDoor(NoLockDoorBehavior n, String loc){
        _doorBehavior = n;
        _location = loc;
    }
    public void open(){
        _doorBehavior.open();
    }
    public void close(){
        _doorBehavior.close();
    }
    public boolean isOpen() {
        return _doorBehavior.isOpen();
    }
    public String toString() {
        String s = _doorBehavior.toString();
        s = s + _location;
        return s;
    }
}
class LockedDoor {
    private LockDoorBehavior _doorBehavior;
    private String _location;
    public LockedDoor(){ // defaults to combination
        _doorBehavior = new CombinationDoorBehavior();
        _location = "";
    }
    public LockedDoor(LockDoorBehavior c, String loc){
        _doorBehavior = c;
        _location = loc;
    }
    public void open(String k){
        _doorBehavior.open(k);
    }
    public void close(){
        _doorBehavior.close();
    }
    public boolean isOpen() {
        return _doorBehavior.isOpen();
    }
    public String toString() {
        String s = _doorBehavior.toString();
        s = s + _location;
        return s;
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<StandardDoor> sdoors = new ArrayList<StandardDoor>();
        ArrayList<LockedDoor> ldoors = new ArrayList<LockedDoor>();

        sdoors.add(new StandardDoor(new ManualDoorBehavior(false), "room1"));
        sdoors.add(new StandardDoor(new AutomaticDoorBehavior(false), "clock2"));

        ldoors.add(new LockedDoor(new CombinationDoorBehavior(false, 343), "office1"));
        ldoors.add(new LockedDoor(new PasswordDoorBehavior(false, "pwd123"), "office2"));

        System.out.println("*** Testing StandardDoor ***");
        for (StandardDoor d : sdoors){
            System.out.println(d.toString());
        }
        //dynamic binding function call open is overridden
        sdoors.get(0).open();
        sdoors.get(1).open();
        for (StandardDoor d : sdoors){
            System.out.println(d.toString());
        }

        System.out.println("*** Testing LockedDoor ***");
        for (LockedDoor d : ldoors){
            System.out.println(d.toString());
        }
        ldoors.get(0).open("243");
        ldoors.get(1).open("pwd123");
        for (LockedDoor d : ldoors){
            System.out.println(d.toString());
        }

    }
}