//Directory system removed on VFS load/save update.
package x86avm.fs;
import java.util.*;
public class VirtualDirectory{
	//public HashMap<String,VirtualFile> FileList = new HashMap<String,VirtualFile>();
	//public HashMap<String,VirtualDirectory> DirectoryList = new HashMap<String,VirtualDirectory>();
	String Path;
	public VirtualDirectory(VirtualDisk disk, String dirPath) {
		//Path = dirPath;
		//disk.AddDirectory(this);
	}
	public VirtualDirectory(VirtualDirectory dir, String dirPath) {
		//Path = dirPath;
		//dir.DirectoryList.put(this.Path,this);
	}
	public void AddFile(VirtualFile file) {
		//FileList.put(file.name,file);
	}
	public void DeleteFile(VirtualFile file) {
		//FileList.remove(file.name);
	}
	public void DeleteFile(String name) {
		//FileList.remove(name);
	}
	public void AddDirectory(VirtualDirectory dir) {
		//DirectoryList.put(dir.Path,dir);
	}
	public void DeleteDirectory(VirtualDirectory dir) {
		//DirectoryList.remove(dir.Path);
	}
	public void DeleteDirectory(String name) {
		//DirectoryList.remove(name);
	}
	public VirtualFile GetFile(String name) {
		//return this.FileList.get(name);
		return null;
	}
	public VirtualDirectory GetDirectory(String name) {
		//return this.DirectoryList.get(name);
		return null;
	}

}