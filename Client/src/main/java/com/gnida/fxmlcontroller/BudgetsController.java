package com.gnida.fxmlcontroller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gnida.Client;
import com.gnida.Main;
import com.gnida.SceneManager;
import com.gnida.converter.Converter;
import com.gnida.domain.BudgetDto;
import com.gnida.domain.UserDto;
import com.gnida.entity.User;
import com.gnida.model.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BudgetsController extends GenericController {

    public TableColumn<BudgetDto, String> initialColumn;
    public TableColumn<BudgetDto, String> leaderColumn;
    public TableColumn<BudgetDto, String> nameColumn;
    public TableColumn<BudgetDto, String> incomeColumn;
    public TableColumn<BudgetDto, String> expenseColumn;
    public TableColumn<BudgetDto, String> totalColumn;
    public TableColumn<BudgetDto, String> finishedColumn;
    @FXML
    private Button backButton;

    @FXML
    private TableView<BudgetDto> tableView;

    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteMenuItem = new MenuItem("Delete");
    MenuItem editMenuItem = new MenuItem("Edit");
    MenuItem addMenuItem = new MenuItem("Add");

    ObservableList<BudgetDto> list;


    @FXML
    void initialize() {
        contextMenu.getItems().addAll(deleteMenuItem, editMenuItem, addMenuItem);

        list = tableView.getItems();
        list.add(BudgetDto.builder()
                .name("сука")
                        .name("говно")
                        .leader("я")
                        .expenses(BigDecimal.valueOf(2500))
                        .income(BigDecimal.valueOf(4000))
                        .initialAmount(BigDecimal.valueOf(5550))
                        .isFinished(false)
                        .total(BigDecimal.valueOf(-1500))
                .build());

        initialColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getInitialAmount().toString()));
        leaderColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getLeader()));
        nameColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getName()));
        incomeColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getIncome().toString()));
        expenseColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getExpenses().toString()));
        totalColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().getTotal().toString()));
        finishedColumn.setCellValueFactory(budget -> new SimpleStringProperty(budget.getValue().isFinished() ? "Да" : "Нет"));

        tableView.setRowFactory(tv -> {
            TableRow<BudgetDto> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                }
            });

            deleteMenuItem.setOnAction(e -> handleDelete(row.getItem()));

            return row;
        });
    }

    @FXML
    void onBackClicked(ActionEvent event) {
        SceneManager.getPreviousRoot(scene);
    }

    private void handleDelete(BudgetDto item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление бюджета");
        alert.setHeaderText(null);
        alert.setContentText("Вы уверены, что хотите удалить бюджет?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tableView.getItems().remove(item);
        }
    }


}

