package com.perhammer.joshua.scores;

import com.perhammer.joshua.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
//    List<Score> findAll();
//    List<Score> findAllBy(Registration registration);
//    List<Score> findAllBy(S scoredIn);
}
