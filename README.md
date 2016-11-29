# SIC-Assembler-Simulator
SIC Assembler and Simulator completely written in Java. Java Swing Library is used for creating the GUI.

# Implementation
A class Memory is created, which contains all registers and and an array is used as consecutive memory locations.

For all tables, HashMaps are used to map from Opcode to Corresponding instruction methods , Symbols to their Address etc.
ram				Memory (Class)
Used as memory for the assembler. Class contains Registers and an array used as the main memory.
OPTAB			Map<String,String>
Mapping of Opcode to Object Code
SYMTAB			Map<String,Integer>
For symbols used in programs and their address
OP				Map<String,Method>
Mapping of Opcodes to their implemented functions
retSub			Stack
For storing and retrieving last instruction address before processing JSUB.

# Instructions Implemented
ADD  AND  STA  STCH  STX  STL  LDA  LDCH  LDX
COMP  MUL  DIV  SUB  OR  LDL  TIX  JSUB  RSUB
JLT  JGT  JEQ  JUMP  

Assembler Directives
BYTE  WORD  RESB  RESW

# Modules

* passOne()	: 	Performs the first pass of the given program. Saves references to SYMTAB.
* exStep()	 : 	Parses each line of code and sets opcode and	operand values.
* execute()	: 	Finds the mapping of corresponding function for each opcode and calls it with designated operand.
* updateRegisters() :	Updates all registers accordingly.
* updateMemory()	: Updates memory locations accordingly.
* checkOpCode()	: Checks if the opcode given is correct.
* storeByte()		: Stores a Byte.
* storeWord()		: Stores a Word

# Running SicEmu
Go to the folder containing SicEmu.java and enter following in the terminal

$ javac SicEmu.java
$ java SicEmu

# General Usage Guide
1.	Write or paste your SIC code on the middle text area.

2.	Press "A" Button on the left hand side to assemble the code.

3.	Press "R" Button on the left hand side to execute the code.

4.	After execution, the Symbol Table on left hand side is updated with all new Symbols and their corresponding memory location.	

5.	After execution, the right hand side memory locations are updated. 
	You can search for a particuar memory location with the top right searchbox and button "Show".		

6.	You can use "St" button to see the execution of each instruction from the beginning and corresponding changes in the memory location.
	The current instruction is highlighted.

7.	Finally, you can press "A" to stop the step execution and pressing "St" again resets the assembler and memory locations from top.


# List of Programs tested
* Adding integers from 1 to n
* Adding elements of an array
* Factorial of n
* Nth Fibonacci Number
* String Concatenation
* Bubble Sort
