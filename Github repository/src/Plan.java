import javax.swing.*;
import java.util.LinkedList;

public class Plan {
    private int idPlan;
      private int nroPlan;
      private LinkedList<Medico> medicosPlan = new LinkedList<>();

    public Plan(int idPlan, int nroPlan, LinkedList<Medico> medicosPlan) {
        this.idPlan = idPlan;
        this.nroPlan = nroPlan;
        this.medicosPlan = medicosPlan;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getNroPlan() {
        return nroPlan;
    }

    public void setNroPlan(int nroPlan) {
        this.nroPlan = nroPlan;
    }

    public LinkedList<Medico> getMedicosPlan() {
        JOptionPane.showMessageDialog(null,"Funcion que muestra los medicos disponibles");
        return medicosPlan;
    }

    public void setMedicosPlan(LinkedList<Medico> medicosPlan) {
        JOptionPane.showMessageDialog(null,"Funcion para editar la lista de medicos");
        this.medicosPlan = medicosPlan;

    }

    @Override
    public String toString() {
        return "Plan{" +
                "nroPlan=" + nroPlan +
                ", medicosPlan=" + medicosPlan +
                '}';
    }
}
