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
import java.util.List;
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
public class Register extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    private SpringLayout springLayout;
    private JButton registerButton;
    private JButton previousButton;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField rptPasswordField;
    private JTextArea errorLabel;
    private JLabel namelabel;
    private JLabel userlabel;
    private JLabel rptPasswordLabel;
    private JLabel passwordlabel; 
    
    public Register() {
        springLayout = new SpringLayout(); 
        this.setLayout(springLayout);
        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        
        buildGui();
    
    }
    
    private void buildGui() {
        // label for name text field
        namelabel = new JLabel("Name:");
        namelabel.setFont(FONT_LARGE);
        this.add(namelabel); 
        
        // name text field
        nameField = new JTextField(30);
        nameField.setFont(FONT_LARGE);
        nameField.setToolTipText("Type your name");
        nameField.addActionListener(new Register.TextFieldAL());
        this.add(nameField);
        springLayout.putConstraint(SpringLayout.NORTH, nameField, 100, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nameField, 60, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, namelabel, 0, SpringLayout.NORTH, nameField);
        springLayout.putConstraint(SpringLayout.EAST, namelabel, -30, SpringLayout.WEST, nameField);
        
        // label for username field
        userlabel = new JLabel("User:");
        userlabel.setFont(FONT_LARGE);
        this.add(userlabel);
        
        
        usernameField = new JTextField(30);
        usernameField.setFont(FONT_LARGE);
        usernameField.setToolTipText("Type your username");
        usernameField.addActionListener(new Register.TextFieldAL());
        this.add(usernameField);
        springLayout.putConstraint(SpringLayout.NORTH, usernameField, 30, SpringLayout.SOUTH, nameField);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameField, 0, SpringLayout.HORIZONTAL_CENTER, nameField);
        springLayout.putConstraint(SpringLayout.NORTH, userlabel, 0, SpringLayout.NORTH, usernameField);
        springLayout.putConstraint(SpringLayout.EAST, userlabel, -30, SpringLayout.WEST, usernameField);
        
        passwordlabel = new JLabel("Password:");
        passwordlabel.setFont(FONT_LARGE);
        this.add(passwordlabel);      
        passwordField = new JPasswordField(30);
        passwordField.setFont(FONT_LARGE);
        passwordField.setToolTipText("Type your password");
        passwordField.addActionListener(new Register.TextFieldAL());
        this.add(passwordField);        
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 30, SpringLayout.SOUTH, usernameField);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordField, 0, SpringLayout.HORIZONTAL_CENTER, nameField);
        springLayout.putConstraint(SpringLayout.NORTH, passwordlabel, 0, SpringLayout.NORTH, passwordField);
        springLayout.putConstraint(SpringLayout.EAST, passwordlabel, -30, SpringLayout.WEST, passwordField);
         
       
        rptPasswordLabel = new JLabel("Confirm Password:");
        rptPasswordLabel.setFont(FONT_LARGE);
        this.add(rptPasswordLabel);      
        rptPasswordField = new JPasswordField(30);
        rptPasswordField.setFont(FONT_LARGE);
        rptPasswordField.setToolTipText("Type your password");
        rptPasswordField.addActionListener(new Register.TextFieldAL());
        this.add(rptPasswordField);
        springLayout.putConstraint(SpringLayout.NORTH, rptPasswordField, 30, SpringLayout.SOUTH, passwordField);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rptPasswordField, 0, SpringLayout.HORIZONTAL_CENTER, nameField);
        springLayout.putConstraint(SpringLayout.NORTH, rptPasswordLabel, 0, SpringLayout.NORTH, rptPasswordField);
        springLayout.putConstraint(SpringLayout.EAST, rptPasswordLabel, -30, SpringLayout.WEST, rptPasswordField);
        
        errorLabel = new JTextArea("");
        errorLabel.setFont(FONT_LARGE);
        errorLabel.setForeground(Color.RED);
        this.add(errorLabel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.SOUTH, errorLabel, -200, SpringLayout.SOUTH, this);
        
        previousButton = new JButton("Previous");
        previousButton.setFont(FONT_LARGE);
        previousButton.addActionListener(new previousButtonAL());
        this.add(previousButton);
        springLayout.putConstraint(SpringLayout.SOUTH, previousButton, -20, SpringLayout.SOUTH, this); 
        springLayout.putConstraint(SpringLayout.WEST, previousButton, 75, SpringLayout.WEST, this);
        
        registerButton = new JButton("Register");
        registerButton.setFont(FONT_LARGE);
        registerButton.addActionListener(new registerButtonAL());
        this.add(registerButton);
        springLayout.putConstraint(SpringLayout.NORTH, registerButton, 0, SpringLayout.NORTH, previousButton); 
        springLayout.putConstraint(SpringLayout.EAST, registerButton, -75, SpringLayout.EAST, this);
        
        ET4437ChatClient.frame.getRootPane().setDefaultButton(registerButton);
    }
    
    private class registerButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userID;
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = Arrays.toString(passwordField.getPassword());
            String rptPassword = Arrays.toString(rptPasswordField.getPassword());
            String inputError = Validate.validateRegister(username, password, rptPassword);
            
            if (inputError == "") {
                userID = chatServiceProxy.registerUser(name, username, password, rptPassword);
            
                if (userID > 0) {
                    System.out.println("Registration success!");
                    ET4437ChatClient.userID = userID;
                    CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                    cl.first(ET4437ChatClient.cardHolder);
                } else {
                    System.out.println("Registration failed!");
                }
            } else {
                errorLabel.setText(inputError);
            }
        }
    }
    
    private class previousButtonAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                cl.first(ET4437ChatClient.cardHolder);
        }
    }
    
    // Action Listener class for the TextFields and PasswordField
    private class TextFieldAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nameField) {
                System.out.println("Your name is: " + nameField.getText());
            }
            else if (e.getSource() == usernameField) {
                System.out.println("Your username is: " + usernameField.getText());
            }
            else if (e.getSource() == passwordField) {
                System.out.println("Your password is: " + new String(passwordField.getPassword()));
            }
            else {
                System.out.println("Error: Textfield not found!");
            }
        }
    }
}