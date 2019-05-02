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
public class ServidorHoroscopo extends UnicastRemoteObject implements ServiciosHoroscopo {

    private static final long serialVersionUID = 1L;
    private static final String[] predicciones = {	
    	"Hoy es un gran dia",
    	"Hoy vas a estar feliz",
    	"Hoy comes asado",
    	"Hoy sale happy hour",
		"Mañana terminas de leer un libro",
		"Ayer sabias que hoy sale happy hour",
		"Te vas a encontrar plata",
		"Te tocan 33 de mano",
		"Te regalan una bicicleta rosa",
		"El codigo compila a la primera",
		"Te llaman de google",
		"El codigo no compila"
	};

    protected ServidorHoroscopo() throws RemoteException {

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
            ServiciosHoroscopo serv = new ServidorHoroscopo();
            Naming.rebind("rmi://localhost:" + args[0] + "/ServidorHoroscopo",serv);

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String consultarHoroscopo(String consulta) throws RemoteException {
        String resultadoCadena = "Vacio";
        try {
            ExecutorService servicio = Executors.newFixedThreadPool(1);
            Future<String> resultado = servicio.submit(new ManejadorHoroscopo(consulta));
            

            System.out.println(resultado.get());
            resultadoCadena = resultado.get();
            
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }

        return resultadoCadena;

    }

    class ManejadorHoroscopo implements Callable<String> {

	    private final String consulta;

	    public ManejadorHoroscopo(String consulta) {
	        this.consulta = consulta;
	        //this.cache = cache;
	    }

	    public  String process(String request) { //Metodo para separar los datos de la consulta
	        
			String resultado="";
			
			switch(request.toLowerCase()) {
			
				case "libra": resultado=predicciones[0] ;break;
				case "aries": resultado=predicciones[1];break;
				case "tauro": resultado=predicciones[2];break;
				case "geminis": resultado=predicciones[3];break;
				case "cancer": resultado=predicciones[4];break;
				case "leo": resultado=predicciones[5];break;
				case "virgo": resultado=predicciones[6];break;
				case "escorpio": resultado=predicciones[7];break;
				case "sagitario": resultado=predicciones[8];break;
				case "capricornio": resultado=predicciones[9];break;
				case "acuario": resultado=predicciones[10];break;
				case "piscis": resultado=predicciones[11];break;
				default: resultado="El signo ingresado es incorrecto!" ;break;	
			
			}
			return resultado;
	    }

	    @Override
	    public String call() throws Exception {
	        String respuesta = "Recibido :)";

	        System.out.println("Cliente> petición [" + consulta + "]");

	        respuesta = process(consulta);
	        
	      
	        System.out.println("Horoscopo> Resultado de petición");
	        System.out.println("Horoscopo> \"" + respuesta + "\"");

	        return respuesta;
	    }
	}



}

