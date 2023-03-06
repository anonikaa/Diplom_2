package pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Register {
    private String email;
    private String password;
    private String name;
    public Register(){}
    public Register (String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
