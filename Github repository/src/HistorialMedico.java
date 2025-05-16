import java.util.ArrayList;
import java.util.List;
public class HistorialMedico {
    private List<EntradaHistorial> entradas;

    public HistorialMedico() {
        this.entradas = new ArrayList<>();
    }

    public List<EntradaHistorial> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<EntradaHistorial> entradas) {
        this.entradas = entradas;
    }

    @Override
    public String toString() {
        return "HistorialMedico{" +
                "entradas=" + entradas +
                '}';
    }
}