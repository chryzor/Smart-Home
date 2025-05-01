// ComfortMode.java
public class ComfortMode implements ModeBehavior {
    @Override
    public void applyMode(Device device) {
        System.out.println(device.getName() + " switched to Comfort Mode.");
    }
}
