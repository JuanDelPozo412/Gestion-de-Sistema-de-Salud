import java.util.Random;

public class Credencial {
    private int nroSocio;
    private int plan;
    private int token;
    private static int nroAleatorio;
    private Paciente paciente;



    public Credencial(int nroSocio, int plan, int token) {
        this.nroSocio = nroAleatorio;
        this.plan = plan;
        this.token = token;
        nroAleatorio++;
    }

    public int getNroSocio() {
        return nroSocio;
    }

    public void setNroSocio(int nroSocio) {
        this.nroSocio = nroSocio;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
    public int randomToken(){
       int tokenAleatorio = (int)(Math.random() * 900) + 100;
        return tokenAleatorio;
    };

    @Override
    public String toString() {
        return "Credencial{" +
                "nroSocio=" + nroSocio +
                ", plan=" + plan +
                ", token=" + token +
                '}';
    }
}
