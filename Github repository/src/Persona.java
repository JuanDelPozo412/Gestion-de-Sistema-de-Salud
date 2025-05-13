import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

public class Persona {
    private String nombre;
    private String apellido;
    private String mail;
    private int dni;
    private String contrasenia;
    private Date fechaNacimiento;

    public Persona(String nombre, String apellido, String mail,int dni, String contrasenia, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.dni = dni;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
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
