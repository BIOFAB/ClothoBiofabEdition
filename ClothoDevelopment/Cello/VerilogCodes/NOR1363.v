module NOR1363(
     A,
     B,
     C,
     D,
     E,
     F,
     O1,
     O2,
     O3
    );

input A;
    input B;
    input C;
    input D;
    input E;
    input F;
    output O1;
    output O2;
    output O3;

assign O1 = ~(A | B);
assign O2 = ~(C | D);
assign O3 = ~(E | F);

endmodule
