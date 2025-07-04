package BLL;

import DLL.ControllerPaciente;
import DLL.ControllerUsuario;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paciente extends Usuario {
    //Atributos
    private HistorialMedico historialMedico;
    private List<Turno> misTurnos;
    private int planId;


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
    }
    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, HistorialMedico historialMedico, List<Turno> misTurnos, int planId, byte[] fotoPerfil) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario, fotoPerfil);
        this.historialMedico = historialMedico;
        this.misTurnos = misTurnos;
        this.planId = planId;
    }
    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, byte[] fotoPerfil, int planId) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario, fotoPerfil);
        this.planId = planId;
    }
    public Paciente(int idUsuario, String nombre, String apellido, String mail,
                    String dni, String contrasenia, Date fechaNacimiento,
                    String tipoUsuario, int planId) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.planId = planId;
    }

    public Paciente() {

    }

    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, java.sql.Date fechaNacimiento, String tipoUsuario, int planid) {
    }

    ;
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


    public String obtenerPlan() {
        return ControllerPaciente.obtenerPlan(this);
    }

    public void verUltimoTurno() {
        ControllerPaciente.verUltimoTurno(this);
    }

    public String verHistorialMedico() {
        return ControllerPaciente.obtenerHistorial(this);
    }

    public void obtenerTurnos() {
        List<Turno> turnos = ControllerPaciente.obtenerTurnos(this);

        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenes turnos registrados");
        } else {
            String resultado = "";
            for (Turno t : turnos) {
                resultado += "ID: " + t.getIdTurno() + "\n";
                resultado += "Medico: " + t.getMedico().getNombre() + "\n";
                resultado += "Especialidad: " + t.getMedico().getEspecialidad() + "\n";
                resultado += "Fecha: " + t.getFecha() + "\n";
                resultado += "Estado: " + t.getEstado() + "\n";
                resultado += "---------------------------\n";
            }

            JOptionPane.showMessageDialog(null, resultado);
        }
    }

    public String editarPerfil() {
        if (this.getNombre().isEmpty() || this.getMail().isEmpty() || this.getContrasenia().isEmpty()) {
            return "No se pudo editar";
        } else {
            return ControllerPaciente.actualizarPerfil(this);
        }
    }

    public List<PlanSalud> obtenerPlanes() {
        return ControllerPaciente.obtenerPlanes();
    }

    public void cancelarTurno(int idTurno) {
        ControllerPaciente.cancelarTurno(idTurno, this.getIdUsuario());
    }
    public String reservarTurno(String especialidad, String medicoSeleccionado, String fecha, String hora) {
        return ControllerPaciente.reservarTurnoDesdePantalla(this, especialidad, medicoSeleccionado, fecha, hora);
    }


    public boolean tieneTurno(String medicoSeleccionado, String fechaHora) {
        return ControllerPaciente.existeTurno(this.getIdUsuario(), medicoSeleccionado, fechaHora);
    }
    public List<Turno> obtenerTurnosFiltrados(String filtro) {
        List<Turno> turnos = ControllerPaciente.obtenerTurnos(this);
        List<Turno> filtrados = new ArrayList<>();

        for (Turno t : turnos) {
            if (filtro.equals("Todos") || t.getEstado().equalsIgnoreCase(filtro)) {
                filtrados.add(t);
            }
        }
        return filtrados;
    }
    public List<Turno> obtenerTodosMisTurnos() {
        return ControllerPaciente.obtenerTurnos(this);
    }
    public ArrayList<String> obtenerEspecialidades() {
        return ControllerPaciente.obtenerEspecialidades();
    }

    public ArrayList<String> obtenerMedicosPorEspecialidad(String especialidad) {
        return ControllerPaciente.obtenerMedicosPorEspecialidad(especialidad);
    }
    public void actualizarFotoPerfil(byte[] nuevaFoto) {
        this.setFotoPerfil(nuevaFoto);
        ControllerUsuario.actualizarFotoPerfil(this.getIdUsuario(), nuevaFoto);
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
                    //verPlan();
                    break;
                case 2:
                    obtenerTurnos();
                    break;
                case 3:
                    verUltimoTurno();
                    break;
                case 4:
                    verHistorialMedico();
                    break;
                case 5:
                    //reservarTurno();
                    break;
                case 6:
                    editarPerfil();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "¡Hasta lueguito, " + getNombre() + "!");
                    break;
            }
        } while (opcion != 7);
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