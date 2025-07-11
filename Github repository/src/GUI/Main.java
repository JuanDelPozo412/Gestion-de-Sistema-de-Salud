package GUI;
import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;

import javax.swing.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        //este trycatch maneja una exepcion cuando se aplica el "lookandfeel" sirve para q la vista tome el estilo que viene en el sistema operativo lo hace ver mas lindo, lo dejo comentado por si lo quieren aplicar, de momento voy con lo basico
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //Swing Utilities lo que hace es ejecutar la pantalla cuando sea seguro, es basicamente para evitar bugs, todavia no lo entiendo bien igualmente
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new PantallaHome().setVisible(true);
//            }
//        });

        //por eso lo dejo de la forma simple
        new PantallaHome().setVisible(true);

    }
}