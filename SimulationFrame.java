import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private JTextArea simulationLog;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;
    private JPanel inputPanel;
    private JLabel avgWaitingLabel;
    private JLabel avgServiceLabel;
    private JLabel peakHourLabel;
    private JTextField numberOfClientsField;
    private JTextField numberOfServersField;
    private JTextField timeLimitField;
    private JTextField minArrivalTimeField;
    private JTextField maxArrivalTimeField;
    private JTextField minServiceTimeField;
    private JTextField maxServiceTimeField;
    private JButton startButton;

    public SimulationFrame() {
        setTitle("Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        simulationLog = new JTextArea();
        simulationLog.setEditable(false);

        scrollPane = new JScrollPane(simulationLog);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        resultsPanel = createResultsPanel();
        add(resultsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        panel.add(new JLabel("Number of Clients:"));
        numberOfClientsField = new JTextField();
        panel.add(numberOfClientsField);

        panel.add(new JLabel("Number of Servers:"));
        numberOfServersField = new JTextField();
        panel.add(numberOfServersField);

        panel.add(new JLabel("Time Limit:"));
        timeLimitField = new JTextField();
        panel.add(timeLimitField);

        panel.add(new JLabel("Min Arrival Time:"));
        minArrivalTimeField = new JTextField();
        panel.add(minArrivalTimeField);

        panel.add(new JLabel("Max Arrival Time:"));
        maxArrivalTimeField = new JTextField();
        panel.add(maxArrivalTimeField);

        panel.add(new JLabel("Min Service Time:"));
        minServiceTimeField = new JTextField();
        panel.add(minServiceTimeField);

        panel.add(new JLabel("Max Service Time:"));
        maxServiceTimeField = new JTextField();
        panel.add(maxServiceTimeField);

        startButton = new JButton("Start Simulation");
        panel.add(new JLabel());
        panel.add(startButton);

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        avgWaitingLabel = new JLabel("Average waiting time:");
        avgServiceLabel = new JLabel("Average service time:");
        peakHourLabel = new JLabel("Peak hour:");
        panel.add(avgWaitingLabel);
        panel.add(new JLabel());
        panel.add(avgServiceLabel);
        panel.add(new JLabel());
        panel.add(peakHourLabel);
        panel.add(new JLabel());

        return panel;
    }

    public void appendLog(String text) {
        simulationLog.append(text + "\n");
        simulationLog.setCaretPosition(simulationLog.getDocument().getLength());
    }

    public void setAvgWaitingTime(double avgWaitingTime) {
        avgWaitingLabel.setText("Average waiting time: " + avgWaitingTime);
    }

    public void setAvgServiceTime(double avgServiceTime) {
        avgServiceLabel.setText("Average service time: " + avgServiceTime);
    }

    public void setPeakHour(int peakHour) {
        peakHourLabel.setText("Peak hour: " + peakHour);
    }
    public void setStartButtonActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    // Getters for input fields
    public int getNumberOfClients() {
        return Integer.parseInt(numberOfClientsField.getText());
    }

    public int getNumberOfServers() {
        return Integer.parseInt(numberOfServersField.getText());
    }

    public int getTimeLimit() {
        return Integer.parseInt(timeLimitField.getText());
    }

    public int getMinArrivalTime() {
        return Integer.parseInt(minArrivalTimeField.getText());
    }

    public int getMaxArrivalTime() {
        return Integer.parseInt(maxArrivalTimeField.getText());
    }

    public int getMinServiceTime() {
        return Integer.parseInt(minServiceTimeField.getText());
    }

    public int getMaxServiceTime() {
        return Integer.parseInt(maxServiceTimeField.getText());
    }
}
