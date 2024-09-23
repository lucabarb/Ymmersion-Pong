import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int paddleWidth = 10;
    private int paddleHeight = 120;
    private int paddle1Y = 150, paddle2Y = 150;
    private int ballX = 250, ballY = 150, ballDiameter = 20;
    private int ballXSpeed = 3, ballYSpeed = 3;

    private Timer timer;
    private Timer paddle1MoveTimer;  // Timer pour la paddle 1
    private Timer paddle2MoveTimer;  // Timer pour la paddle 2

    private boolean paddle1MovingUp = false, paddle1MovingDown = false;
    private boolean paddle2MovingUp = false, paddle2MovingDown = false;

    public PongGame() {
        this.setPreferredSize(new Dimension(500, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(10, this);
        timer.start();

        // Initialiser les Timers pour les deux paddles
        paddle1MoveTimer = new Timer(10, evt -> movePaddle1());
        paddle2MoveTimer = new Timer(10, evt -> movePaddle2());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, paddle1Y, paddleWidth, paddleHeight);
        g.fillRect(getWidth() - paddleWidth, paddle2Y, paddleWidth, paddleHeight);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballY <= 0 || ballY >= getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballX <= paddleWidth && ballY + ballDiameter >= paddle1Y && ballY <= paddle1Y + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= paddle2Y && ballY <= paddle2Y + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballX < 0 || ballX > getWidth()) {
            ballX = 250;
            ballY = 150;
        }

        repaint();
    }

    // Méthode pour déplacer la paddle 1 en fonction de la direction
    private void movePaddle1() {
        if (paddle1MovingUp && paddle1Y > 0) {
            paddle1Y -= 5;
        }
        if (paddle1MovingDown && paddle1Y < getHeight() - paddleHeight) {
            paddle1Y += 5;
        }
    }

    // Méthode pour déplacer la paddle 2 en fonction de la direction
    private void movePaddle2() {
        if (paddle2MovingUp && paddle2Y > 0) {
            paddle2Y -= 5;
        }
        if (paddle2MovingDown && paddle2Y < getHeight() - paddleHeight) {
            paddle2Y += 5;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Paddle 1 (gauche)
        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1MovingUp = true;
            paddle1MovingDown = false;  // Arrêter le mouvement de descente
            if (!paddle1MoveTimer.isRunning()) {
                paddle1MoveTimer.start();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1MovingDown = true;
            paddle1MovingUp = false;  // Arrêter le mouvement de montée
            if (!paddle1MoveTimer.isRunning()) {
                paddle1MoveTimer.start();
            }
        }

        // Paddle 2 (droite)
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle2MovingUp = true;
            paddle2MovingDown = false;  // Arrêter le mouvement de descente
            if (!paddle2MoveTimer.isRunning()) {
                paddle2MoveTimer.start();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2MovingDown = true;
            paddle2MovingUp = false;  // Arrêter le mouvement de montée
            if (!paddle2MoveTimer.isRunning()) {
                paddle2MoveTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Paddle 1 (gauche)
        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1MovingUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1MovingDown = false;
        }

        // Arrêter le Timer si les deux directions sont relâchées
        if (!paddle1MovingUp && !paddle1MovingDown) {
            paddle1MoveTimer.stop();
        }

        // Paddle 2 (droite)
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle2MovingUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2MovingDown = false;
        }

        // Arrêter le Timer si les deux directions sont relâchées
        if (!paddle2MovingUp && !paddle2MovingDown) {
            paddle2MoveTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
