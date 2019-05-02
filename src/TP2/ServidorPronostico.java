/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.rmi.*;
//import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author emiliano
 */
public class ServidorPronostico extends UnicastRemoteObject implements ServiciosPronostico {

    private static final long serialVersionUID = 1L;
    private static final String[] predicciones = {"Granizo","Calor","Nieva","Viento","Terremoto","Tornado"};

    protected ServidorPronostico() throws RemoteException {

        super();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: Servidor Puerto");
            return;
        }
        /*if (System.getSecurityManager() == null) {
            // System.setSecurityManager(new RMISecurityManager()); 
            System.setProperty("java.rmi.server.hostname", "10.0.0.199");
        }*/
        try {
            ServiciosPronostico serv = new ServidorPronostico();
            Naming.rebind("rmi://localhost:" + args[0] + "/ServidorPronostico",serv);

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String consultarPronostico(String consulta) throws RemoteException {
        String resultadoCadena = "Vacio";
        try {
            ExecutorService servicio = Executors.newFixedThreadPool(1);
            Future<String> resultado = servicio.submit(new ManejadorPronostico(consulta));
            

            System.out.println(resultado.get());
            resultadoCadena = resultado.get();
            
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }

        return resultadoCadena;

    }

    class ManejadorPronostico implements Callable<String> {

	    private final String consulta;

	    public ManejadorPronostico(String consulta) {
	        this.consulta = consulta;
	        //this.cache = cache;
	    }

	    public  String process(String request) { //Metodo para separar los datos de la consulta
	        String numero=request.substring(0,request.indexOf('-'));
			int solicitud=Integer.parseInt(numero);
			String respuesta = solicitud > 0 && solicitud < 32 ? predicciones[solicitud%6] : "Clima Incorrecto";
			return respuesta;
	    }

	    @Override
	    public String call() throws Exception {
	        String respuesta = "Recibido :)";

	        System.out.println("Cliente> petición [" + consulta + "]");

	        respuesta = process(consulta);
	        
	      
	        System.out.println("Pronostico> Resultado de petición");
	        System.out.println("Pronostico> \"" + respuesta + "\"");

	        return respuesta;
	    }
	}



}

