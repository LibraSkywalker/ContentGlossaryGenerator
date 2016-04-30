package Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Bill on 2016/4/25.
 */
public class WordList {
    public ArrayList<String> data = new ArrayList<>();
    String address;

    public WordList(){}

    public WordList(String fileName){
        load(fileName);
        address = fileName;
    }

    void load(String fileName){
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader input = new BufferedReader(file);
            while(true){
                String now = input.readLine();
                if (now == null) break;
                data.add(now);
            }
            input.close();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void add(String newWord){
        data.add(newWord);
    }

    public boolean remove(String word){
        return data.remove(word);
    }

    public boolean find(String word){
        return data.contains(word);
    }

    public int findPos(String word){
        return data.indexOf(word);
    }
    public void display(){
        data.sort(String::compareTo);
        for (String word: data) {
            System.out.println(word);
        }
    }

    public void save(String fileName){
        data.sort(String::compareTo);

        try {
            FileWriter file = new FileWriter(fileName);
            BufferedWriter output = new BufferedWriter(file);
            for (String word: data) {
                output.write(word);
                output.newLine();
            }
            output.close();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save(){
        save(address);
    }
}
