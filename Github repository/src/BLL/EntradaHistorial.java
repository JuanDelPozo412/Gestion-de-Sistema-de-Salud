package BLL;

import java.sql.Date;
public class EntradaHistorial {
    private int entradaId;
    private Date fechaHoraEntrada;
    private String tipoEntrada;
    private String descripcionDetallada;
    private Medico medicoResponsable;
    private Turno turnoAsociado;

    public EntradaHistorial(int entradaId, Date fechaHoraEntrada, String tipoEntrada, String descripcionDetallada, Medico medicoResponsable, Turno turnoAsociado) {
        this.entradaId = entradaId;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.tipoEntrada = tipoEntrada;
        this.descripcionDetallada = descripcionDetallada;
        this.medicoResponsable = medicoResponsable;
        this.turnoAsociado = turnoAsociado;
    }

    public int getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(int entradaId) {
        this.entradaId = entradaId;
    }

    public Date getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Date fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getDescripcionDetallada() {
        return descripcionDetallada;
    }

    public void setDescripcionDetallada(String descripcionDetallada) {
        this.descripcionDetallada = descripcionDetallada;
    }

    public Medico getMedicoResponsable() {
        return medicoResponsable;
    }

    public void setMedicoResponsable(Medico medicoResponsable) {
        this.medicoResponsable = medicoResponsable;
    }

    public Turno getTurnoAsociado() {
        return turnoAsociado;
    }

    public void setTurnoAsociado(Turno turnoAsociado) {
        this.turnoAsociado = turnoAsociado;
    }

    @Override
    public String toString() {
        return "BLL.EntradaHistorial{" +
                "entradaId=" + entradaId +
                ", fechaHoraEntrada=" + fechaHoraEntrada +
                ", tipoEntrada='" + tipoEntrada + '\'' +
                ", descripcionDetallada='" + descripcionDetallada + '\'' +
                ", medicoResponsable=" + medicoResponsable +
                ", turnoAsociado=" + turnoAsociado +
                '}';
    }
}
