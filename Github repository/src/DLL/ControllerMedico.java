package DLL;

import BLL.Medico;
import BLL.Paciente;
import BLL.Turno;

import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerMedico {
    private static Connection con = Conexion.getInstance().getConnection();

    public static void verTurnos(Medico medico) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT t.idTurno, t.fecha, t.estado, t.especialidad, " +
                            "p.usuario_id, u.nombre " +
                            "FROM turnos t " +
                            "JOIN pacientes p ON t.paciente_id = p.usuario_id " +
                            "JOIN usuarios u ON p.usuario_id = u.idUsuario " +
                            "WHERE t.medico_id = ?"
            );
            stmt.setInt(1, medico.getIdUsuario());

            ResultSet rs = stmt.executeQuery();

            List<String> resumenTurnos = new ArrayList<>();
            List<String> detallesTurnos = new ArrayList<>();

            while (rs.next()) {
                String nombrePaciente = rs.getString("nombre");

                String resumen = (resumenTurnos.size() + 1) + ".Turno N° " + rs.getInt("idTurno") +
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

    public static void atenderTurnoIndividual(Medico medico) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT t.idTurno, t.fecha, t.especialidad, t.estado, t.paciente_id, u.nombre " +
                            "FROM turnos t " +
                            "JOIN pacientes p ON t.paciente_id = p.usuario_id " +
                            "JOIN usuarios u ON p.usuario_id = u.idUsuario " +
                            "WHERE t.medico_id = ? AND t.estado = 'pendiente'"
            );
            stmt.setInt(1, medico.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            List<String> opcionesTurnos = new ArrayList<>();
            List<Integer> idsTurno = new ArrayList<>();
            List<Integer> idsPaciente = new ArrayList<>();

            while (rs.next()) {
                int idTurno = rs.getInt("idTurno");
                int pacienteId = rs.getInt("paciente_id");
                String nombrePaciente = rs.getString("nombre");
                Date fecha = rs.getDate("fecha");
                String especialidad = rs.getString("especialidad");

                String linea = (opcionesTurnos.size() + 1) + ". Turno N° " + idTurno +
                        " - Paciente: " + nombrePaciente + " - " + especialidad + " - " + fecha;

                opcionesTurnos.add(linea);
                idsTurno.add(idTurno);
                idsPaciente.add(pacienteId);
            }

            if (opcionesTurnos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay turnos pendientes para atender.");
                return;
            }

            String menu = "Seleccione un turno para atender:\n";
            for (String opcion : opcionesTurnos) {
                menu += opcion + "\n";
            }

            String entrada = JOptionPane.showInputDialog(menu);
            int seleccion = Integer.parseInt(entrada) - 1;

            if (seleccion < 0 || seleccion >= idsTurno.size()) {
                JOptionPane.showMessageDialog(null, "Selección inválida.");
                return;
            }

            int turnoId = idsTurno.get(seleccion);
            int pacienteId = idsPaciente.get(seleccion);


            PreparedStatement updateTurno = con.prepareStatement(
                    "UPDATE turnos SET estado = 'atendido' WHERE idTurno = ?"
            );
            updateTurno.setInt(1, turnoId);
            updateTurno.executeUpdate();

            String tipoEntrada = JOptionPane.showInputDialog("Ingrese el tipo de consulta:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la atención:");

            PreparedStatement insertEntrada = con.prepareStatement(
                    "INSERT INTO entradas_historial (turno_asociado_id, paciente_id, medico_responsable_id,tipoEntrada, descripcionDetallada) VALUES (?, ?, ?, ?, ?)"
            );
            insertEntrada.setInt(1, turnoId);
            insertEntrada.setInt(2, pacienteId);
            insertEntrada.setInt(3, medico.getIdUsuario());
            insertEntrada.setString(4, tipoEntrada);
            insertEntrada.setString(5, descripcion);
            insertEntrada.executeUpdate();

            JOptionPane.showMessageDialog(null, "BLL.Turno atendido y entrada registrada correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al atender el turno: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
        }
    }
    public static List<Turno> obtenerTodosLosTurnosDeMedico(int medicoId) {
        List<Turno> turnos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT " +
                    "t.idTurno, t.fecha, t.estado, t.especialidad AS turno_especialidad, " +
                    "p.usuario_id AS paciente_id, u_paciente.nombre AS paciente_nombre, " +
                    "m.usuario_id AS medico_id, u_medico.nombre AS medico_nombre, m.especialidad AS medico_especialidad " +
                    "FROM turnos t " +
                    "JOIN pacientes p ON t.paciente_id = p.usuario_id " +
                    "JOIN usuarios u_paciente ON p.usuario_id = u_paciente.idUsuario " +
                    "JOIN medicos m ON t.medico_id = m.usuario_id " +
                    "JOIN usuarios u_medico ON m.usuario_id = u_medico.idUsuario " +
                    "WHERE t.medico_id = ? " +
                    "ORDER BY t.fecha DESC";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, medicoId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setIdUsuario(rs.getInt("paciente_id"));
                paciente.setNombre(rs.getString("paciente_nombre"));


                Medico medicoTurno = new Medico();
                medicoTurno.setIdUsuario(rs.getInt("medico_id"));
                medicoTurno.setNombre(rs.getString("medico_nombre"));
                medicoTurno.setEspecialidad(rs.getString("medico_especialidad"));

                Turno turno = new Turno();
                turno.setIdTurno(rs.getInt("idTurno"));
                turno.setFecha(rs.getTimestamp("fecha"));
                turno.setEstado(rs.getString("estado"));
                turno.setEspecialidad(rs.getString("turno_especialidad"));
                turno.setPaciente(paciente);
                turno.setMedico(medicoTurno);
                turnos.add(turno);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener turnos para la tabla del médico: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return turnos;
    }





}
