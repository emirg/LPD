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

public class ServidorPronostico {

    /**
     * Puerto
     */
    private final static int PORT = 5002;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Pronostico> Servidor iniciado");
            //Socket de cliente
            Socket clientSocket;
            System.out.println("Pronostico> En espera de cliente...");
            clientSocket = serverSocket.accept();
            System.out.println("Pronostico> Conexion nueva...");
            //Para leer lo que envie el cliente
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //para imprimir datos de salida                
            PrintStream output = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                //en espera de conexion, si existe la acepta

                //se lee peticion del cliente
                String request = input.readLine();
                if (request != null) {
                    new ManejadorPronostico(request, output).start();
                }
                //cierra conexion
                //clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

class ManejadorPronostico extends Thread { // Clase del thread encargado de manejar peticiones una vez aceptadas

    private String request;
    private PrintStream output; // Buffer de salida
    private ServicioPronostico servPronostico; // Servicio encargado de buscar la consulta 

    public ManejadorPronostico(String request, PrintStream output) {
        this.request = request;
        this.output = output;
        servPronostico = new ServicioPronostico();

    }

    public ManejadorPronostico(Runnable r) {
        super(r);
    }

    @Override
    public void run() {
        System.out.println("Cliente> peticion [" + request + "]");
        //se procesa la peticion y se espera resultado
        String respuesta = servPronostico.getClima(request);
        //Se imprime en consola "servidor"
        System.out.println("Pronostico> Resultado de peticion");
        System.out.println("Pronostico> \"" + respuesta + "\"");
        output.flush();//Vacia contenido del buffer
        output.println(respuesta); //Envia respuesta
        //cierra conexion
        //clientSocket.close();
    }

    public static String process(String request) {
        // Metodo utilizado si se desea devolver respuestas random
        String result;
        //Estados meteorologicos
        String[] predicciones = {
            "Soleado",
            "Nublado",
            "Lluvia fuertes",
            "Tormentas",
            "Llovizna",
            "Nieve",
            "Tormenta de arena"
        };
        
        ArrayList<String> prediccionesList = new ArrayList<>();
        Collections.addAll(prediccionesList, predicciones);
        //Collections.shuffle(prediccionesList);
        int randomIndex = new Random().nextInt(prediccionesList.size());
        result = prediccionesList.get(randomIndex);

        return result;
    }
}
