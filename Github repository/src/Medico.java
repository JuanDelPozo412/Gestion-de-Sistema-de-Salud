import javax.swing.*;
import java.time.LocalDate;

public class Medico extends Persona {
    private Especialidades especialidades;
    private int matricula;

    public Medico(String nombre, String apellido, String mail, int dni, String contrasenia, LocalDate fechaNacimiento, Especialidades especialidades, int matricula) {
        super(nombre, apellido, mail, dni, contrasenia, fechaNacimiento);
        this.especialidades = especialidades;
        this.matricula = matricula;
    }

    public Especialidades getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Especialidades especialidades) {
        this.especialidades = especialidades;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nombre=" + getNombre() +
                ", apellido=" + getApellido() +
                ", especialidad='" + especialidades + '\'' +
                ", matricula=" + matricula +
                '}';
    }

    public void login() {
        System.out.println("sesion iniciada como médico.");
    }

    public void menuMedico() {
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Medico",
                    0,
                    0,
                    null,
                    MenuMedico.values(),
                    MenuMedico.values());
            switch (opcion) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=5);

    }
}
