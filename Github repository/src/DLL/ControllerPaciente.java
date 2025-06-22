package DLL;
import BLL.Medico;
import BLL.Paciente;
import BLL.PlanSalud;
import BLL.Turno;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerPaciente {
    private static Connection con = Conexion.getInstance().getConnection();


    public static void verUltimoTurno(Paciente paciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    // esto es para ver el ultimo turno y que traiga solo 1
                    "SELECT idTurno, especialidad, fecha, estado FROM turnos WHERE paciente_id = ? ORDER BY fecha DESC LIMIT 1"
            );
            stmt.setInt(1, paciente.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idTurno = rs.getInt("idTurno");
                String especialidad = rs.getString("especialidad");
                String fecha = rs.getString("fecha");
                String estado = rs.getString("estado");

                String mensaje = "ultimo turno:\n"
                        + "ID: " + idTurno + "\n"
                        + "Especialidad: " + especialidad + "\n"
                        + "Fecha: " + fecha + "\n"
                        + "Estado: " + estado;

                JOptionPane.showMessageDialog(null, mensaje);
            } else {
                JOptionPane.showMessageDialog(null, "No tenes turnos registrados");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el ultimo turno: " + e.getMessage());
        }
    }
    public static String obtenerHistorial(Paciente paciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM entradas_historial WHERE paciente_id = ?"
            );
            stmt.setInt(1, paciente.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            String historial = "";
            while (rs.next()) {
                int entradaId = rs.getInt("entradaId");
                Date fechaHora = rs.getDate("fechaHoraEntrada");
                String tipo = rs.getString("tipoEntrada");
                String descripcion = rs.getString("descripcionDetallada");
                int medicoId = rs.getInt("medico_responsable_id");
                int turnoId = rs.getInt("turno_asociado_id");

                String nombreMedico = obtenerNombreMedicoPorId(medicoId);

                historial += "Entrada ID: " + entradaId + "\n";
                historial += "Fecha: " + fechaHora + "\n";
                historial += "Tipo: " + tipo + "\n";
                historial += "Descripcion: " + descripcion + "\n";
                historial += "Medico responsable: " + nombreMedico + "\n";
                historial += "ID turno asociado: " + turnoId + "\n\n";
            }

            if (historial.isEmpty()) {
                return "No hay historial para mostrar";
            } else {
                return historial;
            }

        } catch (Exception e) {
            return "Error al consultar historial";
        }
    }

    private static String obtenerNombreMedicoPorId(int medicoId) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT nombre FROM usuarios WHERE idUsuario = ?"
            );
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener nombre del medico: " + e.getMessage());
        }
        return "No hay medico";
    }

    public static List<Turno> obtenerTurnos(Paciente paciente) {
        List<Turno> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT idTurno, medico_id, fecha, estado FROM turnos WHERE paciente_id = ? ORDER BY fecha DESC"
            );
            stmt.setInt(1, paciente.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idTurno = rs.getInt("idTurno");
                int medicoId = rs.getInt("medico_id");
                Timestamp fecha = rs.getTimestamp("fecha"); //hicimos el cambio a timestamp
                String estado = rs.getString("estado");

                PreparedStatement stmtEsp = con.prepareStatement(
                        "SELECT especialidad FROM medicos WHERE usuario_id = ?"
                );
                stmtEsp.setInt(1, medicoId);
                ResultSet rsEsp = stmtEsp.executeQuery();
                String especialidad = rsEsp.next() ? rsEsp.getString("especialidad") : "";

                PreparedStatement stmtNom = con.prepareStatement(
                        "SELECT nombre FROM usuarios WHERE idUsuario = ?"
                );
                stmtNom.setInt(1, medicoId);
                ResultSet rsNom = stmtNom.executeQuery();
                String nombre = rsNom.next() ? rsNom.getString("nombre") : "";

                Medico medico = new Medico();
                medico.setIdUsuario(medicoId);
                medico.setNombre(nombre);
                medico.setEspecialidad(especialidad);

                Turno turno = new Turno(idTurno, paciente, medico, fecha, estado);
                lista.add(turno);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener turnos: " + e.getMessage());
        }

        return lista;
    }




    private static String obtenerNombreUsuario(int id) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM usuarios WHERE idUsuario = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Desconocido";
    }
    public static String actualizarPerfil(Paciente paciente) {
        try {
            PreparedStatement stmtUsuario = con.prepareStatement(
                    "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, contrasenia = ? WHERE idUsuario = ?"
            );
            stmtUsuario.setString(1, paciente.getNombre());
            stmtUsuario.setString(2, paciente.getApellido());
            stmtUsuario.setString(3, paciente.getMail());
            stmtUsuario.setString(4, paciente.getContrasenia());
            stmtUsuario.setInt(5, paciente.getIdUsuario());
            stmtUsuario.executeUpdate();

            PreparedStatement stmtPaciente = con.prepareStatement(
                    "UPDATE pacientes SET plan_id = ? WHERE usuario_id = ?"
            );
            stmtPaciente.setInt(1, paciente.getPlanId());
            stmtPaciente.setInt(2, paciente.getIdUsuario());

            int filas = stmtPaciente.executeUpdate();
            if (filas > 0) {
                return "Perfil actualizado correctamente";
            }

        } catch (MySQLIntegrityConstraintViolationException e) {
            return "Error: el mail ya existe";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error al actualizar perfil";
    }
    public static List<PlanSalud> obtenerPlanes() {
        List<PlanSalud> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM planes_salud");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("planId");
                String nombre = rs.getString("nombrePlan");
                String descripcion = rs.getString("descripcion");
                PlanSalud plan = new PlanSalud(id, nombre, descripcion);
                lista.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public static void cancelarTurno(int idTurno, int idPaciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE turnos SET estado = 'Cancelado' WHERE idTurno = ? AND paciente_id = ?"
            );
            stmt.setInt(1, idTurno);
            stmt.setInt(2, idPaciente);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Turno cancelado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo cancelar el turno ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cancelar turno: " + e.getMessage());
        }
    }
    public static ArrayList<String> obtenerEspecialidades() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT especialidad FROM medicos");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("especialidad"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ArrayList<String> obtenerMedicosPorEspecialidad(String especialidad) {
        ArrayList<String> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT usuario_id FROM medicos WHERE especialidad = ?");
            stmt.setString(1, especialidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("usuario_id");
                lista.add(obtenerNombreUsuario(id) + " / ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }




    public static String reservarTurnoDesdePantalla(Paciente paciente, String especialidad, String medicoSeleccionado, String fecha, String hora) {
        try {
            int idMedico = Integer.parseInt(medicoSeleccionado.split("ID: ")[1]);
            Timestamp timestamp = Timestamp.valueOf(fecha + " " + hora);

            PreparedStatement check = con.prepareStatement(
                    "SELECT COUNT(*) FROM turnos WHERE medico_id = ? AND fecha = ? AND estado != 'Cancelado'"
            );
            check.setInt(1, idMedico);
            check.setTimestamp(2, timestamp);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "Ese turno ya esta ocupado por otro paciente";
            }

            PreparedStatement stmtInsert = con.prepareStatement(
                    "INSERT INTO turnos (paciente_id, medico_id, especialidad, fecha, estado) VALUES (?, ?, ?, ?, ?)"
            );
            stmtInsert.setInt(1, paciente.getIdUsuario());
            stmtInsert.setInt(2, idMedico);
            stmtInsert.setString(3, especialidad);
            stmtInsert.setTimestamp(4, timestamp);
            stmtInsert.setString(5, "Pendiente");

            int filas = stmtInsert.executeUpdate();
            return filas > 0 ? "Turno reservado con exito" : "No se pudo reservar el turno";

        } catch (Exception e) {
            return "Error al reservar turno: " + e.getMessage();
        }
    }

    public static boolean existeTurno(int pacienteId, String medicoSeleccionado, String fechaHora) {
        try {
            int idMedico = Integer.parseInt(medicoSeleccionado.split("ID: ")[1]);
            Timestamp timestamp = Timestamp.valueOf(fechaHora);

            PreparedStatement stmt = con.prepareStatement(
                    //si devuelve mas de uno es porque ya esta el turno entonces no deja reservar otro igual
                    "SELECT COUNT(*) FROM turnos WHERE paciente_id = ? AND medico_id = ? AND fecha = ?"
            );
            stmt.setInt(1, pacienteId);
            stmt.setInt(2, idMedico);
            stmt.setTimestamp(3, timestamp);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String obtenerPlan(Paciente paciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT nombrePlan, descripcion FROM planes_salud WHERE planId = ?"
            );
            stmt.setInt(1, paciente.getPlanId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombrePlan");
                String descripcion = rs.getString("descripcion");
                return "Plan de salud: " + nombre + "\nDescripcion: " + descripcion;
            } else {
                return "No se encontro un plan asociado al paciente";
            }
        } catch (Exception e) {
            return "Error al consultar el plan";
        }
    }

}



