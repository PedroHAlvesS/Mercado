package br.com.compass.site;

public class teste {
    public static void main(String[] args) {
//        String teste16 = "0000000000000000";
        String teste16 = "abcasd015";
        boolean valido = teste16.matches("[0-9]{16}");
        System.out.println(valido);
    }
}
