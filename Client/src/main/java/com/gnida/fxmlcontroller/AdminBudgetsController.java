package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import com.gnida.domain.BudgetDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.math.BigDecimal;
import java.util.Optional;

public class AdminBudgetsController extends GenericController {

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

    @Override
    protected void initialize() {
        contextMenu.getItems().addAll(deleteMenuItem, editMenuItem, addMenuItem);
        list = tableView.getItems();


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

