/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.*;

/**
 *
 * @author emiliano
 */
public interface Servicios extends Remote {
    public String consultar(String consulta) throws RemoteException;
}
