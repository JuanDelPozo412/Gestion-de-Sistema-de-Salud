import javax.swing.*;
import java.time.LocalDate;

public class Persona {
    private String nombre;
    private String apellido;
    private String mail;
    private String contrasenia;
    private LocalDate fechaNacimiento;

    public Persona(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public void login(String mailIngresado, String contraseniaIngresada) {
        if (mailIngresado.equals(mail) && contraseniaIngresada.equals(contrasenia)){
            JOptionPane.showMessageDialog(null, "Puede Ingresar :)");
        }else{
            JOptionPane.showMessageDialog(null, "No puede ingresar :( ");
        }
    }

}
