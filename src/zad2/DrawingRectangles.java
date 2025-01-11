package zad2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/*
Zadanie 2.

Program tworzy prostokąty poprzez naciśnięcie i przeciągnięcie myszką po panelu.
Później, trzymając wciśnięty dowolny przycisk myszy (na wybranym prostokącie), można go przesuwać.
 */

public class DrawingRectangles {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Naciśnij i przeciągnij, aby narysować prostokąt.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        DrawingPanel drawingPanel = new DrawingPanel();
        frame.add(drawingPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel {
    private List<Rectangle> rectangles = new ArrayList<>(); // Przechowuje narysowane prostokąty
    private Rectangle currentRect = null;
    private Rectangle selectedRect = null;
    private Point lastMousePosition = null;

    public DrawingPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                boolean isInsideAnyRect = false;

                // Sprawdzamy, czy kliknięcie nastąpiło wewnątrz jakiegoś prostokąta
                for (Rectangle rect : rectangles) {
                    if (rect.contains(point)) {
                        selectedRect = rect;
                        lastMousePosition = point;
                        isInsideAnyRect = true;
                        break;
                    }
                }

                // Jeśli nie - rysujemy kolejny
                if (!isInsideAnyRect) {
                    currentRect = new Rectangle(point.x, point.y, 0, 0);
                    rectangles.add(currentRect);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentRect = null; // Stop drawing
                selectedRect = null; // Stop moving
            }
        });

        // Listener dla przeciągania
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();

                if (currentRect != null) {
                    // Update current rectangle dimensions
                    int width = Math.abs(point.x - currentRect.x);
                    int height = Math.abs(point.y - currentRect.y);
                    currentRect.setSize(width, height);

                    // Dostosowanie pozycji, jeśli przeciągnięto "do tyłu (góra, lewo)"
                    if (point.x < currentRect.x) currentRect.x = point.x;
                    if (point.y < currentRect.y) currentRect.y = point.y;

                } else if (selectedRect != null && lastMousePosition != null) {
                    // Poruszanie wybranym prostokątem
                    int dx = point.x - lastMousePosition.x;
                    int dy = point.y - lastMousePosition.y;
                    selectedRect.translate(dx, dy);
                    lastMousePosition = point; // Update ostatniej pozycji myszki
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // jak się usunie to jest ciekawie
        Graphics2D g2d = (Graphics2D) g; // rzutowanie na rozszerz. wersję Graphics
        // System.out.println("Repainting. Rectangles count: " + rectangles.size()); - debugging
        for (Rectangle rect : rectangles) {
            g2d.setColor(Color.BLUE);
            g2d.draw(rect);
        }

        if (selectedRect != null) {
            g2d.setColor(Color.RED);
            g2d.draw(selectedRect);
        }
    }
}
