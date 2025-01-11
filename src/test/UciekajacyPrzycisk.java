package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UciekajacyPrzycisk {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Uciekający Przycisk");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel(null);
            frame.add(panel);

            JButton przycisk = new JButton("Kliknij mnie!");
            przycisk.setBounds(350, 250, 120, 50);

            int strefaBezpieczna = 20;

            przycisk.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Point mousePosition = e.getPoint();
                    Rectangle przyciskBounds = przycisk.getBounds();

                    int panelWidth = panel.getWidth();
                    int panelHeight = panel.getHeight();

                    int newX = przyciskBounds.x;
                    int newY = przyciskBounds.y;

                    if (mousePosition.getX() >= przyciskBounds.width - strefaBezpieczna) {
                        newX = Math.max(0, przyciskBounds.x - 50);
                    } else if (mousePosition.getX() <= strefaBezpieczna) {
                        newX = Math.min(panelWidth - przyciskBounds.width, przyciskBounds.x + 50);
                    }

                    if (mousePosition.getY() >= przyciskBounds.height - strefaBezpieczna) {
                        newY = Math.max(0, przyciskBounds.y - 50);
                    } else if (mousePosition.getY() <= strefaBezpieczna) {
                        newY = Math.min(panelHeight - przyciskBounds.height, przyciskBounds.y + 50);
                    }

                    przycisk.setLocation(newX, newY);
                }
            });

            panel.add(przycisk);
            frame.setVisible(true);
        });
    }
}

