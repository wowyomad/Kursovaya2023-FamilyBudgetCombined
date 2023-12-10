// Imports
package com.gnida.fxmlcontroller.user;

import com.gnida.PTableColumn;
import com.gnida.SceneManager;
import com.gnida.converters.BudgetPropertiesConverter;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.fxmlcontroller.GenericController;
import com.gnida.fxmlcontroller.windows.Screen;
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
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    private ObservableList<User> userList;


    private BudgetPropertiesConverter converter = new BudgetPropertiesConverter();

    private void initContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Добавить");
        MenuItem deleteMenuItem = new MenuItem("Удалить");
        MenuItem openMenuItem = new MenuItem("Открыть");
        addMenuItem.setOnAction(this::handleAddMenuItem);
        deleteMenuItem.setOnAction(this::handleDelete);
        openMenuItem.setOnAction(this::handleOpen);
        contextMenu.getItems().addAll(addMenuItem, deleteMenuItem, openMenuItem);
        tableView.setContextMenu(contextMenu);

    }

    private void initTable() {
        budgetList = tableView.getItems();
        refreshBudgets(true);

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
        initialAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter.bigDecimalConverter));
        expectedExpenseColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter.bigDecimalConverter));
        expectedIncomeColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter.bigDecimalConverter));
        dateTimeStartColumn.setCellFactory(getDateTimeCellFactory());
        dateTimeEndColumn.setCellFactory(getDateTimeCellFactory());
        linkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ownerColumn.setCellFactory(getUserCellFactory());

        linkColumn.setCellFactory(column -> {
            return new TableCell<Budget, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setEditable(false);
                    }
                }
            };
        });

        nameColumn.setOnEditCommit(event -> {
            event.getRowValue().setName(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        initialAmountColumn.setOnEditCommit(event -> {
            event.getRowValue().setInitialAmount(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        expectedExpenseColumn.setOnEditCommit(event -> {
            event.getRowValue().setExpectedExpense(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        expectedIncomeColumn.setOnEditCommit(event -> {
            event.getRowValue().setExpectedIncome(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        dateTimeStartColumn.setOnEditCommit(event -> {
            event.getRowValue().setStartDate(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        dateTimeEndColumn.setOnEditCommit(event -> {
                event.getRowValue().setEndDate(event.getNewValue());
                updateBudgetOnServer(event.getRowValue());
                refreshBudgets(true);

        });

        linkColumn.setOnEditCommit(event -> {
            event.getRowValue().setLink(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        ownerColumn.setOnEditCommit(event -> {
            event.getRowValue().setOwner(event.getNewValue());
            updateBudgetOnServer(event.getRowValue());
            refreshBudgets(true);
        });

        tableView.setEditable(true);
        idColumn.setEditable(false);
        ownerColumn.setEditable(false);
    }

    private void updateBudgetOnServer(Budget budget) {
        Response response = client.sendRequest(SuperRequest.UPDATE_BUDGET_BUDGET.object(budget).build());
        if (!response.getStatus().equals(Response.Status.OK)) {
            System.out.println("Не поучилось обновить");
        } else {
            System.out.println("OK");
        }
    }


    @Override
    protected void initialize() {
        super.initialize();
        initTable();
        initContextMenu();

    }

    private void refreshBudgets(boolean serverRefresh) {
        if (serverRefresh) {
            Response response = client.sendRequest(SuperRequest.GET_BUDGETS_BY_CURRENT_USER.build());
            try {
                if (response.getObject() == null) {
                    budgetList.removeAll();
                } else {
                    budgetList.setAll((List<Budget>) response.getObject());
//                tableView.refresh();
                }
            } catch (NullPointerException | ClassCastException e) {
                System.out.println(e);
                System.out.println("BEDA");
            }
        }
    }

    private void handleOpen(ActionEvent actionEvent) {
        Budget selectedBudget = tableView.getSelectionModel().getSelectedItem();
        System.out.println(selectedBudget.getOwner());
        client.openBudget(selectedBudget);
        SceneManager.loadScene(Screen.TRANSACTIONS_BUDGET_USER);
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
            if (buttonType == confirmButtonType) {
                return selectedItem;
            } else {
                return null;
            }
        });

        Optional<Budget> result = dialog.showAndWait();

        result.ifPresent(budget -> {
            Task<Response> deleteTask = new Task<>() {
                @Override
                protected Response call() throws Exception {
                    return client.sendRequest(SuperRequest.DELETE_BUDGET_BUDGET.object(budget).build());
                }
            };
            deleteTask.setOnScheduled(e -> {
                        System.out.println("VRODE");
                        refreshBudgets(true);
                    }
            );
            deleteTask.setOnFailed(e -> {
                System.out.println("Не получилось удалить");
                System.out.println(e.getSource().getValue());
            });
            Thread thread = new Thread(deleteTask);
            thread.start();

        });
    }

    private void handleAddMenuItem(ActionEvent event) {
        Dialog<Budget> dialog = new Dialog<>();
        dialog.setTitle("Добавить новый бюджет");
        dialog.getDialogPane();
        dialog.setHeaderText("Введите данные нового бюджета");

        Pane pane = dialog.getDialogPane();
        pane.getStylesheets().clear();
        pane.getStylesheets().add(Theme.DARK_THEME);

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


        dialog.getDialogPane().setContent(gridPane);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButtonType);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == confirmButtonType) {
                String name = nameField.getText();
                BigDecimal initialAmount = converter.bigDecimalConverter.fromString(initialAmountField.getText());
                BigDecimal expectedExpense = converter.bigDecimalConverter.fromString(expectedExpenseField.getText());
                BigDecimal expectedIncome = converter.bigDecimalConverter.fromString(expectedIncomeField.getText());
                LocalDate startDate = startDatePicker.getValue();
                LocalTime startTime = LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue());
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDate endDate = endDatePicker.getValue();
                LocalTime endTime = LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue());
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
                String link = linkField.getText();

                Budget budget = new Budget();
                budget.setName(name);
                budget.setInitialAmount(initialAmount);
                budget.setExpectedExpense(expectedExpense);
                budget.setExpectedIncome(expectedIncome);
                budget.setStartDate(startDateTime);
                budget.setEndDate(endDateTime);
                budget.setLink(link);
                budget.setOwner(client.getCurrentUser());

                return budget;
            } else {
                return null;
            }
        });

        Optional<Budget> result = dialog.showAndWait();

        result.ifPresent(budget -> {
            System.out.println("Добавляем...");
            Task<Response> addTask = new Task<>() {
                @Override
                protected Response call() throws Exception {
                    System.out.println("Отправляем...");
                    return client.sendRequest(SuperRequest.POST_BUDGET_BUDGET.object(budget).build());
                }
            };
            addTask.setOnSucceeded(e -> {
                System.out.println("VRODE ADDED");
                refreshBudgets(true);
            });
            addTask.setOnFailed(e -> {
                System.out.println("Не получилось");
            });
            Thread thread = new Thread(addTask);
            thread.start();

        });
    }

    private Callback<TableColumn<Budget, LocalDateTime>, TableCell<Budget, LocalDateTime>> getDateTimeCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setText(converter.localDateTimeConverter.toString(item));
                } else {
                    setText(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (isEditable()) {
                    LocalDateTime currentDateTime = getItem();

                    Dialog<LocalDateTime> dialog = new Dialog<>();
                    dialog.setTitle("Изменить дату и время");
                    dialog.setHeaderText("Выберите новую дату и время");

                    Label dateLabel = new Label("Дата:");
                    DatePicker datePicker = new DatePicker(currentDateTime.toLocalDate());
                    datePicker.setConverter(converter.localDateConverter);
                    Label timeLabel = new Label("Время:");
                    Spinner<Integer> hourSpinner = new Spinner<>(0, 23, currentDateTime.getHour());
                    Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, currentDateTime.getMinute());

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
                            System.out.println(date);
                            LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());

                            return LocalDateTime.of(date, time);
                        } else {
                            return null;
                        }
                    });

                    Optional<LocalDateTime> result = dialog.showAndWait();

                    result.ifPresent(newDateTime -> {
                        commitEdit(newDateTime);
                        tableView.refresh();
                    });
                }
            }
        };
    }

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

                if (isEditable()) {
                    ComboBox<User> comboBox = new ComboBox<>();
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
}
