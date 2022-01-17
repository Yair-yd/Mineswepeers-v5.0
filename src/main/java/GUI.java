
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

    private int length;
    private int width;
    private int num_mines;
    private static int local_button_I = 0;
    private static int local_button_J = 0;
    private static int right_click = 0;
    private static int left_click = 0;
    private Board b1;
    private JFrame frame;
    private JPanel panel;
    private static boolean game_over = false;
    private static boolean show_all_mines_win = false;

    public void GUI(int length, int width, int num_mines) {
        this.length = length;
        this.width = width;
        this.num_mines = num_mines;
    }//Constractor

    public void main_New_Game() { // Creat new game and connectung to gui.

        b1 = new Board();
        b1.Board(this.length, this.width);
        add_click(b1);
        frame = new JFrame();
        frame.setTitle(" MineSweeper YAIR.D");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\דחבש\\Documents\\NetBeansProjects\\MineSwepeer-master\\src\\main\\java\\minelogo.jpg");
        frame.setIconImage(icon);
        panel = new JPanel();
        panel.setLayout(new GridLayout(b1.getLength(), b1.getWidth()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, b1.getLength() * 35, b1.getWidth() * 35);
        frame.add(panel);
        b1.Put_Mines(this.num_mines);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + b1.countmines);
        print(panel, frame, b1);
    }

    void add_click(Board b1) {
        for (int i = 0; i < b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                b1.getboard()[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        button1MouseClicked(evt);
                    }
                });
            }
        }
    }//Add to buttons event of read mouse click

    private void print_all_mines() {
        ImageIcon icon = new ImageIcon();
        for (int i = 0; i < b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                if (b1.getboard()[i][j].isMine()) {
                    b1.getboard()[i][j].setFlag(false);
                    b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
                    b1.getboard()[i][j].setBackground(new Color(186, 142, 205));
                }
                b1.getboard()[i][j].icon_button();
                panel.add(b1.getboard()[i][j]);
            }
        }
        return;
    }//When the game over,so, show all mines.

    void print(JPanel panel, JFrame frame, Board b1) {
        ImageIcon icon = new ImageIcon();
        for (int i = 0; i < b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                if (b1.getboard()[i][j].isSelect() || b1.getboard()[i][j].isFlag()) {//If is flaf or is need to open show it!
                    b1.getboard()[i][j].icon_button();
                    b1.getboard()[i][j].setBackground(new Color(189, 232, 205));//The color of buttons were pressed
                    b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 1));//the color of button fram were pressed    
                }
                if (b1.getboard()[i][j].getCountflags() == 0) {//If the flag was delete
                    b1.getboard()[i][j].setIcon(icon);
                    if (b1.getboard()[i][j].isSelect()) {//If is button then was open so the background is whith
                        b1.getboard()[i][j].setBackground(new Color(189, 232, 205));//the color of buttons were pressed
                        b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 1));//the color of button fram were pressed   
                    } else {//if not so the background is black
                        b1.getboard()[i][j].setBackground(new Color(139, 219, 127));
                        b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    }

                }
                panel.add(b1.getboard()[i][j]);// Anyway add this button to panel
            }
        }

        panel.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Flage:" + b1.countflages);// Show in the title- how many flags left
        if (this.game_over) {// If the game over show all the mines
            System.out.println("GAME OVER!");
            print_all_mines();
            return;
        }
        if (this.show_all_mines_win) {
            System.out.println("$YOU WIN!$");
            print_all_mines();
            return;
        }
    }// Mange the heart of gui.

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < width; col++) {
                if (b1.getboard()[row][col] == evt.getSource()) {
                    if (evt.getButton() == 3) { // if right click
                        GUI.right_click = 1;
                        GUI.left_click = 0;
                    } else {
                        GUI.left_click = 1;
                        GUI.right_click = 0;
                    }
                    System.out.println(row + "," + col);
                    GUI.local_button_I = row;
                    GUI.local_button_J = col;
                }
            }
        }
        if (GUI.left_click == 1) {
            b1.Press(local_button_I, local_button_J, 0, 1);
            GUI.game_over = b1.isGame_over();
            GUI.show_all_mines_win = b1.all_press();
            print(panel, frame, b1);
        } else {
            b1.Press(local_button_I, local_button_J, 1, 0);
            GUI.game_over = b1.isGame_over();
            GUI.show_all_mines_win = b1.all_press();

            print(panel, frame, b1);
        }

    }//when cell clicking

}
