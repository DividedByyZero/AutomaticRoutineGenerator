
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class Course {
    String Name;
    String Code;
    String Slots;
    String PerWeek;
    String Session;
    String ProfessorCode;
    Boolean assign = false;
    Boolean finish = false;
    ArrayList<String> roomlist = new ArrayList<>();

    public Course(String Name, String Code, String Slots, String PerWeek, String Session) {
        this.Name = Name;
        this.Code = Code;
        this.Slots = Slots;
        this.PerWeek = PerWeek;
        this.Session = Session;
    }
    
}
