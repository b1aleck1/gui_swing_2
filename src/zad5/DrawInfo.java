package zad5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Zadanie 5.

Program wyświetla w konsoli informacje o tym, gdzie aktualnie znajduje się kursor
oraz gdzie przycisk został wciśnięty i zwolniony.
Za pomocą kółka myszy można powiększać i pomniejszać figurę znajdującą się w panelu.
Informacja o tym również wyświetla się w konsoli.
 */

public class DrawInfo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sprawdzanie kursora i kółka myszy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        DrawingPanel drawingPanel = new DrawingPanel();
        frame.add(drawingPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel {
    private int circleX = 100;
    private int circleY = 100;
    private int circleDiameter = 50;

    public DrawingPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Kursor wszedł na obszar rysowania.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Kursor opuścił obszar rysowania.");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Przycisk myszy naciśnięty na obszarze rysowania.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Przycisk myszy zwolniony na obszarze rysowania.");
            }
        });

        addMouseWheelListener(e -> {
            if (e.getPreciseWheelRotation() < 0) {
                circleDiameter += 10;
                System.out.println("Powiekszono figurę.");
            } else {
                circleDiameter = Math.max(10, circleDiameter - 10);
                System.out.println("Pomniejszono figurę.");
            }
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(circleX, circleY, circleDiameter, circleDiameter); // bo szer = wysok (koło)
    }
}

