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
    private static ServiciosHoroscopo horoscopo; // Servicios del servidor horoscopo
    private static ServiciosPronostico pronostico; // Servicios del servidor pronostico
    private static ConcurrentHashMap<String, String> cache; // Estructura de Datos thread-safe utilizada como cache para no repetir consultas

    protected ServidorImplementacion() throws RemoteException {

        super();
        try{
            cache = new ConcurrentHashMap<>();
            pronostico =  (ServiciosPronostico) Naming.lookup("rmi://localhost:54321/ServidorPronostico"); // Conexion al servidor pronostico
            horoscopo =  (ServiciosHoroscopo) Naming.lookup("rmi://localhost:54321/ServidorHoroscopo"); // Conexion al servidor horoscopo

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
    * @param consulta un signo del horoscopo junto con una fecha (Ej: aries 15-05-2019)
    * @return         una prediccion del horoscopo y del tiempo en la fecha dada
    */

    @Override
    public String consultar(String consulta) throws RemoteException {
        String respuesta = "Recibido :)";

        System.out.println("Cliente> petición [" + consulta + "]");

        try {
            if (cache.containsKey(consulta)) { //Si la consulta esta en cache...
                respuesta = (String) cache.get(consulta);
                System.out.println("REQUEST ESTABA EN CACHE");
            } else {
                String[] datos = process(consulta); // Separa la consulta en los dos datos
                String signo = datos[0];
                String fecha = datos[1];

                fecha = pronostico.consultarPronostico(fecha); // Invocacion remota a pronostico
                signo = horoscopo.consultarHoroscopo(signo); // Invocacion remota a horoscopo

                respuesta = signo + " y " + fecha;
                System.out.println("Central> Resultado de petición");
                System.out.println("Central> \"" + respuesta + "\"");
                if (cache.size() > 10) { // Si la cache esta "llena" elimina un elemento
                    String vieja = (String) cache.keys().nextElement();
                    cache.remove(vieja);
                    //System.out.println("SAQUE UNA CONSULTA CACHEADA: "+ vieja);
                }
                cache.put(consulta, respuesta); // Coloca la nueva consulta en la cache
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;

    }

    /**
    * @param request  un signo del horoscopo junto con una fecha (Ej: aries 15-05-2019)
    * @return         un arreglo donde la primer posicion tiene el signo y en la segunda la fecha
    */

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


