/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
    
/**
 *
 * @author andreas
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainGUI extends JFrame{
    private final RuntimeFrame runtimeFrame;
    private final JMenuBar guiMenuBar;
    private final JMenu menu;
    private JMenuItem runtimeItem;
    

    public MainGUI (){
        runtimeFrame = new RuntimeFrame();
        guiMenuBar = new JMenuBar();
        menu = new JMenu("Menü");
        runtimeItem = new JMenuItem("Runtime");
        menu.add(runtimeItem);
        guiMenuBar.add(menu);
        guiMenuBar.setVisible(true);
        runtimeItem.addMouseListener(new MouseListener() {
              
                @Override
                 public void mouseExited(MouseEvent e) {     
                }
                @Override
                 public void mouseEntered(MouseEvent e) {
                } 
                @Override
                 public void mouseReleased(MouseEvent e) {
                }  
                @Override
                 public void mousePressed(MouseEvent e) {
                    runtimeFrame.setVisible(true);
                    runtimeFrame.setSize( getWidth() /2 , getHeight()/ 2);
              
                }  
                @Override
                 public void mouseClicked(MouseEvent e) {
                }   
            }
        );
       
        
        
        
        
        
        initFrame();
    }
    private void initFrame(){
        setTitle("TangoGUI");
        setJMenuBar(guiMenuBar);
        setBackground (Color.LIGHT_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE );
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
       // add(northPanel, BorderLayout.NORTH);
       // add(randomPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
}
