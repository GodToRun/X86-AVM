package x86avm.fs;

public class FloppyDrive extends VirtualDisk {
	//Inches
	public static final double inches_3_5 = 3.5;
	public static final double inches_5_25 = 5.25;
	public static final double inches_8 = 8;
	//Sizes
	public static final int kb_50 = 50019;
	public static final int mb_1_2 = 1213952;
	public static final int mb_1_4 = 1457664;
	public FloppyDrive(String diskname, char DriveLetter) {
		super(diskname, DriveLetter);
	}

}
