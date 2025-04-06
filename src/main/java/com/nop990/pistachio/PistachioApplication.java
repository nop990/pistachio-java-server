package com.nop990.pistachio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class PistachioApplication {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
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

    public static void openBrowser(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();

            if (os.contains("win")) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
                rt.exec("xdg-open " + url);
            } else {
                throw new UnsupportedOperationException("Unsupported OS: " + os);
            }
        }
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
