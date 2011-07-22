
module NOR1232(
     A,
     B,
     C,
     O1,
     O2
    );

input A;
    input B;
    input C;
    input O1;
    input O2;

assign O1 = ~(A | B);
assign O2 = ~(B | C);

endmodule
