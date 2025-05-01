// TurnLightOnCommand.java
public class TurnLightOnCommand implements Command {
    private Light light;

    public TurnLightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
        light.displayStatus();
    }

    @Override
    public void undo() {
        light.turnOff();
        light.displayStatus();
    }
}
