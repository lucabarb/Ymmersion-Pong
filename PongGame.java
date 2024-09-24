import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int paddleWidth = 10;
    private int paddle1Height = 120, paddle2Height = 120;
    private int paddle1Y = 150, paddle2Y = 150;
    private int ballDiameter = 50;
    private int ScoreP1 = 0, ScoreP2 = 0;

    private ArrayList<Ball> balls; // Multiple balls for the "multiply" bonus
    private static final int INITIAL_BALLS = 1; // Start with 1 ball

    // Bonus Variables
    private int bonusX = -100, bonusY = -100, bonusWidth = 100, bonusHeight = 100;
    private boolean bonusActive = false;
    private int bonusType = 0; // 1 = speed, 2 = slow, 3 = multiply balls
    private Image speedImage, slowImage, multiplyImage;

    // Timers
    private Timer timer;
    private Timer paddle1MoveTimer, paddle2MoveTimer;
    private Timer bonusTimer;

    // Paddle movement flags
    private boolean paddle1MovingUp = false, paddle1MovingDown = false;
    private boolean paddle2MovingUp = false, paddle2MovingDown = false;

    private Random random;

    public PongGame() {
        this.setPreferredSize(new Dimension(500, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        balls = new ArrayList<>();
        for (int i = 0; i < INITIAL_BALLS; i++) {
            balls.add(new Ball(250, 150, 5, 5, ballDiameter)); // Initial ball
        }

        timer = new Timer(10, this);
        timer.start();

        paddle1MoveTimer = new Timer(10, evt -> movePaddle1());
        paddle2MoveTimer = new Timer(10, evt -> movePaddle2());

        random = new Random();

        // Timer to generate bonuses every 7 seconds
        bonusTimer = new Timer(7000, evt -> spawnBonus());
        bonusTimer.start();

        // Load bonus images
        speedImage = new ImageIcon("la-vitesse.png").getImage();
        slowImage = new ImageIcon("lent.png").getImage();
        multiplyImage = new ImageIcon("fleches-multiples.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        // Draw paddles
        g.fillRect(0, paddle1Y, paddleWidth, paddle1Height);
        g.fillRect(getWidth() - paddleWidth, paddle2Y, paddleWidth, paddle2Height);
        
        // Draw balls
        for (Ball ball : balls) {
            g.fillOval(ball.x, ball.y, ballDiameter, ballDiameter);
        }
        
        // Draw scores
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1: " + ScoreP1, 50, 30);
        g.drawString("Player 2: " + ScoreP2, getWidth() - 150, 30);
        
        // Draw bonus if active
        if (bonusActive) {
            switch (bonusType) {
                case 1 -> g.drawImage(speedImage, bonusX, bonusY, bonusWidth, bonusHeight, this);
                case 2 -> g.drawImage(slowImage, bonusX, bonusY, bonusWidth, bonusHeight, this);
                case 3 -> g.drawImage(multiplyImage, bonusX, bonusY, bonusWidth, bonusHeight, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ball ball : balls) {
            ball.move(getWidth(), getHeight(), paddle1Y, paddle1Height, paddle2Y, paddle2Height, paddleWidth);
            if (ball.outOfBounds(getWidth())) {
                scorePoint(ball);
            }
        }

        // Check ball collisions with the bonus
        if (bonusActive) {
            for (Ball ball : balls) {
                if (ball.collidesWith(bonusX, bonusY, bonusWidth, bonusHeight)) {
                    applyBonus();
                    bonusActive = false;  // Disable the bonus after collision
                    break;
                }
            }
        }

        repaint(); // Repaint the screen
    }

    private void scorePoint(Ball ball) {
        if (ball.x < 0) {
            ScoreP2++;
        } else if (ball.x > getWidth()) {
            ScoreP1++;
        }
        // Reset the balls to the initial state
        resetBalls();
    }

    private void resetBalls() {
        balls.clear(); // Clear current balls
        for (int i = 0; i < INITIAL_BALLS; i++) {
            balls.add(new Ball(250, 150, 5, 5, ballDiameter)); // Add new initial ball
        }
    }

    private void spawnBonus() {
        bonusX = random.nextInt(getWidth() - bonusWidth);
        bonusY = random.nextInt(getHeight() - bonusHeight);
        bonusType = random.nextInt(3) + 1; // 1: speed, 2: slow, 3: multiply balls
        bonusActive = true;
    }

    private void applyBonus() {
        switch (bonusType) {
            case 1 -> balls.forEach(Ball::increaseSpeed);
            case 2 -> balls.forEach(Ball::decreaseSpeed);
            case 3 -> multiplyBalls();
        }
    }

    private void multiplyBalls() {
        ArrayList<Ball> newBalls = new ArrayList<>();
        for (Ball ball : balls) {
            newBalls.add(new Ball(ball.x, ball.y, ball.xSpeed, -ball.ySpeed, ballDiameter)); // Clone ball with a new direction
        }
        balls.addAll(newBalls);
    }

    private void movePaddle1() {
        if (paddle1MovingUp && paddle1Y > 0) {
            paddle1Y -= 5;
        }
        if (paddle1MovingDown && paddle1Y < getHeight() - paddle1Height) {
            paddle1Y += 5;
        }
    }

    private void movePaddle2() {
        if (paddle2MovingUp && paddle2Y > 0) {
            paddle2Y -= 5;
        }
        if (paddle2MovingDown && paddle2Y < getHeight() - paddle2Height) {
            paddle2Y += 5;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Paddle 1 (left)
        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1MovingUp = true;
            paddle1MoveTimer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1MovingDown = true;
            paddle1MoveTimer.start();
        }

        // Paddle 2 (right)
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle2MovingUp = true;
            paddle2MoveTimer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2MovingDown = true;
            paddle2MoveTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Paddle 1 (left)
        if (e.getKeyCode() == KeyEvent.VK_W) paddle1MovingUp = false;
        if (e.getKeyCode() == KeyEvent.VK_S) paddle1MovingDown = false;

        // Paddle 2 (right)
        if (e.getKeyCode() == KeyEvent.VK_UP) paddle2MovingUp = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) paddle2MovingDown = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game with Bonus/Malus");
        PongGame game = new PongGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Ball {
    int x, y, xSpeed, ySpeed;
    int diameter;

    Ball(int x, int y, int xSpeed, int ySpeed, int diameter) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.diameter = diameter;
    }

    void move(int screenWidth, int screenHeight, int paddle1Y, int paddle1Height, int paddle2Y, int paddle2Height, int paddleWidth) {
        x += xSpeed;
        y += ySpeed;

        // Ball collision with top/bottom
        if (y <= 0 || y >= screenHeight - diameter) {
            ySpeed = -ySpeed;
        }

        // Paddle 1 collision
        if (x <= paddleWidth && y + diameter >= paddle1Y && y <= paddle1Y + paddle1Height) {
            xSpeed = -xSpeed;
            increaseSpeed(); // Increase speed on paddle collision
        }

        // Paddle 2 collision
        if (x >= screenWidth - paddleWidth - diameter && y + diameter >= paddle2Y && y <= paddle2Y + paddle2Height) {
            xSpeed = -xSpeed;
            increaseSpeed(); // Increase speed on paddle collision
        }
    }

    void increaseSpeed() {
        // Limit the maximum speed increase
        int maxSpeed = 15; // Set maximum speed limit
        xSpeed = Math.min(maxSpeed, xSpeed + 1);
        ySpeed = Math.min(maxSpeed, ySpeed + 1);
    }

    void decreaseSpeed() {
        xSpeed = Math.max(1, xSpeed - 1); // Reduced decrease from 2 to 1
        ySpeed = Math.max(1, ySpeed - 1); // Reduced decrease from 2 to 1
    }

    boolean collidesWith(int bonusX, int bonusY, int bonusWidth, int bonusHeight) {
        return x + diameter >= bonusX && x <= bonusX + bonusWidth && y + diameter >= bonusY && y <= bonusY + bonusHeight;
    }

    boolean outOfBounds(int screenWidth) {
        return x < 0 || x > screenWidth;
    }
}
