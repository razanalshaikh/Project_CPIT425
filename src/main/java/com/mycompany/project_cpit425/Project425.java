package com.mycompany.project_cpit425;

import java.util.Scanner;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Razan Alshaikh
 */

/* Section JAR/AAB
 * Razan Alshaikh #######
 * Ahad Fahad     #######
 * Ghala Almalki  #######
 * Ragad Buridi   #######
 * Rana Aljabir   #######
 */
public class Project425 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("################################");
        System.out.println("       Encryption System        ");
        System.out.println("################################");
        System.out.println(" Enter a message:      ");
        String plainText = input.nextLine();

        // obj of Bob as reciver
        Bob bob = new Bob();
        //obj of Alice as Sender 
        Alice alice = new Alice();
        // first we generate keys for bob to use pk of bob in Alice class to encrypt msg
        bob.generateKeys();
        //we use SecureRandom to generate a cryptographically secure random IV
        SecureRandom secureRandom = new SecureRandom();
        // generate Initial vector(IV), we will create array of 16 bytes beacuse AES supports key lengths of 128 bits
        // we need the IV in AES CBC mode encryption & decryption
        byte[] IV = new byte[16];
        secureRandom.nextBytes(IV);
        IvParameterSpec iv = new IvParameterSpec(IV);
        
        // encrypt the message & store to encrypted msg variable to use it in decryption method
        byte [] encrypted_msg = alice.encryptMessageAES(plainText, iv);
        // we need the encrypted key
         byte [] encryptedKeyBytes= alice.getEncryptedKeyByte();
        // we will pass 3 argument to decrypt msg which are:
        // Initial vector(IV) & the encrypted msg & the encrypted key 
        bob.decryptMsg(iv, encrypted_msg, encryptedKeyBytes);

    }

}
