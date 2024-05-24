package MazeUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Algo.MazeSolver;
import Tracker.PathTrackerUI;

public class MazeUI extends JPanel implements ActionListener {
    private final int[][] maze;
    private final String path;
    private float ballRow; // Use float for smooth transition
    private float ballCol; // Use float for smooth transition
    private int pathIndex = 0;
    private int wallRotationCount = 0;
    protected static final int CELL_SIZE = 45;
    private static final float SPEED = 0.00001f;

    public MazeUI(int[][] maze, String path) {
        this.maze = maze;
        this.path = path;
        // Find start position
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == -1) {
                    ballRow = row;
                    ballCol = col;
                    break;
                }
            }
        }
        playBackgroundMusic(
                "/Users/fatimatuzzahra/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/Electric Fields.wav");
        Timer timer = new Timer(500, this); // Faster timer for smooth animation
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                drawCell(g, col, row, maze[row][col], wallRotationCount % 4); // Pass rotation
            }
        }
        drawBall(g, ballCol, ballRow);
    }

    private void drawCell(Graphics g, int col, int row, int cell, int rotation) {
        Graphics2D g2d = (Graphics2D) g;
        int rotatedCell = rotateWalls(cell, rotation);
        int startX = col * CELL_SIZE;
        int startY = row * CELL_SIZE;
        int wallThickness = 5;
        int spacing = 3; // This is the spacing between walls

        Color wallColor = Color.magenta;

        // Draw the start and end positions
        if (cell == -1) { // Start cell
            return;
        }
        if (cell == -2) { // End cell
            g.setColor(Color.RED);
            g.fillOval(startX + CELL_SIZE / 4, startY + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
            return;
        }

        g2d.setColor(wallColor);
        // Adjusted wall drawing with spacing
        if ((rotatedCell & 1) != 0) { // East wall
            g2d.fillRect(startX + CELL_SIZE - wallThickness - spacing, startY + spacing, wallThickness,
                    CELL_SIZE - 2 * spacing);
        }
        if ((rotatedCell & 2) != 0) { // South wall
            g2d.fillRect(startX + spacing, startY + CELL_SIZE - wallThickness - spacing, CELL_SIZE - 2 * spacing,
                    wallThickness);
        }
        if ((rotatedCell & 4) != 0) { // West wall
            g2d.fillRect(startX + spacing, startY + spacing, wallThickness, CELL_SIZE - 2 * spacing);
        }
        if ((rotatedCell & 8) != 0) { // North wall
            g2d.fillRect(startX + spacing, startY + spacing, CELL_SIZE - 2 * spacing, wallThickness);
        }

    }

    private void drawBall(Graphics g, float col, float row) {
        int x = (int) (col * CELL_SIZE) + CELL_SIZE / 4;
        int y = (int) (row * CELL_SIZE) + CELL_SIZE / 4;
        g.setColor(Color.GREEN);
        g.fillOval(x, y, CELL_SIZE / 2, CELL_SIZE / 2);
    }

    private int rotateWalls(int cell, int rotation) {
        int rotatedCell = cell;
        for (int i = 0; i < rotation; i++) {
            // Perform the left shift by 1 position to rotate clockwise
            int shifted = rotatedCell << 1;
            // Check if the most significant bit was set before shifting (i.e., was north
            // wall set)
            if (rotatedCell > 7) {
                shifted = shifted & 15; // Clear bits outside the 4-bit range
                shifted += 1; // Rotate the north wall to the east position
            }
            rotatedCell = shifted;
        }
        return rotatedCell;
    }

    public void actionPerformed(ActionEvent e) {
        if (pathIndex >= path.length()) {
            ((Timer) e.getSource()).stop();
            return;
        }

        char dir = path.charAt(pathIndex);

        int dirX = 0;
        int dirY = 0;
        if (dir == ',')
            wallRotationCount++;
        else if (dir == 'E')
            dirX = 1;
        else if (dir == 'S')
            dirY = 1;
        else if (dir == 'W')
            dirX = -1;
        else if (dir == 'N')
            dirY = -1;

        // Update ball position smoothly
        ballCol += SPEED * dirX;
        ballRow += SPEED * dirY;

        // Check if ball reaches the center of the next cell
        // if (Math.round(ballCol + 1) != Math.round(ballCol) || Math.round(ballRow + 1)
        // != Math.round(ballRow)) {
        ballCol += dirX;
        ballRow += dirY;
        pathIndex++;
        // }

        repaint();
    }

    public static void playBackgroundMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                // clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void run(int[][] maze) {
        JFrame frame = new JFrame("Moving Ball");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MazeSolver solver = new MazeSolver(maze);
        String path = String.join(",", solver.solve());
        frame.add(new MazeUI(maze, path));
        JButton solveButton = new JButton("Generate Graph");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call the solver to start solving the maze
                // For example:
                frame.dispose();
                MazeSolver solver = new MazeSolver(maze);
                String path = String.join(",", solver.solve());
                // mazeUI.setPath(path);

                // Open PathTrackerUI
                PathTrackerUI pathTrackerUI = new PathTrackerUI(maze, path, solver.getExploredPaths());
                pathTrackerUI.run(maze, path, solver.getExploredPaths());
            }
        });
        frame.add(solveButton, BorderLayout.SOUTH);
        frame.setSize(CELL_SIZE * maze[0].length, CELL_SIZE * maze.length);
        frame.setVisible(true);

    }

}