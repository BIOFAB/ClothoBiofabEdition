import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;


public class __Test__ {

    public static void main(String args[]) throws Exception {
        VerilogALexer lex = new VerilogALexer(new ANTLRFileStream("C:\\Users\\rozagh\\Documents\\NetBeansProjects\\Clotho\\ClothoDevelopment\\Cello\\ANTLRVerilog\\output\\__Test___input.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        VerilogAParser g = new VerilogAParser(tokens, 9963, null);
        try {
            g.source_text();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}