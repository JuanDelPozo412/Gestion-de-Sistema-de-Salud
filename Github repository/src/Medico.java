import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDate;


public class Medico extends Usuario {
    private EspecialidadMedico especialidadMedico;
    private static Connection con = Conexion.getInstance().getConnection();

    public Medico(String nombre, String apellido, String email, String dni, String contrasenia, LocalDate fechaNacimiento, int idUsuario, TipoUsuario tipoUsuario, EspecialidadMedico especialidadMedico) {
        super(nombre, apellido, email, dni, contrasenia, fechaNacimiento, idUsuario, tipoUsuario);
        this.especialidadMedico = especialidadMedico;
    }
    public Medico() {
        super();
        this.setTipoUsuario(TipoUsuario.MEDICO);
    }

    public EspecialidadMedico getEspecialidadMedico() {
        return especialidadMedico;
    }

    public void setEspecialidadMedico(EspecialidadMedico especialidadMedico) {
        this.especialidadMedico = especialidadMedico;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "especialidadMedico=" + especialidadMedico +
                '}';
    }

    public void login() {}
    public void verTurnos() {}
    public void verTurnoIndividual() {}
    public void cancelarTurno() {}
    public void reprogramarTurno() {}
    public void verEstudio() {}

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
