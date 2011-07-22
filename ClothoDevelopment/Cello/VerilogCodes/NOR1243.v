module NOR1243(
    input A,
    input B,
    input C,
    input D,
    output O1,
    output O2
    );

assign O1 = ~(A | B);
assign O2 = ~(C | D);

endmodule
