
module NOR1111(
	 A,
    O
    );
input A;
output O;	 
	assign O = ~(A | A);
endmodule
