package BLL;

import DLL.Conexion;
import DLL.ControllerMedico;
import GUI.MenuMedico;

import javax.swing.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class Medico extends Usuario {
    private String especialidad;
    private List<Turno> turnosReservados;
    private static Connection con = Conexion.getInstance().getConnection();

    public Medico(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, String especialidad) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.especialidad = especialidad;
    }


    public Medico() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getTurnosReservados() {
        return turnosReservados;
    }

    public void setTurnosReservados(List<Turno> turnosReservados) {
        this.turnosReservados = turnosReservados;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "especialidad='" + especialidad + '\'' +
                ", turnosReservados=" + turnosReservados +
                '}';
    }

    public void verTurnos() {
        ControllerMedico.verTurnos(this);
    }



//    public void verTurnoIndividual() {
//
//    }
    public void atenderTurnoIndividual() {
        ControllerMedico.atenderTurnoIndividual(this);
    }

//    public void cancelarTurno() {
//    }
//    public void reprogramarTurno() {
//    }


    public void menuMedico() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/medico.png"));
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu de Medico",
                    0,
                    0,
                    icon,
                    MenuMedico.values(),
                    MenuMedico.values());
            switch (opcion) {
                case 0:
                    verTurnos();
                    break;
                case 1:
                    atenderTurnoIndividual();
                    break;
                case 2:
                    //cancelarTurno();
                    JOptionPane.showMessageDialog(null,"Cancelar BLL.Turno: ");
                    break;
                case 3:
                    //reprogramarTurno();
                    JOptionPane.showMessageDialog(null,"Reprogramar turnoo:");
                    break;
                case 4:
                    //verEstudio();
                    JOptionPane.showMessageDialog(null,"Ver Estudio:");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }
        }while (opcion!=5);

    }
}
