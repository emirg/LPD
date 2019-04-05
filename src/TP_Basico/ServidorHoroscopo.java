/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Basico;

/**
 *
 * @author emiliano
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @see http://www.jc-mouse.net/
 * @author mouse
 */
public class ServidorHoroscopo {

    /**
     * Puerto
     */
    private final static int PORT = 5001;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Horoscopo> Servidor iniciado");

            //Socket de cliente
            Socket clientSocket;
            System.out.println("Horoscopo> En espera de cliente...");
            clientSocket = serverSocket.accept();
            System.out.println("Horoscopo> Conexion nueva...");
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //para imprimir datos de salida                
            PrintStream output = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                //en espera de conexion, si existe la acepta

                //Para leer lo que envie el cliente
                //se lee peticion del cliente
                String request = input.readLine();
                if (request != null) {
                    System.out.println("Cliente> petición [" + request + "]");
                    //se procesa la peticion y se espera resultado
                    String strOutput = process(request);
                    //Se imprime en consola "servidor"
                    System.out.println("Horoscopo> Resultado de petición");
                    System.out.println("Horoscopo> \"" + strOutput + "\"");
                    //se imprime en cliente
                    output.flush();//vacia contenido
                    output.println(strOutput);
                }
                //cierra conexion
                //clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String process(String request) {
        int t = 0;
        String result = "";
        //frases
        String[] predicciones = {
            "Triunfaras en el amor",
            "Perderas en el amor",
            "Triunfaras economicamente",
            "Perderas economicamente",
            "Mejoraras de salud",
            "Empeoraras de salud",
            "Pregunta luego"
        };

        ArrayList<String> prediccionesList = new ArrayList<String>();
        Collections.addAll(prediccionesList, predicciones);

        Collections.shuffle(prediccionesList);
        result = prediccionesList.get(0);

        return result;
    }

}
