package br.com.oaledr.fb.entity;

import br.com.oaledr.fb.FlappyBird;
import br.com.oaledr.fb.util.Hitbox;
import br.com.oaledr.fb.util.Canvas;

public class Medal {

	private double x, y;
	private boolean counted = false;

	private Hitbox box;

	public Medal(double x, double y) {
		this.x = x;
		this.y = y;
		this.box = new Hitbox(x, y + 47, x + 60, y);
	}

	public void run(double dt) {
		x += FlappyBird.SPEED * dt;
		box.moveTo(FlappyBird.SPEED * dt, 0);
	}

	public void drawItself(Canvas windows) {
		windows.image(FlappyBird.IMAGE, 604, 274, 60, 47, 0, x, y);
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
	
	public void setY(double y) {
		this.y = y;
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

	public Hitbox getBox() {
		return box;
	}

}
