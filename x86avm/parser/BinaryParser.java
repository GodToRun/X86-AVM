package x86avm.parser;

import java.io.IOException;
import java.util.Arrays;

import x86avm.OSLoader;
import x86avm.hardware.Interrupt;

public class BinaryParser {
	static OSLoader bios = OSLoader.Instance;
	static int cur = 0;
	//****** MACHINE CODE PARSER ***********
	public static int Parse(char[] opcodes) {
		for (int i = 0; i < opcodes.length; i++) {
			char opcode = ' ';
			try {
				opcode = opcodes[cur];

				if (opcode == 0xCD) {
					Interrupt.interrupt(opcodes[++cur]);
				}
				else if (opcode == 0xB4) {
					bios.cpu.ah = (byte)opcodes[++cur];
				}
				else if (opcode == 0xBB) {
					bios.cpu.bx = (short)IV(opcodes);
				}
				else if (opcode == 0xBA) {
					bios.cpu.dx = (short)IV(opcodes);
				}
				else if (opcode == 0x40) {
					bios.cpu.ax++;
				}
				else if (opcode == 0xB2) {
					bios.cpu.dl = (byte)opcodes[++cur];
				}
				else if (opcode == 0xB5) {
					bios.cpu.ch = (byte)opcodes[++cur];
				}
				else if (opcode == 0xB6) {
					bios.cpu.dh = (byte)opcodes[++cur];
				}
				else if (opcode == 0xB7) {
					bios.cpu.bh = (byte)opcodes[++cur];
				}
				else if (opcode == 0xB9) {
					bios.cpu.cx = (short)IV(opcodes);
				}
				else if (opcode == 0xE8) { //CALL
					int max = cur;
					while (opcodes[max] != 0x00) {
						max++;
					}
					//Parse(Arrays.copyOfRange(opcodes, IV(opcodes), max)); WIP TOO!
				}
				else if (opcode == 0xEB) { //JMP JB
					JUMP(opcodes[++cur]);
				}
				else if (opcode == 0xE9) { //JMP JZ
					JUMP(IV(opcodes));
				}
				else if (opcode == 0xC3) { //RET
					
				}
				else if (opcode == 0x3C) {
					if (bios.cpu.al == opcodes[++cur] && opcodes[++cur] == 0x74) {
						JUMP(opcodes[++cur]);
					}
					else if (bios.cpu.al != opcodes[++cur] && opcodes[++cur] == 0x75) {
						JUMP(opcodes[++cur]);
					}
				}
				else if (opcode == 0x50) {
					bios.memory.push(bios.cpu.ax);
				}
				else if (opcode == 0x51) {
					bios.memory.push(bios.cpu.cx);
				}
				else if (opcode == 0x68) {
					bios.memory.push(IV(opcodes));
				}
				else if (opcode == 0x89) { //MOV EV, GV
					if (opcodes[cur+1] == 0xD8) {
						bios.cpu.ax = bios.cpu.bx;
					}
					else if (opcodes[cur+1] == 0xC0) {
						bios.cpu.ax = bios.cpu.ax;
					}
					else if (opcodes[cur+1] == 0xC8) {
						bios.cpu.ax = bios.cpu.cx;
					}
					else if (opcodes[cur+1] == 0xD0) {
						bios.cpu.ax = bios.cpu.dx;
					}
					else if (opcodes[cur+1] == 0xC3) {
						bios.cpu.bx = bios.cpu.ax;
					}
					else if (opcodes[cur+1] == 0xDB) {
						bios.cpu.bx = bios.cpu.bx;
					}
					else if (opcodes[cur+1] == 0xCB) {
						bios.cpu.bx = bios.cpu.cx;
					}
					else if (opcodes[cur+1] == 0xD3) {
						bios.cpu.bx = bios.cpu.dx;
					}
					else if (opcodes[cur+1] == 0xC1) {
						bios.cpu.cx = bios.cpu.ax;
					}
					else if (opcodes[cur+1] == 0xD9) {
						bios.cpu.cx = bios.cpu.bx;
					}
					else if (opcodes[cur+1] == 0xC9) {
						bios.cpu.cx = bios.cpu.cx;
					}
					else if (opcodes[cur+1] == 0xD1) {
						bios.cpu.cx = bios.cpu.dx;
					}
					else if (opcodes[cur+1] == 0xC2) {
						bios.cpu.dx = bios.cpu.ax;
					}
					else if (opcodes[cur+1] == 0xDA) {
						bios.cpu.dx = bios.cpu.bx;
					}
					else if (opcodes[cur+1] == 0xCA) {
						bios.cpu.dx = bios.cpu.cx;
					}
					else if (opcodes[cur+1] == 0xD2) {
						bios.cpu.dx = bios.cpu.dx;
					}

					cur++;
				}
				else if (opcode == 0x88) { //MOV EB, GB
					if (opcodes[cur+1] == 0xD8) {
						bios.cpu.al = bios.cpu.bl;
					}
					else if (opcodes[cur+1] == 0xC0) {
						bios.cpu.al = bios.cpu.al;
					}
					else if (opcodes[cur+1] == 0xC8) {
						bios.cpu.al = bios.cpu.cl;
					}
					else if (opcodes[cur+1] == 0xD0) {
						bios.cpu.al = bios.cpu.dl;
					}
					else if (opcodes[cur+1] == 0xC3) {
						bios.cpu.bl = bios.cpu.al;
					}
					else if (opcodes[cur+1] == 0xDB) {
						bios.cpu.bl = bios.cpu.bl;
					}
					else if (opcodes[cur+1] == 0xCB) {
						bios.cpu.bl = bios.cpu.cl;
					}
					else if (opcodes[cur+1] == 0xD3) {
						bios.cpu.bl = bios.cpu.dl;
					}
					else if (opcodes[cur+1] == 0xC1) {
						bios.cpu.cl = bios.cpu.al;
					}
					else if (opcodes[cur+1] == 0xD9) {
						bios.cpu.cl = bios.cpu.bl;
					}
					else if (opcodes[cur+1] == 0xC9) {
						bios.cpu.cl = bios.cpu.cl;
					}
					else if (opcodes[cur+1] == 0xD1) {
						bios.cpu.cl = bios.cpu.dl;
					}
					else if (opcodes[cur+1] == 0xC2) {
						bios.cpu.dl = bios.cpu.al;
					}
					else if (opcodes[cur+1] == 0xDA) {
						bios.cpu.dl = bios.cpu.bl;
					}
					else if (opcodes[cur+1] == 0xCA) {
						bios.cpu.dl = bios.cpu.cl;
					}
					else if (opcodes[cur+1] == 0xD2) {
						bios.cpu.dl = bios.cpu.dl;
					}

					cur++;
				}
				else if (opcode == 0xBE) { //MOV SI, ...String
					bios.cpu.si = (short)opcodes[++cur];
				}
				else if (opcode == 0xB8) {
					bios.cpu.ax = (short)IV(opcodes);
				}
				else if (opcode == 0x00) {
					cur++;
					continue;
				}
			} catch (ArrayIndexOutOfBoundsException e) { break; }
		cur++;
		}
		return 0;
	}
	static void JUMP(int address) {
		//cur = address;
		//WIP
	}
	static int IV(char[] opcodes) {
		String word = String.valueOf((short)opcodes[cur+2]) + String.valueOf((short)opcodes[cur+1]);
		cur += 2;
		return Integer.parseInt(word);
	}
}
