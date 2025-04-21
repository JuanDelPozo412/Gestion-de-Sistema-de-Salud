import javax.swing.*;
import java.time.LocalDate;

public class PersonalAdministrativo extends Persona {
   private String sector;

    public PersonalAdministrativo(String nombre, String apellido, String mail, int dni, String contrasenia, LocalDate fechaNacimiento, String sector) {
        super(nombre, apellido, mail, dni, contrasenia, fechaNacimiento);
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "PersonalAdministrativo{" +
                "sector='" + sector + '\'' +
                '}';
    }

    public void menuPersonalAdministrativo () {
        String[] opciones = new String[]{"Ver lista de pacientes", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu administradores",
                    0,
                    0,
                    null,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Lista de pacientes:");
                    menuPaciente();


                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Saliendo...");


                    break;
            }
        } while (opcion != 1);
    }
    public void menuPaciente () {
        String[] opciones = new String[]{"Ver historial", "Descargar historial","Atras"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu pacientes",
                    0,
                    0,
                    null,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null,"Historial");

                    break;
                case 1:
                    JOptionPane.showMessageDialog(null,"Descargar historial");

                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;

            }
        } while (opcion != 2);


}
}
