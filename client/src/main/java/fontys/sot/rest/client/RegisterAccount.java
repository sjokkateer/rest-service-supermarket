package fontys.sot.rest.client;

import fontys.sot.rest.client.classes.RegistrationRequestResponseHandler;
import fontys.sot.rest.service.models.User;
import response.helpers.classes.Description;
import response.helpers.classes.Error;

import javax.swing.*;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterAccount extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField enterPasswordField;
    private JPasswordField confirmPasswordField;
    private JLabel usernameLb;
    private JLabel passwordField;
    private JLabel confirmLb;
    private JButton registerBtn;
    private JLabel feedbackLb;
    private JPanel registrationPanel;

    private RegistrationRequestResponseHandler rrrh;

    public RegisterAccount() {
        this("REGISTRATION FORM");
    }

    public RegisterAccount(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(registrationPanel);
        pack();

        rrrh = new RegistrationRequestResponseHandler();

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        Response response = rrrh.register(
                usernameTextField.getText(),
                enterPasswordField.getPassword(),
                confirmPasswordField.getPassword()
        );

        displayAppropriateResult(response);
    }

    public void displayAppropriateResult(Response response) {
        feedbackLb.setForeground(Color.RED);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            feedbackLb.setForeground(Color.GREEN);

            User u = response.readEntity(User.class);
            feedbackLb.setText("Successfully created acc with username: " + u.getName());
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            String result = "<html>";

            for (String error : response.readEntity(Error.class).getErrors()) {
                result += "- " + error + "<br/>";
            }

            result += "</html>";

            feedbackLb.setText(result);
        } else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
            feedbackLb.setText(response.readEntity(Description.class).getDescription());
        } else {
            feedbackLb.setText("Something other went wrong: " + response.getStatus());
        }
    }

    public static void main(String[] args) {
        RegisterAccount registrationApp = new RegisterAccount();
        registrationApp.setVisible(true);
    }
}
