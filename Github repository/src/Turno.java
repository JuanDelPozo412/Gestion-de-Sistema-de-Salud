import java.sql.Date;
public class Turno {
    private int idTurno;
    private Paciente Paciente;
    private Medico medico;
    private Date fecha;
    private String estado;

    public Turno(int idTurno, Paciente paciente, Medico medico, Date fecha, String estado) {
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



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
