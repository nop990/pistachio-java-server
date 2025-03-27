package com.nop990.pistachio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class PistachioApplication {

    public static void main(String[] args) throws SQLException {
        // Start Spring Boot in a separate thread
        new Thread(() -> SpringApplication.run(PistachioApplication.class, args)).start();

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

        // Open Angular app in the system's default browser
        openBrowser("http://localhost:8080");
    }

    private static void openBrowser(String url) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            } else {
                System.out.println("OS not supported for automatic browser opening.");
                System.out.println("Open the URL manually: " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void launchVenv() {
        Process process = null;

        try {
            String venvPath = "pistachioVenv";  // Adjust path to your virtual environment
            String notebookScript = "jupyter notebook";
            String notebookPort = "--port=8888";

            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows-specific code
                venvPath = venvPath + "\\Scripts\\activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "cmd.exe", "/c", "start", "cmd.exe", "/k", "call " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True"
                });
            } else if (os.contains("mac")) {
                // macOS-specific code
                venvPath = venvPath + "/bin/activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "osascript", "-e", "tell application \"Terminal\" to do script \"source " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser\""
                });
            } else {
                // Linux-specific code
                venvPath = venvPath + "/bin/activate";
                process = Runtime.getRuntime().exec(new String[]{
                        "x-terminal-emulator", "-e", "bash", "-c", "source " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser"
                });
            }

            System.out.println("Jupyter Notebook started...");

            // Add shutdown hook to terminate process when the Java app is stopped
            Process finalProcess = process;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (finalProcess != null) {
                    finalProcess.destroy();
                    System.out.println("Jupyter Notebook process terminated.");
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void launchVenvPB() {
        ProcessBuilder processBuilder;

        try {
            String venvPath = "pistachioVenv";  // Adjust path to your virtual environment
            String notebookScript = "jupyter notebook";
            String notebookPort = "--port=8888";

            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows-specific code
                venvPath = venvPath + "\\Scripts\\activate";
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "call " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser");
            } else if (os.contains("mac")) {
                // macOS-specific code
                venvPath = venvPath + "/bin/activate";
                processBuilder = new ProcessBuilder("bash", "-c", "source " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser");
            } else {
                // Linux-specific code
                venvPath = venvPath + "/bin/activate";
                processBuilder = new ProcessBuilder("bash", "-c", "source " + venvPath + " && " + notebookScript + " " + notebookPort + " --NotebookApp.disable_check_xsrf=True --no-browser");
            }

            // Redirect error and output streams
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            // Start the process
            Process process = processBuilder.start();

            process.waitFor();

            // Add shutdown hook to terminate process when the Java app is stopped
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (process != null && process.isAlive()) {
                    System.out.println("Jupyter Notebook process terminating");
                    process.destroyForcibly();
                    System.out.println("Jupyter Notebook process terminated.");
                }
            }));

            System.out.println("Jupyter Notebook started...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
