import javax.swing.*;

public class PizzaGUIRunner {
    private static javax.swing.SwingUtilities SwingUtilities;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}
