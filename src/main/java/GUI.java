
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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
    private static boolean show_all_mines = false;

    public void GUI(int length, int width, int num_mines) {
        this.length = length;
        this.width = width;
        this.num_mines = num_mines;
    }

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
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    void match(Board b1) {
        for (int i = 0; i < b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                b1.getboard()[i][j].setI(i);
                b1.getboard()[i][j].setJ(j);
                this.local_button_I = i;
                this.local_button_J = j;
                b1.getboard()[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        button1MouseClicked(evt);
                    }
                });
            }
        }
    }

    void print(JPanel panel, JFrame frame, Board b1) {
        Scanner str = new Scanner(System.in);
        ImageIcon icon = new ImageIcon();

        for (int i = 0; i < b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                if (b1.getboard()[i][j].isSelect() || b1.getboard()[i][j].isFlag()) {
                    b1.getboard()[i][j].setBackground(new Color(189, 232, 205));
                    b1.getboard()[i][j].icon_button();
                    b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 1));

                } else {
                    b1.getboard()[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    if (b1.getboard()[i][j].isMine() == false || b1.getboard()[i][j].getShow() == -1) {
                        b1.getboard()[i][j].setBackground(new Color(139, 219, 127));
                        if (b1.getboard()[i][j].isMine() == false) {
                            b1.getboard()[i][j].icon_button();

                        } else {
                            b1.getboard()[i][j].setIcon(icon);
                        }
                    }

                }
                panel.add(b1.getboard()[i][j]);
            }
        }
        panel.setVisible(true);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Flage:" + b1.countflages);
        if (this.game_over || this.show_all_mines) {
            System.out.println("GAME OVER!");
            print_all_mines();
            //System.exit(0);
            return;
        }
    }

    public void New_Game() {
        b1 = new Board();
        b1.Board(this.length, this.width);
        match(b1);
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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + b1.All_Mines());
        print(panel, frame, b1);
        //  System.out.println("game over!");

    }

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < width; col++) {
                if (b1.getboard()[row][col] == evt.getSource()) {
                    if (evt.getButton() == 3) { // if right click
                        this.right_click = 1;
                        this.left_click = 0;
                        //b1.getboard()[row][col].setEnabled(true);
                    } else {
                        this.left_click = 1;
                        this.right_click = 0;
                        // button.setEnabled(false);
                    }
                    System.out.println(row + "," + col);
                    this.local_button_I = row;
                    this.local_button_J = col;

                }
            }
        }
        if (this.left_click == 1) {
            b1.Press(local_button_I, local_button_J, 0, 1);
            this.game_over = b1.isGame_over();
            this.show_all_mines = b1.all_press();
            print(panel, frame, b1);
        } else {
            b1.Press(local_button_I, local_button_J, 1, 0);
            this.game_over = b1.isGame_over();
            this.show_all_mines = b1.all_press();

            print(panel, frame, b1);
        }

    }//when cell clicking

}
