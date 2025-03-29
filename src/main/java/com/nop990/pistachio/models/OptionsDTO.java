package com.nop990.pistachio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionsDTO {
    private String csv_path;
    private int scout_id;
    private String team_id;
    private int gb_weight;
}