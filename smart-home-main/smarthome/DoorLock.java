// DoorLock.java
public class DoorLock extends Device {
    private boolean locked;

    public DoorLock(String name) {
        super(name);
        this.locked = true;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void displayStatus() {
        System.out.println(name + " Door is " + (locked ? "Locked" : "Unlocked") + ".");
    }
}
