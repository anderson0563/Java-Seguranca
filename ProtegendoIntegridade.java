package org.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ProtegendoIntegridade {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public String doHash(String nomeArquivo)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String conteudoArquivo = new String(Files.readAllBytes(Paths.get(nomeArquivo)));
        md.update(conteudoArquivo.getBytes());
        byte[] digest = md.digest();
        return(bytesToHex(digest).toLowerCase());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        System.out.println((new ProtegendoIntegridade()).doHash("arquivoEntrada.txt"));
    }
}

