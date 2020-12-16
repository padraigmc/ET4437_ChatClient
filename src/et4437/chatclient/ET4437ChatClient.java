package et4437.chatclient;
 
import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan- 18222765@studentmai.ul.ie
 */
public class ET4437ChatClient {
    
    public static JPanel cardHolder; // a panel that uses CardLayout
    private JButton register;
    private JButton login;
    public static int userID;
    public static int sessionID;
    
    // card commands
    final String FIRST = "FIRST";
    final String NEXT = "NEXT";
    final String PREVIOUS = "PREVIOUS";
    final String LAST = "LAST";
    final String LOGIN = "LOGIN";
    
    ET4437ChatClient() {        
        JFrame frame = new JFrame("Project Messenger");
        SpringLayout layout = new SpringLayout();
 
        //Create the "cards".
        JPanel homePanel = new JPanel();
        homePanel.setLayout(layout);
        ControlActionListenter cal = new ControlActionListenter();
        
        login = new JButton("Login Here");
        login.setActionCommand(NEXT);
        login.addActionListener(cal);
        homePanel.add(login);
        layout.putConstraint(SpringLayout.WEST, login,70, SpringLayout.WEST, homePanel); 
        layout.putConstraint(SpringLayout.NORTH, login,8+90, SpringLayout.NORTH, homePanel);
        
        register = new JButton("Register Here");
        register.setActionCommand(LAST);
        register.addActionListener(cal);
        homePanel.add(register);
        layout.putConstraint(SpringLayout.WEST, register,180, SpringLayout.WEST, homePanel); 
        layout.putConstraint(SpringLayout.NORTH, register,8+90, SpringLayout.NORTH, homePanel);
 
        JPanel loginPanel = new Login();
        JPanel registerPanel = new Register();
        JPanel chatPanel = new ChatGui();
        
        cardHolder = new JPanel(new CardLayout());
        cardHolder.add(homePanel, "Login/Register");
        cardHolder.add(loginPanel, "Login");
        cardHolder.add(chatPanel, "Chat");
        cardHolder.add(registerPanel, "Register");
 
        Container pane = frame.getContentPane();
        pane.add(cardHolder, BorderLayout.CENTER);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setVisible(true);
    }
    
    public class ControlActionListenter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (cardHolder.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals(FIRST)) {
                cl.first(cardHolder);
            } else if (cmd.equals(NEXT)) {
                cl.next(cardHolder);
            } else if (cmd.equals(PREVIOUS)) {
                cl.previous(cardHolder);
            } else if (cmd.equals(LAST)) {
                cl.last(cardHolder);
            }
        }
    }
    
    public static void main(String[] args) {
        ET4437ChatClient chatClient = new ET4437ChatClient();
    }
}