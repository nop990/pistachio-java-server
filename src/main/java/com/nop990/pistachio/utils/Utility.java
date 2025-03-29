package com.nop990.pistachio.utils;

import com.nop990.pistachio.models.BatterReportDTO;
import com.nop990.pistachio.models.PitcherReportDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Utility {
    public static String readFile(String fileName) throws FileNotFoundException {
        try {
            Path path = Paths.get(fileName);
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file");
        }
    }

    public static void writeFile(String fileName, String content) {
        try {
            Path path = Paths.get(fileName);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void validateCsv(String content) {
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)))) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length != 2) {
                    throw new IllegalArgumentException("CSV does not have exactly 2 columns");
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error processing CSV file", e);
        }
    }

    public static ArrayList<BatterReportDTO> readBatterCsv() {
        Path path = null;
        try {
            path = Paths.get("reports", "batter_sWAR.csv");
        } catch (Exception e) {
            System.out.println("Batter CSV file not found");
            e.printStackTrace();
        }

        if (path != null) {
            ArrayList<BatterReportDTO> batterReportList = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
                String[] nextLine;
                boolean isFirstLine = true;
                int counter = 0;
                while ((nextLine = csvReader.readNext()) != null) {
                    counter++;
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header line
                    }

                    if (nextLine.length > 0) {
                        BatterReportDTO batterReport = mapBatterReportDTO(nextLine);
                        batterReportList.add(batterReport);
                    }
                }
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
                throw new RuntimeException("Error reading CSV file", e);
            }
            return batterReportList;
        } else {
            return null;
        }
    }

    public static ArrayList<PitcherReportDTO> readPitcherCsv() {
        Path path = null;

        try {
            path = Paths.get("reports", "pitcher_sWAR.csv");
        } catch (Exception e) {
            System.out.println("Batter CSV file not found");
            e.printStackTrace();
        }

        ArrayList<PitcherReportDTO> pitcherReportList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            String[] nextLine;
            boolean isFirstLine = true;
            int counter = 0;
            while ((nextLine = csvReader.readNext()) != null) {
                counter++;
                if (counter == 1) {
                    continue; // Skip the header line
                }

                if (nextLine.length > 0) {
                    PitcherReportDTO pitcherReport = mapPitcherReportDTO(nextLine);
                    pitcherReportList.add(pitcherReport);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading CSV file", e);
        }
        return pitcherReportList;
    }

    private static BatterReportDTO mapBatterReportDTO(String[] nextLine) {
        BatterReportDTO batterReport = new BatterReportDTO();
        batterReport.setName(nextLine[0] != null ? nextLine[0] : "");
        batterReport.setAge(nextLine[1] != null && !nextLine[1].isEmpty() ? Integer.parseInt(nextLine[1]) : 0);
        batterReport.setTeam(nextLine[2] != null ? nextLine[2] : "");
        batterReport.setMinor(nextLine[3] != null ? nextLine[3] : "");
        batterReport.setPa(nextLine[4] != null && !nextLine[4].isEmpty() ? Integer.parseInt(nextLine[4]) : 0);
        batterReport.setWarOverall(nextLine[5] != null && !nextLine[5].isEmpty() ? Double.parseDouble(nextLine[5]) : 0.0);
        batterReport.setPrimaryPosition(nextLine[6] != null ? nextLine[6] : "");
        batterReport.setRecommendedPositions(nextLine[7] != null ? nextLine[7] : "");
        batterReport.setBats(nextLine[8] != null ? nextLine[8] : "");
        batterReport.setHr_650(nextLine[9] != null && !nextLine[9].isEmpty() ? Integer.parseInt(nextLine[9]) : 0);
        batterReport.setHrOverall(nextLine[10] != null && !nextLine[10].isEmpty() ? Integer.parseInt(nextLine[10]) : 0);
        batterReport.setObpOverall(nextLine[11] != null && !nextLine[11].isEmpty() ? Double.parseDouble(nextLine[11]) : 0.0);
        batterReport.setOpsPlusOverall(nextLine[12] != null && !nextLine[12].isEmpty() ? Double.parseDouble(nextLine[12]) : 0.0);
        batterReport.setWarPotential(nextLine[13] != null && !nextLine[13].isEmpty() ? Double.parseDouble(nextLine[13]) : 0.0);
        batterReport.setHrPotential(nextLine[14] != null && !nextLine[14].isEmpty() ? Integer.parseInt(nextLine[14]) : 0);
        batterReport.setObpPotential(nextLine[15] != null && !nextLine[15].isEmpty() ? Double.parseDouble(nextLine[15]) : 0.0);
        batterReport.setOpsPlusPotential(nextLine[16] != null && !nextLine[16].isEmpty() ? Double.parseDouble(nextLine[16]) : 0.0);
        batterReport.setOpsPlusPotentialHasPosition(nextLine[17] != null && !nextLine[17].isEmpty() ? Integer.parseInt(nextLine[17]) : 0);
        batterReport.setCatcherOverall(nextLine[19] != null && !nextLine[19].isEmpty() ? Double.parseDouble(nextLine[19]) : 0.0);
        batterReport.setFirstBaseOverall(nextLine[20] != null && !nextLine[20].isEmpty() ? Double.parseDouble(nextLine[20]) : 0.0);
        batterReport.setSecondBaseOverall(nextLine[21] != null && !nextLine[21].isEmpty() ? Double.parseDouble(nextLine[21]) : 0.0);
        batterReport.setThirdBaseOverall(nextLine[22] != null && !nextLine[22].isEmpty() ? Double.parseDouble(nextLine[22]) : 0.0);
        batterReport.setShortstopOverall(nextLine[23] != null && !nextLine[23].isEmpty() ? Double.parseDouble(nextLine[23]) : 0.0);
        batterReport.setLeftFieldOverall(nextLine[24] != null && !nextLine[24].isEmpty() ? Double.parseDouble(nextLine[24]) : 0.0);
        batterReport.setCenterFieldOverall(nextLine[25] != null && !nextLine[25].isEmpty() ? Double.parseDouble(nextLine[25]) : 0.0);
        batterReport.setRightFieldOverall(nextLine[26] != null && !nextLine[26].isEmpty() ? Double.parseDouble(nextLine[26]) : 0.0);
        batterReport.setDhOverall(nextLine[27] != null && !nextLine[27].isEmpty() ? Double.parseDouble(nextLine[27]) : 0.0);
        batterReport.setCatcherPotential(nextLine[28] != null && !nextLine[28].isEmpty() ? Double.parseDouble(nextLine[28]) : 0.0);
        batterReport.setFirstBasePotential(nextLine[29] != null && !nextLine[29].isEmpty() ? Double.parseDouble(nextLine[29]) : 0.0);
        batterReport.setSecondBasePotential(nextLine[30] != null && !nextLine[30].isEmpty() ? Double.parseDouble(nextLine[30]) : 0.0);
        batterReport.setThirdBasePotential(nextLine[31] != null && !nextLine[31].isEmpty() ? Double.parseDouble(nextLine[31]) : 0.0);
        batterReport.setShortstopPotential(nextLine[32] != null && !nextLine[32].isEmpty() ? Double.parseDouble(nextLine[32]) : 0.0);
        batterReport.setLeftFieldPotential(nextLine[33] != null && !nextLine[33].isEmpty() ? Double.parseDouble(nextLine[33]) : 0.0);
        batterReport.setCenterFieldPotential(nextLine[34] != null && !nextLine[34].isEmpty() ? Double.parseDouble(nextLine[34]) : 0.0);
        batterReport.setRightFieldPotential(nextLine[35] != null && !nextLine[35].isEmpty() ? Double.parseDouble(nextLine[35]) : 0.0);
        batterReport.setDhPotential(nextLine[36] != null && !nextLine[36].isEmpty() ? Double.parseDouble(nextLine[36]) : 0.0);
        batterReport.setOffensiveWarOverall(nextLine[37] != null && !nextLine[37].isEmpty() ? Double.parseDouble(nextLine[37]) : 0.0);
        batterReport.setOffensiveWarPotential(nextLine[38] != null && !nextLine[38].isEmpty() ? Double.parseDouble(nextLine[38]) : 0.0);
        batterReport.setCatcherDefensiveWar(nextLine[39] != null && !nextLine[39].isEmpty() ? Double.parseDouble(nextLine[39]) : 0.0);
        batterReport.setFirstBaseDefensiveWar(nextLine[40] != null && !nextLine[40].isEmpty() ? Double.parseDouble(nextLine[40]) : 0.0);
        batterReport.setSecondBaseDefensiveWar(nextLine[41] != null && !nextLine[41].isEmpty() ? Double.parseDouble(nextLine[41]) : 0.0);
        batterReport.setThirdBaseDefensiveWar(nextLine[42] != null && !nextLine[42].isEmpty() ? Double.parseDouble(nextLine[42]) : 0.0);
        batterReport.setShortstopDefensiveWar(nextLine[43] != null && !nextLine[43].isEmpty() ? Double.parseDouble(nextLine[43]) : 0.0);
        batterReport.setLeftFieldDefensiveWar(nextLine[44] != null && !nextLine[44].isEmpty() ? Double.parseDouble(nextLine[44]) : 0.0);
        batterReport.setCenterFieldDefensiveWar(nextLine[45] != null && !nextLine[45].isEmpty() ? Double.parseDouble(nextLine[45]) : 0.0);
        batterReport.setRightFieldDefensiveWar(nextLine[46] != null && !nextLine[46].isEmpty() ? Double.parseDouble(nextLine[46]) : 0.0);
        batterReport.setFlagged(nextLine[48] != null && !nextLine[48].isEmpty() && nextLine[48].equalsIgnoreCase("flagged"));
        return batterReport;
    }

    private static PitcherReportDTO mapPitcherReportDTO(String[] nextLine) {
        PitcherReportDTO pitcherReport = new PitcherReportDTO();
        pitcherReport.setName(nextLine[0] != null ? nextLine[0] : "");
        pitcherReport.setAge(nextLine[1] != null && !nextLine[1].isEmpty() ? Integer.parseInt(nextLine[1]) : 0);
        pitcherReport.setTeam(nextLine[2] != null ? nextLine[2] : "");
        pitcherReport.setMinor(nextLine[3] != null ? nextLine[3] : "");
        pitcherReport.setIp(nextLine[4] != null ? nextLine[4] : "");
        pitcherReport.set_throws(nextLine[5] != null ? nextLine[5] : "");
        pitcherReport.setSpOverall(nextLine[6] != null && !nextLine[6].isEmpty() ? (int) Double.parseDouble(nextLine[6]) : 0);
        pitcherReport.setRpOverall(nextLine[7] != null && !nextLine[7].isEmpty() ? (int) Double.parseDouble(nextLine[7]) : 0);
        pitcherReport.setSpPotential(nextLine[8] != null && !nextLine[8].isEmpty() ? (int) Double.parseDouble(nextLine[8]) : 0);
        pitcherReport.setRpPotential(nextLine[9] != null && !nextLine[9].isEmpty() ? (int) Double.parseDouble(nextLine[9]) : 0);
        pitcherReport.setFipOverall(nextLine[10] != null && !nextLine[10].isEmpty() ? (int) Double.parseDouble(nextLine[10]) : 0);
        pitcherReport.setFipPotential(nextLine[11] != null && !nextLine[11].isEmpty() ? (int) Double.parseDouble(nextLine[11]) : 0);
        pitcherReport.setFlagged(nextLine[12] != null && !nextLine[12].isEmpty() && nextLine[12].trim().equalsIgnoreCase("flagged"));
        return pitcherReport;
    }
}
