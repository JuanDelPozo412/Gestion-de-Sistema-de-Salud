import java.time.LocalDate;

public class Horario {
    private int idHorario;
    private LocalDate horario;
    private EstadoHorario estado;

    public Horario(int idHorario, LocalDate horario, EstadoHorario estado) {
        this.idHorario = idHorario;
        this.horario = horario;
        this.estado = estado;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
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
