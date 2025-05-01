// Device.java
public abstract class Device {
    protected String name;
    protected boolean isOn;
    protected ModeBehavior modeBehavior;

    public Device(String name) {
        this.name = name;
        this.isOn = false;
        this.modeBehavior = new EcoMode(); // default behavior
    }

    public String getName() { return name; }
    public boolean isOn() { return isOn; }

    public void turnOn() { isOn = true; }
    public void turnOff() { isOn = false; }

    public void setModeBehavior(ModeBehavior modeBehavior) {
        this.modeBehavior = modeBehavior;
    }

    public void performModeBehavior() {
        if (modeBehavior != null) {
            modeBehavior.applyMode(this);
        }
    }

    public abstract void displayStatus();
}
