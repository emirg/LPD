import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

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
            ServiciosPronostico serv = new ServidorPronostico(); // Se instancian los servicios
            Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/ServidorPronostico",serv); // Se asocia una URL a la IP y puerto de los parametros

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
    * @param consulta una fecha con el formato dd-mm-aaaa
    * @return         una predicción del tiempo acorde a la fecha enviada por parametro
    */

    @Override
    public String consultarPronostico(String consulta) throws RemoteException {
      
        String respuesta = "Recibido :)";

        System.out.println("Cliente> petición [" + consulta + "]");

        respuesta = process(consulta); // Se procesa la consulta para obtener la prediccion


        System.out.println("Pronostico> Resultado de petición");
        System.out.println("Pronostico> \"" + respuesta + "\"");

        return respuesta; // Se devuelve la predicción
    }

    /**
    * @param request una fecha con el formato dd-mm-aaaa
    * @return         una predicción del tiempo acorde a la fecha enviada por parametro
    */

    public  String process(String request) { //Metodo para separar los datos de la consulta
        String numero=request.substring(0,request.indexOf('-'));
        int solicitud=Integer.parseInt(numero);
        String respuesta = solicitud > 0 && solicitud < 32 ? predicciones[solicitud%6] : "Clima Incorrecto";
        return respuesta;
    }
    
}

