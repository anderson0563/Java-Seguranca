package org.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ProtegendoConfidencialidade {
    SecretKey chave;

    public ProtegendoConfidencialidade() throws NoSuchAlgorithmException {
        chave = KeyGenerator.getInstance("DES").generateKey();
    }
    public void Encriptar(String arquivoCripo, String arquivoClaro) throws Exception
    {
        Main cripto = new Main(chave);
        String conteudoArquivoClaro = new String(Files.readAllBytes(Paths.get(arquivoClaro)));
        String conteudoArquivoEncriptado = cripto.encrypt(conteudoArquivoClaro);
        Files.writeString(Path.of("senha.txt"), Base64.getEncoder().encodeToString(chave.getEncoded()));
        Files.writeString(Path.of(arquivoCripo), conteudoArquivoEncriptado);
    }


    public void Decriptar(String novoArquivoClaro, String arquivoCripto) throws Exception {

        String chaveString = new String(Files.readAllBytes(Paths.get("senha.txt")));
        System.out.println(chaveString);
        byte[] data = Base64.getDecoder().decode(chaveString.getBytes());
        SecretKey chave = new SecretKeySpec(data, 0, data.length, "DES");

        Main cripto = new Main(chave);
        String conteudoArquivoCripto = new String(Files.readAllBytes(Paths.get(arquivoCripto)));
        String conteudoArquivoClaro = cripto.decrypt(conteudoArquivoCripto);
        Files.writeString(Path.of(novoArquivoClaro), conteudoArquivoClaro);
    }

    public static void main(String[] args) throws Exception {
        ProtegendoConfidencialidade proteger = new ProtegendoConfidencialidade();

        proteger.Encriptar("arquivoEncriptado.txt", "arquivoEntrada.txt");
        proteger.Decriptar("arquivoDecriptado.txt", "arquivoEncriptado.txt");
    }
}
