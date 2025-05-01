// Fan.java
public class Fan extends Device {
    private int speed; // 1-5

    public Fan(String name) {
        super(name);
        this.speed = 1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void displayStatus() {
        System.out.println(name + " Fan is " + (isOn ? "On" : "Off") + " at speed level " + speed + ".");
    }
}
