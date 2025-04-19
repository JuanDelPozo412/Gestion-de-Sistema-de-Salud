import java.time.LocalDate;

public class Medico extends Persona {
    private Especialidades especialidades;
    private int matricula;

    public Medico(String nombre, String apellido, String mail, int dni, String contrasenia, LocalDate fechaNacimiento, Especialidades especialidades, int matricula) {
        super(nombre, apellido, mail, dni, contrasenia, fechaNacimiento);
        this.especialidades = especialidades;
        this.matricula = matricula;
    }

    public Especialidades getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Especialidades especialidades) {
        this.especialidades = especialidades;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nombre=" + getNombre() +
                ", apellido=" + getApellido() +
                ", especialidad='" + especialidades + '\'' +
                ", matricula=" + matricula +
                '}';
    }

    public void login() {
        System.out.println("sesion iniciada como médico.");
    }
}
