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


public class Main extends Application implements EventHandler<ActionEvent> {

    Stage                 window;

    CSVHandler csvHandler;

    ArrayList<Game_Entry> game_entries;

    Button saveButton;
    Button readButton;

    public DataInputLayout dataInputLayout;

    public String[] heroes = new String[]{"Bangalore", "Bloodhound", "Caustic", "Crypto", "Gibraltar",
            "Lifeline", "Loba", "Mirage", "Octane", "Pathfinder", "Revenant", "Watson", "Wraith"};

    @Override
    public void start (Stage stage){
        csvHandler   = new CSVHandler();
        game_entries = csvHandler.readFromCSV();
        window       = new Stage();

        //Data input layout
        dataInputLayout = new DataInputLayout(10);
        dataInputLayout.setPadding(new Insets(10,20,20,20));

        //Save Button
        saveButton = new Button("Save");
        saveButton.setOnAction(this);

        //Read Button
        readButton = new Button("Read");
        readButton.setOnAction(this);

        //Layout
        HBox pane = new HBox(10);
        pane.setPadding(new Insets(20,20,20,20));
        pane.getChildren().addAll(saveButton, readButton);

        BorderPane border_layout = new BorderPane();
        border_layout.setBottom(pane);
        border_layout.setCenter(dataInputLayout);

        //Window settings
        window.setOnCloseRequest(e ->{
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(border_layout,800, 110);
        window.setScene(scene);
        window.show();


    }

    //Handles button events
    @Override
    public void handle (ActionEvent actionEvent) {

        if(actionEvent.getSource() == saveButton){
            saveToArray(actionEvent);
        }
        if(actionEvent.getSource() == readButton){
            readFromCSV();
        }
    }

    private void saveToArray (ActionEvent actionEvent) {
        String hero_name = dataInputLayout.getHero_name_box().getValue();
        try {
            int damage = Integer.parseInt(dataInputLayout.getDamage_field().getText());
            int kills = Integer.parseInt(dataInputLayout.getKills_field().getText());
            int position = Integer.parseInt(dataInputLayout.getPosition_field().getText());

            boolean answer = ConfirmBox.display("New Entry", "Are you sure you want to add this entry?\n\tHero: " + hero_name + "\n\tDamage: " + damage + "\n\tKills: " + kills + "\n\tPosition: " + position);
            if (answer) {
                Game_Entry tmpGame_Entry = new Game_Entry(hero_name, damage, kills, position);
                game_entries.add(tmpGame_Entry);

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

    private void readFromCSV(){
        if(game_entries.isEmpty())
            game_entries = csvHandler.readFromCSV();

        for(Game_Entry g:game_entries){
            System.out.println(g);
        }

    }

    private void closeProgram (){
        window.close();
    }

    public class DataInputLayout extends HBox {
        private final ChoiceBox<String> hero_name_box ;
        private final TextField damage_field;
        private final TextField kills_field;
        private final TextField position_field;

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

        public DataInputLayout (int spacing){
            super(spacing);
            System.out.println("dataInput layout created");
            Label hero_name_label = new Label("Hero");
            hero_name_box = new ChoiceBox <>();
            addArray(hero_name_box, heroes);
            hero_name_box.setValue("Mirage");
            this.getChildren().addAll(hero_name_label,hero_name_box);

            Label damage_label = new Label("Damage");
            damage_field = new TextField();
            this.getChildren().addAll(damage_label,damage_field);

            Label kills_label = new Label("Kills");
            kills_field = new TextField();
            this.getChildren().addAll(kills_label, kills_field);

            Label position_label = new Label("Position");
            position_field = new TextField();
            this.getChildren().addAll(position_label, position_field);


        }

        public void addArray(ChoiceBox<String> hero_name_box, String[] heroes){
            for(String s:heroes){
                hero_name_box.getItems().add(s);
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}