/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaFileCompress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * 
 */
public class JavaFileCompress extends Application {
    //load GUI
    public static Window mainStage;    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/JavaFileCompress/FXMLDocument.fxml"));        
        Scene scene = new Scene(root);
        stage.setTitle("Compress File");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
        
    
    //create class for Word
    private static class Word
    {
        public String plainWord;
        public double probability;
        public String codeWord;
    }
    
    public static boolean compress(File inputFile, final File outputFile){
        ArrayList<Character> characterList = new ArrayList() {};
        ArrayList<Double> characterCount = new ArrayList();
        double totalCharacters = 0;
        BufferedReader inputReader = null;
        String inputString = "";
        try 
        {
            inputReader = new BufferedReader(new FileReader(inputFile));
            inputString = inputReader.readLine();
        //catch any exceptions from opening file
        }catch (Exception e){e.printStackTrace(System.err);}
        
        while(inputString!= null)
        {   //add carriage return back
            inputString += System.lineSeparator();
            //handle any 'empty' chars, found issues with this between encodings
            inputString = inputString.replace("\0", "");
            for(int i=0; i < inputString.length(); i++)
            {
                Character currentChar = inputString.charAt(i);
                totalCharacters++;
                if(!(characterList.contains(currentChar)))
                {
                    characterList.add(currentChar);
                    characterCount.add(1.0);
                }
                else
                {   //increment the counter for the currentChar
                    Double newCount = characterCount.get(characterList.indexOf(currentChar));
                    newCount++;
                    characterCount.set(characterList.indexOf(currentChar), newCount);
                }
            }//end for(i=0;i<inputString.length)
            //get the next line
            try {
                inputString = inputReader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace(System.err);
            }
        }//end while(inputString!=null)
        //close the inputReader stream
        try {
            inputReader.close();
        } catch (IOException ex) {
            Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
        }
        
        ArrayList<Word> wordList = new ArrayList();
        Word newWord = new Word();
        newWord.plainWord = characterList.get(0).toString();
        newWord.probability = characterCount.get(0) / totalCharacters;
        wordList.add(newWord);
        for(int i = 1; i < characterList.size(); i++)
        {
            newWord = new Word();
            newWord.plainWord = characterList.get(i).toString();
            newWord.probability = characterCount.get(i) / totalCharacters;
            wordList.add(newWord);
        }
        Word[] theWords = new Word[wordList.size()];
        wordList.toArray(theWords);
        //form the codeword tree
        huffman(theWords);      
        
        //get the output file
        OutputStream outputStream = null;
        try{       
            //open output stream and write total pairs to follow
            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            outputStream.write(new byte[] {(byte)theWords.length});            
        }
         catch (IOException ex) {
            Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
        }
        byte writeByte = 0;
        int counter = 0;
        for (Word theWord : theWords) {
            try {
                //write code word
                char plainChar = theWord.plainWord.charAt(0);
                outputStream.write((byte)plainChar);            
                //write code word length
                outputStream.write((byte)theWord.codeWord.length());
            } catch (IOException ex) {
                Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
            }
            //write the code word
            for (int j = 0; j < theWord.codeWord.length(); j++) {
                writeByte *= 2;
                counter++;
                writeByte += (byte)(theWord.codeWord.charAt(j) - '0');
                if(counter ==8)
                {
                    try {
                        outputStream.write(writeByte);
                    } catch (IOException ex) {
                        Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    counter = 0;
                    writeByte = 0;
                }
            }
            //pad the (end) of the last byte
            while (counter < 8)
            {
                writeByte *= 2;
                counter++;
            }
            try {
                outputStream.write(writeByte);
            } catch (IOException ex) {
                Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
            }
            writeByte = 0;
            counter = 0;
        }

        //print the document to binary file
        inputString = "";
        try 
        {
            inputReader = new BufferedReader(new FileReader(inputFile));
            inputString = inputReader.readLine();
        //catch any exceptions from opening file
        }catch (Exception ex){ex.printStackTrace(System.err);}
        //while we have a string to read
        while(inputString != null)
        {	//for every character in inputString
                inputString += System.lineSeparator();
                for(int i=0; i<inputString.length(); i++)
                {	//find the correct Word object
                    for (Word theWord : theWords) {
                        //once we find the correct Word object
                        if (inputString.charAt(i) == theWord.plainWord.charAt(0)) {
                            //print the code word of the Word object
                            for (int w = 0; w < theWord.codeWord.length(); w++) {
                                //shift our byte
                                writeByte *= 2;
                                counter++;
                                //add our value
                                writeByte += (byte) (theWord.codeWord.charAt(w) - '0');
                                if (counter == 8)
                                {	try {
                                    //write the byte once we hit 8 bits;
                                    outputStream.write(writeByte);
                                } catch (IOException ex) {
                                    Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                counter = 0;
                                writeByte = 0;
                                }
                            }
                        }
                    }
                }
            try {
                inputString = inputReader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace(System.err);
            }
        }
        //print the last byte - pad right to make full byte
        while (counter < 8)
        {
                writeByte *= 2;
                counter++;
        }
        try {
            outputStream.write(writeByte);
            outputStream.flush();
            outputStream.close();
            inputReader.close();
        }
        catch (IOException ex) {
        Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//end compress()
    

    public static boolean decompress(File inputFile, File outputFile)
    {        
        try {
            if(!(outputFile.createNewFile()))
            {
                outputFile.setWritable(true);
            }
            Writer outputStream = new BufferedWriter(new FileWriter(outputFile));            
            InputStream inputStream = null;
            inputStream = new BufferedInputStream(new FileInputStream(inputFile));            
            ArrayList<Word> theWords = new ArrayList();            
            //inputString used later for reading inputFile
            String inputString = "";
            int numOfPairs = inputStream.read();
            String plainWordString="";
            String binaryString = "";
            String codeString = "";
            //while there are pairs still in the file...
            while (numOfPairs > 0)
            {   //get the plain char from file
                plainWordString = new String(new char[] {(char)inputStream.read()});
                int codeWordLength = 0;
                //get the 'codeWord' length from file
                codeWordLength = inputStream.read();
                //find amount of bytes to read for codeword
                int bytesToRead = codeWordLength / 8;
                //round up
                bytesToRead++;
                while (bytesToRead > 0)
                {   //get a string from file    
                    String buf = Integer.toBinaryString((int)inputStream.read());
                    if (buf.length() < 8)
                    {   //add any leading zeros
                        buf = String.format("%8s", buf).replace(' ', '0');
                    }
                    //add new string to 'binaryString'
                    binaryString += buf;
                    bytesToRead--;
                }
                //get 'codeString' from 'binaryString'
                codeString = binaryString.substring(0, codeWordLength);
                //create and add our new Word to theWord List
                Word newWord = new Word();
                newWord.plainWord = plainWordString;
                newWord.codeWord = codeString;
                theWords.add(newWord);
                numOfPairs--;
                binaryString = "";
            }
           
            //begin reading compressed file.            
            inputString = "";
            String buffer = "";
            boolean matched;
            while(true)
            {	//need to 'try' this to catch unexpected EOF/ end of stream exceptions
                matched = false;
                try
                {	//get a byte
                    int nextByte = (int)inputStream.read();
                    if(nextByte == -1){
                        break;
                    }
                    buffer = Integer.toBinaryString(nextByte);
                }
                catch (Exception e)
                {	//CATCH EOF
                    e.printStackTrace(System.err);
                }
                //'normalize' our byte, we need leading zeros back for the code words
                if (buffer.length() < 8)
                {
                    buffer = String.format("%8s",buffer).replace(' ', '0');
                }
                //add to inputString
                inputString += buffer;
                //setup string for matching the codewords
                String matcherString = "";
                //set to -1 for looping
                int matchPointer = -1;
                //while !matched AND our inputString is longer than the min codeword length...
                while (!matched)
                {
                    matchPointer++;
                    try
                    {	//we may 'run out' of characters in the inputString, catch this exception.....
                        matcherString += inputString.charAt(matchPointer);
                    }	//we simply break and get more characters for the 'inputString'
                    catch (Exception e) { break; }
                    //match the read strings to the dictionary string
                    for (Word theWord : theWords) {
                        if (matcherString.equals(theWord.codeWord)) {
                            //write the found character
                            outputStream.append((char)theWord.plainWord.charAt(0));
                            //remove this from the 'inpurString'
                            inputString = inputString.substring(theWord.codeWord.length());
                            //reset for looping
                            matchPointer = -1;
                            matcherString = "";
                            break;
                        }
                    }
                    if(inputString.length() == 0)
                    {
                        matched = true;
                    }
                }//end while (!matched && inputString.Length >= minCodeWordLength)
            }//end while(!matched)
            //close our streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception ex) {
            Logger.getLogger(JavaFileCompress.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
        }
        return true;
    }
    
    
    private static void huffman(Word[] theWords)
    {
            ArrayList<RootedBinaryTree<Word>> treeArray = new ArrayList();
            //make our initial list of binary trees
            for (Word theWord : theWords) {
                treeArray.add(new RootedBinaryTree(theWord));
            }
            //combine trees
            while (treeArray.size() > 1)
            {
                    Word newWord = new Word();
                    newWord.plainWord = null;
                    newWord.codeWord = null;
                    //combine the probabilities
                    RootedBinaryTree<Word> lowestProb1 = treeArray.get(0);
                    RootedBinaryTree<Word> lowestProb2 = treeArray.get(1);
                    for(RootedBinaryTree<Word> currentTree : treeArray)
                    {
                        if((currentTree.getData().probability < lowestProb1.getData().probability)
                                && (currentTree != lowestProb2))
                        {
                           lowestProb1 = currentTree;
                        }
                        if((currentTree.getData().probability < lowestProb2.getData().probability)
                                && (currentTree != lowestProb1))
                        {
                            lowestProb2 = currentTree;
                        }
                    }                    
                    
                    newWord.probability = lowestProb1.getData().probability + lowestProb2.getData().probability;
                    //make a new tree for the combined trees.
                    RootedBinaryTree<Word> combinedTrees = new RootedBinaryTree(newWord);
                    combinedTrees.combineTrees(lowestProb1, lowestProb2);
                    //make sure we are at root
                    combinedTrees.toRoot();
                    //insert tree
                    treeArray.add(combinedTrees);
                    treeArray.remove(treeArray.indexOf(lowestProb1));
                    if(treeArray.size()>1)
                    {
                        treeArray.remove(treeArray.indexOf(lowestProb2));
                    }
            }//end while( >1)
            treeArray.get(0).toRoot();
            RootedBinaryTree<Word> combinedTree = treeArray.get(0);
            String codeString = "";
            assignCodes( codeString, theWords, combinedTree);
    }//end huffman()

    private static void assignCodes( String codeString, Word[] theWords, RootedBinaryTree<Word> combinedTree)
    {	//if we can go left...
        if (combinedTree.moveLeft())
        {   //adjust codeword string accordingly...
            codeString += '0';
            if (combinedTree.getData().plainWord != null)
            {	//if we have a character here....
                for (Word theWord : theWords) {
                    //find the character in our 'theWords' array...
                    if (combinedTree.getData().plainWord.equals(theWord.plainWord)) {
                        //label this character with a codeword string
                        theWord.codeWord = codeString;
                        break;
                    }
                }
            }
            //recusive call
            assignCodes( codeString, theWords, combinedTree);
            //back from recursion, remove a character from our codeword string
            codeString = codeString.substring(0, codeString.length() - 1);
        }//end if(combinedTree.moveLeft())
        //do the same for right sides....
        if (combinedTree.moveRight())
        {
                codeString += '1';
                if (combinedTree.getData().plainWord != null)
                {
                    for (Word theWord : theWords) {
                        if (combinedTree.getData().plainWord.equals(theWord.plainWord)) {
                            theWord.codeWord = codeString;
                            break;
                        }
                    }
                }
                assignCodes( codeString, theWords, combinedTree);
                codeString = codeString.substring(0, codeString.length()-1);
        }
        //explored all possibilities, move back up....
        combinedTree.moveUp();
    }
	
    
}
