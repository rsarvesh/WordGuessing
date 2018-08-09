import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordGuessing {
    public static void main(String ar[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        int size = 4;
        String score, guessWord;

        ArrayList<String> dict = getDictionary(size);
        System.out.println(dict);
//        dict = getAnagramDict(dict);
//        System.out.println(dict);

        while(true) {
            int idx = (int)(Math.random() * dict.size());
            System.out.println(dict.size() + "   " + idx);
            guessWord = dict.get(idx);
            System.out.println(dict);

            System.out.print("I guess: " + guessWord + "\nEnter score: ");
            score = sc.next();

            if(score.equals("win"))
                break;

            int numChars = Integer.parseInt(score);
            dict = updateDict(dict, guessWord, numChars);
            dict.remove(guessWord);
        }
        System.out.println("The word is: " + guessWord);
    }

    public static boolean containsDuplicateLetters(String s){
        for(int i=0;i<s.length();i++)
            for(int j=i+1;j<s.length();j++)
                if(s.charAt(i)==s.charAt(j))
                    return true;
        return false;
    }
    public static ArrayList<String> getDictionary(int length) throws IOException {
        File file = new File("./sowpods.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        ArrayList<String> words = new ArrayList<String>();
        while ((st = br.readLine()) != null)
        {
            if(st.length()==length)
            {
                if(!containsDuplicateLetters(st)){
                    words.add(st);
                }
            }
        }
        return words;
    }


    public static int calcScore(String actualWord, String checkWord) {
        int len = actualWord.length();
        int sc = 0;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                if(actualWord.charAt(i) == checkWord.charAt(j))
                    sc++;
        return sc;
    }

    public static ArrayList<String> updateDict(ArrayList<String> dict, String guessWord, int score) {
        ArrayList<String> newDict = new ArrayList<>();
        for(int i=0;i<dict.size();i++) {
            if(score == calcScore(guessWord, dict.get(i)))
                newDict.add(dict.get(i));
        }
        return newDict;
    }
}