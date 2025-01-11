package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Coordinates {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Najedź myszką w wybrane miejsce i kliknij, aby wyświetlić współrzędne.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel drawingPanel = new JPanel();
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setPreferredSize(new Dimension(600, 300));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(600, 30));

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                textField.setText("Kliknięto: (" + x + ", " + y + "). Naciśnij ENTER, aby wyczyścić.");
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textField.setText("");
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);

        // frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

