package com.company;

import java.io.*;
import java.util.ArrayList;

public class CSVHandler {


    public void writeToCSV (Game_Entry game_entry, String path) throws IOException {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
        pw.println(/*game_entry.getId()+ "," +*/ game_entry.getHero_name()+","+game_entry.getDamage()+","+game_entry.getKills()+","+game_entry.getPosition());
        pw.flush();
        pw.close();
    }

    public void writeArrayToFile (ArrayList<Game_Entry> game_entries , String path) throws IOException {
        for(Game_Entry g:game_entries){
            writeToCSV(g, path);
        }
    }

    public ArrayList<Game_Entry> readFromCSV (){

    ArrayList <Game_Entry> game_entries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("game_entries.csv"))) {
            br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            //Read Values From The Line
            String[] values = line.split(",");

            //Specify the game entry parameters
            String name = values[0];
            int damage = Integer.parseInt(values[1]);
            int kills = Integer.parseInt(values[2]);
            int position = Integer.parseInt(values[3]);

            //Create a new Game_entry
            Game_Entry game_entry = new Game_Entry(name, damage,kills,position);

            //Add the game entry to the List
            game_entries.add(game_entry);

        }
    }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("reading");
        return game_entries;
    }

    public void eraseLast() throws IOException {
        RandomAccessFile f = new RandomAccessFile(Main.CSV_FILE_PATH, "rw");
        long length = f.length() - 1;
        byte b;
        do {
            length -= 1;
            f.seek(length);
            b = f.readByte();
        } while(b != 10);
        f.setLength(length+1);
        f.close();

    }
}
