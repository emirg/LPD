import java.rmi.*;

/**
 *
 * @author emiliano
 */

public interface Servicios extends Remote {
    public String consultar(String consulta) throws RemoteException;
}
