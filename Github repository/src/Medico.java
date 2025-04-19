import java.time.LocalDate;

public class Medico extends Persona {
    private String especialidad;
    private int matricula;

    public Medico(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaNacimiento, String especialidad, int matricula) {
        super(nombre, apellido, mail, contrasenia, fechaNacimiento);
        this.especialidad = especialidad;
        this.matricula = matricula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
                ", especialidad='" + especialidad + '\'' +
                ", matricula=" + matricula +
                '}';
    }

    public void login() {
        System.out.println("sesion iniciada como médico.");
    }
}
