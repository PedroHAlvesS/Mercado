package br.com.compass.site;

import java.time.LocalDate;

public class teste {
    public static void main(String[] args) {
        int anoAtual = LocalDate.now().getYear();
        String anoAtualString = String.valueOf(anoAtual);
        System.out.println(anoAtualString);
    }
}
