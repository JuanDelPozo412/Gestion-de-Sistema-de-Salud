import javax.swing.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class Paciente extends Persona { //extends Persona
    //Atributos
    private Credencial credencial;
    private String sexo;
    private Historial historial;

    //Constructor


    public Paciente(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaNacimiento, Credencial credencial, String sexo,Historial historial) {
        super(nombre, apellido, mail, contrasenia, fechaNacimiento);
        this.credencial = credencial;
        this.sexo = sexo;
        this.historial = historial;
    }

    public Paciente(String nombre, String apellido, String mail, String contrasenia, LocalDate fechaNacimiento, String sexo) {
        super(nombre, apellido, mail, contrasenia, fechaNacimiento);
        this.sexo = sexo;
    }


    //Get y Set

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Credencial getCredencial() {
        return credencial;
    }

    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }
    //to String

    @Override
    public String toString() {
        return "Paciente{" +
                "credencial=" + credencial +
                ", sexo='" + sexo + '\'' +
                ", historial=" + historial +
                '}';
    }

    //Metodos
    //el Paciente loguea y como paciente tiene su propio menu


    @Override
    public void login(String mailIngresado, String contraseniaIngresada) {
        super.login(mailIngresado, contraseniaIngresada);
        menuPaciente();
    }

    public void verTurnos() {


    }

    public void pedirTurno() {
        menuHorarios();
    }

    public void cancelarTurno() {
    }

    public void reprogramarTurno() {
    }

    public void verEstudio() {
    }

    public void verSucursales() {
        //deberiamos hacer una clase sucursales?
    }

    public void verDatosCredencial() {
        credencial.toString();
    }

    public void solicitarMedicamentos() {
    }

    public LinkedList verHorariosMedicos() {
        return new LinkedList<>();
    }

    public void verInformacionHorarios() {
    }

    public void verInformacionMedico() {
    }
    public void verInformacionPersonal() {
        String[] opciones = new String[]{"Ver Informacion Personal", "Ver Estudios","Ver Credencial","Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Informacion del Paciente",
                    0,
                    0,
                    null,
                    opciones,
                   opciones);
            switch (opcion) {
                case 0:
                    // Aca el paciente va a poder ver su historial
                    break;
                case 1:
                    verEstudio(); // aca puede ver sus estudios con sus resultados
                    break;
                case 2:
                    verDatosCredencial(); // va a ver su plan actual y su numero de socio habria que ver si tambien sus datos como nombre, telefono etc
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;

            }
        }while (opcion!=3);

    }

    public void menuPaciente() {
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Paciente",
                    0,
                    0,
                    null,
                    MenuPacienteEnu.values(),
                    MenuPacienteEnu.values());
            switch (opcion) {
                case 0:
                    verInformacionPersonal(); // menu con informacion personal
                    break;
                case 1:
                    menuTurnos();
                    break;
                case 2:
                    solicitarMedicamentos();
                    break;
                case 3:
                    verHorariosMedicos();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=4);

    }
    public void menuTurnos(){
        String[] opciones = new String[]{"Ver turno reservado", "Pedir Turno","Reprogramar Turno","Cancelar Turno","Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Turnos",
                    0,
                    0,
                    null,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    verTurnos();
                    break;
                case 1:
                    pedirTurno();
                    break;
                case 2:
                    reprogramarTurno();
                    break;
                case 3:
                    cancelarTurno();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=4);

    }
    public void menuHorarios(){
        String[] opciones = new String[]{"Ver horarios medicos","Ver informacion de medicos","Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Horarios",
                    0,
                    0,
                    null,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null,"Agenda medica");
                    verInformacionHorarios();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null,"Ver informacion de los medicos");
                    verInformacionMedico();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;

            }
        }while (opcion!=2);
    }


}