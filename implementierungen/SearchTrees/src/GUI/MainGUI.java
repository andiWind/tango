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
    private final TangoCanvas canvas;
    private final JMenuItem runtimeTest;
    private final JMenuItem access;
    private final JDialog accessDialog;
    
  
    public MainGUI (){
        accessDialog = new JDialog(this);
        accessDialog.setLayout(new GridLayout(2,2));
        accessDialog.setDefaultCloseOperation(HIDE_ON_CLOSE );
        accessDialog.setSize(400, 200);
        JSpinner accessSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        accessDialog.add(accessSpinner);
        JButton accessButton = new JButton("access");
        accessButton.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent e) {
                canvas.access((int)accessSpinner.getValue());
                canvas.repaint();
                } 
            }    
        );
        accessDialog.add(accessButton);
        accessDialog.add(new JLabel("Reset:"));
        JButton resetButton = new JButton("reset");
        resetButton.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent e) {
                canvas.reset();
                canvas.repaint();
                } 
            }    
        );
        accessDialog.add(resetButton);
        canvas = new TangoCanvas();
        runtimeFrame = new RuntimeFrame();
        guiMenuBar = new JMenuBar();
        menu = new JMenu("Menü");
        runtimeTest = new JMenuItem("RuntimeTest");
        access = new JMenuItem("access");
        access.addMouseListener(new MouseListener() {
              
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
                    accessDialog.setVisible(true);
                    
                }  
                @Override
                 public void mouseClicked(MouseEvent e) {
                }   
            }
        );
        menu.add(runtimeTest);
        menu.add(access);
        guiMenuBar.add(menu);
        guiMenuBar.setVisible(true);
        runtimeTest.addMouseListener(new MouseListener() {
              
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
                    runtimeFrame.setSize( getWidth() /3 , getHeight()/ 3);
              
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
        add(canvas, BorderLayout.CENTER);
        accessDialog.setVisible(true);
        setVisible(true);
    }
    
    
    
}
