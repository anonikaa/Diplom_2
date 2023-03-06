package pojo;

import handles.ChangeUserDataHandles;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeUserData {
    private String email;
    private String name;
    public ChangeUserData(){}
    public ChangeUserData(String email, String name){
        this.email = email;
        this.name = name;
    }
}
