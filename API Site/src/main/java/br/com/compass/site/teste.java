package br.com.compass.site;

import java.math.BigInteger;
import java.util.UUID;

public class teste {
    public static void main(String[] args) {
        boolean negativo = false;

        for (int i = 0; i < 500000; i++) {


            String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
            System.out.println("-----");
            System.out.println(lUUID);
            System.out.println("-----");
            if (lUUID.charAt(0) == '-') {
                negativo = true;
            }

        }
        System.out.println(negativo);

    }
}
