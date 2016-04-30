package Kernel;

import Tools.WordList;
import jxl.Sheet;
import jxl.Workbook;

import java.io.*;
import java.sql.*;
import static GUI.myWindows.setUI;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toString;

/**
 * Created by Bill on 2016/4/23.
 */
public class Kernel {
    public static WordList known,unFamiliar,newWords;
    static Integer count = 0;
    static void build(){
        known = new WordList("data/learnt.txt");
        unFamiliar = new WordList("data/unfamiliar.txt");
        newWords = new WordList();
    }

    public static void learn(String str){
        known.remove(str);
        newWords.remove(str);
        unFamiliar.remove(str);
        unFamiliar.add(str);
    }

    public static void know(String str){
        known.remove(str);
        newWords.remove(str);
        unFamiliar.remove(str);
        known.add(str);
    }

    static boolean check(String _word){
        String word = _word;
        if (known.find(word)) return true;

        return check(word,"s") || check(word,"es") ||
               check(word,"ies","y") || check(word,"ed") ||
               check(word,"ied","y") || check(word,"ed","e") ||
               check(word,"ing") || check(word,"ing","e");
    }

    static boolean check(String word,String suf){
        int len = word.length();
        int len2 = suf.length();
        if (len <= len2) return false;
        if (!word.substring(len - len2).equals(suf)) return false;
        return known.find(word.substring(0,len - len2));
    }

    static boolean check(String word,String suf,String addition){
        int len = word.length();
        int len2 = suf.length();
        if (len <= len2) return false;
        if (!word.substring(len - len2).equals(suf)) return false;
        return known.find(word.substring(0,len - len2) + addition);
    }

    public static void generate(String str){
        try{
            FileReader file = new FileReader(str);
            BufferedReader input = new BufferedReader(file);
            FileWriter file2 = new FileWriter("data/article.md");
            BufferedWriter output = new BufferedWriter(file2);
            Workbook reader = null;
            try {
                File dictIn = new File("data/Dictionary.xls");
                reader = Workbook.getWorkbook(dictIn);
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
            output.flush();
            output.write("#");
            System.out.println(file2.toString());
            Character now = 0;
            boolean firstWord = true;
            String word = "";
            while ((now = (char)input.read()) != 65535){
                if (now > 127) continue;
                if (now >= 'A' && now <= 'Z' || now >='a' && now <= 'z'){
                    if (firstWord) {
                        word += toLowerCase(now);
                        firstWord = false;
                    }
                    else word += now;

                }
                else {
                    if (word.length() > 0 &&
                        !isUpperCase(word.charAt(0)) &&
                        !check(word)){
                        if (!newWords.find(word)) {
                            newWords.add(word);
                            output.write("[^"+ (newWords.findPos(word)) +"]");
                        }
                    }
                    word = "";
                }
                output.write(now);
                if (now == '\n') output.write(now);
                if (now == '.' || now == ':' || now == '!' || now == '?')
                    firstWord = true;
                //System.out.println((int) now);
            }

            for(String newWord : newWords.data){
                output.write("[^" + newWords.findPos(newWord) + "]: " + newWord + " "+ meaning(reader,newWord) + "\n");
            }

            input.close();
            output.close();
            file.close();
            file2.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void save(){
        unFamiliar.save();
        known.save();
    }

    static String meaning(Workbook reader,String now){
        try{
            Sheet list = reader.getSheet(0);
            int row = list.getRows();
            String[] sufList = {"","s","es","ing","ed"};
            for (String suffix : sufList) {
                String str = meaning(list, row, now, suffix);
                if (!str.equals("")) return str;
            }
            String str = meaning(list, row, now, "ing","e");
            if (!str.equals("")) return str;
            str = meaning(list, row, now, "ed","e");
            if (!str.equals("")) return str;
            str = meaning(list, row, now, "ied","y");
            if (!str.equals("")) return str;
            str = meaning(list, row, now, "ies","y");
            if (!str.equals("")) return str;
            return "words not found";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }


    static String meaning(Sheet list,int row,String word,String suf){
        int len = word.length();
        int len2 = suf.length();
        if (len <= len2) return "";
        if (!word.substring(len - len2).equals(suf)) return "";
        for (int i = 1; i < row; i++){
            if (list.getCell(1,i).getContents().equals(word.substring(0,len - len2)))
                return list.getCell(2,i).getContents();
        }
        return "";
    }

    static String meaning(Sheet list,int row,String word,String suf,String addition){
        int len = word.length();
        int len2 = suf.length();
        if (len <= len2) return "";
        if (!word.substring(len - len2).equals(suf)) return "";
        for (int i = 1; i < row; i++){
            if (list.getCell(1,i).getContents().equals(word.substring(0,len - len2) + addition))
                return list.getCell(2,i).getContents();
        }
        return "";
    }

    public static void main(String[] args){
        build();
        setUI();
    }
}
