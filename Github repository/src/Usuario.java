import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

public class Usuario {
    private String nombre;
    private String apellido;
    private String mail;
    private int dni;
    private String contrasenia;
    private Date fechaNacimiento;

<<<<<<< HEAD
<<<<<<< HEAD:Github repository/src/Persona.java
    public Persona(String nombre, String apellido, String mail,int dni, String contrasenia, Date fechaNacimiento) {
=======
    public Usuario(String nombre, String apellido, String mail, int dni, String contrasenia, LocalDate fechaNacimiento) {
>>>>>>> main:Github repository/src/Usuario.java
=======
    public Usuario(String nombre, String apellido, String mail, int dni, String contrasenia, LocalDate fechaNacimiento) {
>>>>>>> main
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

    public static Paciente login(String nombre, String password) {
        Paciente paciente = new Paciente();
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM paciente WHERE nombre = ? AND password = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2,paciente.encriptar(password));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo");
                paciente =  new Paciente(id, nombre, email, tipo, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }
}
