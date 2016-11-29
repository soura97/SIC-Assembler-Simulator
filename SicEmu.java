import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
class Memory{
	public int A,X,L,CC;
	public int M[];
	public String operand;
	Memory(int size)
	{
		M = new int[size];
	}
}
class SIC{
	static Memory ram = new Memory(4000);
	Map<String , Method> OP;
	Map<String , String> OPTAB;
	Map<String , String> OBJ;
	
	public int START=0;
	SIC() throws Exception
	{
		OP = new HashMap<String , Method>();
		OPTAB = new HashMap<String , String>();	
		String[] opcodes = {"ADD","AND","STA","STX","STCH","STL","LDA","LDCH","LDX","COMP","MUL","DIV","SUB","OR","LDL","TIX"};
		OBJ = new HashMap<String , String>();
		OBJ.put("ADD", "18");
		OBJ.put("AND", "40");
		OBJ.put("STA", "0C");
		OBJ.put("STX", "10");
		OBJ.put("STCH", "54");
		OBJ.put("STL", "14");
		OBJ.put("LDA", "00");
		OBJ.put("LDCH", "50");
		OBJ.put("LDX", "04");
		OBJ.put("COMP", "28");
		OBJ.put("MUL", "20");
		OBJ.put("DIV", "24");
		OBJ.put("SUB", "1C");
		OBJ.put("OR", "44");
		OBJ.put("LDL", "08");
		OBJ.put("AND", "2C");
		OBJ.put("JSUB", "48");
		OBJ.put("RSUB", "4C");
		OBJ.put("JUMP", "3C");
		OBJ.put("JEQ", "30");
		OBJ.put("JGT", "34");
		OBJ.put("JLT", "38");
		for(int i=0;i<opcodes.length;i++)
		{
			StringBuilder temp = new StringBuilder();
			
			Random rnd = new Random();
			int n = 10 + rnd.nextInt(99);
			String object = OBJ.get(opcodes[i]) + Integer.toString(n);			
			OPTAB.put(opcodes[i], object);
		}
		for(String i:opcodes)	
			OP.put(i, SIC.class.getMethod(i));
	}
	int getMemory(int address){	return ram.M[address%4000];	}
	int getA(){	return ram.A;	}
	int getX(){	return ram.X;	}
	int getL(){	return ram.L;	}
	int getCC(){	return ram.CC;	}	
	//int getLOCCTR(){return locctr;	}
	int getSTART(){ return START;	}
	void setOperand(String operand){	ram.operand = operand;	}
	void storeByte(char c,int index){	ram.M[index%4000] = c;	}
	void storeWord(int w,int index){	ram.M[index%4000] = w/65536;ram.M[index%4000+1] = (w%65536)/256;ram.M[index%4000+2] = w%256; 	}
	int opcodecount=0;
	void execute(String opcode,String operand) throws Exception
	{	
		//System.out.print("opcode : "+opcode+" , operand(s) : "+ram.operand);	
		
		if(opcode.equals("START"))	System.out.print("H."+"MYPROG.\nT.");
		else if(opcode.equals("END"))	System.out.print("\nE."+START+"\n");
		else if(OPTAB.containsKey(opcode))							
		{	
			opcodecount++;	
			if(opcodecount%10==0)	System.out.print("\nT.");
			System.out.print(OPTAB.get(opcode)+operand+".");
			
		}
		OP.get(opcode).invoke(null);
		
	}
	boolean checkOpCode(String op){	return OPTAB.containsKey(op);	}
	//fw.close();
	public static void LDA(){	ram.A = ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2];	}
	public static void LDX(){	ram.X = ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2];	}
	public static void LDL(){	ram.L = ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2];	}
	public static void LDCH(){	ram.A = ram.M[Integer.parseInt(ram.operand)];	}
	public static void ADD(){	ram.A += (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void SUB(){	ram.A -= (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void MUL(){	ram.A *= (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void DIV(){	ram.A /= (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void AND(){	ram.A &= (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void OR(){	ram.A |= (ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2]);	}
	public static void STA(){	ram.M[Integer.parseInt(ram.operand)] = ram.A/65536;ram.M[Integer.parseInt(ram.operand)+1] = (ram.A%65536)/256;ram.M[Integer.parseInt(ram.operand)+2] = ram.A%256;	}
	public static void STX(){	ram.M[Integer.parseInt(ram.operand)] = ram.X/65536;ram.M[Integer.parseInt(ram.operand)+1] = (ram.X%65536)/256;ram.M[Integer.parseInt(ram.operand)+2] = ram.X%256;	}
	public static void STL(){	ram.M[Integer.parseInt(ram.operand)] = ram.L/65536;ram.M[Integer.parseInt(ram.operand)+1] = (ram.L%65536)/256;ram.M[Integer.parseInt(ram.operand)+2] = ram.L%256;	}
	public static void STCH(){	ram.M[Integer.parseInt(ram.operand)] = ram.A%256;	}
	public static void COMP(){	
		if(ram.A>(ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2])) ram.CC=1;
		else if(ram.A==(ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2])) ram.CC=0;
		else	ram.CC=-1;
	}
	public static void TIX(){
		ram.X++;
		int op=ram.M[Integer.parseInt(ram.operand)]*256*256+ram.M[Integer.parseInt(ram.operand)+1]*256+ram.M[Integer.parseInt(ram.operand)+2];
		if(ram.X<op) ram.CC=-1;
		else if(ram.X==op) ram.CC=0;
		else	ram.CC=1;
	}
		
}
class ErrorHandler
{
	ErrorHandler(String error)
	{
		 JOptionPane.showMessageDialog(null, error);
	}
}
public class SicEmu
{
	JFrame f;
	JButton run,show,step,stop;
	JTextArea code;
	JTable reg,mem,sym;
	JTextField memStart;
	SIC sic;
	
	int current,locctr;
	Stack retSub;
	Map<String,Integer>	SYMTAB;
	Object last = null;
	SicEmu()
	{
		try{
			sic = new SIC();
		}catch(Exception ec1){	/*System.exit(0);*/System.out.println(ec1.toString());	}
		current = 0;
		//locctr=1000;
		locctr=sic.getSTART();
		retSub = new Stack();
		f = new JFrame("SIC Assembler");
		code = new JTextArea(600,500);
		code.setBounds(250,10,600,500);
		code.setBorder(BorderFactory.createCompoundBorder(code.getBorder(),BorderFactory.createEmptyBorder(10,50,50,50)));
		f.add(code);
		memStart = new JTextField();
		memStart.setBounds(900,10,100,25);
		f.add(memStart);
		show = new JButton("Show");
		show.setBounds(1010,10,80,25);
		show.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String mems[][] = new String[25][2];
				String memHeads[] = {"Address","Value"};
				int start = sic.getSTART();
				try {	start = Integer.parseInt(memStart.getText());	}catch(Exception ec){}
				for(int i=0;i<mems.length;i++)
				{
					mems[i][0] = Integer.toString(start+i);
					mems[i][1] = Integer.toString(sic.getMemory(start+i));					
				}
			        mem.setModel (new javax.swing.table.DefaultTableModel (mems,memHeads));
			}
		});
		f.add(show);
		run = new JButton("R");
		run.setBounds(25,10,75,25);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				passOne();
				
				String[] lines = code.getText().split("\n");
				//locctr = 1000;
				locctr=sic.getSTART();	//changed
				for(current=0;current<lines.length;current++)
				{
					exStep(lines[current]);
				}
			}
		});
		f.add(run);
		step = new JButton("St");
		step.setBounds(100,10,50,25);
		f.add(show);
		step.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String[] lines = code.getText().split("\n");
				Highlighter highlighter = code.getHighlighter();
				HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
				HighlightPainter eraser = new DefaultHighlighter.DefaultHighlightPainter(Color.white);
				if(current >= lines.length)
				{
					current = 0;
					//locctr=1000;
					locctr=sic.getSTART();	//changed
					passOne();
					return;
				}
				exStep(lines[current]);
				current++;				
				try{
					int start =  code.getLineStartOffset(current-1);
					int end =    code.getLineEndOffset(current-1);
					if(last != null)	highlighter.removeHighlight(last);
					last = highlighter.addHighlight(start,end, painter );
				}catch(Exception excp){}
			}
		});
		f.add(step);
		stop = new JButton("A");
		stop.setBounds(150,10,50,25);
		f.add(stop);
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				current = 0;
				passOne();
			}
		});
		f.add(stop);
		String regs[][] = {{"A","000000"},{"X","000000"},{"L","000000"},{"CC","0"}};
		String regHeads[] = {"Reg","Value"};
		reg = new JTable(regs,regHeads);
		reg.setBounds(50,40,150,70);
		f.add(reg);
		String syms[][] = {{"",""}};
		String symHeads[] = {"Symbol","Value"};
		sym = new JTable(syms,symHeads);
		sym.setBounds(50,140,150,270);
		f.add(sym);
		String mems[][] = {{"1000","00"},{"1001","00"},{"1002","00"},{"1003","00"},{"1004","00"},{"1005","00"},{"1006","00"},{"1007","00"},{"1008","00"},{"1009","00"},{"1010","00"},{"1011","00"},{"1012","00"},{"1013","00"},{"1014","00"},{"1015","00"},{"1016","00"},{"1017","00"},{"1018","00"},{"1019","00"},{"1020","00"},{"1021","00"},{"1022","00"},{"1023","00"},{"1024","00"}};
		String memHeads[] = {"Address","Value"};
		mem = new JTable(mems,memHeads);
		mem.setBounds(900,40,200,450);
		f.add(mem);
		f.setSize(1200,600);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void passOne()
	{
		String[] lines = code.getText().split("\n");
		SYMTAB = new HashMap<String ,Integer>();	
		//locctr = 1000;

		String syms[][] = new String[25][2];
		String symHeads[] = {"Symbol","Value"};
		locctr=sic.getSTART();	//changed
		int cnt=0,symcnt=0;
		for(String i:lines)
		{
			String[] words = i.split("\\s");
			if(words.length==2 && words[0].equals("START"))
			{
				locctr=Integer.parseInt(words[1]);
				sic.START=locctr;
			}
			if(words.length > 2 && words[1].equals("BYTE"))
			{
				SYMTAB.put(words[0],locctr);
				syms[symcnt][0] = words[0];
				syms[symcnt][1] = Integer.toString(locctr);
				symcnt++;
				for(char c:words[2].toCharArray())
					sic.storeByte(c,locctr++);
			}
			else if(words.length > 2 && words[1].equals("WORD"))
			{
				SYMTAB.put(words[0],locctr);
				syms[symcnt][0] = words[0];
				syms[symcnt][1] = Integer.toString(locctr);
				symcnt++;
				sic.storeWord(Integer.parseInt(words[2]),locctr);
				locctr += 3;
			}
			else if(words.length > 2 && words[1].equals("RESW"))
			{
				SYMTAB.put(words[0],locctr);
				syms[symcnt][0] = words[0];
				syms[symcnt][1] = Integer.toString(locctr);
				symcnt++;
				locctr += Integer.parseInt(words[2])*3;
			}
			else if(words.length > 2 && words[1].equals("RESB"))
			{
				SYMTAB.put(words[0],locctr);
				syms[symcnt][0] = words[0];
				syms[symcnt][1] = Integer.toString(locctr);
				symcnt++;
				locctr += Integer.parseInt(words[2]);			
			}
			else if(words.length >= 2 && !sic.checkOpCode(words[1]) && !words[1].equals("START") && !words[1].equals("END") && !words[1].equals("JLT") && !words[1].equals("JGT") && !words[1].equals("JEQ") && !words[1].equals("JUMP")&& !words[1].equals("JSUB")&& !words[1].equals("RSUB"))	{
				 String message="###Error###\nUndefined OpCode "+words[1];
				 ErrorHandler tool = new ErrorHandler(message);
				 
			}
			else if(!words[0].equals(""))
			{
				SYMTAB.put(words[0],cnt);
				syms[symcnt][0] = words[0];
				syms[symcnt][1] = Integer.toString(cnt*3);
				symcnt++;	
			}
			cnt++;
		}
		//System.out.println(SYMTAB);
		sym.setModel (new javax.swing.table.DefaultTableModel (syms,symHeads));				
	}
	void exStep(String line)
	{
		String[] words = line.split("\\s");
		if(words.length < 2)	return;
		if(words[1].equals("BYTE") || words[1].equals("WORD") || words[1].equals("RESB") || words[1].equals("RESW")) return;
		
		String jumpcheck = words[1];
		if(jumpcheck.charAt(0)=='J'){
			int cc=sic.getCC();
			if(jumpcheck.equals("JUMP"))
			{
				int jpos = SYMTAB.get(words[2]);
				String[] lines = code.getText().split("\n");
				words = lines[jpos].split("\\s");
				current = jpos;
			}
			else if(jumpcheck.equals("JGT") && cc==1)
			{
				int jpos = SYMTAB.get(words[2]);
				String[] lines = code.getText().split("\n");
				words = lines[jpos].split("\\s");
				current = jpos;
			}
			else if(jumpcheck.equals("JEQ") && cc==0)
			{
				int jpos = SYMTAB.get(words[2]);
				String[] lines = code.getText().split("\n");
				words = lines[jpos].split("\\s");
				current = jpos;
			}
			else if(jumpcheck.equals("JLT") && cc==-1)
			{
				int jpos = SYMTAB.get(words[2]);
				String[] lines = code.getText().split("\n");
				words = lines[jpos].split("\\s");
				current = jpos;
			}
			else if(jumpcheck.equals("JSUB"))
			{
				int jpos = SYMTAB.get(words[2]);
				String[] lines = code.getText().split("\n");
				words = lines[jpos].split("\\s");
				retSub.push(new Integer(current));
				current = jpos;
			}
				
		}	
		else if(jumpcheck.equals("RSUB"))
		{
			int jpos = (Integer) retSub.pop();
			String[] lines = code.getText().split("\n");
			words = lines[jpos].split("\\s");
			current = jpos;
		}	
		String opcode="",operand="";
		if(words[1].charAt(0) != '.')	opcode = words[1];
		if(words.length > 2 && words[2].charAt(0) != '.')
		{	int len=words[2].length();
			if(words[2].charAt(len-1)=='X' && words[2].charAt(len-2)==',')
			{	
				int x=sic.getX();
				int val=SYMTAB.get(words[2].split(",")[0])+x;
				operand = Integer.toString(val);
				//System.out.println(operand);
			}
			else	operand = words[2];
		}
		if(SYMTAB.containsKey(operand))	operand = Integer.toString(SYMTAB.get(operand));
		sic.setOperand(operand);
		
		try{	sic.execute(opcode,operand);}catch(Exception ec2){/*System.out.println(ec2.toString());*/}
		updateRegisters();
		updateMemory();
	}
	void updateRegisters()
	{
		String regs[][] = {{"A","000000"},{"X","000000"},{"L","000000"},{"CC","0"}};
		String regHeads[] = {"Reg","Value"};
		regs[0][1] = Integer.toString(sic.getA());
		regs[1][1] = Integer.toString(sic.getX());
		regs[2][1] = Integer.toString(sic.getL());
		regs[3][1] = Integer.toString(sic.getCC());						
	        reg.setModel (new javax.swing.table.DefaultTableModel (regs,regHeads));
	}
	public void updateMemory()
	{
		String mems[][] = new String[25][2];
		String memHeads[] = {"Address","Value"};
		int start = sic.getSTART();
		try {	start = Integer.parseInt(memStart.getText());	}catch(Exception ec){}
		for(int i=0;i<mems.length;i++)
		{
			mems[i][0] = Integer.toString(start+i);
			mems[i][1] = Integer.toString(sic.getMemory(start+i));					
		}
	        mem.setModel (new javax.swing.table.DefaultTableModel (mems,memHeads));
	}
	public static void main(String args[])
	{
		new SicEmu();
	}
}
