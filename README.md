# PISTACHIO UI

## REQUIREMENTS

A browser of some kind
Java runtime environment
OOTP 26

## Main Steps

1. Double-click run.bat.
    - If a browser window does not open automatically, open your preferred web browser and go to http://localhost:8080.
      This should pull up the Pistachio UI.

2. In OOTP, export your database files (you can only on-demand offline without challenge mode). You will need:

- [ ] coaches.csv
- [ ] players.csv
- [ ] players_batting.csv
- [ ] players_career_batting_stats.csv
- [ ] players_contract.csv
- [ ] players_fielding.csv
- [ ] players_pitching.csv
- [ ] players_roster_status.csv
- [ ] players_scouted_rating.csv
- [ ] teams.csv (Optional)

3. Click 'Options'
    1. Set your csv path (path to your save's import-export/csv folder, e.g. C:\Users\USERNAME\Documents\Out of the Park
       Developments\OOTP Baseball 26\saved_games\YOUR_SAVE.lg\import_export\csv)
    2. Set your scout id (found in coaches.csv or the coach editor in commish mode)
    3. Set the team id (This does not have to be your team, it can be any mlb team. It will be the "Abbreviation" found
       on Home > Settings) [**\*\*\*If you relocate or expand your league, see below\*\*\***](
       #help-ive-relocated-my-teamexpanded-my-league-and-now-i-cant-find-my-players)
    4. Set the gb weight to include more or less players based on their GB rating (pitching). 59 will include GB and
       EX-GB only. 54 is neutral. Less than that goes towards FB and EX-FB. 0 will include all players.
    5. Click 'Apply'

4. Click 'Flagged Players' (Optional)
    1. Enter any players you want to make specific note of. For example, Wil Myers is not automatically included in the
       result set as his projected WAR at every position is < 0.1. You can force Pistachio to include him anyway by
       putting him in the text box. Separate players with a new line (press enter after each name). ***\*\*PLAYER NAMES
       MUST BE EXACT\*\*\***
    2. Click 'Save'
    3. If you have previously set a list of flagged players or pasted it directly into the config/flagged.txt file, you
       can click 'Load' to load that list into the Pistachio UI. This is purely visual unless you plan to make edits, as
       flagged.txt is what the backend reads.

5. Click 'Run Notebook'
    1. This will start the backend process of making player projections
    2. Once finished, the UI might freeze for a few seconds as it loads all the data into the tables
    3. If, after giving it a few seconds, the data does not show up or is from a previous run, click the button with the
       refresh icon (to the left of the Flagged Players button) to load the new data.

4. Good hunting!
    1. Note - if you tick a filter checkbox and the table either doesn't update, just give your browser tab a refresh and
       it should sort itself out.

## Advanced

### Displayed Columns

This dialog is pretty self explanatory. You tick a column to display it, untick it to hide it. You can save your configuration by clicking the "Save" button. This will save your configuration to the config/batter_columns.txt file or config/pitcher_columns.txt file. You can load a configuration by clicking the "Load" button.

### Filtering

I have not implemented the Advanced Filtering dialog yet, but there are some advanced filters you can do. Here is a
list:

- To see all infielders (2B, 3B, SS) or all outfielders (LF, CF, RF), select "Recommended Positions" from the Filter Column
  dropdown and type "IF" or "OF" in the Search
- To see all players that can play a certain set of positions (2B, CF), select "Recommended Positions" from the Filter Column
  dropdown and type "2B, CF" or "CF, 2B" Search
- There are two universal checkboxes, "Show Only Flagged Players" (denoted by a flag icon) and "Show Only Major Leaguers".
  The way Pistachio works, any player with a leauge_id of 1 is considered a major leaguer. This means that KBO, Honkball Hoofdklasse, BNBL, and Indy players will still show up if they meet the rating requirements of the engine.
    - Batters have an extra checkbox, called "Fielder". This will only show players that the Pistachio engine has recommended a position for. This will never include 1B or DH.
- A column does not have to be displayed in the table to filter by it.

### HELP! I'VE RELOCATED MY TEAM/EXPANDED MY LEAGUE AND NOW I CAN'T FIND MY PLAYERS

1. Take a chill pill, you'll get an ulcer.
2. If you've relocated, good news! All you have to do is go to the club_lookup.csv file in the config folder, and change
   whichever team you relocated's abbreviation to your new one. For example, if you relocated the Miami Marlins (MIA) to
   Montreal (MON), simply rename the entry for MIA to MON
3. If you've expanded, you have an extra step or two.
    1. First, either enter commish mode or export the teams.csv file via the Database tab in Game Settings.
    2. Go to each of the teams' Home > Settings pages and note the Abbreviation and the INTERNAL TEAM ID. If you are
       unsure which it is, go to the San Diego Padres and find the row that shows their ID as 23
    3. Add this row to club_lookup.csv. The first column is the numeric ID and the second is the Abbreviation.


## Contribution

To contribute, you'll want to pull down this repo, squirrel_plays' jupiter notebook repo, and the Angular repo. To run locally, the notebook should be renamed to pistachio.ipynb and placed into the java project's root directory. You then need to create a python venv in the root directory called pythonVenv and install jupyter, notebook, numpy, pandas, and toml. Finally, build the Angular project and place the contents of the dist/pistachio-ui/browser folder into src/main/resources/static in the Java project.
