import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //JOptionPane.showMessageDialog(null, "Hola Mundo");
        //JOptionPane.showMessageDialog(null, "Hola Mundo 2");
        Paciente paciente1 = new Paciente("flor","bergman","@gmail.com",3435,"123",LocalDate.now(),"sexo");
       paciente1.login("@gmail.com","123");
    }
}
