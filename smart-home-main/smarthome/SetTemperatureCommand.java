// SetTemperatureCommand.java
public class SetTemperatureCommand implements Command {
    private Thermostat thermostat;
    private double previousTemperature;
    private double newTemperature;

    public SetTemperatureCommand(Thermostat thermostat, double newTemp) {
        this.thermostat = thermostat;
        this.newTemperature = newTemp;
    }

    @Override
    public void execute() {
        previousTemperature = thermostat.getTemperature();
        thermostat.setTemperature(newTemperature);
        thermostat.displayStatus();
    }

    @Override
    public void undo() {
        thermostat.setTemperature(previousTemperature);
        thermostat.displayStatus();
    }
}
