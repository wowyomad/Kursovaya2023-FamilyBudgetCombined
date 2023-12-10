package com.gnida.fxmlcontroller.user;

import com.gnida.PTableColumn;
import com.gnida.converters.TransactionPropertiesConverter;
import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.entity.Transaction;
import com.gnida.entity.User;
import com.gnida.enums.CategoryType;
import com.gnida.fxmlcontroller.GenericController;
import com.gnida.fxmlcontroller.windows.Theme;
import com.gnida.model.Response;
import com.gnida.requests.SuperRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private List<Category> budgetCategories;

    TransactionPropertiesConverter converter = new TransactionPropertiesConverter();

    void updateCategories() {
        Response response = client.sendRequest(SuperRequest.GET_BUDGET_CATEGORIES_BY_BUDGET.object(client.getBudget()).build());
        if (!Response.Status.OK.equals(response.getStatus())) {
            throw new RuntimeException(response.getStatus().toString());
        }
        try {
            budgetCategories = (List<Category>) response.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void refreshTransactions(boolean receiveFromServer) {
        if (receiveFromServer) {
            Response response = client.sendRequest(SuperRequest.GET_TRANSACTIONS_BY_BUDGET.object(client.getBudget()).build());
            if (!Response.Status.OK.equals(response.getStatus())) {
                throw new RuntimeException("BEDA!");
            }
            try {
                if (response.getObject() == null) {
                    transactionList.removeAll();
                    return;
                }
                List<Transaction> transactions = (List<Transaction>) response.getObject();

                transactionList.setAll(transactions);
                tableView.refresh();
            } catch (ClassCastException | NullPointerException e) {
                System.out.println("ой");
            }
        }
    }

    void initTable() {
        transactionList = tableView.getItems();
        refreshTransactions(true);

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

        tableView.setEditable(true);
        idColumn.setEditable(false);
        dateColumn.setEditable(false);
        budgetColumn.setEditable(false);
        userColumn.setEditable(false);
    }

    @Override
    protected void initialize() {
        super.initialize();
        initTable();
        initContextMenu();
        budgetCategories = new ArrayList<>();

    }

    private void initContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Добавить");
        MenuItem deleteMenuItem = new MenuItem("Удалить");
        addMenuItem.setOnAction(this::handleAddMenuItem);
        deleteMenuItem.setOnAction(this::handleDelete);
        contextMenu.getItems().add(addMenuItem);
        contextMenu.getItems().add(deleteMenuItem);
        tableView.setContextMenu(contextMenu);
    }

    private void handleDelete(ActionEvent event) {
        Transaction selectedItem = tableView.getSelectionModel().getSelectedItem();
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("Удалить транзакцию");
        dialog.setHeaderText("Вы уверены, что хотите удалить?");
        DialogPane pane = dialog.getDialogPane();
        pane.getStylesheets().clear();
        pane.getStylesheets().add(Theme.DARK_THEME);

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
            Task<Response> deleteTask = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    return client.sendRequest(
                            SuperRequest.DELETE_TRANSACTION_TRANSACTION
                                    .object(transaction)
                                    .build());
                }
            };
            deleteTask.setOnSucceeded(e -> {
                refreshTransactions(true);
            });
            deleteTask.setOnFailed(e -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            });
        });
    }

    private void handleAddMenuItem(ActionEvent event) {
        Transaction transaction = new Transaction();

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

        Category addCategory = new Category();
        addCategory.setName("...");
        categoryComboBox.getItems().addAll(budgetCategories);
        categoryComboBox.getItems().add(addCategory);
        categoryComboBox.setOnAction(e -> {
            Category selectedCategory = categoryComboBox.getValue();
            if (selectedCategory == addCategory) {
                openAddCategoryDialog();
            }
        });
        Label commentLabel = new Label("Комментарий:");
        Button commentButton = new Button("Редактировать");
        commentButton.setOnAction(e -> showCommentDialog(transaction));


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

        dialog.getDialogPane().setContent(gridPane);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                Date date = converter.dateConverter.fromString(datePicker.getValue().toString());
                BigDecimal amount = converter.bigDecimalConverter.fromString(amountField.getText());
                Category category = categoryComboBox.getValue();
                Budget budget = client.getBudget();

                transaction.setDate(date);
                transaction.setAmount(amount);
                transaction.setCategory(category);
                transaction.setUser(client.getCurrentUser());
                transaction.setBudget(budget);

                return transaction;
            } else {
                return null;
            }
        });

        Optional<Transaction> result = dialog.showAndWait();

        result.ifPresent(tr -> {
            transactionList.add(tr);
            tableView.refresh();
        });
    }

    private void openAddCategoryDialog() {
        Dialog<Category> dialog = new Dialog<>();
        dialog.setHeaderText(null);
        dialog.setTitle("Добавление категории");
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label typeLabel = new Label("Type:");
        ChoiceBox<CategoryType> typeChoice = new ChoiceBox<>(FXCollections.observableArrayList(CategoryType.values()));
        typeChoice.getSelectionModel().selectFirst();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(typeLabel, 0, 1);
        gridPane.add(typeChoice, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                String name = nameField.getText();
                CategoryType type = typeChoice.getValue();
                Category category = new Category();
                category.setName(name);
                category.setType(type);
                category.setBudgetId(client.getBudget().getId());

                return category;
            } else {
                return null;
            }
        });

        Optional<Category> result = dialog.showAndWait();

        result.ifPresent(category -> {
            client.sendRequest(SuperRequest.POST_CATEGORY_CATEGORY.object(category).build());
            updateCategories();
        });


    }

    private Callback<TableColumn<Transaction, Category>, TableCell<Transaction, Category>> getCategoryCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (isEditable()) {
                    ComboBox<Category> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.categoryToStringConverter);
                    Category category1 = new Category();
                    category1.setName("Food");
                    Category category2 = new Category();
                    category2.setName("Transport");
                    comboBox.setItems(FXCollections.observableArrayList(category1, category2));

                    setGraphic(comboBox);

                    setText(null);

                    comboBox.setValue(getItem());

                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    private Callback<TableColumn<Transaction, User>, TableCell<Transaction, User>> getUserCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setText(item.getLogin());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (isEditable()) {
                    ComboBox<User> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.userToStringConverter);
                    User user1 = new User();
                    user1.setLogin("1234");
                    user1.setId(1);
                    User user2 = new User();
                    user2.setLogin("1234");
                    user2.setId(2);
                    comboBox.setItems(FXCollections.observableArrayList(user1, user2));

                    setGraphic(comboBox);

                    setText(null);

                    comboBox.setValue(getItem());

                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    private Callback<TableColumn<Transaction, Budget>, TableCell<Transaction, Budget>> getBudgetCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Budget item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (isEditable()) {
                    ComboBox<Budget> comboBox = new ComboBox<>();
                    comboBox.setConverter(converter.budgetToStringConverter);
                    Budget budget1 = new Budget();
                    budget1.setName("Бюджет 1");
                    Budget budget2 = new Budget();
                    budget2.setName("Бюджет 2");
                    comboBox.setItems(FXCollections.observableArrayList(budget1, budget2));

                    setGraphic(comboBox);

                    setText(null);

                    comboBox.setValue(getItem());

                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                        commitEdit(newValue);
                        tableView.refresh();
                    });
                }
            }
        };
    }

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


