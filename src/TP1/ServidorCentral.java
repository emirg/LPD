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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServidorCentral {

    // SC = ServidorCentral
    // SH = ServidorHoroscopo
    // SP = ServidorPronostico
    // C = Cliente
    
    /*
    Puerto
    */
    private final static int PORTCLI = 5000; // Puerto para conectar con clientes
    private final static int PORTHOR = 5001; // Puerto para conectar con ServidorHoroscopo (Un unico socket durante todo el tiempo de vida del server)
    private final static int PORTPRO = 5002; // Puerto para conectar con ServidorPronostico (Un unico socket durante todo el tiempo de vida del server)
    private final static String SERVER = "localhost"; // IP donde se alojan ServidorPronostico y ServidorHoroscopo
    private static ConcurrentHashMap<String, String> cache; // Estructura de Datos thread-safe utilizada como cache para no repetir consultas

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORTCLI); //Socket de servidor para esperar peticiones de la red
            Socket proSocket = new Socket(SERVER, PORTPRO);//Abre socket  
            Socket horSocket = new Socket(SERVER, PORTHOR);//Abre socket
            cache = new ConcurrentHashMap<>();

            System.out.println("Central> Servidor iniciado");
            
            //Socket de cliente
            Socket clientSocket;
            while (true) {
                //En espera de conexion, si existe la acepta
                System.out.println("Central> En espera de cliente...");
                clientSocket = serverSocket.accept();

                //  Una vez aceptada la conexion con el cliente:
                //  Se crea un nuevo socket 
                //  Se crea un hilo que se encargue de las peticiones del cliente sobre el socket creado
                new ManejadorCentral(clientSocket, proSocket, horSocket, cache).start(); 
           }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

class ManejadorCentral extends Thread { // Clase del thread encargado de manejar peticiones una vez aceptadas

    // Todos los atributos son inicializados por el constructor ya que fueron inicializados por el thread principal de ServidorCentral
    private Socket clientSocket;
    private Socket horSocket;
    private Socket proSocket;
    private ConcurrentHashMap cache;

    public ManejadorCentral(Socket cliente, Socket pronos, Socket horos, ConcurrentHashMap cache) {
        this.clientSocket = cliente;
        this.horSocket = horos;
        this.proSocket = pronos;
        this.cache = cache;
    }

    public ManejadorCentral(Runnable r) {
        super(r);
    }

    @Override
    public void run() {
        try {
            String respuesta;

            ///CLIENTE///
            //Para leer lo que envie el cliente
            BufferedReader inputCliente = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //Para imprimir datos de salida               
            PrintStream outputCliente = new PrintStream(clientSocket.getOutputStream());

            ///HOROSCOPO///
            //Para leer lo que envie el cliente
            BufferedReader inputH = new BufferedReader(new InputStreamReader(horSocket.getInputStream()));
            //Para enviar datos a SH
            PrintStream outputHor = new PrintStream(horSocket.getOutputStream());

            ///PRONOSTICO///
            //Para leer lo que envie el cliente
            BufferedReader inputP = new BufferedReader(new InputStreamReader(proSocket.getInputStream()));
            //Para enviar datos a SP
            PrintStream outputPro = new PrintStream(proSocket.getOutputStream());

            //Se lee peticion del cliente
            String request = inputCliente.readLine();
            System.out.println("Cliente> petición [" + request + "]");
            //Se procesa la peticion y se espera resultado
            if (cache.containsKey(request)) { //Si la consulta esta en cache...
                respuesta = (String) cache.get(request);
                System.out.println("REQUEST ESTABA EN CACHE");
            } else {
                String[] datos = process(request); //Consulta no estaba en cache por lo que debo procesarla y consultar a los otros servidores
                String signo = datos[0];
                String fecha = datos[1];

                //Manda peticion al servidorHoroscopo
                outputHor.println(signo);

                //Manda peticion al servidorPronostico
                outputPro.println(fecha);


                //Recibo respuesta SH
                String respuestaHoroscopo = inputH.readLine();
                System.out.println("Respuesta Hor: " + respuestaHoroscopo);
                //recibo respuesta SP
                String respuestaPronostico = inputP.readLine();
                System.out.println("Respuesta Pro: " + respuestaPronostico);

                respuesta = respuestaHoroscopo + " y " + respuestaPronostico;
                
                
                if (cache.size() > 10) {
                    String vieja = (String) cache.keys().nextElement();
                    cache.remove(vieja);
                    //System.out.println("SAQUE UNA CONSULTA CACHEADA: "+ vieja);
                }
                cache.put(request, respuesta);
                //System.out.println("REQUEST NO ESTABA EN CACHE");
            }
            
            //Se imprime en consola "servidor"
            System.out.println("Central> Resultado de petición");
            System.out.println("Central> \"" + respuesta + "\"");

            
            //Por cuestiones de testing se duerme por un tiempo el hilo.
            Thread.sleep(10000); //Duermo 30 sec
            
            //Se imprime en cliente
            outputCliente.flush();//Vacia contenido del buffer
            outputHor.flush();//Vacia contenido del buffer
            outputPro.flush();//Vacia contenido del buffer
            outputCliente.println(respuesta);

            //cierra conexion
            inputCliente.close();
            outputCliente.close();
            clientSocket.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(ManejadorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String[] process(String request) { //Metodo para separar los datos de la consulta
        String[] datos = new String[2];
        if (request != null) {
            datos = request.split(" ");
            System.out.println(datos[0]);
            System.out.println(datos[1]);
        }
        return datos;
    }
}
