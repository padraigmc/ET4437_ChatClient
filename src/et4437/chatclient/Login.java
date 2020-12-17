/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class Login extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    private SpringLayout layout;
    private JTextField username;
    private JPasswordField password;
    private JLabel userlabel;
    private JLabel passwordlabel;
    private JButton loginButton;
    private JButton previousButton;
    
    Login() {
        layout = new SpringLayout(); 
        this.setLayout(layout);
        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        
        buildGui();
    }
    
    private void buildGui() {
        userlabel = new JLabel("User:");
        this.add(userlabel);
        username = new JTextField(30);
        username.setToolTipText("Type your username");
        //sername.addActionListener(new Login.TextFieldAL());
        this.add(username);
        layout.putConstraint(SpringLayout.WEST, userlabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, userlabel,8+30, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, username,70, SpringLayout.WEST, userlabel); 
        layout.putConstraint(SpringLayout.NORTH, username, 1, SpringLayout.NORTH, userlabel);
        
        passwordlabel = new JLabel("Password:");
        this.add(passwordlabel);      
        password = new JPasswordField(30);
        password.setToolTipText("Type your password");
        //password.addActionListener(new Login.TextFieldAL());
        this.add(password);
        layout.putConstraint(SpringLayout.WEST, passwordlabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, passwordlabel,8+60, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, password,70, SpringLayout.WEST, passwordlabel); 
        layout.putConstraint(SpringLayout.NORTH, password, 1, SpringLayout.NORTH, passwordlabel);   
        
        previousButton = new JButton("Previous");
        previousButton.addActionListener(new previousButtonAL());
        this.add(previousButton);
        layout.putConstraint(SpringLayout.WEST, previousButton,6, SpringLayout.WEST, passwordlabel); 
        layout.putConstraint(SpringLayout.NORTH, previousButton,8, SpringLayout.SOUTH, passwordlabel);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(new loginButtonAL());
        this.add(loginButton);
        layout.putConstraint(SpringLayout.WEST, loginButton, 6, SpringLayout.EAST, previousButton); 
        layout.putConstraint(SpringLayout.NORTH, loginButton,8, SpringLayout.SOUTH, passwordlabel);
    }
    
    private class previousButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                cl.first(ET4437ChatClient.cardHolder);
        }
    }
        
    private class loginButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String uname = username.getText();
            String pword = Arrays.toString(password.getPassword());

            int userID = chatServiceProxy.checkLogin(uname, pword);
            
            if (userID > 0) {
                System.out.println("Login successful!");
                ET4437ChatClient.userID = userID;
                ET4437ChatClient.sessionID = chatServiceProxy.newSession(userID);
                ET4437ChatClient.chatPanel = new ChatGui();
                CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                ET4437ChatClient.cardHolder.add(ET4437ChatClient.chatPanel);
                cl.last(ET4437ChatClient.cardHolder);
            } else {
                System.out.println("Login failed!");
            }
        }
    }
    
    // Action Listener class for the TextFields and PasswordField
    public class TextFieldAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        
            if (e.getSource() == username) {
                System.out.println("Your username is: " + username.getText());
            }
            else if (e.getSource() == password) {
                System.out.println("Your password is: " + new String(password.getPassword()));
            }
            else {
                System.out.println("Error: Textfield not found!");
            }     
        }    
    }
}
