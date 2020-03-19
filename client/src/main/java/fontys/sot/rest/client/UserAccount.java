package fontys.sot.rest.client;

import fontys.sot.rest.client.classes.LoginRequestResponseHandler;
import fontys.sot.rest.service.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAccount extends JFrame {
    private JPasswordField passwordField;
    private JTextField userNameTextField;
    private JLabel userNameLb;
    private JLabel passwordLb;
    private JPanel loginPanel;
    private JButton loginBtn;
    private JLabel userDetailsLb;
    private JLabel registerLb;
    private JButton registerBtn;
    private JLabel loginDetailsLb;
    private JLabel uNameLb;
    private JLabel pwdLb;

    private LoginRequestResponseHandler lrrh;

    public UserAccount() {
        this("LOGIN FORM");
    }

    public UserAccount(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(loginPanel);
        pack();

        lrrh = new LoginRequestResponseHandler();

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin(userNameTextField.getText(), String.valueOf(passwordField.getPassword()));
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterAccount register = new RegisterAccount();
                register.setVisible(true);
            }
        });
    }

    private void validateLogin(String username, String password) {
        User user = lrrh.login(username, password);
        userDetailsLb.setForeground(Color.RED);
        String text = "Username and/or password were incorrect.";

        if (user != null) {
            OrderApplication orderApp = new OrderApplication(user);
            orderApp.setVisible(true);

            // Terminate the previous window since login was successful.
            dispose();
        }

        userDetailsLb.setText(text);
    }

    public static void main(String[] args) {
        UserAccount app = new UserAccount();
        app.setVisible(true);
    }

}
