package zad3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimpleShapeMover extends JPanel {
    private final ArrayList<Shape> shapes;
    private Shape selectedShape;
    private long lastClickTime;

    public SimpleShapeMover() {
        shapes = new ArrayList<>();
        shapes.add(new Shape(50, 50, 100, 100, Color.RED, true)); // Prostokąt
        shapes.add(new Shape(200, 150, 50, 50, Color.BLUE, false)); // Koło

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Shape shape : shapes) {
                    if (shape.contains(e.getPoint())) {
                        long currentTime = System.currentTimeMillis();

                        if (selectedShape == shape && lastClickTime > 0) {
                            long elapsed = currentTime - lastClickTime;
                            shape.move((int) elapsed / 100, (int) elapsed / 100);
                            System.out.println(currentTime);
                            System.out.println(lastClickTime);
                            System.out.println(elapsed);
                        }

                        selectedShape = shape;
                        lastClickTime = currentTime;
                        repaint();
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }


    static class Shape {
        int x, y, width, height;
        Color color;
        boolean isRectangle;

        public Shape(int x, int y, int width, int height, Color color, boolean isRectangle) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.isRectangle = isRectangle;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            if (isRectangle) {
                g.fillRect(x, y, width, height);
            } else {
                g.fillOval(x, y, width, height);
            }
        }

        public boolean contains(Point p) {
            if (isRectangle) {
                return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
            } else {
                int centerX = x + width / 2;
                int centerY = y + height / 2;
                int radius = width / 2;
                double dx = p.x - centerX;
                double dy = p.y - centerY;
                return dx * dx + dy * dy <= radius * radius;
            }
        }

        public void move(int dx, int dy) {
            x += dx;
            y += dy;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Przesuwanie zależne od czasu");
        SimpleShapeMover panel = new SimpleShapeMover();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
