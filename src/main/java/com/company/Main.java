package com.company;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application implements EventHandler <ActionEvent> {

    Stage window;

    CSVHandler csvHandler;

    ArrayList <Game_Entry> main_game_entries;

    Button saveButton;
    Button displayButton;
    Button removeButton;

    public DataInputLayout dataInputLayout;

    public TableStage tableStage;

    public String[] heroes = new String[]{"Bangalore", "Bloodhound", "Caustic", "Crypto", "Gibraltar",
            "Lifeline", "Loba", "Mirage", "Octane", "Pathfinder", "Revenant", "Watson", "Wraith"};

    public static final String CSV_FILE_PATH = "C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStats\\game_entries.csv";

    @Override
    public void start (Stage stage) {
        csvHandler        = new CSVHandler();
        main_game_entries = csvHandler.readFromCSV();
        window            = new Stage();
        tableStage   = new TableStage();

        //Data input layout
        dataInputLayout = new DataInputLayout(10);
        dataInputLayout.setPadding(new Insets(10, 20, 20, 20));

        //Save Button
        saveButton = new Button("Save");
        saveButton.setOnAction(this);

        //Display button
        displayButton = new Button("Display data");
        displayButton.setOnAction(this);

        //Remove button
        removeButton = new Button("Remove last entry");
        removeButton.setOnAction(this);

        //Layout
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(20, 20, 20, 20));
        hBox.getChildren().addAll(saveButton, displayButton, removeButton);

        BorderPane border_layout = new BorderPane();
        border_layout.setBottom(hBox);
        border_layout.setCenter(dataInputLayout);

        //Window settings
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(border_layout, 800, 110);
        window.setScene(scene);
        window.show();


    }

    //Handles button events
    @Override
    public void handle (ActionEvent actionEvent) {

        if (actionEvent.getSource() == saveButton) {
            saveToArray(actionEvent);
            tableStage.refreshTable();
        }

        if (actionEvent.getSource() == displayButton) {
            tableStage.display(main_game_entries);
        }

        if (actionEvent.getSource() == removeButton) {
            try {
                removeLastItem();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void removeLastItem () throws IOException {

        Game_Entry tmpgame_Entry = main_game_entries.get(main_game_entries.size()-1);

        boolean answer = ConfirmBox.display("Remove last entry", "Are you sure you want to remove this entry?\n\tHero: " + tmpgame_Entry.getHero_name() + "\n\tDamage: " + tmpgame_Entry.getDamage() + "\n\tKills: " + tmpgame_Entry.getKills() + "\n\tPosition: " + tmpgame_Entry.getPosition());

        if(answer){
            main_game_entries.remove(main_game_entries.size() - 1);
            csvHandler.eraseLast();
        }
    }

    private void saveToArray (ActionEvent actionEvent) {
        String hero_name = dataInputLayout.getHero_name_box().getValue();
        try {
            int damage   = Integer.parseInt(dataInputLayout.getDamage_field().getText());
            int kills    = Integer.parseInt(dataInputLayout.getKills_field().getText());
            int position = Integer.parseInt(dataInputLayout.getPosition_field().getText());

            boolean answer = ConfirmBox.display("New Entry", "Are you sure you want to add this entry?\n\tHero: " + hero_name + "\n\tDamage: " + damage + "\n\tKills: " + kills + "\n\tPosition: " + position);
            if (answer) {
                Game_Entry tmpGame_Entry = new Game_Entry(hero_name, damage, kills, position);
                main_game_entries.add(tmpGame_Entry);

                dataInputLayout.getDamage_field().setText("");
                dataInputLayout.getKills_field().setText("");
                dataInputLayout.getPosition_field().setText("");

                csvHandler.writeToCSV(tmpGame_Entry, "game_entries.csv");
            }
            actionEvent.consume();
        }
        catch (NumberFormatException | IOException exception) {
            AlertBox.display("Wrong input", "Error: You entered a wrong value, which wasn't a number");
            actionEvent.consume();
        }
    }

    private void closeProgram () {
        window.close();
    }

    public class DataInputLayout extends HBox {
        private final ChoiceBox <String> hero_name_box;
        private final TextField          damage_field;
        private final TextField          kills_field;
        private final TextField          position_field;

        public ChoiceBox <String> getHero_name_box () {
            return hero_name_box;
        }

        public TextField getDamage_field () {
            return damage_field;
        }

        public TextField getKills_field () {
            return kills_field;
        }

        public TextField getPosition_field () {
            return position_field;
        }

        public DataInputLayout (int spacing) {
            super(spacing);
            System.out.println("dataInput layout created");
            Label hero_name_label = new Label("Hero");
            hero_name_box = new ChoiceBox <>();
            addArray(hero_name_box, heroes);
            hero_name_box.setValue("Mirage");
            this.getChildren().addAll(hero_name_label, hero_name_box);

            Label damage_label = new Label("Damage");
            damage_field = new TextField();
            this.getChildren().addAll(damage_label, damage_field);

            Label kills_label = new Label("Kills");
            kills_field = new TextField();
            this.getChildren().addAll(kills_label, kills_field);

            Label position_label = new Label("Position");
            position_field = new TextField();
            this.getChildren().addAll(position_label, position_field);


        }

        public void addArray (ChoiceBox <String> hero_name_box, String[] heroes) {
            for (String s : heroes) {
                hero_name_box.getItems().add(s);
            }
        }

    }

    public static void main (String[] args) {
        launch(args);
    }
}