package x86avm.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import x86avm.OSLoader;
import x86avm.VirtualPC;
import x86avm.fs.VirtualDisk;
import x86avm.fs.VirtualFile;
//Work in process
public class VMDriver {
	public VMDriver() {
		
	}
	public void ImportFile(VirtualDisk disk, String path) {
		try {
        	FileReader filereader = new FileReader(path);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            String con = "";
            while((line = bufReader.readLine()) != null){
            	con += line + "\n";
            }
            disk.AddFile(new VirtualFile(disk,con));
            bufReader.close();
        	}
        	catch (Exception e) { e.printStackTrace(); }		
	}
	public void SaveFS(VirtualPC vp) {
		VirtualDisk.Save(vp);
	}
	public void ConnectDrive(VirtualPC vp, String drive_path) {
		//drive_path must be a directory.
		File fn = new File(drive_path);
		if (fn.isDirectory()) {
			VirtualDisk disk = new VirtualDisk(fn.getName(),fn.getName().toCharArray()[0]);
			vp.DiskList.put(fn.getName().toCharArray()[0], disk);
			disk.diskloc.LoadAllData();
			
			disk.diskloc.GetMap().forEach((key,value)->{
				VirtualFile vf = new VirtualFile(disk, key.toString());
				vf.content = value.toString();
				disk.FileList.put(key.toString(), vf);
			});
		}
	}
	
}
