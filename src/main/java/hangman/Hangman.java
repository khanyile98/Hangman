package hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Game{
    Scanner reader = null;
    Random random = new Random();
    private int attempts = 5; 

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

    char randomLetter(String word){
        int index = random.nextInt(word.length());
        return word.charAt(index);
    }

    String wordToGuess(String word, char c){
        StringBuilder newWord = new StringBuilder();
        
        for (int i=0; i<word.length(); i++){
            if (word.charAt(i) != c){
                newWord.append("_");
            }else{
                newWord.append(c);
            }
        }

        System.out.println("Guess the word: " + newWord);
        return String.valueOf(newWord);
    }

    String userInput(){
        this.reader = new Scanner(System.in);
        System.out.print("Guess the missing letter: ");
        String guess = this.reader.nextLine();

        return guess;
    }

    String drawHangman(){
        String[] hangmanSticks = {
        "/----\n|\n|\n|\n|\n_______",
        "/----\n|   0\n|\n|\n|\n_______",
        "/----\n|   0\n|   |\n|   |\n|\n_______",
        "/----\n|   0\n|  /|\\\n|   |\n|\n_______",
        "/----\n|   0\n|  /|\\\n|   |\n|  / \\\n_______"

        }; 

        return hangmanSticks[5 - attempts];
    }

    public String showResponse(String answer, String guess, String word, int attempts){

        if (word.contains(answer) && !guess.contains(answer)){

            for (int i=0; i < word.length(); i++){
                if (String.valueOf(word.charAt(i)).equals(answer)){
                    guess = guess.substring(0, i) + answer + guess.substring(i + 1, word.length());
                }
            }

            return guess;
        }
        else{
            System.out.println("Wrong! Attempts left: " + (attempts - 1));
            System.out.println(drawHangman());
            this.attempts--;
            return guess;
        }
    }


    public void runGame(String filename){
        ArrayList<String> words = this.readFile(filename);
        String word = this.randomWord(words);
        char randLetter = this.randomLetter(word);
        String guess = wordToGuess(word, randLetter);

        while (attempts > 0){
            String answer = this.userInput().toLowerCase();

            if (answer.equals("exit")){
                System.out.println("Thank you for playing. Hope to see you again.");
                return;
            }

            guess = showResponse(answer, guess, word, attempts);

            if (guess.equals(word)){
                System.out.println("Wonderful! You won.");
                return;
            }
            System.out.println(guess);
        }

        System.out.println("Sorry, you are out of guesses. The word was: " + word);
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
