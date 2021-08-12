package x86avm.hardware;

import java.awt.Point;

import x86avm.OSLoader;
import x86avm.parser.BytecodeParser;

public class Interrupt {
	public static void interrupt(int type) {
		int	page = 0;
		CPU bios_cpu = OSLoader.Instance.cpu;
		int ah = BytecodeParser.iget("AH");
		int si = BytecodeParser.addrget("SI");
		if (type == 0x10 &&  ah == 0x0E) {
			String buf = "";
			int cur = 0;
			while (OSLoader.Instance.memory.getMap()[si + cur] != 0x00) {
				buf += (char)OSLoader.Instance.memory.getMap()[si + cur];
				cur++;
			}
			OSLoader.Instance.graphic.Print(buf); //WRITE STRING
		}
		else if (type == 0x10 && ah == 0x02) {
			OSLoader.Instance.graphic.CursorLocation = new Point(OSLoader.Instance.cpu.dl*10,OSLoader.Instance.cpu.dh*10);
		}
		else if (type == 0x10 && ah == 0x00) {
			OSLoader.Instance.graphic.GraphicMode();
		}
		else if (type == 0x10 && ah == 0xC) {
			OSLoader.Instance.graphic.GraphicMode();
			page = BytecodeParser.iget("DH");
			OSLoader.Instance.graphic.setPixel(BytecodeParser.iget("CX"), BytecodeParser.iget("DX"), (short)BytecodeParser.iget("AL"));
		}
		else if (type == 0x13) { //16BIT REAL MODE FLOPPY DRIVE HANDLING
			if (ah == 0x02) {
				BytecodeParser.iset("AH", String.valueOf(OSLoader.Instance.vfs.sector[bios_cpu.cl]));
			}
		}
		else if (type == 0x16) {
			if (ah == 0x00) { //READ KEY PRESS
				char key = OSLoader.Instance.graphic.readKey();
				BytecodeParser.iset("AH",String.valueOf((byte)key));
				BytecodeParser.iset("AL",String.valueOf(key));
			}
		}
	}
}
