package com.company;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Serializer {

    public void arrayToJson(ArrayList <Game_Entry> game_entries) throws IOException {
        FileWriter fw = new FileWriter(new File("C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStats\\game_entries.json"));
        fw.write(toJson(game_entries));
        fw.close();
    }
    public String toJson(ArrayList<Game_Entry> game_entries){
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Game_Entry g:game_entries){
            sb.append(gson.toJson(g));
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(sb.toString()));
        sb.append("]");
        return sb.toString();
    }

}
