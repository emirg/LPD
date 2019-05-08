import java.rmi.*;

/**
 *
 * @author emiliano
 */

public class Cliente { // Clase utilizada por el lado cliente
    
    /**
     * @param args argumentos de la linea de comando
     * @param args[0] un signo del horoscopo
     * @param args[1] una fecha con el formato dd-mm-aaaa
     */

    public static void main(String[] args) {
        if (args.length != 2) { // Si no se ingreso la cantidad correcta de parametros
            System.err.println("Uso: Ingresar Consulta (Formato: 'signo dd-mm-aaaa')");
            return;
        }

        try {
        	System.out.println("Buscando Servicios");
            Servicios serv = (Servicios) Naming.lookup("rmi://localhost:54321/ServidorImplementacion"); // Se buscan los servicios provsitos por el servidor 
            System.out.println("Enviado...");
            System.out.println("Respuestas: " + serv.consultar(args[0] + " " + args[1])); // Invocacion remota
          
        } catch (Exception e) { 
            e.printStackTrace();
            System.out.println("Error con servidores");
            System.out.println("Asegurese que servidores estan funcionando");
        }

    }
}
