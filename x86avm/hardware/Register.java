package x86avm.hardware;
import java.util.*;

import x86avm.OSLoader;
public class Register {
	public static final int NULL = 0;
	public static HashMap<String, Integer> Table = new HashMap<String, Integer>();
	static {
		Table.put("AL", ALLOC());
		Table.put("BL", ALLOC());
		Table.put("CL", ALLOC());
		Table.put("DL", ALLOC());
		
		Table.put("AH", ALLOC());
		Table.put("BH", ALLOC());
		Table.put("CH", ALLOC());
		Table.put("DH", ALLOC());
		
		Table.put("AX", ALLOC());
		Table.put("BX", ALLOC());
		Table.put("CX", ALLOC());
		Table.put("DX", ALLOC());
		
		int spa = ALLOC();
		try {
			OSLoader.Instance.memory.setAddressValue(spa, 100);
		} catch (MemoryAllocationException e) {
			
		}
		Table.put("SP", spa);
		Table.put("BP", ALLOC());
		Table.put("SI", ALLOC());
		Table.put("DI", ALLOC());
		
		Table.put("EAX", ALLOC());
		Table.put("EBX", ALLOC());
		Table.put("ECX", ALLOC());
		Table.put("EDX", ALLOC());
		
		Table.put("ESP", ALLOC());
		Table.put("EBP", ALLOC());
		Table.put("ESI", ALLOC());
		Table.put("EDI", ALLOC());
	}
	public static int ALLOC() {
		Block b = new Block(); //alloc register memory block
		return b.location;
	}
}
