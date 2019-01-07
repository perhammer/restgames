package com.perhammer.joshua.registration;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;

@Data
@Entity
public class Registration {
    private @Id @GeneratedValue Long id;
    String teamname;

    Registration() {
        this.teamname = "UNSPECIFIED";
    }

    Registration(String hostname, String teamname) {
        this();
        this.teamname = teamname;
    }
}
