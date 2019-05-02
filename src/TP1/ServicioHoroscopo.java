package TP1;

public class ServicioHoroscopo {

	/*
        Servicio encargado de guardar las posibles respuestas.
        Es utilizada por la clase ServidorHoroscopo para obtener la respuesta para una consulta dada.
        */
	
	private String[] predicciones;
	
	public ServicioHoroscopo() {
		
		super();
		
		predicciones=new String[12];
		
		predicciones[0]="Hoy es un gran dia";
		predicciones[1]="Hoy vas a estar feliz";
		predicciones[2]="Hoy comes asado";
		predicciones[3]="Hoy sale happy hour";
		predicciones[4]="Mañana terminas de leer un libro";
		predicciones[5]="Ayer sabias que hoy sale happy hour";
		predicciones[6]="Te vas a encontrar plata";
		predicciones[7]="Te tocan 33 de mano";
		predicciones[8]="Te regalan una bicicleta rosa";
		predicciones[9]="El codigo compila a la primera";
		predicciones[10]="Te llaman de google";
		predicciones[11]="El codigo no compila";
		
		
	}
	

	public String getHoroscopo(String unHoroscopoSolicitado) {
		

		String resultado="";
		
		switch(unHoroscopoSolicitado.toLowerCase()) {
		
			case "libra": resultado=predicciones[0] ;break;
			case "aries": resultado=predicciones[1];break;
			case "tauro": resultado=predicciones[2];break;
			case "geminis": resultado=predicciones[3];break;
			case "cancer": resultado=predicciones[4];break;
			case "leo": resultado=predicciones[5];break;
			case "virgo": resultado=predicciones[6];break;
			case "escorpio": resultado=predicciones[7];break;
			case "sagitario": resultado=predicciones[8];break;
			case "capricornio": resultado=predicciones[9];break;
			case "acuario": resultado=predicciones[10];break;
			case "piscis": resultado=predicciones[11];break;
			default: resultado="El signo ingresado es incorrecto!" ;break;	
		
		}
		
		return resultado;
	}

	

}
