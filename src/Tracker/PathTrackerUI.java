package Tracker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import Main.MazeSolverHomePage;

public class PathTrackerUI extends JPanel implements ActionListener {
    private final int[][] maze;
    private final String path;
    private final List<String> exploredPaths;
    private float ballRow;
    private float ballCol;
    private int pathIndex = 0;
    private static final int CELL_SIZE = 45;
    private static final float SPEED = 0.000001f; // Speed of the ball's movement
    private Timer timer;
    private JButton homeButton;

    public PathTrackerUI(int[][] maze, String path, List<String> exploredPaths) {
        this.maze = maze;
        this.path = path;
        this.exploredPaths = exploredPaths;

        setLayout(null); // Use null layout to place components absolutely

        // Initialize ball position
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == -1) {
                    ballRow = row;
                    ballCol = col;
                    break;
                }
            }
        }

        // Create and add home button
        homeButton = new JButton("Home");
        homeButton.setBounds(10, 10, 80, 30); // Set button position and size
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(() -> {
                    MazeSolverHomePage homePage = new MazeSolverHomePage();

                    homePage.setVisible(true);

                });
            }
        });

        add(homeButton);

        // Initialize and start timer
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        drawGrid(g);
        drawExploredPaths(g);
        drawPath(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i <= getWidth(); i += CELL_SIZE) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i <= getHeight(); i += CELL_SIZE) {
            g.drawLine(0, i, getWidth(), i);
        }
    }

    public void switchToNewTab(JPanel newPanel) {
        Container container = this.getParent();
        while (!(container instanceof JTabbedPane) && container != null) {
            container = container.getParent();
        }

        if (container instanceof JTabbedPane) {
            JTabbedPane tabbedPane = (JTabbedPane) container;
            int currentIndex = tabbedPane.indexOfComponent(this);
            if (currentIndex != -1) {
                tabbedPane.removeTabAt(currentIndex); // Remove current tab
                tabbedPane.addTab("New Tab", newPanel); // Add new tab with the desired panel
            } else {
                System.err.println("Error: Panel not found in any tab.");
            }
        } else {
            System.err.println("Error: Could not find parent JTabbedPane.");
        }
    }

    private void drawExploredPaths(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(2));

        for (String exploredPath : exploredPaths) {
            int curRow = (int) ballRow;
            int curCol = (int) ballCol;
            int prevX = curCol * CELL_SIZE + CELL_SIZE / 2;
            int prevY = curRow * CELL_SIZE + CELL_SIZE / 2;

            int curX, curY;
            for (char dir : exploredPath.toCharArray()) {
                g2d.setColor(Color.BLUE);
                int dirX = 0, dirY = 0;
                if (dir == 'E')
                    dirX = 1;
                else if (dir == 'S')
                    dirY = 1;
                else if (dir == 'W')
                    dirX = -1;
                else if (dir == 'N')
                    dirY = -1;

                curRow += dirY;
                curCol += dirX;

                curX = curCol * CELL_SIZE + CELL_SIZE / 2;
                curY = curRow * CELL_SIZE + CELL_SIZE / 2;

                g2d.drawLine(prevX, prevY, curX, curY);
                g2d.fillOval(curX - 5, curY - 5, 10, 10);

                prevX = curX;
                prevY = curY;
            }
        }
    }

    private void drawPath(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));

        int prevX = (int) (ballCol * CELL_SIZE) + CELL_SIZE / 2;
        int prevY = (int) (ballRow * CELL_SIZE) + CELL_SIZE / 2;

        int curX, curY;
        for (int i = 0; i < pathIndex; i++) {
            char dir = path.charAt(i);
            if (dir == ',') {
                g2d.setColor(Color.RED);
                g2d.fillOval(prevX - 5, prevY - 5, 10, 10);
                continue;
            }
            g2d.setColor(Color.GREEN);
            int dirX = 0, dirY = 0;
            if (dir == 'E')
                dirX = 1;
            else if (dir == 'S')
                dirY = 1;
            else if (dir == 'W')
                dirX = -1;
            else if (dir == 'N')
                dirY = -1;

            curX = prevX + dirX * CELL_SIZE;
            curY = prevY + dirY * CELL_SIZE;

            g2d.drawLine(prevX, prevY, curX, curY);
            g2d.fillOval(curX - 5, curY - 5, 10, 10);

            prevX = curX;
            prevY = curY;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pathIndex >= path.length()) {
            timer.stop();
            return;
        }

        char dir = path.charAt(pathIndex);

        int dirX = 0;
        int dirY = 0;
        if (dir == ',') {
            pathIndex++;
            repaint();
            return;
        } else if (dir == 'E') {
            dirX = 1;
        } else if (dir == 'S') {
            dirY = 1;
        } else if (dir == 'W') {
            dirX = -1;
        } else if (dir == 'N') {
            dirY = -1;
        }

        // Update ball position smoothly
        ballCol += SPEED * dirX;
        ballRow += SPEED * dirY;

        ballCol = Math.round(ballCol);
        ballRow = Math.round(ballRow);
        pathIndex++;

        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_SIZE * maze[0].length, CELL_SIZE * maze.length);
    }

    public void run(int[][] maze, String path, List<String> exploredPaths) {
        JFrame frame = new JFrame("Path Tracker");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(CELL_SIZE * maze[0].length, CELL_SIZE * maze.length);

        frame.add(new PathTrackerUI(maze, path, exploredPaths));
        frame.pack();
        frame.setVisible(true);
    }
}
