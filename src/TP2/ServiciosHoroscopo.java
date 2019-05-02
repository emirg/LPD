import java.rmi.*;

/**
 *
 * @author emiliano
 */

public interface ServiciosHoroscopo extends Remote {
    public String consultarHoroscopo(String consulta) throws RemoteException;
}
