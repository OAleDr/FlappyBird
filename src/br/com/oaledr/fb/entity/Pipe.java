package br.com.oaledr.fb.entity;

import br.com.oaledr.fb.FlappyBird;
import br.com.oaledr.fb.util.Hitbox;
import br.com.oaledr.fb.util.Canvas;

public class Pipe {

	private double x, y;

	private boolean counted = false;

	private Hitbox boxTop;
	private Hitbox boxLow;

	public Pipe(double x, double y) {
		this.x = x;
		this.y = y;
		this.boxTop = new Hitbox(x, y - 270 - 220, x + 52, y);
		this.boxLow = new Hitbox(x, y + FlappyBird.HOLE_SIZE, x + 52, y + FlappyBird.HOLE_SIZE + 442);
	}

	public void run(double dt) {
		x += FlappyBird.SPEED * dt;
		boxTop.moveTo(FlappyBird.SPEED * dt, 0);
		boxLow.moveTo(FlappyBird.SPEED * dt, 0);
	}

	public void drawItself(Canvas windows) {
		windows.image(FlappyBird.IMAGE, 660, 0, 52, 242, 0, x, y + FlappyBird.HOLE_SIZE);
		windows.image(FlappyBird.IMAGE, 660, 42, 52, 200, 0, x, y + FlappyBird.HOLE_SIZE + 242);
		windows.image(FlappyBird.IMAGE, 604, 0, 52, 270, 0, x, y - 270);
		windows.image(FlappyBird.IMAGE, 604, 0, 52, 220, 0, x, y - 270 - 220);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setCounted(boolean counted) {
		this.counted = counted;
	}
	
	public boolean isCounted() {
		return counted;
	}

	public Hitbox getBoxTop() {
		return boxTop;
	}

	public Hitbox getBoxLow() {
		return boxLow;
	}

}
