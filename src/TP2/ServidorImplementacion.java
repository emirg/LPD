/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.util.concurrent.Callable;
//import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author emiliano
 */
public class ServidorImplementacion extends UnicastRemoteObject implements Servicios {

    private static final long serialVersionUID = 1L;
    private static ServiciosHoroscopo horoscopo;
    private static ServiciosPronostico pronostico;
    private static ConcurrentHashMap<String, String> cache; // Estructura de Datos thread-safe utilizada como cache para no repetir consultas

    protected ServidorImplementacion() throws RemoteException {

        super();
        try{
            cache = new ConcurrentHashMap<>();
            pronostico =  (ServiciosPronostico) Naming.lookup("rmi://localhost:54321/ServidorPronostico");
            horoscopo =  (ServiciosHoroscopo) Naming.lookup("rmi://localhost:54321/ServidorHoroscopo");

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String consultar(String consulta) throws RemoteException {
        String resultadoCadena = "Vacio";
        try {
            if (cache.containsKey(consulta)) { //Si la consulta esta en cache...
                resultadoCadena = (String) cache.get(consulta);
                System.out.println("REQUEST ESTABA EN CACHE");
            } else {
                ExecutorService servicio = Executors.newFixedThreadPool(1);
                Future<String> resultado = servicio.submit(new ManejadorCentral(consulta));
                System.out.println(resultado.get());
                resultadoCadena = resultado.get();
                if (cache.size() > 10) {
                    String vieja = (String) cache.keys().nextElement();
                    cache.remove(vieja);
                    //System.out.println("SAQUE UNA CONSULTA CACHEADA: "+ vieja);
                }
                cache.put(consulta, resultadoCadena);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("ERROR");

        }

        return resultadoCadena;

    }

    class ManejadorCentral implements Callable<String> {

        private final String consulta;
        //private final ServiciosPronostico pronostico;

        public ManejadorCentral(String consulta) {
            this.consulta = consulta;
            
        }

        public  String[] process(String request) { //Metodo para separar los datos de la consulta
            String[] datos = new String[2];
            if (request != null) {
                datos = request.split(" ");
                System.out.println(datos[0]);
                System.out.println(datos[1]);
            }
            return datos;
        }

        @Override
        public String call() throws Exception {
            String respuesta = "Recibido :)";

            System.out.println("Cliente> petición [" + consulta + "]");

            String[] datos = process(consulta);
            String signo = datos[0];
            String fecha = datos[1];

            fecha = pronostico.consultarPronostico(fecha);
            signo = horoscopo.consultarHoroscopo(signo);

            respuesta = signo + " y " + fecha;
            System.out.println("Central> Resultado de petición");
            System.out.println("Central> \"" + respuesta + "\"");

            return respuesta;
        }
    }

}


