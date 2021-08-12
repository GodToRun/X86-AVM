package x86avm.example;

import x86avm.OSLoader;
import x86avm.hardware.MemoryAllocationException;

public class Calc extends Process {
	public Calc(byte first,byte two) {
		try {
			OSLoader.Instance.memory.setAddressValue(32768, first);
			OSLoader.Instance.memory.setAddressValue(32769, two);
		} catch (MemoryAllocationException e) {
			// TODO Auto-generated catch block
			OSLoader.Instance.graphic.Println(e.getMessage());
		}
	}
	public byte Add() {
		try {
		return (byte)(OSLoader.Instance.memory.getAddressValue(32768) + OSLoader.Instance.memory.getAddressValue(32769));
		}
		catch (Exception e) { return -128; }
	}
	public byte Multiply() {
		try {
		return (byte)(OSLoader.Instance.memory.getAddressValue(32768) * OSLoader.Instance.memory.getAddressValue(32769));
		}
		catch (Exception e) { return -128; }
	}
	public byte Substract() {
		try {
		return (byte)(OSLoader.Instance.memory.getAddressValue(32768) - OSLoader.Instance.memory.getAddressValue(32769));
		}
		catch (Exception e) { return -128; }
	}
	public byte Division() {
		try {
		return (byte)(OSLoader.Instance.memory.getAddressValue(32768) / OSLoader.Instance.memory.getAddressValue(32769));
		}
		catch (Exception e) { return -128; }
	}
	@Override
	public void ProcessMain() {
		// TODO Auto-generated method stub
		
	}
}
