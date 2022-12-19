package hangman;

import java.util.*;
import java.io.*;

class Game{
    Scanner reader = null;
    Random random = new Random();

    ArrayList<String> readFile(String fileName){
        ArrayList<String> contents = new ArrayList<String>();

        try{
            File file = new File(fileName);
            this.reader = new Scanner(file);

            while (this.reader.hasNextLine()){
                contents.add(this.reader.nextLine());
            }
        }catch(Exception ex){
            System.out.println("File not found!");
        }
        return contents;
    }

    String randomWord(ArrayList<String> words){
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    String randomLetter(String word){
        int index = random.nextInt(word.length());
        StringBuilder newWord = new StringBuilder();
       
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == word.charAt(index)){
                newWord.append("_");
            }else{
                newWord.append(word.charAt(i));
            }
        }

        System.out.println("Guess the word: " + newWord);
        return String.valueOf(word.charAt(index));
    }

    String userInput(){
        this.reader = new Scanner(System.in);
        System.out.print("Guess the missing letter: ");
        String guess = this.reader.nextLine();

        return guess;
    }

    public void showResponse(String answer, String word, String randLetter){
        System.out.println("The word was: " + word);

        if (answer.equalsIgnoreCase(randLetter)){
            System.out.println("Well done! You are awesome!");
        }else{
            System.out.println("Wrong! Do better next time!");
        }
       
    }


    public void runGame(String filename){
        ArrayList<String> words = this.readFile(filename);
        String word = this.randomWord(words);
        String randLetter = this.randomLetter(word);
        String answer = this.userInput();

        this.showResponse(answer, word, randLetter);
        this.reader.close();
    }

}

public class Hangman {
    private static Scanner reader;

    public static String getFileName(){
        reader = new Scanner(System.in);
        System.out.print("Words file? [leave empty to use short_words.txt] : ");

        String filename = reader.nextLine();

        if (filename == ""){
            return "short_words.txt";
        }else{
            return filename;
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        String fileName = getFileName();

        game.runGame(fileName);
    }
}
