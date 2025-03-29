package com.nop990.pistachio.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class PistachioService {
    public String mainProcess() throws MalformedURLException {
        runNotebookPB();

        return "Pistachio Projections Generated";
    }

    public void runNotebookPB() {
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
            System.out.println("Jupyter Execute process started...");
            process.waitFor();
            System.out.println("Jupyter Execute process complete.");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (process.isAlive()) {
                    System.out.println("Shutting down Jupyter Execute process...");
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
