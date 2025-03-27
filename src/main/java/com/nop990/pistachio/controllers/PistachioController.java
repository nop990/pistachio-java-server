package com.nop990.pistachio.controllers;

import com.nop990.pistachio.services.PistachioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static com.nop990.pistachio.utils.Utility.readFile;

@Controller
@CrossOrigin(origins = "*")
public class PistachioController {
    @Autowired
    PistachioService pistachioService;

    // Run "Notebook"
    @PostMapping("/runNotebook")
    public ResponseEntity<String> runNotebook() throws IOException, SQLException {
        String result = pistachioService.mainProcess();

        return ResponseEntity.ok(result);
    }

    // Get Batter Report
    @GetMapping("/getBatterReport")
    public ResponseEntity<String> getBatterReport() {
        try {
            String content = readFile("reports/batter_sWAR.csv");
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FileNotFoundException) {
                return new ResponseEntity<>("Report not found", HttpStatus.NOT_FOUND);
            }
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Error locating file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Pitcher Report
    @GetMapping("/getPitcherReport")
    public ResponseEntity<String> getPitcherReport() {
        try {
            String content = readFile("reports/pitcher_sWAR.csv");
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FileNotFoundException) {
                return new ResponseEntity<>("Report not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Error locating file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
