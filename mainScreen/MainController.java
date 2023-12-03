package com.qlilop.kurs.mainScreen;

import com.qlilop.kurs.accountScreen.AccountController;
import com.qlilop.kurs.loginScreen.LoginController;
import com.qlilop.kurs.models.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController {

    private UserManager user;
    private Stage userStage;

    private QuotesModel quotesModel;

    private ObservableList<QuoteItem> observableList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;

    @FXML
    private Button loginButton;

    @FXML
    private TableView<QuoteItem> mainTableView;

    @FXML
    private Label mainTitle;

    @FXML
    private TextField quoteTextFiled;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TextField subjectTextFiled;

    @FXML
    private TextField userTextFiled;

    @FXML
    void initialize() {
        assert datePicker != null :
                "fx:id=\"datePicker\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert deleteButton != null :
                "fx:id=\"deleteButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert loginButton != null :
                "fx:id=\"loginButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert addButton != null :
                "fx:id=\"addButton \" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert mainTableView != null :
                "fx:id=\"mainTableView\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert mainTitle != null :
                "fx:id=\"mainTitle\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert quoteTextFiled != null :
                "fx:id=\"quoteTextFiled\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert saveChangesButton != null :
                "fx:id=\"saveChangesButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert subjectTextFiled != null :
                "fx:id=\"subjectTextFiled\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert userTextFiled != null :
                "fx:id=\"userTextFiled\" was not injected: check your FXML file 'MainScreen.fxml'.";

        quotesModel = new QuotesModel();

        loginButton.setOnAction(
                event -> {
                    if (user == null) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(
                                    getClass()
                                            .getResource("/com/qlilop/kurs/LoginScreen.fxml"
                                            )
                            );
                            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
//                        Parent root = fxmlLoader.getRoot();
                            userStage = new Stage(); //окно
                            userStage.setScene(scene);

                            LoginController editQuoteController = fxmlLoader.getController();
                            editQuoteController.setParent(this);

                            userStage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(
                                    getClass()
                                            .getResource("/com/qlilop/kurs/AccountScreen.fxml"
                                            )
                            );
                            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
//                        Parent root = fxmlLoader.getRoot();
                            userStage = new Stage(); //окно
                            userStage.setScene(scene);

                            AccountController accountController = fxmlLoader.getController();
                            accountController.setParent(this,
                                                        user
                            );
                            userStage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        for (Button button : new Button[]{
                loginButton, addButton, saveChangesButton
        }) {
            button.onKeyPressedProperty().set(
                    event -> {
                        if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                            button.fire();
                        }
                    }
            );
        }
        loginButton.onKeyPressedProperty().set(
                event -> {
                    if (event.getCode().equals(KeyCode.TAB)) {
                        quoteTextFiled.requestFocus();
                    }
                }
        );
        setupTableView();

        setZeroPreparedZone();
        setupAboutZone();

    }

    public void setUser(UserManager user) {
        this.user = user;
        mainTableView
                .getSelectionModel()
                .select(null);
        userStage.close();
        addButton.setDisable(false);
        loginButton.setText("Изменить");
    }

    public void unsetUser() {
        this.user = null;
        mainTableView.getSelectionModel().select(null);
        userStage.close();
        addButton.setDisable(true);
        loginButton.setText("Войти");
    }

    TableColumn quoteColumn;
    TableColumn teacherColumn;
    TableColumn subjectColumn;
    TableColumn dateColumn;

    private void setupTableView() {
        mainTableView.setPlaceholder(
                new Label("Нет данных для отображения"));
        mainTableView.setItems(FXCollections.observableArrayList());
        quoteColumn = mainTableView.getColumns().get(0);
        teacherColumn = mainTableView.getColumns().get(1);
        subjectColumn = mainTableView.getColumns().get(2);
        dateColumn = mainTableView.getColumns().get(3);

        quoteColumn.setCellValueFactory(new PropertyValueFactory<>("quoteText"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        if (quotesModel == null) {
            quotesModel = new QuotesModel();
        }
        observableList =
                FXCollections
                        .observableArrayList(
                                quotesModel
                        );
//        observableList.addListener(
//                (ListChangeListener<QuoteItem>) c -> {
//                    while (c.next()) {
//                        if (c.wasAdded()) {
//                            System.out.println("Добавлено " + c.getAddedSize() + " элементов");
//                        }
//                        if (c.wasRemoved()) {
//                            System.out.println("Удалено " + c.getRemovedSize() + " элементов");
//                        }
//                    }
//                }
//        );
        mainTableView.setItems(observableList);
        mainTableView.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (ChangeListener) (observableValue, oldValue, newValue) -> {
                            //Check whether item is selected and set value of selected item to Label
                            if (mainTableView.getSelectionModel().getSelectedItem() != null) {
                                TableView.TableViewSelectionModel selectionModel =
                                        mainTableView.getSelectionModel();
//                                    ObservableList selectedCells =
//                                            selectionModel.getSelectedCells();
//                                    TablePosition tablePosition = (TablePosition) selectedCells.get(
//                                            0);
//                                    Object val = //получаем конкретный объект который ткнул пользователь.
//                                            tablePosition.getTableColumn().getCellData(newValue);
                                Object value = selectionModel.getSelectedItem();
                                setupAboutZone((QuoteItem) value);
                            } else {
                                setupAboutZone(null);
                            }

                        }
                );
    }

    private void setupAboutZone(QuoteItem item) {
        if (item == null) {
            setZeroPreparedZone();
            return;
        }
        quoteTextFiled.setText(item.getQuoteText());
        subjectTextFiled.setText(item.getSubjectName());
        userTextFiled.setText(item.getTeacherName());
        datePicker.setValue(item.getDate().toLocalDate());
    }

    private void setupAboutZone() {
        //on change textfield

        quoteTextFiled.textProperty().addListener(
                (observable, oldValue, newValue) -> quotesEdition()
        );
        subjectTextFiled.textProperty().addListener(
                (observable, oldValue, newValue) -> quotesEdition()
        );
        userTextFiled.textProperty().addListener(
                (observable, oldValue, newValue) -> quotesEdition()
        );
        datePicker.valueProperty().addListener(
                (observable, oldValue, newValue) -> quotesEdition()
        );
        saveChangesButton.setOnAction(
                event -> {
                    if (!saveChangesButton.isDisabled()) {
                        QuoteItem selected = mainTableView.getSelectionModel().getSelectedItem();
                        System.out.println(selected.getAuthorId());

                        QuoteItem newQ = new QuoteItem(
                                selected.getId(),
                                Date.valueOf(datePicker.getValue()),
                                quoteTextFiled.getText(),
                                userTextFiled.getText(),
                                subjectTextFiled.getText(),
                                selected.getAuthorId()
                        );
                        QuotesManager
                                .updateQuote(
                                        newQ
                                );
                        quotesModel.changeElement(
                                selected,
                                newQ
                        );
                        quotesEdition();

                        observableList.setAll(
                                quotesModel
                        );
                        setZeroPreparedZone();
                        mainTableView.refresh();
                    }

                }
        );
        deleteButton.setOnAction(
                event -> {
                    if (!deleteButton.isDisabled()) {
                        QuoteItem selected = mainTableView.getSelectionModel().getSelectedItem();
                        QuotesManager
                                .deleteQuote(
                                        selected.getId()
                                );
                        quotesModel.removeIf(item -> item.getId() == selected.getId());

                        quotesEdition();
                        observableList.setAll(
                                quotesModel
                        );
                        setZeroPreparedZone();
                        mainTableView.refresh();
                    }
                }
        );
        addButton.setOnAction(
                event -> {
                    if (!addButton.isDisabled()) {

                        if (quoteTextFiled.getText().isEmpty() ||
                                subjectTextFiled.getText().isEmpty() ||
                                userTextFiled.getText().isEmpty()
                        ) {
                            Alert alert = new Alert(
                                    Alert.AlertType.ERROR,
                                    "Не все поля заполнены"
                            );
                            alert.showAndWait();
                            return;
                        }
                        QuoteItem newQ = new QuoteItem(
                                -1,
                                Date.valueOf(datePicker.getValue()),
                                quoteTextFiled.getText(),
                                userTextFiled.getText(),
                                subjectTextFiled.getText(),
                                user.getUser().id
                        );
                        if (quotesModel.hasSameQuote(newQ)) {
                            Alert alert = new Alert(
                                    Alert.AlertType.ERROR,
                                    "Такая цитата уже есть"
                            );
                            alert.showAndWait();
                            return;
                        }
                        QuotesManager
                                .addQuoteToDB(
                                        newQ
                                );
                        quotesModel.add(
                                newQ
                        );
                        quotesEdition();
                        observableList.setAll(
                                quotesModel
                        );
                        setZeroPreparedZone();
                        mainTableView.refresh();
                    }
                }
        );
    }

    private void quotesEdition() {

        QuoteItem item = mainTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            saveChangesButton.setDisable(true);
            deleteButton.setDisable(true);
            return;
        }

        if (user != null) {
            addButton.setDisable(user.getUser() == null);
        }
        boolean isAuth = isAuthorized(item.getAuthorId());
        System.out.println(item.getAuthorId());
        if (isAuth) {
            saveChangesButton.setDisable(false);
            deleteButton.setDisable(false);
        } else {
            deleteButton.setDisable(true);
            saveChangesButton.setDisable(true);
            return;
        }
        if (
                quoteTextFiled.getText().equals(item.getQuoteText())
                        && subjectTextFiled.getText().equals(item.getSubjectName())
                        && userTextFiled.getText().equals(item.getTeacherName())
                        && datePicker.getValue().equals(item.getDate().toLocalDate())
        ) {
            saveChangesButton.setDisable(true);
        }
    }

    private Boolean isAuthorized(Integer authorId) {

        if (user == null) return false;
        if (user.getUser() == null) {
            return false;
        } else {
            return user.getUser().id == authorId ||
                    user.getUser().assignedUsers.isUserInControled(authorId) ||
                    user.getUser().role.equals(UserRole.superUser);
        }
    }

    private void setZeroPreparedZone() {
        mainTableView.getSelectionModel().select(null);
        quoteTextFiled.setText("");
        subjectTextFiled.setText("");
        userTextFiled.setText("");
        datePicker.setValue(LocalDate.now());
        saveChangesButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void buttonsStatus(Boolean disable) {
        saveChangesButton.setDisable(disable);
//        deleteButton.setDisable(disable);
    }

}
