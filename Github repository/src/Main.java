import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //JOptionPane.showMessageDialog(null, "Hola Mundo");
        //JOptionPane.showMessageDialog(null, "Hola Mundo 2");
        Paciente paciente1 = new Paciente("flor","bergman","f@gmail.com","123", LocalDate.now(),"fem");
       paciente1.login("f@gmail.com","123");
    }
}
