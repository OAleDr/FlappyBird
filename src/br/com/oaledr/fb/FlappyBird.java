package br.com.oaledr.fb;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import br.com.oaledr.fb.entity.Bird;
import br.com.oaledr.fb.entity.Medal;
import br.com.oaledr.fb.entity.Pipe;
import br.com.oaledr.fb.util.Canvas;
import br.com.oaledr.fb.util.Hitbox;
import br.com.oaledr.fb.util.ScoreNumber;
import br.com.oaledr.fb.util.Timer;

public class FlappyBird implements Game {
	
	public static final String IMAGE = "flappy.png";
	public static final int SPEED = -100;
	public static final int HOLE_SIZE = 100;

	public double scenario_offset = 0;
	public double ground_offset = 0;
	public ArrayList<Pipe> pipes = new ArrayList<>();
	public ArrayList<Medal> medals = new ArrayList<>();
	public Random random = new Random();
	
	public Bird bird;
	public ScoreNumber scoreNumber;
	public Hitbox groundbox;
	public Timer timer;
	public Timer auxtimer;
	public GameState gameState;
	
	public FlappyBird() {
		this.bird = new Bird(getWidth() / 2 - 70 / 4, getHeight() / 2);
		this.scoreNumber = new ScoreNumber(0);
		this.groundbox = new Hitbox(0, getHeight() - 112, getWidth(), getHeight());
		this.timer = new Timer(5, true, new Runnable() {
			@Override
			public void run() {
				int x = getHeight();
				int y = random.nextInt(getHeight() - 112 - HOLE_SIZE);
				pipes.add(new Pipe(x, y));
				if(random.nextInt(scoreNumber.number + 4) == 0)
					medals.add(new Medal(x + random.nextInt(160) + 15, y + random.nextInt(100) + 30));
			}
		});
		this.gameState = GameState.MENU_MAIN;
	}
	
	public void gameOver() {
		this.pipes = new ArrayList<>();
		this.medals = new ArrayList<>();
		this.bird = new Bird(getWidth() / 2 - 70 / 4, getHeight() / 2);
		this.gameState = GameState.values()[gameState.ordinal() + 1];
		if (scoreNumber.getScore() > ScoreNumber.record)
			ScoreNumber.record = scoreNumber.getScore();
		scoreNumber.setScore(0);
	}
	
	@Override
	public String getTitle() {
		return "Flappy Bird";
	}

	@Override
	public int getWidth() {
		return 384;
	}

	@Override
	public int getHeight() {
		return 512;
	}
	

	@Override
	public void run(double dt) {
		scenario_offset += dt * 25;
		scenario_offset = scenario_offset % 288;
		ground_offset += dt * 100;
		ground_offset = ground_offset % 308;
		
		switch (gameState) {
		case GAME:
			timer.run(dt);
			bird.update(dt);
			bird.updateSprite(dt);
			if (groundbox.intersection(bird.getBox()) == Hitbox.TOP) {
				gameOver();
				return;
			}
			if(groundbox.intersection(bird.getBox()) != 0 && bird.isUseFlap()) {
				bird.setUseFlap(false);
				return;
			}
			if (bird.getY() < -7) {
				bird.setUseFlap(false);
				return;
			}
			for (Pipe pipe : pipes) {
				pipe.run(dt);
				if (pipe.getBoxTop().intersection(bird.getBox()) != 0 || pipe.getBoxLow().intersection(bird.getBox()) != 0) {
					bird.setUseFlap(false);
					return;
				}
				if (!pipe.isCounted() && pipe.getX() < bird.getX()) {
					pipe.setCounted(true);
					scoreNumber.modifyScore(1);
				}
			}
			for(Medal medal : medals) {
				medal.run(dt);
				if(medal.getBox().intersection(bird.getBox()) != 0 && !medal.isCounted() && bird.isUseFlap()) {
					medal.setX(medal.getX() -1000);
					medal.setCounted(true);
				}
			}
			if (pipes.size() > 0 && pipes.get(0).getX() < -70)
				pipes.remove(0);
			if(medals.size() > 0 && medals.get(0).getX() < -70)
				medals.remove(0);
			break;
		case GET_READY:
			auxtimer.run(dt);
			bird.updateSprite(dt);
			break;
		case MENU_MAIN:
			bird.updateSprite(dt);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyEvent(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_SPACE) {
			switch (gameState) {
			case GAME_OVER:
				gameState = GameState.MENU_MAIN;
				break;
			case GAME:
				bird.flap();
				break;
			case GET_READY:
				break;
			case MENU_MAIN:
				this.bird = new Bird(50, getHeight() / 4);
				auxtimer = new Timer(1.6, false, new Runnable() {
					@Override
					public void run() {
						gameState = GameState.values()[gameState.ordinal() + 1];
					}
				});
				gameState = GameState.values()[gameState.ordinal() + 1];
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void drawing(Canvas windows) {
		windows.image(IMAGE, 0, 0, 288, 512, 0, (int) -scenario_offset, 0);
		windows.image(IMAGE, 0, 0, 288, 512, 0, (int) (288 - scenario_offset), 0);
		windows.image(IMAGE, 0, 0, 288, 512, 0, (int) ((288 * 2) - scenario_offset), 0);
		
		pipes.forEach(pipe -> {
			pipe.drawItself(windows);
		});
		medals.forEach(medal -> {
			medal.drawItself(windows);
		});
		
		windows.image(IMAGE, 292, 0, 308, 112, 0, -ground_offset, getHeight() - 112);
		windows.image(IMAGE, 292, 0, 308, 112, 0, 308 - ground_offset, getHeight() - 112);
		windows.image(IMAGE, 292, 0, 308, 112, 0, (308 * 2) - ground_offset, getHeight() - 112);
		
		switch (gameState) {
		case GAME_OVER:
			windows.image(IMAGE, 292, 398, 188, 38, 0, getWidth() / 2 - 188 / 2, 100);
			windows.image(IMAGE, 292, 116, 226, 116, 0, getWidth() / 2 - 226 / 2, getHeight() / 2 - 116 / 2);
			scoreNumber.drawScore(windows, getWidth() / 2 + 50, getHeight() / 2 - 25);
			scoreNumber.drawRecord(windows, getWidth() / 2 + 55, getHeight() / 2 + 16);
			break;
		case GAME:
			bird.drawItself(windows);
			scoreNumber.drawScore(windows, 5, 5);
			break;
		case GET_READY:
			bird.drawItself(windows);
			windows.image(IMAGE, 292, 442, 174, 44, 0, getWidth() / 2 - 174 / 2, getHeight() / 3);
			break;
		case MENU_MAIN:
			bird.drawItself(windows);
			windows.image(IMAGE, 292, 346, 192, 44, 0, getWidth() / 2 - 192 / 2, 100);
			windows.image(IMAGE, 352, 306, 70, 36, 0, getWidth() / 2 - 70 / 2, 175);
			windows.text("Pressione espaço", 60, getHeight() / 2 - 16, 32, Color.WHITE);
			break;
		}
	}
	
	public enum GameState {
		MENU_MAIN,
		GET_READY,
		GAME,
		GAME_OVER;
	}
	
}
