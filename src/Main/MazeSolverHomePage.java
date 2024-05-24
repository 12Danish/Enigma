package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MazeSolverHomePage extends JFrame {
    private JButton startButton;
    private JLabel logoLabel;
    private JLabel nameLabel;

    public MazeSolverHomePage() {
        // Set up the frame
        setTitle("Home Page");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        AnimatedBackgroundPanel bg = new AnimatedBackgroundPanel();
        setContentPane(bg);
        bg.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Create and add the logo
        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setIcon(
                resizeImageIcon("/Users/fatimatuzzahra/Downloads/MazeSolver-3/src/Resources/LOGO.png", 200, 200)); // Resize
                                                                                                                   // the
                                                                                                                   // image
                                                                                                                   // to
                                                                                                                   // 200x200
                                                                                                                   // pixels
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        bg.add(logoLabel, gbc);

        // Create and add the name label
        nameLabel = new JLabel("Enigma", JLabel.CENTER);
        nameLabel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
        nameLabel.setOpaque(true);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 48));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        bg.add(nameLabel, gbc);

        // Create and add the start button
        startButton = new JButton("Start") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(102, 0, 153).darker()); // pruple color for button
                } else {
                    g.setColor(new Color(102, 0, 153));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                // Do nothing to keep the rounded shape
            }
        };
        startButton.setFont(new Font("Serif", Font.PLAIN, 24));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.MAGENTA);
        startButton.setFocusPainted(false); // Remove focus border
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside the button
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.addActionListener(new StartButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        bg.add(startButton, gbc);

        // Debugging output
        System.out.println("Button added to the layout");
    }

    // Method to resize the image icon
    private ImageIcon resizeImageIcon(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Action listener for the start button
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                ThemeSelectionPage themePage = new ThemeSelectionPage();
                themePage.setVisible(true);
                MazeSolverHomePage.this.dispose(); // Close the home page
            });
        }
    }

    public static void main(String[] args) {
        // Create and display the home page
        SwingUtilities.invokeLater(() -> {
            MazeSolverHomePage homePage = new MazeSolverHomePage();
            homePage.setVisible(true);

        });
    }
}

class AnimatedBackgroundPanel extends JPanel {
    private final int STAR_COUNT = 100;
    private final int MAX_STAR_SIZE = 3;
    private final int TIMER_DELAY = 50; // Milliseconds
    private ArrayList<Star> stars;
    private Random random;

    public AnimatedBackgroundPanel() {
        this.setPreferredSize(new Dimension(1500, 1000));
        this.setBackground(Color.BLACK);
        stars = new ArrayList<>();
        random = new Random();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initializeStars();
            }
        });
    }

    private void initializeStars() {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0 || MAX_STAR_SIZE <= 0) {
            return;
        }

        stars.clear(); // Clear any existing stars
        for (int i = 0; i < STAR_COUNT; i++) {
            stars.add(new Star(random.nextInt(width), random.nextInt(height), random.nextInt(MAX_STAR_SIZE) + 1));
        }

        Timer timer = new Timer(TIMER_DELAY, e -> moveStars());
        timer.start();
    }

    private void moveStars() {
        for (Star star : stars) {
            star.y += star.size;
            if (star.y > getHeight()) {
                star.y = 0;
                star.x = random.nextInt(getWidth());
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        for (Star star : stars) {
            g.fillOval(star.x, star.y, star.size, star.size);
        }
    }

    private static class Star {
        int x, y, size;

        public Star(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
    }
}
