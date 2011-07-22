
module NOR2352(
     A,
     B,
     C,
     D,
     E,
     O1,
     O2
    );
 input A;
    input B;
    input C;
    input D;
    input E;
    output O1;
    output O2;
assign O1 = ~( ~( A | B) | C);

assign O2 = ~( D | E);

endmodule
