import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        //String nombre = JOptionPane.showInputDialog(null,"Ingresa tu nombre de usuario");
        //String contrasenia = JOptionPane.showInputDialog(null,"Ingresa tu contrasenia");

        //Usuario.login(nombre,contrasenia);
        int idUsuario = 1;
        String nombre = "Flor";
        String apellido = "Bergman";
        String mail = "flor@gmail.com";
        String dni = "87654321";
        String contrasenia = "flor123";
        Date fechaNacimiento = Date.valueOf("1992-04-10");
        String tipoUsuario = "pacientes";
        int planId = 1;


        Paciente flor = new Paciente(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario, null, null, planId);

        flor.mostrarMenuPaciente();

        //JOptionPane.showMessageDialog(null, "Hola Mundo");
        //JOptionPane.showMessageDialog(null, "Hola Mundo 2");
        //Paciente paciente1 = new Paciente("flor","bergman","@gmail.com",3435,"123",LocalDate.now(),"sexo");


       //Administrador admin = new Administrador("agus","Panno","@gmail.com",123,"1234",LocalDate.of(2004,03,03),"admin");


       //PersonalAdministrativo personal = new PersonalAdministrativo("Juan","Del pozo","@gmail.com",123,"1234",LocalDate.now(),"adminstrativo");

       //Medico medico1 = new Medico("Fer", "Fernandez", "@gmail", 43335197, "1234", LocalDate.of(2004,03,03), Especialidades.CLINICO, 5335);

//            String[] opciones = new String[]{"Menu paciente", "Menu medicos","Menu Administrador","Menu Personal Administrativo","Salir"};
//            int opcion;
//            do {
//                opcion = JOptionPane.showOptionDialog(null,
//                        "Seleccione una opcion:",
//                        "Menu principal",
//                        0,
//                        0,
//                        null,
//                        opciones,
//                        opciones);
//                switch (opcion) {
//                    case 0:
//                        paciente1.menuPaciente();
//                        break;
//                    case 1:
//                        medico1.menuMedico();
//                        break;
//                    case 2:
//                        admin.menuadministrador();
//                        break;
//                    case 3:
//                        personal.menuPersonalAdministrativo();
//                        break;
//                    case 4:
//                        JOptionPane.showMessageDialog(null, "Saliendo...");
//                        break;
//
//                }
//            }while (opcion!=4);
//
//
//


    }
}
