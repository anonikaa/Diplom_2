package pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ingredients {
    private String ingredients;
    public Ingredients(){}
    public Ingredients (String ingredients){
        this.ingredients = ingredients;
    }
}
