/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Avanzado;

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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Deberiamos en teoria mantener los buffers durante el tiempo de vida del socket
Si cerramos el socket tenemos que cerrar los buffers
 */
public class ServidorCentral {

    /**
     * Puerto
     */
    private final static int PORTCLI = 5000;
    private final static int PORTHOR = 5001;
    private final static int PORTPRO = 5002;
    private final static String SERVER = "localhost";
    private static ConcurrentHashMap<String, String> cache;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORTCLI);
            Socket proSocket = new Socket(SERVER, PORTPRO);//abre socket  
            Socket horSocket = new Socket(SERVER, PORTHOR);//abre socket
            cache = new ConcurrentHashMap<>();

            System.out.println("Central> Servidor iniciado");
            //Socket de cliente
            Socket clientSocket;
            while (true) {
                //en espera de conexion, si existe la acepta
                System.out.println("Central> En espera de cliente...");
                clientSocket = serverSocket.accept();

                new ManejadorCentral(clientSocket, proSocket, horSocket, cache).start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}

class ManejadorCentral extends Thread {

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
            //para imprimir datos de salida               
            PrintStream outputCliente = new PrintStream(clientSocket.getOutputStream());

            ///HOROSCOPO///
            //Para leer lo que envie el cliente
            BufferedReader inputH = new BufferedReader(new InputStreamReader(horSocket.getInputStream()));
            //para enviar datos a SH
            PrintStream outputHor = new PrintStream(horSocket.getOutputStream());

            ///PRONOSTICO///
            //Para leer lo que envie el cliente
            BufferedReader inputP = new BufferedReader(new InputStreamReader(proSocket.getInputStream()));
            //para enviar datos a SP
            PrintStream outputPro = new PrintStream(proSocket.getOutputStream());

            //se lee peticion del cliente
            String request = inputCliente.readLine();
            System.out.println("Cliente> petición [" + request + "]");
            //se procesa la peticion y se espera resultado
            if (cache.containsKey(request)) {
                respuesta = (String) cache.get(request);
                System.out.println("REQUEST ESTABA EN CACHE");
            } else {
                String[] datos = process(request);
                String signo = datos[0];
                String fecha = datos[1];

                /*
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date today = sdf.parse(datos[1]);
                 */
                //manda peticion al servidorHoroscopo
                outputHor.println(signo);
                //System.out.println("LE MANDE A HOR");
                //manda peticion al servidorPronostico
                outputPro.println(fecha);
                //System.out.println("LE MANDE A PRO");

                //recibo respuesta SH
                String respuestaHoroscopo = inputH.readLine();
                System.out.println("Respuesta Hor: " + respuestaHoroscopo);
                //recibo respuesta SP
                String respuestaPronostico = inputP.readLine();
                System.out.println("Respuesta Pro: " + respuestaPronostico);

                respuesta = respuestaHoroscopo + " y " + respuestaPronostico;
                
                
                if (cache.mappingCount() > 10) {
                    String vieja = (String) cache.keys().nextElement();
                    cache.remove(vieja);
                    System.out.println("SAQUE UNA CONSULTA CACHEADA: "+ vieja);
                }
                cache.put(request, respuesta);
                //System.out.println("REQUEST NO ESTABA EN CACHE");
            }
            //Se imprime en consola "servidor"
            System.out.println("Central> Resultado de petición");
            System.out.println("Central> \"" + respuesta + "\"");
            //System.out.println("DURMIENDO THREAD");
            Thread.sleep(10000); //Duermo 30 sec
            //se imprime en cliente
            outputCliente.flush();//vacia contenido
            outputHor.flush();
            outputPro.flush();
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
        /*catch (ParseException ex) {
            Logger.getLogger(ManejadorCentral.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FORMATO FECHA NO VALIDO");
        }*/
    }

    public static String[] process(String request) {
        String[] datos = new String[2];
        if (request != null) {
            datos = request.split(" ");

            System.out.println(datos[0]);
            System.out.println(datos[1]);
        }
        return datos;
    }
}
