package x86avm.driver;

import java.awt.event.KeyEvent;

import x86avm.OSLoader;

public class Keyboard {
	public KeyEvent key;
	public void sendKeyRequest(KeyEvent k) {
		key = k;
		OSLoader.Instance.keyTyped(k);
	}
}
