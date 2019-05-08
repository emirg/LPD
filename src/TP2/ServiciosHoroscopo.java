import java.rmi.*;

/**
 *
 * @author emiliano
 */

/*
Interfaz implementada por ServidorHoroscopo
*/

public interface ServiciosHoroscopo extends Remote {
    public String consultarHoroscopo(String consulta) throws RemoteException; // Metodo utilizado para consultar el horoscopo
}
