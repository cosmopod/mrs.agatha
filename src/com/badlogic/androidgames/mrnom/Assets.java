package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.impl.AndroidGame;
import com.badlogic.androidgames.framework.impl.AndroidGraphics;

public class Assets {
	private Game game;
	private static volatile Assets instancia = null;
	private Graphics g = null;

	// Imágenes ===============================
	private Pixmap background;
	private Pixmap logo;
	private Pixmap mainMenu;
	private Pixmap buttons;
	private Pixmap help1;
	private Pixmap help2;
	private Pixmap help3;
	private Pixmap numbers;
	private Pixmap ready;
	private Pixmap pause;
	private Pixmap gameOver;
	private Pixmap headUp;
	private Pixmap headLeft;
	private Pixmap headDown;
	private Pixmap headRight;
	private Pixmap tail;
	private Pixmap stain1;
	private Pixmap stain2;
	private Pixmap stain3;
	// Sonidos ================================
	private Sound click;
	private Sound eat;
	private Sound bitten;
	// Música ================================
	private Music music;

	private Assets(Game game) {
		this.game = game;
		g = this.game.getGraphics();
		this.background = g.newPixmap("background.png", PixmapFormat.RGB565);
		this.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
		this.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
		this.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
		this.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
		this.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
		this.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
		this.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
		this.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
		this.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
		this.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
		this.headUp = g.newPixmap("headup.png", PixmapFormat.ARGB4444);
		this.headLeft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
		this.headDown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
		this.headRight = g.newPixmap("headright.png", PixmapFormat.ARGB4444);
		this.tail = g.newPixmap("tail.png", PixmapFormat.ARGB4444);
		this.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444);
		this.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444);
		this.stain3 = g.newPixmap("stain3.png", PixmapFormat.ARGB4444);
		// Sonidos ================================
		this.click = game.getAudio().newSound("click.ogg");
		this.eat = game.getAudio().newSound("eat.ogg");
		this.bitten = game.getAudio().newSound("bitten.ogg");
		// Música ================================
		this.music = game.getAudio().newMusic("music.ogg");
	}

	// Implementación de la versión de Bill Pugh del patrón Singleton

	public synchronized static Assets getInstancia(Game game) {
		if (instancia == null) {
			instancia = new Assets(game);
		}
		return instancia;
	}

	public Pixmap getBackground() {

		return background;
	}

	public Pixmap getLogo() {
		return logo;
	}

	public Pixmap getMainMenu() {
		return mainMenu;
	}

	public Pixmap getButtons() {
		return buttons;
	}

	public Pixmap getHelp1() {
		return help1;
	}

	public Pixmap getHelp2() {
		return help2;
	}

	public Pixmap getHelp3() {
		return help3;
	}

	public Pixmap getNumbers() {
		return numbers;
	}

	public Pixmap getReady() {
		return ready;
	}

	public Pixmap getPause() {
		return pause;
	}

	public Pixmap getGameOver() {
		return gameOver;
	}

	public Pixmap getHeadUp() {
		return headUp;
	}

	public Pixmap getHeadLeft() {
		return headLeft;
	}

	public Pixmap getHeadDown() {
		return headDown;
	}

	public Pixmap getHeadRight() {
		return headRight;
	}

	public Pixmap getTail() {
		return tail;
	}

	public Pixmap getStain1() {
		return stain1;
	}

	public Pixmap getStain2() {
		return stain2;
	}

	public Pixmap getStain3() {
		return stain3;
	}

	public Sound getClick() {
		return click;
	}

	public Sound getEat() {
		return eat;
	}

	public Sound getBitten() {
		return bitten;
	}

	public Music getMusic() {
		return music;
	}

}