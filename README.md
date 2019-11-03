# Linked Dictionaries
Project by Matheus Paro and Yuval Cole.
## Objective
- [x] Create a program that takes in a file of words and sort them through Linked Lists.
- [x] Create a new file with the sorted list.
- [x] Take in arguments and return the words corresponding to argument.
- [x] Make it clean and faster.
- [ ] Get a good grade from the project. (waiting for Chelu)

### Main 
This is supposed to be done as soon as you read the word, it is identified where to be stored and put into the dict automatically.
We approached it with coming up with a linked list of linked lists with different letters of the alphabet so that it would faster the program, since it would only compare with words of the same first letter.
Therefore we create the linked lists, sort it, write the new file and talk to console.
All with showing the time it took to take each method.

```java
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
```
Now we have 27 different linked lists for each starter letter in the alphabet.

### File reader
In order to read the file we need to create an object File with the path of the file and a Scanner with the file as argument, after this using *scan.nextLine()* will return the word on the specific line.

```java
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
```

### Sorting 
Since we could *not* use the sorting algorithm for the array we came up with our own known as *getPosition()*, this goes through the word and checks the first letter. After knowing the letter we need to send it to the correct linked list through our int Indicator, which is in int value the representation of which letter it starts. After that we check within the linked list and compare to all words that are smaller than the real word. If thelist is empty it will return -1 and be added randomly.

```java
    public static int getPosition(String word, int indicator) {
    
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
```
### New File
To create a new file we will be using the *PrintWriter* class. This allows us to create a new file and iterate through the list adding one by one the sorted list into the new file. This is the method we came up with:

```java
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
```
We had to create a new array of Strings to substitute the list of lists or else it would be too slow to check for the correct word on the console requirement.

### Console and arguments
For the console to accept both number and words as arguments the following function was created:

```java
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
                        case ":q":
                            Runtime.getRuntime().exit(0);
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
        long startingTime = System.currentTimeMillis();
        File sorted = new File(path_sorted);
        Scanner access = new Scanner(sorted);
        while(access.hasNextLine()){
            if(access.nextLine() !=toPrint[i]){
                errors++;
            }
            i++;
        }
        long elapsedTime = (System.currentTimeMillis() - startingTime);
        System.out.println("Time to check the Linked List's accuracy: " + elapsedTime + "ms");
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
            
```
## Proof of concept

![proof](https://cdn.discordapp.com/attachments/364021000964276234/640590735422390323/unknown.png)

## Improvements
### One Linked List
At first we were working with a single linked list with 99 thousand values that would be checked before adding the last values since this was so slow that we didn't even end the program once.

### Indexing with letters
Secondly we managed to make the whole system faster by before the list starts to be populated we add all the letters from the alphabet so that it removes unecesary comparasions aout of the way. But the program was still slow at a whopping 38 mins to correctly sort all of the values.
We thought that it was due to the structure of how the nodes store their value and the fact that each value records every single value before itself in the linked list. Therefore we came up with a way of reducing that.

### Indexing with linked lists

This is the best method we were able to come up with which was to create a linked list to every single letter that a word starts with. therefore everytime a word is sorted it not only bypasses being compared to every other word that doenst start with the same letter but the size of the linked list becomes smaller allowing for faster storing of data.

### Adding the iterator

By simply creating an iterator to locate the correct position for new words the program works extremely faster and is able to do the same as before in only 5 seconds.


#### Results
Finally it was achieved with only 5 seconds.

| One Linked List                   | 60 mins+  |
|-----------------------------------|---|
| Linked list separated by alphabet | 38 mins |
| Linked list for each letter       | 26 mins |
| Linked list for each letter + iterator      | 5.5 seconds |
