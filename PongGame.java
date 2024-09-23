import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    // Paramètres des paddles
    private int paddleWidth = 10;
    private int paddleHeight = 200;  // Augmenté la taille des paddles
    private int paddle1Y = 150, paddle2Y = 150;
    
    // Paramètres de la balle
    private int ballX = 250, ballY = 150, ballDiameter = 20;
    private int ballXSpeed = 10, ballYSpeed = 10;

    private Timer timer;

    public PongGame() {
        this.setPreferredSize(new Dimension(500, 300)); // Taille de la fenêtre
        this.setBackground(Color.PINK);  // Fond noir
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(10, this);  // Actualisation toutes les 10 ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        // Paddle 1 : Toujours à l'extrémité gauche
        g.fillRect(0, paddle1Y, paddleWidth, paddleHeight);  // Paddle 1 (à gauche)
        
        // Paddle 2 : Toujours à l'extrémité droite
        g.fillRect(getWidth() - paddleWidth, paddle2Y, paddleWidth, paddleHeight);  // Paddle 2 (à droite)
        
        // Dessiner la balle
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);  // Balle
    }

    public void actionPerformed(ActionEvent e) {
        // Mouvement de la balle
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Collision de la balle avec les bords supérieur et inférieur
        if (ballY <= 0 || ballY >= getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed;
        }

        // Collision de la balle avec les paddles
        if (ballX <= paddleWidth && ballY + ballDiameter >= paddle1Y && ballY <= paddle1Y + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }
        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= paddle2Y && ballY <= paddle2Y + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }

        // Réinitialisation de la balle si elle sort de l'écran
        if (ballX < 0 || ballX > getWidth()) {
            ballX = 250;
            ballY = 150;
        }

        repaint();  // Redessiner l'interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Mouvement des paddles
        if (e.getKeyCode() == KeyEvent.VK_W && paddle1Y > 0) {
            paddle1Y -= 15;
        }
        if (e.getKeyCode() == KeyEvent.VK_S && paddle1Y < getHeight() - paddleHeight) {
            paddle1Y += 15;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && paddle2Y > 0) {
            paddle2Y -= 15;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && paddle2Y < getHeight() - paddleHeight) {
            paddle2Y += 15;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

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
