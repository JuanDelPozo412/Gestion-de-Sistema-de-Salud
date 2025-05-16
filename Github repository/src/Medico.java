import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

public class Medico extends Usuario {
    private Especialidades especialidades;


    public Medico(String nombre, String apellido, String mail, int dni, String contrasenia, Date fechaNacimiento, Especialidades especialidades) {
        super(nombre, apellido, mail, dni, contrasenia, fechaNacimiento);
        this.especialidades = especialidades;
    }

    public Especialidades getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Especialidades especialidades) {
        this.especialidades = especialidades;
    }



    @Override
    public String toString() {
        return "Medico{" +
                "nombre=" + getNombre() +
                ", apellido=" + getApellido() +
                ", especialidad='" + especialidades + '\'' +
                '}';
    }

    public void login() {
        System.out.println("sesion iniciada como médico.");
    }

    public void verTurnos() {

    }
    public void verTurnoIndividual() {

    }
    public void cancelarTurno() {
    }
    public void reprogramarTurno() {
    }
    public void verEstudio() {
    }

    public void menuMedico() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/medico.png"));
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Medico",
                    0,
                    0,
                    icon,
                    MenuMedico.values(),
                    MenuMedico.values());
            switch (opcion) {
                case 0:
                    verTurnos();
                    JOptionPane.showMessageDialog(null,"Informacion del turno: \nNumero de turno:  \nTipo de turno: \nMedico: \nHorario: \nFecha: \nMotivo de la consulta: \nDiagnostico: \nTratamiento: \nDetalles: ");

                    break;
                case 1:
                    verTurnoIndividual();
                    JOptionPane.showMessageDialog(null,"Ver turno Individual de tus pacientes: ");
                    break;
                case 2:
                    cancelarTurno();
                    JOptionPane.showMessageDialog(null,"Cancelar Turno: ");
                    break;
                case 3:
                    reprogramarTurno();
                    JOptionPane.showMessageDialog(null,"Reprogramar turnoo:");
                    break;
                case 4:
                    verEstudio();
                    JOptionPane.showMessageDialog(null,"Ver Estudio:");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=5);

    }
}
