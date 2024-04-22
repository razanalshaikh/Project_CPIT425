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
import javax.crypto.spec.SecretKeySpec;

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
public class Bob {

    private static PublicKey PK;
    private static PrivateKey PV;
    Key key;
    private byte[] msg;
    byte[] encryptedKeyBytes;

    public void generateKeys() throws Exception {
        try {
            /* Generate a RSA key pair */
            KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
            KeyPair myPair = g.generateKeyPair();
            // public & private key 
            // we will use PK in Alice class to encrypt the key
            PK = myPair.getPublic();
            PV = myPair.getPrivate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("error in generate key for Bob");
        }
    }

    public void decryptKey() {
        try {
            // RSA obj
            Cipher cipher = Cipher.getInstance("RSA");
            // init decrypt the key using private key of bobs
            cipher.init(Cipher.DECRYPT_MODE, PV);
            // decrypt the key 
            byte[] DecryptKey = cipher.doFinal(encryptedKeyBytes);
            String MyDecryptedKey = Base64.getEncoder().encodeToString(DecryptKey);
            System.out.println("the decrypted key: "+ MyDecryptedKey);
            // the decrypted symmetric key to use it in decrypting the msg
            key = new SecretKeySpec(DecryptKey, 0,DecryptKey.length,"AES");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("error in decrypt key");
        }

    }

    public void decryptMsg(IvParameterSpec IV, byte[] msg, byte[] encryptedKeyBytes) throws Exception {
        // first we decrypt the key
        this.encryptedKeyBytes = encryptedKeyBytes;
        this.msg = msg;
        decryptKey();
        // obj of AES to decrypt the msg
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        // to decrypt using AES CBC Mode we will use IV(Initial vector), KEY ( the decrypted symmetric key)
          cipher.init(Cipher.DECRYPT_MODE,key,IV );
          byte[] decryptedMsg = cipher.doFinal(msg);
          System.out.println("the decrypted msg: "+ new String(decryptedMsg));

    }

    public static PublicKey getPublicKey() {
        return PK;
    }

}
