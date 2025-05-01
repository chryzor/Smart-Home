import java.util.ArrayList;
import java.util.List;

public class HomeController {
    private static HomeController instance;
    private List<Device> devices = new ArrayList<>();
    private CommandInvoker invoker = new CommandInvoker();

    private HomeController() {}

    public static HomeController getInstance() {
        if (instance == null) {
            instance = new HomeController();
        }
        return instance;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void listDevices() {
        for (Device d : devices) {
            d.displayStatus();
        }
    }

    public void executeCommand(Command command) {
        invoker.executeCommand(command);
    }

    public void undoCommand() {
        invoker.undo();
    }
}