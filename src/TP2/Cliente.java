import java.rmi.*;

/**
 *
 * @author emiliano
 */

public class Cliente {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: Ingresar Consulta (Formato: 'signo dd-mm-aaaa')");
            return;
        }

        try {
        	System.out.println("Buscando Servicios");
            Servicios serv = (Servicios) Naming.lookup("rmi://localhost:54321/ServidorImplementacion");
            System.out.println("Enviado...");
            System.out.println("Respuestas: " + serv.consultar(args[0] + " " + args[1]));
          
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error con servidores");
            System.out.println("Asegurese que servidores estan funcionando");
        }

    }
}
