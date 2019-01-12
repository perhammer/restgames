package com.perhammer.joshua.scores;

import com.perhammer.joshua.registration.Registration;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor
@Entity
public class Score {
    private @Id @GeneratedValue @Column(name = "SCORE_ID") Long id;
    private Long scoredByRegistrationId;
    private String gameVariation;
    private Long moves = 0L;
    private Integer points = 0;
    private Long trackedDurationMillis = 0L;
}
