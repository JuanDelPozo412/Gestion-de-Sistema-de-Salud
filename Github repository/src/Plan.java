public class Plan {
    private int idPlan;
    private String nombre;
    private String descripcion;
    private double costoMensual;
    private String cobertura;

    public Plan() {}

    public Plan(String nombre, String descripcion, double costoMensual, String cobertura) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoMensual = costoMensual;
        this.cobertura = cobertura;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoMensual() {
        return costoMensual;
    }

    public void setCostoMensual(double costoMensual) {
        this.costoMensual = costoMensual;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "idPlan=" + idPlan +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoMensual=" + costoMensual +
                ", cobertura='" + cobertura + '\'' +
                '}';
    }
}
