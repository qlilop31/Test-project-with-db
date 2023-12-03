package com.qlilop.kurs.loginScreen;

import java.net.URL;
import java.util.ResourceBundle;

import com.qlilop.kurs.mainScreen.MainController;
import com.qlilop.kurs.models.UserManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    private MainController parent;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label infoLabel;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button registerButton;

    private final LoginModel loginModel;

    public LoginController() {
        loginModel = new LoginModel();
    }

    @FXML
    void initialize() {
        assert infoLabel != null :
                "fx:id=\"infoLabel\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert loginButton != null :
                "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert loginTextField != null :
                "fx:id=\"loginTextField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordTextField != null :
                "fx:id=\"passwordTextField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert registerButton != null :
                "fx:id=\"registerButton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        screenInit();

        loginTextField.onActionProperty().set(event -> {
            passwordTextField.requestFocus();
            checkFields();
        });
        loginTextField.onKeyPressedProperty().set(event -> {
            checkFields();
        });
        passwordTextField.onActionProperty().set(event -> {
            loginButton.requestFocus();
            checkFields();
        });
        passwordTextField.onKeyPressedProperty().set(event -> {
            checkFields();
            if (event.getCode().equals(javafx.scene.input.KeyCode.TAB)) {
                loginButton.requestFocus();
            }
        });
        loginButton.onActionProperty().set(
                event -> {
                    UserManager user = new UserManager(loginTextField.getText(),
                                                       passwordTextField.getText()
                    );
                    if (user.getUser() == null) {
                        setupScreen(
                                "Такого пользователя не существует \n или логин/пароль неверен");
                        user = null;
                    } else {
                        parent.setUser(user);
                    }
                }
        );
        registerButton.onActionProperty().set(event -> {
            UserManager user = new UserManager();
            String error = user.registerUser(loginTextField.getText(),
                              passwordTextField.getText()
            );
            if (user.getUser() == null) {
                setupScreen(
                        error
                );
                user = null;
            } else {
                parent.setUser(user);
            }
        });
        loginButton.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.TAB)) {
                registerButton.requestFocus();
            }
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                loginButton.fire();
            }
        });
        registerButton.onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.TAB)) {
                loginButton.requestFocus();
            }
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                registerButton.fire();
            }
        });
    }

    private void screenInit() {
        setupScreen("");

    }

    private void checkFields() {
        //get textfield data and check if it is correct
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        if (!login.equals("") && !password.equals("")) {
            if (password.equals("ILoveDucks")) {
                setupScreen("Этот пароль занят Еленой. \n Попробуйте другой пароль.");
            } else {
                setupScreen(null);
            }
        } else {
            setupScreen("Пожалуйста, заполните все поля.");
        }
    }

    private void setupScreen(String errorMessage) {
        if (errorMessage == null) {
            infoLabel.setText("");
            infoLabel.setVisible(false);
            loginButton.setDisable(false);
            registerButton.setDisable(false);
        } else {
            infoLabel.setText(errorMessage);
            infoLabel.setVisible(true);
            loginButton.setDisable(true);
            registerButton.setDisable(true);
        }
    }

    public void setParent(MainController parent) {
        this.parent = parent;
    }

}
