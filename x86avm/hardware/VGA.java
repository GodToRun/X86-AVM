package x86avm.hardware;

import java.awt.*;

import javax.swing.JPanel;

import x86avm.OSLoader;
/*
 * Virtual VGA
 * 
 */
public class VGA {
	public String StrGraphic = "";
	public Point CursorLocation = new Point(5,20);
	public static final short WHITE = 15;
	public static final short LIGHTGRAY = 7;
	public static final short DARK_GRAY = 8;
	public static final short CYAN = 3;
	public static final short BLACK = 0;
	public static final short RED = 4;
	public static final short GREEN = 2;
	public static final short MAGENTA = 5;
	public static final short BROWN = 6;
	public static final short BLUE = 1;
	public static final short LIGHT_BLUE = 9;
	public static final short LIGHT_GREEN = 10;
	public static final short LIGHT_CYAN = 11;
	public static final short LIGHT_RED = 12;
	public static final short YELLOW = 14;
	public static final short LIGHT_MAGENTA = 13;
	public boolean InInputState = false;
	public boolean InKeyState = false;
	private boolean mode = false; //Graphic Mode
	char CursorCharacter = '_';
	int y = 20;
	public short[][] display = new short[480][320]; //VGA. 320x200 
	public Memory ram = new Memory(256 * 1024); //256 KB RAM
	public char InKey = ' ';
	public boolean CursorVisible = false;
	public String InputStr = "";
	public void Clear() {
		if (mode) {
			short x = 0;
			short y = 0;
			for (short[] yvec : display) {
				for (short ymeta : yvec) {
					display[x][y] = 0;
					y++;
				}
				x++;
				y = 0;
			}
		}
		StrGraphic = "";
	}
	public void drawString(Graphics g, String text, int x, int y, int col) {
		g.setColor(ConvertColor((short)col));
		g.drawString(text, x, y);
	}
	public void setPixel(int x, int y, short color) {
		display[x][y] = color;
	}
	public void FilledRectagle(int x0, int y0, int x1, int y1, short color) {
		int rx = x0;
		int ry = y0;
		for (int x = 0; x < x1 - x0; x++ ) { //drawLine(2,300, ...)
			for (int y = 0; y < y1 - y0; y++) {
				setPixel(rx,ry,color);
				ry++;
			}
			ry = y0;
			rx++;
		}
	}
	public void GraphicMode() {
		OSLoader.Instance.CursorVisible = false;
		mode = true;
	}
	public void Draw(Color bgColor, Graphics g) {
		g.setColor(bgColor);
		short x = 0;
		short y = 0;
		for (short[] yvec : display) {
			for (short ymeta : yvec) {
				//System.out.println("x: " + x + " y: " + y);
				g.setColor(ConvertColor(ymeta));
				g.drawLine(x, y, x, y);
				y++;
			}
			x++;
			y = 0;
		}
	}
	//BIOS Color attributes
	private Color ConvertColor(short classic) {
		if (classic == 0) {
			return Color.BLACK;
		}
		else if (classic == 1) {
			return Color.BLUE;
		}
		else if (classic == 2) {
			return Color.GREEN;
		}
		else if (classic == 3) {
			return Color.CYAN;
		}
		else if (classic == 4) {
			return Color.RED;
		}
		else if (classic == 5) {
			return Color.MAGENTA;
		}
		else if (classic == 6) {
			return new Color(143, 66, 14);
		}
		else if (classic == 7) {
			return Color.LIGHT_GRAY;
		}
		else if (classic == 8) {
			return Color.DARK_GRAY;
		}
		else if (classic == 9) {
			return new Color(52, 207, 235);
		}
		else if (classic == 10) {
			return new Color(89, 255, 106);
		}
		else if (classic == 11) {
			new Color(105, 143, 214);
		}
		else if (classic == 12) {
			return new Color(255, 97, 97);
		}
		else if (classic == 13) {
			return new Color(237, 71, 234);
		}
		else if (classic == 14) {
			return Color.YELLOW;
		}
		else if (classic == 15) {
			return Color.WHITE;
		}
		else {
			return Color.black;
		}
		return null;
	}
	public void ConsoleMode() {
		OSLoader.Instance.CursorVisible = true;
		mode = false;
	}
	public boolean isConsoleMode() {
		return OSLoader.Instance.CursorVisible;
	}
	public void Show(Graphics g,Color c, Memory memoryInstance) {
		if (!mode) {
			g.setFont(new Font("Arial",Font.BOLD, 15));
			g.setColor(c);
			int i = 0;
			for (String line : StrGraphic.split("\n")) {
				if (y > 400) {
					StrGraphic = StrGraphic.substring(StrGraphic.indexOf('\n')+1);
				}
				try { 
					if (OSLoader.Instance.CursorVisible && memoryInstance.getAddressValue(10) == 0 && StrGraphic.split("\n").length - 1 == i) {
						g.drawString(line, 5, y);
						g.drawString(CursorCharacter + "", CursorLocation.x, CursorLocation.y);
					}
					else {
						g.drawString(line, 5, y);
					}
				}
				catch (MemoryAllocationException e) { Println(e.getMessage());}


				i++;
				y += 20;
			}
			y = 20;
		}
	}
	public void Println(String STR) {
		StrGraphic += STR + "\n";
	}
	public void Print(String STR) {
		StrGraphic += STR;
	}
	public String GetInput() {
		InInputState = true;
		while (InInputState) {
			System.out.print("");
		}
		String tmp = InputStr + "";
		InputStr = "";
		return tmp;
	}
	public char readKey() {
		InKeyState = true;
		while (InKeyState) {
			System.out.print("");
		}
		char tmp = InKey;
		InKey = ' ';
		return tmp;
	}
}
