package com.nop990.pistachio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class PistachioApplication  {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        // Start Spring Boot in a separate thread
        SpringApplication.run(PistachioApplication.class, args);

        // Wait for Spring Boot to start
        try {
            Thread.sleep(3000); // Adjust delay if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        launchVenvPB();

        try {
            Thread.sleep(3000); // Adjust delay if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openBrowser("http://localhost:8080");
    }

    private static void openBrowser(String url) {

    }

    public static void launchVenvPB() {
        ProcessBuilder processBuilder;

        try {
            String venvPath = "pistachioVenv";
            String notebookScript = "jupyter notebook";
            String notebookPort = "--port=8888";

            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                venvPath = venvPath + "\\Scripts\\activate";
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "call " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser");
            } else {
                venvPath = venvPath + "/bin/activate";
                processBuilder = new ProcessBuilder("bash", "-c", "source " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser");
            }

            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process process = processBuilder.start();

            // Get Jupyter’s PID
            long jupyterPID = process.pid();
            System.out.println("Jupyter Notebook started with PID: " + jupyterPID);

            // Ensure Jupyter is killed properly
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (process.isAlive()) {
                        System.out.println("Shutting down Jupyter Notebook (PID: " + jupyterPID + ")...");

                        // Kill process and its children
                        if (os.contains("win")) {
                            new ProcessBuilder("taskkill", "/F", "/T", "/PID", String.valueOf(jupyterPID)).start();
                        } else {
                            new ProcessBuilder("kill", "-9", String.valueOf(jupyterPID)).start();
                        }

                        System.out.println("Jupyter Notebook terminated.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
