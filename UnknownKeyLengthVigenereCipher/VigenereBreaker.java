import java.util.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slicedMessage = new StringBuilder();
        for(int i=whichSlice; i<message.length(); i+= totalSlices)
        {
            slicedMessage.append(message.charAt(i));
        }
        return slicedMessage.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for(int i=0; i<klength; i++)
        {
            String slice = sliceString(encrypted, i, klength);
            key[i] = cc.getKey(slice);
        }
        return key;
    }
    
    public HashSet<String> readDictionary(String dictionary){
        HashSet<String> hs = new HashSet<String>();
        for(String word : dictionary.split("\\W"))
        {
            if(! hs.contains(word.toLowerCase()))
            {
                hs.add(word.toLowerCase());
            }
        }
        return hs;        
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int count = 0;
        for(String word : message.split("\\W"))
        {
            if(dictionary.contains(word))
            {
                count++;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encMessage, HashSet<String> dictionary){
        int maxNumOfWords = 0;
        String message= "";
        for(int i = 1; i<encMessage.length(); i++)
        {
            int[] key = tryKeyLength(encMessage, i, 'e');
            VigenereCipher vc = new VigenereCipher(key);
            String decryptedMessage = vc.decrypt(encMessage);
            int numOfWords = countWords(decryptedMessage, dictionary);
            if(numOfWords > maxNumOfWords)
            {
                maxNumOfWords = numOfWords;
                message = decryptedMessage;
            }
        }
        return message;
    }
                
    public String readMessage(){
        String message = "";
        //write your message here
        return message;
    }
    
    public HashSet<String> readDictionary() {
        String dictionary = "";
        //write your dictionary here
        HashSet<String> dictionarySet = readDictionary(dictionary);
        return dictionarySet;
    }
    
    public void breakVigenere () {
        
        String encMessage = readMessage();
        HashSet<String> dictionary = readDictionary();
        String message = breakForLanguage(encMessage, dictionary);
        System.out.println(message);        
    }
    
}
