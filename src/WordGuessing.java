import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordGuessing {
    static Scanner sc = new Scanner(System.in);

    ArrayList<String> currentDict;
    int size;
    String otherPlayerWord;

    public WordGuessing(int size) {
        this.size = size;
        this.currentDict = new ArrayList<String>();
        this.populateDictionary(this.size);
        int idx = (int)(Math.random() * this.currentDict.size());
        this.otherPlayerWord= this.currentDict.get(idx);
    }

    private void populateDictionary(int length) {
        File file = new File("./sowpods.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null)
                if(st.length()==length && !containsDuplicateLetters(st))
                    this.currentDict.add(st);
        }
        catch(IOException io) {
            io.printStackTrace();
        }
    }

    private boolean containsDuplicateLetters(String s){
        for(int i=0;i<s.length();i++)
            for(int j=i+1;j<s.length();j++)
                if(s.charAt(i)==s.charAt(j))
                    return true;
        return false;
    }

    public void playGame() {
        boolean isGameOver;
        while(true) {
            isGameOver = this.computerTurn();
            if(isGameOver)
                break;

            isGameOver = this.playerTurn();
            if(isGameOver)
                break;
        }
    }

    private boolean computerTurn() {
        int idx = (int)(Math.random() * this.currentDict.size());
        String guessWord = this.currentDict.get(idx);

        System.out.print("\nI guess: " + guessWord + "\nEnter score ('win' if correct): ");
        String score = sc.next();

        if(score.equals("win")) {
            System.out.println("I win!! Your word is " + guessWord);
            return true;
        }

        int numChars = Integer.parseInt(score);
        this.updateDict(guessWord, numChars);
        this.currentDict.remove(guessWord);
        return false;
    }

    private boolean playerTurn() {
        System.out.print("\nEnter your guess: ");
        String playerGuess = sc.next();
        playerGuess = playerGuess.toUpperCase();
        if(playerGuess.equals(this.otherPlayerWord)) {
            System.out.println("Congrats!! You win");
            System.out.println("The word is " + this.otherPlayerWord);
            return true;
        }
        int playerScore = this.calcScore(this.otherPlayerWord, playerGuess);
        System.out.println("Your score is: " + playerScore);
        return false;
    }

    private int calcScore(String actualWord, String checkWord) {
        int len = actualWord.length();
        int sc = 0;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                if(actualWord.charAt(i) == checkWord.charAt(j))
                    sc++;
        return sc;
    }

    private void updateDict(String guessWord, int score) {
        for(int i=0;i<this.currentDict.size();i++) {
            if(score != calcScore(guessWord, this.currentDict.get(i)))
                this.currentDict.remove(i--);
        }
    }

    public static void main(String ar[]) {
        System.out.print("Enter length of word (4, 5 or 6): ");
        int difficulty = sc.nextInt();
        WordGuessing wg = new WordGuessing(difficulty);
        wg.playGame();
    }
}