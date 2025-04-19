
import javax.swing.*;
import java.util.LinkedList;

public class HistorialTotal {

    private static HistorialTotal instancia;
    private LinkedList<Historial> historiales;


    private HistorialTotal() {
        historiales = new LinkedList<>();
    }

    // la funcion  hace que solamente haya una instancia sola de la clase, y la retorna;
    public static HistorialTotal getInstance() {
        if (instancia == null) {
            instancia = new HistorialTotal();
        }
        return instancia;
    }

    //Agregar historial
    public void agregarHistorial(Historial historial) {
        JOptionPane.showMessageDialog(null, "Funcion de agregar historial");

        //historiales.add(historial);
    }


    public LinkedList<Historial> getHistoriales() {
        JOptionPane.showMessageDialog(null,"Funcion que trae la lista de historiales");
        return historiales;
    }

   //Funcion editar historial
    public void setHistoriales(LinkedList<Historial> historiales) {
        JOptionPane.showMessageDialog(null,"Funcion para editar historial");
        this.historiales = historiales;
    }


    @Override
    public String toString() {
        return "HistorialTotal{" +
                "historiales=" + historiales +
                '}';
    }
}
