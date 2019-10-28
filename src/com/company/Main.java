package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String alphabet = "abcdefghijklmnopqrstuvwxyzé";
    static String path = "/Users/Matheus/Linked/src/com/company/unsortedDictTest.txt";
    static LinkedList<LinkedList> dict = new LinkedList<>();
    static String[] toPrint = new String[99171];



    public static void main(String args[])
    {
                                                                                long startingTime = System.currentTimeMillis();
        createLinkedLists();
                                                                                long elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Time to create Linked Lists: " + elapsedTime);
        try {
                                                                                startingTime = System.currentTimeMillis();
            sortLinkedLists();
                                                                                elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Time to sort Linked List: " + elapsedTime);
                                                                                startingTime = System.currentTimeMillis();
           writeNewFile();
                                                                                elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Filled new file in: " + elapsedTime);

            talkToConsole();
            }

        catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public static int getPosition(String word, int indicator) {
        String temp = Character.toString(word.charAt(0));
        int index = dict.get(indicator).indexOf(temp);

        if (index < 0){
            index = 0;}

        for(int j = index; j < dict.get(indicator).size(); j++) {
            String word_2 = (String) dict.get(indicator).get(j);
            if (word.compareTo(word_2) < 0){
                return j;
            }

        }
        return -1;
    }

        public static void createLinkedLists(){
            for ( char letter : alphabet.toCharArray()){
                dict.add(new LinkedList<String>());
            }
        }


        public static void sortLinkedLists() throws FileNotFoundException {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {

                String i = sc.nextLine().toLowerCase();

                int indicator = alphabet.indexOf(i.charAt(0));

                int position = getPosition(i, indicator);
                if(position != -1) {
                    dict.get(indicator).add(position, i);
                }
                else {
                    dict.get(indicator).add(i);
                }
            }
            sc.close();
        }


        public static void writeNewFile() throws FileNotFoundException, UnsupportedEncodingException {
            PrintWriter writer = new PrintWriter("sorteddict.txt","UTF-8");
            int i =0;
            for(LinkedList<String> element : dict){
                for(String word: element){
                    toPrint[i] = word;
                    i++;
                    writer.println(word);

                }
            }
            writer.close();
        }

        public static void talkToConsole(){
            Scanner scan = new Scanner(System.in);
            for (int j = 0;j<10;j++) {
                int x = scan.nextInt();
                if (toPrint[x]== null){
                }else {
                    System.out.println(toPrint[x]);

                }
            }
            scan.close();
        }
}