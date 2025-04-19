import java.time.LocalDate;

public class Horario {
    private LocalDate horario;
    private EstadoHorario estado;

    public Horario(LocalDate horario, EstadoHorario estado) {
        this.horario = horario;
        this.estado = estado;
    }

    public LocalDate getHorario() {
        return horario;
    }

    public void setHorario(LocalDate horario) {
        this.horario = horario;
    }

    public EstadoHorario getEstado() {
        return estado;
    }

    public void setEstado(EstadoHorario estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "horario=" + horario +
                ", estado=" + estado +
                '}';
    }
}
