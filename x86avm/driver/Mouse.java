package x86avm.driver;

import java.awt.MouseInfo;

public class Mouse {
	public static int getX() {
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	public static int getY() {
		return MouseInfo.getPointerInfo().getLocation().y;
	}
}
