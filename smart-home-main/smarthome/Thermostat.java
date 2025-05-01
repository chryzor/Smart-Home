// Thermostat.java
public class Thermostat extends Device {
    private double temperature;

    public Thermostat(String name) {
        super(name);
        this.temperature = 22.0;
    }

    public void setTemperature(double temp) {
        this.temperature = temp;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public void displayStatus() {
        System.out.println(name + " Thermostat set to " + temperature + "°C and is " + (isOn ? "On" : "Off") + ".");
    }
}
