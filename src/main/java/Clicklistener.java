
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author YAIR.D
 */
class Clicklistener {
 public int actionPerformed(ActionEvent e,JButton button){
if (e.getSource() == button)
{
return 1;
}
return 0;
}   
}
