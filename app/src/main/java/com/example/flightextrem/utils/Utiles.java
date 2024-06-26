package com.example.flightextrem.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Utiles {

    private static final String SECRETKEY = "EasyFlight";
    private static final String IV = "0123456789123456";

    public static boolean validarPatron(String cadena, String patron){
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    public static boolean validarSiNumero(String cadena){
        String patron = "[0-9]+";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si la cadena, en este caso el Id del vuelo, coincide con el patrón
        return matcher.matches();
    }

    public static boolean validarIdVuelo(String cadena) {
        // Expresión regular para el patrón alfanumérico
        String patron = "^[A-Z]{2}\\d{4}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si la cadena, en este caso el Id del vuelo, coincide con el patrón
        return matcher.matches();
    }

    public static boolean validarHora(String cadena){
        String patron = "^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean compararFechas(LocalDate fechaInicio, LocalDate fechaFinal){
        return fechaInicio.isBefore(fechaFinal);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalTime convertirATime(String horas){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalTime.parse(horas, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate convertirADate(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");

        try {
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarHoras(LocalTime horaSalida, LocalTime horaLlegada) {
        return horaSalida.isBefore(horaLlegada);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean validarFecha(LocalDate fechaSeleccionada) {
        LocalDate fechaActual = LocalDate.now();
        return !fechaSeleccionada.isBefore(fechaActual);
    }

    public static boolean validarIdAvion(String cadena) {

        String patron = "^[A-Z]\\d{3}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    public static boolean validarTelefono(String cadena) {

        String patron = "^[6|7|9][0-9]{8}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    public static boolean validarSiFecha(String cadena){
        String patron = "([0-9]{4,})([-])([0-9]{2,})([-])([0-9]{2,})";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    public static Boolean validaEmail (String cadena) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    public static boolean validarDNI(String cadena) {

        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(cadena.length() != 9 || Character.isLetter(cadena.charAt(8)) == false ) {
            return false;
        }


        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (cadena.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(cadena) == true && letraDNI(cadena).equals(letraMayuscula)) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean soloNumeros(String cadena) {

        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(i = 0; i < cadena.length() - 1; i++) {
            numero = cadena.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

    private static String letraDNI(String cadena) {
               // pasar miNumero a integer
        int miDNI = Integer.parseInt(cadena.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }

    public static String encriptarAMD5(String input) {
        try {
            PasswordEncrypter password = new PasswordEncrypter();
            return password.encrypt(input,IV,SECRETKEY);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String desencriptarMD5(String passwordBD){
        try {
            PasswordEncrypter password = new PasswordEncrypter();
            return password.decrypt(passwordBD,IV,SECRETKEY);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generarCodigoReservas() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numeros = "0123456789";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            codigo.append(letras.charAt(random.nextInt(letras.length())));
        }

        for (int i = 0; i < 2; i++) {
            codigo.append(numeros.charAt(random.nextInt(numeros.length())));
        }

        return codigo.toString();
    }
}
