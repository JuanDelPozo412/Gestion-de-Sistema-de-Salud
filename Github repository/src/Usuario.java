import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
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
        return "Usuario{" +
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

    //    public void login(String mailIngresado, String contraseniaIngresada) {
//        if (mailIngresado.equals(mail) && contraseniaIngresada.equals(contrasenia)){
//            JOptionPane.showMessageDialog(null, "Puede Ingresar :)");
//        }else{
//            JOptionPane.showMessageDialog(null, "No puede ingresar :( ");
//        }
//    }
//


    public static Usuario login(String nombre, String password) {
        Usuario usuario = new Usuario();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            int id = 0;
            String email = "";
            String tipo = "";

            if (rs.next()) {
                id = rs.getInt("idUsuario");
                email = rs.getString("mail");
                tipo = rs.getString("tipoUsuario");
                usuario = new Usuario(id, email, tipo);
            }
            switch (usuario.getTipoUsuario()) {
                case "administradores":
                    stmt = con.prepareStatement(
                            "SELECT * FROM `administradores` WHERE  `usuario_id` = ?"
                    );
                    stmt.setInt(1, usuario.getIdUsuario());
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int cargo = rs.getInt("cargo");
                        Administrador usuarioadmin = new Administrador(nombre, email, tipo, password, cargo);

                    }
                    break;

                case "medicos":
                    stmt = con.prepareStatement(
                            "SELECT * FROM `administradores` WHERE  `usuario_id` = ?"
                    );
                    stmt.setInt(1, usuario.getIdUsuario());
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int cargo = rs.getInt("cargo");
                        Administrador usuarioadmin = new Administrador(nombre, email, tipo, password, cargo);

                    }
                    break;
                    
                case "pacientes":
                    stmt = con.prepareStatement(
                            "SELECT * FROM `administradores` WHERE  `usuario_id` = ?"
                    );
                    stmt.setInt(1, usuario.getIdUsuario());
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int cargo = rs.getInt("cargo");
                        Administrador usuarioadmin = new Administrador(nombre, email, tipo, password, cargo);

                    }
                    break;

                default:

                    break;


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }
}



