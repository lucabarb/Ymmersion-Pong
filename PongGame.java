import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int paddleWidth = 10;
<<<<<<< HEAD
    private int paddle1Height = 120;
    private int paddle2Height = 120;
    private int paddle1Y = 150, paddle2Y = 150;
    private int ballX = 250, ballY = 150, ballDiameter = 50;
    private int ballXSpeed = 5, ballYSpeed = 5;
    private int ScoreP1 = 0;
    private int ScoreP2 = 0;


    // Bonus/Malus
    private int bonusX = -100, bonusY = -100, bonusWidth = 100, bonusHeight = 100;
    private boolean bonusActive = false;
    private boolean isBonus = true;  // Bonus (vert) ou malus (rouge)
=======
    private int paddleHeight = 150;
    private int paddle1Y = 150, paddle2Y = 150;
    private int ballX = 250, ballY = 150, ballDiameter = 20;
    private int ballXSpeed = 10, ballYSpeed = 10;
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6

    private Timer timer;
    private Timer paddle1MoveTimer;
    private Timer paddle2MoveTimer;
<<<<<<< HEAD
    private Timer bonusTimer;
=======
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6

    private boolean paddle1MovingUp = false, paddle1MovingDown = false;
    private boolean paddle2MovingUp = false, paddle2MovingDown = false;

<<<<<<< HEAD
    private Random random;

    // Variable pour suivre quel paddle a touché la balle en dernier
    private boolean lastHitByPaddle1 = false; 
=======
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private boolean gameRunning = false;
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6

    public PongGame() {
        this.setPreferredSize(new Dimension(500, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(10, this);

        paddle1MoveTimer = new Timer(10, evt -> movePaddle1());
        paddle2MoveTimer = new Timer(10, evt -> movePaddle2());

        random = new Random();

        // Timer pour générer des bonus/malus toutes les 7 secondes
        bonusTimer = new Timer(7000, evt -> spawnBonus());
        bonusTimer.start();
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
<<<<<<< HEAD
        
        // Afficher les paddles
        g.fillRect(0, paddle1Y, paddleWidth, paddle1Height);
        g.fillRect(getWidth() - paddleWidth, paddle2Y, paddleWidth, paddle2Height);
        
        // Afficher la balle
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
        
        // Afficher les scores
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Définir une police plus grande
        g.drawString("Player 1: " + ScoreP1, 50, 30);  // Afficher le score du joueur 1
        g.drawString("Player 2: " + ScoreP2, getWidth() - 150, 30);  // Afficher le score du joueur 2
        
        // Dessiner le bonus/malus s'il est actif
        if (bonusActive) {
            g.setColor(isBonus ? Color.GREEN : Color.RED);
            g.fillRect(bonusX, bonusY, bonusWidth, bonusHeight);
=======
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("Pong Game", 180, 100);
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g.drawString("Press Start to Play", 170, 150);
        if (scorePlayer1 == 5 || scorePlayer2 == 5) {
            g.drawString("Game Over! Press Restart to Play Again", 110, 200);
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6
        }
    }

    public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
        ballX += ballXSpeed;
        ballY += ballYSpeed;
    
        if (ballY <= 0 || ballY >= getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed;
        }
    
        // Paddle 1 touche la balle
        if (ballX <= paddleWidth && ballY + ballDiameter >= paddle1Y && ballY <= paddle1Y + paddle1Height) {
            ballXSpeed = -ballXSpeed;
            lastHitByPaddle1 = true;
        }
    
        // Paddle 2 touche la balle
        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= paddle2Y && ballY <= paddle2Y + paddle2Height) {
            ballXSpeed = -ballXSpeed;
            lastHitByPaddle1 = false;
        }
    
        // Gérer les sorties de balle
        if (ballX < 0 || ballX > getWidth()) {
            scoreP();  // Mettre à jour les scores lorsque la balle sort
        }
    
        // Gérer la collision de la balle avec le bonus/malus
        if (bonusActive && ballX + ballDiameter >= bonusX && ballX <= bonusX + bonusWidth &&
            ballY + ballDiameter >= bonusY && ballY <= bonusY + bonusHeight) {
            applyBonusOrMalus();
            bonusActive = false;  // Désactiver le bonus/malus après collision
        }
    
=======
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
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6
        repaint();
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

    public void scoreP() {
        if (ballX < 0) {  // Si la balle sort du côté gauche
            ScoreP2 += 1;  // Le joueur 2 marque un point
        } else if (ballX > getWidth()) {  // Si la balle sort du côté droit
            ScoreP1 += 1;  // Le joueur 1 marque un point
        }
        
        // Réinitialiser la position de la balle
        ballX = getWidth() / 2 - ballDiameter / 2;
        ballY = getHeight() / 2 - ballDiameter / 2;
        
        // Réinitialiser la vitesse de la balle
        ballXSpeed = 5;
        ballYSpeed = 5;
    }

    // Génération aléatoire d'un bonus/malus
    public void spawnBonus() {
        bonusX = random.nextInt(getWidth() - bonusWidth);
        bonusY = random.nextInt(getHeight() - bonusHeight);
        isBonus = random.nextBoolean();  // Choisir si c'est un bonus ou un malus
        bonusActive = true;  // Activer le bonus/malus
    }

    // Appliquer les effets du bonus ou malus
    public void applyBonusOrMalus() {
        if (isBonus) {
            // Appliquer un bonus : augmenter la vitesse de la balle
            ballXSpeed += 2;
            ballYSpeed += 2;
        } else {
            // Appliquer un malus : réduire la taille du paddle qui a touché la balle en dernier
            if (lastHitByPaddle1) {
                paddle1Height = Math.max(60, paddle1Height - 20); // Réduire la taille du paddle 1
            } else {
                paddle2Height = Math.max(60, paddle2Height - 20); // Réduire la taille du paddle 2
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
<<<<<<< HEAD
        // Paddle 1 (gauche)
        if (e.getKeyCode() == KeyEvent.VK_W) {
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
=======
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
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6
            }

<<<<<<< HEAD
        // Paddle 2 (droite)
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
=======
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
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
<<<<<<< HEAD
        // Paddle 1 (gauche)
        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1MovingUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1MovingDown = false;
        }
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
        if (!paddle2MovingUp && !paddle2MovingDown) {
            paddle2MoveTimer.stop();
=======
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
>>>>>>> b86af674459fd2e9c61d21e08eb9c626ad3b65d6
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
        JFrame frame = new JFrame("Pong Game with Bonus/Malus");
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
