package br.com.oaledr.fb.util;

public class Hitbox {

	public static final int TOP = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 8;
	public static final int BOTTOM = 4;

	private double x, y, Ox, Oy;

	public Hitbox(double x, double y, double Ox, double Oy) {
		if (x < Ox) {
			this.x = x;
			this.Ox = Ox;
		} else {
			this.x = Ox;
			this.Ox = x;
		}
		if (y < Oy) {
			this.y = y;
			this.Oy = Oy;
		} else {
			this.y = Oy;
			this.Oy = y;
		}
	}

	public void moveTo(double x, double y, double Ox, double Oy) {
		if (x < Ox) {
			this.x = x;
			this.Ox = Ox;
		} else {
			this.x = Ox;
			this.Ox = x;
		}
		if (y < Oy) {
			this.y = y;
			this.Oy = Oy;
		} else {
			this.y = Oy;
			this.Oy = y;
		}
	}

	public void moveTo(double dx, double dy) {
		x += dx;
		Ox += dx;
		y += dy;
		Oy += dy;
	}

	public int intersection(Hitbox hb) {
		double w = ((Ox - x) + (hb.Ox - hb.x)) / 2;
		double h = ((Oy - y) + (hb.Oy - hb.y)) / 2;
		double dx = ((Ox + x) - (hb.Ox + hb.x)) / 2;
		double dy = ((Oy + y) - (hb.Oy + hb.y)) / 2;
		if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
			double wy = w * dy;
			double hx = h * dx;
			if (wy > hx) {
				if (wy > -hx)
					return BOTTOM;
				else
					return LEFT;
			} else {
				if (wy > -hx)
					return RIGHT;
				else
					return TOP;
			}
		}
		return 0;
	}

}