package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVSaver {


    public void WriteToCSV(Game_Entry game_entry, String path) throws IOException {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
        pw.println(game_entry.getHero_name()+","+game_entry.getDamage()+","+game_entry.getKills()+","+game_entry.getPosition());
        pw.flush();
        pw.close();
    }

    public void WriteArrayToFile(ArrayList<Game_Entry> game_entries , String path) throws IOException {
        for(Game_Entry g:game_entries){
            WriteToCSV(g, path);
        }
    }
}
