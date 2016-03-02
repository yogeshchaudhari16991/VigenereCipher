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
        char mostCommChar=mostCommonCharIn(dictionary);
        for(int i = 1; i<encMessage.length(); i++)
        {
            int[] key = tryKeyLength(encMessage, i, mostCommChar);
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
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> count = new HashMap<Character, Integer>();
        char maxChar = '0';
        for(String word : dictionary)
        {
            word = word.toLowerCase();
            for(char ch : word.toCharArray())
            {
                if(count.containsKey(ch))
                {
                    int charCount = count.get(ch);
                    count.put(ch, ++charCount);
                }
                else
                {
                    count.put(ch, 1);
                }
            }
        }
        int max = 0;
        for(char ch : count.keySet())
        {
            if(count.get(ch) > max)
            {
                max = count.get(ch);
                maxChar = ch;
            }
        }
        return maxChar;
    }
    
    public void breakForAllLanguages(String encMessage, HashMap<String, HashSet<String>> languageMap){
        int maxNumOfWords = 0;
        String messageLanguage = "", message = "";
        for(String languageName : languageMap.keySet())
        {
            HashSet<String> dictionary = languageMap.get(languageName);
            String decryptedMessage = breakForLanguage(encMessage, dictionary);
            int numOfWords = countWords(decryptedMessage, dictionary);
            if(numOfWords > maxNumOfWords)
            {
                maxNumOfWords = numOfWords;
                messageLanguage = languageName;
                message = decryptedMessage;
            }
        }
        System.out.println("Message Language: " + messageLanguage + ",\nMessage: " + message);
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
        HashMap<String, HashSet<String>> languageMap = new HashMap<String, HashSet<String>>();
        //Number of dictionaries'
        languageMap.put("English", readDictionary());
        languageMap.put("Spanish", readDictionary());
        breakForAllLanguages(encMessage, languageMap);
    }
    
}
