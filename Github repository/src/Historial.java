import java.util.LinkedList;

public class Historial {
    private Paciente paciente;
    private LinkedList<Turno> turno = new LinkedList<>();

    public Historial(Paciente paciente, LinkedList<Turno> personas) {
        this.paciente = paciente;
        this.turno = personas;
    }

    public LinkedList<Turno> getPersonas() {
        return turno;
    }

    public void setPersonas(LinkedList<Turno> personas) {
        this.turno = personas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LinkedList<Turno> getTurno() {
        return turno;
    }

    public void setTurno(LinkedList<Turno> turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "paciente=" + paciente +
                ", turno=" + turno +
                '}';
    }
}
