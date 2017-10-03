package novels;

import java.util.ArrayList;

public class Choice {
    public  String value;
    public  String to;
    public Choice(String value, String to){
        this.value = value;
        this.to = to;

    }

    public boolean match (String annswer) {
        return value.equalsIgnoreCase(annswer) || value.equalsIgnoreCase("other");
    }

}
