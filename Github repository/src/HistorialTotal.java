
import java.util.LinkedList;

public class HistorialTotal {
  private  LinkedList<Historial> historiales = new LinkedList<>();

    public HistorialTotal(LinkedList<Historial> historiales) {
        this.historiales = historiales;
    }

    public LinkedList<Historial> getHistoriales() {
        return historiales;
    }

    public void setHistoriales(LinkedList<Historial> historiales) {
        this.historiales = historiales;
    }

    @Override
    public String toString() {
        return "HistorialTotal{" +
                "historiales=" + historiales +
                '}';
    }
}
