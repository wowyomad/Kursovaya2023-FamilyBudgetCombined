package com.gnida.fxmlcontroller.user;

import com.gnida.PTableColumn;
import com.gnida.converters.TransactionPropertiesConverter;
import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.entity.Transaction;
import com.gnida.entity.User;
import com.gnida.fxmlcontroller.GenericController;
import com.gnida.model.Response;
import com.gnida.requests.SuperRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

// Class definition
public class TransactionsUserController extends GenericController {

    public TableView<Transaction> tableView;
    public PTableColumn<Transaction, Integer> idColumn;
    public PTableColumn<Transaction, Date> dateColumn;
    public PTableColumn<Transaction, BigDecimal> amountColumn;
    public PTableColumn<Transaction, Category> categoryColumn;
    public PTableColumn<Transaction, String> commentColumn;
    public PTableColumn<Transaction, User> userColumn;
    public PTableColumn<Transaction, Budget> budgetColumn;

    private ObservableList<Transaction> transactionList;

    TransactionPropertiesConverter converter = new TransactionPropertiesConverter();

    @Override
    protected void initialize() {
        super.initialize();

        Response response = client.sendRequest(SuperRequest.GET_TRANSACTIONS_BY_BUDGET.build());
        if(!Response.Status.OK.equals(response.getStatus())) {
            throw new RuntimeException("BEDA!");
        }
        transactionList.setAll((List<Transaction>) response.getObject());


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));

        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter.dateConverter));
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter.bigDecimalConverter));
        categoryColumn.setCellFactory(getCategoryCellFactory());
        commentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userColumn.setCellFactory(getUserCellFactory());
        budgetColumn.setCellFactory(getBudgetCellFactory());

        dateColumn.setOnEditCommit(event -> event.getRowValue().setDate(event.getNewValue()));
        amountColumn.setOnEditCommit(event -> event.getRowValue().setAmount(event.getNewValue()));
        categoryColumn.setOnEditCommit(event -> event.getRowValue().setCategory(event.getNewValue()));
        commentColumn.setOnEditCommit(event -> event.getRowValue().setComment(event.getNewValue()));
        userColumn.setOnEditCommit(event -> event.getRowValue().setUser(event.getNewValue()));
        budgetColumn.setOnEditCommit(event -> event.getRowValue().setBudget(event.getNewValue()));

        tableView.setItems(transactionList);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Добавить");
        MenuItem deleteMenuItem = new MenuItem("Удалить");
        addMenuItem.setOnAction(this::handleAddMenuItem);
        deleteMenuItem.setOnAction(this::handleDelete);
        contextMenu.getItems().add(addMenuItem);
        contextMenu.getItems().add(deleteMenuItem);
        tableView.setContextMenu(contextMenu);
        tableView.setEditable(true);
        idColumn.setEditable(false);
        userColumn.setEditable(false);
    }

    private void handleDelete(ActionEvent event) {
        Transaction selectedItem = tableView.getSelectionModel().getSelectedItem();
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("Удалить транзакцию");
        dialog.setHeaderText("Вы уверены, что хотите удалить?");
        DialogPane pane = dialog.getDialogPane();

        ButtonType confirmButtonType = new ButtonType("Удалить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Не удалять", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                return selectedItem;
            } else {
                return null;
            }
        });

        Optional<Transaction> result = dialog.showAndWait();

        result.ifPresent(transaction -> {
            transactionList.remove(transaction);
            tableView.refresh();
        });
    }

    private void handleAddMenuItem(ActionEvent event) {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("Добавить новую транзакцию");
        dialog.setHeaderText("Введите данные новой транзакции");

        Label dateLabel = new Label("Дата:");
        DatePicker datePicker = new DatePicker();
        Label amountLabel = new Label("Сумма:");
        TextField amountField = new TextField();
        Label categoryLabel = new Label("Категория:");
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.setConverter(converter.categoryToStringConverter);
        Category category1 = new Category();
        category1.setName("Food");
        Category category2 = new Category();
        category2.setName("Transport");
        categoryComboBox.setItems(FXCollections.observableArrayList(category1, category2));
        Label commentLabel = new Label("Комментарий:");
        Button commentButton = new Button("Редактировать");
        Label userLabel = new Label("Пользователь:");
        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.setConverter(converter.userToStringConverter);
        User user1 = new User();
        user1.setLogin("1234");
        user1.setId(1);
        User user2 = new User();
        user2.setLogin("1234");
        user2.setId(2);
        userComboBox.setItems(FXCollections.observableArrayList(user1, user2));
        Label budgetLabel = new Label("Бюджет:");
        ComboBox<Budget> budgetComboBox = new ComboBox<>();
        budgetComboBox.setConverter(converter.budgetToStringConverter);
        Budget budget1 = new Budget();
        budget1.setName("Бюджет 1");
        Budget budget2 = new Budget();
        budget2.setName("Бюджет 2");
        budgetComboBox.setItems(FXCollections.observableArrayList(budget1, budget2));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(dateLabel, 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(amountLabel, 0, 1);
        gridPane.add(amountField, 1, 1);
        gridPane.add(categoryLabel, 0, 2);
        gridPane.add(categoryComboBox, 1, 2);
        gridPane.add(commentLabel, 0, 3);
        gridPane.add(commentButton, 1, 3);
        gridPane.add(userLabel, 0, 4);
        gridPane.add(userComboBox, 1, 4);
        gridPane.add(budgetLabel, 0, 5);
        gridPane.add(budgetComboBox, 1, 5);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        // Set the result converter for the dialog to create a new transaction from the fields
        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                // Get the values from the fields
                Date date = converter.dateConverter.fromString(datePicker.getValue().toString());
                BigDecimal amount = converter.bigDecimalConverter.fromString(amountField.getText());
                Category category = categoryComboBox.getValue();
                String comment = commentButton.getText();
                User user = userComboBox.getValue();
                Budget budget = budgetComboBox.getValue();

                // Create a new transaction with the values
                Transaction transaction = new Transaction();
                transaction.setDate(date);
                transaction.setAmount(amount);
                transaction.setCategory(category);
                transaction.setComment(comment);
                transaction.setUser(user);
                transaction.setBudget(budget);

                // Return the new transaction
                return transaction;
            } else {
                // Return null if the button is not confirm
                return null;
            }
        });

        // Show the dialog and wait for the result
        Optional<Transaction> result = dialog.showAndWait();

        // If the result is present, add it to the transaction list and refresh the table view
        result.ifPresent(transaction -> {
            transactionList.add(transaction);
            tableView.refresh();
        });
    }

    // A method to create a cell factory for the Category column
    private Callback<TableColumn<Transaction, Category>, TableCell<Transaction, Category>> getCategoryCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);

                // If the cell is not empty, set the text to the category name
                if (!empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                // If the cell is editable, create a combo box to edit the category
                if (isEditable()) {
                    // Create a combo box with the available categories
                    ComboBox<Category> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.categoryToStringConverter);
                    Category category1 = new Category();
                    category1.setName("Food");
                    Category category2 = new Category();
                    category2.setName("Transport");
                    comboBox.setItems(FXCollections.observableArrayList(category1, category2));

                    // Set the cell graphic to the combo box
                    setGraphic(comboBox);

                    // Set the cell text to null
                    setText(null);

                    // Set the combo box value to the current category
                    comboBox.setValue(getItem());

                    // Add a listener to the combo box value to commit the edit and update the table view
                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    // A method to create a cell factory for the User column
    private Callback<TableColumn<Transaction, User>, TableCell<Transaction, User>> getUserCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                // If the cell is not empty, set the text to the user name
                if (!empty) {
                    setText(item.getLogin());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                // If the cell is editable, create a combo box to edit the user
                if (isEditable()) {
                    // Create a combo box with the available users
                    ComboBox<User> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.userToStringConverter);
                    User user1 = new User();
                    user1.setLogin("1234");
                    user1.setId(1);
                    User user2 = new User();
                    user2.setLogin("1234");
                    user2.setId(2);
                    comboBox.setItems(FXCollections.observableArrayList(user1, user2));

                    // Set the cell graphic to the combo box
                    setGraphic(comboBox);

                    // Set the cell text to null
                    setText(null);

                    // Set the combo box value to the current user
                    comboBox.setValue(getItem());

                    // Add a listener to the combo box value to commit the edit and update the table view
                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    // A method to create a cell factory for the Budget column
    private Callback<TableColumn<Transaction, Budget>, TableCell<Transaction, Budget>> getBudgetCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Budget item, boolean empty) {
                super.updateItem(item, empty);

                // If the cell is not empty, set the text to the budget name
                if (!empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                // If the cell is editable, create a combo box to edit the budget
                if (isEditable()) {
                    // Create a combo box with the available budgets
                    ComboBox<Budget> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.budgetToStringConverter);
                    Budget budget1 = new Budget();
                    budget1.setName("Бюджет 1");
                    Budget budget2 = new Budget();
                    budget2.setName("Бюджет 2");
                    comboBox.setItems(FXCollections.observableArrayList(budget1, budget2));

                    // Set the cell graphic to the combo box
                    setGraphic(comboBox);

                    // Set the cell text to null
                    setText(null);

                    // Set the combo box value to the current budget
                    comboBox.setValue(getItem());

                    // Add a listener to the combo box value to commit the edit and update the table view
                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    // A method to create a dialog for the Comment column
    private void showCommentDialog(Transaction transaction) {
        // Create a dialog to edit the comment
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Редактировать комментарий");
        dialog.setHeaderText("Введите новый комментарий");

        // Create a label and a text area for the dialog
        Label commentLabel = new Label("Комментарий:");
        TextArea commentArea = new TextArea();
        commentArea.setWrapText(true);
        commentArea.setText(transaction.getComment());

        // Create a grid pane to layout the label and the text area
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(commentLabel, 0, 0);
        gridPane.add(commentArea, 0, 1);

        // Set the dialog content to the grid pane
        dialog.getDialogPane().setContent(gridPane);

        // Add a button to the dialog to confirm the new comment
        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        // Set the result converter for the dialog to get the new comment from the text area
        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                // Get the value from the text area
                String comment = commentArea.getText();

                // Return the new comment
                return comment;
            } else {
                // Return null if the button is not confirm
                return null;
            }
        });

        // Show the dialog and wait for the result
        Optional<String> result = dialog.showAndWait();

        // If the result is present, update the transaction comment and refresh the table view
        result.ifPresent(comment -> {
            transaction.setComment(comment);
            tableView.refresh();
        });
    }

    // A method to create a cell factory for the Comment column
    private Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>> getCommentCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // If the cell is not empty, set the text to the comment
                if (!empty) {
                    setText(item);
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                // If the cell is editable, show the comment dialog
                if (isEditable()) {
                    // Get the current transaction from the row
                    Transaction transaction = getTableRow().getItem();

                    // Show the comment dialog with the transaction
                    showCommentDialog(transaction);
                }
            }
        };
    }
}


