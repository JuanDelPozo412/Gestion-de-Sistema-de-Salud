package BLL;

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

                String resumen = (resumenTurnos.size() + 1) + ". BLL.Turno N° " + rs.getInt("idTurno") +
                        " - BLL.Paciente: " + nombrePaciente +
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
public void atenderTurnoIndividual() {
    try {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT t.idTurno, t.fecha, t.especialidad, t.estado, t.paciente_id, u.nombre " +
                        "FROM turnos t " +
                        "JOIN pacientes p ON t.paciente_id = p.usuario_id " +
                        "JOIN usuarios u ON p.usuario_id = u.idUsuario " +
                        "WHERE t.medico_id = ? AND t.estado = 'pendiente'"
        );
        stmt.setInt(1, this.getIdUsuario());
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

            String linea = (opcionesTurnos.size() + 1) + ". BLL.Turno N° " + idTurno +
                    " - BLL.Paciente: " + nombrePaciente + " - " + especialidad + " - " + fecha;

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


        // actualiza el estado del turno a atendido en la base de datos
        PreparedStatement updateTurno = con.prepareStatement(
                "UPDATE turnos SET estado = 'atendido' WHERE idTurno = ?"
        );
        updateTurno.setInt(1, turnoId);
        updateTurno.executeUpdate();

        String tipoEntrada = JOptionPane.showInputDialog("Ingrese el tipo de consulta:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la atención:");

        //inserta la entrada en entrada_historial
        PreparedStatement insertEntrada = con.prepareStatement(
                "INSERT INTO entradas_historial (turno_asociado_id, paciente_id, medico_responsable_id,tipoEntrada, descripcionDetallada) VALUES (?, ?, ?, ?, ?)"
        );
        insertEntrada.setInt(1, turnoId);
        insertEntrada.setInt(2, pacienteId);
        insertEntrada.setInt(3, this.getIdUsuario());
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
                    atenderTurnoIndividual();
                    break;
                case 2:
                    //cancelarTurno();
                    JOptionPane.showMessageDialog(null,"Cancelar BLL.Turno: ");
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
