# __SynopticProject__

This is a Maze Game which I have created for my Synoptic Project. It is written in Java and utilises the JavaFx framework, which allowed for a more rapid development due to my familiarity and experience with it.

It uses a separate window for the Main Menu, Player Name entry, Game and Game completion screens, supported by model classes for each of the main components of the game (found in the "com.temenos.rlanouette.dungeoncrawler.entities" package). This design, due to the nature of JavaFx uses the Model, View Controller (MVC) architectural pattern, which I then added a static "GameContext" class to in order to keep track of the over-arching properties and game state (player name, score etc). Each of the Models, Views and Controllers can be found in their respective packages (dungeoncrawler.main.controllers, models, views), although the "entities" package acts more as a "models" package.

(Please note: All comments in the code starting with "//region" are Intellij's region system)

GameContext tests are written using Junit.

## __Known Bugs/Issues__

- When launching the game for the first time, or when a player name isn't set, pressing the "Cancel" button on the Set Name window causes the player name to remain empty.

## __Future Improvements__

- Configuration-specific leaderboards to store player scores for each maze.
- Map in the top-right hand corner of the game screen, to allow the player to navigate more easily.
- Room graphics for the center of the game screen, to further improve the player experience and relay information easier.
- Re-write the player action system, to handle all actions, including movement, more efficiently.
- Add the ability to define new/custom actions in a similar fashion to the threats and treasures, to further broaden the customisation and player experience.
- Improve configuration file validation. Add Json schema validation.

---

## __Pre-Requisites__

This game requires Java version 1.8 or above to be installed to be able to run.

---

## __Download and Installation__

Download the latest release [here](https://github.com/Rikelek1/SynopticProject/releases).

Extract the contents of SynopticProject.zip to a folder of your choice.

There are then two ways to run this program:

- __Windows only__
  - Double click the Maze Game.exe
- __Windows, Mac or Linux__
  - Double click the SynopticProject.jar
- __If double clicking the jar doesn't work__
  - Navigate a shell of your choice (powershell, command prompt, bash etc.) to the directory which you extracted the jar file to.
  - Type "java -jar SynopticProject.jar"

---

## __Playing the game__

When launching the game for the first time or without a player name set, you will be greeted with the [name set screen](#player-name).

Once a valid name has been entered, you will be taken to the [main menu](#main-menu).

After [loading a valid configuration file](#load-config), you will be able to start a [new game](#game-screen). The "New Game" button will be greyed out until that point.

When in the game, you must traverse the rooms within the maze, collecting any treasures you find and defeating any threats. The goal is to escape with as much wealth as possible.

**Pressing 'R' at any point will reset the maze to it's original configuration and spawn the player in another, random room**

Find the exit, and you will see the [game completion screen](#game-completion).

---

## __Customisation__

In the [resources folder](https://github.com/Rikelek1/SynopticProject/tree/master/res) there are two files which allow you to customise your experience.

### __threats.json__

You can add any number of threats to this json file as long as you follow the format:

```JSON
    {
        "name": "{NAME}",
        "defeatingAction": "{ACTION}"
    }
```

Where {NAME} is the name of the threat and {ACTION} is one of the 6 valid actions (ATTACK, SAVE, DEFUSE, REMOVE PICK_UP or DROP_COIN).

This will then allow you to use this threat in your maze configuration files.

### __treasures__

As well as threats, you can also add any number of treasures to the game in the treasures.json file. Following the format:

```JSON
{
    "name": "{NAME}",
    "value": "{VALUE}"
}
```

Where once again, {NAME} is the name of the treasure, and {VALUE} is how much gold the player should earn by picking up the treasure.

NOTE: All treasure only has PICK_UP as it's defeating action, no other action can be assigned.

### __Creating custom mazes__

You can create and load any custom configuration files you'd like. The game runs on a grid-based system, allowing you to place a room in any location on a 10x10 (0-based) grid.

Each configuration file should be an array of "room" objects:

```JSON
{
        "position": {
            "x": 0,
            "y": 0
        },
        "items": [
            {
                "name": "Rusty Sword"
            },
            {
                "name": "Wizard"
            }
        ],
        "passages": [
            {
                "direction": "SOUTH"
            }
        ]
    }
```

**Position:** A valid x, y co-ordinate.

**Items:** The names of any treasures and threats you'd like to be present in the room.

**Passages:** 1-4 passages, with the possible directions of "NORTH", "EAST", "SOUTH" or "WEST".

You can look at any of the [default configuration files](https://github.com/Rikelek1/SynopticProject/tree/master/res/configs) for a better idea of how to format custom files.

---

## __Game Windows and Menus__

### __Main Menu__

Without a configuration file selected

![Main menu with no config loaded](/res/images/readme/main_menu_no_config.png)

With a configuration file selected

![Main menu with a config loaded](/res/images/readme/main_menu_config.png)

### __Player Name__

![Player Name Screen](/res/images/readme/player_name.png)

### __Load Config__

![Config selection Screen](/res/images/readme/config_file_select.png)

### __Game Screen__

![Game screen](/res/images/readme/game_screen.png)

- 1 - Player/Room information
  - Shows current player name and wealth.
  - Shows co-ordinates, threats, treasures and items of the current room.
- 2 - Player Action buttons
  - Allows the player to perform actions on items in the current room.
  - Opens the [target select window](#target-selection) if more than one item is in the room.
- 3 - Game output console
  - Relays information back to the player about their actions (movement, actions performed etc).
- 4 - Movement Arrows
  - Used to navigate between rooms.

### __Target Selection__

![Target selection screen](/res/images/readme/target_select.png)

### __Game Completion__

![Game completion screen](/res/images/readme/game_complete_screen.png)
