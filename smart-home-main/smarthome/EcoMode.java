// EcoMode.java
public class EcoMode implements ModeBehavior {
    @Override
    public void applyMode(Device device) {
        System.out.println(device.getName() + " switched to Eco Mode.");
    }
}
