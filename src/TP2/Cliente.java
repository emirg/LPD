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
public class Cliente {

    public static void main(String[] args) {
        try {
        	System.out.println("Buscando Servicios");
            Servicios serv = (Servicios) Naming.lookup("rmi://localhost:54321/ServidorImplementacion");
            System.out.println("Enviado...");
            System.out.println(serv.consultar("aries 10-11-2002"));
          
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
