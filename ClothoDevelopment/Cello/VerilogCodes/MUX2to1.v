module MUX2to1(
     A,
     B,
     S,
     O
    );

input A;
    input B;
    input S;
    output O;

assign O = ~( ~( ~(A | S) | ~(B | ~S)));

endmodule
