// DeviceFactory.java
public class DeviceFactory {
    public static Device createDevice(String type, String name) {
        switch (type.toLowerCase()) {
            case "light": return new Light(name);
            case "thermostat": return new Thermostat(name);
            case "fan": return new Fan(name);
            case "doorlock": return new DoorLock(name);
            default: throw new IllegalArgumentException("Unknown device type: " + type);
        }
    }
}
