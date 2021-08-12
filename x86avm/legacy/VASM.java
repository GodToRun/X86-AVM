package x86avm.legacy;
/*package jobvm.legacy;
package jobvm.hardware;

import java.lang.reflect.Method;

import jobvm.OSLoader;
import jobvm.Pointer;
import jobvm.misc.Code;

public class VASM {
	
	 * Virtual CPU Assembly
	 * run assembly commands on java!
	 
	CPU cpu;
	public VASM(CPU cpu) {
		this.cpu = cpu;
	}
	public void push(Number num) {
		OSLoader.Instance.memory.push((int)num);
	}
	public void pop() {
		OSLoader.Instance.memory.pop();
	}
	public void times(Code _do, int count) {
		for (int i = 0; i < count; i++) {
			_do.Run();
		}
	}
	public void INT(int api) {
		Interrupt.interrupt((char)api);
	}
	public int call(Method method) {
		try {
			method.invoke(null,"");
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	public void syscall() {
		//WIP
	}
	public void jmp() {
		//WIP
	}
	public void lea(Object value,Pointer p) {
		value = p;
	}
	public int dec(Pointer p) {
		return p.setValue(p.getValue()-1);
	}
	public int inc(Pointer p) {
			return p.setValue(p.getValue()+1);
	}
	public void DB(String value, char newline) {
		cpu.LoadToRAM(value + newline, OSLoader.Instance.memory.get_free());
	}
	public void mov(Object obj1, Object obj2) {
		obj1 = obj2;
	}
	public boolean cmp(Object obj1, Object obj2) {
		return obj1.equals(obj2);
	}
	public void sub(Pointer obj1, int obj2) {
		obj1.setValue(obj1.getValue() - obj2);
	}
	public void add(Pointer obj1, int obj2) {
		obj1.setValue(obj1.getValue() + obj2);
	}
	public void mul(Pointer obj1, int obj2) {
		obj1.setValue(obj1.getValue() * obj2);
	}
	public void div(Pointer obj1, int obj2) {
		obj1.setValue(obj1.getValue() / obj2);
	}
	public void nop() {
		;
	}
	public void noop() {
		;
	}
	public void DO(Code _do) {
		_do.Run();
	}
	public void ret() {
		return;
	}
	public boolean IF(boolean condition) {
		return condition;
	}
	public Pointer DWORD(int data) {
		return OSLoader.Instance.INT(data);
	}
	public Pointer WORD(short data) {
		return OSLoader.Instance.SHORT(data);
	}
	public Pointer BYTE(byte data) {
		return OSLoader.Instance.BYTE(data);
	}
	public int movmem(int address, int value) {
		try {
			OSLoader.Instance.memory.setAddressValue(address, value);
			return 0;
		}
		catch(Exception e) {
			return -1;
		}
	}
}*/