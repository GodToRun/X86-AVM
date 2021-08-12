package x86avm.fs;
//LAGECY...
import java.util.*;

import x86avm.FSManager;
import x86avm.OSLoader;
import x86avm.VirtualPC;
import x86avm.driver.*;
import x86avm.fs.*;

import java.io.*;
public class VirtualDisk {
	String name;
	public char DriveLetter;
	public FSManager diskloc = new FSManager();
	//int UnallocatedSector = 0;
	public static final int FILE_MAX = 512;
	public static short cluster = 1024; //DEFAULT: 1KB
	int maxsize = 512; //default Maximum size : 64KB
	public byte[][] sector = new byte[maxsize][FILE_MAX];
	public HashMap<String,VirtualFile> FileList = new HashMap<String,VirtualFile>();
	
	public HashMap<String,VirtualDirectory> DirectoryList = new HashMap<String,VirtualDirectory>();
	public VirtualDisk(String diskname,char DriveLetter) {
		this.DriveLetter = DriveLetter;
		this.name = diskname;
		diskloc.savepath += DriveLetter + "\\";
		OSLoader.Instance.vp.DiskList.put(DriveLetter,this);
	}
	@SuppressWarnings("unchecked")
	public static void Load(VirtualPC vp) {
		for (File fn : new File("data\\").listFiles()) {
			if (fn.isDirectory()) {
				VirtualDisk disk = new VirtualDisk(fn.getName(),fn.getName().toCharArray()[0]);
				vp.DiskList.put((Character)fn.getName().toCharArray()[0], disk);
				disk.diskloc.LoadAllData();
				
				disk.diskloc.GetMap().forEach((key,value)->{
					VirtualFile vf = new VirtualFile(disk, key.toString());
					vf.content = value.toString();
					disk.FileList.put(key.toString().toUpperCase(), vf);
				});
			}
		}
	}
	public byte[][] ListAllSectors() {
		return sector;
	}
	public int GetUnallowcatedAddress() {
		int i = 0;
		for (byte[] sec : sector) {
			if (sec.equals(null) || sec[0] == 0)
				return i;
			i++;
		}
		return -1;
	}
	@SuppressWarnings("unchecked")
	public static void Save(VirtualPC vp) {
		vp.DiskList.forEach((key,value)->{
			value.diskloc.savepath = "data\\" + key + "\\";
			File f = new File("data\\" + key);
			if (f.isDirectory())
				for (File file : new File("data\\" + key + "\\").listFiles()) {
						file.delete();
				}
			else
				f.mkdir();
			value.FileList.forEach((fkey,fvalue)->{
				value.diskloc.SetKey(fkey.toString().toUpperCase(),fvalue.content);
			});
			value.diskloc.SaveAllData();
		});
	}
	public void Format() {
		FileList.clear();
		DirectoryList.clear();
	}
	public void setMaximumSize(int size) {
		sector = new byte[size][FILE_MAX];
	}
	public int getMaximumSize() {
		return sector.length - 1;
	}
	public void AddDirectory(VirtualDirectory dir) {
		DirectoryList.put(dir.Path, dir);
	}
	public void AddFile(VirtualFile file) {
		FileList.put(file.name, file);
	}
	public void DeleteFile(String name) {
		FileList.remove(name);
	}
	public void DeleteDirectory(String name) {
		DirectoryList.remove(name);
	}

}
