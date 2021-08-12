package x86avm;

public class jobvm extends OSLoader {

	public jobvm(String btldrname) {
		super(btldrname);
	}
	public static void main(String[] args) {
		String btldrname = null;
		for (String s : args) {
			if (s.startsWith("f:")) {
				s = s.replace("f:", "");
				btldrname = s;
			}
			else if (s.startsWith("--" )) {
				s = s.replace("--", "");
			}
		}
		new jobvm(btldrname);
	}

	@Override
	public void Booted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Run() {
		// TODO Auto-generated method stub
		
	}

}
