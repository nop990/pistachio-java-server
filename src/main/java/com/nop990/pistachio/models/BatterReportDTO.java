package com.nop990.pistachio.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BatterReportDTO {
    private String name;
    private int age;
    private String team;
    private String minor;
    private int pa;
    private double warOverall;
    private String primaryPosition;
    private String recommendedPositions;
    private String bats;
    private int hr_650;
    private int hrOverall;
    private double obpOverall;
    private double opsPlusOverall;
    private double warPotential;
    private int hrPotential;
    private double obpPotential;
    private double opsPlusPotential;
    private int opsPlusPotentialHasPosition;
    private double dhOverall;
    private double catcherOverall;
    private double firstBaseOverall;
    private double secondBaseOverall;
    private double thirdBaseOverall;
    private double shortstopOverall;
    private double leftFieldOverall;
    private double centerFieldOverall;
    private double rightFieldOverall;
    private double dhPotential;
    private double catcherPotential;
    private double firstBasePotential;
    private double secondBasePotential;
    private double thirdBasePotential;
    private double shortstopPotential;
    private double leftFieldPotential;
    private double centerFieldPotential;
    private double rightFieldPotential;
    private double offensiveWarOverall;
    private double offensiveWarPotential;
    private double catcherDefensiveWar;
    private double firstBaseDefensiveWar;
    private double secondBaseDefensiveWar;
    private double thirdBaseDefensiveWar;
    private double shortstopDefensiveWar;
    private double leftFieldDefensiveWar;
    private double centerFieldDefensiveWar;
    private double rightFieldDefensiveWar;
    private boolean flagged;
}
