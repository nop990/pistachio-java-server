package com.nop990.pistachio.controllers;

import com.nop990.pistachio.PistachioApplication;
import com.nop990.pistachio.models.BatterReportDTO;
import com.nop990.pistachio.models.PitcherReportDTO;
import com.nop990.pistachio.services.PistachioService;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.nop990.pistachio.utils.Utility.readBatterCsv;
import static com.nop990.pistachio.utils.Utility.readPitcherCsv;

@Controller
@CrossOrigin(origins = "*")
public class PistachioController {
    @Autowired
    PistachioService pistachioService;

    // Run "Notebook"
    @PostMapping("/runNotebook")
    public ResponseEntity<String> runNotebook() throws IOException {
        String result = pistachioService.mainProcess();

        return ResponseEntity.ok(result);
    }

    // Get Batter Report
    @GetMapping("/getBatterReport")
    public ResponseEntity<ArrayList<BatterReportDTO>> getBatterReport() {
        try {
            ArrayList<BatterReportDTO> response = readBatterCsv();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Pitcher Report
    @GetMapping("/getPitcherReport")
    public ResponseEntity<ArrayList<PitcherReportDTO>> getPitcherReport() {
        try {
            ArrayList<PitcherReportDTO> response = readPitcherCsv();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
