# X86-AVM
x86 Bytecode Interpreter VM
# HOW TO RUN
First, you need to create directory 'data' and subdirectory 'A'<br>
and, write VASM file, and run jobvm.java with args "jobvm.java f:FileName"
# SIMPLE VASM EXAMPLE
First, you need to copy VASM folder to C Drive. This is code:<br><br>
MOV	SI	"Hello, World!",0;<br>
CALL	PRINTS;<br>
RET;<br>
INCLUDE	C:/VASM/STD/IO.VASM;
# FORM
Don't forget operands need tabs!
