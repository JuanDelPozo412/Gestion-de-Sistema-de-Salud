import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Historial {
    private int idHistorial;
    private LocalDateTime fechaActualizacion;
    private Paciente paciente;
    private Medico medico;
    private List<Consulta> consultas;

    public Historial(int idHistorial, LocalDateTime fechaActualizacion, Paciente paciente, Medico medico, List<Consulta> consultas) {
        this.idHistorial = idHistorial;
        this.fechaActualizacion = fechaActualizacion;
        this.paciente = paciente;
        this.medico = medico;
        this.consultas = consultas;
    }

    // Getters / setters básicos


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

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void agregarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
        this.fechaActualizacion = LocalDateTime.now();
    }
}
