package CustomMaze;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JFrame;
import Algo.MazeSolver;
import Tracker.PathTrackerUI;
import MazeUI.MazeUI;

public class CustomMazeCreator extends JFrame implements ActionListener {
    protected static final int CELL_SIZE = 45;
    private JTextField rowsField;
    private JTextField colsField;
    private JPanel gridPanel;
    private JButton createButton;
    private JButton okButton;
    private JTextField[][] mazeFields;
    private int[][] maze;

    public CustomMazeCreator() {
        setTitle("Custom Maze Creator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        inputPanel.add(new JLabel("Rows:"));
        rowsField = new JTextField(5);
        inputPanel.add(rowsField);

        inputPanel.add(new JLabel("Cols:"));
        colsField = new JTextField(5);
        inputPanel.add(colsField);

        createButton = new JButton("Create Grid");
        createButton.addActionListener(this);
        inputPanel.add(createButton);

        add(inputPanel, BorderLayout.NORTH);

        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        add(okButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            gridPanel.removeAll();
            gridPanel.setLayout(new GridLayout(rows, cols));
            mazeFields = new JTextField[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    mazeFields[i][j] = new JTextField(2);
                    gridPanel.add(mazeFields[i][j]);
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
        } else if (e.getSource() == okButton) {
            int rows = mazeFields.length;
            int cols = mazeFields[0].length;
            maze = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = Integer.parseInt(mazeFields[i][j].getText());
                }
            }
            displayMaze(maze);
        }
    }

    private void displayMaze(int[][] maze) {
        JFrame frame = new JFrame("Moving Ball");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MazeSolver solver = new MazeSolver(maze);
        String path = String.join(",", solver.solve());
        frame.add(new MazeUI(maze, path));
        JButton solveButton = new JButton("Solve Maze");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call the solver to start solving the maze
                // For example:
                MazeSolver solver = new MazeSolver(maze);
                String path = String.join(",", solver.solve());
                MazeUI show = new MazeUI(maze, null);
                show.run(maze);
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

    public void run() {
        SwingUtilities.invokeLater(() -> {
            new CustomMazeCreator().setVisible(true);
        });
    }
}
