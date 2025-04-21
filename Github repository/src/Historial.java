import javax.swing.*;
import java.util.LinkedList;

public class Historial {
    private Paciente paciente;
    private LinkedList<Turno> turno = new LinkedList<>();
    private static Historial instancia;

    public Historial(Paciente paciente, LinkedList<Turno> personas) {
        this.paciente = paciente;
        this.turno = personas;
    }
    private Historial() {
        turno = new LinkedList<>();
    }
    // la funcion  hace que solamente haya una instancia sola de la clase, y la retorna;
    public static Historial getInstance() {
        if (instancia == null) {
            instancia = new Historial();
        }
        return instancia;
    }


    //Funcion para agregar turnos al historial
    public void agregarTurno(turno Turno){
        JOptionPane.showMessageDialog(null,"Funcion de agregar historial");
        turno.add(Turno);

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
