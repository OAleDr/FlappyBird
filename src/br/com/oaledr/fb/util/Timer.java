package br.com.oaledr.fb.util;

public class Timer {
	
	private double time;
	private double limit;
	private Runnable runnable;
	private boolean repeat;
	private boolean end;

	public Timer(double limit, boolean repeat, Runnable runnable) {
		this.limit = limit;
		this.runnable = runnable;
		this.repeat = repeat;
	}

	public void run(double dt) {
		if (end)
			return;
		time += dt;
		if (time > limit) {
			runnable.run();
			if (repeat) {
				time -= limit;
			} else {
				end = true;
			}
		}
	}
}