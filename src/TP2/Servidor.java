import java.rmi.*;

/**
 *
 * @author emiliano
 */

public class Servidor {

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
            Servicios serv = new ServidorImplementacion(); // Se instancian los servicios
            Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/ServidorImplementacion",serv); // Se asocia una URL a la IP y puerto de los parametros

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
