module NOR1242(
     A,
     B,
     C,
	  D,
     O1,
     O2
    );


 input A;
    input B;
    input C;
 input D;
    input O1;
    input O2;


assign O1 = ~(A | B);
assign O2 = ~(C | D);

endmodule
