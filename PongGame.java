import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int paddleWidth = 10;
    private int paddleHeight = 150;
    private int paddle1Y = 150, paddle2Y = 150;
    private int ballX = 250, ballY = 150, ballDiameter = 20;
    private int ballXSpeed = 10, ballYSpeed = 10;

    private Timer timer;
    private Timer paddle1MoveTimer;
    private Timer paddle2MoveTimer;

    private boolean paddle1MovingUp = false, paddle1MovingDown = false;
    private boolean paddle2MovingUp = false, paddle2MovingDown = false;

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private boolean gameRunning = false;

    public PongGame() {
        this.setPreferredSize(new Dimension(500, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(10, this);

        paddle1MoveTimer = new Timer(10, evt -> movePaddle1());
        paddle2MoveTimer = new Timer(10, evt -> movePaddle2());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameRunning) {
            g.setColor(Color.WHITE);
            g.fillRect(0, paddle1Y, paddleWidth, paddleHeight);
            g.fillRect(getWidth() - paddleWidth, paddle2Y, paddleWidth, paddleHeight);
            g.fillOval(ballX, ballY, ballDiameter, ballDiameter);

            // Afficher les scores au-dessus des paddles
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString(String.valueOf(scorePlayer1), 20, 30);
            g.drawString(String.valueOf(scorePlayer2), getWidth() - 40, 30);
        } else {
            displayMainMenu(g);
        }
    }

    // Affiche le menu principal ou le message de fin de jeu
    private void displayMainMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("Pong Game", 180, 100);
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g.drawString("Press Start to Play", 170, 150);
        if (scorePlayer1 == 5 || scorePlayer2 == 5) {
            g.drawString("Game Over! Press Restart to Play Again", 110, 200);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
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

            if (ballX < 0) {
                scorePlayer2++;
                resetBall();
            }
            if (ballX > getWidth()) {
                scorePlayer1++;
                resetBall();
            }

            if (scorePlayer1 == 5 || scorePlayer2 == 5) {
                gameRunning = false;
                showWinner();
            }

            repaint();
        }
    }

    private void showWinner() {
        String winner = scorePlayer1 == 5 ? "Player 1 Wins!" : "Player 2 Wins!";
        JOptionPane.showMessageDialog(this, winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetBall() {
        ballX = 250;
        ballY = 150;
    }

    private void resetGame() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        resetBall();
        gameRunning = false;
        repaint();
    }

    private void movePaddle1() {
        if (paddle1MovingUp && paddle1Y > 0) {
            paddle1Y -= 5;
        }
        if (paddle1MovingDown && paddle1Y < getHeight() - paddleHeight) {
            paddle1Y += 5;
        }
    }

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
        if (gameRunning) {
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                paddle1MovingUp = true;
                paddle1MovingDown = false;
                if (!paddle1MoveTimer.isRunning()) {
                    paddle1MoveTimer.start();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                paddle1MovingDown = true;
                paddle1MovingUp = false;
                if (!paddle1MoveTimer.isRunning()) {
                    paddle1MoveTimer.start();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                paddle2MovingUp = true;
                paddle2MovingDown = false;
                if (!paddle2MoveTimer.isRunning()) {
                    paddle2MoveTimer.start();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                paddle2MovingDown = true;
                paddle2MovingUp = false;
                if (!paddle2MoveTimer.isRunning()) {
                    paddle2MoveTimer.start();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameRunning) {
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                paddle1MovingUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                paddle1MovingDown = false;
            }

            if (!paddle1MovingUp && !paddle1MovingDown) {
                paddle1MoveTimer.stop();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                paddle2MovingUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                paddle2MovingDown = false;
            }

            if (!paddle2MovingUp && !paddle2MovingDown) {
                paddle2MoveTimer.stop();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void startGame() {
        gameRunning = true;
        this.requestFocusInWindow();
        timer.start();
        repaint();
    }

    public void restartGame() {
        resetGame();
        startGame();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        JButton restartButton = new JButton("Restart Game");
        restartButton.setVisible(false); // Masquer le bouton de redémarrage au début

        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        startButton.addActionListener(e -> {
            startButton.setVisible(false); // Cacher le bouton de départ
            restartButton.setVisible(false); // Assurez-vous que le bouton de redémarrage est caché
            game.startGame(); // Lancer le jeu
        });

        restartButton.addActionListener(e -> {
            restartButton.setVisible(false); // Cacher le bouton de redémarrage
            game.restartGame(); // Redémarrer le jeu
            startButton.setVisible(false); // Cacher le bouton de départ
        });

        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
