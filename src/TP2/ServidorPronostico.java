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
            ServiciosPronostico serv = new ServidorPronostico();
            Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/ServidorPronostico",serv);

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String consultarPronostico(String consulta) throws RemoteException {
      
        String respuesta = "Recibido :)";

        System.out.println("Cliente> petición [" + consulta + "]");

        respuesta = process(consulta);


        System.out.println("Pronostico> Resultado de petición");
        System.out.println("Pronostico> \"" + respuesta + "\"");

        return respuesta;
    }

    public  String process(String request) { //Metodo para separar los datos de la consulta
        String numero=request.substring(0,request.indexOf('-'));
        int solicitud=Integer.parseInt(numero);
        String respuesta = solicitud > 0 && solicitud < 32 ? predicciones[solicitud%6] : "Clima Incorrecto";
        return respuesta;
    }
    
}

