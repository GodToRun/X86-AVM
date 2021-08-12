package x86avm.debug;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.*;

import pw.kit.*;
import x86avm.OSLoader;
import x86avm.hardware.MemoryAllocationException;
import x86avm.hardware.Register;
import x86avm.parser.BytecodeParser;

public class Window extends GameManager {
	OSLoader bios;
	JList<Object> scrollList = new JList<Object>();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	JTextArea asm = new JTextArea("",7,20);
	JButton exec = new JButton("Execute");
	public Window(OSLoader bios) {
		super("x86 AVM Debugger", new Point(250,450));
		this.bios = bios;
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(scrollList);
		//asm.setBounds(200,100,400,400);
		c.add(asm);
		ActionListener asmexec = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BytecodeParser.Parse(asm.getText().split(";"));
			}
		};
		exec.addActionListener(asmexec);
		c.add(exec);
		c.add(new JScrollPane(scrollList)); 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void DrawScreen(Graphics g) {
		Container c = getContentPane();
		ArrayList<String> regs = new ArrayList<String>();
		try {
			Register.Table.forEach((key,value)->{
				regs.add(key + " [" + value + "]: " + BytecodeParser.iget(key));
			});
			scrollList.setListData(regs.toArray());
		}
	catch (Exception nullptr) { }
	}

	@Override
	protected void MainLoop() {
		// TODO Auto-generated method stub
		
	}

}
