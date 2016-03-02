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

    public void breakVigenere () {
        
        String encMessage = readMessage();
        int keyLength = 5;
        int key[] = tryKeyLength(encMessage, keyLength, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        String message = vc.decrypt(encMessage);
        System.out.println(message);
    }
    
    public String readMessage(){
        String message = "";
        //write your message here
        return message;
    }
}
