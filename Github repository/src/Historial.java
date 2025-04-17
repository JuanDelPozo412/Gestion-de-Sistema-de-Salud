import java.util.LinkedList;

public class Historial {
    private Paciente paciente;
    private LinkedList<Turno>personas = new LinkedList<>();

    public Historial(Paciente paciente, LinkedList<Turno> personas) {
        this.paciente = paciente;
        this.personas = personas;
    }

    public LinkedList<Turno> getPersonas() {
        return personas;
    }

    public void setPersonas(LinkedList<Turno> personas) {
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "paciente=" + paciente +
                ", personas=" + personas +
                '}';
    }
}
