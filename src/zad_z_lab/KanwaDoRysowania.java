package zad_z_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class KanwaDoRysowania {

    static class Figura {
        int x, y;
        String typ;

        Figura(int x, int y, String typ) {
            this.x = x;
            this.y = y;
            this.typ = typ;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {

            JFrame frame = new JFrame("Kanwa do rysowania");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            List<Figura> figury = new ArrayList<>();
            String[] aktualnyTypFigury = {"KOLO"};

            JPanel kanwa = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    for (Figura figura : figury) {
                        if ("KOLO".equals(figura.typ)) {
                            g.setColor(Color.BLUE);
                            g.fillOval(figura.x - 20, figura.y - 20, 40, 40);
                        } else if ("KWADRAT".equals(figura.typ)) {
                            g.setColor(Color.RED);
                            g.fillRect(figura.x - 20, figura.y - 20, 40, 40);
                        }
                    }
                }
            };
            kanwa.setBackground(Color.WHITE);

            kanwa.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    figury.add(new Figura(e.getX(), e.getY(), aktualnyTypFigury[0]));
                    kanwa.repaint();
                }
            });

            kanwa.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    frame.setTitle("Pozycja myszy: X=" + e.getX() + " Y=" + e.getY());
                }
            });

            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == 'k' || e.getKeyChar() == 'K') {
                        aktualnyTypFigury[0] = "KOLO";
                    } else if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
                        aktualnyTypFigury[0] = "KWADRAT";
                    }
                }
            });

            frame.add(kanwa);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
