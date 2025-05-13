import javax.swing.*;
import java.time.LocalDate;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String dni;
    private String contrasenia;
    private LocalDate fechaNacimiento;
    private int idUsuario;

    public Usuario(String nombre, String apellido, String email, String dni, String contrasenia, LocalDate fechaNacimiento, int idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void login(String mailIngresado, String contraseniaIngresada) {
        if (mailIngresado.equals(email) && contraseniaIngresada.equals(contrasenia)){
            JOptionPane.showMessageDialog(null, "Puede Ingresar :)");
        }else{
            JOptionPane.showMessageDialog(null, "No puede ingresar :( ");
        }
    }

}
