package com.perhammer.joshua.registration;

import com.perhammer.joshua.scores.Score;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor
@Entity
public class Registration {
    private @Id @GeneratedValue @Column(name = "REG_ID") Long id;
    @Column(unique=true)
    private String teamname;
    private String teampass;
    private String displayName;
}
