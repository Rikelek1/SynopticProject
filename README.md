# __SynopticProject__

A maze game for my Synoptic Project

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

After [loading a valid configuration file](#load-config), you will be able to start a [new game](#game-screen).

When in the game, you must traverse the rooms within the maze, collecting any treasures you find and defeating any threats. The goal is to escape with as much wealth as possible.

Find the exit, and you will see the [game completion screen](#game-completion).

---

## __Game Windows and Menus__

### __Main Menu__

Without a configuration file selected

![Main menu with no config loaded](/res/readme/main_menu_no_config.png)

With a configuration file selected

![Main menu with a config loaded](/res/readme/main_menu_config.png)

### __Player Name__

![Player Name Screen](/res/readme/player_name.png)

### __Load Config__

![Config selection Screen](/res/readme/config_file_select.png)

### __Game Screen__

![Game screen](/res/readme/game_screen.png)

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

![Target selection screen](/res/readme/target_select.png)

### __Game Completion__

![Game completion screen](/res/readme/game_complete_screen.png)
