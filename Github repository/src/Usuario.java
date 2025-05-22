import DLL.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;

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

//
//    public static Usuario login(String nombre, String password) {
//        Usuario usuario = new Usuario();
//
//        try {
//            PreparedStatement stmt = con.prepareStatement(
//                    "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?"
//            );
//            stmt.setString(1, nombre);
//            stmt.setString(2, password);
//
//            ResultSet rs = stmt.executeQuery();
//            int id = 0;
//            String email = "";
//            String tipo = "";
//
//            if (rs.next()) {
//                id = rs.getInt("idUsuario");
//                email = rs.getString("mail");
//                tipo = rs.getString("tipoUsuario");
//                usuario = new Usuario(id, email, tipo);
//            }
//            System.out.println("Tipo de usuario recuperado: " + usuario.getTipoUsuario());
//
//            switch (usuario.getTipoUsuario()) {
//                case "administradores":
//
//                    stmt = con.prepareStatement(
//                            "SELECT * FROM `administradores` WHERE  `usuario_id` = ?"
//                    );
//                    stmt.setInt(1, usuario.getIdUsuario());
//                    rs = stmt.executeQuery();
//
//
//                    if (rs.next()) {
//                        int cargo = rs.getInt("cargo");
//                        usuario = new Administrador(nombre, email, tipo, password, cargo);
//
//                    }
//
//
//                    break;
//
//                case "medicos":
//                    stmt = con.prepareStatement(
//                            "SELECT * FROM `medicos` WHERE  `usuario_id` = ?"
//                    );
//                    stmt.setInt(1, usuario.getIdUsuario());
//                    rs = stmt.executeQuery();
//
//                    if (rs.next()) {
//                        int especialidad = rs.getInt("especialidad");
////                        Medico usuariomedico = new Medico(nombre, email, tipo, password, especialidad);
//
//                    }
//                    break;
//
//                case "pacientes":
//                    stmt = con.prepareStatement(
//                            "SELECT * FROM `pacientes` WHERE  `usuario_id` = ?"
//                    );
//                    stmt.setInt(1, usuario.getIdUsuario());
//                    rs = stmt.executeQuery();
//
//                    if (rs.next()) {
//                        int planId = rs.getInt("plan_id");
//                        Paciente usuarioPaciente = new Paciente(planId ,nombre, email, tipo, password);
//
//                    }
//                    break;
//
//                default:
//
//                    break;
//
//
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return usuario;
//    }
public static Usuario login(String nombre, String password) {
    try {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?"
        );
        stmt.setString(1, nombre);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("idUsuario");
            String apellido = rs.getString("apellido");
            String mail = rs.getString("mail");
            String dni = rs.getString("dni");
            Date fechaNacimiento = rs.getDate("fechaNacimiento");
            String tipo = rs.getString("tipoUsuario");
            switch (tipo) {
                case "paciente":
                    PreparedStatement psPaciente = con.prepareStatement(
                            "SELECT * FROM pacientes WHERE usuario_id = ?"
                    );
                    psPaciente.setInt(1, id);
                    ResultSet rsPaciente = psPaciente.executeQuery();

                    if (rsPaciente.next()) {
                        int planId = rsPaciente.getInt("plan_id");
                        return new Paciente(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, null, null, planId);
                    }
                    break;

                case "medico":
                    PreparedStatement psMedico = con.prepareStatement(
                            "SELECT * FROM medicos WHERE usuario_id = ?"
                    );
                    psMedico.setInt(1, id);
                    ResultSet rsMedico = psMedico.executeQuery();

                    if (rsMedico.next()) {
                        String especialidad = rsMedico.getString("especialidad");
                        return new Medico(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, especialidad);
                    }
                    break;

                case "administrador":
                    PreparedStatement psAdmin = con.prepareStatement(
                            "SELECT * FROM administradores WHERE usuario_id = ?"
                    );
                    psAdmin.setInt(1, id);
                    ResultSet rsAdmin = psAdmin.executeQuery();

                    if (rsAdmin.next()) {
                        String cargo = rsAdmin.getString("cargo");
                        return new Administrador(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, cargo);
                    }
                    break;
            }
        }

    } catch (Exception e) {
        System.out.println("Error en login: " + e.getMessage());
    }
    return null;
}


//    int id = rs.getInt("idUsuario");
//    String apellido = rs.getString("apellido");
//    String mail = rs.getString("mail");
//    String dni = rs.getString("dni");
//    Date fechaNacimiento = rs.getDate("fechaNacimiento");
//    String tipo = rs.getString("tipoUsuario");
//
//    public static void agregarUsuario(Usuario usuario) {
//        try {
//            PreparedStatement statement = con.prepareStatement(
//                    "INSERT INTO `usuarios`(`idUsuario`, `nombre`, `apellido`, `mail`, `dni`, `contrasenia`, `fechaNacimiento`, `tipoUsuario`) VALUES (?,?,?,?,?,?,?,?)"
//            );
//
//            statement.setInt(1, usuario.getIdUsuario());
//            statement.setString(2, usuario.getNombre());
//            statement.setString(3, usuario.getApellido());
//            statement.setString(4, usuario.getMail());
//            statement.setString(5, usuario.getDni());
//            statement.setString(6, usuario.getContrasenia());
//            statement.setDate(7, (java.sql.Date) usuario.getFechaNacimiento());
//            statement.setString(8, usuario.getTipoUsuario());
//
//
//            int filas = statement.executeUpdate();
//            if (filas > 0) {
//                System.out.println("Usuario agregado correctamente.");
//            }
//
//            String tipo = usuario.getTipoUsuario();
//
//            PreparedStatement insertTablaTipoUsuario = null;
//            switch (tipo) {
//                case "paciente":
//                    insertTablaTipoUsuario = con.prepareStatement("INSERT INTO `pacientes`(`idUsuario`) VALUES (?)" );
//                    break;
//                case "medico":
//                    insertTablaTipoUsuario = con.prepareStatement("INSERT INTO `medicos`(`idUsuario`) VALUES (?)");
//                    break;
//                case "administrador":
//                    insertTablaTipoUsuario = con.prepareStatement("INSERT INTO `administradores`(`idUsuario`) VALUES (?)");
//                    break;
//                default:
//
//                    System.out.println("default");
//            }
//
//            if (insertTablaTipoUsuario != null) {
//                insertTablaTipoUsuario.setInt(1, usuario.getIdUsuario());
//                insertTablaTipoUsuario.executeUpdate();
//                System.out.println("ID insertado en tabla secundaria: " + tipo);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public static void agregarUsuario(Usuario usuario) {
    try {

        String sqlInsertUsuario = "INSERT INTO usuarios (nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getApellido());
        statement.setString(3, usuario.getMail());
        statement.setString(4, usuario.getDni());
        statement.setString(5, usuario.getContrasenia());
        java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
        statement.setDate(6, sqlDate);

        statement.setString(7, usuario.getTipoUsuario());

        int filas = statement.executeUpdate();

        if (filas > 0) {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                usuario.setIdUsuario(idGenerado);
                System.out.println("Usuario agregado con ID: " + idGenerado);
            }
        } else {
            System.out.println("No se pudo insertar el usuario.");
            return;
        }


        String tipo = usuario.getTipoUsuario();
        PreparedStatement insertTabla = null;
        switch (tipo) {
            case "paciente":
                if (usuario instanceof Paciente) {
                    int planId = ((Paciente) usuario).getPlanId();
                    insertTabla = con.prepareStatement("INSERT INTO pacientes (usuario_id, plan_id) VALUES (?, ?)");
                    insertTabla.setInt(1, usuario.getIdUsuario());
                    insertTabla.setInt(2, planId);
                } else {
                    System.out.println("el objeto no es una instancia de Paciente.");
                    return;
                }
                break;

            case "medico":
                if (usuario instanceof Medico) {
                    String especialidad = ((Medico) usuario).getEspecialidad();
                    insertTabla = con.prepareStatement("INSERT INTO medicos (usuario_id, especialidad) VALUES (?, ?)");
                    insertTabla.setInt(1, usuario.getIdUsuario());
                    insertTabla.setString(2, especialidad);
                } else {
                    System.out.println("el objeto no es una instancia de Medico.");
                    return;
                }
                break;

            case "administrador":
                if (usuario instanceof Administrador) {
                    String cargo = ((Administrador) usuario).getCargo();
                    insertTabla = con.prepareStatement("INSERT INTO administradores (usuario_id, cargo) VALUES (?, ?)");
                    insertTabla.setInt(1, usuario.getIdUsuario());
                    insertTabla.setString(2, cargo);
                } else {
                    System.out.println("el objeto no es una instancia de Administrador.");
                    return;
                }
                break;
        }

        if (insertTabla != null) {
            insertTabla.executeUpdate();
            System.out.println("Insertado correctamente en tabla tipo " + tipo);
        }

    } catch (Exception e) {
        System.out.println("error en agregarUsuario: " + e.getMessage());
    }
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
        }else {
            JOptionPane.showMessageDialog(null, "usuario ya creado");
        }


    }
    public static LinkedList<Usuario> mostrarUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String mail = rs.getString("mail");
                String dni = rs.getString("dni");
                String contrasenia = rs.getString("contrasenia");
                Date fechaNacimiento = rs.getDate("fechaNacimiento");
                String tipo = rs.getString("tipoUsuario");

                usuarios.add(new Usuario(id, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }



}



