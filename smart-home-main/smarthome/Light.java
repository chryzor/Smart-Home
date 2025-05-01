// Light.java
public class Light extends Device {
    private int brightness; // 0-100%

    public Light(String name) {
        super(name);
        this.brightness = 50;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    @Override
    public void displayStatus() {
        System.out.println(name + " Light is " + (isOn ? "On" : "Off") + " with brightness " + brightness + "%.");
    }
}
