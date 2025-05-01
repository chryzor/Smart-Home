import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        HomeController controller = HomeController.getInstance();

        // Create devices
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen Light");
        Thermostat bedroomThermostat = new Thermostat("Bedroom");
        DoorLock frontDoorLock = new DoorLock("Front Door");
        Fan kitchenFan = new Fan("Kitchen Fan");

        // Add devices to controller
        controller.addDevice(livingRoomLight);
        controller.addDevice(kitchenLight);
        controller.addDevice(bedroomThermostat);
        controller.addDevice(frontDoorLock);
        controller.addDevice(kitchenFan);

        // Launch the GUI on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            GUI gui = new GUI(controller);
        });
    }
}