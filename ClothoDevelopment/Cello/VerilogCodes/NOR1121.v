module NOR1121(
     A,
    B,
    O
    );

    input A;
    input B;
    output O;
	 
assign O = ~(A | B);
endmodule
