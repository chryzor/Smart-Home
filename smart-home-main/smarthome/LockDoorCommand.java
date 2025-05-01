// LockDoorCommand.java
public class LockDoorCommand implements Command {
    private DoorLock doorLock;

    public LockDoorCommand(DoorLock doorLock) {
        this.doorLock = doorLock;
    }

    @Override
    public void execute() {
        doorLock.lock();
        doorLock.displayStatus();
    }

    @Override
    public void undo() {
        doorLock.unlock();
        doorLock.displayStatus();
    }
}
