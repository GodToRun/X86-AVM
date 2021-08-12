package x86avm.hardware;

import x86avm.OSLoader;

public class Block {
	public int location;
	public static int mem_cur = 0;
	public Block() {
		location = mem_cur;
		mem_cur += 512;
		try {
			OSLoader.Instance.memory.setAddressValue(location, 0);
		} catch (MemoryAllocationException e) {
			
		}
	}
}
