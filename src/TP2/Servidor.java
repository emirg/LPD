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
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: Servidor Puerto");
            return;
        }
        /*if (System.getSecurityManager() == null) {
            // System.setSecurityManager(new RMISecurityManager()); 
            System.setProperty("java.rmi.server.hostname", "10.0.0.199");
        }*/
        try {
            Servicios serv = new ServidorImplementacion();
            Naming.rebind("rmi://localhost:" + args[0] + "/ServidorImplementacion",serv);

        } catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
