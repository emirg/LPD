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
import java.time.*;

/**
 * @see http://www.jc-mouse.net/
 * @author mouse
 */
public class ServidorCentral {

    /**
     * Puerto
     */
    private final static int PORTCLI = 5000;
    private final static int PORTHOR = 5001;
    private final static int PORTPRO = 5002;
    private final static String SERVER = "localhost";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORTCLI);
            Socket ProSocket = new Socket(SERVER, PORTPRO);//abre socket  
            Socket HorSocket = new Socket(SERVER, PORTHOR);//abre socket  
            System.out.println("Central> Servidor iniciado");
            System.out.println("Central> En espera de cliente...");
            //Socket de cliente
            Socket clientSocket;
            while (true) {
                //en espera de conexion, si existe la acepta
                System.out.println("Central> En espera de cliente...");
                clientSocket = serverSocket.accept();
                
                
                ///CLIENTE///
                //Para leer lo que envie el cliente
                BufferedReader inputCliente = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //para imprimir datos de salida                
                PrintStream outputCliente = new PrintStream(clientSocket.getOutputStream());
                
                
                ///HOROSCOPO///
                //Para leer lo que envie el cliente
                BufferedReader inputH = new BufferedReader(new InputStreamReader(HorSocket.getInputStream()));
                //para enviar datos a SH
                PrintStream outputHor = new PrintStream(HorSocket.getOutputStream());
                
                
                ///PRONOSTICO///
                //Para leer lo que envie el cliente
                BufferedReader inputP = new BufferedReader(new InputStreamReader(ProSocket.getInputStream()));
                //para enviar datos a SP
                PrintStream outputPro = new PrintStream(ProSocket.getOutputStream());
                
                
            
                //se lee peticion del cliente
                String request = inputCliente.readLine();
                System.out.println("Cliente> petición [" + request + "]");
                //se procesa la peticion y se espera resultado
                String[] datos = process(request);
                String signo = datos[0];
                String fecha = datos[1];
                
                //manda peticion al servidorHoroscopo
                outputHor.println(signo);
                
                //manda peticion al servidorPronostico
                outputPro.println(fecha);
                
                
                //recibo respuesta SH
                String respuestaHoroscopo = inputH.readLine();
                     
                //recibo respuesta SP
                String respuestaPronostico = inputP.readLine();
                
                String respuesta = respuestaHoroscopo + " y " + respuestaPronostico;
                
                //Se imprime en consola "servidor"
                System.out.println("Central> Resultado de petición");
                System.out.println("Central> \"" + respuesta + "\"");
                

                //se imprime en cliente
                outputCliente.flush();//vacia contenido
                outputHor.flush();
                outputPro.flush();
                outputCliente.println(respuesta);
                
                //cierra conexion
                clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String[] process(String request) {
        String[] datos;
        datos = request.split(" ");
        
        System.out.println(datos[0]);
        System.out.println(datos[1]);
        return datos;
    }
}
