import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paciente extends Usuario { //extends Persona
    //Atributos
    private HistorialMedico historialMedico;
    private List<Turno> misTurnos;
    private int planId;
    private static Connection con = Conexion.getInstance().getConnection();

    //Constructor


    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, HistorialMedico historialMedico, List<Turno> misTurnos, int planId) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.historialMedico = historialMedico;
        this.misTurnos = misTurnos;
        this.planId = planId;
    }

    public Paciente(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, PlanSalud planSalud) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.planId = planId;
    }

    public Paciente(int planId, String nombre, String email, String tipo, String password) {
        this.planId = planId;
    };
    //Get y Set

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public List<Turno> getMisTurnos() {
        return misTurnos;
    }

    public void setMisTurnos(List<Turno> misTurnos) {
        this.misTurnos = misTurnos;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void verPlan(){
        try{
            PreparedStatement  stmt = con.prepareStatement(
                    "SELECT nombrePlan, descripcion FROM planes_salud WHERE planId= ?"
            );

            stmt.setInt(1,this.planId);
            ResultSet rs= stmt.executeQuery();

            if (rs.next()){
                String nombre = rs.getString("nombrePlan");
                String descripcion = rs.getString("descripcion");
                System.out.println("Plan de salud: " + nombre);
                System.out.println("Descripcion:" + descripcion);
            }else{
                System.out.println("No se encontro un plan con ese ID" + this.planId);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el plan: " + e.getMessage());
        }
    }
    public void verUltimoTurno() {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT idTurno, especialidad, fecha, estado " + "FROM turnos " + "WHERE paciente_id = ? " + "ORDER BY fecha DESC " + "LIMIT 1" // esto es para que traiga solo el ultimo turno
            );
            stmt.setInt(1, this.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ultimo turno:");
                System.out.println("Turno "   + rs.getInt("idTurno") + "Especialidad: " + rs.getString("especialidad") +
                        ",Fecha: " + rs.getDate("fecha") + ",Estado: " + rs.getString("estado")
                );
            } else {
                System.out.println("No tenes turnos antiguos");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el ultimo turno: " + e.getMessage());
        }
    }
    private String obtenerNombreMedicoPorId(int medicoId) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT nombre FROM medicos WHERE usuario_id = ?"
            );
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener nombre del medico: " + e.getMessage());
        }
        return "No hay medico";
    }
    public void verHistorialMedico(int pacienteId) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM entradas_historial WHERE paciente_id = ?"
            );
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int entradaId = rs.getInt("entradaId");
                Date fechaHora = rs.getDate("fechaHoraEntrada");
                String tipo = rs.getString("tipoEntrada");
                String descripcion = rs.getString("descripcionDetallada");
                int medicoId = rs.getInt("medico_responsable_id");
                int turnoId = rs.getInt("turno_asociado_id");


                String nombreMedico = obtenerNombreMedicoPorId(medicoId);
                System.out.println("Entrada ID: " + entradaId);
                System.out.println("Fecha: " + fechaHora);
                System.out.println("Tipo: " + tipo);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Medico Responsable: " + nombreMedico);
                System.out.println("ID Turno Asociado: " + turnoId);
            }


        } catch (Exception e) {
            System.out.println("Error al obtener historial: " + e.getMessage());
        }
    }


    public void reservarTurno() {
        try {
            PreparedStatement stmtEspecialidad = con.prepareStatement(
                    // esto lo use para que traiga solo 1 especialidad y no duplicadas
                    "SELECT DISTINCT especialidad FROM medicos");

            ResultSet rsEspecialidad = stmtEspecialidad.executeQuery();

            // array para guardar las especialidades encontradas
            ArrayList<String> especialidades = new ArrayList<>();
            while (rsEspecialidad.next()) {
                especialidades.add(rsEspecialidad.getString("especialidad"));
            }
            // use un tooarray para mostrar todas las especialidades
            String[] opcionesEsp = especialidades.toArray(new String[0]);
            String especialidadSeleccionada = (String) JOptionPane.showInputDialog(null,
                    "Seleccione especialidad:",
                    "Especialidad",
                    0,
                    null,
                    opcionesEsp,
                    opcionesEsp[0]);
            // traido todos los medicos que tienen esa especialidad
            PreparedStatement stmtMedico = con.prepareStatement(
                    "SELECT * FROM medicos WHERE especialidad = ?");

            stmtMedico.setString(1, especialidadSeleccionada);
            ResultSet rsMedico = stmtMedico.executeQuery();
            ArrayList<String> medicos = new ArrayList<>();
            ArrayList<Integer> ids = new ArrayList<>();
            while (rsMedico.next()) {
                int id = rsMedico.getInt("usuario_id");
                String nombre = obtenerNombreUsuario(id); // aca obtengo el id del medico de la tabla USUARIOS no de la tabla medicos
                medicos.add(nombre + " / ID: " + id);
                ids.add(id);
            }
            String[] opcionesMed = medicos.toArray(new String[0]);
            String seleccionado = (String) JOptionPane.showInputDialog(null,
                    "Seleccione medico:",
                    "Medico",
                    0,
                    null,
                    opcionesMed,
                    opcionesMed[0]);
            // busco la posicion del medico
            int posicionSeleccionada = medicos.indexOf(seleccionado);
            // y aca obtengo el id
            int idMedicoSeleccionado = ids.get(posicionSeleccionada);

            String[] horarios = {"2025-05-20 08:00:00", "2025-05-20 10:00:00", "2025-05-20 14:00:00"}; // esto deberiamos cambiarlo pero por ahora lo voy a dejar asi porquen o se me ocurre otra manera
            String fechaElegida = (String) JOptionPane.showInputDialog(null,
                    "Seleccione horario:",
                    "Horario",
                    0,
                    null,
                    horarios,
                    horarios[0]);
           // hago los insert
            PreparedStatement stmtInsert = con.prepareStatement(
                    "INSERT INTO turnos (paciente_id, medico_id, especialidad, fecha, estado) VALUES (?, ?, ?, ?, ?)");
            stmtInsert.setInt(1, this.getIdUsuario());
            stmtInsert.setInt(2, idMedicoSeleccionado);
            stmtInsert.setString(3, especialidadSeleccionada);
            stmtInsert.setString(4, fechaElegida);
            stmtInsert.setString(5, "Pendiente");
            stmtInsert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Turno reservado con exito");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al reservar turno: " + e.getMessage());
        }
    }

    private String obtenerNombreUsuario(int id) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM usuarios WHERE idUsuario = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Desconocido";
    }

    public void mostrarMenuPaciente() {
        String[] opciones = {"Info Personal", "Ver Plan de Salud", "Ver Todos los Turnos", "Ver Ultimo Turno", "Ver Historial", "Reservar Turno", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null, "¿Que desea hacer, " + getNombre() + "?", "Menu Paciente", 0, 0, null, opciones, opciones[0]);
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Nombre: " + getNombre() + "\nApellido: " + getApellido() + "\nDNI: " + getDni() + "\nNacimiento: " + getFechaNacimiento());
                    break;
                case 1:
                    verPlan();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "hacer eso");
                    break;
                case 3:
                    verUltimoTurno();
                    break;
                case 4:
                    verHistorialMedico(this.getIdUsuario());
                    break;
                case 5:
                    reservarTurno();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "¡Hasta lueguito, " + getNombre() + "!");
                    break;
            }
        } while (opcion != 6);
    }




//to String

    @Override
    public String toString() {
        return "Paciente{" +
                "historialMedico=" + historialMedico +
                ", misTurnos=" + misTurnos +
                ", planSalud=" + planId +
                '}';
    }


    //Metodos
    //el Paciente loguea y como paciente tiene su propio menu


//    @Override
//    public void login(String mailIngresado, String contraseniaIngresada) {
//        super.login(mailIngresado, contraseniaIngresada);
//        menuPaciente();
//    }

//    public void verTurnos() {
//
//    }
//
//    public void pedirTurno() {
//        menuHorarios();
//    }
//
//    public void cancelarTurno() {
//    }
//
//    public void reprogramarTurno() {
//    }
//
//    public void verEstudio() {
//    }
//
//    public void verSucursales() {
//        //deberiamos hacer una clase sucursales?
//    }
//
//    public void verDatosCredencial() {
//        credencial.toString();
//    }
//
//    public void solicitarMedicamentos() {
//    }
//
//    public LinkedList verHorariosMedicos() {
//        return new LinkedList<>();
//    }
//
//    public void verInformacionHorarios() {
//    }
//
//    public void verInformacionMedico() {
//    }
//    public void verInformacionPersonal() {
//        String[] opciones = new String[]{"Ver Informacion Personal", "Ver Estudios","Ver Credencial","Salir"};
//        int opcion;
//        do {
//            opcion = JOptionPane.showOptionDialog(null,
//                    "Seleccione una opcion:",
//                    "Menu de Informacion del Paciente",
//                    0,
//                    0,
//                    null,
//                    opciones,
//                   opciones);
//            switch (opcion) {
//                case 0:
//                    JOptionPane.showMessageDialog(null,"Informacion personal del paciente");
//                    break;
//                case 1:
//                    JOptionPane.showMessageDialog(null,"Estudios del paciente");
//                    verEstudio(); // aca puede ver sus estudios con sus resultados
//                    break;
//                case 2:
//                    JOptionPane.showMessageDialog(null,"Datos de la credencial");
//                     // va a ver su plan actual y su numero de socio habria que ver si tambien sus datos como nombre, telefono etc
//                    break;
//                case 3:
//                    JOptionPane.showMessageDialog(null, "Saliendo...");
//                    break;
//
//            }
//        }while (opcion!=3);
//
//    }
//
//    public void menuPaciente() {
//        ImageIcon icon = new ImageIcon(getClass().getResource("/img/paciente.png"));
//        int opcion;
//        do {
//            opcion = JOptionPane.showOptionDialog(null,
//                    "Seleccione una opcion:",
//                    "Menu de Paciente",
//                    0,
//                    0,
//                    icon,
//                    MenuPacienteEnu.values(),
//                    MenuPacienteEnu.values());
//            switch (opcion) {
//                case 0:
//                    verInformacionPersonal(); // menu con informacion personal
//                    break;
//                case 1:
//                    menuTurnos();
//                    break;
//                case 2:
//                    solicitarMedicamentos();
//                    break;
//                case 3:
//                    verHorariosMedicos();
//                    break;
//                case 4:
//                    JOptionPane.showMessageDialog(null, "Saliendo...");
//                    break;
//            }
//        }while (opcion!=4);
//
//    }
//    public void menuTurnos(){
//        String[] opciones = new String[]{"Ver turno reservado", "Pedir Turno","Reprogramar Turno","Cancelar Turno","Salir"};
//        int opcion;
//        do {
//            opcion = JOptionPane.showOptionDialog(null,
//                    "Seleccione una opcion:",
//                    "Menu de Turnos",
//                    0,
//                    0,
//                    null,
//                    opciones,
//                    opciones
//            );
//            switch (opcion) {
//                case 0:
//                    verTurnos();
//                    JOptionPane.showMessageDialog(null,"Informacion del turno: \nNumero de turno:  \nTipo de turno: \nMedico: \nHorario: \nFecha: \nMotivo de la consulta: \nDiagnostico: \nTratamiento: \nDetalles: ");
//                    break;
//                case 1:
//                    pedirTurno();
//                    break;
//                case 2:
//                    reprogramarTurno();
//                    JOptionPane.showMessageDialog(null,"Reprogramar turno:");
//                    break;
//                case 3:
//                    cancelarTurno();
//                    JOptionPane.showMessageDialog(null,"Cancelar Turno");
//                    break;
//                case 4:
//                    JOptionPane.showMessageDialog(null, "Saliendo...");
//                    break;
//            }
//        }while (opcion!=4);
//
//    }
//    public void menuHorarios(){
//        String[] opciones = new String[]{"Ver horarios medicos","Ver informacion de medicos","Salir"};
//        int opcion;
//        do {
//            opcion = JOptionPane.showOptionDialog(null,
//                    "Seleccione una opcion:",
//                    "Menu de Horarios",
//                    0,
//                    0,
//                    null,
//                    opciones,
//                    opciones
//            );
//            switch (opcion) {
//                case 0:
//                    JOptionPane.showMessageDialog(null,"Agenda medica: horarios ");
//                    verInformacionHorarios();
//                    break;
//                case 1:
//                    JOptionPane.showMessageDialog(null,"Ver informacion de los medicos: \nNombre Medico: \nEspecialidad: \nHorarios:");
//                    verInformacionMedico();
//                    break;
//                case 2:
//                    JOptionPane.showMessageDialog(null, "Saliendo...");
//                    break;
//
//            }
//        }while (opcion!=2);
//    }



}