import javax.swing.*;
import java.util.LinkedList;

public class Plan {
      private int nroPlan;
      private LinkedList<Medico> medicosPlan = new LinkedList<>();

    public int getNroPlan() {
        JOptionPane.showMessageDialog(null,"Funcion que muestra nro de plan");
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
