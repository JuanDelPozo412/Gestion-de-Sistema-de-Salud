public class Consulta {
    private int idConsulta;
    private Paciente paciente;
    private Medico medico;
    private String detallesConsulta;

    public Consulta(int idConsulta, Paciente paciente, Medico medico, String detallesConsulta) {
        this.idConsulta = idConsulta;
        this.paciente = paciente;
        this.medico = medico;
        this.detallesConsulta = detallesConsulta;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
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

    public String getDetallesConsulta() {
        return detallesConsulta;
    }

    public void setDetallesConsulta(String detallesConsulta) {
        this.detallesConsulta = detallesConsulta;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", detallesConsulta='" + detallesConsulta + '\'' +
                '}';
    }
}
