package com.qlilop.kurs.accountScreen;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import com.qlilop.kurs.PasswordHelper;
import com.qlilop.kurs.mainScreen.MainController;
import com.qlilop.kurs.models.UpdateUserEnum;
import com.qlilop.kurs.models.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button leaveAccount;

    @FXML
    private TextField loginTextFiled;

    @FXML
    private TextField passwordTextField;
    private MainController parent;
    private UserManager userManager;

    @FXML
    void initialize() {
        assert leaveAccount != null :
                "fx:id=\"leaveAccount\" was not injected: check your FXML file 'AccountScreen.fxml'.";
        assert loginTextFiled != null :
                "fx:id=\"loginTextFiled\" was not injected: check your FXML file 'AccountScreen.fxml'.";
        assert passwordTextField != null :
                "fx:id=\"passwordTextField\" was not injected: check your FXML file 'AccountScreen.fxml'.";
        loginTextFiled.onActionProperty().set(
                event -> {
                    String login = loginTextFiled.getText();
                    if (!login.isEmpty()) {
                        String error = userManager.setupNewField(
                                userManager.getUser(),
                                UpdateUserEnum.LOGIN,
                                login
                        );
                        if (error != null) {
                            //alert error
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Error");
                            alert.setContentText(error);
                            alert.showAndWait();
                            return;
                        } else {
                            parent.setUser(userManager);
                        }
                    }

                }
        );
        passwordTextField.onActionProperty().set(
                event -> {
                    String password = passwordTextField.getText();
                    if (!password.isEmpty()) {
                        String error = null;
                        try {
                            error = userManager.setupNewField(
                                    userManager.getUser(),
                                    UpdateUserEnum.PASSWORD,
                                    PasswordHelper.generateStorngPasswordHash(password)
                            );
                        } catch (NoSuchAlgorithmException e) {
                            error = "Ошибка в создании пароля, метод не найден";
                        } catch (InvalidKeySpecException e) {
                            error = "Ошибка в создании пароля, неизвестная ошибка";
                        }
                        if (error != null) {
                            //alert error
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Error");
                            alert.setContentText(error);
                            alert.showAndWait();
                            return;
                        } else {
                            parent.setUser(userManager);
                        }
                    } else {
                        //alert error
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Пароль не может быть пустым");
                        alert.showAndWait();
                    }
                }
        );
        leaveAccount.setOnAction(
                event -> {
                    parent.unsetUser();
                }
        );
    }


    public void setParent(MainController parent,
                          UserManager userManager
    ) {
        this.parent = parent;
        this.userManager = userManager;
    }

}
