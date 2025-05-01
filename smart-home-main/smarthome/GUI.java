import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GUI extends JFrame {
	private HomeController controller;
	private JPanel floorPlanPanel;
	private JPanel controlPanel;

	// Device collections
	private Map<String, Light> lights = new HashMap<>();
	private Map<String, Thermostat> thermostats = new HashMap<>();
	private Map<String, Fan> fans = new HashMap<>();
	private Map<String, DoorLock> doorLocks = new HashMap<>();

	// Floor plan settings
	private static final int HOUSE_X = 50;
	private static final int HOUSE_Y = 50;
	private static final int HOUSE_WIDTH = 800;
	private static final int HOUSE_HEIGHT = 500;

	// Device positions on floor plan
	private Map<String, Point> devicePositions = new HashMap<>();

	public GUI(HomeController controller) {
		super("Smart Home Control System");
		this.controller = controller;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 700);
		setLayout(new BorderLayout());

		initializeDevices();
		setupPositions();
		createFloorPlanPanel();
		createControlPanel();

		add(floorPlanPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.EAST);

		setVisible(true);
	}

	private void initializeDevices() {
		// This method should be called by Main.java to register devices with the GUI
		for (Device device : controller.getDevices()) {
			if (device instanceof Light) {
				lights.put(device.getName(), (Light) device);
			} else if (device instanceof Thermostat) {
				thermostats.put(device.getName(), (Thermostat) device);
			} else if (device instanceof Fan) {
				fans.put(device.getName(), (Fan) device);
			} else if (device instanceof DoorLock) {
				doorLocks.put(device.getName(), (DoorLock) device);
			}
		}
	}

	private void setupPositions() {
		// Light positions
		devicePositions.put("Living Room", new Point(250, 180));
		devicePositions.put("Kitchen Light", new Point(650, 180));
		devicePositions.put("Kitchen Fan", new Point(600,80));
		devicePositions.put("Bedroom", new Point(200, 400));
		devicePositions.put("Bathroom", new Point(500, 400));
		devicePositions.put("Front Door", new Point(750, 540));
	}

	private void createFloorPlanPanel() {
		floorPlanPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawFloorPlan(g);
				drawDevices(g);
			}
		};
		floorPlanPanel.setPreferredSize(new Dimension(900, 600));
	}

	private void drawFloorPlan(Graphics g) {
		// Draw house outline
		g.drawRect(HOUSE_X - 1, HOUSE_Y - 1, HOUSE_WIDTH + 1, HOUSE_HEIGHT + 1);

		// Floor
		g.setColor(new Color(213, 176, 124));
		g.fillRect(HOUSE_X, HOUSE_Y, HOUSE_WIDTH, HOUSE_HEIGHT);

		// Door
		g.setColor(new Color(137, 34, 1));
		g.fillRect(710, 545, 100, 10);

		// Walls
		g.setColor(Color.black);

		// Dining room, kitchen
		g.fillRect(450, HOUSE_X, 20, 80);
		g.fillRect(450, 220, 20, 80);

		// Entry way living room
		g.fillRect(650, 320, 20, 80);
		g.fillRect(650, 500, 20, 50);

		// Middle house walls
		g.fillRect(HOUSE_X, HOUSE_Y + HOUSE_HEIGHT/2, 100, 20);
		g.fillRect(HOUSE_X + (int) (.35 * HOUSE_WIDTH), HOUSE_Y + HOUSE_HEIGHT/2, 380, 20);
		g.fillRect(HOUSE_X + (int) (.95 * HOUSE_WIDTH), HOUSE_Y + HOUSE_HEIGHT/2, 40, 20);

		// Room labels
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Living Room", 250, 150);
		g.drawString("Kitchen", 650, 150);
		g.drawString("Bedroom", 200, 350);
		g.drawString("Bathroom", 500, 350);
		g.drawString("Entry", 750, 520);
	}

	private void drawDevices(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// Draw lights
		for (Map.Entry<String, Light> entry : lights.entrySet()) {
			String name = entry.getKey();
			Light light = entry.getValue();
			Point position = devicePositions.get(name);

			if (position != null) {
				Color lightColor = light.isOn() ? Color.YELLOW : Color.BLACK;
				drawLight(g2d, position, lightColor, 20, (float) light.getBrightness()/100);

				// Draw label
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(name + " (" + light.getBrightness() + "%)", position.x - 40, position.y + 30);
			}
		}

		// Draw thermostats
		for (Map.Entry<String, Thermostat> entry : thermostats.entrySet()) {
			String name = entry.getKey();
			Thermostat thermostat = entry.getValue();
			Point position = devicePositions.get(name);

			if (position != null) {
				g.setColor(Color.BLUE);
				g.fillRect(position.x - 15, position.y - 15, 30, 30);

				// Temperature display
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawString(String.format("%.1f°C", thermostat.getTemperature()), position.x - 14, position.y + 4);

				// Draw label
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(name, position.x - 25, position.y + 30);
			}
		}

		// Draw fans
		for (Map.Entry<String, Fan> entry : fans.entrySet()) {
			String name = entry.getKey();
			Fan fan = entry.getValue();
			Point position = devicePositions.get(name);

			if (position != null) {
				g.setColor(fan.isOn() ? Color.GREEN : Color.GRAY);
				g.fillOval(position.x - 15, position.y - 15, 30, 30);

				// Fan speed display
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawString(String.valueOf(fan.getSpeed()), position.x - 4, position.y + 4);

				// Draw label
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(name, position.x - 20, position.y + 30);
			}
		}

		// Draw door locks
		for (Map.Entry<String, DoorLock> entry : doorLocks.entrySet()) {
			String name = entry.getKey();
			DoorLock lock = entry.getValue();
			Point position = devicePositions.get(name);

			if (position != null) {
				g.setColor(lock.isLocked() ? Color.RED : Color.GREEN);
				g.fillRect(position.x - 10, position.y - 15, 20, 30);

				// Draw lock symbol
				g.setColor(Color.WHITE);
				g.drawRect(position.x - 5, position.y - 10, 10, 15);

				// Draw label
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(name, position.x - 25, position.y + 30);
			}
		}
	}

	private void drawLight(Graphics2D g2d, Point light, Color lightColor, int radius, float dim) {
		float dist[] = {0.2f, 1.0f};
		Color color[] = {lightColor, new Color(0, 0, 0, 0)};
		RadialGradientPaint p = new RadialGradientPaint(light, (float) (radius * (0.5 + dim)), dist, color);
		g2d.setPaint(p);
		g2d.fillRect(light.x - 20, light.y - 20, 40, 40);
	}
	private void drawLight(Graphics2D g2d, Point light, Color lightColor, int radius) {
		float dist[] = {0.2f, 1.0f};
		Color color[] = {lightColor, new Color(0, 0, 0, 0)};
		RadialGradientPaint p = new RadialGradientPaint(light, radius, dist, color);
		g2d.setPaint(p);
		g2d.fillRect(light.x - 20, light.y - 20, 40, 40);
	}

	private void createControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(350, 600));
		controlPanel.setBackground(new Color(220, 220, 220));
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		// Create controls for each device type
		addLightControls();
		addThermostatControls();
		addFanControls();
		addDoorControls();

		// Add mode controls
		addModeControls();

		// Add undo button
		JButton undoButton = new JButton("Undo Last Command");
		undoButton.addActionListener(e -> {
			controller.undoCommand();
			repaint();
		});
		controlPanel.add(undoButton);

		// Add device status button
		JButton statusButton = new JButton("Show Device Status");
		statusButton.addActionListener(e -> {
			controller.listDevices();
		});
		controlPanel.add(statusButton);
	}

	private void addLightControls() {
		JPanel lightPanel = new JPanel();
		lightPanel.setBorder(BorderFactory.createTitledBorder("Light Controls"));
		lightPanel.setLayout(new BoxLayout(lightPanel, BoxLayout.Y_AXIS));

		// All lights on/off buttons
		JPanel allLightsPanel = new JPanel(new FlowLayout());
		JButton allOnButton = new JButton("All Lights On");
		allOnButton.addActionListener(e -> {
			for (Light light : lights.values()) {
				controller.executeCommand(new TurnLightOnCommand(light));
			}
			repaint();
		});

		JButton allOffButton = new JButton("All Lights Off");
		allOffButton.addActionListener(e -> {
			for (Light light : lights.values()) {
				light.turnOff();
			}
			repaint();
		});

		allLightsPanel.add(allOnButton);
		allLightsPanel.add(allOffButton);
		lightPanel.add(allLightsPanel);

		// Individual light controls
		for (Map.Entry<String, Light> entry : lights.entrySet()) {
			String name = entry.getKey();
			Light light = entry.getValue();

			JPanel devicePanel = new JPanel();
			devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			devicePanel.setBorder(BorderFactory.createEtchedBorder());

			JLabel nameLabel = new JLabel(name + ": ");
			JToggleButton toggleButton = new JToggleButton("ON/OFF");
			toggleButton.setSelected(light.isOn());
			toggleButton.addActionListener(e -> {
				if (toggleButton.isSelected()) {
					controller.executeCommand(new TurnLightOnCommand(light));
				} else {
					light.turnOff();
				}
				repaint();
			});

			JLabel brightnessLabel = new JLabel("Brightness: ");
			JSlider brightnessSlider = new JSlider(0, 100, light.getBrightness());
			brightnessSlider.setPreferredSize(new Dimension(100, 20));
			brightnessSlider.addChangeListener(e -> {
				light.setBrightness(brightnessSlider.getValue());
				repaint();
			});

			devicePanel.add(nameLabel);
			devicePanel.add(toggleButton);
			devicePanel.add(brightnessLabel);
			devicePanel.add(brightnessSlider);

			lightPanel.add(devicePanel);
		}

		controlPanel.add(lightPanel);
	}

	private void addThermostatControls() {
		JPanel thermostatPanel = new JPanel();
		thermostatPanel.setBorder(BorderFactory.createTitledBorder("Thermostat Controls"));
		thermostatPanel.setLayout(new BoxLayout(thermostatPanel, BoxLayout.Y_AXIS));

		for (Map.Entry<String, Thermostat> entry : thermostats.entrySet()) {
			String name = entry.getKey();
			Thermostat thermostat = entry.getValue();

			JPanel devicePanel = new JPanel();
			devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			devicePanel.setBorder(BorderFactory.createEtchedBorder());

			JLabel nameLabel = new JLabel(name + ": ");

			JSpinner tempSpinner = new JSpinner(new SpinnerNumberModel(thermostat.getTemperature(), 10.0, 30.0, 0.5));
			tempSpinner.setPreferredSize(new Dimension(60, 25));
			tempSpinner.addChangeListener(e -> {
				double newTemp = (Double) tempSpinner.getValue();
				controller.executeCommand(new SetTemperatureCommand(thermostat, newTemp));
				repaint();
			});

			JToggleButton powerButton = new JToggleButton("ON/OFF");
			powerButton.setSelected(thermostat.isOn());
			powerButton.addActionListener(e -> {
				if (powerButton.isSelected()) {
					thermostat.turnOn();
				} else {
					thermostat.turnOff();
				}
				repaint();
			});

			devicePanel.add(nameLabel);
			devicePanel.add(new JLabel("Temp: "));
			devicePanel.add(tempSpinner);
			devicePanel.add(new JLabel("°C"));
			devicePanel.add(powerButton);

			thermostatPanel.add(devicePanel);
		}

		controlPanel.add(thermostatPanel);
	}

	private void addFanControls() {
		JPanel fanPanel = new JPanel();
		fanPanel.setBorder(BorderFactory.createTitledBorder("Fan Controls"));
		fanPanel.setLayout(new BoxLayout(fanPanel, BoxLayout.Y_AXIS));

		for (Map.Entry<String, Fan> entry : fans.entrySet()) {
			String name = entry.getKey();
			Fan fan = entry.getValue();

			JPanel devicePanel = new JPanel();
			devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			devicePanel.setBorder(BorderFactory.createEtchedBorder());

			JLabel nameLabel = new JLabel(name + ": ");

			JToggleButton powerButton = new JToggleButton("ON/OFF");
			powerButton.setSelected(fan.isOn());
			powerButton.addActionListener(e -> {
				if (powerButton.isSelected()) {
					fan.turnOn();
				} else {
					fan.turnOff();
				}
				repaint();
			});

			JLabel speedLabel = new JLabel("Speed: ");
			JSlider speedSlider = new JSlider(1, 5, fan.getSpeed());
			speedSlider.setMajorTickSpacing(1);
			speedSlider.setPaintTicks(true);
			speedSlider.setPaintLabels(true);
			speedSlider.setPreferredSize(new Dimension(120, 40));
			speedSlider.addChangeListener(e -> {
				fan.setSpeed(speedSlider.getValue());
				repaint();
			});

			devicePanel.add(nameLabel);
			devicePanel.add(powerButton);
			devicePanel.add(speedLabel);
			devicePanel.add(speedSlider);

			fanPanel.add(devicePanel);
		}

		controlPanel.add(fanPanel);
	}

	private void addDoorControls() {
		JPanel doorPanel = new JPanel();
		doorPanel.setBorder(BorderFactory.createTitledBorder("Door Controls"));
		doorPanel.setLayout(new BoxLayout(doorPanel, BoxLayout.Y_AXIS));

		for (Map.Entry<String, DoorLock> entry : doorLocks.entrySet()) {
			String name = entry.getKey();
			DoorLock lock = entry.getValue();

			JPanel devicePanel = new JPanel();
			devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			devicePanel.setBorder(BorderFactory.createEtchedBorder());

			JLabel nameLabel = new JLabel(name + ": ");

			JButton lockButton = new JButton("Lock");
			lockButton.addActionListener(e -> {
				controller.executeCommand(new LockDoorCommand(lock));
				repaint();
			});

			JButton unlockButton = new JButton("Unlock");
			unlockButton.addActionListener(e -> {
				lock.unlock();
				repaint();
			});

			JLabel statusLabel = new JLabel(lock.isLocked() ? "LOCKED" : "UNLOCKED");
			statusLabel.setForeground(lock.isLocked() ? Color.RED : Color.GREEN);

			devicePanel.add(nameLabel);
			devicePanel.add(lockButton);
			devicePanel.add(unlockButton);
			devicePanel.add(statusLabel);

			doorPanel.add(devicePanel);
		}

		controlPanel.add(doorPanel);
	}

	private void addModeControls() {
		JPanel modePanel = new JPanel();
		modePanel.setBorder(BorderFactory.createTitledBorder("Mode Controls"));
		modePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel modeLabel = new JLabel("Set All Devices to: ");
		JButton ecoButton = new JButton("Eco Mode");
		ecoButton.addActionListener(e -> {
			for (Device device : controller.getDevices()) {
				device.setModeBehavior(new EcoMode());
				device.performModeBehavior();
			}
			repaint();
		});

		JButton comfortButton = new JButton("Comfort Mode");
		comfortButton.addActionListener(e -> {
			for (Device device : controller.getDevices()) {
				device.setModeBehavior(new ComfortMode());
				device.performModeBehavior();
			}
			repaint();
		});

		modePanel.add(modeLabel);
		modePanel.add(ecoButton);
		modePanel.add(comfortButton);

		controlPanel.add(modePanel);
	}

	// Method to refresh the UI
	public void refresh() {
		repaint();
	}
}