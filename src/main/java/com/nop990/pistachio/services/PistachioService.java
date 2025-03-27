package com.nop990.pistachio.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

@Service
public class PistachioService {
    public String mainProcess() throws IOException, SQLException {
        // Read in configs
        TomlMapper tomlMapper = new TomlMapper();
        JsonNode tomlNode = tomlMapper.readTree(new File("config/settings.toml"));
        String csv_path = tomlNode.get("csv_path").asText();
        int scout_id = tomlNode.get("scout_id").asInt();
        String team_id = tomlNode.get("team_id").asText();
        int min_gb = tomlNode.get("gb_weight").asInt();

        runNotebookPB();

        return "Pistachio Projections Generated";
    }

    public void runNotebook() throws MalformedURLException {
        try {
            String venvPath = "pistachioVenv";
            String notebookScript = "jupyter execute";
            String os = System.getProperty("os.name").toLowerCase();
            Process process;

            if (os.contains("win")) {
                // Windows-specific code
                venvPath = venvPath + "\\Scripts\\activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "cmd.exe", "/c", "start", "cmd.exe", "/k", "call " + venvPath + " && " + notebookScript + " pistachio.ipynb && exit"
                });
            } else if (os.contains("mac")) {
                // macOS-specific code
                venvPath = venvPath + "/bin/activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "osascript", "-e", "tell application \"Terminal\" to do script \"source " + venvPath + " && " + notebookScript + " pistachio.ipynb; exit\""
                });
            } else {
                // Linux-specific code
                venvPath = venvPath + "/bin/activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "x-terminal-emulator", "-e", "bash", "-c", "source " + venvPath + " && " + notebookScript + " pistachio.ipynb; exit"
                });
            }

            // Wait for the process to complete
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runNotebookPB() throws MalformedURLException {
        try {
            String venvPath = "pistachioVenv";
            String notebookScript = "jupyter execute";
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                // Windows-specific code
                venvPath = venvPath + "\\Scripts\\activate";
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "call " + venvPath + " && " + notebookScript + " pistachio.ipynb");
            } else if (os.contains("mac")) {
                // macOS-specific code
                venvPath = venvPath + "/bin/activate";
                processBuilder = new ProcessBuilder("bash", "-c", "source " + venvPath + " && " + notebookScript + " pistachio.ipynb");
            } else {
                // Linux-specific code
                venvPath = venvPath + "/bin/activate";
                processBuilder = new ProcessBuilder("bash", "-c", "source " + venvPath + " && " + notebookScript + " pistachio.ipynb");
            }

            // Redirect error and output streams
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            // Start the process
            Process process = processBuilder.start();

            process.waitFor();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (process != null && process.isAlive()) {
                    System.out.println("Jupyter Execute process terminating");
                    process.destroyForcibly();
                    System.out.println("Jupyter Execute process terminated.");
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
