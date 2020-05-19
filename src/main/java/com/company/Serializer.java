package com.company;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public void serializeArrayList(List <Game_Entry> game_entries) throws IOException {

        for(Game_Entry x:game_entries){
            ObjectMapper mapper = new XmlMapper();
            mapper.writeValue(new File("C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStats\\game_entries.xml"), x);
        }
    }

    public Game_Entry deserialize(String xml){
        XStream xStream = new XStream();
        return (Game_Entry) xStream.fromXML(xml);
    }
}
