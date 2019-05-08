import java.rmi.*;

/**
 *
 * @author emiliano
 */

/*
Interfaz implementada por ServidorImplementacion
*/

public interface Servicios extends Remote {
    public String consultar(String consulta) throws RemoteException; // Metodo utilizado para consultar un signo y pronostico
}
