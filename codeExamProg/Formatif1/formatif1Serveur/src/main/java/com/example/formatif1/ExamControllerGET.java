package com.example.formatif1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExamControllerGET {

    @GetMapping("/exam/h25/{x}")
    public ResponseEntity<String> direBonjour(@PathVariable String x) {

        if (x.length() <= 2) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erreur : le nom doit avoir plus de 2 lettres");
        }

        return ResponseEntity.ok("Bonjour " + x);
    }
}