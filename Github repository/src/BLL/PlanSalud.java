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

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "PlanSalud{" +
                "planId=" + planId +
                ", nombrePlan='" + nombrePlan + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
