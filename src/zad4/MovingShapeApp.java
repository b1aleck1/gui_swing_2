package zad4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovingShapeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Przesuwanie figur");
        DrawingPanel panel = new DrawingPanel();

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

    class DrawingPanel extends JPanel implements KeyListener { // można też bez implementowania interfejsu (zad 6)

        private final int[][] shapes = {
                {150, 100, 80, 80}, // Figura 1 (poł x, y, szer, wys)
                {250, 100, 60, 60}, // Figura 2
                {330, 100, 40, 40}  // Figura 3
        };
        private int selectedShape = 0; // Indeks wybranej figury
        private final int step = 10; // Odległość przesunięcia w pikselach

        public DrawingPanel() {
            setFocusable(true);
            setFocusTraversalKeysEnabled(false); // Wyłączenie domyślnego zachowania klawisza Tab
            requestFocusInWindow();
            addKeyListener(this);
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            for (int i = 0; i < shapes.length; i++) {
                if (i == selectedShape) {
                    g.setColor(Color.RED); // Wybrana figura na czerwono
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(shapes[i][0], shapes[i][1], shapes[i][2], shapes[i][3]);
            }   //                wsp x         wsp y        szer          wysok
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (shapes[selectedShape][1] - step >= 0) {
                        shapes[selectedShape][1] -= step;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (shapes[selectedShape][1] + shapes[selectedShape][3] + step <= getHeight()) {
                        shapes[selectedShape][1] += step;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (shapes[selectedShape][0] - step >= 0) {
                        shapes[selectedShape][0] -= step;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (shapes[selectedShape][0] + shapes[selectedShape][2] + step <= getWidth()) {
                        shapes[selectedShape][0] += step;
                    }
                    break;
                case KeyEvent.VK_TAB:
                    selectedShape = (selectedShape + 1) % shapes.length; // Przełącz na następną figurę
                    break;
            }

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    }

