package com.gnida.fxmlcontroller;

import com.gnida.domain.BudgetDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class BudgetsController {

    @FXML
    private Button exitButton;

    @FXML
    private TableView<BudgetDto> tableView;

    @FXML
    void initialize() {

    }

    @FXML
    void onBackClicked(ActionEvent event) {

    }

}
