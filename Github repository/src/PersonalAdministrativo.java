import javax.swing.*;
import java.time.LocalDate;

public class PersonalAdministrativo extends Usuario {
    //esta clase desaparece es una sola con administrador
   private String sector;
   private int idPersonalAdministrativo;


    public PersonalAdministrativo(String nombre, String apellido, String email, String dni, String contrasenia, LocalDate fechaNacimiento, int idUsuario, TipoUsuario tipoUsuario, String sector) {
        super(nombre, apellido, email, dni, contrasenia, fechaNacimiento, idUsuario, tipoUsuario);
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getIdPersonalAdministrativo() {
        return idPersonalAdministrativo;
    }

    public void setIdPersonalAdministrativo(int idPersonalAdministrativo) {
        this.idPersonalAdministrativo = idPersonalAdministrativo;
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
