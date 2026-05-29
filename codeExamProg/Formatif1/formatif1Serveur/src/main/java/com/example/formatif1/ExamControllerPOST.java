package com.example.formatif1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class ExamControllerPOST {
    @PostMapping("/exam/h25")
    public ResponseEntity<String> bonjour(@RequestBody RequeteBonjour requete) {

        if (requete.nom.length() <= 2) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Nom trop court");
        }

        return ResponseEntity.ok("Bonjour " + requete.nom);
    }


}
