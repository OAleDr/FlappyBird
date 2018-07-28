package br.com.oaledr.fb;

import java.awt.event.KeyEvent;

import br.com.oaledr.fb.util.Canvas;

public interface Game {
	
	String getTitle();

	int getWidth();

	int getHeight();
	
	void run(double dt);
	
	void keyEvent(KeyEvent event);

	void drawing(Canvas windows);
	
}
