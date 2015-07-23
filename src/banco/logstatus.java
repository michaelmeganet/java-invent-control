package banco;

//import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class logstatus{
	logstatus(String txt){
		this.addNewEvent(txt);
	}
        public void showError(int exceptionerror){
            switch(exceptionerror){
                case 1045:
                        JOptionPane.showMessageDialog(null, "Erro ao conectar-se: Acesso negado.");
                break;
                case 1046:
                        JOptionPane.showMessageDialog(null, "Erro ao conectar-se: Nenhum banco de dados foi selecionado.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro ao conectar-se.");
                    break;
         }
}
	public void addNewEvent(String msg)
	{
		bdados.ta_status_a.append( msg);
	}
        /*
public String getHora() {  
      
    // cria um StringBuilder  
    StringBuilder sb = new StringBuilder();  
  
    // cria um GregorianCalendar que vai conter a hora atual  
    GregorianCalendar d = new GregorianCalendar();  
      
    // anexa do StringBuilder os dados da hora  
    sb.append( d.get( GregorianCalendar.HOUR_OF_DAY ) );  
    sb.append( ":" );  
    sb.append( d.get( GregorianCalendar.MINUTE ) );  
    sb.append( ":" );  
    sb.append( d.get( GregorianCalendar.SECOND ) );  
      
    // retorna a String do StringBuilder  
    return sb.toString();  
      
    }  */
}
