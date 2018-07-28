package br.com.oaledr.fb.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Canvas {
	
	Graphics2D g;

	public static HashMap<String, BufferedImage> sprites = new HashMap<>();

	public Canvas(Graphics2D g) {
		this.g = g;
		g.setColor(Color.white);
	}

	public void triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color cor) {
		g.setColor(cor);
		g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
	}

	public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color cor) {
		triangle((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2), (int) Math.round(x3), (int) Math.round(y3), cor);
	}

	public void circle(int cx, int cy, int raio, Color cor) {
		g.setColor(cor);
		g.fillOval(cx - raio, cy - raio, raio * 2, raio * 2);
	}

	public void circle(double cx, double cy, int raio, Color cor) {
		circle((int) Math.round(cx), (int) Math.round(cy), raio, cor);
	}

	public void square(int x, int y, int lado, Color cor) {
		g.setColor(cor);
		g.fillRect(x, y, lado, lado);
	}

	public void square(double x, double y, int lado, Color cor) {
		square((int) Math.round(x), (int) Math.round(y), lado, cor);
	}

	public void rectangle(int x, int y, int largura, int altura, Color cor) {
		g.setColor(cor);
		g.fillRect(x, y, largura, altura);
	}

	public void rectangle(double x, double y, int largura, int altura, Color cor) {
		rectangle((int) Math.round(x), (int) Math.round(y), largura, altura, cor);
	}

	public void text(String texto, int x, int y, int tamanho, Color cor) {
		g.setColor(cor);
		g.setFont(new Font("Arial", Font.BOLD, tamanho));
		g.drawString(texto, x, y);
	}

	public void text(String texto, double x, double y, int tamanho, Color cor) {
		text(texto, (int) Math.round(x), (int) Math.round(y), tamanho, cor);
	}

	public void image(String arquivo, int xa, int ya, int larg, int alt, double dir, double x, double y) {
		if (!sprites.containsKey(arquivo)) {
			try {
				sprites.put(arquivo, ImageIO.read(new File(arquivo)));
			} catch (java.io.IOException ioex) {
				throw new RuntimeException(ioex);
			}
		}
		AffineTransform trans = g.getTransform();
		g.rotate(dir, x + larg / 2, y + alt / 2);
		g.drawImage(sprites.get(arquivo), (int) Math.round(x), (int) Math.round(y), (int) Math.round(x) + larg, (int) Math.round(y) + alt, xa, ya, xa + larg, ya + alt, null);
		g.setTransform(trans);
	}
}
