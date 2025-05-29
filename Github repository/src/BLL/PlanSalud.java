package BLL;

public class PlanSalud {
    private int planId;
    private String nombrePlan;
    private String descripcion;

    public PlanSalud(int planId, String nombrePlan, String descripcion) {
        this.planId = planId;
        this.nombrePlan = nombrePlan;
        this.descripcion = descripcion;
    }

    public int getPlanId() {
        return planId;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombrePlan;
    }

    public String mostrarDatos() {
        return "ID: " + planId + "\nNombre: " + nombrePlan + "\nDescripcion: " + descripcion;
    }
}
