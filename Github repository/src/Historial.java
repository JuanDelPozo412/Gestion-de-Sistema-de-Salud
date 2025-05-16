import java.time.LocalDateTime;
public class Historial {
    private int idHistorial;
    private LocalDateTime fechaActualizacion;
    private Paciente paciente;
    private Medico medico;

    public Historial(Paciente paciente, LinkedList<Turno> turnosHistorial) {
        this.paciente = paciente;
        this.turnosHistorial = turnosHistorial;
    }

    private Historial() {
        turnosHistorial = new LinkedList<>();
    }

    // la funcion  hace que solamente haya una instancia sola de la clase, y la retorna;
    public static Historial getInstance() {
        if (instancia == null) {
            instancia = new Historial();
        }
        return instancia;
    }


    //Funcion para agregar turnos al historial
    public void agregarTurno(Turno turno) {
        JOptionPane.showMessageDialog(null, "Funcion de agregar historial");
        turnosHistorial.add(turno);

    }

    public LinkedList<Turno> getPersonas() {
        return turnosHistorial;
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
