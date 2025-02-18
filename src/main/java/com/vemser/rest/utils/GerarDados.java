package com.vemser.rest.utils;

import net.datafaker.Faker;

import java.util.Locale;

public class GerarDados {
    public static Faker faker = new Faker(new Locale("pt", "BR"));

    public static String gerarEmail(){
      return faker.internet().emailAddress();
    }
    public static String gerarNome(){
        return faker.name().fullName();
    }
    public static int gerarInt(){
        return faker.number().numberBetween(1, 100);
    }
    public static int gerarIntComParametro(int numero){
        return faker.number().numberBetween(0, numero);
    }


    public static String gerarPassword(){
       return faker.internet().password();
    }

    public static String gerarBoolean(){
        return faker.bool().bool() ? "true" : "false";
    }

    public static String gerarDescricao(){
        return faker.book().title();
    }
}
