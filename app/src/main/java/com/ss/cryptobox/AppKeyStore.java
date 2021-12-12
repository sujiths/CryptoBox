package com.ss.cryptobox;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyStore;


import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AppKeyStore {

    private String TAG = "AppKeyStore";

    public AppKeyStore() {
        if (!IsInitialised())
            initialise();
    }

    public boolean IsInitialised() {
        try {
            Log.i(TAG, "Check if keystore is initialised");

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.Entry entry = keyStore.getEntry(Constants.AES_KEY_ALIAS, null);
            if (entry != null) {
                Log.i(TAG, "Keystore is already initialised");
                return true;
            }
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException
                | UnrecoverableEntryException e) {
            Log.e(TAG, "Caught Exception " + e.getMessage());
            e.printStackTrace();
        }
        Log.e(TAG, "keystore is not yet initialised");
        return false;
    }

    private boolean initialise() {
        Log.i(TAG, "Initialising keystore");

        KeyGenerator AESKeyGenerator = null;
        try {
            AESKeyGenerator = AESKeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            KeyGenParameterSpec AESAlgoParams = new KeyGenParameterSpec.Builder(Constants.AES_KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build();
            AESKeyGenerator.init(AESAlgoParams);

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
                e) {
            Log.e(TAG, "Caught Exception in keystore initialisation " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        AESKeyGenerator.generateKey();

        return true;
    }

    public Pair<byte[], byte[]> Encrypt(String clearText) {
        Pair<byte[], byte[]> retVal = null;
        KeyStore.SecretKeyEntry secretKeyEntry = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(Constants.AES_KEY_ALIAS, null);
            SecretKey aesKey = secretKeyEntry.getSecretKey();
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            // we need to store the IV also
            byte[] iv = cipher.getIV();
            byte[] cipherText = cipher.doFinal(clearText.getBytes());
            retVal = new Pair<>(iv, cipherText);

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException | CertificateException
                | IOException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public String Decrypt(Pair<byte[], byte[]> cipherText) {
        String clearText = null;
        KeyStore.SecretKeyEntry secretKeyEntry = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(Constants.AES_KEY_ALIAS, null);
            SecretKey aesKey = secretKeyEntry.getSecretKey();
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            final GCMParameterSpec spec = new GCMParameterSpec(128, cipherText.first); // IV
            cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);
            final byte[] decodedData = cipher.doFinal(cipherText.second);
            clearText = new String(decodedData);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException | CertificateException | IOException |
                NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e ) {
            e.printStackTrace();
        }
        return clearText;

    }

}
