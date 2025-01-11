package zad6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeChanger {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Shape Mover Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        DrawingPanel drawingPanel = new DrawingPanel();
        frame.add(drawingPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        drawingPanel.requestFocusInWindow();
    }

    static class DrawingPanel extends JPanel {
        private List<ShapeInfo> shapes;
        private ShapeInfo selectedShape;
        private final int MOVE_STEP = 10;

        public DrawingPanel() {
            setPreferredSize(new Dimension(800, 600));
            setBackground(Color.WHITE);

            shapes = new ArrayList<>();
            shapes.add(new ShapeInfo(new Rectangle(100, 100, 50, 50), Color.BLACK));
            shapes.add(new ShapeInfo(new Rectangle(200, 150, 80, 60), Color.BLACK));
            shapes.add(new ShapeInfo(new Rectangle(300, 200, 100, 100), Color.BLACK));
            selectedShape = shapes.get(0);

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (selectedShape != null) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP -> selectedShape.shape.y -= MOVE_STEP;
                            case KeyEvent.VK_DOWN -> selectedShape.shape.y += MOVE_STEP;
                            case KeyEvent.VK_LEFT -> selectedShape.shape.x -= MOVE_STEP;
                            case KeyEvent.VK_RIGHT -> selectedShape.shape.x += MOVE_STEP;
                            case KeyEvent.VK_SHIFT -> selectedShape.toggleShapeType();
                            case KeyEvent.VK_CONTROL -> selectedShape.color = Color.BLUE;
                        }
                        repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (selectedShape != null) {
                        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                            selectedShape.color = Color.GREEN;
                        }
                       repaint();
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (ShapeInfo shapeInfo : shapes) {
                        if (shapeInfo.shape.contains(e.getPoint())) {
                            selectedShape = shapeInfo;
                            repaint();
                            break;
                        }
                    }
                }
            });

            setFocusable(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            for (ShapeInfo shapeInfo : shapes) {
                g2d.setColor(shapeInfo.color);
                if (shapeInfo.isRectangle) {
                    g2d.fill(shapeInfo.shape);
                } else {
                    g2d.fillOval(shapeInfo.shape.x, shapeInfo.shape.y, shapeInfo.shape.width, shapeInfo.shape.height);
                }

                if (shapeInfo == selectedShape) {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3)); // Grubość obrysu
                    if (shapeInfo.isRectangle) {
                        g2d.draw(shapeInfo.shape);
                    } else {
                        g2d.drawOval(shapeInfo.shape.x, shapeInfo.shape.y, shapeInfo.shape.width, shapeInfo.shape.height);
                    }
                }

            }
        }
        static class ShapeInfo {
            Rectangle shape;
            Color color;
            boolean isRectangle;

            ShapeInfo(Rectangle shape, Color color) {
                this.shape = shape;
                this.color = color;
                this.isRectangle = true;
            }

            void toggleShapeType() {
                isRectangle = !isRectangle;
            }
        }
    }
}

