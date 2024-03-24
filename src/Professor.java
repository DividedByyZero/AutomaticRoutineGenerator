
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class Professor extends Routine {
    String name;
    String code;
    String number;
    String jobyear;
    static ArrayList<String> courses = new ArrayList<>();

    public Professor(String name, String code, String number, String jobyear) {
        this.name = name;
        this.code = code;
        this.number = number;
        this.jobyear = jobyear;
    }
    
}
