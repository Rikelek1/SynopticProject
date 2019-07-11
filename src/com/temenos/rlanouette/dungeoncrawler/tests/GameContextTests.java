package com.temenos.rlanouette.dungeoncrawler.tests;

import com.temenos.rlanouette.dungeoncrawler.entities.Maze;
import com.temenos.rlanouette.dungeoncrawler.main.controllers.GameController;
import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class GameContextTests {
    private GameContext gameContext = GameContext.getInstance();
    private File goodConfig = new File("/configs/default.json");

    @Test
    public void testGoodConfig() {
        assertThat(gameContext.validateConfig(goodConfig), is(equalTo(true)));
    }

    @After
    public void resetMaze() {
        gameContext.setMaze(new Maze());
    }

    @Test
    public void testConfigWithTwoExits() {
        File config = new File("/configs/testing/twoExits.json");
        assertThat(gameContext.validateConfig(config), is(equalTo(false)));
    }

    @Test
    public void testConfigWithNoExit() {
        File config = new File("/configs/testing/noExits.json");
        assertThat(gameContext.validateConfig(config), is(equalTo(false)));
    }

    @Test
    public void testConfigWithNoCoords() {
        File config = new File("/configs/testing/noCoords.json");
        assertThat(gameContext.validateConfig(config), is(equalTo(false)));
    }

    @Test
    public void testConfigWithDuplicateCoords() {
        File config = new File("/configs/testing/duplicateCoords.json");
        assertThat(gameContext.validateConfig(config), is(equalTo(false)));
    }

    @Test
    public void testBlankConfig() {
        File config = new File("/configs/testing/blank.json");
        assertThat(gameContext.validateConfig(config), is(equalTo(false)));
    }
}
