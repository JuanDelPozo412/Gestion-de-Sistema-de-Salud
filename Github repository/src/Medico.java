import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Medico extends Usuario {
    private String especialidad;
    private List<Turno> turnosReservados;
    private static Connection con = Conexion.getInstance().getConnection();

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

    @Override
    public String toString() {
        return "Medico{" +
                "especialidad='" + especialidad + '\'' +
                ", turnosReservados=" + turnosReservados +
                '}';
    }

    public void verTurnos() {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT t.idTurno, t.fecha, t.estado, t.especialidad, " +
                            "p.usuario_id, u.nombre " +
                            "FROM turnos t " +
                            "JOIN pacientes p ON t.paciente_id = p.usuario_id " +
                            "JOIN usuarios u ON p.usuario_id = u.idUsuario " +
                            "WHERE t.medico_id = ?"
            );
            stmt.setInt(1, this.getIdUsuario());

            ResultSet rs = stmt.executeQuery();

            List<String> resumenTurnos = new ArrayList<>();
            List<String> detallesTurnos = new ArrayList<>();

            while (rs.next()) {
                String nombrePaciente = rs.getString("nombre");

                String resumen = (resumenTurnos.size() + 1) + ". Turno N° " + rs.getInt("idTurno") +
                        " - Paciente: " + nombrePaciente +
                        " - Especialidad: " + rs.getString("especialidad") +
                        " - Fecha: " + rs.getDate("fecha");

                String detalles = "Información del turno:\n"
                        + "N° de turno: " + rs.getInt("idTurno") + "\n"
                        + "Nombre del paciente: " + nombrePaciente + "\n"
                        + "Tipo de turno: " + rs.getString("especialidad") + "\n"
                        + "Fecha: " + rs.getDate("fecha") + "\n"
                        + "Estado: " + rs.getString("estado");

                resumenTurnos.add(resumen);
                detallesTurnos.add(detalles);
            }

            if (resumenTurnos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay turnos registrados para este médico.");
                return;
            }

            String menu = "Seleccione un turno:\n";
            for (String linea : resumenTurnos) {
                menu += linea + "\n";
            }

            String entrada = JOptionPane.showInputDialog(menu);
            int seleccion = Integer.parseInt(entrada) - 1;

            if (seleccion >= 0 && seleccion < detallesTurnos.size()) {
                JOptionPane.showMessageDialog(null, detallesTurnos.get(seleccion));
            } else {
                JOptionPane.showMessageDialog(null, "Selección inválida.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar los turnos: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
        }
    }



//    public void verTurnoIndividual() {
//
//    }
    // public void atenderTurnoIndividual();
//    public void cancelarTurno() {
//    }
//    public void reprogramarTurno() {
//    }


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

                    break;
                case 1:
                    //verTurnoIndividual();
                    JOptionPane.showMessageDialog(null,"Ver turno Individual de tus pacientes: ");
                    break;
                case 2:
                    //cancelarTurno();
                    JOptionPane.showMessageDialog(null,"Cancelar Turno: ");
                    break;
                case 3:
                    //reprogramarTurno();
                    JOptionPane.showMessageDialog(null,"Reprogramar turnoo:");
                    break;
                case 4:
                    //verEstudio();
                    JOptionPane.showMessageDialog(null,"Ver Estudio:");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=5);

    }
}
