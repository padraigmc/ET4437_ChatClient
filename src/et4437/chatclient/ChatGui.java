/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class ChatGui extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    private JPanel masterPanel;
    private JPanel userPanel;
    private SpringLayout springLayout;
    private JButton submit;
    private JTextField name;
    private JTextField username;
    private JPasswordField password;
    private JRadioButton male;
    private JRadioButton female;
    private JTextArea comments;
    private JLabel namelabel;
    private JLabel userlabel;
    private JLabel passwordlabel;  
    private JCheckBox subscribe;
    
    private JPanel unicastChat;
    private JTextArea chatDisplay;
    private JLabel label;
    
    // GUI stuff
    private JTextArea  chatHistory;
    private JTextField typedText;
    private JButton sendButton;
    private JTextArea previousMessageBox;
    private JTextField userMessageField;
    private JButton sendMessage;
     
    // constructor
    ChatGui() {        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        springLayout = new SpringLayout();
        
        masterPanel = new JPanel(); 
        this.setLayout(new BorderLayout(0, 0));
        this.setSize(450,400);
        
        // JPanel of list of usernames as buttons
        JPanel userPane = new UserPane(chatServiceProxy.getUsers(true));
        userPane.setMinimumSize(new Dimension(160, 200));
        this.add(userPane, BorderLayout.LINE_START);
        //springLayout.putConstraint(SpringLayout.WEST, userPane, 6, SpringLayout.WEST, this); 
        //springLayout.putConstraint(SpringLayout.NORTH, userPane, 8, SpringLayout.NORTH, this);
        
        //JPanel unicastChat = new UnicastChat(); // the panel is not visible in output
        //unicastChat.setLayout(new BoxLayout(unicastChat, BoxLayout.Y_AXIS));
        //this.add(unicastChat);
        
        
        //JPanel unicastChat = createAndShowGUI();
        //this.add(unicastChat, BorderLayout.CENTER);
        //springLayout.putConstraint(SpringLayout.WEST, unicastChat, 6, SpringLayout.EAST, userPane); 
        //springLayout.putConstraint(SpringLayout.NORTH, unicastChat, 8, SpringLayout.NORTH, this);
        
        // GUI stuff
        SpringLayout unicastSpringLayout = new SpringLayout();
        JPanel unicastChat = new JPanel(unicastSpringLayout);
        this.add(unicastChat, BorderLayout.CENTER);
        
        chatHistory = new JTextArea(10, 32);
        chatHistory.setEditable(false);
        JScrollPane chatHistoryScrollPane = new JScrollPane(chatHistory);
        typedText = new JTextField(400);
        sendButton = new JButton("Send");
        

        unicastChat.add(chatHistoryScrollPane);
        unicastChat.add(typedText);
        unicastChat.add(sendButton);
        
        unicastSpringLayout.putConstraint(SpringLayout.NORTH, chatHistoryScrollPane, 8, SpringLayout.NORTH, this);
        unicastSpringLayout.putConstraint(SpringLayout.WEST, chatHistoryScrollPane, 6, SpringLayout.EAST, this);
        
        unicastSpringLayout.putConstraint(SpringLayout.NORTH, typedText, 8, SpringLayout.SOUTH, chatHistoryScrollPane);
        unicastSpringLayout.putConstraint(SpringLayout.WEST, typedText, 0, SpringLayout.WEST, chatHistoryScrollPane);
        unicastSpringLayout.putConstraint(SpringLayout.EAST, typedText, 6, SpringLayout.WEST, sendButton);
        
        unicastSpringLayout.putConstraint(SpringLayout.NORTH, sendButton, 8, SpringLayout.SOUTH, chatHistoryScrollPane);
        unicastSpringLayout.putConstraint(SpringLayout.EAST, sendButton, 0, SpringLayout.EAST, chatHistoryScrollPane);        
    }
    
    public class UserPane extends JPanel {
        public UserPane(List<String> registeredUsers) {
            JPanel usernameButtons = new ListOfUsers(registeredUsers);
            JScrollPane listOfUsers = new JScrollPane(usernameButtons,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            listOfUsers.setMinimumSize(new Dimension(160, 200));
        listOfUsers.setPreferredSize(new Dimension(160, 200));
            
            //listOfUsers = getUsernameButtons();
            //listOfUsers.setLayout(layout);
            this.add(listOfUsers);
                     
            setSize(400,400);
            setVisible(true);
        }
    }
    
    public class ListOfUsers extends JPanel {
        public ListOfUsers(List<String> registeredUsers) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (String user : registeredUsers)
            {
                //JButton usernameButton = new JButton(user);
                //usernameButton.setMinimumSize(new Dimension(160, 5));
                //usernameButton.setPreferredSize(new Dimension(160, 5));
                this.add(new JButton(user));                
            }
            
            setVisible(true);
        }
    }
    
    public class UnicastChat extends JPanel {
        public UnicastChat() {            
            // Text Area at the Center
            JTextArea previousMessageBox = new JTextArea();
            JLabel enterTextLabel = new JLabel("Enter Text");
            JTextField userMessageField = new JTextField(10); // accepts up to 10 characters
            JButton sendMessage = new JButton("Send");
            
            this.add(previousMessageBox);
            this.add(enterTextLabel);
            this.add(userMessageField);
            this.add(sendMessage);
            
            /*
            springLayout.putConstraint(SpringLayout.WEST, previousMessageBox, 0, SpringLayout.WEST, this);
            springLayout.putConstraint(SpringLayout.NORTH, previousMessageBox, 0, SpringLayout.NORTH, this);
            springLayout.putConstraint(SpringLayout.WEST, enterTextLabel, 0, SpringLayout.WEST, this);
            springLayout.putConstraint(SpringLayout.NORTH, enterTextLabel, 0, SpringLayout.NORTH, previousMessageBox);
            springLayout.putConstraint(SpringLayout.WEST, userMessageField, 0, SpringLayout.WEST, enterTextLabel);
            springLayout.putConstraint(SpringLayout.NORTH, userMessageField, 0, SpringLayout.NORTH, previousMessageBox);
            springLayout.putConstraint(SpringLayout.EAST, sendMessage, 0, SpringLayout.EAST, this);
            springLayout.putConstraint(SpringLayout.NORTH, sendMessage, 0, SpringLayout.NORTH, previousMessageBox);
            */
        }
    }
    
    public class UpdateUnicastMessages implements Runnable {
        public void run() {
            //if (chatHistory.getText() == chatServiceProxy.getMessages(WIDTH, WIDTH, receiver, WIDTH)) {
                
        }
    }
    
    private JPanel createAndShowGUI()
    {        
        // Create a JPanel and set layout
        unicastChat = new JPanel();
        unicastChat.setLayout(new BoxLayout(unicastChat, BoxLayout.Y_AXIS));
        label = new JLabel();
        unicastChat.add(label);
        
        // Add panel to the south,
        add(unicastChat,BorderLayout.SOUTH);
        
        // Create a textarea
        chatDisplay = new JTextArea();
                
        // Make it non-editable
        chatDisplay.setEditable(false);
        
        // Set some margin, for the text
        //chatDisplay.setMargin(new Insets(7,7,7,7));
        
        // Set a scrollpane
        JScrollPane js = new JScrollPane(chatDisplay);
        add(js);
        
        setSize(400,400);
        setVisible(true);
        
        return unicastChat;
    }
}
