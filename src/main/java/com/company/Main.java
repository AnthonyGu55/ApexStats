package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JFrame{
    public Main() {
        final ArrayList <Game_Entry> game_entries = new ArrayList<Game_Entry>();
        final JFrame frame = this;

        GUIPanel panel = new GUIPanel();

        final Serializer serializer = new Serializer();

        JButton button = new JButton("Save");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String hero_name = panel.getHero_name_field().getText();
                int damage = Integer.parseInt(panel.getDamage_field().getText());
                int kills = Integer.parseInt(panel.getKills_field().getText());
                int position = Integer.parseInt(panel.getPosition_field().getText());

                game_entries.add(new Game_Entry(hero_name, damage, kills, position));
                for(Game_Entry x:game_entries){
                    System.out.println(x);
                }

            }
        });
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    serializer.arrayToJson(game_entries);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Closed");
                e.getWindow().dispose();
            }
        });

        this.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
    }

public class GUIPanel extends JPanel{
    private final JTextField hero_name_field;
    private final JTextField damage_field;
    private final JTextField kills_field;
    private final JTextField position_field;

    public JTextField getHero_name_field() {
        return hero_name_field;
    }

    public JTextField getDamage_field() {
        return damage_field;
    }

    public JTextField getKills_field() {
        return kills_field;
    }

    public JTextField getPosition_field() {
        return position_field;
    }

    public GUIPanel() {

        JLabel hero_name_label = new JLabel("Hero Name");
        hero_name_field = new JTextField(10);

        add(hero_name_label);
        add(hero_name_field);

        JLabel damage_label= new JLabel("Damage");
        damage_field = new JTextField(5);
        add(damage_label);
        add(damage_field);

        JLabel kills_label = new JLabel("Kills");
        kills_field = new JTextField(2);
        add(kills_label);
        add(kills_field);

        JLabel position_label = new JLabel("Position");
        position_field = new JTextField(2);
        add(position_label);
        add(position_field);



    }

}
    public static void main(String[] args) throws IOException {
        new Main();

    }
}