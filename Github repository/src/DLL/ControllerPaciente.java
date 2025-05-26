package DLL;
import BLL.Paciente;
import com.mysql.jdbc.Connection;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerPaciente {
    private static Connection con = Conexion.getInstance().getConnection();

    public static void verPlan(Paciente paciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT nombrePlan, descripcion FROM planes_salud WHERE planId = ?"
            );

            stmt.setInt(1, paciente.getPlanId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombrePlan");
                String descripcion = rs.getString("descripcion");
                String mensaje = "Plan de salud: " + nombre + "\nDescripcion: " + descripcion;
                JOptionPane.showMessageDialog(null, mensaje);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro un plan asociado al paciente: " + paciente.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el plan");
        }
    }
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
    public static void verHistorialMedico(Paciente paciente) {
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
                JOptionPane.showMessageDialog(null, "No hay historial para mostrar");
            } else {
                JOptionPane.showMessageDialog(null, historial);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar historial");
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

    //cambie este metodo para probar el combobox
    public static List<String> obtenerTurnos(Paciente paciente) {
        List<String> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT idTurno, especialidad, fecha, estado FROM turnos WHERE paciente_id = ? ORDER BY fecha DESC"
            );
            stmt.setInt(1, paciente.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idTurno = rs.getInt("idTurno");
                String especialidad = rs.getString("especialidad");
                String fecha = rs.getString("fecha");
                String estado = rs.getString("estado");

                String turnoStr = "ID: " + idTurno + " - " + especialidad + " - " + fecha + " - " + estado;
                lista.add(turnoStr);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar turnos: " + e.getMessage());
        }

        return lista;
    }
    ///////////////
    public static void reservarTurno(Paciente paciente) {
        try {
            PreparedStatement stmtEspecialidad = con.prepareStatement("SELECT DISTINCT especialidad FROM medicos");
            ResultSet rsEspecialidad = stmtEspecialidad.executeQuery();
            ArrayList<String> especialidades = new ArrayList<>();
            while (rsEspecialidad.next()) {
                especialidades.add(rsEspecialidad.getString("especialidad"));
            }

            String[] opcionesEsp = especialidades.toArray(new String[0]);
            String especialidadSeleccionada = (String) JOptionPane.showInputDialog(null,
                    "Seleccione especialidad:",
                    "Especialidad",
                    0,
                    null,
                    opcionesEsp,
                    opcionesEsp[0]);
            PreparedStatement stmtMedico = con.prepareStatement("SELECT * FROM medicos WHERE especialidad = ?");
            stmtMedico.setString(1, especialidadSeleccionada);
            ResultSet rsMedico = stmtMedico.executeQuery();
            ArrayList<String> medicos = new ArrayList<>();
            ArrayList<Integer> ids = new ArrayList<>();
            while (rsMedico.next()) {
                int id = rsMedico.getInt("usuario_id");
                String nombre = obtenerNombreUsuario(id);
                medicos.add(nombre + " / ID: " + id);
                ids.add(id);
            }

            String[] opcionesMed = medicos.toArray(new String[0]);
            String seleccionado = (String) JOptionPane.showInputDialog(null,
                    "Seleccione medico:",
                    "Médico",
                    0,
                    null,
                    opcionesMed,
                    opcionesMed[0]);

            int posicion = medicos.indexOf(seleccionado);
            int idMedicoSeleccionado = ids.get(posicion);


            ArrayList<String> fechasDisponibles = new ArrayList<>();
            LocalDate hoy = LocalDate.now();
            for (int i = 0; i < 5; i++) {
                fechasDisponibles.add(hoy.plusDays(i).toString());
            }
            String[] fechas = fechasDisponibles.toArray(new String[0]);

            String fechaElegida = (String) JOptionPane.showInputDialog(null,
                    "Seleccione fecha:",
                    "Fecha",
                    0,
                    null,
                    fechas,
                    fechas[0]);

            String[] horarios = {"08:00:00", "10:00:00", "14:00:00"};
            String horaSeleccionada = (String) JOptionPane.showInputDialog(null,
                    "Seleccione horario:",
                    "Horario",
                    0,
                    null,
                    horarios,
                    horarios[0]);
            String fechaHora = fechaElegida + " " + horaSeleccionada;
            Timestamp timestamp = Timestamp.valueOf(fechaHora);

            PreparedStatement stmtInsert = con.prepareStatement(
                    "INSERT INTO turnos (paciente_id, medico_id, especialidad, fecha, estado) VALUES (?, ?, ?, ?, ?)"
            );
            stmtInsert.setInt(1, paciente.getIdUsuario());
            stmtInsert.setInt(2, idMedicoSeleccionado);
            stmtInsert.setString(3, especialidadSeleccionada);
            stmtInsert.setTimestamp(4, timestamp);
            stmtInsert.setString(5, "Pendiente");
            stmtInsert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Turno reservado con exito");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al reservar turno: " + e.getMessage());
        }
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
    public static void actualizarPerfil(Paciente paciente) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, contrasenia = ? WHERE idUsuario = ?"
            );
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getMail());
            stmt.setString(4, paciente.getContrasenia());
            stmt.setInt(5, paciente.getIdUsuario());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Perfil actualizado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar perfil: " + e.getMessage());
        }
    }





}



