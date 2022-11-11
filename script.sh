#gen keystore for server
keytool -genkeypair -alias gw.com -storetype pkcs12 -keystore server.p12 -keyalg rsa -keysize 2048

# gen keystore for client
keytool -genkeypair -alias client -storetype pkcs12 -keystore client.p12 -keyalg rsa -keysize 2048

# client-cert
keytool -exportcert -keystore client.p12 -alias client -file client.cer
keytool -exportcert -keystore client.p12 -alias client -file client.pem -rfc

# client-key
openssl pkcs12 -info -nodes -nocerts -in client.p12 > client.key


# make truststore for server
keytool -importcert -alias client -trustcacerts -keystore truststore.jks -file client.cer

