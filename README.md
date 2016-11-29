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

# Instructions implemented
* *ADD*
* *AND*
* *STA*
* *STCH*
* *STX*
* *STL*
* *LDA*
* *LDCH*
* *LDX*
* *COMP*	
* *MUL*
* *DIV*
* *SUB*
* *OR*
* *LDL*
* *TIX*
* *JSUB*
* *RSUB*
* *JLT*	
* *JGT*
* *JEQ*
* *JUMP*
* *START*
* *END*

Assembler Directives
* *BYTE*
* *WORD*
* *RESB*
* *RESW*
 

