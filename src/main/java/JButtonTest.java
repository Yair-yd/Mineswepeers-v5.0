/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javafx.scene.image.ImageView;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;




/**
 *
 * @author YAIR.D
 */
public class JButtonTest implements ActionListener  {
    private Board b1=new Board();
    JFrame frame;
    JButton button;
    
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
}
    private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();

    return resizedImg;
}
    
    public JButtonTest() {
         b1.Board(8,8);
        JButton [][] board_play=new JButton[b1.getLength()][b1.getWidth()];
        frame = new JFrame();
        frame.setTitle("My JFrame");
        frame.setBounds(500, 250, b1.getLength()*75, b1.getWidth()*50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i <b1.getLength(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
        board_play[i][j] = new JButton();
         
        board_play[i][j].addActionListener(this);
       board_play[i][j].setSize(30, 30);
      ImageIcon icon = new ImageIcon("C:\\Users\\USER001\\Documents\\NetBeansProjects\\MineSweeper\\src\\main\\java\\Mine.png");
  
                board_play[i][j].setIcon(resizeIcon(icon,10,10));
         
        frame.setLayout(new FlowLayout());
        frame.add( board_play[i][j]); 
            }
        }
   
        frame.setVisible(true);
    }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("You have clicked the button");
    }
     
   
    
}
