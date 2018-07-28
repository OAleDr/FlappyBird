package br.com.oaledr.fb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Launcher {
	
	public static void main(String[] args) {
		new Launcher(new FlappyBird());
	}
	
	private Game game;
	private BufferStrategy strategy;
	
	public Launcher(Game game) {
		this.game = game;
		Canvas canvas = new Canvas();
		JFrame container = new JFrame(game.getTitle());
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
		panel.setLayout(null);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		Rectangle bounds = gs[gs.length - 1].getDefaultConfiguration().getBounds();
		container.setResizable(false);
		container.setBounds(bounds.x + (bounds.width - game.getWidth()) / 2, bounds.y + (bounds.height - game.getHeight()) / 2, game.getWidth(), game.getHeight());
		canvas.setBounds(0, 0, game.getWidth(), game.getHeight());
		panel.add(canvas);
		canvas.setIgnoreRepaint(true);
		container.pack();
		container.setVisible(true);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.keyEvent(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		mainLoop();
	}
	
	private void mainLoop() {
		Timer t = new Timer(5, new ActionListener() {
			public long t0;
			public void actionPerformed(ActionEvent event) {
				long t1 = System.currentTimeMillis();
				if (t0 == 0)
					t0 = t1;
				if (t1 > t0) {
					double dt = (t1 - t0) / 1000.0;
					t0 = t1;
					game.run(dt);
					Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
					g.setColor(Color.black);
					g.fillRect(0, 0, game.getWidth(), game.getHeight());
					game.drawing(new br.com.oaledr.fb.util.Canvas(g));
					strategy.show();
				}
			}
		});
		t.start();
	}

}
