package BLL;
import java.sql.Timestamp;

public class Turno {
    private int idTurno;
    private Paciente Paciente;
    private Medico medico;
    private Timestamp fecha;
    private String estado;

    public Turno(int idTurno, Paciente paciente, Medico medico, Timestamp fecha, String estado) {
        this.idTurno = idTurno;
        Paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.estado = estado;
    }
    public Turno(){

    }

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
        Paciente = paciente;
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
}
