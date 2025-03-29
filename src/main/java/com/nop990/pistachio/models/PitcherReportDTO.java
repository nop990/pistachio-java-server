package com.nop990.pistachio.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PitcherReportDTO {
    private String name;
    private int age;
    private String team;
    private String minor;
    private String ip;
    private String _throws;
    private int spOverall;
    private int rpOverall;
    private int spPotential;
    private int rpPotential;
    private int fipOverall;
    private int fipPotential;
    private boolean flagged;
}