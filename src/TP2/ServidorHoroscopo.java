import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

/**
 *
 * @author emiliano
 */

public class ServidorHoroscopo extends UnicastRemoteObject implements ServiciosHoroscopo {

    private static final long serialVersionUID = 1L;
    private static final String[] predicciones = { // BD con posibles predicciones 	
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
    * @param args argumentos de la linea de comando 
    * @param args[0] una direccion IP (localhost es valido)
    * @param args[1] un numero de puerto (debe tener asociado un servicio RMI Registry)
    */

    public static void main(String[] args) {
        if (args.length != 2) { // Si no se ingresan la cantidad de parametros correctos
            System.err.println("Uso: Ingresar IP y Puerto");
            return;
        }
        /*if (System.getSecurityManager() == null) {
            // System.setSecurityManager(new RMISecurityManager()); 
            System.setProperty("java.rmi.server.hostname", "10.0.0.199");
        }*/
        try {
            ServiciosHoroscopo serv = new ServidorHoroscopo(); // Se instancian los servicios
            Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/ServidorHoroscopo",serv); // Se asocia una URL a la IP y puerto de los parametros

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
	* @param consulta es un signo del horoscopo
	* @return         una predicción acorde al signo enviado por parametro
    */

    @Override
    public String consultarHoroscopo(String consulta) throws RemoteException {
		String respuesta = "Recibido :)"; 

		System.out.println("Cliente> petición [" + consulta + "]");

		respuesta = process(consulta); // Se procesa la consulta buscando la respuesta en la BD


		System.out.println("Horoscopo> Resultado de petición");
		System.out.println("Horoscopo> \"" + respuesta + "\"");

		return respuesta; // Devuelve la predicción
    }

    /**
	* @param request es un signo del horoscopo
	* @return         una predicción acorde al signo enviado por parametro
    */
    
    public String process(String request) { //Metodo para separar los datos de la consulta
	        
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
}

