package et4437.chatclient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan- 18222765@studentmai.ul.ie
 */
public class ET4437ChatClient {
    
    public static JFrame frame;
    public static JPanel cardHolder; // a panel that uses CardLayout
    private JButton register;
    private JButton login;
    public static int userID;
    public static int sessionID;
    
    public JPanel loginPanel;
    public JPanel registerPanel;
    public static JPanel chatPanel;
    
    private final String FRAME_TITLE = "Play Chat";

    // card layout commands
    final String FIRST = "FIRST";
    final String NEXT = "NEXT";
    final String PREVIOUS = "PREVIOUS";
    final String LAST = "LAST";
    final String LOGIN = "LOGIN";
    
    public static final Font FONT_REGULAR = new Font("Sego UI", Font.PLAIN, 14);
    public static final Font FONT_LARGE = new Font("Sego UI", Font.PLAIN, 26);
    
    ET4437ChatClient() {
        frame = new JFrame(FRAME_TITLE);
        SpringLayout layout = new SpringLayout();
 
        // Create the "cards".
        JPanel homePanel = new JPanel();
        homePanel.setLayout(layout);
        FlowControlAL flowControlAL = new FlowControlAL();
        
        // login button
        login = new JButton("Login Here");
        login.setActionCommand(NEXT);
        login.addActionListener(flowControlAL);
        login.setFont(FONT_LARGE);
        homePanel.add(login);
        
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, login, 0, SpringLayout.VERTICAL_CENTER, homePanel);
        layout.putConstraint(SpringLayout.EAST, login, -20, SpringLayout.HORIZONTAL_CENTER, homePanel); 
        //layout.putConstraint(SpringLayout.NORTH, login, 10, SpringLayout.NORTH, homePanel);
        
        // register button
        register = new JButton("Register Here");
        register.setActionCommand(LAST);
        register.addActionListener(flowControlAL);
        register.setFont(FONT_LARGE);
        homePanel.add(register);
        
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, register, 0, SpringLayout.VERTICAL_CENTER, login);
        layout.putConstraint(SpringLayout.WEST, register, 20, SpringLayout.HORIZONTAL_CENTER, homePanel); 
        //layout.putConstraint(SpringLayout.NORTH, register,10, SpringLayout.NORTH, homePanel);
 
        // create login and register JPanels
        loginPanel = new Login();
        registerPanel = new Register();
        
        // create JPanel with card layour and add register and login panels
        cardHolder = new JPanel(new CardLayout());
        cardHolder.add(homePanel, "Login/Register");
        cardHolder.add(loginPanel, "Login");
        cardHolder.add(registerPanel, "Register");
        
        // add cardHolder JPanel to frame
        Container pane = frame.getContentPane();
        pane.add(cardHolder, BorderLayout.CENTER);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setVisible(true);
    }
    
    // ActionListener for controlling cardLayout
    public class FlowControlAL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            CardLayout cardLayout = (CardLayout) (cardHolder.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals(FIRST)) {
                cardLayout.first(cardHolder);
            } else if (cmd.equals(NEXT)) { // user logged in
                
                cardLayout.next(cardHolder);
            } else if (cmd.equals(PREVIOUS)) {
                cardLayout.previous(cardHolder);
            } else if (cmd.equals(LAST)) {
                cardLayout.last(cardHolder);
            }
        }
    }
    
    public static void main(String[] args) {
        ET4437ChatClient chatClient = new ET4437ChatClient();
    }
}