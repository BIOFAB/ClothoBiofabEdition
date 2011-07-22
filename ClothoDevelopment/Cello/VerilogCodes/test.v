module adder_implicit (
result        , // Output of the adder
carry         , // Carry output of adder
r1            , // first input
r2            , // second input
ci              // carry input
);

// Input Port Declarations       
input    [3:0]   r1         ;
input    [3:0]   r2         ;
input            ci         ;

// Output Port Declarations
output   [3:0]  result      ;
output          carry       ;


assign result = ~(~r1 & ~r2);

endmodule
