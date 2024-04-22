/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project_cpit425;

import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Razan Alshaikh
 */
/**
 * Section JAR/AAB Razan Alshaikh #######
 * Ahad Fahad ####### 
 * Ghala Almalki #######
 * Ragad Buridi #######
 * Rana Aljabir   #######
 */
public class Alice {

    Key key;
    byte[] EncryptedKeyBytes;
    byte[] EncryptedTextBytes;
    PublicKey pk;

    public byte[] encryptMessageAES(String plaintext, IvParameterSpec IV) throws Exception {
        // convert the msg to array of bytes
        byte[] plaintextBytes = plaintext.getBytes();
        EncryptKeyRSA();
        // create cipher obj using CBC mode AES to encrypt msg
        Cipher cipherText = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        // intiliaze encrypt 
        cipherText.init(Cipher.ENCRYPT_MODE, key, IV);
        //encrypted msg
        EncryptedTextBytes = cipherText.doFinal(plaintextBytes);
        String encryptedText = Base64.getEncoder().encodeToString(EncryptedTextBytes);
        System.out.println("the encrypted message: "+encryptedText);
        return EncryptedTextBytes;
    }

    public void EncryptKeyRSA() {
        try {
            // generate key for AES
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            key = kg.generateKey();
            // create obj RSA to encrypt the key
            Cipher cipher = Cipher.getInstance("RSA");
            // take public key from reciever
            PublicKey pk = Bob.getPublicKey();
            // encrypt key using public key of Bobs
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            //
            EncryptedKeyBytes = cipher.doFinal(key.getEncoded());
            String myEncryptedKeyBytes = Base64.getEncoder().encodeToString(EncryptedKeyBytes);
            System.out.println("the encrypted key: " + myEncryptedKeyBytes);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in Encrypt msg ");
        }
    }

    public byte[] getEncryptedKeyByte() {
        return EncryptedKeyBytes;
    }
}
