package com.haniel.Shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haniel.Shooter.level.Level;
import com.haniel.Shooter.util.GameState;

public class LevelCompleteScreen implements Screen{
	final MyGdxGame game; 
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton buttonContinue;
	private Label continues, score;
	private TextureAtlas atlas;
	private GameState gameState;
	private Level level;
	
	public LevelCompleteScreen (final MyGdxGame gam, GameState gameState, Level level) {
    	this.game = gam;
    	this.gameState = gameState;
    	this.level = level;
    }
	
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	stage.act(Gdx.graphics.getDeltaTime());
    	stage.draw();	
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.setBounds(0, 0, stage.getWidth(), stage.getHeight());	
	}

	public void show() {
		atlas = new TextureAtlas("ui/uiskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
		stage = new Stage();
		table = new Table(skin);
		stage.addActor(table);
		table.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
		
		continues =new Label("Continues: " + gameState.numContinues, skin);
		score = new Label("Your score: " + getScore(gameState.numContinues), skin);
		
		buttonContinue = new TextButton("Continue", skin);
		buttonContinue.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new LevelIntroScreen(game, gameState));
				dispose();				
			}
		});
		table.add(continues).padBottom(30);
		table.row();
		table.add(score).padBottom(30);
		table.row();
		table.add(buttonContinue).width(200).height(40);
	}
	
    private String getScore(int numContinues) {
    	String score = "";
    	if (numContinues == 0) score = "A++";
    	else if (numContinues < 3) score = "A";
    	else if (numContinues < 6) score = "B";
    	else if (numContinues < 9) score = "C";
    	else if (numContinues < 12) score = "D";
    	else score = "F";
    	return score;
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}
	
	public void dispose() {
		stage.dispose();
		level.dispose();
	}

}