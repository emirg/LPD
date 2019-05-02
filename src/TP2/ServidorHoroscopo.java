import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

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
        if (args.length != 2) {
            System.err.println("Uso: Ingresar IP y Puerto");
            return;
        }
        /*if (System.getSecurityManager() == null) {
            // System.setSecurityManager(new RMISecurityManager()); 
            System.setProperty("java.rmi.server.hostname", "10.0.0.199");
        }*/
        try {
            ServiciosHoroscopo serv = new ServidorHoroscopo();
            Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/ServidorHoroscopo",serv);

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String consultarHoroscopo(String consulta) throws RemoteException {
		String respuesta = "Recibido :)";

		System.out.println("Cliente> petición [" + consulta + "]");

		respuesta = process(consulta);


		System.out.println("Horoscopo> Resultado de petición");
		System.out.println("Horoscopo> \"" + respuesta + "\"");

		return respuesta;
    }

      public  String process(String consulta) { //Metodo para separar los datos de la consulta
	        
			String resultado="";
			
			switch(consulta.toLowerCase()) {
			
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

