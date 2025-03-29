package com.nop990.pistachio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.nop990.pistachio.models.OptionsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.nop990.pistachio.utils.Utility.*;

@Controller
@CrossOrigin(origins = "*")
public class ConfigController {
    // Get/Set Settings
    @GetMapping("/getSettings")
    public ResponseEntity<OptionsDTO> getSettings() {
        try {
            Path path = Paths.get("config", "settings.toml").toAbsolutePath();
            TomlMapper mapper = new TomlMapper();
            OptionsDTO content = mapper.readValue(path.toFile(), OptionsDTO.class);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setSettings")
    public ResponseEntity<String> postSettings(@RequestBody String options) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OptionsDTO optionsDTO = objectMapper.readValue(options, OptionsDTO.class);

            TomlMapper tomlMapper = new TomlMapper();
            String tomlContent = tomlMapper.writeValueAsString(optionsDTO);

            writeFile("config/settings.toml", tomlContent);

            return new ResponseEntity<>("Settings Saved", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get/Set Flagged Players
    @GetMapping("/getFlagged")
    public ResponseEntity<String> getFlagged() {
        try {
            Path path = Paths.get("config", "flagged.txt").toAbsolutePath();
            String content = readFile(path.toString());
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/setFlagged")
    public ResponseEntity<String> setFlagged(@RequestBody String content) {
        try {
            Path path = Paths.get("config", "flagged.txt").toAbsolutePath();
            writeFile(path.toString(), content);
            return new ResponseEntity<>("Flagged Players Saved", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get/Set Displayed Columns
    @GetMapping("/getBatterColumns")
    public ResponseEntity<String> getBatterColumns() {
        try {
            Path path = Paths.get("config", "batter-columns.txt").toAbsolutePath();
            String content = readFile(path.toString());
            System.out.println(content);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPitcherColumns")
    public ResponseEntity<String> getPitcherColumns() {
        try {
            Path path = Paths.get("config", "pitcher-columns.txt").toAbsolutePath();
            String content = readFile(path.toString());
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/setBatterColumns")
    public ResponseEntity<String> setBatterColumns(@RequestBody String content) {
        try {
            Path path = Paths.get("config", "batter-columns.txt").toAbsolutePath();
            writeFile(path.toString(), content);
            return new ResponseEntity<>("Batter Columns Saved", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setPitcherColumns")
    public ResponseEntity<String> setPitcherColumns(@RequestBody String content) {
        try {
            Path path = Paths.get("config", "pitcher-columns.txt").toAbsolutePath();
            writeFile(path.toString(), content);
            return new ResponseEntity<>("Pitcher Columns Saved", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get/Set Club Index
    @GetMapping("/getClubLookup")
    public ResponseEntity<String> getClubLookup() {
        try {
            Path path = Paths.get("config", "club_lookup.csv").toAbsolutePath();
            String content = readFile(path.toString());
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/setClubLookup")
    public ResponseEntity<String> setClubLookup(@RequestBody String content) {
        try {
            validateCsv(content);
            Path path = Paths.get("config", "club_lookup.csv").toAbsolutePath();
            writeFile(path.toString(), content);
            return new ResponseEntity<>("Club Index Saved", HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("2 columns")) {
                return new ResponseEntity<>("Invalid CSV", HttpStatus.NOT_ACCEPTABLE);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
