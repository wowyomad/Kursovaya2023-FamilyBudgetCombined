// Imports
package com.gnida.fxmlcontroller.user;

import com.gnida.PTableColumn;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.fxmlcontroller.GenericController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

// Class definition
public class BudgetsUserController extends GenericController {

    public TableView<Budget> tableView;
    public PTableColumn<Budget, Integer> idColumn;
    public PTableColumn<Budget, String> nameColumn;
    public PTableColumn<Budget, BigDecimal> initialAmountColumn;
    public PTableColumn<Budget, BigDecimal> expectedExpenseColumn;
    public PTableColumn<Budget, BigDecimal> expectedIncomeColumn;
    public PTableColumn<Budget, LocalDateTime> dateTimeStartColumn;
    public PTableColumn<Budget, LocalDateTime> dateTimeEndColumn;
    public PTableColumn<Budget, String> linkColumn;
    public PTableColumn<Budget, User> ownerColumn;

    private ObservableList<Budget> budgetList;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private StringConverter<BigDecimal> bigDecimalConverter = new StringConverter<>() {
        @Override
        public String toString(BigDecimal object) {
            return object == null ? "" : object.toString();
        }

        @Override
        public BigDecimal fromString(String string) {
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
    };

    private StringConverter<User> userToStringConverter = new StringConverter<User>() {
        @Override
        public String toString(User user) {
            if (user != null)
                return user.getLogin();
            else
                return "";
        }

        @Override
        public User fromString(String s) {
            return null;
        }
    };
    private StringConverter<LocalDateTime> localDateTimeConverter = new StringConverter<>() {
        @Override
        public String toString(LocalDateTime object) {
            return object == null ? "" : object.format(dateTimeFormatter);
        }

        @Override
        public LocalDateTime fromString(String string) {
            try {
                return LocalDateTime.parse(string, dateTimeFormatter);
            } catch (Exception e) {
                return LocalDateTime.now();
            }
        }
    };

    @Override
    protected void initialize() {
        super.initialize();

        budgetList = FXCollections.observableArrayList();
        Budget budget1 = new Budget();
        budget1.setId(1);
        budget1.setName("Бюджет 1");
        budget1.setInitialAmount(new BigDecimal("1000.00"));
        budget1.setExpectedExpense(new BigDecimal("500.00"));
        budget1.setExpectedIncome(new BigDecimal("200.00"));
        budget1.setStartDate(LocalDateTime.now());
        budget1.setEndDate(LocalDateTime.now().plusMonths(1));
        budget1.setLink("1234");
        budget1.setOwner(new User());
        budget1.getOwner().setLogin("test1");
        budgetList.add(budget1);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        initialAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initialAmount"));
        expectedExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("expectedExpense"));
        expectedIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("expectedIncome"));
        dateTimeStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dateTimeEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        initialAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
        expectedExpenseColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
        expectedIncomeColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
        dateTimeStartColumn.setCellFactory(getDateTimeCellFactory());
        dateTimeEndColumn.setCellFactory(getDateTimeCellFactory());
        linkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ownerColumn.setCellFactory(getUserCellFactory());

        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));
        initialAmountColumn.setOnEditCommit(event -> event.getRowValue().setInitialAmount(event.getNewValue()));
        expectedExpenseColumn.setOnEditCommit(event -> event.getRowValue().setExpectedExpense(event.getNewValue()));
        expectedIncomeColumn.setOnEditCommit(event -> event.getRowValue().setExpectedIncome(event.getNewValue()));
        dateTimeStartColumn.setOnEditCommit(event -> event.getRowValue().setStartDate(event.getNewValue()));
        dateTimeEndColumn.setOnEditCommit(event -> event.getRowValue().setEndDate(event.getNewValue()));
        linkColumn.setOnEditCommit(event -> event.getRowValue().setLink(event.getNewValue()));
        ownerColumn.setOnEditCommit(event -> event.getRowValue().setOwner(event.getNewValue()));

        tableView.setItems(budgetList);

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
        ownerColumn.setEditable(false);
    }

    private void handleDelete(ActionEvent event) {
        Budget selectedItem = tableView.getSelectionModel().getSelectedItem();
        Dialog<Budget> dialog = new Dialog<>();
        dialog.setTitle("Удалить бюджет");
        dialog.setHeaderText("Вы уверены, что хотите удалить?");
        DialogPane pane = dialog.getDialogPane();

        ButtonType confirmButtonType = new ButtonType("Удалить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Не удалять", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        dialog.setResultConverter(buttonType -> {
            if(buttonType == confirmButtonType) {
                return selectedItem;
            } else {
                return null;
            }
        });

        Optional<Budget> result = dialog.showAndWait();

        result.ifPresent(budget -> {
            budgetList.remove(budget);
            tableView.refresh();
        });
    }

    private void handleAddMenuItem(ActionEvent event) {
        Dialog<Budget> dialog = new Dialog<>();
        dialog.setTitle("Добавить новый бюджет");
        dialog.setHeaderText("Введите данные нового бюджета");

        Label nameLabel = new Label("Название:");
        TextField nameField = new TextField();
        Label initialAmountLabel = new Label("Начальная сумма:");
        TextField initialAmountField = new TextField();
        Label expectedExpenseLabel = new Label("Ожидаемые расходы:");
        TextField expectedExpenseField = new TextField();
        Label expectedIncomeLabel = new Label("Ожидаемые доходы:");
        TextField expectedIncomeField = new TextField();
        Label dateTimeStartLabel = new Label("Дата и время начала:");
        DatePicker startDatePicker = new DatePicker();
        Spinner<Integer> startHourSpinner = new Spinner<>(0, 23, 0);
        Spinner<Integer> startMinuteSpinner = new Spinner<>(0, 59, 0);
        Label dateTimeEndLabel = new Label("Дата и время окончания:");
        DatePicker endDatePicker = new DatePicker();
        Spinner<Integer> endHourSpinner = new Spinner<>(0, 23, 0);
        Spinner<Integer> endMinuteSpinner = new Spinner<>(0, 59, 0);
        Label linkLabel = new Label("Ссылка:");
        TextField linkField = new TextField();
        Label ownerLabel = new Label("Владелец:");
        ComboBox<User> ownerComboBox = new ComboBox<>();
        ownerComboBox.setConverter(userToStringConverter);
        User user1 = new User();
        user1.setLogin("1234");
        user1.setId(1);
        User user2 = new User();
        user2.setLogin("1234");
        user2.setId(2);
        ownerComboBox.setItems(FXCollections.observableArrayList(user1, user2));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(initialAmountLabel, 0, 1);
        gridPane.add(initialAmountField, 1, 1);
        gridPane.add(expectedExpenseLabel, 0, 2);
        gridPane.add(expectedExpenseField, 1, 2);
        gridPane.add(expectedIncomeLabel, 0, 3);
        gridPane.add(expectedIncomeField, 1, 3);
        gridPane.add(dateTimeStartLabel, 0, 4);
        gridPane.add(startDatePicker, 1, 4);
        gridPane.add(startHourSpinner, 2, 4);
        gridPane.add(startMinuteSpinner, 3, 4);
        gridPane.add(dateTimeEndLabel, 0, 5);
        gridPane.add(endDatePicker, 1, 5);
        gridPane.add(endHourSpinner, 2, 5);
        gridPane.add(endMinuteSpinner, 3, 5);
        gridPane.add(linkLabel, 0, 6);
        gridPane.add(linkField, 1, 6);
        gridPane.add(ownerLabel, 0, 7);
        gridPane.add(ownerComboBox, 1, 7);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        // Set the result converter for the dialog to create a new budget from the fields
        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                // Get the values from the fields
                String name = nameField.getText();
                BigDecimal initialAmount = bigDecimalConverter.fromString(initialAmountField.getText());
                BigDecimal expectedExpense = bigDecimalConverter.fromString(expectedExpenseField.getText());
                BigDecimal expectedIncome = bigDecimalConverter.fromString(expectedIncomeField.getText());
                LocalDate startDate = startDatePicker.getValue();
                LocalTime startTime = LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue());
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDate endDate = endDatePicker.getValue();
                LocalTime endTime = LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue());
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
                String link = linkField.getText();
                User owner = ownerComboBox.getValue();

                // Create a new budget with the values
                Budget budget = new Budget();
                budget.setName(name);
                budget.setInitialAmount(initialAmount);
                budget.setExpectedExpense(expectedExpense);
                budget.setExpectedIncome(expectedIncome);
                budget.setStartDate(startDateTime);
                budget.setEndDate(endDateTime);
                budget.setLink(link);
                budget.setOwner(owner);

                // Return the new budget
                return budget;
            } else {
                // Return null if the button is not confirm
                return null;
            }
        });

        // Show the dialog and wait for the result
        Optional<Budget> result = dialog.showAndWait();

        // If the result is present, add it to the budget list and refresh the table view
        result.ifPresent(budget -> {
            budgetList.add(budget);
            tableView.refresh();
        });
    }

    // A method to create a cell factory for the LocalDateTime columns
    private Callback<TableColumn<Budget, LocalDateTime>, TableCell<Budget, LocalDateTime>> getDateTimeCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                // If the cell is not empty, set the text to the formatted date and time
                if (!empty) {
                    setText(localDateTimeConverter.toString(item));
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                // If the cell is editable, create a dialog to edit the date and time
                if (isEditable()) {
                    // Get the current date and time from the cell
                    LocalDateTime currentDateTime = getItem();

                    // Create a dialog to edit the date and time
                    Dialog<LocalDateTime> dialog = new Dialog<>();
                    dialog.setTitle("Изменить дату и время");
                    dialog.setHeaderText("Выберите новую дату и время");

                    // Create the labels and fields for the dialog
                    Label dateLabel = new Label("Дата:");
                    DatePicker datePicker = new DatePicker(currentDateTime.toLocalDate());
                    Label timeLabel = new Label("Время:");
                    Spinner<Integer> hourSpinner = new Spinner<>(0, 23, currentDateTime.getHour());
                    Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, currentDateTime.getMinute());

                    // Create a grid pane to layout the labels and fields
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.add(dateLabel, 0, 0);
                    gridPane.add(datePicker, 1, 0);
                    gridPane.add(timeLabel, 0, 1);
                    gridPane.add(hourSpinner, 1, 1);
                    gridPane.add(minuteSpinner, 2, 1);

                    dialog.getDialogPane().setContent(gridPane);

                    ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

                    dialog.setResultConverter(buttonType -> {
                        if (buttonType == confirmButtonType) {
                            // Get the values from the fields
                            LocalDate date = datePicker.getValue();
                            LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());

                            return LocalDateTime.of(date, time);
                        } else {
                            return null;
                        }
                    });

                    // Show the dialog and wait for the result
                    Optional<LocalDateTime> result = dialog.showAndWait();

                    // If the result is present, commit the edit and update the table view
                    result.ifPresent(newDateTime -> {
                        commitEdit(newDateTime);
                        tableView.refresh();
                    });
                }
            }
        };
    }

    // A method to create a cell factory for the User column
    private Callback<TableColumn<Budget, User>, TableCell<Budget, User>> getUserCellFactory() {
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
}
