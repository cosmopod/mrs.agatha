package com.badlogic.androidgames.mrnom;

import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	World world;
	int oldScore = 0;
	String score = "0";
	MediaPlayer mediaPLayer = new MediaPlayer();
	private Assets assets;

	public GameScreen(Game game) {
		super(game);
		world = new World();
		assets = Assets.getInstancia(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0) {
			state = GameState.Running;
			if (Settings.soundEnabled) {
				assets.getMusic().play();
				assets.getMusic().setLooping(true);
			}
		}

	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();

		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < 64 && event.y < 64) {
					if (Settings.soundEnabled) {
						assets.getClick().play(1);

					}

					state = GameState.Paused;
					// poner .pause() cuando mejore la API
					assets.getMusic().stop();
					return;
				}
			}
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (event.x < 64 && event.y > 416) {
					world.snake.turnLeft();
				}
				if (event.x > 256 && event.y > 416) {
					world.snake.turnRight();
				}
			}
		}

		world.update(deltaTime);
		if (world.gameOver) {
			if (Settings.soundEnabled) {
				assets.getBitten().play(1);
				assets.getMusic().stop();

			}

			state = GameState.GameOver;
		}
		if (oldScore != world.score) {
			oldScore = world.score;
			score = "" + oldScore;
			if (Settings.soundEnabled) {
				assets.getEat().play(1);

			}

		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 80 && event.x <= 240) {
					if (event.y > 100 && event.y <= 148) {
						if (Settings.soundEnabled) {
							assets.getClick().play(1);
							assets.getMusic().play();

						}
						state = GameState.Running;
						return;
					}
					if (event.y > 148 && event.y < 196) {
						if (Settings.soundEnabled) {
							assets.getClick().play(1);
							assets.getMusic().stop();

						}
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x >= 128 && event.x <= 192 && event.y >= 200
						&& event.y <= 264) {
					if (Settings.soundEnabled) {
						assets.getClick().play(1);
						assets.getMusic().stop();

					}

					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(assets.getBackground(), 0, 0);
		drawWorld(world);
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

		drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2,
				g.getHeight() - 42);
	}

	private void drawWorld(World world) {
		Graphics g = game.getGraphics();
		Snake snake = world.snake;
		SnakePart head = snake.parts.get(0);
		Stain stain = world.stain;

		Pixmap stainPixmap = null;
		if (stain.type == Stain.TYPE_1)
			stainPixmap = assets.getStain1();
		if (stain.type == Stain.TYPE_2)
			stainPixmap = assets.getStain2();
		if (stain.type == Stain.TYPE_3)
			stainPixmap = assets.getStain3();
		int x = stain.x * 32;
		int y = stain.y * 32;
		g.drawPixmap(stainPixmap, x, y);

		int len = snake.parts.size();
		for (int i = 1; i < len; i++) {
			SnakePart part = snake.parts.get(i);
			x = part.x * 32;
			y = part.y * 32;
			g.drawPixmap(assets.getTail(), x, y);
		}

		Pixmap headPixmap = null;
		if (snake.direction == Snake.UP)
			headPixmap = assets.getHeadUp();
		if (snake.direction == Snake.LEFT)
			headPixmap = assets.getHeadLeft();
		if (snake.direction == Snake.DOWN)
			headPixmap = assets.getHeadDown();
		if (snake.direction == Snake.RIGHT)
			headPixmap = assets.getHeadRight();
		x = head.x * 32 + 16;
		y = head.y * 32 + 16;
		g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2,
				y - headPixmap.getHeight() / 2);
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(assets.getReady(), 47, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(assets.getButtons(), 0, 0, 0, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
		g.drawPixmap(assets.getButtons(), 0, 416, 0, 64, 64, 64);
		g.drawPixmap(assets.getButtons(), 256, 416, 64, 64, 64, 64);
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(assets.getPause(), 80, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(assets.getGameOver(), 62, 100);
		g.drawPixmap(assets.getButtons(), 128, 200, 64, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for (int i = 0; i < len; i++) {
			char character = line.charAt(i);

			if (character == ' ') {
				x += 20;
				continue;
			}

			int srcX = 0;
			int srcWidth = 0;
			if (character == '.') {
				srcX = 200;
				srcWidth = 10;
			} else {
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}

			g.drawPixmap(assets.getNumbers(), x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
		if (Settings.soundEnabled) {
			assets.getMusic().stop();

		}

		if (world.gameOver) {
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
			if (Settings.soundEnabled) {
				assets.getMusic().stop();
			}
		}
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
