package x86avm.example; //please edit valid package name.
import x86avm.OSLoader;
public class SimpleOS extends OSLoader {
	public SimpleOS() {
		super("BOOT.VASM"); //Never forget you must call super constructor.
	}
	public static void main(String[] args) {
		new SimpleOS(); //Create instance
	}
	@Override
	public void Booted() { //Active on booted
		
	}
	@Override
	public void Run() { //Main loop
	}
}
//End of Kernel