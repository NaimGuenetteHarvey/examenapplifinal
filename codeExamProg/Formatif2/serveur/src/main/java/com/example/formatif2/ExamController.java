package com.example.formatif2;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExamController {

    @GetMapping("/exam/{x}/{y}")
    public List<Integer> nombresPairs(
            @PathVariable int x,
            @PathVariable int y
    ) {

        System.out.println("Appel reçu : " + x + " à " + y);

        List<Integer> liste = new ArrayList<>();

        for (int i = x; i <= y; i++) {

            if (i % 2 == 0) {
                liste.add(i);
            }
        }

        return liste;
    }
}