/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class ChatGui extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    public JButton selectedUsernameBtn;
    private SpringLayout springLayout;
    private JPanel unicastChatPanel;
    
    private final int POLLING_DELAY = 500;
    private final Dimension USER_LIST_DIMENSION = new Dimension(250, 700);
    private final Dimension CHAT_BOX_DIMENSION = new Dimension(425, 700);
    
    // GUI stuff
    private JTextArea unicastTitle;
    private JTextArea unicastChatHistory;
    private JTextField unicastMessageBox;
    private JButton sendUnicast;
    private JTextArea multicastChatHistory;
    private JTextField multicastMessageBox;
    private JButton sendMulticast;
     
    // constructor
    ChatGui() {       
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        springLayout = new SpringLayout();
        
        this.setLayout(new BorderLayout());
        
        // Username list - build a list of registered usernames as buttons
        JPanel userPanel = new UserPanel();
        
        // Build unicast chat JPanel
        unicastChatPanel = new UnicastChatPanel();
        
        // multicast chat
        JPanel multicastChatPanel = new MulticastChatPanel();
        
        // set preferred dimensions for user, unicast and multicast panes
        userPanel.setPreferredSize(USER_LIST_DIMENSION);
        unicastChatPanel.setPreferredSize(CHAT_BOX_DIMENSION);
        multicastChatPanel.setPreferredSize(CHAT_BOX_DIMENSION);
        
        // add user, unicast and multicast panes to GUI
        this.add(userPanel, BorderLayout.LINE_START);
        this.add(unicastChatPanel, BorderLayout.CENTER);
        this.add(multicastChatPanel, BorderLayout.LINE_END);
        
        MulticastMessageUpdater multicastMessageUpdater = new MulticastMessageUpdater();
        multicastMessageUpdater.start();
    }
    
    public class UserPanel extends JPanel {
        public UserPanel() {
            this.setLayout(springLayout);
            List<String> registeredUsers = chatServiceProxy.getUsers(true);
            
            JButton logOutButton = new JButton("Log Out");
            logOutButton.addActionListener(new LogOutButtonAL());
            logOutButton.setFont(ET4437ChatClient.FONT_REGULAR);
            logOutButton.setBackground(Color.red);
            this.add(logOutButton);
            springLayout.putConstraint(springLayout.NORTH, logOutButton, 0, springLayout.NORTH, this);
            springLayout.putConstraint(springLayout.EAST, logOutButton, 0, springLayout.EAST, this);
            springLayout.putConstraint(springLayout.WEST, logOutButton, 0, springLayout.WEST, this);
            
            JPanel usernameButtons = new ListOfUsers(registeredUsers);
            JScrollPane listOfUsers = new JScrollPane(usernameButtons,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            listOfUsers.setMinimumSize(new Dimension(160, 200));
            listOfUsers.setPreferredSize(new Dimension(160, 200));
        
            this.add(listOfUsers);
            
            springLayout.putConstraint(springLayout.NORTH, listOfUsers, 0, springLayout.SOUTH, logOutButton);
            springLayout.putConstraint(springLayout.SOUTH, listOfUsers, 0, springLayout.SOUTH, this);
            springLayout.putConstraint(springLayout.EAST, listOfUsers, 0, springLayout.EAST, this);
            springLayout.putConstraint(springLayout.WEST, listOfUsers, 0, springLayout.WEST, this);
                     
            setSize(400,400);
            setVisible(true);
        }
        
        public class LogOutButtonAL implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatServiceProxy.logOut(ET4437ChatClient.userID, ET4437ChatClient.sessionID);
                System.exit(1);
            }
        }
        
        public class ListOfUsers extends JPanel {
            public ListOfUsers(List<String> registeredUsers) {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                ActionListener usernameButtonAL = new UsernameButtonAL();
                for (String user : registeredUsers)
                {
                    JButton usernameButton = new JButton(user);
                    usernameButton.addActionListener(usernameButtonAL);
                    usernameButton.setFont(ET4437ChatClient.FONT_REGULAR);
                    usernameButton.setMinimumSize(new Dimension(250, 50));
                    usernameButton.setMaximumSize(new Dimension(250, 50));
                    this.add(usernameButton);       
                    
                }

                setVisible(true);
            }
            
            public class UsernameButtonAL implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    unicastChatHistory.setText(""); // clear chat box
                    selectedUsernameBtn = (JButton) e.getSource();
                    System.out.println(selectedUsernameBtn.getText() + " selected");
                    UnicastMessageUpdater uniMessageUpdater = new UnicastMessageUpdater(selectedUsernameBtn.getText());
                    uniMessageUpdater.start();
                }
            }
        }
    }
    
    public class UnicastChatPanel extends JPanel {
        public UnicastChatPanel() {
            this.setLayout(springLayout);

            buildGui();
        }
        
        public void buildGui() {
            unicastTitle = new JTextArea("Direct Message");
            unicastChatHistory.setEditable(false);
            unicastChatHistory.setWrapStyleWord(true);
            unicastChatHistory.setFont(ET4437ChatClient.FONT_REGULAR);
            unicastChatHistory.setMargin(new Insets(5,5,5,5));
            
            
            unicastChatHistory = new JTextArea();
            unicastChatHistory.setEditable(false);
            unicastChatHistory.setWrapStyleWord(true);
            unicastChatHistory.setFont(ET4437ChatClient.FONT_REGULAR);
            unicastChatHistory.setMargin(new Insets(5,5,5,5));
            
            JScrollPane chatHistoryScrollPane = new JScrollPane(unicastChatHistory);
            
            unicastMessageBox = new JTextField(400);
            unicastMessageBox.setFont(ET4437ChatClient.FONT_REGULAR);
            
            sendUnicast = new JButton("Send");
            sendUnicast.setFont(ET4437ChatClient.FONT_REGULAR);
            sendUnicast.addActionListener(new UnicastSendButtonAL());

            // add unicast chatbox elements to JPanel
            this.add(chatHistoryScrollPane);
            this.add(unicastMessageBox);
            this.add(sendUnicast);
            
            // set unicast chatbox element contraints
            springLayout.putConstraint(SpringLayout.NORTH, chatHistoryScrollPane, 0, SpringLayout.NORTH, this);
            springLayout.putConstraint(SpringLayout.SOUTH, chatHistoryScrollPane, 0, SpringLayout.NORTH, unicastMessageBox);
            springLayout.putConstraint(SpringLayout.EAST, chatHistoryScrollPane, 0, SpringLayout.EAST, this);
            springLayout.putConstraint(SpringLayout.WEST, chatHistoryScrollPane, 0, SpringLayout.WEST, this);

            springLayout.putConstraint(SpringLayout.SOUTH, unicastMessageBox, 0, SpringLayout.SOUTH, this);
            springLayout.putConstraint(SpringLayout.EAST, unicastMessageBox, 0, SpringLayout.WEST, sendUnicast);
            springLayout.putConstraint(SpringLayout.WEST, unicastMessageBox, 0, SpringLayout.WEST, chatHistoryScrollPane);

            springLayout.putConstraint(SpringLayout.NORTH, sendUnicast, 0, SpringLayout.NORTH, unicastMessageBox);
            springLayout.putConstraint(SpringLayout.SOUTH, sendUnicast, 0, SpringLayout.SOUTH, unicastMessageBox);
            springLayout.putConstraint(SpringLayout.EAST, sendUnicast, 0, SpringLayout.EAST, chatHistoryScrollPane);
        }
        
        public class UnicastSendButtonAL implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = unicastMessageBox.getText();
                String receiver = selectedUsernameBtn.getText();

                if (message.length() > 0) {
                    unicastMessageBox.setText(""); // clear message box
                    boolean messageSent = chatServiceProxy.postMessage(ET4437ChatClient.userID, ET4437ChatClient.sessionID, receiver, message);

                    if (messageSent) {
                        System.out.println("Message sent.");
                    } else {
                        System.out.println("Could not send message.");
                    }
                }
            }
        }
    }
    
    public class UnicastMessageUpdater extends Thread {
            public final String username;

            public UnicastMessageUpdater(String username) {
                this.username = username;
            }

            @Override
            public void run() {
                List newMessages;
                int greatestMID = 0; // assume greates message ID is 0 to get all messages

                while (true) {
                    if (username != null && !username.isEmpty()) {                
                        newMessages = chatServiceProxy.getMessages(ET4437ChatClient.userID, 
                            ET4437ChatClient.sessionID, 
                            username, 
                            greatestMID);                    

                        // test if any messages were returned
                        if (newMessages.get(0) != null) {
                            System.out.println("New message(s) found, updating unicast message box.");
                            updateUnicastChatHistory(newMessages);

                            // get MID of most recent message
                            chatwebservice.Message lastMessage = (chatwebservice.Message) newMessages.get(newMessages.size()-1);
                            greatestMID = lastMessage.getMID();
                        }
                    }

                    try {
                        java.lang.Thread.sleep(POLLING_DELAY);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void updateUnicastChatHistory(List messages) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        chatwebservice.Message message;

                        for (Object messageObj : messages) {
                            message = (chatwebservice.Message) messageObj;
                            unicastChatHistory.append(message.getSender() + ": " + message.getMessage() + "\n");
                        }
                    }
                });
            }
        }
    
    public class MulticastChatPanel extends JPanel {
        public MulticastChatPanel() {
            this.setLayout(springLayout);

            buildGui();
        }
        
        public void buildGui() {
            multicastChatHistory = new JTextArea();
            multicastChatHistory.setEditable(false);
            multicastChatHistory.setWrapStyleWord(true);
            multicastChatHistory.setFont(ET4437ChatClient.FONT_REGULAR);
            multicastChatHistory.setMargin(new Insets(5,5,5,5));
            
            JScrollPane multicastScrollPane = new JScrollPane(multicastChatHistory);
            
            multicastMessageBox = new JTextField(400);
            multicastMessageBox.setFont(ET4437ChatClient.FONT_REGULAR);
            
            sendMulticast = new JButton("Send");
            sendMulticast.setFont(ET4437ChatClient.FONT_REGULAR);
            sendMulticast.addActionListener(new MulticastSendButtonAL());

            // add multicast chatbox elements to JPanel
            this.add(multicastScrollPane);
            this.add(multicastMessageBox);
            this.add(sendMulticast);
            
            // set multicast chatbox element contraints
            springLayout.putConstraint(SpringLayout.NORTH, multicastScrollPane, 0, SpringLayout.NORTH, this);
            springLayout.putConstraint(SpringLayout.SOUTH, multicastScrollPane, 0, SpringLayout.NORTH, multicastMessageBox);
            springLayout.putConstraint(SpringLayout.EAST, multicastScrollPane, 0, SpringLayout.EAST, this);
            springLayout.putConstraint(SpringLayout.WEST, multicastScrollPane, 0, SpringLayout.WEST, this);

            springLayout.putConstraint(SpringLayout.SOUTH, multicastMessageBox, 0, SpringLayout.SOUTH, this);
            springLayout.putConstraint(SpringLayout.EAST, multicastMessageBox, 0, SpringLayout.WEST, sendMulticast);
            springLayout.putConstraint(SpringLayout.WEST, multicastMessageBox, 0, SpringLayout.WEST, multicastScrollPane);

            springLayout.putConstraint(SpringLayout.NORTH, sendMulticast, 0, SpringLayout.NORTH, multicastMessageBox);
            springLayout.putConstraint(SpringLayout.SOUTH, sendMulticast, 0, SpringLayout.SOUTH, multicastMessageBox);
            springLayout.putConstraint(SpringLayout.EAST, sendMulticast, 0, SpringLayout.EAST, multicastScrollPane);
        }
        
        public class MulticastSendButtonAL implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = multicastMessageBox.getText();

                if (message.length() > 0) {
                    multicastMessageBox.setText(""); // clear message box
                    boolean messageSent = chatServiceProxy.postMulticast(
                            ET4437ChatClient.userID, 
                            ET4437ChatClient.sessionID, 
                            message
                    );

                    if (messageSent) {
                        System.out.println("Message sent.");
                    } else {
                        System.out.println("Could not send message.");
                    }
                }
            }
        }
    }
    
    public class MulticastMessageUpdater extends Thread {
        @Override
        public void run() {
            List newMessages;
            int greatestMID = 0; // assume greates message ID is 0 to get all messages

            while (true) {              
                newMessages = chatServiceProxy.getMulticast(ET4437ChatClient.sessionID, greatestMID);

                // test if any messages were returned
                if (newMessages.get(0) != null) {
                    System.out.println("New message(s) found, updating unicast message box.");
                    updateMulticastChatHistory(newMessages);

                    // get MID of most recent message
                    chatwebservice.Message lastMessage = (chatwebservice.Message) newMessages.get(newMessages.size()-1);
                    greatestMID = lastMessage.getMID();
                }

                try {
                    java.lang.Thread.sleep(POLLING_DELAY);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void updateMulticastChatHistory(List messages) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    chatwebservice.Message message;

                    for (Object messageObj : messages) {
                        message = (chatwebservice.Message) messageObj;
                        multicastChatHistory.append(message.getSender() + ": " + message.getMessage() + "\n");
                    }
                }
            });
        }
    }
}
