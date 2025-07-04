package DLL;

import BLL.Medico;
import BLL.Paciente;
import BLL.Turno;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.swing.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ControllerMedico {
    private static Connection con = Conexion.getInstance().getConnection();



    public static List<Turno> obtenerTodosLosTurnosDeMedico(int medicoId) {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT " +
                "t.idTurno, t.fecha, t.estado, "
                + "p.usuario_id AS paciente_id, u_paciente.nombre AS paciente_nombre, "
                + "m.usuario_id AS medico_id, u_medico.nombre AS medico_nombre, m.especialidad AS medico_especialidad "
                + "FROM turnos t "
                + "JOIN pacientes p ON t.paciente_id = p.usuario_id "
                + "JOIN usuarios u_paciente ON p.usuario_id = u_paciente.idUsuario "
                + "JOIN medicos m ON t.medico_id = m.usuario_id "
                + "JOIN usuarios u_medico ON m.usuario_id = u_medico.idUsuario "
                + "WHERE t.medico_id = ? "
                + "ORDER BY t.fecha DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();

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
                turno.setPaciente(paciente);
                turno.setMedico(medicoTurno);
                turnos.add(turno);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los turnos del médico: " + e.getMessage());
            e.printStackTrace();
        }
        return turnos;
    }

    public static void atenderTurno(Turno turno, Medico medico) {
        if (!turno.getEstado().equalsIgnoreCase("Pendiente")) {
            JOptionPane.showMessageDialog(null, "Este turno no está pendiente, no se puede atender.");
            return;
        }

        try {
            String tipoEntrada = JOptionPane.showInputDialog(null, "Ingrese el tipo de consulta (Ej: Diagnóstico, Seguimiento):", "Atender Turno", JOptionPane.PLAIN_MESSAGE);

            if (tipoEntrada == null) {
                return;
            }
            if (tipoEntrada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El tipo de consulta no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción de la atención y diagnóstico:", "Atender Turno", JOptionPane.PLAIN_MESSAGE);

            if (descripcion == null) {
                return;
            }
            if (descripcion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La descripción no puede estar vacía.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement updateTurno = con.prepareStatement(
                    "UPDATE turnos SET estado = 'Atendido' WHERE idTurno = ?"
            );
            updateTurno.setInt(1, turno.getIdTurno());
            int filasTurno = updateTurno.executeUpdate();

            if (filasTurno > 0) {
                Timestamp fechaHoraActual = new Timestamp(new java.util.Date().getTime());

                String sqlInsert = "INSERT INTO entradas_historial " +
                        "(turno_asociado_id, paciente_id, medico_responsable_id, fechaHoraEntrada, tipoEntrada, descripcionDetallada) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement insertEntrada = con.prepareStatement(sqlInsert);

                insertEntrada.setInt(1, turno.getIdTurno());
                insertEntrada.setInt(2, turno.getPaciente().getIdUsuario());
                insertEntrada.setInt(3, medico.getIdUsuario());
                insertEntrada.setTimestamp(4, fechaHoraActual);
                insertEntrada.setString(5, tipoEntrada);
                insertEntrada.setString(6, descripcion);

                insertEntrada.executeUpdate();

                JOptionPane.showMessageDialog(null, "Turno atendido y entrada en historial registrada correctamente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al atender el turno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean cancelarTurno(int idTurno) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE turnos SET estado = 'Cancelado' WHERE idTurno = ?"
            );
            stmt.setInt(1, idTurno);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar turno: " + e.getMessage());
            return false;
        }
    }

    public static boolean reprogramarTurno(int idTurno, Timestamp nuevaFechaHora) {
        try{
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE turnos SET fecha = ? WHERE idTurno = ?"
            );
            stmt.setTimestamp(1, nuevaFechaHora);
            stmt.setInt(2, idTurno);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al reprogramar el turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

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

    public static String actualizarPerfil(Medico medico) {
        try {
            PreparedStatement stmtUsuario = con.prepareStatement(
                    "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, contrasenia = ? WHERE idUsuario = ?"
            );
            stmtUsuario.setString(1, medico.getNombre());
            stmtUsuario.setString(2, medico.getApellido());
            stmtUsuario.setString(3, medico.getMail());
            stmtUsuario.setString(4, medico.getContrasenia());
            stmtUsuario.setInt(5, medico.getIdUsuario());

            int filasAfectadas = stmtUsuario.executeUpdate();

            if (filasAfectadas > 0) {

                return "Perfil actualizado correctamente";
            } else {

                return "Error: No se encontró el usuario o no hubo cambios para actualizar.";
            }

        } catch (MySQLIntegrityConstraintViolationException e) {

            return "Error: El mail ingresado ya está registrado.";
        } catch (SQLException e) {
            System.err.println("Error al actualizar perfil de médico: " + e.getMessage());
            e.printStackTrace();
            return "Error al actualizar perfil en la base de datos.";
        }

    }





}

