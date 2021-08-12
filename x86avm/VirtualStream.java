package x86avm;

import x86avm.fs.VirtualFile;

public class VirtualStream {
	VirtualFile File;
	public VirtualStream(VirtualFile file) {
		File = file;
	}
	public void Write(String INPUT_DATA) {
		File.content += INPUT_DATA;
	}
	public void WriteLine(String INPUT_DATA) {
		File.content += INPUT_DATA + "\n";
	}
	public String Read() {
		return File.content;
	}
	public String[] ReadAllLine() {
		String[] line = File.content.split("\n");
		return line;
	}
}
