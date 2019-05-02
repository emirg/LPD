import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
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
        String respuesta = "Recibido :)";

        System.out.println("Cliente> petición [" + consulta + "]");

        try {
            if (cache.containsKey(consulta)) { //Si la consulta esta en cache...
                respuesta = (String) cache.get(consulta);
                System.out.println("REQUEST ESTABA EN CACHE");
            } else {
                String[] datos = process(consulta);
                String signo = datos[0];
                String fecha = datos[1];

                fecha = pronostico.consultarPronostico(fecha);
                signo = horoscopo.consultarHoroscopo(signo);

                respuesta = signo + " y " + fecha;
                System.out.println("Central> Resultado de petición");
                System.out.println("Central> \"" + respuesta + "\"");
                if (cache.size() > 10) {
                    String vieja = (String) cache.keys().nextElement();
                    cache.remove(vieja);
                    //System.out.println("SAQUE UNA CONSULTA CACHEADA: "+ vieja);
                }
                cache.put(consulta, respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;

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

}


