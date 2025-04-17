import javax.swing.*;
import java.util.LinkedList;

public class Paciente  { //extends Persona
    //Atributos
    //private Credencial credencial;
    private String sexo;
    //Constructor

    public Paciente(String sexo) {
        this.sexo = sexo;
    }

    //Get y Set

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    //to String

    @Override
    public String toString() {
        return "Paciente{" +
                "sexo='" + sexo + '\'' +
                '}';
    }

    //Metodos
    // el Paciente loguea y como paciente tiene su propio menu
    public void login() {
        //metodo de persona sobrescrito
        // en su menu va a tener opciones como Ver su informacion personal
        menuPaciente();

    }

    public void verInformacionPersonal() {
            int opcion;
            do {
                opcion = JOptionPane.showOptionDialog(null,
                        "Seleccione una opcion:",
                        "Menu de Informacion del Paciente",
                        0,
                        0,
                        null,
                        MenuPacienteEnu.values(),
                        MenuPacienteEnu.values());
                switch (opcion) {
                    case 0:
                        verInformacionPersonal();
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                }
            }while (opcion!=4);



    }

    public void verTurnos() {
    }

    public void pedirTurno() {
    }

    public void cancelarTurno() {
    }

    public void reprogramarTurno() {
    }

    public void verEstudio() {
    }

    public void verSucursales() {
    }

    public void verDatosCredencial() {
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
        String[] opciones = new String[]{"ver Turno", "Pedir Turno","Reprogramar Turno","Cancalerar Turno","Salir"};
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


}