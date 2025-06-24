package BLL;

import DLL.ControllerMedico;
import GUI.MenuMedico;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Medico extends Usuario {
    private String especialidad;
    private List<Turno> turnosReservados;

    public Medico(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, String especialidad) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.especialidad = especialidad;
    }

    public Medico() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getTurnosReservados() {
        return turnosReservados;
    }

    public void setTurnosReservados(List<Turno> turnosReservados) {
        this.turnosReservados = turnosReservados;
    }

    public String editarPerfil() {
        if (this.getNombre().isEmpty() || this.getMail().isEmpty() || this.getContrasenia().isEmpty()) {
            return "No se pudo editar";
        } else {
            return ControllerMedico.actualizarPerfil(this);
        }
    }

    public List<Turno> obtenerMisTurnos() {
        return ControllerMedico.obtenerTodosLosTurnosDeMedico(this.getIdUsuario());
    }

    public void atenderTurno(Turno turno) {
        ControllerMedico.atenderTurno(turno, this);
    }

    public boolean cancelarTurno(int idTurno) {
        return ControllerMedico.cancelarTurno(idTurno);
    }

    public boolean reprogramarTurno(int idTurno, Timestamp nuevaFechaHora) {
        return ControllerMedico.reprogramarTurno(idTurno, nuevaFechaHora);
    }

    public void verTurnosAntiguo() {
        ControllerMedico.verTurnos(this);
    }

    public void atenderTurnoIndividualAntiguo() {
        ControllerMedico.atenderTurnoIndividual(this);
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
                    verTurnosAntiguo();
                    break;
                case 1:
                    atenderTurnoIndividualAntiguo();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null,"Cancelar Turno: ");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null,"Reprogramar turno:");
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null,"Ver Estudio:");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        } while (opcion != 5);
    }

    @Override
    public String toString() {
        return "Medico{" +
                "especialidad='" + especialidad + '\'' +
                ", turnosReservados=" + turnosReservados +
                '}';
    }
}
