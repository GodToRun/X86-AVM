package x86avm;
import java.io.*;
import java.nio.*;
import java.util.*;
public class FSManager {
	public String savepath = "data\\";
	private HashMap<String,String> keys = new HashMap<String,String>();
	public void SetKey(String key, String value) {
		keys.put(key,value);
	}
	public String GetValue(String key) {
		return  keys.get(key);
	}
	public int GetIntegerValue(String key) {
		return Integer.parseInt(keys.get(key).replace("\n", ""));
	}
	public void LoadAllData() {
		File savedir = new File(savepath);
		if (!savedir.exists()) {
			savedir.mkdir();
		}
        for (File key : savedir.listFiles()) {
        	String content = "";
        	try {
        	if (key.isDirectory())
        		continue;
        	FileReader filereader = new FileReader(savepath + key.getName());
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                content += line;
            }
            bufReader.close();
        	}
        	catch (Exception e) { e.printStackTrace(); }
            SetKey(key.getName(), content);
		}
	}
	public HashMap<String,String> GetMap() {
		return this.keys;
	}
	public void SaveAllData() {
		File savedir = new File(savepath);
		if (!savedir.exists()) {
			savedir.mkdir();
		}
		keys.forEach((key,value)->{
		File f = new File(savepath.trim() + key.trim());
		if (f.exists())
			f.delete();
		try {
			new File(f.getPath()).createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f.getPath()),"UTF-8"));
		    writer.write(value);
		    writer.close();
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		});
	}
}
