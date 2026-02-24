import javax.swing.JFrame;
import java.awt.Color;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");

        GamePanel panel = new GamePanel();
        frame.add(panel);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);



    }
}