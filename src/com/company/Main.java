package com.company;
import java.io.*;
import java.util.*;
import java.util.LinkedList;

public class Main {
    static String alphabet = "abcdefghijklmnopqrstuvwxyzé";
    static String path = "C:\\Users\\Matheus\\IdeaProjects\\Linked-Dictionary\\src\\com\\company\\unsorteddict.txt";
    static String path_sorted = "C:\\Users\\Matheus\\IdeaProjects\\Linked-Dictionary\\src\\com\\company\\sortedDictTest.txt";
    static LinkedList<LinkedList> dict = new LinkedList<>();
    static String[] toPrint = new String[99171];



    public static void main(String args[])
    {
                                                                                long startingTime = System.currentTimeMillis();
        createLinkedLists();
                                                                                long elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Time to create Linked Lists: " + elapsedTime + "ms");
        try {
                                                                                startingTime = System.currentTimeMillis();
            sortLinkedLists();
                                                                                elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Time to sort Linked List: " + elapsedTime + "ms");
                                                                                startingTime = System.currentTimeMillis();
           writeNewFile();
                                                                                elapsedTime = (System.currentTimeMillis() - startingTime);
                                                                                System.out.println("Filled new file in: " + elapsedTime+ "ms");

            talkToConsole();
            }

        catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public static int getPosition(String word, int indicator) { //1075ms;--- with list iterator = 180ms
            ListIterator list_Iter = dict.get(indicator).listIterator(0);
            int result =0;
            while(list_Iter.hasNext()){

            String word_2 = list_Iter.next().toString();
            if (word.compareTo(word_2) < 0){
                return result;
            }
            result++;
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

                String word = sc.nextLine().toLowerCase();
                int indicator = alphabet.indexOf(word.charAt(0));

                int position = getPosition(word, indicator);

                if(position != -1) {
                    dict.get(indicator).add(position, word);
                }
                else {
                    dict.get(indicator).add(word);
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

        public static void talkToConsole() throws FileNotFoundException {
            Scanner scan = new Scanner(System.in);
            boolean flag = true;
            boolean number = true;
            int argument;
            while (flag){
                String arg = scan.nextLine();

                try{
                    argument = Integer.parseInt(arg);
                    switch(argument) {
                        case -1:
                            checkIfCorrect();
                            break;

                        default:
                            if(argument<-1){
                                System.out.println("That number is too tiny");
                                break;
                            }else if (argument > toPrint.length){
                                System.out.println("Number too big");
                            }else {
                                System.out.println(toPrint[argument]);
                            }

                    }
                } catch (Exception e) {
                    switch(arg) {
                        case "test":
                            checkIfCorrect();
                            break;
                        case "":
                            System.out.println("This is not an input");
                            break;
                        default:
                            char add = arg.toLowerCase().charAt(0);
                            LinkedList list = dict.get(alphabet.indexOf(add));
                            if(list.contains(arg)){
                                System.out.println("This word is in the following position: " + getWord(arg));
                            }
                            else{
                                System.out.println("word doesn't exist, try again");
                            }
                    }
                }
            }
            scan.close();
        }

    private static void checkIfCorrect() throws FileNotFoundException {
        int errors = 0;
        int i =0;
        File sorted = new File(path_sorted);
        Scanner access = new Scanner(sorted);
        while(access.hasNextLine()){
            if(access.nextLine() !=toPrint[i]){
                errors++;
            }
            i++;
        }
        System.out.println("The sorted Arraylist is "+(errors/i*100)+"% accurate.");
        access.close();
    }

    private static int getWord(String arg) {
        for (int i = 0;i<toPrint.length;i++){
            if (arg.toLowerCase().equals(toPrint[i].toLowerCase())){
                return i;

            }
        }
        return -1;
    }
}