import java.rmi.*;

/**
 *
 * @author emiliano
 */

public interface ServiciosPronostico extends Remote {
    public String consultarPronostico(String consulta) throws RemoteException;
}
