/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP1;

/**
 *
 * @author emiliano
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente {

    private final static int PORT = 5000; // Puerto por el que se conectara al ServidorCentral
    private final static String SERVER = "localhost"; // IP donde se aloja el ServidorCentral

    public static void main(String[] args) {
        boolean exit = false; //Bandera para controlar ciclo del programa
        Socket socket;//Socket para la comunicacion cliente servidor        
        try {
            System.out.println("Cliente> Inicio");
            while (!exit) {//Ciclo repetitivo                                
                socket = new Socket(SERVER, PORT);//Abre socket                
                
                //Para leer lo que envie el servidor      
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                //Para imprimir dataos del servidor
                PrintStream output = new PrintStream(socket.getOutputStream());
                
                //Para leer lo que escriba el usuario            
                BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));
                
                System.out.println("Cliente> Escriba peticion [signo dd-mm-yyyy]"); // signo dd-mm-yyyy
                
                //Captura comando escrito por el usuario
                String request = brRequest.readLine();
                
                //Manda peticion al servidor
                output.println(request);
                
                //Captura respuesta e imprime
                String st = input.readLine();
                if (st != null) {
                    System.out.println("Servidor> " + st);
                }
                if (request.equals("exit")) {//Terminar aplicacion
                    exit = true;
                    System.out.println("Cliente> Fin de programa");
                }
                socket.close();
            } //Fin while                                    
        } catch (IOException ex) {
            System.err.println("Cliente> " + ex.getMessage());
        }
    }
    


}
