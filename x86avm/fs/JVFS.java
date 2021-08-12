/* 
 * JOB Virtual File system (JVFS)
 * Version 0.4
 * 
 * CHANGES:
 * 
 * Temporally removed Directory System. 
 * 
 * */
package x86avm.fs;

public class JVFS {
	public char[] VOLUME_LABEL = new char[11];
	public int cluster = 512;
	public char[] track = new char[24];
	public char[] sector = new char[track.length*20];
	public static final double Version = 0.4;
	public JVFS(char[] label) {
		this.VOLUME_LABEL = label;
	}
}
