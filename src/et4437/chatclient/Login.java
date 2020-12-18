/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import static et4437.chatclient.ET4437ChatClient.FONT_LARGE;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class Login extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    private SpringLayout springLayout;
    private JTextField username;
    private JPasswordField password;
    private JLabel userlabel;
    private JLabel passwordlabel;
    private JTextArea errorLabel;
    private JButton loginButton;
    private JButton previousButton;
    
    Login() {
        springLayout = new SpringLayout(); 
        this.setLayout(springLayout);
        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        
        buildGui();
    }
    
    private void buildGui() {
        userlabel = new JLabel("User:");
        userlabel.setFont(ET4437ChatClient.FONT_LARGE);
        this.add(userlabel);
        
        username = new JTextField(30);
        username.setFont(ET4437ChatClient.FONT_LARGE);
        username.setToolTipText("Type your username");
        this.add(username);
        
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, username, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, username, -75, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, userlabel, 0, SpringLayout.NORTH, username);
        springLayout.putConstraint(SpringLayout.EAST, userlabel, -30, SpringLayout.WEST, username);
        
        passwordlabel = new JLabel("Password:");
        passwordlabel.setFont(ET4437ChatClient.FONT_LARGE);
        this.add(passwordlabel);      
        password = new JPasswordField(30);
        password.setFont(ET4437ChatClient.FONT_LARGE);
        password.setToolTipText("Type your password");
        this.add(password);
        
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, password, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, password, 0, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, passwordlabel, 0, SpringLayout.NORTH, password);
        springLayout.putConstraint(SpringLayout.EAST, passwordlabel, -30, SpringLayout.WEST, password);
        
        errorLabel = new JTextArea("");
        errorLabel.setFont(FONT_LARGE);
        errorLabel.setForeground(Color.RED);
        this.add(errorLabel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.SOUTH, errorLabel, -200, SpringLayout.SOUTH, this);
        
        previousButton = new JButton("Previous");
        previousButton.addActionListener(new previousButtonAL());
        previousButton.setFont(ET4437ChatClient.FONT_LARGE);
        this.add(previousButton);
        
        springLayout.putConstraint(SpringLayout.SOUTH, previousButton, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.WEST, previousButton, 75, SpringLayout.WEST, this);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonAL());
        loginButton.setFont(ET4437ChatClient.FONT_LARGE);
        this.add(loginButton);
        
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 0, SpringLayout.NORTH, previousButton);
        springLayout.putConstraint(SpringLayout.EAST, loginButton, -75, SpringLayout.EAST, this);
    }
    
    private class previousButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
            cl.first(ET4437ChatClient.cardHolder);
        }
    }
        
    private class LoginButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            errorLabel.setText("");
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
                errorLabel.setText(Validate.LOGIN_FAILED);
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
