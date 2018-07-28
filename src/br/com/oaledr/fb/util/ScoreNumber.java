package br.com.oaledr.fb.util;

import br.com.oaledr.fb.FlappyBird;

public class ScoreNumber {
	
	public int number;
	public String snumber;
	public static int record = 0;
	public static int[][] numberData = {
		{576, 200},
		{578, 236},
		{578, 268},
		{578, 300},
		{574, 346},
		{574, 370},
		{330, 490},
		{350, 490},
		{370, 490},
		{390, 490}
	};
	
	public ScoreNumber(int n) {
		this.number = n;
		setSNumber();
	}

	public void setSNumber() {
		snumber = String.valueOf(number);
	}

	public void setScore(int n) {
		number = n;
		setSNumber();
	}

	public int getScore() {
		return number;
	}

	public void modifyScore(int dn) {
		number += dn;
		setSNumber();
	}

	public void drawScore(Canvas windows, int x, int y) {
		for (int i = 0; i < snumber.length(); i++) {
			drawNumber(windows, Integer.parseInt(snumber.substring(i, i + 1)), (x + 15 * i) + 13, y);
		}
	}

	public void drawRecord(Canvas windows, int x, int y) {
		String srecord = String.valueOf(ScoreNumber.record);
		for (int i = 0; i < srecord.length(); i++) {
			drawNumber(windows, Integer.parseInt(srecord.substring(i, i + 1)), (x + 15 * i) + 10, y);
		}
	}

	public void drawNumber(Canvas windows, int number, double x, double y) {
		windows.image(FlappyBird.IMAGE, numberData[number][0], numberData[number][1], 14, 20, 0, x, y);
	}
}
