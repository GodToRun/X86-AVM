//32BIT MEMORY
package x86avm.hardware;
import java.util.*;

import x86avm.parser.BytecodeParser;
public class Memory {
	private int[] map;
	private int heap;
	private int option;
	private int stack;
	private int stack_top;
	private int size;
	public static final int READ_ONLY = 2;
	public static final int RANDOM_ACCESS = 0;
	public static final int WRITE_ONLY = 1;
	public void setMaxAddress(int size) {
		map = new int[size];
		this.size = size;
		heap = size/2;
		stack = heap+size/2;
	}
	public void push(int dat) {
		if (option != READ_ONLY) {
			//SP를 4만�?� 뺌
			BytecodeParser.iset("SP", String.valueOf(BytecodeParser.iget("SP") - 4));
			try {
				//4+("1"+"${SP}")�?� 값 = SP가 96�?��?�면 1100. 그러면 메모리 주소 1100�? dat �?��?�터를 넣는다.
				this.setAddressValue(4 + Integer.parseInt(String.valueOf("1" + BytecodeParser.iget("SP"))), dat);
			} catch (MemoryAllocationException e) {
				
			}
			//stack_top++;
		}
	}
	public void pop(String register) {
		int SP = 4+Integer.parseInt(String.valueOf("1" + BytecodeParser.iget("SP")));
		//SP를 4만�?� �?�함
		BytecodeParser.iset("SP", String.valueOf(BytecodeParser.iget("SP") + 4));
		//
		try {
			BytecodeParser.iset(register, String.valueOf(this.getAddressValue(SP)));
		} catch (MemoryAllocationException e) {
			
		}
		// 메모리 누수 방지. 스�?�? 있�?� 값�?� 비움
		this.free(SP);
		//free(heap+stack_top);
		//if (stack_top > -1) {
		//	stack_top--;			
		//}
	}
	public int get_free() {
		int memloc = 100;
		for (int b : map) {
			if (b == -128 && map[memloc+50] == -128 && map[memloc-50] == -128) {
				map[memloc] = 0;
				return memloc;
			}
			memloc++;
		}
		return -1;
	}
	public void free(int address) {
		if (option != READ_ONLY)
			map[address] = -128;
	}
	public void setAddressValue(int address, int value) throws MemoryAllocationException {
		if (option != READ_ONLY) {
			if (address > size)
				throw new MemoryAllocationException("Attempted to allocate more than memory.");
			else
				map[address] = value;
		}
		else
			throw new MemoryAllocationException("Attempted to allocate read-only memory.");
	}
	public int getAddressValue(int address) throws MemoryAllocationException {
		if (option != WRITE_ONLY) {
			if (address > size)
				throw new MemoryAllocationException("Attempted to load more than memory.");
			else
				return map[address];
		}
		else throw new MemoryAllocationException("Attempted to load address on write-only memory.");
	}
	public int getMaxAddress() {
		return this.size;
	}
	public Memory(int size) {
		map = new int[size];
		this.size = size;
		heap = size/2;
		stack = heap + size/2;
		this.option = RANDOM_ACCESS;
	}
	public Memory(int size, int OPTION) {
		this(size);
		this.option = OPTION;
	}
	public Memory(int size, int OPTION, int[] INITIAL_VALUE) {
		this(size);
		try {
			for (int i : INITIAL_VALUE)
				setAddressValue(this.get_free(),i);
		} catch (MemoryAllocationException e) {
			
		}
		this.option = OPTION;
	}
	public int[] getMap() {
		return map;
	}
	public Memory() {
		
	}
}