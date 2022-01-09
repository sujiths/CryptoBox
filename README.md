# CryptoBox
Android application to store passwords on phone.

This application stores keys in Android key store. Passwords are encrypted using this key and stored in the application database. Application do not provide any backup as of now. Hence data will be lost if phone is damaged or lost.
Encryption keys will be deleted if application is uninstalled which also results in data loss. Application uses only finger print for authentication. If finger print system or any other subsystem used by the app is compromised,
secrets can be recovered by malicious actors. More security measures will be added in future. 

Caution: Passwords cannot be recovered if phone is lost or damaged.
