import java.rmi.*;

/**
 *
 * @author emiliano
 */

/*
Interfaz implementada por ServidorPronostico
*/

public interface ServiciosPronostico extends Remote {
    public String consultarPronostico(String consulta) throws RemoteException; // Metodo utilizado para consultar el pronostico
}
