package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import MazeUI.*;
import CustomMaze.*;

public class ThemeSelectionPage extends JFrame {

        private JButton backButton = new JButton("Back");
        private JButton[] themeButtons = new JButton[10];
        private int[][][] matrices = new int[10][][];
        private int[][] selectedArray;
        private String[] mazeNames = {
                        "Maze 1", "Maze 2", "Maze 3", "Maze 4",
                        "Maze 5", "Maze 6", "Maze 7", "Maze 8",
                        "Maze 9", "Maze 10"
        };

        public ThemeSelectionPage() {

                // Set up the frame
                setTitle("MAZE PAGE");
                setSize(1500, 1000);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                AnimatedBackgroundPanel bg = new AnimatedBackgroundPanel();
                setContentPane(bg);
                bg.setLayout(new GridBagLayout());
                // Add title label
                JLabel title = new JLabel("PICK YOUR MAZE");
                title.setFont(new Font("Serif", Font.BOLD, 24)); // Increase font size and change style
                title.setBackground(Color.BLACK);
                title.setForeground(Color.WHITE);
                title.setOpaque(true); // Make background color visible

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

                // Set GridBagConstraints for the title
                gbc.gridx = 3;
                gbc.gridy = 0;
                gbc.gridwidth = 1; // Span across 5 columns
                gbc.insets = new Insets(10, 0, 20, 0); // Add some padding
                gbc.anchor = GridBagConstraints.NORTH; // Position at the top
                gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
                bg.add(title, gbc);

                // Create and add back button

                backButton = createRoundedButton("Back");
                backButton.setFont(new Font("Serif", Font.PLAIN, 15));
                 backButton.setBackground(new Color(102, 0, 153)); // Set background color
                backButton.setForeground(Color.WHITE);
                backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // Open the home page and close the theme selection page
                                SwingUtilities.invokeLater(() -> {
                                        MazeSolverHomePage homePage = new MazeSolverHomePage();
                                        homePage.setVisible(true);
                                        ThemeSelectionPage.this.dispose();
                                });
                        }
                });
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.NORTHWEST; // Position the back button to the top left corner
                bg.add(backButton, gbc);

                // Create and add theme buttons
                String[] imagePaths = {
                        
                                "src/Resources/maze1-modified.png",
                                "src/Resources/maze2-modified.png",
                                "src/Resources/maze3-modified.png",
                                "src/Resources/maze4-modified.png",
                                "src/Resources/maze5-modified.png",
                                "src/Resources/maze6-modified.png",
                                "src/Resources/maze7-modified.png",
                                "src/Resources/maze8-modified.png",
                                "src/Resources/maze9-modified.png",
                                "src/Resources/Custom_maze.jpeg",
                        
                };

                for (int i = 0; i < themeButtons.length; i++) {
                        themeButtons[i] = createRoundedButton(""); // No text if using images
                        themeButtons[i].setPreferredSize(new Dimension(200, 200));
                        themeButtons[i].setToolTipText(mazeNames[i]); // Setting tooltip for clarity
                        themeButtons[i].setIcon(resizeImageIcon(imagePaths[i], 200, 200)); // Assuming imagePaths
                                                                                           // includes proper
                                                                                           // paths
                        themeButtons[i].addActionListener(new ThemeButtonListener());
                        GridBagConstraints gbc1 = new GridBagConstraints();
                        gbc1.gridx = i % 5; // Layout configuration
                        gbc1.gridy = i / 5 + 1;
                        gbc1.insets = new Insets(10, 10, 10, 10);
                        bg.add(themeButtons[i], gbc1);
                }

                initializeMatrices();
        }

        // Method to resize the image icon
        private ImageIcon resizeImageIcon(String path, int width, int height) {
                try {
                        BufferedImage img = ImageIO.read(new File(path));
                        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        return new ImageIcon(scaledImg);
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
        }

        // Method to create a rounded button
        private JButton createRoundedButton(String text) {
                JButton button = new JButton(text) {
                        @Override
                        protected void paintComponent(Graphics g) {
                                Graphics2D g2 = (Graphics2D) g.create();
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                Shape round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
                                g2.setColor(getBackground());

                                g2.fill(round);
                                super.paintComponent(g2);
                                g2.dispose();
                        }

                        @Override
                        protected void paintBorder(Graphics g) {
                                Graphics2D g2 = (Graphics2D) g.create();

                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                Shape round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
                                g2.setColor(Color.WHITE);

                                g2.draw(round);
                                g2.dispose();
                        }

                        @Override
                        public boolean contains(int x, int y) {
                                Shape round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
                                return round.contains(x, y);
                        }
                };
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setOpaque(false);
                return button;
        }

        // array stores all sample mazes
        private void initializeMatrices() {
                // Example arrays to initialize the matrices array
                matrices[0] = new int[][] {

                                { 6, 3, 12, 14, 10, 7, 12, 4 },
                                { 6, 9, 2, 6, 10, 10, 7, 4 },
                                { -1, 7, 7, 2, 5, 4, 1, 7 },
                                { 11, 13, 5, 1, 15, 11, 4, 0 },
                                { 11, 9, 5, 12, 11, 8, 0, 10 },
                                { 10, 14, 9, 15, 11, 11, 15, 14 },
                                { 13, 13, 14, 13, 2, 11, 6, 3 },
                                { 15, 8, 2, 4, 2, -2, 4, 8 }
                };
                matrices[1] = new int[][] {

                                { -1, 2, 3, 3, 9, 10, 10, 4, 3, 10, 13, 9, 7, 5, 5, 7, 6, 14, 9, 13, 10, 11, 9, 12, 7,
                                                13, 1, 8, 0,
                                                14 },
                                { 7, 11, 10, 11, 11, 3, 15, 3, 4, 7, 4, 7, 14, 9, 0, 11, 14, 9, 8, 5, 2, 12, 13, 7, 8,
                                                5, 10, 6, 0,
                                                13 },
                                { 4, 4, 9, 13, 3, 5, 6, 8, 0, 14, 5, 6, 2, 7, 14, 4, 14, 8, 4, 8, 9, 3, 8, 4, 9, 8, 2,
                                                14, 3, 8 },
                                { 9, 10, 5, 1, 6, 1, 10, 1, 10, 0, 15, 10, 10, 11, 10, 2, 5, 7, 8, 6, 3, 5, 4, 7, 0, 0,
                                                12, 14, 10, 5 },
                                { 9, 11, 1, 4, 0, 6, 0, 3, 8, 0, 3, 5, 5, 11, 14, 12, 7, 0, 6, 0, 15, 12, 13, 6, 2, 10,
                                                7, 2, 13, 15 },
                                { 13, 12, 0, 12, 12, 13, 15, 7, 2, 1, 0, 13, 2, 14, 1, 1, 5, 11, 0, 1, 13, 5, 2, 14, 3,
                                                0, 7, 12, 6,
                                                6 },
                                { 2, 15, 9, 9, 2, 13, 1, 11, 0, 6, 6, 11, 15, 1, 6, 12, 7, 0, 8, 6, 2, 7, 6, 10, 14, 12,
                                                7, 5, 8, 14 },
                                { 4, 2, 2, 0, 11, 14, 7, 1, 2, 15, 7, 13, 15, 7, 13, 10, 15, 3, 3, 4, 5, 4, 13, 14, 6,
                                                15, 1, 6, 9, 6 },
                                { 0, 6, 4, 2, 15, 12, 2, 3, 12, 11, 1, 1, 0, 14, 4, 3, 10, 9, 14, 1, 2, 6, 6, 11, 3, 4,
                                                4, 5, 15, 13 },
                                { 8, 1, 4, 11, 2, 3, 9, 5, 11, 7, 0, 9, 13, 7, 10, 13, 6, 15, 15, 0, 1, 2, 0, 13, 8, 7,
                                                1, 12, 8, 6 },
                                { 8, 5, 5, 10, 13, 3, 9, 9, 12, 12, 1, 6, 4, 10, 5, 1, 15, 4, 9, 3, 8, 5, 1, 0, 4, 8, 5,
                                                9, 11, 6 },
                                { 4, 9, 5, 4, 10, 8, 14, 5, 10, 7, 1, 11, 12, 8, 4, 6, 9, 12, 0, 9, 4, 11, 13, 9, 13,
                                                15, 1, 11, 5,
                                                10 },
                                { 0, 2, 4, 3, 2, 9, 12, 4, 10, 7, 10, 15, 3, 0, 10, 6, 7, 2, 5, 1, 6, 14, 7, 5, 5, 4, 7,
                                                0, 9, 3 },
                                { 14, 3, 2, 2, 3, 10, 0, 13, 10, 12, 10, 1, 8, 9, 1, 4, 8, 8, 7, 0, 0, 8, 12, 10, 7, 9,
                                                8, 6, 2, 2 },
                                { 1, 4, 11, 11, 2, 13, 5, 1, 11, 2, 4, 11, 10, 10, 0, 12, 0, 9, 8, 6, 0, 14, 11, 13, 15,
                                                7, 2, 7, 14,
                                                4 },
                                { 0, 6, 4, 5, 15, 5, 7, 8, 1, 8, 11, 14, 10, 9, 8, 12, 9, 14, 15, 9, 15, 6, 0, 6, 9, 4,
                                                5, 9, 6, 13 },
                                { 14, 4, 6, 8, 2, 15, 7, 1, 10, 6, 12, 1, 14, 13, 3, 6, 2, 1, 10, 9, 6, 0, 13, 14, 7, 2,
                                                11, 11, 12,
                                                1 },
                                { 2, 4, 10, 7, 9, 6, 6, 11, 6, 14, 7, 0, 3, 12, 5, 8, 12, 1, 0, 15, 12, 9, 11, 13, 1, 6,
                                                2, 9, 1, 13 },
                                { 10, 6, 12, 12, 1, 15, 15, 2, 10, 6, 13, 3, 12, 13, 0, 12, 13, 4, 11, 8, 15, 7, 11, 14,
                                                8, 9, 13, 8,
                                                14, 6 },
                                { 3, 4, 0, 13, 0, 2, 12, 13, 7, 13, 1, 3, 1, 13, 4, 8, 15, 0, 4, 13, 14, 8, 14, 0, 8, 7,
                                                3, 11, 10, -2 }

                };
                matrices[2] = new int[][] {
                                { 6, 1, 3, 8, 11, -2, 1 },
                                { 9, 8, 9, 4, 1, 3, 11 },
                                { 14, -1, 6, 11, 12, 7, 14 },
                                { 2, 13, 0, 3, 1, 7, 3 },
                                { 1, 13, 5, 5, 9, 3, 5 }
                };
                matrices[3] = new int[][] {
                                { 12, 14, 1, 0, 11, -1, 9, 13, 3 },
                                { 13, 14, 15, 14, 7, 13, 6, 11, 8 },
                                { 13, 7, 4, 15, 12, 1, 14, 4, 7 },
                                { 9, 8, 11, 11, 11, 12, 8, 12, 14 },
                                { -2, 0, 8, 6, 8, 7, 0, 11, 7 }
                };
                matrices[4] = new int[][] {
                                { 7, 14, 10, 2, 0, 7, 2, 2, 0, 10, 4, 9 },
                                { 6, 9, 8, 11, 6, 8, 7, 11, 1, 0, 15, 6 },
                                { 6, 13, 7, 4, 2, 11, 7, 5, 10, 2, 0, 2 },
                                { 4, 14, 13, 2, 0, 4, 9, 6, 14, 13, 6, 10 },
                                { 8, 14, 14, 9, 9, 11, 12, 2, 14, 15, 6, 0 },
                                { 3, 12, -2, 3, 13, 4, 6, 6, 12, 14, 10, 12 },
                                { 3, 12, 15, 6, 10, 2, 5, 11, 1, 9, 12, 13 },
                                { 8, 4, 5, 11, 11, 11, 11, 3, 13, 13, 10, 15 },
                                { 9, 6, 11, 8, 6, 13, 0, 0, 8, 10, 13, 8 },
                                { 3, 12, 8, 2, 6, 5, 7, 10, 8, 13, 4, 0 },
                                { 2, 9, 11, 7, 14, 10, 5, 15, 15, 7, 8, 3 },
                                { 0, 13, 0, 9, 3, 11, 13, 6, 1, 2, -1, 4 }
                };
                matrices[5] = new int[][] {
                                { 0, 7, 0, 10, 0, -1 },
                                { 1, 11, 5, 6, 4, 15 },
                                { 0, 0, 2, 13, 1, 4 },
                                { 11, 9, 15, 5, 15, 6 },
                                { 3, 6, 10, 7, 10, 0 },
                                { -2, 5, 7, 4, 3, 1 }
                };
                matrices[6] = new int[][] {
                                { 5, 5, 10, 14, 15, 15, 0, 8, 11, 10, 5, 15, 12, 2, 3 },
                                { 11, 10, 3, 2, 9, 2, 14, 2, 3, 15, 6, 3, 8, 0, 7 },
                                { 15, 6, 1, 7, 0, 10, 11, 8, 8, 1, 6, 14, 13, 9, 2 },
                                { 6, 11, 15, 9, 15, 8, 3, 11, 0, 1, 0, 15, 13, 11, 4 },
                                { 4, 15, 10, 6, 8, 8, 2, 2, 15, 15, 2, 3, 7, 5, 7 },
                                { 0, 7, 3, -1, 0, 7, 3, 5, 7, 3, 13, 2, 15, 13, 8 },
                                { 2, 8, 12, 1, 15, 13, 1, 1, 5, 2, 15, 12, 8, 3, 0 },
                                { 3, 0, 13, 4, 15, 3, 7, 15, 7, 6, 2, 0, 15, 0, 15 },
                                { 11, 10, 2, 5, 6, 5, 13, 13, 5, 5, 12, 2, 5, 7, 10 },
                                { 10, 1, 4, 13, 0, 11, 14, 15, 0, 4, 11, 12, 15, 2, 3 },
                                { 2, 15, 0, 15, 0, 11, 11, 13, 12, 13, 4, 5, 2, 11, 8 },
                                { 4, 7, 12, 0, 13, 4, 14, 2, 11, 0, 3, 14, 12, 4, 15 },
                                { 6, 0, 15, 2, 15, 1, 8, 9, 14, 5, 9, 2, 7, 13, 7 },
                                { 1, 15, 14, 5, 6, 1, 14, 10, 9, 1, 9, 0, 7, 0, 8 },
                                { 10, 5, 15, 6, 12, 9, 6, 9, 2, 1, 8, -2, 12, 11, 9 }
                };
                matrices[7] = new int[][] {
                                { 2, 2, 3, 3, 9, 10, 10, 4, 3, 10, 13, 9, 7, 5, 5, 7, 6, 14, 9, 13, 10, 11, 9, 12, 7,
                                                13, 1, 8, 0, 14 },
                                { 7, 11, 10, 11, 11, 3, 15, 3, 4, 7, 4, 7, 14, 9, 0, 11, 14, 9, 8, 5, 2, 12, 13, 7, 8,
                                                5, 10, 6, 0,
                                                13 },
                                { 4, 4, 9, 13, 3, 5, 6, 8, 0, 14, 5, 6, 2, 7, 14, 4, 14, 8, 4, 8, 9, 3, 8, 4, 9, 8, 2,
                                                14, -2, 8 },
                                { 9, 10, 5, 1, 6, 1, 10, 1, 10, 0, 15, 10, 10, 11, 10, 2, 5, 7, 8, 6, 3, 5, 4, 7, 0, 0,
                                                12, 14, 10, 5 },
                                { 9, 11, 1, 4, 0, 6, 0, 3, 8, 0, 3, 5, 5, 11, 14, 12, 7, 0, 6, 0, 15, 12, 13, 6, 2, 10,
                                                7, 2, 13, 15 },
                                { 13, 12, 0, 12, 12, 13, 15, 7, 2, 1, 0, 13, 2, 14, 1, 1, 5, 11, 0, 1, 13, 5, 2, 14, 3,
                                                0, 7, 12, 6,
                                                6 },
                                { 2, 15, 9, 9, 2, 13, 1, 11, 0, 6, 6, 11, 15, 1, 6, 12, 7, 0, 8, 6, 2, 7, 6, 10, 14, 12,
                                                7, 5, 8, 14 },
                                { 4, 2, 2, 0, 11, 14, 7, 1, 2, 15, 7, 13, 15, 7, 13, 10, 15, 3, 3, 4, 5, 4, 13, 14, 6,
                                                15, 1, 6, 9, 6 },
                                { 0, 6, 4, 2, -1, 12, 2, 3, 12, 11, 1, 1, 0, 14, 4, 3, 10, 9, 14, 1, 2, 6, 6, 11, 3, 4,
                                                4, 5, 15, 13 },
                                { 8, 1, 4, 11, 2, 3, 9, 5, 11, 7, 0, 9, 13, 7, 10, 13, 6, 15, 15, 0, 1, 2, 0, 13, 8, 7,
                                                1, 12, 8, 6 },
                                { 8, 5, 5, 10, 13, 3, 9, 9, 12, 12, 1, 6, 4, 10, 5, 1, 15, 4, 9, 3, 8, 5, 1, 0, 4, 8, 5,
                                                9, 11, 6 },
                                { 4, 9, 5, 4, 10, 8, 14, 5, 10, 7, 1, 11, 12, 8, 4, 6, 9, 12, 0, 9, 4, 11, 13, 9, 13,
                                                15, 1, 11, 5,
                                                10 },
                                { 0, 2, 4, 3, 2, 9, 12, 4, 10, 7, 10, 15, 3, 0, 10, 6, 7, 2, 5, 1, 6, 14, 7, 5, 5, 4, 7,
                                                0, 9, 3 },
                                { 14, 3, 2, 2, 3, 10, 0, 13, 10, 12, 10, 1, 8, 9, 1, 4, 8, 8, 7, 0, 0, 8, 12, 10, 7, 9,
                                                8, 6, 2, 2 },
                                { 1, 4, 11, 11, 2, 13, 5, 1, 11, 2, 4, 11, 10, 10, 0, 12, 0, 9, 8, 6, 0, 14, 11, 13, 15,
                                                7, 2, 7, 14,
                                                4 },
                                { 0, 6, 4, 5, 15, 5, 7, 8, 1, 8, 11, 14, 10, 9, 8, 12, 9, 14, 15, 9, 15, 6, 0, 6, 9, 4,
                                                5, 9, 6, 13 },
                                { 14, 4, 6, 8, 2, 15, 7, 1, 10, 6, 12, 1, 14, 13, 3, 6, 2, 1, 10, 9, 6, 0, 13, 14, 7, 2,
                                                11, 11, 12,
                                                1 },
                                { 2, 4, 10, 7, 9, 6, 6, 11, 6, 14, 7, 0, 3, 12, 5, 8, 12, 1, 0, 15, 12, 9, 11, 13, 1, 6,
                                                2, 9, 1, 13 },
                                { 10, 6, 12, 12, 1, 15, 15, 2, 10, 6, 13, 3, 12, 13, 0, 12, 13, 4, 11, 8, 15, 7, 11, 14,
                                                8, 9, 13, 8,
                                                14, 6 },
                                { 3, 4, 0, 13, 0, 2, 12, 13, 7, 13, 1, 3, 1, 13, 4, 8, 15, 0, 4, 13, 14, 8, 14, 0, 8, 7,
                                                3, 11, 10, 9 }
                };
                matrices[8] = new int[][] {
                                { 12, 3, 9, 10, 2, 3, 11, 2, 8, 4, 2, 0, 15, 11, 14, 3, 15, 12, 10, 8, 2, 0, 11, 4, 3,
                                                11, 2, 3, 13, 12,
                                                8, 6, 9, 14, 13, 4, 2, 10, 10, 9, 8, 14, 4, 12, 6, 1, 6, 15, 15, 1 },
                                { 9, 8, -1, 0, 13, 5, 12, 11, 7, 5, 3, 4, 6, 0, 4, 7, 1, 5, 0, 3, 2, 1, 14, 6, 7, 2, 6,
                                                4, 3, 8, 9, 15,
                                                15, 2, 14, 11, 12, 9, 12, 6, 13, 12, 3, 10, 15, 9, 4, 5, 11, 2 },
                                { 2, 9, 1, 5, 6, 0, 8, 12, 13, 7, 9, 2, 0, 5, 8, 3, 13, 2, 13, 3, 11, 11, 4, 12, 8, 11,
                                                7, 3, 11, 1, 9,
                                                14, 2, 14, 6, 10, 12, 4, 12, 13, 3, 8, 4, 2, 12, 11, 8, 1, 15, 6 },
                                { 13, 3, 14, 15, 0, 9, 6, 11, 8, 11, 7, 1, 0, 5, 0, 7, 11, 4, 5, 0, 1, 13, 5, 1, 2, 13,
                                                14, 3, 7, 1, 4,
                                                4, 9, 14, 9, 10, 15, 14, 1, 6, 7, 9, 5, 2, 11, 1, 14, 9, 4, 7 },
                                { 7, 1, 12, 6, 13, 15, 12, 14, 14, 2, 10, 5, 3, 8, 5, 1, 4, 7, 7, 3, 5, 4, 6, 8, 7, 3,
                                                2, 12, 0, 10, 9,
                                                4, 2, 1, 15, 8, 2, 12, 7, 11, 4, 6, 10, 10, 4, 14, 8, 11, 5, 0 },
                                { 3, 0, 14, 9, 5, 11, 10, 8, 14, 13, 14, 8, 8, 10, 13, 3, 8, 7, 1, 14, 9, 12, 7, 5, 0,
                                                4, 3, 1, 7, 12,
                                                -2, 13, 3, 14, 14, 12, 13, 6, 7, 5, 13, 12, 9, 9, 6, 11, 9, 10, 8, 7 },
                                { 12, 11, 8, 9, 2, 14, 1, 6, 11, 4, 8, 0, 6, 6, 2, 15, 9, 5, 5, 2, 1, 2, 9, 0, 14, 8, 3,
                                                8, 13, 3, 12,
                                                11, 1, 2, 2, 14, 3, 9, 13, 3, 14, 1, 0, 8, 5, 14, 7, 11, 7, 0 },
                                { 2, 3, 6, 11, 8, 1, 9, 7, 15, 9, 5, 4, 5, 3, 2, 3, 4, 13, 10, 7, 0, 11, 4, 2, 7, 9, 5,
                                                12, 14, 1, 13,
                                                10, 5, 15, 7, 11, 1, 10, 8, 13, 7, 14, 3, 10, 6, 14, 8, 11, 13, 2 }
                };
                matrices[9] = new int[][] {
                                { 6, 8, 3, 12, -2 },
                                { -1, 7, 15, 13, 15 }
                };
        };

        public void printMatrix() {
                if (matrices == null) {
                        System.out.println("Matrix is null");
                        return;
                }

                for (int i = 0; i < matrices.length; i++) {
                        for (int j = 0; j < matrices[i].length; j++) {
                                System.out.print(matrices[i][j] + " ");
                        }
                        System.out.println();
                }
        }

        public int[][] getSelectedArray() {
                return selectedArray;
        }

        private class ThemeButtonListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                        JButton source = (JButton) e.getSource();
                        for (int i = 0; i < themeButtons.length; i++) {
                                if (source == themeButtons[i] && source != themeButtons[9]) {
                                        selectedArray = matrices[i];
                                        MazeUI maze = new MazeUI(selectedArray, null);
                                        maze.run(selectedArray);
                                }
                                if (source == themeButtons[9]) {
                                        CustomMazeCreator custom = new CustomMazeCreator();
                                        custom.run();
                                }
                        }
                }
        }

        public void printMatrix(int[][] matrix) {
                for (int[] row : matrix) {
                        for (int element : row) {
                                System.out.print(element + " ");
                        }
                        System.out.println();
                }
        }

        // public static void main(String[] args) {
        // SwingUtilities.invokeLater(new Runnable() {
        // @Override
        // public void run() {
        // ThemeSelectionPage themeSelectionPage = new ThemeSelectionPage();
        // themeSelectionPage.setVisible(true);

        // int[][] maze = themeSelectionPage.getSelectedArray();

        // MazeSolver solver = new MazeSolver(maze);
        // String path = String.join(",", solver.solve());

        // MazeUI ui = new MazeUI(maze, path);

        // // Example of how to get the selected array
        // // int[][] selectedArray = themeSelectionPage.getSelectedArray();
        // }
        // });
        // }

}
