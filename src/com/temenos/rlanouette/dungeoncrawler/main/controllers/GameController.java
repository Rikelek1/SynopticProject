package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.entities.*;
import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import com.temenos.rlanouette.dungeoncrawler.main.util.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class GameController {
    private GameContext gameContext = GameContext.getInstance();
    private static ItemSelectController itemSelectController;

    private Stage stage;
    private Scene scene;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnNorth, btnEast, btnSouth, btnWest;

    @FXML
    private HBox actionsHBox;

    @FXML
    private TextArea outputConsole;

    @FXML
    private Label lblPlayerName, lblPlayerWealth, lblRoomPosition, lblRoomTreasure, lblRoomThreats, lblRoomMiscItems;

    @FXML
    public void initialize() {
        // Set up the stage
        initStage();

        // Set up the direction buttons
        initDirectionButtons();

        gameContext.reloadMaze();

        // For each action which has been configured, add a button for that action to the "action bar" on the window
        // and bind the "handleActionClick" handler to them
        for(Action action : Action.values()) {
            Button button = new Button(action.printString());
            button.setOnAction(this::handleActionClick);
            actionsHBox.getChildren().add(button);
        }

        // Spawn the player
        spawnPlayer();
    }

    //region Handlers
    private void handleActionClick(ActionEvent actionEvent) {
        Room currentRoom = gameContext.getPlayer().getRoom();
        Button callingButton = (Button) actionEvent.getSource();
        Action action = Action.valueOf(callingButton.getText().replace(" ", "_").toUpperCase());
        System.out.println(action);

        if(action == Action.DROP_COIN) {
            // The player wants to drop a coin
            if(gameContext.getPlayer().getWealth() > 0) {
                // The player has enough money
                if(!currentRoom.hasCoin()) {
                    // There aren't any coins in this room already, so drop one
                    currentRoom.addItem(new Item("Coin", null, null));
                    gameContext.getPlayer().subtractWealth(1);
                    writeToConsole("You deposit a coin onto the floor, making it easier to see if you've been here before..");
                } else {
                    // There is already a coin in this room
                    writeToConsole("Why would you want to drop more than one coin?");
                }
            } else {
                // The player has no money to drop
                writeToConsole("You do not have any coins to drop!");
            }
        } else {
            if (!currentRoom.hasItems()) {
                // The current room doesn't have any items, so tell the player they cannot use the action
                writeToConsole(String.format("There is nothing in this room to %s", action.printString()));
            } else {
                // Set the action target to the first item in the room, this can then be updated with a chosen target
                // if there is more than one item in the room
                Item actionTarget = currentRoom.getItems().get(0);

                if (currentRoom.getItems().size() > 1) {
                    // There is more than 1 item in this room so get the player to choose which one to target
                    Popup.selectItemDisplay(currentRoom.getItems(), "Choose a target", stage);

                    // Get the item from the ItemSelectController, this is why the "injectItemSelectController" method is
                    // needed
                    actionTarget = itemSelectController.getReturnItem();
                }

                if (actionTarget != null) {
                    // The item chosen is valid and exists, so perform the action on it
                    executeAction(action, actionTarget, currentRoom);
                }
            }
        }

        // Update the labels to reflect the new room state after the action has been performed
        updateLabels();
    }

    @FXML
    private void handleDirectionClick(ActionEvent event) {
        // Set the variables to be used
        Room currentRoom = gameContext.getPlayer().getRoom();
        Passage passageToTake = null;
        Point newPosition = currentRoom.getPosition();
        Button sourceButton = (Button) event.getSource();
        Direction direction = null;
        boolean canMove, passageExists;

        switch(sourceButton.getId()) {
            case "btnNorth":
                // North arrow was clicked, so set the direction we want to travel to NORTH and set the new position to
                // be -1 on the y axis (up)
                direction = Direction.NORTH;
                newPosition = new Point(currentRoom.getPosition().x, currentRoom.getPosition().y - 1);
                break;
            case "btnEast":
                // East arrow was clicked, so set the direction we want to travel to EAST and set the new position to
                // be +1 on the x axis (right)
                direction = Direction.EAST;
                newPosition = new Point(currentRoom.getPosition().x + 1, currentRoom.getPosition().y);
                break;
            case "btnSouth":
                // South arrow was clicked, so set the direction we want to travel to SOUTH and set the new position to
                // be +1 on the y axis (down)
                direction = Direction.SOUTH;
                newPosition = new Point(currentRoom.getPosition().x, currentRoom.getPosition().y + 1);
                break;
            case "btnWest":
                // West arrow was clicked, so set the direction we want to travel to WEST and set the new position to
                // be -1 on the x axis (left)
                direction = Direction.WEST;
                newPosition = new Point(currentRoom.getPosition().x - 1, currentRoom.getPosition().y);
                break;
            default:
                writeToConsole("An invalid button has been pressed");
                break;
        }

        // Find out if the passage exists
        passageExists = gameContext.getPlayer().getRoom().validPassage(direction);
        System.out.println(direction + ": " + passageExists);

        // Get the passage that we want to take
        passageToTake = currentRoom.getPassage(direction);

        /*  Set the "canMove" boolean, this just needs to ensure the passage exists and the current room has no threats
            in it.
        */

        canMove = passageExists && !currentRoom.hasThreats();

        if(canMove) {
            // The player can move
            if(!passageToTake.isExit()) {
                // The passage isn't an exit, so move the player
                movePlayer(direction, newPosition);
            } else {
                // The passage is an exit, so complete the game
                completeGame();
            }
        } else {
            // The player can't move, so tell them why
            if(!passageExists) {
                // There isn't a passage for the player to use
                writeToConsole("You cannot travel that way, there is no passage.");
            } else {
                // The only other option is that there is a threat still in the room
                writeToConsole("There are still threats in the room which you must defeat.");
            }
        }
    }
    //endregion

    //region initialisers
    private void initStage() {
        // Add a listener for the scene property of the "mainLayout" BorderPane.
        mainLayout.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // Scene is set for the first time so listen for stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // Stage is set so we can now use it in the controller.
                        stage = (Stage)mainLayout.getScene().getWindow();
                        scene = stage.getScene();

                        // Add a listener to the whole window which will trigger the "reloadGame()" method when the
                        // player presses "r"
                        scene.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.R) {
                                reloadGame();
                            }
                        });

//                        stage.centerOnScreen();
                    }
                });
            }
        });
    }

    private void initDirectionButtons() {
        // Load the arrow image from disk and create an image view for each button which uses the arrow image
        // meaning the image is only loaded once
        Image arrow = new Image(getClass().getClassLoader().getResourceAsStream("images/arrow-icon.png"), 10, 10, true, true);
        ImageView arrowImageViewNorth = new ImageView(arrow);
        ImageView arrowImageViewEast = new ImageView(arrow);
        ImageView arrowImageViewSouth = new ImageView(arrow);
        ImageView arrowImageViewWest = new ImageView(arrow);

        // set the correct arrow orientation for each button
        arrowImageViewNorth.setRotate(arrowImageViewNorth.getRotate() - 90);
        arrowImageViewSouth.setRotate(arrowImageViewSouth.getRotate() + 90);
        arrowImageViewWest.setRotate(arrowImageViewWest.getRotate() + 180);

        // set each button's graphic to the corresponding image
        btnNorth.setGraphic(arrowImageViewNorth);
        btnEast.setGraphic(arrowImageViewEast);
        btnSouth.setGraphic(arrowImageViewSouth);
        btnWest.setGraphic(arrowImageViewWest);
    }

    private void spawnPlayer() {
        // Create a new random which will be used to select a room
        Random random = new Random();
        // Create a new player object using the constructor with the parameters (String name, Room room)
        // The player name is kept the same (as it's the same player), the random created above is then used to pick a
        // random room from the list of available rooms in the maze
        gameContext.setPlayer(new Player(gameContext.getPlayer().getName(),
                gameContext.getMaze().getRoom(random.nextInt(gameContext.getMaze().getRooms().size()))));

        // Tell the player where they have spawned
        writeToConsole(String.format("You have spawned at position %d, %d",
                gameContext.getPlayer().getRoom().getPosition().x, gameContext.getPlayer().getRoom().getPosition().y));

        // Update all the "details" labels to reset the information
        updateLabels();
    }

    private void movePlayer(Direction direction, Point newPosition) {
        // Set the player's room to the room which is at the new position they wish to move to
        gameContext.getPlayer().setRoom(gameContext.getMaze().getRoom(newPosition));

        // Write to the console to tell the player where they've moved to
        writeToConsole(String.format("You move %s into a new room (%d, %d)", direction.printString(), newPosition.x,
                newPosition.y));

        // Update all the labels to reflect this room change
        updateLabels();
    }
    //endregion

    //region Helpers
    public static void injectItemSelectController(ItemSelectController itemSelectCtlr) {
        // We need to set an instance of the ItemSelectController so we can access the "returnValue" from it (the target
        // which the user chooses)
        itemSelectController = itemSelectCtlr;
    }

    private void executeAction(Action action, Item target, Room room) {
        // Check to see if the correct action is being used for the target
        if(target.getDefeatingAction() == action) {
            // It is, so now handle the action based on whether the target is a treasure or a threat
            if (target.getType() == ItemType.THREAT) {
                // The target is a threat, so the only processing we need to do before removing it is telling the user
                // they can now continue
                writeToConsole(String.format("You %s the %s, you are now free to continue your journey.", action.printString(), target.getName()));
            } else {
                // The target is a treasure, so we need to do some more processing before removing it

                // Tell the user they've picked up the item
                writeToConsole(String.format("You %s the %s.", action.printString(), target.getName()));

                // Add the value of the treasure to the player's wealth and update the labels to show the new value
                gameContext.getPlayer().addWealth(((Treasure) target).getValue());
                updateLabels();
            }

            // Remove the item from the room
            room.removeItem(room.getItem(target));
        } else {
            // The correct action was not used, so show the player it was ineffective
            writeToConsole(String.format("You attempt to %s the %s, but it doesn't quite work..", action.printString(), target.getName()));
        }
    }

    private void writeToConsole(String message) {
        outputConsole.appendText(message + "\n");
    }

    private void reloadGame() {
        gameContext.reloadMaze();
        outputConsole.clear();
        spawnPlayer();
    }

    private void completeGame() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/temenos/rlanouette/dungeoncrawler/main/views/GameComplete.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    private void updateRoomDetailsLabel(Label label, Item item) {
        if (!label.getText().equals("")) {
            // The label is not empty, so a comma is needed before adding the item
            label.setText(label.getText() + ", ");
        }

        // The label is empty, meaning this is the first item to be added, so no comma required
        label.setText(label.getText() + item.getName());
    }

    private void updateLabels() {
        // Set all label values to ""
        clearLabels();

        // Set the player name and wealth values
        lblPlayerName.setText(gameContext.getPlayer().getName());
        lblPlayerWealth.setText(String.valueOf("$" + gameContext.getPlayer().getWealth()));

        // Set the room position label to the x and y co-ordinate of the room the player is in
        lblRoomPosition.setText(String.format("%d, %d", gameContext.getPlayer().getRoom().getPosition().x,
                gameContext.getPlayer().getRoom().getPosition().y));

        // Loop through each item in the current room
        for(Item item : gameContext.getPlayer().getRoom().getItems()) {
            if (item.getType() == ItemType.THREAT) {
                // The item is a threat, so pass the room threats label
                updateRoomDetailsLabel(lblRoomThreats, item);
                continue;
            }

            if(item.getType() == ItemType.TREASURE){
                // The item is a threat, so pass the room threats label
                updateRoomDetailsLabel(lblRoomTreasure, item);
                continue;
            }

            // The item doesn't match either of the other criteria, so add it to the "misc" section
            // The item is a threat, so pass the room threats label
            updateRoomDetailsLabel(lblRoomMiscItems, item);
        }

        // Update if there's a coin in the room
        if(gameContext.getPlayer().getRoom().getCoin() != null) {
            updateRoomDetailsLabel(lblRoomMiscItems, gameContext.getPlayer().getRoom().getCoin());
        }
    }

    private void clearLabels() {
        // Set the text of all "details" labels to an empty string
        lblPlayerName.setText("");
        lblPlayerWealth.setText("");
        lblRoomPosition.setText("");
        lblRoomThreats.setText("");
        lblRoomTreasure.setText("");
        lblRoomMiscItems.setText("");
    }
    //endregion
}
