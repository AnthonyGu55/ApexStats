package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TableStage {
    Stage window;
    TableView<Game_Entry> table = new TableView <>();

    public void display(ArrayList<Game_Entry> game_entries){
        Stage window = new Stage();

        //Hero name column
        TableColumn<Game_Entry, String> hero_name_column = new TableColumn <>("Hero");
        hero_name_column.setMinWidth(200);
        hero_name_column.setCellValueFactory(new PropertyValueFactory<>("hero_name"));

            //Damage column
            TableColumn<Game_Entry, String> damage_column = new TableColumn <>("Damage");
            damage_column.setMinWidth(100);
            damage_column.setCellValueFactory(new PropertyValueFactory<>("damage"));

            //Hero name column
            TableColumn<Game_Entry, String> kills_column = new TableColumn <>("Kills");
            kills_column.setMinWidth(100);
            kills_column.setCellValueFactory(new PropertyValueFactory<>("kills"));

            //Hero name column
            TableColumn<Game_Entry, String> position_column = new TableColumn <>("Position");
            position_column.setMinWidth(100);
            position_column.setCellValueFactory(new PropertyValueFactory<>("position"));

        //TableView
        table.setItems(getGame_entries(game_entries));
        table.getColumns().addAll(hero_name_column, damage_column, kills_column, position_column);

        //Layout
        VBox vBox = new VBox();
        vBox.getChildren().add(table);

        //Scene
        Scene scene = new Scene(vBox, 500, 1000);
        window.setScene(scene);
        window.show();
    }

    public static ObservableList<Game_Entry> getGame_entries(ArrayList<Game_Entry> game_entries){
        ObservableList<Game_Entry> game_entryObservableList = FXCollections.observableArrayList();
        game_entryObservableList.addAll(game_entries);

        return game_entryObservableList;
    }

    public void refreshTable () {
        table.refresh();
    }
}
