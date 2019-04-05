package Final;


public class ServicioPronostico {
	
        /*
        Servicio encargado de guardar las posibles respuestas.
        Es utilizada por la clase ServidorPronostico para obtener la respuesta para una consulta dada.
        */
    
	private String[] predicciones;
	private int solicitud;
	
	public ServicioPronostico() {
		
		super();
		
		predicciones=new String[31];
				
		predicciones[0]="Granizo";
		predicciones[1]="Calor";
		predicciones[2]="Nieva";
		predicciones[3]="Viento";
		predicciones[4]="Terremoto";
		predicciones[5]="Tornado";
		predicciones[6]="Granizo";
		predicciones[7]="Llueve";
		predicciones[8]="Calor";
		predicciones[9]="Nieva";
		predicciones[10]="Viento";
		predicciones[11]="Terremoto";
		predicciones[12]="Tornado";
		predicciones[13]="Granizo";
		predicciones[14]="Llueve";
		predicciones[15]="Calor";
		predicciones[16]="Nieva";
		predicciones[17]="Viento";
		predicciones[18]="Terremoto";
		predicciones[19]="Tornado";
		predicciones[20]="Granizo";
		predicciones[21]="Llueve";
		predicciones[22]="Calor";
		predicciones[23]="Nieva";
		predicciones[24]="Viento";
		predicciones[25]="Terremoto";
		predicciones[26]="Tornado";
		predicciones[27]="Granizo";
		predicciones[28]="Tornado";
		predicciones[29]="Granizo";
		predicciones[30]="Llueve";

		
	}
	
	

	public String getClima(String unClimaSolicitado) {
		
		String numero=unClimaSolicitado.substring(0,unClimaSolicitado.indexOf('-'));
		solicitud=Integer.parseInt(numero)-1;
		String resultado="";
		
		switch(solicitud) {
			
			case 0: resultado=predicciones[31];break;
			case 1: resultado=predicciones[0] ;break;
			case 2: resultado=predicciones[1];break;
			case 3: resultado=predicciones[2];break;
			case 4: resultado=predicciones[3];break;
			case 5: resultado=predicciones[4];break;
			case 6: resultado=predicciones[5];break;
			case 7: resultado=predicciones[6];break;
			case 8: resultado=predicciones[8];break;
			case 9: resultado=predicciones[9];break;
			case 10: resultado=predicciones[10];break;
			case 11: resultado=predicciones[11];break;
			case 12: resultado=predicciones[12];break;
			case 13: resultado=predicciones[13];break;
			case 14: resultado=predicciones[14];break;
			case 15: resultado=predicciones[15];break;
			case 16: resultado=predicciones[16];break;
			case 17: resultado=predicciones[17];break;
			case 18: resultado=predicciones[18];break;
			case 19: resultado=predicciones[19];break;
			case 20: resultado=predicciones[20];break;
			case 21: resultado=predicciones[21];break;
			case 22: resultado=predicciones[22];break;
			case 23: resultado=predicciones[23];break;
			case 24: resultado=predicciones[24];break;
			case 25: resultado=predicciones[25];break;
			case 26: resultado=predicciones[26];break;
			case 27: resultado=predicciones[27];break;
			case 28: resultado=predicciones[28];break;
			case 29: resultado=predicciones[29];break;
			case 30: resultado=predicciones[30];break;
			

			
			default: resultado="Clima incorrecto!" ;break;	
		
		}
		
		return resultado;
	}

	

}
