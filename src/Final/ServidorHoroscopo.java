/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

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
import java.util.Random;


public class ServidorHoroscopo {

    /*
    Puerto
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
            //Socket de cliente, en este caso el cliente sera el ServidorCentral
            Socket clientSocket;
            System.out.println("Horoscopo> En espera de cliente...");
            clientSocket = serverSocket.accept(); //En espera de conexion, si existe la acepta
            System.out.println("Horoscopo> Conexion nueva...");
            //Para leer lo que envie el cliente
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //Para imprimir datos de salida                
            PrintStream output = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                //Se lee peticion del cliente
                String request = input.readLine();
                if (request != null) { // Si hay una consulta nueva en el buffer...
                    new ManejadorHoroscopo(request, output).start(); //Se crea un hilo que se encargue de las peticiones del cliente sobre el socket creado
                }
                //cierra conexion
                //clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

class ManejadorHoroscopo extends Thread { // Clase del thread encargado de manejar peticiones una vez aceptadas

    private String request; 
    private PrintStream output; // Buffer de salida
    private ServicioHoroscopo servHoroscopo; // Servicio encargado de buscar la consulta 
    
    public ManejadorHoroscopo(String request, PrintStream output) {
        this.request = request;
        this.output = output;
        servHoroscopo=new ServicioHoroscopo();

    }

    public ManejadorHoroscopo(Runnable r) {
        super(r);
    }

    @Override
    public void run() {
        System.out.println("Horoscopo> peticion [" + request + "]");
        //Se procesa la peticion y se espera resultado
        String respuesta = servHoroscopo.getHoroscopo(request);
        //Se imprime en consola "servidor"
        System.out.println("Horoscopo> Resultado de petición");
        System.out.println("Horoscopo> \"" + respuesta + "\"");
        output.flush();//Vacia contenido del buffer
        output.println(respuesta); //Se envia la respuesta al ServidorCentral
    }

    public static String process(String request) {
        // Metodo utilizado si se desea devolver respuestas random
        String result;
        //Frases
        String[] predicciones = {
            "Triunfaras en el amor",
            "Perderas en el amor",
            "Triunfaras economicamente",
            "Perderas economicamente",
            "Mejoraras de salud",
            "Empeoraras de salud",
            "Pregunta luego"
        };

        ArrayList<String> prediccionesList = new ArrayList<>();
        Collections.addAll(prediccionesList, predicciones);
        //Collections.shuffle(prediccionesList);
        int randomIndex = new Random().nextInt(prediccionesList.size()); //Tomo una respuesta random
        result = prediccionesList.get(randomIndex);

        return result;
    }
}
