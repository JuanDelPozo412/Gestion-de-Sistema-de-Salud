import java.time.LocalDate;

public class Turno {
    //Atributos
    private EstadoTurno estadoTurno;
    private Paciente paciente;
    private Medico medico;
    private LocalDate fechaTurno;
    private int idTurno;
    private Consulta consulta;

    //constructor

    public Turno(EstadoTurno estadoTurno, Paciente paciente, Medico medico, LocalDate fechaTurno, int idTurno, Consulta consulta) {
        this.estadoTurno = estadoTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaTurno = fechaTurno;
        this.idTurno = idTurno;
        this.consulta = consulta;
    }


    //get y set


    public EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(EstadoTurno estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "estadoTurno=" + estadoTurno +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", fechaTurno=" + fechaTurno +
                ", idTurno=" + idTurno +
                ", consulta=" + consulta +
                '}';
    }
}
