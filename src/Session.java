
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Nabeel Ahsan
 */
public class Session extends Routine {
    String name;
    ArrayList<String> Courses = new ArrayList<String>();
    Map<Integer,ArrayList<String>> Track = new HashMap<Integer,ArrayList<String>>();
    public Session(String name) {
        this.name = name;
    }
}
