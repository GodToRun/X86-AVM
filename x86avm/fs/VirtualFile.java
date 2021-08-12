package x86avm.fs;
import java.util.*;
public class VirtualFile {
	public String content = "";
	public String name = null;
	public byte size = -128;
	public String Format;
	public VirtualFile(VirtualDisk disk,String Name) {
		name = Name;
		size = (byte)name.length();
		disk.AddFile(this);
	}
	public VirtualFile(VirtualDirectory DIR,String Name) {
		name = Name;
		size = (byte)name.length();
		DIR.AddFile(this);
	}
	public VirtualFile() {
		
	}


}
