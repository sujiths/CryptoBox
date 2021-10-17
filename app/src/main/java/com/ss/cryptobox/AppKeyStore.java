package com.ss.cryptobox;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import 	java.security.KeyStore;


import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class AppKeyStore {

    private String TAG="AppKeyStore";

    public AppKeyStore()
    {
        if(!IsInitialised())
            initialise();
    }

    public boolean IsInitialised() {
        try {
            Log.i(TAG, "Check if keystore is initialised");

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.Entry entry = keyStore.getEntry(Constants.EC_KEY_ALIAS, null);
            if(entry != null) {
                Log.i(TAG, "Keystore is already initialised");
                return true;
            }
        } catch (IOException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation check " + e.getMessage());
            e.printStackTrace();
        } catch (CertificateException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation check " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation check " + e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation check " + e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation check " + e.getMessage());
            e.printStackTrace();
        }
        Log.e(TAG, "keystore is not yet initialised");
        return false;
    }
    private boolean initialise()
    {
        Log.i(TAG, "Initialising keystore");

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(
                    Constants.EC_KEY_ALIAS,
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
                    .build());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Caught Exception in keystore initialisation " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return true;
    }

    public String Encrypt(String clearText) {
        return clearText;
    }

    public String Decrypt(String cipherText) {
        return cipherText;
    }

}
