package br.com.oaledr.fb.entity;

import br.com.oaledr.fb.FlappyBird;
import br.com.oaledr.fb.util.Hitbox;
import br.com.oaledr.fb.util.Timer;
import br.com.oaledr.fb.util.Canvas;

public class Bird {

	private double x, y, vy;
	private double gravity = 1200;
	private double flap = -330;
	private Hitbox box;
	private Timer sprite_timer;
	private int sprite_state = 0;
	private int[] sprite_states = { 0, 1, 2, 1 };
	private int[] sprites_x = { 528, 528, 446 };
	private int[] sprites_y = { 128, 180, 248 };
	private boolean useFlap = true;
	
	public Bird(double x, double y) {
		this.x = x;
		this.y = y;
		this.vy = 0;
		this.box = new Hitbox(x, y, x + 34, y + 24);
		sprite_timer = new Timer(0.1, true, new Runnable() {
			@Override
			public void run() {
				sprite_state += 1;
				sprite_state = sprite_state % sprite_states.length;
			}
		});
	}

	public void update(double dt) {
		vy += gravity * dt;
		y += vy * dt;
		this.box.moveTo(0, vy * dt);
	}

	public void updateSprite(double dt) {
		sprite_timer.run(dt);
	}

	public void drawItself(Canvas windows) {
		windows.image(FlappyBird.IMAGE, sprites_x[sprite_states[sprite_state]], sprites_y[sprite_states[sprite_state]], 34, 24, Math.atan(vy / 200), x, y);
	}

	public void flap() {
		if (useFlap)
			this.vy = flap;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVy() {
		return vy;
	}
	
	public double getGravity() {
		return gravity;
	}

	public Hitbox getBox() {
		return box;
	}

	public void setUseFlap(boolean useFlap) {
		if(this.useFlap) {
			this.gravity = 600;
			this.vy = -1;
		} 
		this.useFlap = useFlap;
	}
	
	public boolean isUseFlap() {
		return useFlap;
	}

}
