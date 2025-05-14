import com.mysql.jdbc.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Turno {
    //Atributos
    private int idTurno;
    private int idPaciente;
    private int idMedico;
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private EstadoTurno estado;
    private LocalDateTime fechaReservacion;
    private LocalDateTime fechaCancelacion;

    private static Connection con = Conexion.getInstance().getConnection();

    public Turno() {

    }

    //constructor

    public Turno(int idTurno, int idPaciente, int idMedico, LocalDate fechaTurno, LocalTime horaTurno, EstadoTurno estado, LocalDateTime fechaReservacion, LocalDateTime fechaCancelacion) {
        this.idTurno = idTurno;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
        this.estado = estado;
        this.fechaReservacion = fechaReservacion;
        this.fechaCancelacion = fechaCancelacion;
    }


    //get y set


    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public LocalTime getHoraTurno() {
        return horaTurno;
    }

    public void setHoraTurno(LocalTime horaTurno) {
        this.horaTurno = horaTurno;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(LocalDateTime fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "idTurno=" + idTurno +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", fechaTurno=" + fechaTurno +
                ", horaTurno=" + horaTurno +
                ", estado=" + estado +
                ", fechaReservacion=" + fechaReservacion +
                ", fechaCancelacion=" + fechaCancelacion +
                '}';
    }
}

