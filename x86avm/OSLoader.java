// JOBVM BIOS
package x86avm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import pw.common.*;
import pw.kit.*;
import x86avm.debug.Window;
import x86avm.driver.*;
import x86avm.fs.JVFS;
import x86avm.fs.VirtualDisk;
import x86avm.hardware.CPU;
import x86avm.hardware.Memory;
import x86avm.hardware.MemoryAllocationException;
import x86avm.hardware.VGA;
import pw.component.*;
class debugwin extends Thread {
	OSLoader BIOS;
	public debugwin(OSLoader BIOS) {
		this.BIOS = BIOS;
	}
	@Override
	public void run() {
		Window deb = new Window(BIOS);
	}
}
public abstract class OSLoader extends GameManager implements KeyListener {
	public static final long OSLOAD_ORG = 0x07C00;
	public JVFS vfs = new JVFS(new char[] {
			'X','8','6','A','V','M','B','O','O','T',' '
	});
	public VGA graphic = new VGA();
	public Memory memory = new Memory();
	public VirtualPC vp = new VirtualPC("JOBVM PC");
	public Keyboard keyboard = new Keyboard();
	public VirtualDisk disk;
	public String BOOT_SECTOR_NAME = "BOOT.IMG"; //Default file: BOOT.IMG
	public VMDriver vmdriver = new VMDriver();
	public FSManager configfs = new FSManager();
	public boolean CursorVisible = true;
	public static OSLoader Instance;
	public CPU cpu = new CPU();
	public Color FontColor = Color.LIGHT_GRAY;
	public final Color BackgroundColor = Color.black;
	public int maxMemory = 65535; //Default: 64KB Memory
	public OSLoader(String btldr) {
		// Boot Load
		super("JOBVM [Press Alt+F4 to Exit]", new Point(750,450));
		BOOT_SECTOR_NAME = btldr;
		try {
			getPanel().setBackground(BackgroundColor);
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					cursorImg, new Point(0, 0), "blank cursor");
			this.getContentPane().setCursor(blankCursor);
			memory.setMaxAddress(maxMemory);
			for (int i = 0; i < memory.getMap().length; i++) {
				memory.free(i);
			}
			Instance = this;
			
			//Interface menu = MenuManager.CreateMenu();
			
			//memory.setAddressValue(10, (byte)1);
			try {
				configfs.LoadAllData();
			} catch (RuntimeException e) { } //PASS
			new CursorShow().start();
			//if (configfs.GetValue("maxmem") == null) {
				//Configuration();
				//disk.setMaximumSize(Integer.parseInt(configfs.GetValue("maxdisk").replace("\n", "")) * 1024);
				//memory.setMaxAddress(Integer.parseInt(configfs.GetValue("maxmem").replace("\n", "")) * 1024);
			//}
			//else {
				//disk.setMaximumSize(Integer.parseInt(configfs.GetValue("maxdisk").replace("\n", "")) * 1024);
				//memory.setMaxAddress(Integer.parseInt(configfs.GetValue("maxmem").replace("\n", "")) * 1024);
			//}
			//maxMemory = Integer.parseInt(configfs.GetValue("maxmem").replace("\n", "")) * 1024;
			VirtualDisk.Load(vp);
			disk = vp.getDisk('A');
			new debugwin(this).start();
			//disk.setMaximumSize(Integer.parseInt(configfs.GetValue("maxdisk").replace("\n", "")) * 1024);
			try {
				memory.setAddressValue((int)OSLOAD_ORG, 0); //Allocation
				//Read boot record
				String sector = vp.getDisk('A').FileList.get(BOOT_SECTOR_NAME).content;
						try {
							cpu.request(sector.split(";"));
						}
				catch (NumberFormatException e) { } //PASS
				//cpu.request(new char[] {
				//		0xB4,0x0E,0xBB,0x00,0x80,0xBE,0x7,0x48,0x65,0x6C,0x6C,0x6F,0x00,0xCD,0x10
				//});
			}
			catch(NullPointerException nullptr) {
				graphic.Println(BOOT_SECTOR_NAME + " not found.\nPress any key to continue...");
				graphic.readKey();
				Shutdown();
			}
			/*int i = 0;
			//Free all memory space
			for (int b : memory.getMap()) {
				free(i);
				i++;
			}*/
		}
		catch (Exception e) {
			e.printStackTrace();
			failed();
		}
		//Auto save
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	VirtualDisk.Save(vp); //Save JVFS
		    }
		});
		Booted();
		
		
	}
	public String IntegerTrim(String msg) {
		return msg.replace(" ", "").replace("\n", "");
	}
	public void failed() {
		graphic.Println("Bootable Image not found\nPress any key to continue...");
		graphic.readKey();
		Shutdown();
	}
	public JPanel getVMPanel() {
		return getPanel();
	}
	//Virtual methods
	public Pointer BYTE(byte dat) {
		int loc = 0;
		try {
			loc = memory.get_free();
			memory.setAddressValue(loc,dat);
		} catch (MemoryAllocationException e) {
			return null; //failed
		}
		return Pointer.ref(loc); //success
	}
	public Pointer SHORT(short dat) {
		int loc = 0;
		try {
			loc = memory.get_free();
			memory.setAddressValue(loc,dat);
		} catch (MemoryAllocationException e) {
			return null; //failed
		}
		return Pointer.ref(loc); //success
	}
	public Pointer INT(int dat) {
		int loc = 0;
		try {
			loc = memory.get_free();
			memory.setAddressValue(loc,dat);
		} catch (MemoryAllocationException e) {
			return null; //failed
		}
		return Pointer.ref(loc); //success
	}
	public void free(int location) {
		memory.free(location);
	}
	void Configuration() {
		graphic.Println("JOBVM Configuration");
		graphic.Println("");
		graphic.Println("Harddisk Settings");
		graphic.Print("Maximum Space (KB)>");
		int value = Integer.parseInt(graphic.GetInput());
		graphic.Println("");
		graphic.Println("");
		graphic.Println("Memory Settings");
		graphic.Print("Maximum Space (KB)>");
	    int maxmem = Integer.parseInt(graphic.GetInput());
	    configfs.SetKey("maxdisk", value + "");
	    configfs.SetKey("maxmem", maxmem + "");
	    configfs.SaveAllData();
 		graphic.Clear();
	}
	public void Shutdown() {
		VirtualDisk.Save(vp);
		System.exit(0);
	}
	@Override
	protected void MainLoop() {
		Run();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		keyboard.key = e;
		if (graphic.InInputState) {
			if (e.getKeyChar() == e.VK_BACK_SPACE) {
				try {
					StringBuilder sb = new StringBuilder(graphic.InputStr);
					sb.deleteCharAt(sb.length() - 1);
					StringBuilder str = new StringBuilder(graphic.StrGraphic);
					str.deleteCharAt(str.length() - 1);
					graphic.InputStr = sb.toString();
					graphic.StrGraphic = str.toString();
				}
				catch (StringIndexOutOfBoundsException se) { }
				return;
			}
			else if (e.getKeyChar() == e.VK_ENTER) {
				graphic.InInputState = false;
				return;
			}
			else {
				graphic.InputStr += e.getKeyChar();
				graphic.Print(e.getKeyChar() + "");
			}
		}
		else if (graphic.InKeyState) {
			graphic.InKey = e.getKeyChar();
			graphic.InKeyState = false;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	protected void DrawScreen(Graphics g) {
		try { 
			graphic.Show(g,FontColor,memory);
			if (!graphic.isConsoleMode()) {
				Thread.sleep(20);
			}
		
		} 
		catch (Exception e) { }
	}
	public abstract void Booted();
	public abstract void Run();
	class CursorShow extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
				if (memory.getAddressValue(10) == 0) {
					memory.setAddressValue(10, (byte)1);
				}
				else {
					memory.setAddressValue(10, (byte)0);
				}
					Thread.sleep(250);
				} catch (Exception e) {
					OSLoader.Instance.graphic.Println(e.getMessage());
				}
			}
			
		}
	}
}
/*class MenuManager extends Thread {
	static Interface inter = null;
	public static Interface CreateMenu() {
		new MenuManager().start();
		return inter;
	}
	@Override
	public void run() {
		inter = new Interface();
	}
} WIP */