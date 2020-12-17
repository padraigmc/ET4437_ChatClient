package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan- 18222765@studentmai.ul.ie
 */
public class ET4437ChatClient {
    
    private static ChatWebService chatServiceProxy;
    public static JPanel cardHolder; // a panel that uses CardLayout
    private JButton register;
    private JButton login;
    public static int userID;
    public static int sessionID;
    
    public JPanel loginPanel;
    public JPanel registerPanel;
    public static JPanel chatPanel;
    
    private final String FRAME_TITLE = "ezChat";

    // card commands
    final String FIRST = "FIRST";
    final String NEXT = "NEXT";
    final String PREVIOUS = "PREVIOUS";
    final String LAST = "LAST";
    final String LOGIN = "LOGIN";
    
    public static final Font BUTTON_FONT = new Font("Sego UI", Font.PLAIN, 14);
    
    ET4437ChatClient() {        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        
        JFrame frame = new JFrame(FRAME_TITLE);
        SpringLayout layout = new SpringLayout();
 
        //Create the "cards".
        JPanel homePanel = new JPanel();
        homePanel.setLayout(layout);
        ControlActionListenter cal = new ControlActionListenter();
        
        login = new JButton("Login Here");
        login.setActionCommand(NEXT);
        login.addActionListener(cal);
        homePanel.add(login);
        
        register = new JButton("Register Here");
        register.setActionCommand(LAST);
        register.addActionListener(cal);
        homePanel.add(register);
        
        layout.putConstraint(SpringLayout.WEST, login, 10, SpringLayout.WEST, homePanel); 
        layout.putConstraint(SpringLayout.NORTH, login,10, SpringLayout.NORTH, homePanel);
        
        layout.putConstraint(SpringLayout.WEST, register,10, SpringLayout.EAST, login); 
        layout.putConstraint(SpringLayout.NORTH, register,10, SpringLayout.NORTH, homePanel);
 
        loginPanel = new Login();
        registerPanel = new Register();
        
        cardHolder = new JPanel(new CardLayout());
        cardHolder.add(homePanel, "Login/Register");
        cardHolder.add(loginPanel, "Login");
        cardHolder.add(registerPanel, "Register");
        Container pane = frame.getContentPane();
        pane.add(cardHolder, BorderLayout.CENTER);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setVisible(true);
    }
    
    public class ControlActionListenter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (cardHolder.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals(FIRST)) {
                cl.first(cardHolder);
            } else if (cmd.equals(NEXT)) { // user logged in
                
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