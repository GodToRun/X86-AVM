package x86avm.hardware;


import x86avm.OSLoader;
import x86avm.fs.VirtualDisk;
import x86avm.fs.VirtualFile;
import x86avm.parser.*;

public class CPU {
	private String txt = ""; //nothing
	private String operand; //nothing
	//8Bit registers
	public byte al;
	public byte bl;
	public byte dl;
	public byte cl;
	public byte dh;
	public byte bh;
	public byte ah;
	public byte ch;
	//16Bit registers
	public short ax;
	public short bx;
	public short cx;
	public short dx;
	public short di;
	public short si;
	public short bp;
	public short sp;
	public short ip;
	private Object buf;
	//32Bit registers
	/*public int eax;
	public int ebx;
	public int ecx;
	public int edx;
	public int edi;
	public int esi;
	public int ebp;
	public int esp;
	public int eip;*/
	
	//public VASM vasm = new VASM(this);
	private int strcmp(String str, String str2) {
		if (str.equals(str2))
			return 0;
		else
			return 1;
	}
	public void LoadToRAM(String str,int location) {
		int loc = location;
			for (char c : str.toCharArray()) {
				try {
					OSLoader.Instance.memory.setAddressValue(loc, (byte)c);
				} catch (MemoryAllocationException e) {
					OSLoader.Instance.graphic.Println(e.getMessage());
				}
				loc++;
			}
		
	}
	public int toByteLiteral(int i) {
		return i * 1024;
	}
	
	//CPU ISA Opcode
	public void request(String[] opcodes) {
		BytecodeParser.Parse(opcodes);
	}
}
