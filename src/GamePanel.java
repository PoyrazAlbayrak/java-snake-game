import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Rectangle;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    boolean isGameOver = false;

    int snakeX = 100;
    int snakeY = 100;
    int boxSize = 50;
    int highScore;
    Image appleImage;
    boolean isGameStarted = false;
    Clip eatSound;
    Clip dieSound;

    File scoreFile = new File("highscore.txt");

    int foodX;
    int foodY;
    Random random = new Random();

    char direction = 'R';
    Timer timer;
    int delay = 100;

    ArrayList<Point> body = new ArrayList<>();

    public GamePanel() {
        readHighScore();
        this.setPreferredSize(new Dimension(600, 650));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        ImageIcon iia = new ImageIcon(getClass().getResource("apple.png"));
        appleImage = iia.getImage();

        eatSound = loadSound("eat.wav");
        dieSound = loadSound("die.wav");
        timer = new Timer(delay, this);


        spawnFood();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (isGameStarted) {
            g.setColor(new Color(80, 60, 30));
            g.fillRect(0, 0, 600, 50);
            g.setColor(new Color(30, 120, 30));
            g.fillRoundRect(10, 10, 580, 30, 20, 20);
            g.setColor(new Color(80, 60, 30));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("POINTS: " + body.size(), 100, 32);
            g.drawString("BEST SCORE: " + highScore, 375, 32);


            g.setColor(new Color(30, 120, 30));
            for (int i = 0; i <= 600; i += 100) {
                for (int y = 50; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }
            g.setColor(new Color(30, 120, 30));
            for (int i = 50; i <= 600; i += 100) {
                for (int y = 100; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }

            g.setColor(new Color(90, 70, 30));
            for (int i = 50; i <= 600; i += 100) {
                for (int y = 50; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }
            g.setColor(new Color(90, 70, 30));
            for (int i = 0; i <= 600; i += 100) {
                for (int y = 100; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }


            for (Point part : body) {
                g.setColor(new Color(190, 56, 250));
                g.fillRoundRect(part.x + 2, part.y + 2, boxSize - 4, boxSize - 4, 20, 20);

                g.setColor(new Color(29, 89, 56));
                g.drawRoundRect(part.x + 2, part.y + 2, boxSize - 4, boxSize - 4, 20, 20);
            }

            g.setColor(new Color(165, 5, 240));
            g.fillRect(snakeX + 2, snakeY + 2, boxSize - 4, boxSize - 4);

            g.setColor(new Color(20, 100, 20));
            g.drawRect(snakeX + 2, snakeY + 2, boxSize - 4, boxSize - 4);

            if (direction == 'R') {
                g.setColor(Color.BLACK);
                g.fillRect(snakeX + 35, snakeY + 10, 5, 5); // Üst göz
                g.fillRect(snakeX + 35, snakeY + 35, 5, 5); // Alt göz
            }
            if (direction == 'L') {
                g.setColor(Color.BLACK);
                g.fillRect(snakeX + 15, snakeY + 10, 5, 5); // Üst göz
                g.fillRect(snakeX + 15, snakeY + 35, 5, 5); // Alt göz
            }
            if (direction == 'U') {
                g.setColor(Color.BLACK);
                g.fillRect(snakeX + 10, snakeY + 15, 5, 5); // Üst göz
                g.fillRect(snakeX + 35, snakeY + 15, 5, 5); // Alt göz
            }
            if (direction == 'D') {
                g.setColor(Color.BLACK);
                g.fillRect(snakeX + 10, snakeY + 35, 5, 5); // Üst göz
                g.fillRect(snakeX + 35, snakeY + 35, 5, 5); // Alt göz
            }

            g.drawImage(appleImage, foodX, foodY, boxSize, boxSize, this);

            if (isGameOver) {
                g.setColor(new Color(0, 0, 0, 127));
                g.fillRect(0, 0, 600, 650);


                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("GAME OVER!", 175, 300);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Points: " + body.size(), 260, 350);
                g.setFont(new Font("Arial", Font.ITALIC, 20));
                g.drawString("Press ENTER to Restart", 195, 400);
            }


        } else {
            g.setColor(new Color(30, 120, 30));
            for (int i = 0; i <= 600; i += 100) {
                for (int y = 50; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }
            g.setColor(new Color(30, 120, 30));
            for (int i = 50; i <= 600; i += 100) {
                for (int y = 0; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }

            g.setColor(new Color(90, 70, 30));
            for (int i = 50; i <= 600; i += 100) {
                for (int y = 50; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }
            g.setColor(new Color(90, 70, 30));
            for (int i = 0; i <= 600; i += 100) {
                for (int y = 0; y <= 650; y += 100) {
                    g.fillRect(i, y, boxSize, boxSize);
                }
            }
            g.setColor(new Color(0, 0, 0, 220));
            g.fillRect(0, 0, 600, 650);

            g.setColor(new Color(20, 100, 20));
            //g.drawRect(170, 325, 260, 27);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Snake Game ", 150, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Created by Poyraz Albayrak", 175, 345);
            g.setFont(new Font("Arial", Font.ITALIC, 20));
            g.drawString("Press ENTER to start", 200, 500);

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            direction = 'R';
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            direction = 'L';
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            direction = 'U';
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            direction = 'D';
        }
        if (isGameOver && code == KeyEvent.VK_ENTER) {
            restartGame();
        }
        if (!isGameStarted && code == KeyEvent.VK_ENTER) {
            timer.start();
            isGameStarted = true;
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        body.add(new Point(snakeX, snakeY));

        if (direction == 'R') {
            snakeX = snakeX + boxSize;
        } else if (direction == 'L') {
            snakeX = snakeX - boxSize;
        } else if (direction == 'U') {
            snakeY = snakeY - boxSize;
        } else if (direction == 'D') {
            snakeY = snakeY + boxSize;
        }

        checkCollisions();

        Rectangle snakeRect = new Rectangle(snakeX, snakeY, boxSize, boxSize);
        Rectangle foodRect = new Rectangle(foodX, foodY, boxSize, boxSize);

        if (snakeRect.intersects(foodRect)) {
            spawnFood();
            playSound(eatSound);
            System.out.println("Apple is eaten. Snake growing! " + (body.size()));
        } else {
            body.removeFirst();
        }

        repaint();

    }

    public void spawnFood() {
        foodX = random.nextInt(12) * boxSize;
        foodY = random.nextInt(12) * boxSize + 50;

        for (Point part : body) {
            if (part.x == foodX && part.y == foodY) {
                spawnFood();
                return;
            }
        }
        if (foodX == snakeX && foodY == snakeY) {
            spawnFood();
        }
    }

    public void checkCollisions() {
        if (snakeX < 0 || snakeX >= 600 || snakeY < 50 || snakeY >= 650) {
            isGameOver = true;
        }

        for (Point part : body) {
            if (part.x == snakeX && part.y == snakeY) {
                isGameOver = true;
                break;
            }
        }

        if (isGameOver) {
            timer.stop();
            int currentScore = body.size() - 1;
            playSound(dieSound);

            if (currentScore > highScore) {
                highScore = currentScore;
                saveHighScore();
            }
        }
    }

    public Clip loadSound(String soundFile) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playSound(Clip clip) {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0);
                clip.start();
            }).start();
        }
    }


    public void readHighScore() {
        if (!scoreFile.exists()) {
            return;
        }
        try {
            Scanner scanner = new Scanner(scoreFile);
            if (scanner.hasNextInt()) {
                highScore = scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHighScore() {
        try {
            FileWriter writer = new FileWriter(scoreFile);
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restartGame() {
        snakeX = 100;
        snakeY = 100;

        direction = 'R';

        body.clear();

        isGameOver = false;

        spawnFood();
        timer.start();
        repaint();
    }

}
