package BLL;

import DLL.Conexion;
import DLL.ControllerUsuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;

import static DLL.ControllerUsuario.agregarUsuario;
import static DLL.ControllerUsuario.mostrarUsuarios;


public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String mail;
    private String dni;
    private String contrasenia;
    private Date fechaNacimiento;
    private String tipoUsuario;

    private static Connection con = Conexion.getInstance().getConnection();

    public Usuario(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.dni = dni;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(int idUsuario, String mail, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.mail = mail;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "BLL.Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", dni='" + dni + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }

    public static Usuario login(String nombre, String password) {
        Usuario usuario = null;
        if (nombre.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese los datos correctamente");
        } else {
            usuario = ControllerUsuario.login(nombre, password);
        }
        return usuario;
    }

    public static void RegistrarUsuario(Usuario nuevo) {

        LinkedList<Usuario> existentes = mostrarUsuarios();
        boolean flag = true;
        for (Usuario existente : existentes) {
            if (existente.getMail().equals(nuevo.getMail())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            agregarUsuario(nuevo);
        } else {
            JOptionPane.showMessageDialog(null, "usuario ya creado");
        }


    }


}



