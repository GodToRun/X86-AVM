package x86avm;

import x86avm.hardware.MemoryAllocationException;

public class Pointer {
	public int addr;
	private Pointer(int addr) {
		
	}
	private Pointer() {
		
	}
	public int getValue() {
		try {
			return OSLoader.Instance.memory.getAddressValue(addr);
		}
		catch (Exception e) {
			return -1;
		}
	}
	public int setValue(int value) {
		try {
		OSLoader.Instance.memory.setAddressValue(addr, value);
		return 0;
		}
		catch(Exception e) { return -1; }
	}
	public static Pointer ref(int addr) {
		return new Pointer(addr);
	}
}
