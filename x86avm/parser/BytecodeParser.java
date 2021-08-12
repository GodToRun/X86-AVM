package x86avm.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import x86avm.OSLoader;
import x86avm.hardware.Block;
import x86avm.hardware.Interrupt;
import x86avm.hardware.MemoryAllocationException;
import x86avm.hardware.Register;

public class BytecodeParser {
	static OSLoader bios = OSLoader.Instance;
	//****** BYTE CODE PARSER ***********
	/* BYTE CODE FORM
	 * 
	 * XOR	EAX	EAX
	 * 
	 */
	public static ArrayList<Label> labels = new ArrayList<Label>();
	private static String[] include(String[] code) {
		for (int cur = 0; cur < code.length; cur++) {
			String li = "";
			li = code[cur];
			String[] split = li.split("\t");
			for (int scur = 0; scur < split.length; scur++) {
				String s = split[scur];
				if ("INCLUDE".equals(s.toUpperCase())) {
					String fpath = split[++scur].replace("\"", "");
					String fval = "";
					try {
						FileReader filereader = new FileReader(fpath);
						BufferedReader bufReader = new BufferedReader(filereader);
						String line = "";
						while((line = bufReader.readLine()) != null){
							fval += line;
						}
						bufReader.close();
					}
					catch (Exception e) { e.printStackTrace(); }

					ArrayList<String> mNewList = new ArrayList<String>(Arrays.asList(code));
					for (String _s : fval.split(";"))
						mNewList.add(_s);
					code = mNewList.toArray(new String[mNewList.size()]);
				}

			}

		}
		return code;
	}
	private static String[] scan(String[] code) {
		code = include(code);
		for (int cur = 0; cur < code.length; cur++) {
			String li = "";
			try {
				li = code[cur];
				String[] split = li.split("\t");
				for (int scur = 0; scur < split.length; scur++) {
					String s = split[scur];
					//if ("RET".equals(s.toUpperCase()) || "ENDLABEL".equals(s.toUpperCase())) {
					//	return;
					//}
					if ("LABEL".equals(s.toUpperCase())) {
						labels.add(new Label(split[++scur],cur));
					}
					scur++;
				}
			}
			 catch (ArrayIndexOutOfBoundsException e) { break; }
		}
		return code;
	}
	public static int Parse(String[] code) {
		int cmpstate = 0;
		code = scan(code); //�?�터프리터 특유�?� 문제�?� 순서 문제 방지
		for (int cur = 0; cur < code.length; cur++) {
			String li = "";
			try {
				li = code[cur];
				String[] split = li.split("\t");
				for (int scur = 0; scur < split.length; scur++) {
					String s = split[scur];
					if ("INT".equals(s.toUpperCase())) {
						Interrupt.interrupt(iget(split[++scur]));
					}
					else if ("NOP".equals(s.toUpperCase())) { // No Operation
						scur++;
						continue;
					}
					else if ("#".equals(s.toUpperCase())) {
						scur = 0;
						cur++;
						continue;
					}
					else if ("RET".equals(s.toUpperCase()) || "ENDLABEL".equals(s.toUpperCase())) {
						return 0;
		
					}
					else if ("MOV".equals(s.toUpperCase())) {
						iset(split[scur+1], split[scur+2]);
						scur += 2;
					}
					else if ("LEA".equals(s.toUpperCase())) {
						addrset(split[scur+1], split[scur+2]);
						scur += 2;
					}
					else if ("ALLOC".equals(s.toUpperCase())) {
						Block mb = new Block();
						int addr = mb.location;
						Register.Table.put(split[++scur], addr);
					}
					else if ("JMP".equals(s.toUpperCase())) {
						cur = iget(split[++scur])-1;
						scur = 0;
					}
					else if ("XOR".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) ^ iget(split[scur+2])));
						scur += 2;
					}
					else if ("ADD".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) + iget(split[scur+2])));
						scur += 2;
					}
					else if ("MUL".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) * iget(split[scur+2])));
						scur += 2;
					}
					else if ("AND".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) & iget(split[scur+2])));
						scur += 2;
					}
					else if ("OR".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) | iget(split[scur+2])));
						scur += 2;
					}
					else if ("INC".equals(s.toUpperCase())) {
						iset(split[scur+1], "" + (iget(split[scur+1])+1));
					}
					else if ("DEC".equals(s.toUpperCase())) {
						iset(split[scur+1], "" + (iget(split[scur+1])-1));
					}
					else if ("DIV".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) / iget(split[scur+2])));
						scur += 2;
					}
					else if ("SUB".equals(s.toUpperCase())) {
						iset(split[scur+1], String.valueOf(iget(split[scur+1]) - iget(split[scur+2])));
						scur += 2;
					}
					else if ("CMP".equals(s.toUpperCase())) {
						if (iget(split[scur+1]) == iget(split[scur+2]))
							cmpstate = 1;
						else if (iget(split[scur+1]) != iget(split[scur+2]))
							cmpstate = 2;
						else if (iget(split[scur+1]) > iget(split[scur+2]))
							cmpstate = 3;
						else if (iget(split[scur+1]) < iget(split[scur+2]))
							cmpstate = 4;
						else if (iget(split[scur+1]) >= iget(split[scur+2]))
							cmpstate = 5;
						else if (iget(split[scur+1]) <= iget(split[scur+2]))
							cmpstate = 6;
						else if (iget(split[scur+1]) == 0)
							cmpstate = 7;
						scur += 2;
					}
					else if ("JE".equals(s.toUpperCase())) {
						if (cmpstate == 1) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JNE".equals(s.toUpperCase())) {
						if (cmpstate == 2) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JZ".equals(s.toUpperCase())) {
						if (cmpstate == 7) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JB".equals(s.toUpperCase())) {
						if (cmpstate == 4) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JBE".equals(s.toUpperCase())) {
						if (cmpstate == 6) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JA".equals(s.toUpperCase()) || "JG".equals(s.toUpperCase())) {
						if (cmpstate == 3) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("JAE".equals(s.toUpperCase()) || "JGE".equals(s.toUpperCase())) {
						if (cmpstate == 5) {
							cur = iget(split[scur+1])-1;
							scur = 0;
						}
					}
					else if ("FREE".equals(s.toUpperCase())) {
						OSLoader.Instance.memory.free(addrget(split[++scur]));
						Block.mem_cur -= 512;
					}
					else if ("PUSH".equals(s.toUpperCase())) {
						OSLoader.Instance.memory.push(iget(split[++scur]));
					}
					else if ("POP".equals(s.toUpperCase())) {
						OSLoader.Instance.memory.pop(split[++scur]);
					}
					else if ("CALL".equals(s.toUpperCase())) {
						Parse(Arrays.copyOfRange(code, iget(split[++scur]), code.length));
					}
					scur++;
				}
			}
			 catch (ArrayIndexOutOfBoundsException e) { break; }
		}
		return 0;
	}
	public static int addrget(String obj) {
		int i = Register.NULL;
		for (Map.Entry<String, Integer> entry : Register.Table.entrySet()) {
			if (entry.getKey().equals(obj)) {
				try {
					return entry.getValue();
				} catch (Exception e) {
					
				}
			}
		}
		try { i = Integer.parseInt(obj); } catch (NumberFormatException format) { }
		return i;
	}
	public static int iget(String obj) {
		int i = Register.NULL;
		for (Map.Entry<String, Integer> entry : Register.Table.entrySet()) {
			if (entry.getKey().equals(obj)) {
				try {
					return OSLoader.Instance.memory.getAddressValue(entry.getValue());
				} catch (Exception e) {
					
				}
			}
		}
		for (Label label : labels) {
			if (label.name.equals(obj))
				return label.line;
		}
		try { i = Integer.parseInt(obj); } catch (NumberFormatException format) { }
		return i;
	}
	public static void iset(String obj1, String obj2) {
		int i = 0;
		try {
				for (String s : obj2.split(",")) {
					if (s.contains("\"")) {
						s = s.replace("\"", "");
						//i = 0;
						for (char c : s.toCharArray()) {
							OSLoader.Instance.memory.setAddressValue(Register.Table.get(obj1)+i, c);
							i++;
						}
					}
					else if (s.contains("&")) {
						s = s.replace("&", "");
						OSLoader.Instance.memory.setAddressValue(Register.Table.get(obj1)+i, iget(s));
					}
					else if (obj1.contains("&")) {
						obj1 = obj1.replace("&", "");
						Register.Table.put(obj1, iget(s));
					}
					else
						OSLoader.Instance.memory.setAddressValue(Register.Table.get(obj1)+i, addrget(s));
				}
			
		} catch (MemoryAllocationException e) {
			
		}
		
	}
	public static void addrset(String obj1, String s) {
			Register.Table.put(obj1, addrget(s));
		
	}
}
