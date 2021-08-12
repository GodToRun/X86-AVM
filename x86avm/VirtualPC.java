package x86avm;

import java.util.*;

import x86avm.driver.*;
import x86avm.fs.VirtualDisk;

public class VirtualPC {
	public String name;
	public HashMap<Character,VirtualDisk> DiskList = new HashMap<Character,VirtualDisk>();
	public VirtualPC(String PCNAME) {
		name = PCNAME;
	}
	public void setDisk(Character c,VirtualDisk disk) {
		DiskList.put(c, disk);
	}
	public VirtualDisk getDisk(Character c) {
		return DiskList.get(c);
	}
}
