package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Tetris {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN};

    private static final int[][][] SHAPES = {
            // I-shape
            {{1, 1, 1, 1}},
            // T-shape
            {{0, 1, 0}, {1, 1, 1}},
            // O-shape
            {{1, 1}, {1, 1}},
            // L-shape
            {{1, 0}, {1, 0}, {1, 1}},
            // Z-shape
            {{1, 1, 0}, {0, 1, 1}}
    };

    private static class TetrisGame extends JPanel {
        private final int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        private int[][] currentShape;
        private int currentX, currentY;
        private Color currentColor;
        private boolean gameOver = false;

        public TetrisGame() {
            setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE));
            setBackground(Color.BLACK);
            spawnNewShape();

            Timer timer = new Timer(50, e -> {
                if (!gameOver) {
                    moveDown();
                    repaint();
                }
            });
            timer.start();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!gameOver) {
                        rotateShape();
                        repaint();
                    }
                }
            });
        }

        private void spawnNewShape() {
            Random random = new Random();
            int shapeIndex = random.nextInt(SHAPES.length);
            currentShape = SHAPES[shapeIndex];
            currentColor = COLORS[shapeIndex];
            currentX = random.nextInt(BOARD_WIDTH - currentShape[0].length + 1);
            currentY = 0;

            if (!canPlaceShape(currentX, currentY)) {
                gameOver = true;
            }
        }

        private boolean canPlaceShape(int x, int y) {
            for (int row = 0; row < currentShape.length; row++) {
                for (int col = 0; col < currentShape[row].length; col++) {
                    if (currentShape[row][col] == 1) {
                        int boardX = x + col;
                        int boardY = y + row;
                        if (boardX < 0 || boardX >= BOARD_WIDTH || boardY >= BOARD_HEIGHT || board[boardY][boardX] == 1) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        private void placeShape() {
            for (int row = 0; row < currentShape.length; row++) {
                for (int col = 0; col < currentShape[row].length; col++) {
                    if (currentShape[row][col] == 1) {
                        board[currentY + row][currentX + col] = 1;
                    }
                }
            }
        }

        private void moveDown() {
            if (canPlaceShape(currentX, currentY + 1)) {
                currentY++;
            } else {
                placeShape();
                clearLines();
                spawnNewShape();
            }
        }

        private void rotateShape() {
            int[][] rotated = new int[currentShape[0].length][currentShape.length];
            for (int row = 0; row < currentShape.length; row++) {
                for (int col = 0; col < currentShape[row].length; col++) {
                    rotated[col][currentShape.length - 1 - row] = currentShape[row][col];
                }
            }

            if (canPlaceShape(currentX, currentY)) {
                currentShape = rotated;
            }
        }

        private void clearLines() {
            for (int row = 0; row < BOARD_HEIGHT; row++) {
                boolean fullLine = true;
                for (int col = 0; col < BOARD_WIDTH; col++) {
                    if (board[row][col] == 0) {
                        fullLine = false;
                        break;
                    }
                }

                if (fullLine) {
                    for (int r = row; r > 0; r--) {
                        System.arraycopy(board[r - 1], 0, board[r], 0, BOARD_WIDTH);
                    }
                    board[0] = new int[BOARD_WIDTH];
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int row = 0; row < BOARD_HEIGHT; row++) {
                for (int col = 0; col < BOARD_WIDTH; col++) {
                    if (board[row][col] == 1) {
                        g.setColor(Color.GRAY);
                        g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }

            g.setColor(currentColor);
            for (int row = 0; row < currentShape.length; row++) {
                for (int col = 0; col < currentShape[row].length; col++) {
                    if (currentShape[row][col] == 1) {
                        g.fillRect((currentX + col) * BLOCK_SIZE, (currentY + row) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TetrisGame game = new TetrisGame();
            frame.add(game);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
