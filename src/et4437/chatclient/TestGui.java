package et4437.chatclient;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class TestGui {
 
    TestGui() {
 
        final String card1Text = "Card 1";
        final String card2Text = "Card 2";
        final String card3Text = "Card 3";
        final JPanel cards; //a panel that uses CardLayout
        // button commands
        final String FIRST = "FIRST";
        final String NEXT = "NEXT";
        final String PREVIOUS = "PREVIOUS";
        final String LAST = "LAST";
        
        JButton login;
        JButton register;
        
        SpringLayout layout = new SpringLayout();
        
        JFrame frame = new JFrame("ET4437");
 
        //Create the "cards".
        JPanel homePanel = new JPanel();
        
        login = new JButton("Login Here");
        login.addActionListener(new loginButtonAL());
        homePanel.add(login);
        layout.putConstraint(SpringLayout.WEST, login,70, SpringLayout.WEST, homePanel); 
        layout.putConstraint(SpringLayout.NORTH, login,8+90, SpringLayout.NORTH, homePanel);
        
        register = new JButton("Regitser Here");
        register.addActionListener(new registerButtonAL());
        homePanel.add(register);
        layout.putConstraint(SpringLayout.WEST, register,180, SpringLayout.WEST, homePanel); 
        layout.putConstraint(SpringLayout.NORTH, register,8+90, SpringLayout.NORTH, homePanel);
        homePanel.add(new JButton("Button 1 - Card 1"));
        homePanel.add(new JButton("Button 2 - Card 1"));
        homePanel.setBackground(new Color(255,0,0));
 
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField on Card 2", 20));
        card2.setBackground(new Color(0,255,0));
 
        JPanel card3 = new JPanel();
        card3.add(new JLabel("Card 3"));
        card3.setBackground(new Color(0,0,255));
 
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(homePanel);
        cards.add(card2, card2Text);
        cards.add(card3, card3Text);
 
        class ControlActionListenter implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                String cmd = e.getActionCommand();
                if (cmd.equals(FIRST)) {
                    cl.first(cards);
                } else if (cmd.equals(NEXT)) {
                    cl.next(cards);
                } else if (cmd.equals(PREVIOUS)) {
                    cl.previous(cards);
                } else if (cmd.equals(LAST)) {
                    cl.last(cards);
                }
            }
        }
        ControlActionListenter cal = new ControlActionListenter();
 
        JButton btn1 = new JButton("First");
        btn1.setActionCommand(FIRST);
        btn1.addActionListener(cal);
 
        JButton btn2 = new JButton("Next");
        btn2.setActionCommand(NEXT);
        btn2.addActionListener(cal);
 
        JButton btn3 = new JButton("Previous");
        btn3.setActionCommand(PREVIOUS);
        btn3.addActionListener(cal);
 
        JButton btn4 = new JButton("Last");
        btn4.setActionCommand(LAST);
        btn4.addActionListener(cal);
 
        JPanel controlButtons = new JPanel();
        controlButtons.add(btn1);
        controlButtons.add(btn2);
        controlButtons.add(btn3);
        controlButtons.add(btn4);
 
        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);
        pane.add(controlButtons, BorderLayout.PAGE_END);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
    
    // Action Listener class for the Button
    private class registerButtonAL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //call class registerwindow
               Register register = new Register(); 
        }
    } 
    
     // Action Listener class for the Button
    private class loginButtonAL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           Login login = new Login();
        }
    }
}