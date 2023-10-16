import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrustRadio, regularCrustRadio, deepDishCrustRadio;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingsCheckboxes;
    private JTextArea orderTextArea;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();
        JPanel orderPanel = createOrderPanel();
        JPanel buttonPanel = createButtonPanel();

        setLayout(new BorderLayout());
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.CENTER);
        add(toppingsPanel, BorderLayout.WEST);
        add(orderPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCrustPanel() {
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));

        thinCrustRadio = new JRadioButton("Thin");
        regularCrustRadio = new JRadioButton("Regular");
        deepDishCrustRadio = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustRadio);
        crustGroup.add(regularCrustRadio);
        crustGroup.add(deepDishCrustRadio);

        crustPanel.add(thinCrustRadio);
        crustPanel.add(regularCrustRadio);
        crustPanel.add(deepDishCrustRadio);
        return crustPanel;
    }

    private JPanel createSizePanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));

        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large", "Super"});
        sizePanel.add(sizeComboBox);
        return sizePanel;
    }

    private JPanel createToppingsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        toppingsCheckboxes = new JCheckBox[6];
        toppingsCheckboxes[0] = new JCheckBox("Pepperoni");
        toppingsCheckboxes[1] = new JCheckBox("Mushrooms");
        toppingsCheckboxes[2] = new JCheckBox("Onions");
        toppingsCheckboxes[3] = new JCheckBox("Green Peppers");
        toppingsCheckboxes[4] = new JCheckBox("Olives");
        toppingsCheckboxes[5] = new JCheckBox("Bacon");

        for (JCheckBox checkbox : toppingsCheckboxes) {
            toppingsPanel.add(checkbox);
        }
        return toppingsPanel;
    }

    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel();
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderPanel.add(scrollPane);
        return orderPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        return buttonPanel;
    }

    private void generateOrder() {
        String crustSelect = "";
        if (thinCrustRadio.isSelected()) {
            crustSelect = "Thin";
        } else if (regularCrustRadio.isSelected()) {
            crustSelect = "Regular";
        } else if (deepDishCrustRadio.isSelected()) {
            crustSelect = "Deep-dish";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a crust type.");
            return;
        }

        String sizeSelect = (String) sizeComboBox.getSelectedItem();
        double sizePrice = 0.0;
        switch (sizeSelect) {
            case "Small":
                sizePrice = 8.00;
                break;
            case "Medium":
                sizePrice = 12.00;
                break;
            case "Large":
                sizePrice = 16.00;
                break;
            case "Super":
                sizePrice = 20.00;
                break;
        }

        List<String> selectedToppings = new ArrayList<>();
        double toppingCost = 0.0;
        for (JCheckBox checkbox : toppingsCheckboxes) {
            if (checkbox.isSelected()) {
                selectedToppings.add(checkbox.getText());
                toppingCost += 1.00;
            }
        }

        double subtotal = sizePrice + toppingCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("==============================\n");
        receiptBuilder.append(crustSelect).append(", ").append(sizeSelect).append("             $").append(String.format("%.2f", sizePrice)).append("\n");

        for (String topping : selectedToppings) {
            receiptBuilder.append(topping).append("             $1.00\n");
        }

        receiptBuilder.append("Sub-total:             $").append(String.format("%.2f", subtotal)).append("\n");
        receiptBuilder.append("Tax:             $").append(String.format("%.2f", tax)).append("\n");
        receiptBuilder.append("------------------------------\n");
        receiptBuilder.append("Total:             $").append(String.format("%.2f", total)).append("\n");
        receiptBuilder.append("==============================");

        orderTextArea.setText(receiptBuilder.toString());
        orderTextArea.setText(receiptBuilder.toString());
    }

    private void clearForm() {
        thinCrustRadio.setSelected(false);
        regularCrustRadio.setSelected(false);
        deepDishCrustRadio.setSelected(false);
        sizeComboBox.setSelectedIndex(0);

        for (JCheckBox checkbox : toppingsCheckboxes) {
            checkbox.setSelected(false);
        }

        orderTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}

