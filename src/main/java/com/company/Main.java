package com.company;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    Stage window;
    CSVSaver saver;
    ArrayList<Game_Entry> game_entries;

    public String[] heroes = new String[]{"Bangalore", "Bloodhound", "Caustic", "Crypto", "Gibraltar",
            "Lifeline", "Loba", "Mirage", "Octane", "Pathfinder", "Revenant", "Watson", "Wraith"};

    @Override
    public void start (Stage stage){
        game_entries = new ArrayList <>();
        saver = new CSVSaver();
        window = new Stage();


        DataInputLayout dataInputLayout = new DataInputLayout();
        //Buttons
        Button button = new Button("Save");
        button.setOnAction(e ->{

                String hero_name = dataInputLayout.getHero_name_box().getValue();
                try {
                    int damage = Integer.parseInt(dataInputLayout.getDamage_field().getText());
                    int kills = Integer.parseInt(dataInputLayout.getKills_field().getText());
                    int position = Integer.parseInt(dataInputLayout.getPosition_field().getText());

                    boolean anwser = ConfirmBox.display("New Entry", "Are you sure you want to add this entry?\n\tHero: " + hero_name + "\n\tDamage: " + damage +"\n\tKills: " + kills + "\n\tPosition: " + position);
                    if(anwser) {
                        game_entries.add(new Game_Entry(hero_name, damage, kills, position));

                        dataInputLayout.getDamage_field().setText("");
                        dataInputLayout.getKills_field().setText("");
                        dataInputLayout.getPosition_field().setText("");
                    }
                    e.consume();
                }catch(NumberFormatException exception){
                    AlertBox.display("Wrong input", "Error: You entered a wrong value, which wasn't a number");
                     e.consume();
                }

        });
        StackPane pane = new StackPane();
        pane.getChildren().add(button);



        //Layout
        BorderPane border_layout = new BorderPane();
        border_layout.setBottom(pane);
        border_layout.setCenter(dataInputLayout);


        window.setOnCloseRequest(e ->{
            e.consume();
            try {
                closeProgram();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(border_layout,900, 100);
        window.setScene(scene);
        window.show();


    }

    private void closeProgram () throws IOException {
        saver.WriteArrayToFile(game_entries, "C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStats\\game_entries.csv");
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

        public DataInputLayout (){

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