package BLL;

import DLL.Conexion;
import DLL.ControllerPaciente;
import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paciente extends Usuario {
    //Atributos
    private HistorialMedico historialMedico;
    private List<Turno> misTurnos;
    private int planId;
    private static Connection con = Conexion.getInstance().getConnection();

    //Constructor


    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, HistorialMedico historialMedico, List<Turno> misTurnos, int planId) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.historialMedico = historialMedico;
        this.misTurnos = misTurnos;
        this.planId = planId;
    }

    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, PlanSalud planSalud) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.planId = planId;
    }

    public Paciente(int planId, String nombre, String email, String tipo, String password) {
        this.planId = planId;
    };
    //Get y Set

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public List<Turno> getMisTurnos() {
        return misTurnos;
    }

    public void setMisTurnos(List<Turno> misTurnos) {
        this.misTurnos = misTurnos;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void verPlan() {
        ControllerPaciente.verPlan(this);
    }

    public void verUltimoTurno() {
        ControllerPaciente.verUltimoTurno(this);
    }
    public void verHistorialMedico() {
        ControllerPaciente.verHistorialMedico(this);
    }
    public void verTodosLosTurnos() {
        ControllerPaciente.verTodosLosTurnos(this);
    }
    public void reservarTurno() {
        ControllerPaciente.reservarTurno(this);
    }
    public void editarPerfil() {
        String nuevoNombre = JOptionPane.showInputDialog(null, "Nombre actual: " + getNombre() + "\nNuevo nombre:", getNombre());
        String nuevoApellido = JOptionPane.showInputDialog(null, "Apellido actual: " + getApellido() + "\nNuevo apellido:", getApellido());
        String nuevoMail = JOptionPane.showInputDialog(null, "Email actual: " + getMail() + "\nNuevo email:", getMail());
        String nuevaContrasenia = JOptionPane.showInputDialog(null, "Nueva contraseña:");

        if (nuevoNombre != null && nuevoApellido != null && nuevoMail != null && nuevaContrasenia != null) {
            this.setNombre(nuevoNombre);
            this.setApellido(nuevoApellido);
            this.setMail(nuevoMail);
            this.setContrasenia(nuevaContrasenia);

            ControllerPaciente.actualizarPerfil(this);
        } else {
            JOptionPane.showMessageDialog(null, "Cancelar");
        }
    }




    public void mostrarMenuPaciente() {
        String[] opciones = {"Info Personal", "Ver Plan de Salud", "Ver Todos los Turnos", "Ver Ultimo Turno", "Ver Historial", "Reservar Turno", "Editar Perfil", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "¿Que desea hacer, " + getNombre() + "?", "Menu Paciente",
                    0,
                    0,
                    null,
                    opciones,
                    opciones[0]);
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Nombre: " + getNombre() + "\nApellido: " + getApellido() + "\nDNI: " + getDni() + "\nNacimiento: " + getFechaNacimiento());
                    break;
                case 1:
                    verPlan();
                    break;
                case 2:
                    verTodosLosTurnos();
                    break;
                case 3:
                    verUltimoTurno();
                    break;
                case 4:
                    verHistorialMedico();
                    break;
                case 5:
                    reservarTurno();
                    break;
                case 6:
                    editarPerfil();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "¡Hasta lueguito, " + getNombre() + "!");
                    break;
            }
        } while (opcion != 6);
    }

//to String

    @Override
    public String toString() {
        return "Paciente{" +
                "historialMedico=" + historialMedico +
                ", misTurnos=" + misTurnos +
                ", planSalud=" + planId +
                '}';
    }





}