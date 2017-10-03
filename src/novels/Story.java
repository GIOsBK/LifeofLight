package novels;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays
;public class Story {
    public String id;
    public String text;
    public List<Choice> choices;

    public Story(String id, String text, Choice... choices) {
        this.id = id;
        this.text = text;
        this.choices = Arrays.asList(choices);
    }
}
