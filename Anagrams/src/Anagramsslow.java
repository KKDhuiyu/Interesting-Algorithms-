
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Huiyu Jia. Jason Zhao.
 */
public class Anagramsslow {

    private static ArrayList<String> combination = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<Integer, ArrayList<String>> dictionary;
long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);
        int maxWords;
        int inputStringLength;
        String sortedInputString;
        String input = args[0];
        String s1 = args[0].toLowerCase().replaceAll("[^a-z]", "");
        inputStringLength = s1.length();
        maxWords = Integer.valueOf(args[1]);
        sortedInputString = sortCharInString(s1);
        dictionary = new HashMap<>();

        while (sc.hasNextLine()) { // read input and store valid word in map
            
            String word = sc.nextLine().toLowerCase().replaceAll("[^a-z]", "");
            boolean flag = true; // the word has no char not in inputString
            for (int i = 0; i < word.length(); i++) {
                if (sortedInputString.indexOf(word.charAt(i)) == -1) {
                    flag = false;
                }
               
            }
             if(cannotBeAnagrams(s1,word)){
                 flag=false;
             }
            int len = word.length();
            if (flag) {
                if (len != 0 && len <= inputStringLength) {
                    //length of the word shorter than inputStringLength 
                    //nor empty
                    if (dictionary.containsKey(len)) {
                        if (!dictionary.get(len).contains(word)) {
                            dictionary.get(len).add(word);
                        }
                    } else {
                        dictionary.put(len, new ArrayList<>());
                        dictionary.get(len).add(word);
                    }
                }
            }
        }

        for (int i = 1; i <= inputStringLength; i++) { //sort list in map          
            if (dictionary.containsKey(i)) {
                ArrayList<String> temp2 = dictionary.get(i);
                Collections.sort(temp2);
                dictionary.put(i, temp2);
                 //fast
            }
        }
       long endTime = System.currentTimeMillis();
        //this one is slow.
        for (int i = inputStringLength; i > 0; i--) { //for each word in dic         
            if (dictionary.containsKey(i)&& i>=inputStringLength/maxWords) {
                ArrayList<String> temp2 = dictionary.get(i);
                for (String s : temp2) {
                    String word = s;
                    findAllCombination(maxWords - 1, 
                            inputStringLength, dictionary, word, input);
                }
            }
        }
 
long endTime2 = System.currentTimeMillis();

        ArrayList<String> result2 = combination;
       
        boolean inOrder= sortResult(result2,maxWords);
        while(!inOrder){//sort 
            inOrder= sortResult(result2,maxWords);
        }
        int c=0;
        for (int i = 1; i <=maxWords; i++) {//print in order
            for (String ss : result2) {
                if (ss.split(" ").length == i) {
                    System.out.println(ss);
                    c++;
                }
            }
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println((endTime-startTime)+"     "+(endTime2-endTime)+"      "+
                (endTime3-endTime2));

    }
    /**
     * sort the result array
     * @param result
     * @param maxwords
     * @return if its in order
     */
    public static boolean sortResult(ArrayList<String> result, int maxwords){
        boolean flag=true;
         ArrayList<String[]> resultArray = new ArrayList<>();
         for(String s : result) {
            resultArray.add(s.split(" "));
        }
        for (int i = 1; i <= maxwords; i++) {
            for (int j = 0; j < resultArray.size() - 1; j++) {
                if (resultArray.get(j).length - 1 > i &&resultArray.get(j+1).length - 1 > i ) {
                    if (resultArray.get(j)[i - 1].length()
                            == resultArray.get(j + 1)[i - 1].length()
                            && resultArray.get(j)[i].length()
                            < resultArray.get(j + 1)[i].length()) {
                        String[] temp = resultArray.get(j);
                        resultArray.set(j, resultArray.get(j + 1));
                        resultArray.set(j + 1, temp);//swap;
                        String tempS = result.get(j);
                        result.set(j, result.get(j + 1));
                        result.set(j + 1, tempS);
                        flag = false;
                    }
                }else if(resultArray.get(j+1).length<resultArray.get(j).length){
                     String[] temp = resultArray.get(j);
                        resultArray.set(j, resultArray.get(j + 1));
                        resultArray.set(j + 1, temp);//swap;
                        String tempS = result.get(j);
                        result.set(j, result.get(j + 1));
                        result.set(j + 1, tempS);
                        flag = false;
                }
            }
        }
        return flag;
    }
    
   
/**
 * return true if a equals b. 
 * @param a
 * @param b
 * @return 
 */
    public static boolean compareArrays(String[] a, String[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            int x = Arrays.asList(b).indexOf(a[i]);
            if (x == -1) {
                return false;
            }
        }
        return true;
    }

    public static String sortCharInString(String s) {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public static boolean isAnagrams(String input, String wordsToTry) {
        input = input.replaceAll("[^a-z]", "");
        wordsToTry = wordsToTry.replaceAll("[^a-z]", "");
        // init inputMap
        input= sortCharInString(input);
         wordsToTry =sortCharInString(wordsToTry);
        return input.equals(wordsToTry);
    }

    public static boolean cannotBeAnagrams(String input,String word){
         input = input.replaceAll("[^a-z]", "");
        word = word.replaceAll("[^a-z]", "");
        char[] inputArray = input.toCharArray();
        char[] wordArray = word.toCharArray();
        for(int i=0;i<wordArray.length;i++){
            for(int j=0;j<inputArray.length;j++){
                if(wordArray[i]==inputArray[j]){
                    wordArray[i]=0;
                    inputArray[j]=1;
                }
            }
        }
        for(char c: wordArray){
            if(c!=0){
                return true;
            }
        }
        return false;
    }

    public static void findAllCombination(int maxWords,
            int len, HashMap<Integer, ArrayList<String>> map,
            String startWords, String input) {
        int startWordsLength = startWords.replaceAll("[^a-z]", "").length();
        if (startWordsLength == len) {
            if (isAnagrams(input, startWords)) {
                
                for(String s:combination){
                    if(compareArrays(startWords.split(" "),s.split(" "))){
                        return;
                    }
                }
                 combination.add(startWords);
                return;
            }
           

        }
        if(cannotBeAnagrams(input,startWords)){
            return;
        }
        if (startWordsLength >= len) {
            return;
        }

        if (maxWords == 0) {
            return;
        }
        int lastWordLength
                = startWords.split(" ")[startWords.split(" ").length - 1].
                        length();
        int wordsLeft = len-startWordsLength;
        int lookupIndex=Math.min(lastWordLength, wordsLeft);
        for (int i = lookupIndex; i > wordsLeft/maxWords-1; i--) {
            if (map.containsKey(i)) {
                ArrayList<String> temp2 = (ArrayList<String>) map.get(i);
                for (String s : temp2) {
                    
                    String words = startWords + " " + s; 
                    
                    if(!cannotBeAnagrams(input,words)){
                    findAllCombination(maxWords - 1, len, map, words, input);
                    }
               
                }
            }
        }

    }

}
