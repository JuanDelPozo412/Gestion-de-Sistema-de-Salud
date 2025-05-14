import java.time.LocalDateTime;
public class Historial {
    private int idHistorial;
    private LocalDateTime fechaActualizacion;
    private Paciente paciente;
    private Medico medico;


    public Historial(int idHistorial, LocalDateTime fechaActualizacion, Paciente paciente, Medico medico) {
        this.idHistorial = idHistorial;
        this.fechaActualizacion = fechaActualizacion;
        this.paciente = paciente;
        this.medico = medico;

    }


    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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




}
