module NOR3341(
     A,
     B,
     C,
     D,
     O
    );

  input A;
    input B;
    input C;
    input D;
    output O;

assign O = ~( ~ (~( A | B) | C) | D);

endmodule
