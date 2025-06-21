package BLL;


import java.sql.Date;
import java.sql.Timestamp;

public class Turno {
    private int idTurno;
    private Paciente Paciente;
    private Medico medico;
    private Timestamp fecha;
    private String estado;
    private String especialidad;


    public Turno(int idTurno, Paciente paciente, Medico medico, Timestamp fecha, String estado, String especialidad) {
        this.idTurno = idTurno;
        this.Paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.estado = estado;
        this.especialidad = especialidad;
    }

    public Turno(int idTurno, Paciente paciente, Medico medico, Timestamp fecha, String estado) {
        this.idTurno = idTurno;
        this.Paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.estado = estado;
    }


    public Turno(){

    }

    public Turno(int idTurno, Paciente paciente, Medico medico, Date fecha, String estado) {
    }

    // Getters y Setters
    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public Paciente getPaciente() {
        return Paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.Paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }


    // @Override
    // public String toString() {
    //     return "Turno{" +
    //             "idTurno=" + idTurno +
    //             ", Paciente=" + Paciente.getNombre() +
    //             ", medico=" + medico.getNombre() +
    //             ", fecha=" + fecha +
    //             ", estado='" + estado + '\'' +
    //             ", especialidad='" + especialidad + '\'' +
    //             '}';
    // }
}