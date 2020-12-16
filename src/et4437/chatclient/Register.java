/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService;
import chatwebservice.ChatWebService_Service;
import java.awt.CardLayout;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class Register extends JPanel {
    
    private static ChatWebService chatServiceProxy;
    private SpringLayout layout;
    private JButton register;
    private JButton previousButton;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField rptPasswordField;
    private JLabel namelabel;
    private JLabel userlabel;
    private JLabel rprtpasswordlabel;
    private JLabel passwordlabel; 
    
    public Register() {
        layout = new SpringLayout(); 
        this.setLayout(layout);
        
        ChatWebService_Service service = new ChatWebService_Service();
        chatServiceProxy = service.getChatWebServicePort();
        
        buildGui();
    
    }
    
    private void buildGui() {
        namelabel = new JLabel("Name:");
        this.add(namelabel); 
        nameField = new JTextField(30);
        nameField.setToolTipText("Type your name");
        nameField.addActionListener(new Register.TextFieldAL());
        this.add(nameField);
        layout.putConstraint(SpringLayout.WEST, namelabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, namelabel,8, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, nameField,70, SpringLayout.WEST, namelabel); 
        layout.putConstraint(SpringLayout.NORTH, nameField, 1, SpringLayout.NORTH, namelabel); 
        
        userlabel = new JLabel("User:");
        this.add(userlabel);
        usernameField = new JTextField(30);
        usernameField.setToolTipText("Type your username");
        usernameField.addActionListener(new Register.TextFieldAL());
        this.add(usernameField);
        layout.putConstraint(SpringLayout.WEST, userlabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, userlabel,8+30, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, usernameField,70, SpringLayout.WEST, userlabel); 
        layout.putConstraint(SpringLayout.NORTH, usernameField, 1, SpringLayout.NORTH, userlabel);
        
        passwordlabel = new JLabel("Password:");
        this.add(passwordlabel);      
        passwordField = new JPasswordField(30);
        passwordField.setToolTipText("Type your password");
        passwordField.addActionListener(new Register.TextFieldAL());
        this.add(passwordField);
        layout.putConstraint(SpringLayout.WEST, passwordlabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, passwordlabel,8+60, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, passwordField,70, SpringLayout.WEST, passwordlabel); 
        layout.putConstraint(SpringLayout.NORTH, passwordField, 1, SpringLayout.NORTH, passwordlabel);   
       
        rprtpasswordlabel = new JLabel("Confirm Password:");
        this.add(rprtpasswordlabel);      
        rptPasswordField = new JPasswordField(30);
        rptPasswordField.setToolTipText("Type your password");
        rptPasswordField.addActionListener(new Register.TextFieldAL());
        this.add(rptPasswordField);
        layout.putConstraint(SpringLayout.WEST, rprtpasswordlabel,6, SpringLayout.WEST, this); 
        layout.putConstraint(SpringLayout.NORTH, rprtpasswordlabel,8+90, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, rptPasswordField,120, SpringLayout.WEST, rprtpasswordlabel); 
        layout.putConstraint(SpringLayout.NORTH, rptPasswordField, 1, SpringLayout.NORTH, rprtpasswordlabel);   
        
        previousButton = new JButton("Previous");
        previousButton.addActionListener(new previousButtonAL());
        this.add(previousButton);
        layout.putConstraint(SpringLayout.WEST, previousButton,6, SpringLayout.WEST, rprtpasswordlabel); 
        layout.putConstraint(SpringLayout.NORTH, previousButton,8, SpringLayout.SOUTH, rprtpasswordlabel);
        
        register = new JButton("Submit");
        register.addActionListener(new registerButtonAL());
        this.add(register);
        layout.putConstraint(SpringLayout.WEST, register, 6, SpringLayout.EAST, previousButton); 
        layout.putConstraint(SpringLayout.NORTH, register,8, SpringLayout.SOUTH, rprtpasswordlabel);
    }
    
    private class registerButtonAL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int userID;
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = Arrays.toString(passwordField.getPassword());
            String rptPassword = Arrays.toString(rptPasswordField.getPassword());
            
            userID = chatServiceProxy.registerUser(name, username, password, rptPassword);
            
            if (userID > 0) {
                System.out.println("Registration success!");
                ET4437ChatClient.userID = userID;
                CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                cl.first(ET4437ChatClient.cardHolder);
            } else {
                System.out.println("Registration failed!");
            }
        }
    }
    
    private class previousButtonAL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (ET4437ChatClient.cardHolder.getLayout());
                cl.first(ET4437ChatClient.cardHolder);
        }
    }
    
    // Action Listener class for the TextFields and PasswordField
    private class TextFieldAL implements ActionListener {
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
        
    private boolean validateInput() {
        boolean isInputValid = true;
        String usernameRegex = "^[a-zA-Z0-9._-]{3,30}$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z]).{8,64}$"; // at least one number and lower case letter. Must be at least 8 chars and max 64 chars
        String passwordStr = Arrays.toString(passwordField.getPassword());
        String rptPasswordStr = Arrays.toString(rptPasswordField.getPassword());

        if (!Pattern.matches(usernameRegex, nameField.getText())) {
            isInputValid = false;
        } else if(!Pattern.matches(passwordRegex, passwordStr)) {
            isInputValid = false;
        } else if(!passwordStr.matches(rptPasswordStr)) {
            isInputValid = false;
        }

        return isInputValid;
    }
}