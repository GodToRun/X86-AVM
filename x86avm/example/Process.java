package x86avm.example;

import x86avm.OSLoader;
import x86avm.hardware.MemoryAllocationException;

//Runtme Process Library
public abstract class Process extends Thread {
	public Process() {
		try {
			OSLoader.Instance.memory.setAddressValue(32767, (byte)0);
		}
		catch (MemoryAllocationException e) { OSLoader.Instance.graphic.Println(e.getMessage());}
	}
	public abstract void ProcessMain();
	@Override
	public void run() {
		ProcessMain();
	}
}
