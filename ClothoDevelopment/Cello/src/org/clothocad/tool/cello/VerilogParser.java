// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g 2011-02-16 15:51:13

package org.clothocad.tool.cello;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.debug.*;
import java.io.IOException;
public class VerilogParser extends DebugParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "LPAREN", "COMMA", "RPAREN", "DOT", "LCURLY", "RCURLY", "LBRACK", "COLON", "RBRACK", "ASSIGN", "NUMBER", "KW_TRIREG", "KW_ASSIGN", "POUND", "IDENTIFIER", "LE", "TRIGGER", "SYSTEM_TASK_NAME", "PPATH", "FPATH", "PLUS", "MINUS", "STRING", "DEFINE", "QUESTION", "LNOT", "BNOT", "BAND", "RNAND", "BOR", "RNOR", "BXOR", "BXNOR", "STAR", "DIV", "MOD", "EQUAL", "NOT_EQ", "EQ_CASE", "NOT_EQ_CASE", "LAND", "LOR", "LT_", "GT", "GE", "SR", "SL", "ESCAPED_IDENTIFIER", "AT", "VOCAB", "SIZE", "BASE", "SIZED_DIGIT", "SIZED_NUMBER", "UNSIZED_NUMBER", "DIGIT", "HEXDIGIT", "EXPONENT", "SPACE_OR_TAB", "WS", "ML_COMMENT", "SL_COMMENT", "'module'", "'macromodule'", "'endmodule'", "'primitive'", "'endprimitive'", "'initial'", "'1\\'b0'", "'1\\'b1'", "'1\\'bx'", "'table'", "'endtable'", "'task'", "'endtask'", "'function'", "'endfunction'", "'integer'", "'real'", "'parameter'", "'input'", "'output'", "'inout'", "'wire'", "'tri'", "'tri1'", "'supply0'", "'wand'", "'triand'", "'tri0'", "'supply1'", "'wor'", "'trior'", "'scalared'", "'vectored'", "'reg'", "'time'", "'event'", "'defparam'", "'small'", "'medium'", "'large'", "'strong0'", "'pull0'", "'weak0'", "'highz0'", "'strong1'", "'pull1'", "'weak1'", "'highz1'", "'and'", "'nand'", "'or'", "'nor'", "'xor'", "'xnor'", "'buf'", "'bufif0'", "'bufif1'", "'not'", "'notif0'", "'notif1'", "'pulldown'", "'pullup'", "'nmos'", "'rnmos'", "'pmos'", "'rpmos'", "'cmos'", "'rcmos'", "'tran'", "'rtran'", "'tranif0'", "'rtranif0'", "'tranif1'", "'rtranif1'", "'always'", "'if'", "'else'", "'endcase'", "'case'", "'casez'", "'casex'", "'default'", "'forever'", "'repeat'", "'while'", "'for'", "'wait'", "'disable'", "'begin'", "'end'", "'fork'", "'join'", "'deassign'", "'force'", "'release'", "'specify'", "'endspecify'", "'specparam'", "'$setup'", "'$hold'", "'$period'", "'$width'", "'$skew'", "'$recovery'", "'$setuphold'", "'&&&'", "'posedge'", "'negedge'", "'edge'", "'0x'", "'1x'", "'`define'", "'`include'"
    };
    public static final int EOF=-1;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int T__123=123;
    public static final int T__124=124;
    public static final int T__125=125;
    public static final int T__126=126;
    public static final int T__127=127;
    public static final int T__128=128;
    public static final int T__129=129;
    public static final int T__130=130;
    public static final int T__131=131;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int T__136=136;
    public static final int T__137=137;
    public static final int T__138=138;
    public static final int T__139=139;
    public static final int T__140=140;
    public static final int T__141=141;
    public static final int T__142=142;
    public static final int T__143=143;
    public static final int T__144=144;
    public static final int T__145=145;
    public static final int T__146=146;
    public static final int T__147=147;
    public static final int T__148=148;
    public static final int T__149=149;
    public static final int T__150=150;
    public static final int T__151=151;
    public static final int T__152=152;
    public static final int T__153=153;
    public static final int T__154=154;
    public static final int T__155=155;
    public static final int T__156=156;
    public static final int T__157=157;
    public static final int T__158=158;
    public static final int T__159=159;
    public static final int T__160=160;
    public static final int T__161=161;
    public static final int T__162=162;
    public static final int T__163=163;
    public static final int T__164=164;
    public static final int T__165=165;
    public static final int T__166=166;
    public static final int T__167=167;
    public static final int T__168=168;
    public static final int T__169=169;
    public static final int T__170=170;
    public static final int T__171=171;
    public static final int T__172=172;
    public static final int T__173=173;
    public static final int T__174=174;
    public static final int T__175=175;
    public static final int T__176=176;
    public static final int T__177=177;
    public static final int T__178=178;
    public static final int T__179=179;
    public static final int SEMI=4;
    public static final int LPAREN=5;
    public static final int COMMA=6;
    public static final int RPAREN=7;
    public static final int DOT=8;
    public static final int LCURLY=9;
    public static final int RCURLY=10;
    public static final int LBRACK=11;
    public static final int COLON=12;
    public static final int RBRACK=13;
    public static final int ASSIGN=14;
    public static final int NUMBER=15;
    public static final int KW_TRIREG=16;
    public static final int KW_ASSIGN=17;
    public static final int POUND=18;
    public static final int IDENTIFIER=19;
    public static final int LE=20;
    public static final int TRIGGER=21;
    public static final int SYSTEM_TASK_NAME=22;
    public static final int PPATH=23;
    public static final int FPATH=24;
    public static final int PLUS=25;
    public static final int MINUS=26;
    public static final int STRING=27;
    public static final int DEFINE=28;
    public static final int QUESTION=29;
    public static final int LNOT=30;
    public static final int BNOT=31;
    public static final int BAND=32;
    public static final int RNAND=33;
    public static final int BOR=34;
    public static final int RNOR=35;
    public static final int BXOR=36;
    public static final int BXNOR=37;
    public static final int STAR=38;
    public static final int DIV=39;
    public static final int MOD=40;
    public static final int EQUAL=41;
    public static final int NOT_EQ=42;
    public static final int EQ_CASE=43;
    public static final int NOT_EQ_CASE=44;
    public static final int LAND=45;
    public static final int LOR=46;
    public static final int LT_=47;
    public static final int GT=48;
    public static final int GE=49;
    public static final int SR=50;
    public static final int SL=51;
    public static final int ESCAPED_IDENTIFIER=52;
    public static final int AT=53;
    public static final int VOCAB=54;
    public static final int SIZE=55;
    public static final int BASE=56;
    public static final int SIZED_DIGIT=57;
    public static final int SIZED_NUMBER=58;
    public static final int UNSIZED_NUMBER=59;
    public static final int DIGIT=60;
    public static final int HEXDIGIT=61;
    public static final int EXPONENT=62;
    public static final int SPACE_OR_TAB=63;
    public static final int WS=64;
    public static final int ML_COMMENT=65;
    public static final int SL_COMMENT=66;

    // delegates
    // delegators

    public static final String[] ruleNames = new String[] {
        "invalidRule", "list_of_specparam_assignments", "specify_block", 
        "wait_statement", "udp_instantiation", "polarity_operator", "net_declaration", 
        "module_item", "init_val", "full_path_descriptor", "parallel_level_sensitive_path_description", 
        "function", "synpred16_Verilog", "synpred4_Verilog", "name_of_gate_instance", 
        "edge_descriptor", "binary_operator", "list_of_register_variables", 
        "name_of_UDP", "exp11", "drive_strength", "path_delay_value", "name_of_register", 
        "procedural_timing_control_statement", "synpred6_Verilog", "udp", 
        "port", "udp_name_of_port", "udp_instance", "named_port_connection", 
        "always_statement", "terminal", "disable_statement", "exp0", "parameter_override", 
        "edge_sensitive_path_declaration", "timing_check_condition", "edge_identifier", 
        "exp7", "real_declaration", "name_of_module", "synpred2_Verilog", 
        "concatenation", "delay_control", "integer_declaration", "conditional_statement", 
        "directive", "seq_block", "time_declaration", "blocking_assignment", 
        "timing_check_limit", "list_of_ports", "table_definition", "system_timing_check", 
        "synpred18_Verilog", "name_of_instance", "parameter_declaration", 
        "event_control", "gate_instance", "tf_declaration", "assignment", 
        "range", "strength0", "notify_register", "exp10", "case_statement", 
        "expression", "register_variable", "path_delay_expression", "timing_check_event_control", 
        "name_of_memory", "event_expression", "synpred1_Verilog", "synpred10_Verilog", 
        "module_port_connection", "source_text", "name_of_block", "sdpd", 
        "synpred3_Verilog", "list_of_assignments", "edge_control_specifier", 
        "list_of_path_terminals", "task_enable", "simple_path_declaration", 
        "synpred19_Verilog", "case_item", "identifier", "specify_terminal_descriptor", 
        "param_assignment", "exp8", "module_instantiation", "list_of_variables", 
        "inout_declaration", "statement_or_null", "scalar_timing_check_condition", 
        "description", "synpred5_Verilog", "task", "list_of_assigned_variables", 
        "module_instance", "module", "synpred17_Verilog", "expression_list", 
        "real_identifier", "level_sensitive_path_declaration", "delay", 
        "function_call", "loop_statement", "output_declaration", "unary_operator", 
        "input_declaration", "synpred12_Verilog", "gate_declaration", "net_type", 
        "sub_event_expression", "statement", "synpred13_Verilog", "identifier_path", 
        "event_trigger", "name_of_variable", "synpred15_Verilog", "name_of_port", 
        "parameter_value_assignment", "udp_declaration", "spec_param_declaration", 
        "parallel_path_description", "full_level_sensitive_path_description", 
        "port_reference", "delay_or_event_control", "path_declaration", 
        "synpred7_Verilog", "port_expression", "name_of_event", "exp9", 
        "procedural_continuous_assignment", "name_of_function", "mintypmax_expression", 
        "specify_item", "name_of_UDP_instance", "range_or_type", "specparam_assignment", 
        "synpred11_Verilog", "udp_initial_statement", "charge_strength", 
        "udp_port_list", "data_source_expression", "initial_statement", 
        "synpred14_Verilog", "lvalue", "synpred9_Verilog", "expandrange", 
        "instantiation", "case_keyword", "non_blocking_assignment", "controlled_timing_check_event", 
        "system_task_enable", "block_declaration", "list_of_path_delay_expressions", 
        "par_block", "define_directive", "synpred20_Verilog", "name_of_task", 
        "list_of_module_connections", "output_terminal_name", "reg_declaration", 
        "local_identifier", "event_declaration", "strength1", "continuous_assign", 
        "include_directive", "timing_check_event", "table_entries", "specparam_identifier", 
        "list_of_param_assignments", "gate_type", "synpred8_Verilog", "net_identifier"
    };
    public static final boolean[] decisionCanBacktrack = new boolean[] {
        false, // invalid decision
        false, false, false, false, false, false, false, false, false, 
            false, true, false, true, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            true, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            true, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, true, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false, false, false, false, true, false, true, true, false, 
            true, true, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            true, false, false, false, false, true, false, true, false, 
            false, true, false, false, false, false, false, false, false, 
            false, false, false, false, false, false, false, false, false, 
            false
    };

     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public VerilogParser(TokenStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public VerilogParser(TokenStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, null);
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public VerilogParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return VerilogParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g"; }



    // $ANTLR start "source_text"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:19:1: source_text : ( description )* EOF ;
    public final void source_text() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "source_text");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(19, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:19:13: ( ( description )* EOF )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:20:9: ( description )* EOF
            {
            dbg.location(20,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:20:9: ( description )*
            try { dbg.enterSubRule(1);

            loop1:
            do {
                int alt1=2;
                try { dbg.enterDecision(1, decisionCanBacktrack[1]);

                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=67 && LA1_0<=68)||LA1_0==70||(LA1_0>=178 && LA1_0<=179)) ) {
                    alt1=1;
                }


                } finally {dbg.exitDecision(1);}

                switch (alt1) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:20:11: description
            	    {
            	    dbg.location(20,11);
            	    pushFollow(FOLLOW_description_in_source_text75);
            	    description();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);
            } finally {dbg.exitSubRule(1);}

            dbg.location(21,2);
            match(input,EOF,FOLLOW_EOF_in_source_text81); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(22, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "source_text");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "source_text"


    // $ANTLR start "description"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:24:1: description : ( module | udp | directive );
    public final void description() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "description");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(24, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:24:13: ( module | udp | directive )
            int alt2=3;
            try { dbg.enterDecision(2, decisionCanBacktrack[2]);

            switch ( input.LA(1) ) {
            case 67:
            case 68:
                {
                alt2=1;
                }
                break;
            case 70:
                {
                alt2=2;
                }
                break;
            case 178:
            case 179:
                {
                alt2=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(2);}

            switch (alt2) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:25:9: module
                    {
                    dbg.location(25,9);
                    pushFollow(FOLLOW_module_in_description106);
                    module();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:26:9: udp
                    {
                    dbg.location(26,9);
                    pushFollow(FOLLOW_udp_in_description118);
                    udp();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:27:2: directive
                    {
                    dbg.location(27,2);
                    pushFollow(FOLLOW_directive_in_description123);
                    directive();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(28, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "description");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "description"


    // $ANTLR start "module"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:30:1: module : ( 'module' | 'macromodule' ) name_of_module ( list_of_ports )? SEMI ( module_item )* 'endmodule' ;
    public final void module() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "module");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(30, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:30:8: ( ( 'module' | 'macromodule' ) name_of_module ( list_of_ports )? SEMI ( module_item )* 'endmodule' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:31:9: ( 'module' | 'macromodule' ) name_of_module ( list_of_ports )? SEMI ( module_item )* 'endmodule'
            {
            dbg.location(31,9);
            if ( (input.LA(1)>=67 && input.LA(1)<=68) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }

            dbg.location(32,2);
            pushFollow(FOLLOW_name_of_module_in_module159);
            name_of_module();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(33,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:33:9: ( list_of_ports )?
            int alt3=2;
            try { dbg.enterSubRule(3);
            try { dbg.enterDecision(3, decisionCanBacktrack[3]);

            int LA3_0 = input.LA(1);

            if ( (LA3_0==LPAREN) ) {
                alt3=1;
            }
            } finally {dbg.exitDecision(3);}

            switch (alt3) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:33:11: list_of_ports
                    {
                    dbg.location(33,11);
                    pushFollow(FOLLOW_list_of_ports_in_module171);
                    list_of_ports();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(3);}

            dbg.location(33,28);
            match(input,SEMI,FOLLOW_SEMI_in_module176); if (state.failed) return ;
            dbg.location(34,2);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:34:2: ( module_item )*
            try { dbg.enterSubRule(4);

            loop4:
            do {
                int alt4=2;
                try { dbg.enterDecision(4, decisionCanBacktrack[4]);

                try {
                    isCyclicDecision = true;
                    alt4 = dfa4.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(4);}

                switch (alt4) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:34:4: module_item
            	    {
            	    dbg.location(34,4);
            	    pushFollow(FOLLOW_module_item_in_module181);
            	    module_item();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);
            } finally {dbg.exitSubRule(4);}

            dbg.location(35,2);
            match(input,69,FOLLOW_69_in_module187); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(36, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "module");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "module"


    // $ANTLR start "list_of_ports"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:38:1: list_of_ports : LPAREN port ( COMMA port )* RPAREN ;
    public final void list_of_ports() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_ports");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(38, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:38:15: ( LPAREN port ( COMMA port )* RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:39:9: LPAREN port ( COMMA port )* RPAREN
            {
            dbg.location(39,9);
            match(input,LPAREN,FOLLOW_LPAREN_in_list_of_ports212); if (state.failed) return ;
            dbg.location(39,16);
            pushFollow(FOLLOW_port_in_list_of_ports214);
            port();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(39,21);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:39:21: ( COMMA port )*
            try { dbg.enterSubRule(5);

            loop5:
            do {
                int alt5=2;
                try { dbg.enterDecision(5, decisionCanBacktrack[5]);

                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                } finally {dbg.exitDecision(5);}

                switch (alt5) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:39:23: COMMA port
            	    {
            	    dbg.location(39,23);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_ports218); if (state.failed) return ;
            	    dbg.location(39,29);
            	    pushFollow(FOLLOW_port_in_list_of_ports220);
            	    port();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);
            } finally {dbg.exitSubRule(5);}

            dbg.location(39,37);
            match(input,RPAREN,FOLLOW_RPAREN_in_list_of_ports225); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(40, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_ports");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_ports"


    // $ANTLR start "port"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:42:1: port : ( ( port_expression )? | DOT name_of_port LPAREN ( port_expression )? RPAREN );
    public final void port() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "port");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(42, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:42:6: ( ( port_expression )? | DOT name_of_port LPAREN ( port_expression )? RPAREN )
            int alt8=2;
            try { dbg.enterDecision(8, decisionCanBacktrack[8]);

            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=COMMA && LA8_0<=RPAREN)||LA8_0==LCURLY||LA8_0==IDENTIFIER||LA8_0==ESCAPED_IDENTIFIER) ) {
                alt8=1;
            }
            else if ( (LA8_0==DOT) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(8);}

            switch (alt8) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:43:2: ( port_expression )?
                    {
                    dbg.location(43,2);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:43:2: ( port_expression )?
                    int alt6=2;
                    try { dbg.enterSubRule(6);
                    try { dbg.enterDecision(6, decisionCanBacktrack[6]);

                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==LCURLY||LA6_0==IDENTIFIER||LA6_0==ESCAPED_IDENTIFIER) ) {
                        alt6=1;
                    }
                    } finally {dbg.exitDecision(6);}

                    switch (alt6) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:43:3: port_expression
                            {
                            dbg.location(43,3);
                            pushFollow(FOLLOW_port_expression_in_port244);
                            port_expression();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(6);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:44:9: DOT name_of_port LPAREN ( port_expression )? RPAREN
                    {
                    dbg.location(44,9);
                    match(input,DOT,FOLLOW_DOT_in_port258); if (state.failed) return ;
                    dbg.location(44,13);
                    pushFollow(FOLLOW_name_of_port_in_port260);
                    name_of_port();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(44,26);
                    match(input,LPAREN,FOLLOW_LPAREN_in_port262); if (state.failed) return ;
                    dbg.location(44,33);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:44:33: ( port_expression )?
                    int alt7=2;
                    try { dbg.enterSubRule(7);
                    try { dbg.enterDecision(7, decisionCanBacktrack[7]);

                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==LCURLY||LA7_0==IDENTIFIER||LA7_0==ESCAPED_IDENTIFIER) ) {
                        alt7=1;
                    }
                    } finally {dbg.exitDecision(7);}

                    switch (alt7) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:44:34: port_expression
                            {
                            dbg.location(44,34);
                            pushFollow(FOLLOW_port_expression_in_port265);
                            port_expression();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(7);}

                    dbg.location(44,52);
                    match(input,RPAREN,FOLLOW_RPAREN_in_port269); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(45, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "port");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "port"


    // $ANTLR start "port_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:47:1: port_expression : ( port_reference | LCURLY port_reference ( COMMA port_reference )* RCURLY );
    public final void port_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "port_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(47, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:47:17: ( port_reference | LCURLY port_reference ( COMMA port_reference )* RCURLY )
            int alt10=2;
            try { dbg.enterDecision(10, decisionCanBacktrack[10]);

            int LA10_0 = input.LA(1);

            if ( (LA10_0==IDENTIFIER||LA10_0==ESCAPED_IDENTIFIER) ) {
                alt10=1;
            }
            else if ( (LA10_0==LCURLY) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(10);}

            switch (alt10) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:48:9: port_reference
                    {
                    dbg.location(48,9);
                    pushFollow(FOLLOW_port_reference_in_port_expression287);
                    port_reference();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:49:2: LCURLY port_reference ( COMMA port_reference )* RCURLY
                    {
                    dbg.location(49,2);
                    match(input,LCURLY,FOLLOW_LCURLY_in_port_expression292); if (state.failed) return ;
                    dbg.location(49,9);
                    pushFollow(FOLLOW_port_reference_in_port_expression294);
                    port_reference();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(49,24);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:49:24: ( COMMA port_reference )*
                    try { dbg.enterSubRule(9);

                    loop9:
                    do {
                        int alt9=2;
                        try { dbg.enterDecision(9, decisionCanBacktrack[9]);

                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==COMMA) ) {
                            alt9=1;
                        }


                        } finally {dbg.exitDecision(9);}

                        switch (alt9) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:49:26: COMMA port_reference
                    	    {
                    	    dbg.location(49,26);
                    	    match(input,COMMA,FOLLOW_COMMA_in_port_expression298); if (state.failed) return ;
                    	    dbg.location(49,32);
                    	    pushFollow(FOLLOW_port_reference_in_port_expression300);
                    	    port_reference();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(9);}

                    dbg.location(49,50);
                    match(input,RCURLY,FOLLOW_RCURLY_in_port_expression305); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(50, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "port_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "port_expression"


    // $ANTLR start "port_reference"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:52:1: port_reference : ( ( name_of_variable LBRACK expression COLON )=> name_of_variable LBRACK expression COLON expression RBRACK | ( name_of_variable LBRACK )=> name_of_variable LBRACK expression RBRACK | name_of_variable );
    public final void port_reference() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "port_reference");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(52, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:52:16: ( ( name_of_variable LBRACK expression COLON )=> name_of_variable LBRACK expression COLON expression RBRACK | ( name_of_variable LBRACK )=> name_of_variable LBRACK expression RBRACK | name_of_variable )
            int alt11=3;
            try { dbg.enterDecision(11, decisionCanBacktrack[11]);

            try {
                isCyclicDecision = true;
                alt11 = dfa11.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(11);}

            switch (alt11) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:53:9: ( name_of_variable LBRACK expression COLON )=> name_of_variable LBRACK expression COLON expression RBRACK
                    {
                    dbg.location(54,9);
                    pushFollow(FOLLOW_name_of_variable_in_port_reference352);
                    name_of_variable();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(54,26);
                    match(input,LBRACK,FOLLOW_LBRACK_in_port_reference354); if (state.failed) return ;
                    dbg.location(54,33);
                    pushFollow(FOLLOW_expression_in_port_reference356);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(54,44);
                    match(input,COLON,FOLLOW_COLON_in_port_reference358); if (state.failed) return ;
                    dbg.location(54,50);
                    pushFollow(FOLLOW_expression_in_port_reference360);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(54,61);
                    match(input,RBRACK,FOLLOW_RBRACK_in_port_reference362); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:55:9: ( name_of_variable LBRACK )=> name_of_variable LBRACK expression RBRACK
                    {
                    dbg.location(56,9);
                    pushFollow(FOLLOW_name_of_variable_in_port_reference392);
                    name_of_variable();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(56,26);
                    match(input,LBRACK,FOLLOW_LBRACK_in_port_reference394); if (state.failed) return ;
                    dbg.location(56,33);
                    pushFollow(FOLLOW_expression_in_port_reference396);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(56,44);
                    match(input,RBRACK,FOLLOW_RBRACK_in_port_reference398); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:57:9: name_of_variable
                    {
                    dbg.location(57,9);
                    pushFollow(FOLLOW_name_of_variable_in_port_reference410);
                    name_of_variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(58, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "port_reference");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "port_reference"


    // $ANTLR start "module_item"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:60:1: module_item : ( parameter_declaration | input_declaration | output_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration | gate_declaration | instantiation | parameter_override | continuous_assign | specify_block | initial_statement | always_statement | task | function | directive );
    public final void module_item() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "module_item");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(60, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:60:13: ( parameter_declaration | input_declaration | output_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration | gate_declaration | instantiation | parameter_override | continuous_assign | specify_block | initial_statement | always_statement | task | function | directive )
            int alt12=19;
            try { dbg.enterDecision(12, decisionCanBacktrack[12]);

            try {
                isCyclicDecision = true;
                alt12 = dfa12.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(12);}

            switch (alt12) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:63:9: parameter_declaration
                    {
                    dbg.location(63,9);
                    pushFollow(FOLLOW_parameter_declaration_in_module_item442);
                    parameter_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:64:9: input_declaration
                    {
                    dbg.location(64,9);
                    pushFollow(FOLLOW_input_declaration_in_module_item454);
                    input_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:65:9: output_declaration
                    {
                    dbg.location(65,9);
                    pushFollow(FOLLOW_output_declaration_in_module_item466);
                    output_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:66:9: inout_declaration
                    {
                    dbg.location(66,9);
                    pushFollow(FOLLOW_inout_declaration_in_module_item478);
                    inout_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:68:9: reg_declaration
                    {
                    dbg.location(68,9);
                    pushFollow(FOLLOW_reg_declaration_in_module_item499);
                    reg_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:69:9: time_declaration
                    {
                    dbg.location(69,9);
                    pushFollow(FOLLOW_time_declaration_in_module_item511);
                    time_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:70:9: integer_declaration
                    {
                    dbg.location(70,9);
                    pushFollow(FOLLOW_integer_declaration_in_module_item523);
                    integer_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:71:9: real_declaration
                    {
                    dbg.location(71,9);
                    pushFollow(FOLLOW_real_declaration_in_module_item535);
                    real_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 9 :
                    dbg.enterAlt(9);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:72:9: event_declaration
                    {
                    dbg.location(72,9);
                    pushFollow(FOLLOW_event_declaration_in_module_item547);
                    event_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 10 :
                    dbg.enterAlt(10);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:73:3: gate_declaration
                    {
                    dbg.location(73,3);
                    pushFollow(FOLLOW_gate_declaration_in_module_item553);
                    gate_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 11 :
                    dbg.enterAlt(11);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:74:9: instantiation
                    {
                    dbg.location(74,9);
                    pushFollow(FOLLOW_instantiation_in_module_item565);
                    instantiation();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 12 :
                    dbg.enterAlt(12);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:75:9: parameter_override
                    {
                    dbg.location(75,9);
                    pushFollow(FOLLOW_parameter_override_in_module_item577);
                    parameter_override();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 13 :
                    dbg.enterAlt(13);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:76:9: continuous_assign
                    {
                    dbg.location(76,9);
                    pushFollow(FOLLOW_continuous_assign_in_module_item589);
                    continuous_assign();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 14 :
                    dbg.enterAlt(14);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:77:9: specify_block
                    {
                    dbg.location(77,9);
                    pushFollow(FOLLOW_specify_block_in_module_item601);
                    specify_block();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 15 :
                    dbg.enterAlt(15);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:78:9: initial_statement
                    {
                    dbg.location(78,9);
                    pushFollow(FOLLOW_initial_statement_in_module_item613);
                    initial_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 16 :
                    dbg.enterAlt(16);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:79:9: always_statement
                    {
                    dbg.location(79,9);
                    pushFollow(FOLLOW_always_statement_in_module_item625);
                    always_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 17 :
                    dbg.enterAlt(17);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:80:9: task
                    {
                    dbg.location(80,9);
                    pushFollow(FOLLOW_task_in_module_item637);
                    task();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 18 :
                    dbg.enterAlt(18);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:81:9: function
                    {
                    dbg.location(81,9);
                    pushFollow(FOLLOW_function_in_module_item649);
                    function();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 19 :
                    dbg.enterAlt(19);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:82:3: directive
                    {
                    dbg.location(82,3);
                    pushFollow(FOLLOW_directive_in_module_item655);
                    directive();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(83, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "module_item");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "module_item"


    // $ANTLR start "instantiation"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:85:1: instantiation : ( ( module_instantiation )=> module_instantiation | udp_instantiation );
    public final void instantiation() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "instantiation");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(85, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:85:14: ( ( module_instantiation )=> module_instantiation | udp_instantiation )
            int alt13=2;
            try { dbg.enterDecision(13, decisionCanBacktrack[13]);

            try {
                isCyclicDecision = true;
                alt13 = dfa13.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(13);}

            switch (alt13) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:86:2: ( module_instantiation )=> module_instantiation
                    {
                    dbg.location(86,28);
                    pushFollow(FOLLOW_module_instantiation_in_instantiation678);
                    module_instantiation();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:87:2: udp_instantiation
                    {
                    dbg.location(87,2);
                    pushFollow(FOLLOW_udp_instantiation_in_instantiation683);
                    udp_instantiation();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(88, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "instantiation");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "instantiation"


    // $ANTLR start "udp"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:94:1: udp : 'primitive' name_of_UDP LPAREN name_of_variable ( COMMA name_of_variable )* RPAREN SEMI ( udp_declaration )+ ( udp_initial_statement )? table_definition 'endprimitive' ;
    public final void udp() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(94, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:94:5: ( 'primitive' name_of_UDP LPAREN name_of_variable ( COMMA name_of_variable )* RPAREN SEMI ( udp_declaration )+ ( udp_initial_statement )? table_definition 'endprimitive' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:95:9: 'primitive' name_of_UDP LPAREN name_of_variable ( COMMA name_of_variable )* RPAREN SEMI ( udp_declaration )+ ( udp_initial_statement )? table_definition 'endprimitive'
            {
            dbg.location(95,9);
            match(input,70,FOLLOW_70_in_udp705); if (state.failed) return ;
            dbg.location(95,21);
            pushFollow(FOLLOW_name_of_UDP_in_udp707);
            name_of_UDP();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(96,2);
            match(input,LPAREN,FOLLOW_LPAREN_in_udp710); if (state.failed) return ;
            dbg.location(96,9);
            pushFollow(FOLLOW_name_of_variable_in_udp712);
            name_of_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(96,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:96:26: ( COMMA name_of_variable )*
            try { dbg.enterSubRule(14);

            loop14:
            do {
                int alt14=2;
                try { dbg.enterDecision(14, decisionCanBacktrack[14]);

                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                } finally {dbg.exitDecision(14);}

                switch (alt14) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:96:28: COMMA name_of_variable
            	    {
            	    dbg.location(96,28);
            	    match(input,COMMA,FOLLOW_COMMA_in_udp716); if (state.failed) return ;
            	    dbg.location(96,34);
            	    pushFollow(FOLLOW_name_of_variable_in_udp718);
            	    name_of_variable();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);
            } finally {dbg.exitSubRule(14);}

            dbg.location(96,54);
            match(input,RPAREN,FOLLOW_RPAREN_in_udp723); if (state.failed) return ;
            dbg.location(96,61);
            match(input,SEMI,FOLLOW_SEMI_in_udp725); if (state.failed) return ;
            dbg.location(97,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:97:9: ( udp_declaration )+
            int cnt15=0;
            try { dbg.enterSubRule(15);

            loop15:
            do {
                int alt15=2;
                try { dbg.enterDecision(15, decisionCanBacktrack[15]);

                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=85 && LA15_0<=86)||LA15_0==100) ) {
                    alt15=1;
                }


                } finally {dbg.exitDecision(15);}

                switch (alt15) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:97:10: udp_declaration
            	    {
            	    dbg.location(97,10);
            	    pushFollow(FOLLOW_udp_declaration_in_udp736);
            	    udp_declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt15++;
            } while (true);
            } finally {dbg.exitSubRule(15);}

            dbg.location(98,2);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:98:2: ( udp_initial_statement )?
            int alt16=2;
            try { dbg.enterSubRule(16);
            try { dbg.enterDecision(16, decisionCanBacktrack[16]);

            int LA16_0 = input.LA(1);

            if ( (LA16_0==72) ) {
                alt16=1;
            }
            } finally {dbg.exitDecision(16);}

            switch (alt16) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:98:3: udp_initial_statement
                    {
                    dbg.location(98,3);
                    pushFollow(FOLLOW_udp_initial_statement_in_udp742);
                    udp_initial_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(16);}

            dbg.location(99,2);
            pushFollow(FOLLOW_table_definition_in_udp747);
            table_definition();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(100,9);
            match(input,71,FOLLOW_71_in_udp757); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(101, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp"


    // $ANTLR start "udp_port_list"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:103:1: udp_port_list : udp_name_of_port ( COMMA udp_name_of_port )* ;
    public final void udp_port_list() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_port_list");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(103, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:103:15: ( udp_name_of_port ( COMMA udp_name_of_port )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:104:9: udp_name_of_port ( COMMA udp_name_of_port )*
            {
            dbg.location(104,9);
            pushFollow(FOLLOW_udp_name_of_port_in_udp_port_list782);
            udp_name_of_port();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(104,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:104:26: ( COMMA udp_name_of_port )*
            try { dbg.enterSubRule(17);

            loop17:
            do {
                int alt17=2;
                try { dbg.enterDecision(17, decisionCanBacktrack[17]);

                int LA17_0 = input.LA(1);

                if ( (LA17_0==COMMA) ) {
                    alt17=1;
                }


                } finally {dbg.exitDecision(17);}

                switch (alt17) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:104:28: COMMA udp_name_of_port
            	    {
            	    dbg.location(104,28);
            	    match(input,COMMA,FOLLOW_COMMA_in_udp_port_list786); if (state.failed) return ;
            	    dbg.location(104,34);
            	    pushFollow(FOLLOW_udp_name_of_port_in_udp_port_list788);
            	    udp_name_of_port();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);
            } finally {dbg.exitSubRule(17);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(105, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_port_list");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_port_list"


    // $ANTLR start "udp_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:107:1: udp_declaration : ( output_declaration | input_declaration | reg_declaration );
    public final void udp_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(107, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:107:17: ( output_declaration | input_declaration | reg_declaration )
            int alt18=3;
            try { dbg.enterDecision(18, decisionCanBacktrack[18]);

            switch ( input.LA(1) ) {
            case 86:
                {
                alt18=1;
                }
                break;
            case 85:
                {
                alt18=2;
                }
                break;
            case 100:
                {
                alt18=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(18);}

            switch (alt18) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:108:9: output_declaration
                    {
                    dbg.location(108,9);
                    pushFollow(FOLLOW_output_declaration_in_udp_declaration816);
                    output_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:109:9: input_declaration
                    {
                    dbg.location(109,9);
                    pushFollow(FOLLOW_input_declaration_in_udp_declaration828);
                    input_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:110:9: reg_declaration
                    {
                    dbg.location(110,9);
                    pushFollow(FOLLOW_reg_declaration_in_udp_declaration840);
                    reg_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(111, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_declaration"


    // $ANTLR start "udp_initial_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:113:1: udp_initial_statement : 'initial' output_terminal_name ASSIGN init_val SEMI ;
    public final void udp_initial_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_initial_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(113, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:113:23: ( 'initial' output_terminal_name ASSIGN init_val SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:114:9: 'initial' output_terminal_name ASSIGN init_val SEMI
            {
            dbg.location(114,9);
            match(input,72,FOLLOW_72_in_udp_initial_statement865); if (state.failed) return ;
            dbg.location(114,19);
            pushFollow(FOLLOW_output_terminal_name_in_udp_initial_statement867);
            output_terminal_name();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(114,40);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_udp_initial_statement869); if (state.failed) return ;
            dbg.location(114,47);
            pushFollow(FOLLOW_init_val_in_udp_initial_statement871);
            init_val();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(114,56);
            match(input,SEMI,FOLLOW_SEMI_in_udp_initial_statement873); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(115, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_initial_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_initial_statement"


    // $ANTLR start "init_val"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:121:1: init_val : ( '1\\'b0' | '1\\'b1' | '1\\'bx' | n= NUMBER {...}?);
    public final void init_val() throws RecognitionException {
        Token n=null;

        try { dbg.enterRule(getGrammarFileName(), "init_val");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(121, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:121:10: ( '1\\'b0' | '1\\'b1' | '1\\'bx' | n= NUMBER {...}?)
            int alt19=4;
            try { dbg.enterDecision(19, decisionCanBacktrack[19]);

            switch ( input.LA(1) ) {
            case 73:
                {
                alt19=1;
                }
                break;
            case 74:
                {
                alt19=2;
                }
                break;
            case 75:
                {
                alt19=3;
                }
                break;
            case NUMBER:
                {
                alt19=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(19);}

            switch (alt19) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:122:9: '1\\'b0'
                    {
                    dbg.location(122,9);
                    match(input,73,FOLLOW_73_in_init_val914); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:123:9: '1\\'b1'
                    {
                    dbg.location(123,9);
                    match(input,74,FOLLOW_74_in_init_val926); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:124:9: '1\\'bx'
                    {
                    dbg.location(124,9);
                    match(input,75,FOLLOW_75_in_init_val938); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:125:2: n= NUMBER {...}?
                    {
                    dbg.location(125,3);
                    n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_init_val945); if (state.failed) return ;
                    dbg.location(126,2);
                    if ( !(evalPredicate( (n!=null?n.getText():null)=="0" || (n!=null?n.getText():null)=="1"," $n.text==\"0\" || $n.text==\"1\"")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "init_val", " $n.text==\"0\" || $n.text==\"1\"");
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(127, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "init_val");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "init_val"


    // $ANTLR start "table_definition"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:129:1: table_definition : 'table' table_entries 'endtable' ;
    public final void table_definition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "table_definition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(129, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:129:18: ( 'table' table_entries 'endtable' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:130:9: 'table' table_entries 'endtable'
            {
            dbg.location(130,9);
            match(input,76,FOLLOW_76_in_table_definition966); if (state.failed) return ;
            dbg.location(130,17);
            pushFollow(FOLLOW_table_entries_in_table_definition968);
            table_entries();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(130,31);
            match(input,77,FOLLOW_77_in_table_definition970); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(131, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "table_definition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "table_definition"


    // $ANTLR start "table_entries"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:139:1: table_entries : ( (~ ( SEMI | 'endtable' ) )+ SEMI )* ;
    public final void table_entries() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "table_entries");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(139, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:139:15: ( ( (~ ( SEMI | 'endtable' ) )+ SEMI )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:142:2: ( (~ ( SEMI | 'endtable' ) )+ SEMI )*
            {
            dbg.location(142,2);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:142:2: ( (~ ( SEMI | 'endtable' ) )+ SEMI )*
            try { dbg.enterSubRule(21);

            loop21:
            do {
                int alt21=2;
                try { dbg.enterDecision(21, decisionCanBacktrack[21]);

                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=LPAREN && LA21_0<=76)||(LA21_0>=78 && LA21_0<=179)) ) {
                    alt21=1;
                }


                } finally {dbg.exitDecision(21);}

                switch (alt21) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:142:3: (~ ( SEMI | 'endtable' ) )+ SEMI
            	    {
            	    dbg.location(142,3);
            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:142:3: (~ ( SEMI | 'endtable' ) )+
            	    int cnt20=0;
            	    try { dbg.enterSubRule(20);

            	    loop20:
            	    do {
            	        int alt20=2;
            	        try { dbg.enterDecision(20, decisionCanBacktrack[20]);

            	        int LA20_0 = input.LA(1);

            	        if ( ((LA20_0>=LPAREN && LA20_0<=76)||(LA20_0>=78 && LA20_0<=179)) ) {
            	            alt20=1;
            	        }


            	        } finally {dbg.exitDecision(20);}

            	        switch (alt20) {
            	    	case 1 :
            	    	    dbg.enterAlt(1);

            	    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:142:5: ~ ( SEMI | 'endtable' )
            	    	    {
            	    	    dbg.location(142,5);
            	    	    if ( (input.LA(1)>=LPAREN && input.LA(1)<=76)||(input.LA(1)>=78 && input.LA(1)<=179) ) {
            	    	        input.consume();
            	    	        state.errorRecovery=false;state.failed=false;
            	    	    }
            	    	    else {
            	    	        if (state.backtracking>0) {state.failed=true; return ;}
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        dbg.recognitionException(mse);
            	    	        throw mse;
            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt20 >= 1 ) break loop20;
            	    	    if (state.backtracking>0) {state.failed=true; return ;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(20, input);
            	                dbg.recognitionException(eee);

            	                throw eee;
            	        }
            	        cnt20++;
            	    } while (true);
            	    } finally {dbg.exitSubRule(20);}

            	    dbg.location(142,29);
            	    match(input,SEMI,FOLLOW_SEMI_in_table_entries1031); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);
            } finally {dbg.exitSubRule(21);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(143, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "table_entries");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "table_entries"


    // $ANTLR start "task"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:146:1: task : 'task' name_of_task SEMI ( tf_declaration )* statement_or_null 'endtask' ;
    public final void task() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "task");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(146, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:223:6: ( 'task' name_of_task SEMI ( tf_declaration )* statement_or_null 'endtask' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:224:9: 'task' name_of_task SEMI ( tf_declaration )* statement_or_null 'endtask'
            {
            dbg.location(224,9);
            match(input,78,FOLLOW_78_in_task1056); if (state.failed) return ;
            dbg.location(224,16);
            pushFollow(FOLLOW_name_of_task_in_task1058);
            name_of_task();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(224,29);
            match(input,SEMI,FOLLOW_SEMI_in_task1060); if (state.failed) return ;
            dbg.location(225,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:225:9: ( tf_declaration )*
            try { dbg.enterSubRule(22);

            loop22:
            do {
                int alt22=2;
                try { dbg.enterDecision(22, decisionCanBacktrack[22]);

                try {
                    isCyclicDecision = true;
                    alt22 = dfa22.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(22);}

                switch (alt22) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:225:10: tf_declaration
            	    {
            	    dbg.location(225,10);
            	    pushFollow(FOLLOW_tf_declaration_in_task1071);
            	    tf_declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);
            } finally {dbg.exitSubRule(22);}

            dbg.location(226,9);
            pushFollow(FOLLOW_statement_or_null_in_task1083);
            statement_or_null();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(227,9);
            match(input,79,FOLLOW_79_in_task1093); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(228, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "task");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "task"


    // $ANTLR start "function"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:230:1: function : 'function' ( range_or_type )? name_of_function SEMI ( tf_declaration )+ statement 'endfunction' ;
    public final void function() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "function");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(230, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:230:10: ( 'function' ( range_or_type )? name_of_function SEMI ( tf_declaration )+ statement 'endfunction' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:231:9: 'function' ( range_or_type )? name_of_function SEMI ( tf_declaration )+ statement 'endfunction'
            {
            dbg.location(231,9);
            match(input,80,FOLLOW_80_in_function1118); if (state.failed) return ;
            dbg.location(231,20);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:231:20: ( range_or_type )?
            int alt23=2;
            try { dbg.enterSubRule(23);
            try { dbg.enterDecision(23, decisionCanBacktrack[23]);

            int LA23_0 = input.LA(1);

            if ( (LA23_0==LBRACK||(LA23_0>=82 && LA23_0<=83)) ) {
                alt23=1;
            }
            } finally {dbg.exitDecision(23);}

            switch (alt23) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:231:21: range_or_type
                    {
                    dbg.location(231,21);
                    pushFollow(FOLLOW_range_or_type_in_function1121);
                    range_or_type();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(23);}

            dbg.location(231,37);
            pushFollow(FOLLOW_name_of_function_in_function1125);
            name_of_function();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(231,54);
            match(input,SEMI,FOLLOW_SEMI_in_function1127); if (state.failed) return ;
            dbg.location(232,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:232:9: ( tf_declaration )+
            int cnt24=0;
            try { dbg.enterSubRule(24);

            loop24:
            do {
                int alt24=2;
                try { dbg.enterDecision(24, decisionCanBacktrack[24]);

                try {
                    isCyclicDecision = true;
                    alt24 = dfa24.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(24);}

                switch (alt24) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:232:10: tf_declaration
            	    {
            	    dbg.location(232,10);
            	    pushFollow(FOLLOW_tf_declaration_in_function1138);
            	    tf_declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt24 >= 1 ) break loop24;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(24, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt24++;
            } while (true);
            } finally {dbg.exitSubRule(24);}

            dbg.location(233,9);
            pushFollow(FOLLOW_statement_in_function1150);
            statement();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(234,9);
            match(input,81,FOLLOW_81_in_function1160); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(235, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "function");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "function"


    // $ANTLR start "range_or_type"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:237:1: range_or_type : ( range | 'integer' | 'real' );
    public final void range_or_type() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "range_or_type");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(237, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:237:15: ( range | 'integer' | 'real' )
            int alt25=3;
            try { dbg.enterDecision(25, decisionCanBacktrack[25]);

            switch ( input.LA(1) ) {
            case LBRACK:
                {
                alt25=1;
                }
                break;
            case 82:
                {
                alt25=2;
                }
                break;
            case 83:
                {
                alt25=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(25);}

            switch (alt25) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:238:9: range
                    {
                    dbg.location(238,9);
                    pushFollow(FOLLOW_range_in_range_or_type1185);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:239:9: 'integer'
                    {
                    dbg.location(239,9);
                    match(input,82,FOLLOW_82_in_range_or_type1197); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:240:9: 'real'
                    {
                    dbg.location(240,9);
                    match(input,83,FOLLOW_83_in_range_or_type1209); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(241, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "range_or_type");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "range_or_type"


    // $ANTLR start "tf_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:243:1: tf_declaration : ( parameter_declaration | output_declaration | input_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration );
    public final void tf_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "tf_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(243, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:243:16: ( parameter_declaration | output_declaration | input_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration )
            int alt26=9;
            try { dbg.enterDecision(26, decisionCanBacktrack[26]);

            try {
                isCyclicDecision = true;
                alt26 = dfa26.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(26);}

            switch (alt26) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:244:9: parameter_declaration
                    {
                    dbg.location(244,9);
                    pushFollow(FOLLOW_parameter_declaration_in_tf_declaration1234);
                    parameter_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:245:9: output_declaration
                    {
                    dbg.location(245,9);
                    pushFollow(FOLLOW_output_declaration_in_tf_declaration1246);
                    output_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:246:9: input_declaration
                    {
                    dbg.location(246,9);
                    pushFollow(FOLLOW_input_declaration_in_tf_declaration1258);
                    input_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:247:9: inout_declaration
                    {
                    dbg.location(247,9);
                    pushFollow(FOLLOW_inout_declaration_in_tf_declaration1270);
                    inout_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:248:9: reg_declaration
                    {
                    dbg.location(248,9);
                    pushFollow(FOLLOW_reg_declaration_in_tf_declaration1282);
                    reg_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:249:9: time_declaration
                    {
                    dbg.location(249,9);
                    pushFollow(FOLLOW_time_declaration_in_tf_declaration1294);
                    time_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:250:9: integer_declaration
                    {
                    dbg.location(250,9);
                    pushFollow(FOLLOW_integer_declaration_in_tf_declaration1306);
                    integer_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:251:9: real_declaration
                    {
                    dbg.location(251,9);
                    pushFollow(FOLLOW_real_declaration_in_tf_declaration1318);
                    real_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 9 :
                    dbg.enterAlt(9);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:252:9: event_declaration
                    {
                    dbg.location(252,9);
                    pushFollow(FOLLOW_event_declaration_in_tf_declaration1330);
                    event_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(253, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "tf_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "tf_declaration"


    // $ANTLR start "parameter_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:260:1: parameter_declaration : 'parameter' ( range )? list_of_param_assignments SEMI ;
    public final void parameter_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "parameter_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(260, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:260:23: ( 'parameter' ( range )? list_of_param_assignments SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:261:9: 'parameter' ( range )? list_of_param_assignments SEMI
            {
            dbg.location(261,9);
            match(input,84,FOLLOW_84_in_parameter_declaration1361); if (state.failed) return ;
            dbg.location(261,21);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:261:21: ( range )?
            int alt27=2;
            try { dbg.enterSubRule(27);
            try { dbg.enterDecision(27, decisionCanBacktrack[27]);

            int LA27_0 = input.LA(1);

            if ( (LA27_0==LBRACK) ) {
                alt27=1;
            }
            } finally {dbg.exitDecision(27);}

            switch (alt27) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:261:22: range
                    {
                    dbg.location(261,22);
                    pushFollow(FOLLOW_range_in_parameter_declaration1364);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(27);}

            dbg.location(261,30);
            pushFollow(FOLLOW_list_of_param_assignments_in_parameter_declaration1368);
            list_of_param_assignments();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(261,56);
            match(input,SEMI,FOLLOW_SEMI_in_parameter_declaration1370); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(262, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "parameter_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "parameter_declaration"


    // $ANTLR start "list_of_param_assignments"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:264:1: list_of_param_assignments : param_assignment ( COMMA param_assignment )* ;
    public final void list_of_param_assignments() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_param_assignments");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(264, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:264:27: ( param_assignment ( COMMA param_assignment )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:265:9: param_assignment ( COMMA param_assignment )*
            {
            dbg.location(265,9);
            pushFollow(FOLLOW_param_assignment_in_list_of_param_assignments1395);
            param_assignment();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(265,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:265:26: ( COMMA param_assignment )*
            try { dbg.enterSubRule(28);

            loop28:
            do {
                int alt28=2;
                try { dbg.enterDecision(28, decisionCanBacktrack[28]);

                int LA28_0 = input.LA(1);

                if ( (LA28_0==COMMA) ) {
                    alt28=1;
                }


                } finally {dbg.exitDecision(28);}

                switch (alt28) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:265:28: COMMA param_assignment
            	    {
            	    dbg.location(265,28);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_param_assignments1399); if (state.failed) return ;
            	    dbg.location(265,34);
            	    pushFollow(FOLLOW_param_assignment_in_list_of_param_assignments1401);
            	    param_assignment();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);
            } finally {dbg.exitSubRule(28);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(266, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_param_assignments");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_param_assignments"


    // $ANTLR start "param_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:268:1: param_assignment : identifier ASSIGN expression ;
    public final void param_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "param_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(268, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:268:18: ( identifier ASSIGN expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:269:9: identifier ASSIGN expression
            {
            dbg.location(269,9);
            pushFollow(FOLLOW_identifier_in_param_assignment1429);
            identifier();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(269,20);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_param_assignment1431); if (state.failed) return ;
            dbg.location(269,27);
            pushFollow(FOLLOW_expression_in_param_assignment1433);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(270, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "param_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "param_assignment"


    // $ANTLR start "input_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:272:1: input_declaration : 'input' ( range )? list_of_variables SEMI ;
    public final void input_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "input_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(272, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:272:19: ( 'input' ( range )? list_of_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:273:9: 'input' ( range )? list_of_variables SEMI
            {
            dbg.location(273,9);
            match(input,85,FOLLOW_85_in_input_declaration1458); if (state.failed) return ;
            dbg.location(273,17);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:273:17: ( range )?
            int alt29=2;
            try { dbg.enterSubRule(29);
            try { dbg.enterDecision(29, decisionCanBacktrack[29]);

            int LA29_0 = input.LA(1);

            if ( (LA29_0==LBRACK) ) {
                alt29=1;
            }
            } finally {dbg.exitDecision(29);}

            switch (alt29) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:273:18: range
                    {
                    dbg.location(273,18);
                    pushFollow(FOLLOW_range_in_input_declaration1461);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(29);}

            dbg.location(273,26);
            pushFollow(FOLLOW_list_of_variables_in_input_declaration1465);
            list_of_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(273,44);
            match(input,SEMI,FOLLOW_SEMI_in_input_declaration1467); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(274, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "input_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "input_declaration"


    // $ANTLR start "output_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:276:1: output_declaration : 'output' ( range )? list_of_variables SEMI ;
    public final void output_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "output_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(276, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:276:20: ( 'output' ( range )? list_of_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:277:9: 'output' ( range )? list_of_variables SEMI
            {
            dbg.location(277,9);
            match(input,86,FOLLOW_86_in_output_declaration1492); if (state.failed) return ;
            dbg.location(277,18);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:277:18: ( range )?
            int alt30=2;
            try { dbg.enterSubRule(30);
            try { dbg.enterDecision(30, decisionCanBacktrack[30]);

            int LA30_0 = input.LA(1);

            if ( (LA30_0==LBRACK) ) {
                alt30=1;
            }
            } finally {dbg.exitDecision(30);}

            switch (alt30) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:277:19: range
                    {
                    dbg.location(277,19);
                    pushFollow(FOLLOW_range_in_output_declaration1495);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(30);}

            dbg.location(277,27);
            pushFollow(FOLLOW_list_of_variables_in_output_declaration1499);
            list_of_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(277,45);
            match(input,SEMI,FOLLOW_SEMI_in_output_declaration1501); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(278, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "output_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "output_declaration"


    // $ANTLR start "inout_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:280:1: inout_declaration : 'inout' ( range )? list_of_variables SEMI ;
    public final void inout_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "inout_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(280, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:280:19: ( 'inout' ( range )? list_of_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:281:9: 'inout' ( range )? list_of_variables SEMI
            {
            dbg.location(281,9);
            match(input,87,FOLLOW_87_in_inout_declaration1526); if (state.failed) return ;
            dbg.location(281,17);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:281:17: ( range )?
            int alt31=2;
            try { dbg.enterSubRule(31);
            try { dbg.enterDecision(31, decisionCanBacktrack[31]);

            int LA31_0 = input.LA(1);

            if ( (LA31_0==LBRACK) ) {
                alt31=1;
            }
            } finally {dbg.exitDecision(31);}

            switch (alt31) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:281:18: range
                    {
                    dbg.location(281,18);
                    pushFollow(FOLLOW_range_in_inout_declaration1529);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(31);}

            dbg.location(281,26);
            pushFollow(FOLLOW_list_of_variables_in_inout_declaration1533);
            list_of_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(281,44);
            match(input,SEMI,FOLLOW_SEMI_in_inout_declaration1535); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(282, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "inout_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "inout_declaration"


    // $ANTLR start "net_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:284:1: net_declaration : ( ( net_type ( expandrange )? )=> net_type ( expandrange )? ( delay )? list_of_assigned_variables SEMI | KW_TRIREG ( charge_strength )? ( expandrange )? ( delay )? list_of_variables SEMI );
    public final void net_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "net_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(284, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:284:17: ( ( net_type ( expandrange )? )=> net_type ( expandrange )? ( delay )? list_of_assigned_variables SEMI | KW_TRIREG ( charge_strength )? ( expandrange )? ( delay )? list_of_variables SEMI )
            int alt37=2;
            try { dbg.enterDecision(37, decisionCanBacktrack[37]);

            try {
                isCyclicDecision = true;
                alt37 = dfa37.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(37);}

            switch (alt37) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:285:9: ( net_type ( expandrange )? )=> net_type ( expandrange )? ( delay )? list_of_assigned_variables SEMI
                    {
                    dbg.location(286,9);
                    pushFollow(FOLLOW_net_type_in_net_declaration1581);
                    net_type();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(286,18);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:286:18: ( expandrange )?
                    int alt32=2;
                    try { dbg.enterSubRule(32);
                    try { dbg.enterDecision(32, decisionCanBacktrack[32]);

                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==LBRACK||(LA32_0>=98 && LA32_0<=99)) ) {
                        alt32=1;
                    }
                    } finally {dbg.exitDecision(32);}

                    switch (alt32) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:286:19: expandrange
                            {
                            dbg.location(286,19);
                            pushFollow(FOLLOW_expandrange_in_net_declaration1584);
                            expandrange();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(32);}

                    dbg.location(286,33);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:286:33: ( delay )?
                    int alt33=2;
                    try { dbg.enterSubRule(33);
                    try { dbg.enterDecision(33, decisionCanBacktrack[33]);

                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==POUND) ) {
                        alt33=1;
                    }
                    } finally {dbg.exitDecision(33);}

                    switch (alt33) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:286:34: delay
                            {
                            dbg.location(286,34);
                            pushFollow(FOLLOW_delay_in_net_declaration1589);
                            delay();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(33);}

                    dbg.location(287,12);
                    pushFollow(FOLLOW_list_of_assigned_variables_in_net_declaration1604);
                    list_of_assigned_variables();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(287,39);
                    match(input,SEMI,FOLLOW_SEMI_in_net_declaration1606); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:9: KW_TRIREG ( charge_strength )? ( expandrange )? ( delay )? list_of_variables SEMI
                    {
                    dbg.location(288,9);
                    match(input,KW_TRIREG,FOLLOW_KW_TRIREG_in_net_declaration1618); if (state.failed) return ;
                    dbg.location(288,19);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:19: ( charge_strength )?
                    int alt34=2;
                    try { dbg.enterSubRule(34);
                    try { dbg.enterDecision(34, decisionCanBacktrack[34]);

                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==LPAREN) ) {
                        alt34=1;
                    }
                    } finally {dbg.exitDecision(34);}

                    switch (alt34) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:20: charge_strength
                            {
                            dbg.location(288,20);
                            pushFollow(FOLLOW_charge_strength_in_net_declaration1621);
                            charge_strength();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(34);}

                    dbg.location(288,38);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:38: ( expandrange )?
                    int alt35=2;
                    try { dbg.enterSubRule(35);
                    try { dbg.enterDecision(35, decisionCanBacktrack[35]);

                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==LBRACK||(LA35_0>=98 && LA35_0<=99)) ) {
                        alt35=1;
                    }
                    } finally {dbg.exitDecision(35);}

                    switch (alt35) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:39: expandrange
                            {
                            dbg.location(288,39);
                            pushFollow(FOLLOW_expandrange_in_net_declaration1626);
                            expandrange();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(35);}

                    dbg.location(288,53);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:53: ( delay )?
                    int alt36=2;
                    try { dbg.enterSubRule(36);
                    try { dbg.enterDecision(36, decisionCanBacktrack[36]);

                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==POUND) ) {
                        alt36=1;
                    }
                    } finally {dbg.exitDecision(36);}

                    switch (alt36) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:288:54: delay
                            {
                            dbg.location(288,54);
                            pushFollow(FOLLOW_delay_in_net_declaration1631);
                            delay();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(36);}

                    dbg.location(289,12);
                    pushFollow(FOLLOW_list_of_variables_in_net_declaration1646);
                    list_of_variables();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(289,30);
                    match(input,SEMI,FOLLOW_SEMI_in_net_declaration1648); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(290, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "net_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "net_declaration"


    // $ANTLR start "net_type"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:292:1: net_type : ( 'wire' | 'tri' | 'tri1' | 'supply0' | 'wand' | 'triand' | 'tri0' | 'supply1' | 'wor' | 'trior' | KW_TRIREG );
    public final void net_type() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "net_type");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(292, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:292:10: ( 'wire' | 'tri' | 'tri1' | 'supply0' | 'wand' | 'triand' | 'tri0' | 'supply1' | 'wor' | 'trior' | KW_TRIREG )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(292,10);
            if ( input.LA(1)==KW_TRIREG||(input.LA(1)>=88 && input.LA(1)<=97) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(304, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "net_type");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "net_type"


    // $ANTLR start "expandrange"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:306:1: expandrange : ( 'scalared' range | 'vectored' range | range );
    public final void expandrange() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expandrange");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(306, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:306:13: ( 'scalared' range | 'vectored' range | range )
            int alt38=3;
            try { dbg.enterDecision(38, decisionCanBacktrack[38]);

            switch ( input.LA(1) ) {
            case 98:
                {
                alt38=1;
                }
                break;
            case 99:
                {
                alt38=2;
                }
                break;
            case LBRACK:
                {
                alt38=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(38);}

            switch (alt38) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:307:9: 'scalared' range
                    {
                    dbg.location(307,9);
                    match(input,98,FOLLOW_98_in_expandrange1816); if (state.failed) return ;
                    dbg.location(307,20);
                    pushFollow(FOLLOW_range_in_expandrange1818);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:308:2: 'vectored' range
                    {
                    dbg.location(308,2);
                    match(input,99,FOLLOW_99_in_expandrange1823); if (state.failed) return ;
                    dbg.location(308,13);
                    pushFollow(FOLLOW_range_in_expandrange1825);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:309:2: range
                    {
                    dbg.location(309,2);
                    pushFollow(FOLLOW_range_in_expandrange1830);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(310, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expandrange");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expandrange"


    // $ANTLR start "reg_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:312:1: reg_declaration : 'reg' ( range )? list_of_register_variables SEMI ;
    public final void reg_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "reg_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(312, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:312:17: ( 'reg' ( range )? list_of_register_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:313:9: 'reg' ( range )? list_of_register_variables SEMI
            {
            dbg.location(313,9);
            match(input,100,FOLLOW_100_in_reg_declaration1855); if (state.failed) return ;
            dbg.location(313,15);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:313:15: ( range )?
            int alt39=2;
            try { dbg.enterSubRule(39);
            try { dbg.enterDecision(39, decisionCanBacktrack[39]);

            int LA39_0 = input.LA(1);

            if ( (LA39_0==LBRACK) ) {
                alt39=1;
            }
            } finally {dbg.exitDecision(39);}

            switch (alt39) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:313:16: range
                    {
                    dbg.location(313,16);
                    pushFollow(FOLLOW_range_in_reg_declaration1858);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(39);}

            dbg.location(313,24);
            pushFollow(FOLLOW_list_of_register_variables_in_reg_declaration1862);
            list_of_register_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(313,51);
            match(input,SEMI,FOLLOW_SEMI_in_reg_declaration1864); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(314, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "reg_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "reg_declaration"


    // $ANTLR start "time_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:316:1: time_declaration : 'time' list_of_register_variables SEMI ;
    public final void time_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "time_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(316, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:316:18: ( 'time' list_of_register_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:317:9: 'time' list_of_register_variables SEMI
            {
            dbg.location(317,9);
            match(input,101,FOLLOW_101_in_time_declaration1889); if (state.failed) return ;
            dbg.location(317,16);
            pushFollow(FOLLOW_list_of_register_variables_in_time_declaration1891);
            list_of_register_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(317,43);
            match(input,SEMI,FOLLOW_SEMI_in_time_declaration1893); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(318, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "time_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "time_declaration"


    // $ANTLR start "integer_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:320:1: integer_declaration : 'integer' list_of_register_variables SEMI ;
    public final void integer_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "integer_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(320, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:320:21: ( 'integer' list_of_register_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:321:9: 'integer' list_of_register_variables SEMI
            {
            dbg.location(321,9);
            match(input,82,FOLLOW_82_in_integer_declaration1918); if (state.failed) return ;
            dbg.location(321,19);
            pushFollow(FOLLOW_list_of_register_variables_in_integer_declaration1920);
            list_of_register_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(321,46);
            match(input,SEMI,FOLLOW_SEMI_in_integer_declaration1922); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(322, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "integer_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "integer_declaration"


    // $ANTLR start "real_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:324:1: real_declaration : 'real' list_of_variables SEMI ;
    public final void real_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "real_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(324, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:324:18: ( 'real' list_of_variables SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:325:9: 'real' list_of_variables SEMI
            {
            dbg.location(325,9);
            match(input,83,FOLLOW_83_in_real_declaration1947); if (state.failed) return ;
            dbg.location(325,16);
            pushFollow(FOLLOW_list_of_variables_in_real_declaration1949);
            list_of_variables();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(325,34);
            match(input,SEMI,FOLLOW_SEMI_in_real_declaration1951); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(326, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "real_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "real_declaration"


    // $ANTLR start "event_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:328:1: event_declaration : 'event' name_of_event ( COMMA name_of_event )* SEMI ;
    public final void event_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "event_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(328, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:328:19: ( 'event' name_of_event ( COMMA name_of_event )* SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:329:9: 'event' name_of_event ( COMMA name_of_event )* SEMI
            {
            dbg.location(329,9);
            match(input,102,FOLLOW_102_in_event_declaration1976); if (state.failed) return ;
            dbg.location(329,17);
            pushFollow(FOLLOW_name_of_event_in_event_declaration1978);
            name_of_event();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(329,31);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:329:31: ( COMMA name_of_event )*
            try { dbg.enterSubRule(40);

            loop40:
            do {
                int alt40=2;
                try { dbg.enterDecision(40, decisionCanBacktrack[40]);

                int LA40_0 = input.LA(1);

                if ( (LA40_0==COMMA) ) {
                    alt40=1;
                }


                } finally {dbg.exitDecision(40);}

                switch (alt40) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:329:33: COMMA name_of_event
            	    {
            	    dbg.location(329,33);
            	    match(input,COMMA,FOLLOW_COMMA_in_event_declaration1982); if (state.failed) return ;
            	    dbg.location(329,39);
            	    pushFollow(FOLLOW_name_of_event_in_event_declaration1984);
            	    name_of_event();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);
            } finally {dbg.exitSubRule(40);}

            dbg.location(329,56);
            match(input,SEMI,FOLLOW_SEMI_in_event_declaration1989); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(330, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "event_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "event_declaration"


    // $ANTLR start "continuous_assign"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:332:1: continuous_assign : ( KW_ASSIGN ( drive_strength )? ( delay )? list_of_assignments SEMI | net_type ( drive_strength )? ( expandrange )? ( delay )? list_of_assignments SEMI );
    public final void continuous_assign() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "continuous_assign");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(332, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:332:19: ( KW_ASSIGN ( drive_strength )? ( delay )? list_of_assignments SEMI | net_type ( drive_strength )? ( expandrange )? ( delay )? list_of_assignments SEMI )
            int alt46=2;
            try { dbg.enterDecision(46, decisionCanBacktrack[46]);

            int LA46_0 = input.LA(1);

            if ( (LA46_0==KW_ASSIGN) ) {
                alt46=1;
            }
            else if ( (LA46_0==KW_TRIREG||(LA46_0>=88 && LA46_0<=97)) ) {
                alt46=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(46);}

            switch (alt46) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:333:9: KW_ASSIGN ( drive_strength )? ( delay )? list_of_assignments SEMI
                    {
                    dbg.location(333,9);
                    match(input,KW_ASSIGN,FOLLOW_KW_ASSIGN_in_continuous_assign2014); if (state.failed) return ;
                    dbg.location(333,19);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:333:19: ( drive_strength )?
                    int alt41=2;
                    try { dbg.enterSubRule(41);
                    try { dbg.enterDecision(41, decisionCanBacktrack[41]);

                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==LPAREN) ) {
                        alt41=1;
                    }
                    } finally {dbg.exitDecision(41);}

                    switch (alt41) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:333:20: drive_strength
                            {
                            dbg.location(333,20);
                            pushFollow(FOLLOW_drive_strength_in_continuous_assign2017);
                            drive_strength();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(41);}

                    dbg.location(333,37);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:333:37: ( delay )?
                    int alt42=2;
                    try { dbg.enterSubRule(42);
                    try { dbg.enterDecision(42, decisionCanBacktrack[42]);

                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==POUND) ) {
                        alt42=1;
                    }
                    } finally {dbg.exitDecision(42);}

                    switch (alt42) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:333:38: delay
                            {
                            dbg.location(333,38);
                            pushFollow(FOLLOW_delay_in_continuous_assign2022);
                            delay();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(42);}

                    dbg.location(333,46);
                    pushFollow(FOLLOW_list_of_assignments_in_continuous_assign2026);
                    list_of_assignments();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(333,66);
                    match(input,SEMI,FOLLOW_SEMI_in_continuous_assign2028); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:9: net_type ( drive_strength )? ( expandrange )? ( delay )? list_of_assignments SEMI
                    {
                    dbg.location(334,9);
                    pushFollow(FOLLOW_net_type_in_continuous_assign2040);
                    net_type();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(334,18);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:18: ( drive_strength )?
                    int alt43=2;
                    try { dbg.enterSubRule(43);
                    try { dbg.enterDecision(43, decisionCanBacktrack[43]);

                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==LPAREN) ) {
                        alt43=1;
                    }
                    } finally {dbg.exitDecision(43);}

                    switch (alt43) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:19: drive_strength
                            {
                            dbg.location(334,19);
                            pushFollow(FOLLOW_drive_strength_in_continuous_assign2043);
                            drive_strength();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(43);}

                    dbg.location(334,36);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:36: ( expandrange )?
                    int alt44=2;
                    try { dbg.enterSubRule(44);
                    try { dbg.enterDecision(44, decisionCanBacktrack[44]);

                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==LBRACK||(LA44_0>=98 && LA44_0<=99)) ) {
                        alt44=1;
                    }
                    } finally {dbg.exitDecision(44);}

                    switch (alt44) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:37: expandrange
                            {
                            dbg.location(334,37);
                            pushFollow(FOLLOW_expandrange_in_continuous_assign2048);
                            expandrange();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(44);}

                    dbg.location(334,51);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:51: ( delay )?
                    int alt45=2;
                    try { dbg.enterSubRule(45);
                    try { dbg.enterDecision(45, decisionCanBacktrack[45]);

                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==POUND) ) {
                        alt45=1;
                    }
                    } finally {dbg.exitDecision(45);}

                    switch (alt45) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:334:52: delay
                            {
                            dbg.location(334,52);
                            pushFollow(FOLLOW_delay_in_continuous_assign2053);
                            delay();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(45);}

                    dbg.location(335,12);
                    pushFollow(FOLLOW_list_of_assignments_in_continuous_assign2068);
                    list_of_assignments();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(335,32);
                    match(input,SEMI,FOLLOW_SEMI_in_continuous_assign2070); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(336, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "continuous_assign");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "continuous_assign"


    // $ANTLR start "parameter_override"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:338:1: parameter_override : 'defparam' list_of_param_assignments SEMI ;
    public final void parameter_override() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "parameter_override");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(338, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:338:20: ( 'defparam' list_of_param_assignments SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:339:9: 'defparam' list_of_param_assignments SEMI
            {
            dbg.location(339,9);
            match(input,103,FOLLOW_103_in_parameter_override2095); if (state.failed) return ;
            dbg.location(339,20);
            pushFollow(FOLLOW_list_of_param_assignments_in_parameter_override2097);
            list_of_param_assignments();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(339,46);
            match(input,SEMI,FOLLOW_SEMI_in_parameter_override2099); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(340, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "parameter_override");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "parameter_override"


    // $ANTLR start "list_of_variables"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:342:1: list_of_variables : name_of_variable ( COMMA name_of_variable )* ;
    public final void list_of_variables() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_variables");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(342, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:342:19: ( name_of_variable ( COMMA name_of_variable )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:343:9: name_of_variable ( COMMA name_of_variable )*
            {
            dbg.location(343,9);
            pushFollow(FOLLOW_name_of_variable_in_list_of_variables2124);
            name_of_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(343,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:343:26: ( COMMA name_of_variable )*
            try { dbg.enterSubRule(47);

            loop47:
            do {
                int alt47=2;
                try { dbg.enterDecision(47, decisionCanBacktrack[47]);

                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                } finally {dbg.exitDecision(47);}

                switch (alt47) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:343:28: COMMA name_of_variable
            	    {
            	    dbg.location(343,28);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_variables2128); if (state.failed) return ;
            	    dbg.location(343,34);
            	    pushFollow(FOLLOW_name_of_variable_in_list_of_variables2130);
            	    name_of_variable();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);
            } finally {dbg.exitSubRule(47);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(344, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_variables");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_variables"


    // $ANTLR start "list_of_assigned_variables"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:346:1: list_of_assigned_variables : name_of_variable ( ASSIGN expression )? ( COMMA name_of_variable ( ASSIGN expression )? )* ;
    public final void list_of_assigned_variables() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_assigned_variables");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(346, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:346:28: ( name_of_variable ( ASSIGN expression )? ( COMMA name_of_variable ( ASSIGN expression )? )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:347:9: name_of_variable ( ASSIGN expression )? ( COMMA name_of_variable ( ASSIGN expression )? )*
            {
            dbg.location(347,9);
            pushFollow(FOLLOW_name_of_variable_in_list_of_assigned_variables2158);
            name_of_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(347,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:347:26: ( ASSIGN expression )?
            int alt48=2;
            try { dbg.enterSubRule(48);
            try { dbg.enterDecision(48, decisionCanBacktrack[48]);

            int LA48_0 = input.LA(1);

            if ( (LA48_0==ASSIGN) ) {
                alt48=1;
            }
            } finally {dbg.exitDecision(48);}

            switch (alt48) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:347:28: ASSIGN expression
                    {
                    dbg.location(347,28);
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_list_of_assigned_variables2162); if (state.failed) return ;
                    dbg.location(347,35);
                    pushFollow(FOLLOW_expression_in_list_of_assigned_variables2164);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(48);}

            dbg.location(348,2);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:348:2: ( COMMA name_of_variable ( ASSIGN expression )? )*
            try { dbg.enterSubRule(50);

            loop50:
            do {
                int alt50=2;
                try { dbg.enterDecision(50, decisionCanBacktrack[50]);

                int LA50_0 = input.LA(1);

                if ( (LA50_0==COMMA) ) {
                    alt50=1;
                }


                } finally {dbg.exitDecision(50);}

                switch (alt50) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:348:4: COMMA name_of_variable ( ASSIGN expression )?
            	    {
            	    dbg.location(348,4);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_assigned_variables2172); if (state.failed) return ;
            	    dbg.location(348,10);
            	    pushFollow(FOLLOW_name_of_variable_in_list_of_assigned_variables2174);
            	    name_of_variable();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(348,27);
            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:348:27: ( ASSIGN expression )?
            	    int alt49=2;
            	    try { dbg.enterSubRule(49);
            	    try { dbg.enterDecision(49, decisionCanBacktrack[49]);

            	    int LA49_0 = input.LA(1);

            	    if ( (LA49_0==ASSIGN) ) {
            	        alt49=1;
            	    }
            	    } finally {dbg.exitDecision(49);}

            	    switch (alt49) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:348:29: ASSIGN expression
            	            {
            	            dbg.location(348,29);
            	            match(input,ASSIGN,FOLLOW_ASSIGN_in_list_of_assigned_variables2178); if (state.failed) return ;
            	            dbg.location(348,36);
            	            pushFollow(FOLLOW_expression_in_list_of_assigned_variables2180);
            	            expression();

            	            state._fsp--;
            	            if (state.failed) return ;

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(49);}


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);
            } finally {dbg.exitSubRule(50);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(349, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_assigned_variables");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_assigned_variables"


    // $ANTLR start "list_of_register_variables"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:351:1: list_of_register_variables : register_variable ( COMMA register_variable )* ;
    public final void list_of_register_variables() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_register_variables");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(351, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:351:28: ( register_variable ( COMMA register_variable )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:352:9: register_variable ( COMMA register_variable )*
            {
            dbg.location(352,9);
            pushFollow(FOLLOW_register_variable_in_list_of_register_variables2211);
            register_variable();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(352,27);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:352:27: ( COMMA register_variable )*
            try { dbg.enterSubRule(51);

            loop51:
            do {
                int alt51=2;
                try { dbg.enterDecision(51, decisionCanBacktrack[51]);

                int LA51_0 = input.LA(1);

                if ( (LA51_0==COMMA) ) {
                    alt51=1;
                }


                } finally {dbg.exitDecision(51);}

                switch (alt51) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:352:29: COMMA register_variable
            	    {
            	    dbg.location(352,29);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_register_variables2215); if (state.failed) return ;
            	    dbg.location(352,35);
            	    pushFollow(FOLLOW_register_variable_in_list_of_register_variables2217);
            	    register_variable();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);
            } finally {dbg.exitSubRule(51);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(353, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_register_variables");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_register_variables"


    // $ANTLR start "register_variable"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:355:1: register_variable : ( name_of_register | name_of_memory LBRACK expression COLON expression RBRACK );
    public final void register_variable() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "register_variable");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(355, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:355:19: ( name_of_register | name_of_memory LBRACK expression COLON expression RBRACK )
            int alt52=2;
            try { dbg.enterDecision(52, decisionCanBacktrack[52]);

            int LA52_0 = input.LA(1);

            if ( (LA52_0==IDENTIFIER||LA52_0==ESCAPED_IDENTIFIER) ) {
                int LA52_1 = input.LA(2);

                if ( (LA52_1==SEMI||LA52_1==COMMA) ) {
                    alt52=1;
                }
                else if ( (LA52_1==LBRACK) ) {
                    alt52=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 52, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(52);}

            switch (alt52) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:356:9: name_of_register
                    {
                    dbg.location(356,9);
                    pushFollow(FOLLOW_name_of_register_in_register_variable2245);
                    name_of_register();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:357:9: name_of_memory LBRACK expression COLON expression RBRACK
                    {
                    dbg.location(357,9);
                    pushFollow(FOLLOW_name_of_memory_in_register_variable2257);
                    name_of_memory();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(357,24);
                    match(input,LBRACK,FOLLOW_LBRACK_in_register_variable2259); if (state.failed) return ;
                    dbg.location(357,31);
                    pushFollow(FOLLOW_expression_in_register_variable2261);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(357,42);
                    match(input,COLON,FOLLOW_COLON_in_register_variable2263); if (state.failed) return ;
                    dbg.location(357,48);
                    pushFollow(FOLLOW_expression_in_register_variable2265);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(357,59);
                    match(input,RBRACK,FOLLOW_RBRACK_in_register_variable2267); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(358, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "register_variable");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "register_variable"


    // $ANTLR start "charge_strength"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:360:1: charge_strength : ( LPAREN 'small' RPAREN | LPAREN 'medium' RPAREN | LPAREN 'large' RPAREN );
    public final void charge_strength() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "charge_strength");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(360, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:360:17: ( LPAREN 'small' RPAREN | LPAREN 'medium' RPAREN | LPAREN 'large' RPAREN )
            int alt53=3;
            try { dbg.enterDecision(53, decisionCanBacktrack[53]);

            int LA53_0 = input.LA(1);

            if ( (LA53_0==LPAREN) ) {
                switch ( input.LA(2) ) {
                case 104:
                    {
                    alt53=1;
                    }
                    break;
                case 105:
                    {
                    alt53=2;
                    }
                    break;
                case 106:
                    {
                    alt53=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(53);}

            switch (alt53) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:361:9: LPAREN 'small' RPAREN
                    {
                    dbg.location(361,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_charge_strength2292); if (state.failed) return ;
                    dbg.location(361,16);
                    match(input,104,FOLLOW_104_in_charge_strength2294); if (state.failed) return ;
                    dbg.location(361,25);
                    match(input,RPAREN,FOLLOW_RPAREN_in_charge_strength2297); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:362:9: LPAREN 'medium' RPAREN
                    {
                    dbg.location(362,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_charge_strength2309); if (state.failed) return ;
                    dbg.location(362,16);
                    match(input,105,FOLLOW_105_in_charge_strength2311); if (state.failed) return ;
                    dbg.location(362,25);
                    match(input,RPAREN,FOLLOW_RPAREN_in_charge_strength2313); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:363:9: LPAREN 'large' RPAREN
                    {
                    dbg.location(363,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_charge_strength2325); if (state.failed) return ;
                    dbg.location(363,16);
                    match(input,106,FOLLOW_106_in_charge_strength2327); if (state.failed) return ;
                    dbg.location(363,25);
                    match(input,RPAREN,FOLLOW_RPAREN_in_charge_strength2330); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(364, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "charge_strength");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "charge_strength"


    // $ANTLR start "drive_strength"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:366:1: drive_strength : ( LPAREN strength0 COMMA strength1 RPAREN | LPAREN strength1 COMMA strength0 RPAREN );
    public final void drive_strength() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "drive_strength");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(366, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:366:16: ( LPAREN strength0 COMMA strength1 RPAREN | LPAREN strength1 COMMA strength0 RPAREN )
            int alt54=2;
            try { dbg.enterDecision(54, decisionCanBacktrack[54]);

            int LA54_0 = input.LA(1);

            if ( (LA54_0==LPAREN) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==91||(LA54_1>=107 && LA54_1<=110)) ) {
                    alt54=1;
                }
                else if ( (LA54_1==95||(LA54_1>=111 && LA54_1<=114)) ) {
                    alt54=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(54);}

            switch (alt54) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:367:9: LPAREN strength0 COMMA strength1 RPAREN
                    {
                    dbg.location(367,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_drive_strength2355); if (state.failed) return ;
                    dbg.location(367,16);
                    pushFollow(FOLLOW_strength0_in_drive_strength2357);
                    strength0();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(367,26);
                    match(input,COMMA,FOLLOW_COMMA_in_drive_strength2359); if (state.failed) return ;
                    dbg.location(367,32);
                    pushFollow(FOLLOW_strength1_in_drive_strength2361);
                    strength1();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(367,42);
                    match(input,RPAREN,FOLLOW_RPAREN_in_drive_strength2363); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:368:9: LPAREN strength1 COMMA strength0 RPAREN
                    {
                    dbg.location(368,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_drive_strength2375); if (state.failed) return ;
                    dbg.location(368,16);
                    pushFollow(FOLLOW_strength1_in_drive_strength2377);
                    strength1();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(368,26);
                    match(input,COMMA,FOLLOW_COMMA_in_drive_strength2379); if (state.failed) return ;
                    dbg.location(368,32);
                    pushFollow(FOLLOW_strength0_in_drive_strength2381);
                    strength0();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(368,42);
                    match(input,RPAREN,FOLLOW_RPAREN_in_drive_strength2383); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(369, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "drive_strength");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "drive_strength"


    // $ANTLR start "strength0"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:371:1: strength0 : ( 'supply0' | 'strong0' | 'pull0' | 'weak0' | 'highz0' );
    public final void strength0() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "strength0");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(371, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:371:11: ( 'supply0' | 'strong0' | 'pull0' | 'weak0' | 'highz0' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(371,11);
            if ( input.LA(1)==91||(input.LA(1)>=107 && input.LA(1)<=110) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(377, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "strength0");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "strength0"


    // $ANTLR start "strength1"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:379:1: strength1 : ( 'supply1' | 'strong1' | 'pull1' | 'weak1' | 'highz1' );
    public final void strength1() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "strength1");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(379, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:379:11: ( 'supply1' | 'strong1' | 'pull1' | 'weak1' | 'highz1' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(379,11);
            if ( input.LA(1)==95||(input.LA(1)>=111 && input.LA(1)<=114) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(385, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "strength1");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "strength1"


    // $ANTLR start "range"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:387:1: range : ( ( LBRACK expression COLON )=> LBRACK expression COLON expression RBRACK | LBRACK expression RBRACK );
    public final void range() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "range");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(387, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:387:7: ( ( LBRACK expression COLON )=> LBRACK expression COLON expression RBRACK | LBRACK expression RBRACK )
            int alt55=2;
            try { dbg.enterDecision(55, decisionCanBacktrack[55]);

            try {
                isCyclicDecision = true;
                alt55 = dfa55.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(55);}

            switch (alt55) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:388:2: ( LBRACK expression COLON )=> LBRACK expression COLON expression RBRACK
                    {
                    dbg.location(389,9);
                    match(input,LBRACK,FOLLOW_LBRACK_in_range2551); if (state.failed) return ;
                    dbg.location(389,16);
                    pushFollow(FOLLOW_expression_in_range2553);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(389,27);
                    match(input,COLON,FOLLOW_COLON_in_range2555); if (state.failed) return ;
                    dbg.location(389,33);
                    pushFollow(FOLLOW_expression_in_range2557);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(389,44);
                    match(input,RBRACK,FOLLOW_RBRACK_in_range2559); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:390:9: LBRACK expression RBRACK
                    {
                    dbg.location(390,9);
                    match(input,LBRACK,FOLLOW_LBRACK_in_range2571); if (state.failed) return ;
                    dbg.location(390,16);
                    pushFollow(FOLLOW_expression_in_range2573);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(390,27);
                    match(input,RBRACK,FOLLOW_RBRACK_in_range2575); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(391, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "range");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "range"


    // $ANTLR start "list_of_assignments"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:393:1: list_of_assignments : assignment ( COMMA assignment )* ;
    public final void list_of_assignments() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_assignments");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(393, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:393:21: ( assignment ( COMMA assignment )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:394:9: assignment ( COMMA assignment )*
            {
            dbg.location(394,9);
            pushFollow(FOLLOW_assignment_in_list_of_assignments2600);
            assignment();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(394,20);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:394:20: ( COMMA assignment )*
            try { dbg.enterSubRule(56);

            loop56:
            do {
                int alt56=2;
                try { dbg.enterDecision(56, decisionCanBacktrack[56]);

                int LA56_0 = input.LA(1);

                if ( (LA56_0==COMMA) ) {
                    alt56=1;
                }


                } finally {dbg.exitDecision(56);}

                switch (alt56) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:394:22: COMMA assignment
            	    {
            	    dbg.location(394,22);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_assignments2604); if (state.failed) return ;
            	    dbg.location(394,28);
            	    pushFollow(FOLLOW_assignment_in_list_of_assignments2606);
            	    assignment();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);
            } finally {dbg.exitSubRule(56);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(395, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_assignments");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_assignments"


    // $ANTLR start "gate_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:402:1: gate_declaration : gate_type ( drive_strength )? ( delay )? gate_instance ( COMMA gate_instance )* SEMI ;
    public final void gate_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "gate_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(402, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:402:18: ( gate_type ( drive_strength )? ( delay )? gate_instance ( COMMA gate_instance )* SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:403:2: gate_type ( drive_strength )? ( delay )? gate_instance ( COMMA gate_instance )* SEMI
            {
            dbg.location(403,2);
            pushFollow(FOLLOW_gate_type_in_gate_declaration2632);
            gate_type();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(403,12);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:403:12: ( drive_strength )?
            int alt57=2;
            try { dbg.enterSubRule(57);
            try { dbg.enterDecision(57, decisionCanBacktrack[57]);

            try {
                isCyclicDecision = true;
                alt57 = dfa57.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(57);}

            switch (alt57) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:403:13: drive_strength
                    {
                    dbg.location(403,13);
                    pushFollow(FOLLOW_drive_strength_in_gate_declaration2635);
                    drive_strength();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(57);}

            dbg.location(403,30);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:403:30: ( delay )?
            int alt58=2;
            try { dbg.enterSubRule(58);
            try { dbg.enterDecision(58, decisionCanBacktrack[58]);

            int LA58_0 = input.LA(1);

            if ( (LA58_0==POUND) ) {
                alt58=1;
            }
            } finally {dbg.exitDecision(58);}

            switch (alt58) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:403:31: delay
                    {
                    dbg.location(403,31);
                    pushFollow(FOLLOW_delay_in_gate_declaration2640);
                    delay();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(58);}

            dbg.location(404,11);
            pushFollow(FOLLOW_gate_instance_in_gate_declaration2654);
            gate_instance();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(404,25);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:404:25: ( COMMA gate_instance )*
            try { dbg.enterSubRule(59);

            loop59:
            do {
                int alt59=2;
                try { dbg.enterDecision(59, decisionCanBacktrack[59]);

                int LA59_0 = input.LA(1);

                if ( (LA59_0==COMMA) ) {
                    alt59=1;
                }


                } finally {dbg.exitDecision(59);}

                switch (alt59) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:404:27: COMMA gate_instance
            	    {
            	    dbg.location(404,27);
            	    match(input,COMMA,FOLLOW_COMMA_in_gate_declaration2658); if (state.failed) return ;
            	    dbg.location(404,33);
            	    pushFollow(FOLLOW_gate_instance_in_gate_declaration2660);
            	    gate_instance();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);
            } finally {dbg.exitSubRule(59);}

            dbg.location(404,50);
            match(input,SEMI,FOLLOW_SEMI_in_gate_declaration2665); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(405, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "gate_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "gate_declaration"


    // $ANTLR start "gate_type"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:407:1: gate_type : ( 'and' | 'nand' | 'or' | 'nor' | 'xor' | 'xnor' | 'buf' | 'bufif0' | 'bufif1' | 'not' | 'notif0' | 'notif1' | 'pulldown' | 'pullup' | 'nmos' | 'rnmos' | 'pmos' | 'rpmos' | 'cmos' | 'rcmos' | 'tran' | 'rtran' | 'tranif0' | 'rtranif0' | 'tranif1' | 'rtranif1' );
    public final void gate_type() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "gate_type");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(407, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:407:11: ( 'and' | 'nand' | 'or' | 'nor' | 'xor' | 'xnor' | 'buf' | 'bufif0' | 'bufif1' | 'not' | 'notif0' | 'notif1' | 'pulldown' | 'pullup' | 'nmos' | 'rnmos' | 'pmos' | 'rpmos' | 'cmos' | 'rcmos' | 'tran' | 'rtran' | 'tranif0' | 'rtranif0' | 'tranif1' | 'rtranif1' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(407,11);
            if ( (input.LA(1)>=115 && input.LA(1)<=140) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(434, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "gate_type");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "gate_type"


    // $ANTLR start "delay"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:436:1: delay : ( POUND NUMBER | POUND identifier | POUND LPAREN mintypmax_expression ( COMMA mintypmax_expression ( COMMA mintypmax_expression )? )? RPAREN );
    public final void delay() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "delay");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(436, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:436:7: ( POUND NUMBER | POUND identifier | POUND LPAREN mintypmax_expression ( COMMA mintypmax_expression ( COMMA mintypmax_expression )? )? RPAREN )
            int alt62=3;
            try { dbg.enterDecision(62, decisionCanBacktrack[62]);

            int LA62_0 = input.LA(1);

            if ( (LA62_0==POUND) ) {
                switch ( input.LA(2) ) {
                case NUMBER:
                    {
                    alt62=1;
                    }
                    break;
                case LPAREN:
                    {
                    alt62=3;
                    }
                    break;
                case IDENTIFIER:
                case ESCAPED_IDENTIFIER:
                    {
                    alt62=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 62, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(62);}

            switch (alt62) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:437:9: POUND NUMBER
                    {
                    dbg.location(437,9);
                    match(input,POUND,FOLLOW_POUND_in_delay3008); if (state.failed) return ;
                    dbg.location(437,15);
                    match(input,NUMBER,FOLLOW_NUMBER_in_delay3010); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:438:2: POUND identifier
                    {
                    dbg.location(438,2);
                    match(input,POUND,FOLLOW_POUND_in_delay3016); if (state.failed) return ;
                    dbg.location(438,8);
                    pushFollow(FOLLOW_identifier_in_delay3018);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:439:9: POUND LPAREN mintypmax_expression ( COMMA mintypmax_expression ( COMMA mintypmax_expression )? )? RPAREN
                    {
                    dbg.location(439,9);
                    match(input,POUND,FOLLOW_POUND_in_delay3030); if (state.failed) return ;
                    dbg.location(439,15);
                    match(input,LPAREN,FOLLOW_LPAREN_in_delay3032); if (state.failed) return ;
                    dbg.location(439,22);
                    pushFollow(FOLLOW_mintypmax_expression_in_delay3034);
                    mintypmax_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(440,10);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:440:10: ( COMMA mintypmax_expression ( COMMA mintypmax_expression )? )?
                    int alt61=2;
                    try { dbg.enterSubRule(61);
                    try { dbg.enterDecision(61, decisionCanBacktrack[61]);

                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==COMMA) ) {
                        alt61=1;
                    }
                    } finally {dbg.exitDecision(61);}

                    switch (alt61) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:440:12: COMMA mintypmax_expression ( COMMA mintypmax_expression )?
                            {
                            dbg.location(440,12);
                            match(input,COMMA,FOLLOW_COMMA_in_delay3047); if (state.failed) return ;
                            dbg.location(440,18);
                            pushFollow(FOLLOW_mintypmax_expression_in_delay3049);
                            mintypmax_expression();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(441,5);
                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:441:5: ( COMMA mintypmax_expression )?
                            int alt60=2;
                            try { dbg.enterSubRule(60);
                            try { dbg.enterDecision(60, decisionCanBacktrack[60]);

                            int LA60_0 = input.LA(1);

                            if ( (LA60_0==COMMA) ) {
                                alt60=1;
                            }
                            } finally {dbg.exitDecision(60);}

                            switch (alt60) {
                                case 1 :
                                    dbg.enterAlt(1);

                                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:441:7: COMMA mintypmax_expression
                                    {
                                    dbg.location(441,7);
                                    match(input,COMMA,FOLLOW_COMMA_in_delay3057); if (state.failed) return ;
                                    dbg.location(441,13);
                                    pushFollow(FOLLOW_mintypmax_expression_in_delay3059);
                                    mintypmax_expression();

                                    state._fsp--;
                                    if (state.failed) return ;

                                    }
                                    break;

                            }
                            } finally {dbg.exitSubRule(60);}


                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(61);}

                    dbg.location(443,8);
                    match(input,RPAREN,FOLLOW_RPAREN_in_delay3076); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(444, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "delay");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "delay"


    // $ANTLR start "gate_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:446:1: gate_instance : ( name_of_gate_instance )? LPAREN terminal ( COMMA terminal )* RPAREN ;
    public final void gate_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "gate_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(446, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:446:15: ( ( name_of_gate_instance )? LPAREN terminal ( COMMA terminal )* RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:447:9: ( name_of_gate_instance )? LPAREN terminal ( COMMA terminal )* RPAREN
            {
            dbg.location(447,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:447:9: ( name_of_gate_instance )?
            int alt63=2;
            try { dbg.enterSubRule(63);
            try { dbg.enterDecision(63, decisionCanBacktrack[63]);

            int LA63_0 = input.LA(1);

            if ( (LA63_0==IDENTIFIER||LA63_0==ESCAPED_IDENTIFIER) ) {
                alt63=1;
            }
            } finally {dbg.exitDecision(63);}

            switch (alt63) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:447:10: name_of_gate_instance
                    {
                    dbg.location(447,10);
                    pushFollow(FOLLOW_name_of_gate_instance_in_gate_instance3102);
                    name_of_gate_instance();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(63);}

            dbg.location(448,2);
            match(input,LPAREN,FOLLOW_LPAREN_in_gate_instance3107); if (state.failed) return ;
            dbg.location(448,9);
            pushFollow(FOLLOW_terminal_in_gate_instance3109);
            terminal();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(448,18);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:448:18: ( COMMA terminal )*
            try { dbg.enterSubRule(64);

            loop64:
            do {
                int alt64=2;
                try { dbg.enterDecision(64, decisionCanBacktrack[64]);

                int LA64_0 = input.LA(1);

                if ( (LA64_0==COMMA) ) {
                    alt64=1;
                }


                } finally {dbg.exitDecision(64);}

                switch (alt64) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:448:20: COMMA terminal
            	    {
            	    dbg.location(448,20);
            	    match(input,COMMA,FOLLOW_COMMA_in_gate_instance3113); if (state.failed) return ;
            	    dbg.location(448,26);
            	    pushFollow(FOLLOW_terminal_in_gate_instance3115);
            	    terminal();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);
            } finally {dbg.exitSubRule(64);}

            dbg.location(448,38);
            match(input,RPAREN,FOLLOW_RPAREN_in_gate_instance3120); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(449, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "gate_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "gate_instance"


    // $ANTLR start "udp_instantiation"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:451:1: udp_instantiation : name_of_UDP ( drive_strength )? ( delay )? udp_instance ( COMMA udp_instance )* SEMI ;
    public final void udp_instantiation() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_instantiation");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(451, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:451:19: ( name_of_UDP ( drive_strength )? ( delay )? udp_instance ( COMMA udp_instance )* SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:452:2: name_of_UDP ( drive_strength )? ( delay )? udp_instance ( COMMA udp_instance )* SEMI
            {
            dbg.location(452,2);
            pushFollow(FOLLOW_name_of_UDP_in_udp_instantiation3138);
            name_of_UDP();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(452,14);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:452:14: ( drive_strength )?
            int alt65=2;
            try { dbg.enterSubRule(65);
            try { dbg.enterDecision(65, decisionCanBacktrack[65]);

            try {
                isCyclicDecision = true;
                alt65 = dfa65.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(65);}

            switch (alt65) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:452:15: drive_strength
                    {
                    dbg.location(452,15);
                    pushFollow(FOLLOW_drive_strength_in_udp_instantiation3141);
                    drive_strength();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(65);}

            dbg.location(452,32);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:452:32: ( delay )?
            int alt66=2;
            try { dbg.enterSubRule(66);
            try { dbg.enterDecision(66, decisionCanBacktrack[66]);

            int LA66_0 = input.LA(1);

            if ( (LA66_0==POUND) ) {
                alt66=1;
            }
            } finally {dbg.exitDecision(66);}

            switch (alt66) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:452:33: delay
                    {
                    dbg.location(452,33);
                    pushFollow(FOLLOW_delay_in_udp_instantiation3146);
                    delay();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(66);}

            dbg.location(453,11);
            pushFollow(FOLLOW_udp_instance_in_udp_instantiation3160);
            udp_instance();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(453,24);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:453:24: ( COMMA udp_instance )*
            try { dbg.enterSubRule(67);

            loop67:
            do {
                int alt67=2;
                try { dbg.enterDecision(67, decisionCanBacktrack[67]);

                int LA67_0 = input.LA(1);

                if ( (LA67_0==COMMA) ) {
                    alt67=1;
                }


                } finally {dbg.exitDecision(67);}

                switch (alt67) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:453:26: COMMA udp_instance
            	    {
            	    dbg.location(453,26);
            	    match(input,COMMA,FOLLOW_COMMA_in_udp_instantiation3164); if (state.failed) return ;
            	    dbg.location(453,32);
            	    pushFollow(FOLLOW_udp_instance_in_udp_instantiation3166);
            	    udp_instance();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);
            } finally {dbg.exitSubRule(67);}

            dbg.location(453,48);
            match(input,SEMI,FOLLOW_SEMI_in_udp_instantiation3171); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(454, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_instantiation");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_instantiation"


    // $ANTLR start "udp_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:456:1: udp_instance : ( name_of_UDP_instance )? LPAREN terminal ( COMMA terminal )* RPAREN ;
    public final void udp_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(456, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:456:14: ( ( name_of_UDP_instance )? LPAREN terminal ( COMMA terminal )* RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:457:9: ( name_of_UDP_instance )? LPAREN terminal ( COMMA terminal )* RPAREN
            {
            dbg.location(457,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:457:9: ( name_of_UDP_instance )?
            int alt68=2;
            try { dbg.enterSubRule(68);
            try { dbg.enterDecision(68, decisionCanBacktrack[68]);

            int LA68_0 = input.LA(1);

            if ( (LA68_0==IDENTIFIER||LA68_0==ESCAPED_IDENTIFIER) ) {
                alt68=1;
            }
            } finally {dbg.exitDecision(68);}

            switch (alt68) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:457:10: name_of_UDP_instance
                    {
                    dbg.location(457,10);
                    pushFollow(FOLLOW_name_of_UDP_instance_in_udp_instance3190);
                    name_of_UDP_instance();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(68);}

            dbg.location(458,2);
            match(input,LPAREN,FOLLOW_LPAREN_in_udp_instance3195); if (state.failed) return ;
            dbg.location(458,9);
            pushFollow(FOLLOW_terminal_in_udp_instance3197);
            terminal();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(458,18);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:458:18: ( COMMA terminal )*
            try { dbg.enterSubRule(69);

            loop69:
            do {
                int alt69=2;
                try { dbg.enterDecision(69, decisionCanBacktrack[69]);

                int LA69_0 = input.LA(1);

                if ( (LA69_0==COMMA) ) {
                    alt69=1;
                }


                } finally {dbg.exitDecision(69);}

                switch (alt69) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:458:20: COMMA terminal
            	    {
            	    dbg.location(458,20);
            	    match(input,COMMA,FOLLOW_COMMA_in_udp_instance3201); if (state.failed) return ;
            	    dbg.location(458,26);
            	    pushFollow(FOLLOW_terminal_in_udp_instance3203);
            	    terminal();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);
            } finally {dbg.exitSubRule(69);}

            dbg.location(458,38);
            match(input,RPAREN,FOLLOW_RPAREN_in_udp_instance3208); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(459, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_instance"


    // $ANTLR start "terminal"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:461:1: terminal : expression ;
    public final void terminal() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "terminal");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(461, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:461:10: ( expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:462:9: expression
            {
            dbg.location(462,9);
            pushFollow(FOLLOW_expression_in_terminal3233);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(464, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "terminal");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "terminal"


    // $ANTLR start "module_instantiation"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:470:1: module_instantiation : name_of_module ( parameter_value_assignment )? module_instance ( COMMA module_instance )* SEMI ;
    public final void module_instantiation() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "module_instantiation");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(470, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:470:22: ( name_of_module ( parameter_value_assignment )? module_instance ( COMMA module_instance )* SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:471:9: name_of_module ( parameter_value_assignment )? module_instance ( COMMA module_instance )* SEMI
            {
            dbg.location(471,9);
            pushFollow(FOLLOW_name_of_module_in_module_instantiation3266);
            name_of_module();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(471,24);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:471:24: ( parameter_value_assignment )?
            int alt70=2;
            try { dbg.enterSubRule(70);
            try { dbg.enterDecision(70, decisionCanBacktrack[70]);

            int LA70_0 = input.LA(1);

            if ( (LA70_0==POUND) ) {
                alt70=1;
            }
            } finally {dbg.exitDecision(70);}

            switch (alt70) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:471:25: parameter_value_assignment
                    {
                    dbg.location(471,25);
                    pushFollow(FOLLOW_parameter_value_assignment_in_module_instantiation3269);
                    parameter_value_assignment();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(70);}

            dbg.location(472,9);
            pushFollow(FOLLOW_module_instance_in_module_instantiation3281);
            module_instance();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(472,25);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:472:25: ( COMMA module_instance )*
            try { dbg.enterSubRule(71);

            loop71:
            do {
                int alt71=2;
                try { dbg.enterDecision(71, decisionCanBacktrack[71]);

                int LA71_0 = input.LA(1);

                if ( (LA71_0==COMMA) ) {
                    alt71=1;
                }


                } finally {dbg.exitDecision(71);}

                switch (alt71) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:472:27: COMMA module_instance
            	    {
            	    dbg.location(472,27);
            	    match(input,COMMA,FOLLOW_COMMA_in_module_instantiation3285); if (state.failed) return ;
            	    dbg.location(472,33);
            	    pushFollow(FOLLOW_module_instance_in_module_instantiation3287);
            	    module_instance();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);
            } finally {dbg.exitSubRule(71);}

            dbg.location(472,52);
            match(input,SEMI,FOLLOW_SEMI_in_module_instantiation3292); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(473, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "module_instantiation");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "module_instantiation"


    // $ANTLR start "parameter_value_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:475:1: parameter_value_assignment : POUND LPAREN expression ( COMMA expression )* RPAREN ;
    public final void parameter_value_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "parameter_value_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(475, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:475:28: ( POUND LPAREN expression ( COMMA expression )* RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:476:9: POUND LPAREN expression ( COMMA expression )* RPAREN
            {
            dbg.location(476,9);
            match(input,POUND,FOLLOW_POUND_in_parameter_value_assignment3317); if (state.failed) return ;
            dbg.location(476,15);
            match(input,LPAREN,FOLLOW_LPAREN_in_parameter_value_assignment3319); if (state.failed) return ;
            dbg.location(476,22);
            pushFollow(FOLLOW_expression_in_parameter_value_assignment3321);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(476,33);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:476:33: ( COMMA expression )*
            try { dbg.enterSubRule(72);

            loop72:
            do {
                int alt72=2;
                try { dbg.enterDecision(72, decisionCanBacktrack[72]);

                int LA72_0 = input.LA(1);

                if ( (LA72_0==COMMA) ) {
                    alt72=1;
                }


                } finally {dbg.exitDecision(72);}

                switch (alt72) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:476:35: COMMA expression
            	    {
            	    dbg.location(476,35);
            	    match(input,COMMA,FOLLOW_COMMA_in_parameter_value_assignment3325); if (state.failed) return ;
            	    dbg.location(476,41);
            	    pushFollow(FOLLOW_expression_in_parameter_value_assignment3327);
            	    expression();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);
            } finally {dbg.exitSubRule(72);}

            dbg.location(476,55);
            match(input,RPAREN,FOLLOW_RPAREN_in_parameter_value_assignment3332); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(477, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "parameter_value_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "parameter_value_assignment"


    // $ANTLR start "module_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:479:1: module_instance : name_of_instance LPAREN list_of_module_connections RPAREN ;
    public final void module_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "module_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(479, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:479:17: ( name_of_instance LPAREN list_of_module_connections RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:480:9: name_of_instance LPAREN list_of_module_connections RPAREN
            {
            dbg.location(480,9);
            pushFollow(FOLLOW_name_of_instance_in_module_instance3357);
            name_of_instance();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(480,26);
            match(input,LPAREN,FOLLOW_LPAREN_in_module_instance3359); if (state.failed) return ;
            dbg.location(480,33);
            pushFollow(FOLLOW_list_of_module_connections_in_module_instance3361);
            list_of_module_connections();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(480,60);
            match(input,RPAREN,FOLLOW_RPAREN_in_module_instance3363); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(481, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "module_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "module_instance"


    // $ANTLR start "list_of_module_connections"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:483:1: list_of_module_connections : ( module_port_connection ( COMMA module_port_connection )* | named_port_connection ( COMMA named_port_connection )* );
    public final void list_of_module_connections() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_module_connections");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(483, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:483:28: ( module_port_connection ( COMMA module_port_connection )* | named_port_connection ( COMMA named_port_connection )* )
            int alt75=2;
            try { dbg.enterDecision(75, decisionCanBacktrack[75]);

            try {
                isCyclicDecision = true;
                alt75 = dfa75.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(75);}

            switch (alt75) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:484:9: module_port_connection ( COMMA module_port_connection )*
                    {
                    dbg.location(484,9);
                    pushFollow(FOLLOW_module_port_connection_in_list_of_module_connections3388);
                    module_port_connection();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(484,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:484:32: ( COMMA module_port_connection )*
                    try { dbg.enterSubRule(73);

                    loop73:
                    do {
                        int alt73=2;
                        try { dbg.enterDecision(73, decisionCanBacktrack[73]);

                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==COMMA) ) {
                            alt73=1;
                        }


                        } finally {dbg.exitDecision(73);}

                        switch (alt73) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:484:34: COMMA module_port_connection
                    	    {
                    	    dbg.location(484,34);
                    	    match(input,COMMA,FOLLOW_COMMA_in_list_of_module_connections3392); if (state.failed) return ;
                    	    dbg.location(484,40);
                    	    pushFollow(FOLLOW_module_port_connection_in_list_of_module_connections3394);
                    	    module_port_connection();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(73);}


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:485:9: named_port_connection ( COMMA named_port_connection )*
                    {
                    dbg.location(485,9);
                    pushFollow(FOLLOW_named_port_connection_in_list_of_module_connections3409);
                    named_port_connection();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(485,31);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:485:31: ( COMMA named_port_connection )*
                    try { dbg.enterSubRule(74);

                    loop74:
                    do {
                        int alt74=2;
                        try { dbg.enterDecision(74, decisionCanBacktrack[74]);

                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==COMMA) ) {
                            alt74=1;
                        }


                        } finally {dbg.exitDecision(74);}

                        switch (alt74) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:485:33: COMMA named_port_connection
                    	    {
                    	    dbg.location(485,33);
                    	    match(input,COMMA,FOLLOW_COMMA_in_list_of_module_connections3413); if (state.failed) return ;
                    	    dbg.location(485,39);
                    	    pushFollow(FOLLOW_named_port_connection_in_list_of_module_connections3415);
                    	    named_port_connection();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop74;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(74);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(486, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_module_connections");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_module_connections"


    // $ANTLR start "module_port_connection"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:488:1: module_port_connection : ( expression | );
    public final void module_port_connection() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "module_port_connection");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(488, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:488:24: ( expression | )
            int alt76=2;
            try { dbg.enterDecision(76, decisionCanBacktrack[76]);

            try {
                isCyclicDecision = true;
                alt76 = dfa76.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(76);}

            switch (alt76) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:489:9: expression
                    {
                    dbg.location(489,9);
                    pushFollow(FOLLOW_expression_in_module_port_connection3443);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:491:9: 
                    {
                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(491, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "module_port_connection");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "module_port_connection"


    // $ANTLR start "named_port_connection"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:495:1: named_port_connection : DOT IDENTIFIER LPAREN ( expression )? RPAREN ;
    public final void named_port_connection() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "named_port_connection");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(495, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:495:23: ( DOT IDENTIFIER LPAREN ( expression )? RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:496:9: DOT IDENTIFIER LPAREN ( expression )? RPAREN
            {
            dbg.location(496,9);
            match(input,DOT,FOLLOW_DOT_in_named_port_connection3476); if (state.failed) return ;
            dbg.location(496,13);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_named_port_connection3478); if (state.failed) return ;
            dbg.location(496,24);
            match(input,LPAREN,FOLLOW_LPAREN_in_named_port_connection3480); if (state.failed) return ;
            dbg.location(496,31);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:496:31: ( expression )?
            int alt77=2;
            try { dbg.enterSubRule(77);
            try { dbg.enterDecision(77, decisionCanBacktrack[77]);

            try {
                isCyclicDecision = true;
                alt77 = dfa77.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(77);}

            switch (alt77) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:496:32: expression
                    {
                    dbg.location(496,32);
                    pushFollow(FOLLOW_expression_in_named_port_connection3483);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(77);}

            dbg.location(496,45);
            match(input,RPAREN,FOLLOW_RPAREN_in_named_port_connection3487); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(497, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "named_port_connection");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "named_port_connection"


    // $ANTLR start "initial_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:503:1: initial_statement : 'initial' statement ;
    public final void initial_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "initial_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(503, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:503:19: ( 'initial' statement )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:504:9: 'initial' statement
            {
            dbg.location(504,9);
            match(input,72,FOLLOW_72_in_initial_statement3516); if (state.failed) return ;
            dbg.location(504,19);
            pushFollow(FOLLOW_statement_in_initial_statement3518);
            statement();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(505, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "initial_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "initial_statement"


    // $ANTLR start "always_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:507:1: always_statement : 'always' statement ;
    public final void always_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "always_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(507, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:507:18: ( 'always' statement )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:508:9: 'always' statement
            {
            dbg.location(508,9);
            match(input,141,FOLLOW_141_in_always_statement3543); if (state.failed) return ;
            dbg.location(508,18);
            pushFollow(FOLLOW_statement_in_always_statement3545);
            statement();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(509, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "always_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "always_statement"


    // $ANTLR start "statement_or_null"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:511:1: statement_or_null : ( ( statement )=> statement | SEMI );
    public final void statement_or_null() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "statement_or_null");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(511, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:511:19: ( ( statement )=> statement | SEMI )
            int alt78=2;
            try { dbg.enterDecision(78, decisionCanBacktrack[78]);

            try {
                isCyclicDecision = true;
                alt78 = dfa78.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(78);}

            switch (alt78) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:512:9: ( statement )=> statement
                    {
                    dbg.location(512,24);
                    pushFollow(FOLLOW_statement_in_statement_or_null3576);
                    statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:513:2: SEMI
                    {
                    dbg.location(513,2);
                    match(input,SEMI,FOLLOW_SEMI_in_statement_or_null3581); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(514, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "statement_or_null");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "statement_or_null"


    // $ANTLR start "statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:516:1: statement : ( ( lvalue ASSIGN )=> blocking_assignment SEMI | ( lvalue LE )=> non_blocking_assignment SEMI | conditional_statement | case_statement | loop_statement | procedural_timing_control_statement | wait_statement | event_trigger | seq_block | par_block | task_enable | system_task_enable | disable_statement | procedural_continuous_assignment );
    public final void statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(516, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:516:11: ( ( lvalue ASSIGN )=> blocking_assignment SEMI | ( lvalue LE )=> non_blocking_assignment SEMI | conditional_statement | case_statement | loop_statement | procedural_timing_control_statement | wait_statement | event_trigger | seq_block | par_block | task_enable | system_task_enable | disable_statement | procedural_continuous_assignment )
            int alt79=14;
            try { dbg.enterDecision(79, decisionCanBacktrack[79]);

            try {
                isCyclicDecision = true;
                alt79 = dfa79.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(79);}

            switch (alt79) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:517:9: ( lvalue ASSIGN )=> blocking_assignment SEMI
                    {
                    dbg.location(517,28);
                    pushFollow(FOLLOW_blocking_assignment_in_statement3614);
                    blocking_assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(517,48);
                    match(input,SEMI,FOLLOW_SEMI_in_statement3616); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:518:9: ( lvalue LE )=> non_blocking_assignment SEMI
                    {
                    dbg.location(518,24);
                    pushFollow(FOLLOW_non_blocking_assignment_in_statement3636);
                    non_blocking_assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(518,48);
                    match(input,SEMI,FOLLOW_SEMI_in_statement3638); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:519:9: conditional_statement
                    {
                    dbg.location(519,9);
                    pushFollow(FOLLOW_conditional_statement_in_statement3650);
                    conditional_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:520:9: case_statement
                    {
                    dbg.location(520,9);
                    pushFollow(FOLLOW_case_statement_in_statement3662);
                    case_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:521:9: loop_statement
                    {
                    dbg.location(521,9);
                    pushFollow(FOLLOW_loop_statement_in_statement3674);
                    loop_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:522:9: procedural_timing_control_statement
                    {
                    dbg.location(522,9);
                    pushFollow(FOLLOW_procedural_timing_control_statement_in_statement3686);
                    procedural_timing_control_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:523:9: wait_statement
                    {
                    dbg.location(523,9);
                    pushFollow(FOLLOW_wait_statement_in_statement3698);
                    wait_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 8 :
                    dbg.enterAlt(8);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:524:9: event_trigger
                    {
                    dbg.location(524,9);
                    pushFollow(FOLLOW_event_trigger_in_statement3710);
                    event_trigger();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 9 :
                    dbg.enterAlt(9);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:525:9: seq_block
                    {
                    dbg.location(525,9);
                    pushFollow(FOLLOW_seq_block_in_statement3722);
                    seq_block();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 10 :
                    dbg.enterAlt(10);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:526:9: par_block
                    {
                    dbg.location(526,9);
                    pushFollow(FOLLOW_par_block_in_statement3734);
                    par_block();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 11 :
                    dbg.enterAlt(11);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:527:9: task_enable
                    {
                    dbg.location(527,9);
                    pushFollow(FOLLOW_task_enable_in_statement3746);
                    task_enable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 12 :
                    dbg.enterAlt(12);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:528:9: system_task_enable
                    {
                    dbg.location(528,9);
                    pushFollow(FOLLOW_system_task_enable_in_statement3758);
                    system_task_enable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 13 :
                    dbg.enterAlt(13);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:529:9: disable_statement
                    {
                    dbg.location(529,9);
                    pushFollow(FOLLOW_disable_statement_in_statement3770);
                    disable_statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 14 :
                    dbg.enterAlt(14);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:530:9: procedural_continuous_assignment
                    {
                    dbg.location(530,9);
                    pushFollow(FOLLOW_procedural_continuous_assignment_in_statement3782);
                    procedural_continuous_assignment();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(531, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "statement"


    // $ANTLR start "assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:533:1: assignment : lvalue ASSIGN expression ;
    public final void assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(533, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:533:12: ( lvalue ASSIGN expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:534:9: lvalue ASSIGN expression
            {
            dbg.location(534,9);
            pushFollow(FOLLOW_lvalue_in_assignment3807);
            lvalue();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(534,16);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment3809); if (state.failed) return ;
            dbg.location(534,23);
            pushFollow(FOLLOW_expression_in_assignment3811);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(535, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "assignment"


    // $ANTLR start "blocking_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:537:1: blocking_assignment : lvalue ASSIGN ( delay_or_event_control )? expression ;
    public final void blocking_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "blocking_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(537, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:537:21: ( lvalue ASSIGN ( delay_or_event_control )? expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:538:9: lvalue ASSIGN ( delay_or_event_control )? expression
            {
            dbg.location(538,9);
            pushFollow(FOLLOW_lvalue_in_blocking_assignment3836);
            lvalue();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(538,16);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_blocking_assignment3838); if (state.failed) return ;
            dbg.location(538,23);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:538:23: ( delay_or_event_control )?
            int alt80=2;
            try { dbg.enterSubRule(80);
            try { dbg.enterDecision(80, decisionCanBacktrack[80]);

            try {
                isCyclicDecision = true;
                alt80 = dfa80.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(80);}

            switch (alt80) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:538:25: delay_or_event_control
                    {
                    dbg.location(538,25);
                    pushFollow(FOLLOW_delay_or_event_control_in_blocking_assignment3842);
                    delay_or_event_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(80);}

            dbg.location(538,52);
            pushFollow(FOLLOW_expression_in_blocking_assignment3848);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(539, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "blocking_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "blocking_assignment"


    // $ANTLR start "non_blocking_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:541:1: non_blocking_assignment : lvalue LE ( delay_or_event_control )? expression ;
    public final void non_blocking_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "non_blocking_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(541, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:541:25: ( lvalue LE ( delay_or_event_control )? expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:542:9: lvalue LE ( delay_or_event_control )? expression
            {
            dbg.location(542,9);
            pushFollow(FOLLOW_lvalue_in_non_blocking_assignment3873);
            lvalue();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(542,16);
            match(input,LE,FOLLOW_LE_in_non_blocking_assignment3875); if (state.failed) return ;
            dbg.location(542,19);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:542:19: ( delay_or_event_control )?
            int alt81=2;
            try { dbg.enterSubRule(81);
            try { dbg.enterDecision(81, decisionCanBacktrack[81]);

            try {
                isCyclicDecision = true;
                alt81 = dfa81.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(81);}

            switch (alt81) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:542:21: delay_or_event_control
                    {
                    dbg.location(542,21);
                    pushFollow(FOLLOW_delay_or_event_control_in_non_blocking_assignment3879);
                    delay_or_event_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(81);}

            dbg.location(542,48);
            pushFollow(FOLLOW_expression_in_non_blocking_assignment3885);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(543, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "non_blocking_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "non_blocking_assignment"


    // $ANTLR start "conditional_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:547:1: conditional_statement : 'if' LPAREN expression RPAREN statement_or_null ( 'else' statement_or_null )? ;
    public final void conditional_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "conditional_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(547, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:547:23: ( 'if' LPAREN expression RPAREN statement_or_null ( 'else' statement_or_null )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:548:9: 'if' LPAREN expression RPAREN statement_or_null ( 'else' statement_or_null )?
            {
            dbg.location(548,9);
            match(input,142,FOLLOW_142_in_conditional_statement3918); if (state.failed) return ;
            dbg.location(548,14);
            match(input,LPAREN,FOLLOW_LPAREN_in_conditional_statement3920); if (state.failed) return ;
            dbg.location(548,21);
            pushFollow(FOLLOW_expression_in_conditional_statement3922);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(548,32);
            match(input,RPAREN,FOLLOW_RPAREN_in_conditional_statement3924); if (state.failed) return ;
            dbg.location(548,39);
            pushFollow(FOLLOW_statement_or_null_in_conditional_statement3926);
            statement_or_null();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(549,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:549:9: ( 'else' statement_or_null )?
            int alt82=2;
            try { dbg.enterSubRule(82);
            try { dbg.enterDecision(82, decisionCanBacktrack[82]);

            try {
                isCyclicDecision = true;
                alt82 = dfa82.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(82);}

            switch (alt82) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:549:13: 'else' statement_or_null
                    {
                    dbg.location(549,13);
                    match(input,143,FOLLOW_143_in_conditional_statement3940); if (state.failed) return ;
                    dbg.location(549,20);
                    pushFollow(FOLLOW_statement_or_null_in_conditional_statement3942);
                    statement_or_null();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(82);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(550, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "conditional_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "conditional_statement"


    // $ANTLR start "case_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:552:1: case_statement : case_keyword LPAREN expression RPAREN ( case_item )+ 'endcase' ;
    public final void case_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "case_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(552, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:552:16: ( case_keyword LPAREN expression RPAREN ( case_item )+ 'endcase' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:553:9: case_keyword LPAREN expression RPAREN ( case_item )+ 'endcase'
            {
            dbg.location(553,9);
            pushFollow(FOLLOW_case_keyword_in_case_statement3969);
            case_keyword();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(553,22);
            match(input,LPAREN,FOLLOW_LPAREN_in_case_statement3971); if (state.failed) return ;
            dbg.location(553,29);
            pushFollow(FOLLOW_expression_in_case_statement3973);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(553,40);
            match(input,RPAREN,FOLLOW_RPAREN_in_case_statement3975); if (state.failed) return ;
            dbg.location(553,47);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:553:47: ( case_item )+
            int cnt83=0;
            try { dbg.enterSubRule(83);

            loop83:
            do {
                int alt83=2;
                try { dbg.enterDecision(83, decisionCanBacktrack[83]);

                try {
                    isCyclicDecision = true;
                    alt83 = dfa83.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(83);}

                switch (alt83) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:553:48: case_item
            	    {
            	    dbg.location(553,48);
            	    pushFollow(FOLLOW_case_item_in_case_statement3978);
            	    case_item();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt83 >= 1 ) break loop83;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(83, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt83++;
            } while (true);
            } finally {dbg.exitSubRule(83);}

            dbg.location(553,60);
            match(input,144,FOLLOW_144_in_case_statement3982); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(554, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "case_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "case_statement"


    // $ANTLR start "case_keyword"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:556:1: case_keyword : ( 'case' | 'casez' | 'casex' );
    public final void case_keyword() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "case_keyword");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(556, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:556:14: ( 'case' | 'casez' | 'casex' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(556,14);
            if ( (input.LA(1)>=145 && input.LA(1)<=147) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(558, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "case_keyword");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "case_keyword"


    // $ANTLR start "case_item"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:560:1: case_item : ( expression ( COMMA expression )* COLON statement_or_null | 'default' ( COLON )? statement_or_null );
    public final void case_item() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "case_item");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(560, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:560:11: ( expression ( COMMA expression )* COLON statement_or_null | 'default' ( COLON )? statement_or_null )
            int alt86=2;
            try { dbg.enterDecision(86, decisionCanBacktrack[86]);

            try {
                isCyclicDecision = true;
                alt86 = dfa86.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(86);}

            switch (alt86) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:561:9: expression ( COMMA expression )* COLON statement_or_null
                    {
                    dbg.location(561,9);
                    pushFollow(FOLLOW_expression_in_case_item4040);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(561,20);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:561:20: ( COMMA expression )*
                    try { dbg.enterSubRule(84);

                    loop84:
                    do {
                        int alt84=2;
                        try { dbg.enterDecision(84, decisionCanBacktrack[84]);

                        int LA84_0 = input.LA(1);

                        if ( (LA84_0==COMMA) ) {
                            alt84=1;
                        }


                        } finally {dbg.exitDecision(84);}

                        switch (alt84) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:561:22: COMMA expression
                    	    {
                    	    dbg.location(561,22);
                    	    match(input,COMMA,FOLLOW_COMMA_in_case_item4044); if (state.failed) return ;
                    	    dbg.location(561,28);
                    	    pushFollow(FOLLOW_expression_in_case_item4046);
                    	    expression();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop84;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(84);}

                    dbg.location(561,42);
                    match(input,COLON,FOLLOW_COLON_in_case_item4051); if (state.failed) return ;
                    dbg.location(561,48);
                    pushFollow(FOLLOW_statement_or_null_in_case_item4053);
                    statement_or_null();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:562:9: 'default' ( COLON )? statement_or_null
                    {
                    dbg.location(562,9);
                    match(input,148,FOLLOW_148_in_case_item4065); if (state.failed) return ;
                    dbg.location(562,19);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:562:19: ( COLON )?
                    int alt85=2;
                    try { dbg.enterSubRule(85);
                    try { dbg.enterDecision(85, decisionCanBacktrack[85]);

                    try {
                        isCyclicDecision = true;
                        alt85 = dfa85.predict(input);
                    }
                    catch (NoViableAltException nvae) {
                        dbg.recognitionException(nvae);
                        throw nvae;
                    }
                    } finally {dbg.exitDecision(85);}

                    switch (alt85) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:562:20: COLON
                            {
                            dbg.location(562,20);
                            match(input,COLON,FOLLOW_COLON_in_case_item4068); if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(85);}

                    dbg.location(562,28);
                    pushFollow(FOLLOW_statement_or_null_in_case_item4072);
                    statement_or_null();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(563, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "case_item");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "case_item"


    // $ANTLR start "loop_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:565:1: loop_statement : ( 'forever' statement | 'repeat' LPAREN expression RPAREN statement | 'while' LPAREN expression RPAREN statement | 'for' LPAREN assignment SEMI expression SEMI assignment RPAREN statement );
    public final void loop_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "loop_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(565, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:565:16: ( 'forever' statement | 'repeat' LPAREN expression RPAREN statement | 'while' LPAREN expression RPAREN statement | 'for' LPAREN assignment SEMI expression SEMI assignment RPAREN statement )
            int alt87=4;
            try { dbg.enterDecision(87, decisionCanBacktrack[87]);

            switch ( input.LA(1) ) {
            case 149:
                {
                alt87=1;
                }
                break;
            case 150:
                {
                alt87=2;
                }
                break;
            case 151:
                {
                alt87=3;
                }
                break;
            case 152:
                {
                alt87=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(87);}

            switch (alt87) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:566:9: 'forever' statement
                    {
                    dbg.location(566,9);
                    match(input,149,FOLLOW_149_in_loop_statement4097); if (state.failed) return ;
                    dbg.location(566,19);
                    pushFollow(FOLLOW_statement_in_loop_statement4099);
                    statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:567:9: 'repeat' LPAREN expression RPAREN statement
                    {
                    dbg.location(567,9);
                    match(input,150,FOLLOW_150_in_loop_statement4111); if (state.failed) return ;
                    dbg.location(567,18);
                    match(input,LPAREN,FOLLOW_LPAREN_in_loop_statement4113); if (state.failed) return ;
                    dbg.location(567,25);
                    pushFollow(FOLLOW_expression_in_loop_statement4115);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(567,36);
                    match(input,RPAREN,FOLLOW_RPAREN_in_loop_statement4117); if (state.failed) return ;
                    dbg.location(567,43);
                    pushFollow(FOLLOW_statement_in_loop_statement4119);
                    statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:568:9: 'while' LPAREN expression RPAREN statement
                    {
                    dbg.location(568,9);
                    match(input,151,FOLLOW_151_in_loop_statement4131); if (state.failed) return ;
                    dbg.location(568,17);
                    match(input,LPAREN,FOLLOW_LPAREN_in_loop_statement4133); if (state.failed) return ;
                    dbg.location(568,24);
                    pushFollow(FOLLOW_expression_in_loop_statement4135);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(568,35);
                    match(input,RPAREN,FOLLOW_RPAREN_in_loop_statement4137); if (state.failed) return ;
                    dbg.location(568,42);
                    pushFollow(FOLLOW_statement_in_loop_statement4139);
                    statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:569:9: 'for' LPAREN assignment SEMI expression SEMI assignment RPAREN statement
                    {
                    dbg.location(569,9);
                    match(input,152,FOLLOW_152_in_loop_statement4151); if (state.failed) return ;
                    dbg.location(569,15);
                    match(input,LPAREN,FOLLOW_LPAREN_in_loop_statement4153); if (state.failed) return ;
                    dbg.location(569,22);
                    pushFollow(FOLLOW_assignment_in_loop_statement4155);
                    assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(569,33);
                    match(input,SEMI,FOLLOW_SEMI_in_loop_statement4157); if (state.failed) return ;
                    dbg.location(569,38);
                    pushFollow(FOLLOW_expression_in_loop_statement4159);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(569,49);
                    match(input,SEMI,FOLLOW_SEMI_in_loop_statement4161); if (state.failed) return ;
                    dbg.location(569,54);
                    pushFollow(FOLLOW_assignment_in_loop_statement4163);
                    assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(569,65);
                    match(input,RPAREN,FOLLOW_RPAREN_in_loop_statement4165); if (state.failed) return ;
                    dbg.location(569,72);
                    pushFollow(FOLLOW_statement_in_loop_statement4167);
                    statement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(570, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "loop_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "loop_statement"


    // $ANTLR start "procedural_timing_control_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:572:1: procedural_timing_control_statement : delay_or_event_control statement_or_null ;
    public final void procedural_timing_control_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "procedural_timing_control_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(572, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:572:37: ( delay_or_event_control statement_or_null )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:573:9: delay_or_event_control statement_or_null
            {
            dbg.location(573,9);
            pushFollow(FOLLOW_delay_or_event_control_in_procedural_timing_control_statement4192);
            delay_or_event_control();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(573,32);
            pushFollow(FOLLOW_statement_or_null_in_procedural_timing_control_statement4194);
            statement_or_null();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(574, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "procedural_timing_control_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "procedural_timing_control_statement"


    // $ANTLR start "wait_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:576:1: wait_statement : 'wait' LPAREN expression RPAREN statement_or_null ;
    public final void wait_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "wait_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(576, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:576:16: ( 'wait' LPAREN expression RPAREN statement_or_null )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:577:9: 'wait' LPAREN expression RPAREN statement_or_null
            {
            dbg.location(577,9);
            match(input,153,FOLLOW_153_in_wait_statement4219); if (state.failed) return ;
            dbg.location(577,16);
            match(input,LPAREN,FOLLOW_LPAREN_in_wait_statement4221); if (state.failed) return ;
            dbg.location(577,23);
            pushFollow(FOLLOW_expression_in_wait_statement4223);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(577,34);
            match(input,RPAREN,FOLLOW_RPAREN_in_wait_statement4225); if (state.failed) return ;
            dbg.location(577,41);
            pushFollow(FOLLOW_statement_or_null_in_wait_statement4227);
            statement_or_null();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(578, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "wait_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "wait_statement"


    // $ANTLR start "event_trigger"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:580:1: event_trigger : TRIGGER name_of_event SEMI ;
    public final void event_trigger() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "event_trigger");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(580, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:580:15: ( TRIGGER name_of_event SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:581:9: TRIGGER name_of_event SEMI
            {
            dbg.location(581,9);
            match(input,TRIGGER,FOLLOW_TRIGGER_in_event_trigger4252); if (state.failed) return ;
            dbg.location(581,17);
            pushFollow(FOLLOW_name_of_event_in_event_trigger4254);
            name_of_event();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(581,31);
            match(input,SEMI,FOLLOW_SEMI_in_event_trigger4256); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(582, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "event_trigger");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "event_trigger"


    // $ANTLR start "disable_statement"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:584:1: disable_statement : 'disable' IDENTIFIER SEMI ;
    public final void disable_statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "disable_statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(584, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:584:19: ( 'disable' IDENTIFIER SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:585:9: 'disable' IDENTIFIER SEMI
            {
            dbg.location(585,9);
            match(input,154,FOLLOW_154_in_disable_statement4281); if (state.failed) return ;
            dbg.location(585,19);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_disable_statement4283); if (state.failed) return ;
            dbg.location(585,30);
            match(input,SEMI,FOLLOW_SEMI_in_disable_statement4285); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(586, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "disable_statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "disable_statement"


    // $ANTLR start "seq_block"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:588:1: seq_block : 'begin' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'end' ;
    public final void seq_block() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "seq_block");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(588, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:588:11: ( 'begin' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'end' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:589:9: 'begin' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'end'
            {
            dbg.location(589,9);
            match(input,155,FOLLOW_155_in_seq_block4310); if (state.failed) return ;
            dbg.location(590,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:590:9: ( COLON name_of_block ( block_declaration )* )?
            int alt89=2;
            try { dbg.enterSubRule(89);
            try { dbg.enterDecision(89, decisionCanBacktrack[89]);

            try {
                isCyclicDecision = true;
                alt89 = dfa89.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(89);}

            switch (alt89) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:590:11: COLON name_of_block ( block_declaration )*
                    {
                    dbg.location(590,11);
                    match(input,COLON,FOLLOW_COLON_in_seq_block4322); if (state.failed) return ;
                    dbg.location(590,17);
                    pushFollow(FOLLOW_name_of_block_in_seq_block4324);
                    name_of_block();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(590,31);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:590:31: ( block_declaration )*
                    try { dbg.enterSubRule(88);

                    loop88:
                    do {
                        int alt88=2;
                        try { dbg.enterDecision(88, decisionCanBacktrack[88]);

                        try {
                            isCyclicDecision = true;
                            alt88 = dfa88.predict(input);
                        }
                        catch (NoViableAltException nvae) {
                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                        } finally {dbg.exitDecision(88);}

                        switch (alt88) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:590:32: block_declaration
                    	    {
                    	    dbg.location(590,32);
                    	    pushFollow(FOLLOW_block_declaration_in_seq_block4327);
                    	    block_declaration();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop88;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(88);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(89);}

            dbg.location(591,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:591:9: ( statement )*
            try { dbg.enterSubRule(90);

            loop90:
            do {
                int alt90=2;
                try { dbg.enterDecision(90, decisionCanBacktrack[90]);

                try {
                    isCyclicDecision = true;
                    alt90 = dfa90.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(90);}

                switch (alt90) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:591:10: statement
            	    {
            	    dbg.location(591,10);
            	    pushFollow(FOLLOW_statement_in_seq_block4343);
            	    statement();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);
            } finally {dbg.exitSubRule(90);}

            dbg.location(592,2);
            match(input,156,FOLLOW_156_in_seq_block4348); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(593, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "seq_block");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "seq_block"


    // $ANTLR start "par_block"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:595:1: par_block : 'fork' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'join' ;
    public final void par_block() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "par_block");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(595, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:595:11: ( 'fork' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'join' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:596:9: 'fork' ( COLON name_of_block ( block_declaration )* )? ( statement )* 'join'
            {
            dbg.location(596,9);
            match(input,157,FOLLOW_157_in_par_block4366); if (state.failed) return ;
            dbg.location(597,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:597:9: ( COLON name_of_block ( block_declaration )* )?
            int alt92=2;
            try { dbg.enterSubRule(92);
            try { dbg.enterDecision(92, decisionCanBacktrack[92]);

            try {
                isCyclicDecision = true;
                alt92 = dfa92.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(92);}

            switch (alt92) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:597:11: COLON name_of_block ( block_declaration )*
                    {
                    dbg.location(597,11);
                    match(input,COLON,FOLLOW_COLON_in_par_block4378); if (state.failed) return ;
                    dbg.location(597,17);
                    pushFollow(FOLLOW_name_of_block_in_par_block4380);
                    name_of_block();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(597,31);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:597:31: ( block_declaration )*
                    try { dbg.enterSubRule(91);

                    loop91:
                    do {
                        int alt91=2;
                        try { dbg.enterDecision(91, decisionCanBacktrack[91]);

                        try {
                            isCyclicDecision = true;
                            alt91 = dfa91.predict(input);
                        }
                        catch (NoViableAltException nvae) {
                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                        } finally {dbg.exitDecision(91);}

                        switch (alt91) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:597:32: block_declaration
                    	    {
                    	    dbg.location(597,32);
                    	    pushFollow(FOLLOW_block_declaration_in_par_block4383);
                    	    block_declaration();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop91;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(91);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(92);}

            dbg.location(598,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:598:9: ( statement )*
            try { dbg.enterSubRule(93);

            loop93:
            do {
                int alt93=2;
                try { dbg.enterDecision(93, decisionCanBacktrack[93]);

                try {
                    isCyclicDecision = true;
                    alt93 = dfa93.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(93);}

                switch (alt93) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:598:10: statement
            	    {
            	    dbg.location(598,10);
            	    pushFollow(FOLLOW_statement_in_par_block4399);
            	    statement();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);
            } finally {dbg.exitSubRule(93);}

            dbg.location(599,2);
            match(input,158,FOLLOW_158_in_par_block4404); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(600, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "par_block");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "par_block"


    // $ANTLR start "block_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:602:1: block_declaration : ( parameter_declaration | reg_declaration | integer_declaration | real_declaration | time_declaration | event_declaration );
    public final void block_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "block_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(602, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:602:19: ( parameter_declaration | reg_declaration | integer_declaration | real_declaration | time_declaration | event_declaration )
            int alt94=6;
            try { dbg.enterDecision(94, decisionCanBacktrack[94]);

            switch ( input.LA(1) ) {
            case 84:
                {
                alt94=1;
                }
                break;
            case 100:
                {
                alt94=2;
                }
                break;
            case 82:
                {
                alt94=3;
                }
                break;
            case 83:
                {
                alt94=4;
                }
                break;
            case 101:
                {
                alt94=5;
                }
                break;
            case 102:
                {
                alt94=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(94);}

            switch (alt94) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:603:9: parameter_declaration
                    {
                    dbg.location(603,9);
                    pushFollow(FOLLOW_parameter_declaration_in_block_declaration4429);
                    parameter_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:604:9: reg_declaration
                    {
                    dbg.location(604,9);
                    pushFollow(FOLLOW_reg_declaration_in_block_declaration4441);
                    reg_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:605:9: integer_declaration
                    {
                    dbg.location(605,9);
                    pushFollow(FOLLOW_integer_declaration_in_block_declaration4453);
                    integer_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:606:9: real_declaration
                    {
                    dbg.location(606,9);
                    pushFollow(FOLLOW_real_declaration_in_block_declaration4465);
                    real_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:607:9: time_declaration
                    {
                    dbg.location(607,9);
                    pushFollow(FOLLOW_time_declaration_in_block_declaration4477);
                    time_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:608:9: event_declaration
                    {
                    dbg.location(608,9);
                    pushFollow(FOLLOW_event_declaration_in_block_declaration4489);
                    event_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(609, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "block_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "block_declaration"


    // $ANTLR start "task_enable"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:611:1: task_enable : name_of_task ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI ;
    public final void task_enable() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "task_enable");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(611, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:611:13: ( name_of_task ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:9: name_of_task ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI
            {
            dbg.location(612,9);
            pushFollow(FOLLOW_name_of_task_in_task_enable4515);
            name_of_task();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(612,22);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:22: ( LPAREN expression ( COMMA ( expression )? )* RPAREN )?
            int alt97=2;
            try { dbg.enterSubRule(97);
            try { dbg.enterDecision(97, decisionCanBacktrack[97]);

            int LA97_0 = input.LA(1);

            if ( (LA97_0==LPAREN) ) {
                alt97=1;
            }
            } finally {dbg.exitDecision(97);}

            switch (alt97) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:24: LPAREN expression ( COMMA ( expression )? )* RPAREN
                    {
                    dbg.location(612,24);
                    match(input,LPAREN,FOLLOW_LPAREN_in_task_enable4519); if (state.failed) return ;
                    dbg.location(612,31);
                    pushFollow(FOLLOW_expression_in_task_enable4521);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(612,42);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:42: ( COMMA ( expression )? )*
                    try { dbg.enterSubRule(96);

                    loop96:
                    do {
                        int alt96=2;
                        try { dbg.enterDecision(96, decisionCanBacktrack[96]);

                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==COMMA) ) {
                            alt96=1;
                        }


                        } finally {dbg.exitDecision(96);}

                        switch (alt96) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:43: COMMA ( expression )?
                    	    {
                    	    dbg.location(612,43);
                    	    match(input,COMMA,FOLLOW_COMMA_in_task_enable4524); if (state.failed) return ;
                    	    dbg.location(612,49);
                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:49: ( expression )?
                    	    int alt95=2;
                    	    try { dbg.enterSubRule(95);
                    	    try { dbg.enterDecision(95, decisionCanBacktrack[95]);

                    	    try {
                    	        isCyclicDecision = true;
                    	        alt95 = dfa95.predict(input);
                    	    }
                    	    catch (NoViableAltException nvae) {
                    	        dbg.recognitionException(nvae);
                    	        throw nvae;
                    	    }
                    	    } finally {dbg.exitDecision(95);}

                    	    switch (alt95) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:612:50: expression
                    	            {
                    	            dbg.location(612,50);
                    	            pushFollow(FOLLOW_expression_in_task_enable4527);
                    	            expression();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(95);}


                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(96);}

                    dbg.location(612,65);
                    match(input,RPAREN,FOLLOW_RPAREN_in_task_enable4533); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(97);}

            dbg.location(613,2);
            match(input,SEMI,FOLLOW_SEMI_in_task_enable4539); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(614, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "task_enable");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "task_enable"


    // $ANTLR start "system_task_enable"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:616:1: system_task_enable : SYSTEM_TASK_NAME ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI ;
    public final void system_task_enable() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "system_task_enable");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(616, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:616:20: ( SYSTEM_TASK_NAME ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:9: SYSTEM_TASK_NAME ( LPAREN expression ( COMMA ( expression )? )* RPAREN )? SEMI
            {
            dbg.location(617,9);
            match(input,SYSTEM_TASK_NAME,FOLLOW_SYSTEM_TASK_NAME_in_system_task_enable4564); if (state.failed) return ;
            dbg.location(617,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:26: ( LPAREN expression ( COMMA ( expression )? )* RPAREN )?
            int alt100=2;
            try { dbg.enterSubRule(100);
            try { dbg.enterDecision(100, decisionCanBacktrack[100]);

            int LA100_0 = input.LA(1);

            if ( (LA100_0==LPAREN) ) {
                alt100=1;
            }
            } finally {dbg.exitDecision(100);}

            switch (alt100) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:28: LPAREN expression ( COMMA ( expression )? )* RPAREN
                    {
                    dbg.location(617,28);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_task_enable4568); if (state.failed) return ;
                    dbg.location(617,35);
                    pushFollow(FOLLOW_expression_in_system_task_enable4570);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(617,46);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:46: ( COMMA ( expression )? )*
                    try { dbg.enterSubRule(99);

                    loop99:
                    do {
                        int alt99=2;
                        try { dbg.enterDecision(99, decisionCanBacktrack[99]);

                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==COMMA) ) {
                            alt99=1;
                        }


                        } finally {dbg.exitDecision(99);}

                        switch (alt99) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:47: COMMA ( expression )?
                    	    {
                    	    dbg.location(617,47);
                    	    match(input,COMMA,FOLLOW_COMMA_in_system_task_enable4573); if (state.failed) return ;
                    	    dbg.location(617,53);
                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:53: ( expression )?
                    	    int alt98=2;
                    	    try { dbg.enterSubRule(98);
                    	    try { dbg.enterDecision(98, decisionCanBacktrack[98]);

                    	    try {
                    	        isCyclicDecision = true;
                    	        alt98 = dfa98.predict(input);
                    	    }
                    	    catch (NoViableAltException nvae) {
                    	        dbg.recognitionException(nvae);
                    	        throw nvae;
                    	    }
                    	    } finally {dbg.exitDecision(98);}

                    	    switch (alt98) {
                    	        case 1 :
                    	            dbg.enterAlt(1);

                    	            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:617:54: expression
                    	            {
                    	            dbg.location(617,54);
                    	            pushFollow(FOLLOW_expression_in_system_task_enable4576);
                    	            expression();

                    	            state._fsp--;
                    	            if (state.failed) return ;

                    	            }
                    	            break;

                    	    }
                    	    } finally {dbg.exitSubRule(98);}


                    	    }
                    	    break;

                    	default :
                    	    break loop99;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(99);}

                    dbg.location(617,69);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_task_enable4582); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(100);}

            dbg.location(618,2);
            match(input,SEMI,FOLLOW_SEMI_in_system_task_enable4588); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(619, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "system_task_enable");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "system_task_enable"


    // $ANTLR start "procedural_continuous_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:621:1: procedural_continuous_assignment : ( KW_ASSIGN assignment SEMI | 'deassign' lvalue SEMI | 'force' assignment SEMI | 'release' lvalue SEMI );
    public final void procedural_continuous_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "procedural_continuous_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(621, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:621:34: ( KW_ASSIGN assignment SEMI | 'deassign' lvalue SEMI | 'force' assignment SEMI | 'release' lvalue SEMI )
            int alt101=4;
            try { dbg.enterDecision(101, decisionCanBacktrack[101]);

            switch ( input.LA(1) ) {
            case KW_ASSIGN:
                {
                alt101=1;
                }
                break;
            case 159:
                {
                alt101=2;
                }
                break;
            case 160:
                {
                alt101=3;
                }
                break;
            case 161:
                {
                alt101=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(101);}

            switch (alt101) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:622:9: KW_ASSIGN assignment SEMI
                    {
                    dbg.location(622,9);
                    match(input,KW_ASSIGN,FOLLOW_KW_ASSIGN_in_procedural_continuous_assignment4613); if (state.failed) return ;
                    dbg.location(622,19);
                    pushFollow(FOLLOW_assignment_in_procedural_continuous_assignment4615);
                    assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(622,30);
                    match(input,SEMI,FOLLOW_SEMI_in_procedural_continuous_assignment4617); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:623:9: 'deassign' lvalue SEMI
                    {
                    dbg.location(623,9);
                    match(input,159,FOLLOW_159_in_procedural_continuous_assignment4629); if (state.failed) return ;
                    dbg.location(623,20);
                    pushFollow(FOLLOW_lvalue_in_procedural_continuous_assignment4631);
                    lvalue();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(623,27);
                    match(input,SEMI,FOLLOW_SEMI_in_procedural_continuous_assignment4633); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:624:9: 'force' assignment SEMI
                    {
                    dbg.location(624,9);
                    match(input,160,FOLLOW_160_in_procedural_continuous_assignment4645); if (state.failed) return ;
                    dbg.location(624,17);
                    pushFollow(FOLLOW_assignment_in_procedural_continuous_assignment4647);
                    assignment();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(624,28);
                    match(input,SEMI,FOLLOW_SEMI_in_procedural_continuous_assignment4649); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:625:9: 'release' lvalue SEMI
                    {
                    dbg.location(625,9);
                    match(input,161,FOLLOW_161_in_procedural_continuous_assignment4661); if (state.failed) return ;
                    dbg.location(625,19);
                    pushFollow(FOLLOW_lvalue_in_procedural_continuous_assignment4663);
                    lvalue();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(625,26);
                    match(input,SEMI,FOLLOW_SEMI_in_procedural_continuous_assignment4665); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(626, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "procedural_continuous_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "procedural_continuous_assignment"


    // $ANTLR start "delay_or_event_control"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:628:1: delay_or_event_control : ( delay_control | event_control );
    public final void delay_or_event_control() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "delay_or_event_control");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(628, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:628:24: ( delay_control | event_control )
            int alt102=2;
            try { dbg.enterDecision(102, decisionCanBacktrack[102]);

            int LA102_0 = input.LA(1);

            if ( (LA102_0==POUND) ) {
                alt102=1;
            }
            else if ( (LA102_0==AT) ) {
                alt102=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(102);}

            switch (alt102) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:629:9: delay_control
                    {
                    dbg.location(629,9);
                    pushFollow(FOLLOW_delay_control_in_delay_or_event_control4690);
                    delay_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:630:9: event_control
                    {
                    dbg.location(630,9);
                    pushFollow(FOLLOW_event_control_in_delay_or_event_control4702);
                    event_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(631, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "delay_or_event_control");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "delay_or_event_control"


    // $ANTLR start "specify_block"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:637:1: specify_block : 'specify' ( specify_item )* 'endspecify' ;
    public final void specify_block() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "specify_block");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(637, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:637:15: ( 'specify' ( specify_item )* 'endspecify' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:638:9: 'specify' ( specify_item )* 'endspecify'
            {
            dbg.location(638,9);
            match(input,162,FOLLOW_162_in_specify_block4731); if (state.failed) return ;
            dbg.location(638,19);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:638:19: ( specify_item )*
            try { dbg.enterSubRule(103);

            loop103:
            do {
                int alt103=2;
                try { dbg.enterDecision(103, decisionCanBacktrack[103]);

                try {
                    isCyclicDecision = true;
                    alt103 = dfa103.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(103);}

                switch (alt103) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:638:20: specify_item
            	    {
            	    dbg.location(638,20);
            	    pushFollow(FOLLOW_specify_item_in_specify_block4734);
            	    specify_item();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);
            } finally {dbg.exitSubRule(103);}

            dbg.location(638,35);
            match(input,163,FOLLOW_163_in_specify_block4738); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(639, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "specify_block");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "specify_block"


    // $ANTLR start "specify_item"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:641:1: specify_item : ( spec_param_declaration | ( path_declaration )=> path_declaration | system_timing_check | sdpd );
    public final void specify_item() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "specify_item");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(641, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:641:14: ( spec_param_declaration | ( path_declaration )=> path_declaration | system_timing_check | sdpd )
            int alt104=4;
            try { dbg.enterDecision(104, decisionCanBacktrack[104]);

            try {
                isCyclicDecision = true;
                alt104 = dfa104.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(104);}

            switch (alt104) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:642:9: spec_param_declaration
                    {
                    dbg.location(642,9);
                    pushFollow(FOLLOW_spec_param_declaration_in_specify_item4763);
                    spec_param_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:643:9: ( path_declaration )=> path_declaration
                    {
                    dbg.location(643,31);
                    pushFollow(FOLLOW_path_declaration_in_specify_item4781);
                    path_declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:644:9: system_timing_check
                    {
                    dbg.location(644,9);
                    pushFollow(FOLLOW_system_timing_check_in_specify_item4793);
                    system_timing_check();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:645:11: sdpd
                    {
                    dbg.location(645,11);
                    pushFollow(FOLLOW_sdpd_in_specify_item4805);
                    sdpd();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(646, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "specify_item");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "specify_item"


    // $ANTLR start "spec_param_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:648:1: spec_param_declaration : 'specparam' list_of_specparam_assignments SEMI ;
    public final void spec_param_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "spec_param_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(648, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:648:24: ( 'specparam' list_of_specparam_assignments SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:649:9: 'specparam' list_of_specparam_assignments SEMI
            {
            dbg.location(649,9);
            match(input,164,FOLLOW_164_in_spec_param_declaration4830); if (state.failed) return ;
            dbg.location(649,21);
            pushFollow(FOLLOW_list_of_specparam_assignments_in_spec_param_declaration4832);
            list_of_specparam_assignments();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(649,51);
            match(input,SEMI,FOLLOW_SEMI_in_spec_param_declaration4834); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(650, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "spec_param_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "spec_param_declaration"


    // $ANTLR start "list_of_specparam_assignments"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:652:1: list_of_specparam_assignments : specparam_assignment ( COMMA specparam_assignment )* ;
    public final void list_of_specparam_assignments() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_specparam_assignments");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(652, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:652:31: ( specparam_assignment ( COMMA specparam_assignment )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:653:9: specparam_assignment ( COMMA specparam_assignment )*
            {
            dbg.location(653,9);
            pushFollow(FOLLOW_specparam_assignment_in_list_of_specparam_assignments4859);
            specparam_assignment();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(653,30);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:653:30: ( COMMA specparam_assignment )*
            try { dbg.enterSubRule(105);

            loop105:
            do {
                int alt105=2;
                try { dbg.enterDecision(105, decisionCanBacktrack[105]);

                int LA105_0 = input.LA(1);

                if ( (LA105_0==COMMA) ) {
                    alt105=1;
                }


                } finally {dbg.exitDecision(105);}

                switch (alt105) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:653:32: COMMA specparam_assignment
            	    {
            	    dbg.location(653,32);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_specparam_assignments4863); if (state.failed) return ;
            	    dbg.location(653,38);
            	    pushFollow(FOLLOW_specparam_assignment_in_list_of_specparam_assignments4865);
            	    specparam_assignment();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop105;
                }
            } while (true);
            } finally {dbg.exitSubRule(105);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(654, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_specparam_assignments");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_specparam_assignments"


    // $ANTLR start "specparam_assignment"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:656:1: specparam_assignment : identifier ASSIGN expression ;
    public final void specparam_assignment() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "specparam_assignment");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(656, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:656:22: ( identifier ASSIGN expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:657:9: identifier ASSIGN expression
            {
            dbg.location(657,9);
            pushFollow(FOLLOW_identifier_in_specparam_assignment4893);
            identifier();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(657,20);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_specparam_assignment4895); if (state.failed) return ;
            dbg.location(657,27);
            pushFollow(FOLLOW_expression_in_specparam_assignment4897);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(658, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "specparam_assignment");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "specparam_assignment"


    // $ANTLR start "path_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:660:1: path_declaration : ( ( simple_path_declaration )=> simple_path_declaration SEMI | ( level_sensitive_path_declaration )=> level_sensitive_path_declaration SEMI | edge_sensitive_path_declaration SEMI );
    public final void path_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "path_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(660, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:660:18: ( ( simple_path_declaration )=> simple_path_declaration SEMI | ( level_sensitive_path_declaration )=> level_sensitive_path_declaration SEMI | edge_sensitive_path_declaration SEMI )
            int alt106=3;
            try { dbg.enterDecision(106, decisionCanBacktrack[106]);

            try {
                isCyclicDecision = true;
                alt106 = dfa106.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(106);}

            switch (alt106) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:661:9: ( simple_path_declaration )=> simple_path_declaration SEMI
                    {
                    dbg.location(662,5);
                    pushFollow(FOLLOW_simple_path_declaration_in_path_declaration4932);
                    simple_path_declaration();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(662,29);
                    match(input,SEMI,FOLLOW_SEMI_in_path_declaration4934); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:663:9: ( level_sensitive_path_declaration )=> level_sensitive_path_declaration SEMI
                    {
                    dbg.location(664,5);
                    pushFollow(FOLLOW_level_sensitive_path_declaration_in_path_declaration4956);
                    level_sensitive_path_declaration();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(664,38);
                    match(input,SEMI,FOLLOW_SEMI_in_path_declaration4958); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:665:9: edge_sensitive_path_declaration SEMI
                    {
                    dbg.location(665,9);
                    pushFollow(FOLLOW_edge_sensitive_path_declaration_in_path_declaration4970);
                    edge_sensitive_path_declaration();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(665,41);
                    match(input,SEMI,FOLLOW_SEMI_in_path_declaration4972); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(666, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "path_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "path_declaration"


    // $ANTLR start "simple_path_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:668:1: simple_path_declaration : ( ( parallel_path_description )=> parallel_path_description ASSIGN path_delay_value | full_path_descriptor ASSIGN path_delay_value );
    public final void simple_path_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "simple_path_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(668, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:668:25: ( ( parallel_path_description )=> parallel_path_description ASSIGN path_delay_value | full_path_descriptor ASSIGN path_delay_value )
            int alt107=2;
            try { dbg.enterDecision(107, decisionCanBacktrack[107]);

            int LA107_0 = input.LA(1);

            if ( (LA107_0==LPAREN) ) {
                int LA107_1 = input.LA(2);

                if ( (LA107_1==IDENTIFIER||LA107_1==ESCAPED_IDENTIFIER) ) {
                    int LA107_2 = input.LA(3);

                    if ( (LA107_2==DOT) ) {
                        int LA107_3 = input.LA(4);

                        if ( (synpred12_Verilog()) ) {
                            alt107=1;
                        }
                        else if ( (true) ) {
                            alt107=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 107, 3, input);

                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                    }
                    else if ( (LA107_2==LBRACK) ) {
                        int LA107_4 = input.LA(4);

                        if ( (synpred12_Verilog()) ) {
                            alt107=1;
                        }
                        else if ( (true) ) {
                            alt107=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 107, 4, input);

                            dbg.recognitionException(nvae);
                            throw nvae;
                        }
                    }
                    else if ( (LA107_2==PPATH) && (synpred12_Verilog())) {
                        alt107=1;
                    }
                    else if ( (LA107_2==COMMA||LA107_2==FPATH) ) {
                        alt107=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 107, 2, input);

                        dbg.recognitionException(nvae);
                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 107, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(107);}

            switch (alt107) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:669:9: ( parallel_path_description )=> parallel_path_description ASSIGN path_delay_value
                    {
                    dbg.location(670,5);
                    pushFollow(FOLLOW_parallel_path_description_in_simple_path_declaration5007);
                    parallel_path_description();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(670,31);
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_simple_path_declaration5009); if (state.failed) return ;
                    dbg.location(670,38);
                    pushFollow(FOLLOW_path_delay_value_in_simple_path_declaration5011);
                    path_delay_value();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:671:9: full_path_descriptor ASSIGN path_delay_value
                    {
                    dbg.location(671,9);
                    pushFollow(FOLLOW_full_path_descriptor_in_simple_path_declaration5023);
                    full_path_descriptor();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(671,30);
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_simple_path_declaration5025); if (state.failed) return ;
                    dbg.location(671,37);
                    pushFollow(FOLLOW_path_delay_value_in_simple_path_declaration5027);
                    path_delay_value();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(672, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "simple_path_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "simple_path_declaration"


    // $ANTLR start "parallel_path_description"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:674:1: parallel_path_description : LPAREN specify_terminal_descriptor PPATH specify_terminal_descriptor RPAREN ;
    public final void parallel_path_description() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "parallel_path_description");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(674, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:674:27: ( LPAREN specify_terminal_descriptor PPATH specify_terminal_descriptor RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:675:9: LPAREN specify_terminal_descriptor PPATH specify_terminal_descriptor RPAREN
            {
            dbg.location(675,9);
            match(input,LPAREN,FOLLOW_LPAREN_in_parallel_path_description5052); if (state.failed) return ;
            dbg.location(675,16);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_parallel_path_description5054);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(675,44);
            match(input,PPATH,FOLLOW_PPATH_in_parallel_path_description5056); if (state.failed) return ;
            dbg.location(675,50);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_parallel_path_description5058);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(675,78);
            match(input,RPAREN,FOLLOW_RPAREN_in_parallel_path_description5060); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(676, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "parallel_path_description");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "parallel_path_description"


    // $ANTLR start "full_path_descriptor"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:678:1: full_path_descriptor : LPAREN list_of_path_terminals FPATH list_of_path_terminals RPAREN ;
    public final void full_path_descriptor() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "full_path_descriptor");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(678, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:678:22: ( LPAREN list_of_path_terminals FPATH list_of_path_terminals RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:679:9: LPAREN list_of_path_terminals FPATH list_of_path_terminals RPAREN
            {
            dbg.location(679,9);
            match(input,LPAREN,FOLLOW_LPAREN_in_full_path_descriptor5085); if (state.failed) return ;
            dbg.location(679,16);
            pushFollow(FOLLOW_list_of_path_terminals_in_full_path_descriptor5087);
            list_of_path_terminals();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(679,40);
            match(input,FPATH,FOLLOW_FPATH_in_full_path_descriptor5090); if (state.failed) return ;
            dbg.location(679,46);
            pushFollow(FOLLOW_list_of_path_terminals_in_full_path_descriptor5092);
            list_of_path_terminals();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(679,69);
            match(input,RPAREN,FOLLOW_RPAREN_in_full_path_descriptor5094); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(680, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "full_path_descriptor");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "full_path_descriptor"


    // $ANTLR start "list_of_path_terminals"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:682:1: list_of_path_terminals : specify_terminal_descriptor ( COMMA specify_terminal_descriptor )* ;
    public final void list_of_path_terminals() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_path_terminals");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(682, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:682:24: ( specify_terminal_descriptor ( COMMA specify_terminal_descriptor )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:683:9: specify_terminal_descriptor ( COMMA specify_terminal_descriptor )*
            {
            dbg.location(683,9);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_list_of_path_terminals5119);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(683,37);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:683:37: ( COMMA specify_terminal_descriptor )*
            try { dbg.enterSubRule(108);

            loop108:
            do {
                int alt108=2;
                try { dbg.enterDecision(108, decisionCanBacktrack[108]);

                int LA108_0 = input.LA(1);

                if ( (LA108_0==COMMA) ) {
                    alt108=1;
                }


                } finally {dbg.exitDecision(108);}

                switch (alt108) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:683:39: COMMA specify_terminal_descriptor
            	    {
            	    dbg.location(683,39);
            	    match(input,COMMA,FOLLOW_COMMA_in_list_of_path_terminals5123); if (state.failed) return ;
            	    dbg.location(683,45);
            	    pushFollow(FOLLOW_specify_terminal_descriptor_in_list_of_path_terminals5125);
            	    specify_terminal_descriptor();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop108;
                }
            } while (true);
            } finally {dbg.exitSubRule(108);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(684, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_path_terminals");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_path_terminals"


    // $ANTLR start "specify_terminal_descriptor"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:686:1: specify_terminal_descriptor : ( ( identifier LBRACK expression COLON )=> identifier LBRACK expression COLON expression RBRACK | ( identifier LBRACK )=> identifier LBRACK expression RBRACK | identifier );
    public final void specify_terminal_descriptor() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "specify_terminal_descriptor");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(686, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:686:29: ( ( identifier LBRACK expression COLON )=> identifier LBRACK expression COLON expression RBRACK | ( identifier LBRACK )=> identifier LBRACK expression RBRACK | identifier )
            int alt109=3;
            try { dbg.enterDecision(109, decisionCanBacktrack[109]);

            try {
                isCyclicDecision = true;
                alt109 = dfa109.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(109);}

            switch (alt109) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:687:2: ( identifier LBRACK expression COLON )=> identifier LBRACK expression COLON expression RBRACK
                    {
                    dbg.location(688,12);
                    pushFollow(FOLLOW_identifier_in_specify_terminal_descriptor5169);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(688,23);
                    match(input,LBRACK,FOLLOW_LBRACK_in_specify_terminal_descriptor5171); if (state.failed) return ;
                    dbg.location(688,30);
                    pushFollow(FOLLOW_expression_in_specify_terminal_descriptor5173);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(688,41);
                    match(input,COLON,FOLLOW_COLON_in_specify_terminal_descriptor5175); if (state.failed) return ;
                    dbg.location(688,47);
                    pushFollow(FOLLOW_expression_in_specify_terminal_descriptor5177);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(688,58);
                    match(input,RBRACK,FOLLOW_RBRACK_in_specify_terminal_descriptor5179); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:689:9: ( identifier LBRACK )=> identifier LBRACK expression RBRACK
                    {
                    dbg.location(690,12);
                    pushFollow(FOLLOW_identifier_in_specify_terminal_descriptor5210);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(690,23);
                    match(input,LBRACK,FOLLOW_LBRACK_in_specify_terminal_descriptor5212); if (state.failed) return ;
                    dbg.location(690,30);
                    pushFollow(FOLLOW_expression_in_specify_terminal_descriptor5214);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(690,41);
                    match(input,RBRACK,FOLLOW_RBRACK_in_specify_terminal_descriptor5216); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:691:9: identifier
                    {
                    dbg.location(691,9);
                    pushFollow(FOLLOW_identifier_in_specify_terminal_descriptor5228);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(692, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "specify_terminal_descriptor");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "specify_terminal_descriptor"


    // $ANTLR start "path_delay_value"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:694:1: path_delay_value : ( ( path_delay_expression )=> path_delay_expression | LPAREN list_of_path_delay_expressions RPAREN );
    public final void path_delay_value() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "path_delay_value");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(694, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:694:18: ( ( path_delay_expression )=> path_delay_expression | LPAREN list_of_path_delay_expressions RPAREN )
            int alt110=2;
            try { dbg.enterDecision(110, decisionCanBacktrack[110]);

            try {
                isCyclicDecision = true;
                alt110 = dfa110.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(110);}

            switch (alt110) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:695:9: ( path_delay_expression )=> path_delay_expression
                    {
                    dbg.location(695,36);
                    pushFollow(FOLLOW_path_delay_expression_in_path_delay_value5259);
                    path_delay_expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:696:9: LPAREN list_of_path_delay_expressions RPAREN
                    {
                    dbg.location(696,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_path_delay_value5271); if (state.failed) return ;
                    dbg.location(696,16);
                    pushFollow(FOLLOW_list_of_path_delay_expressions_in_path_delay_value5273);
                    list_of_path_delay_expressions();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(696,47);
                    match(input,RPAREN,FOLLOW_RPAREN_in_path_delay_value5275); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(697, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "path_delay_value");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "path_delay_value"


    // $ANTLR start "list_of_path_delay_expressions"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:699:1: list_of_path_delay_expressions : path_delay_expression COMMA path_delay_expression ( COMMA path_delay_expression ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )? )? ;
    public final void list_of_path_delay_expressions() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "list_of_path_delay_expressions");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(699, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:699:32: ( path_delay_expression COMMA path_delay_expression ( COMMA path_delay_expression ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )? )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:700:9: path_delay_expression COMMA path_delay_expression ( COMMA path_delay_expression ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )? )?
            {
            dbg.location(700,9);
            pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5300);
            path_delay_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(700,31);
            match(input,COMMA,FOLLOW_COMMA_in_list_of_path_delay_expressions5302); if (state.failed) return ;
            dbg.location(700,37);
            pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5304);
            path_delay_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(701,4);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:701:4: ( COMMA path_delay_expression ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )? )?
            int alt112=2;
            try { dbg.enterSubRule(112);
            try { dbg.enterDecision(112, decisionCanBacktrack[112]);

            int LA112_0 = input.LA(1);

            if ( (LA112_0==COMMA) ) {
                alt112=1;
            }
            } finally {dbg.exitDecision(112);}

            switch (alt112) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:701:6: COMMA path_delay_expression ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )?
                    {
                    dbg.location(701,6);
                    match(input,COMMA,FOLLOW_COMMA_in_list_of_path_delay_expressions5311); if (state.failed) return ;
                    dbg.location(701,12);
                    pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5313);
                    path_delay_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(702,6);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:702:6: ( COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression )?
                    int alt111=2;
                    try { dbg.enterSubRule(111);
                    try { dbg.enterDecision(111, decisionCanBacktrack[111]);

                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==COMMA) ) {
                        alt111=1;
                    }
                    } finally {dbg.exitDecision(111);}

                    switch (alt111) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:702:8: COMMA path_delay_expression COMMA path_delay_expression COMMA path_delay_expression
                            {
                            dbg.location(702,8);
                            match(input,COMMA,FOLLOW_COMMA_in_list_of_path_delay_expressions5322); if (state.failed) return ;
                            dbg.location(702,14);
                            pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5324);
                            path_delay_expression();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(702,36);
                            match(input,COMMA,FOLLOW_COMMA_in_list_of_path_delay_expressions5326); if (state.failed) return ;
                            dbg.location(703,15);
                            pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5342);
                            path_delay_expression();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(703,37);
                            match(input,COMMA,FOLLOW_COMMA_in_list_of_path_delay_expressions5344); if (state.failed) return ;
                            dbg.location(703,43);
                            pushFollow(FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5346);
                            path_delay_expression();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(111);}


                    }
                    break;

            }
            } finally {dbg.exitSubRule(112);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(704, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "list_of_path_delay_expressions");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "list_of_path_delay_expressions"


    // $ANTLR start "path_delay_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:706:1: path_delay_expression : mintypmax_expression ;
    public final void path_delay_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "path_delay_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(706, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:706:23: ( mintypmax_expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:707:9: mintypmax_expression
            {
            dbg.location(707,9);
            pushFollow(FOLLOW_mintypmax_expression_in_path_delay_expression5378);
            mintypmax_expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(708, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "path_delay_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "path_delay_expression"


    // $ANTLR start "system_timing_check"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:710:1: system_timing_check : ( '$setup' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$hold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$period' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$width' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA expression COMMA notify_register )? RPAREN SEMI | '$skew' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$recovery' LPAREN controlled_timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$setuphold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI );
    public final void system_timing_check() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "system_timing_check");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(710, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:710:21: ( '$setup' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$hold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$period' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$width' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA expression COMMA notify_register )? RPAREN SEMI | '$skew' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$recovery' LPAREN controlled_timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI | '$setuphold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI )
            int alt120=7;
            try { dbg.enterDecision(120, decisionCanBacktrack[120]);

            switch ( input.LA(1) ) {
            case 165:
                {
                alt120=1;
                }
                break;
            case 166:
                {
                alt120=2;
                }
                break;
            case 167:
                {
                alt120=3;
                }
                break;
            case 168:
                {
                alt120=4;
                }
                break;
            case 169:
                {
                alt120=5;
                }
                break;
            case 170:
                {
                alt120=6;
                }
                break;
            case 171:
                {
                alt120=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(120);}

            switch (alt120) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:711:9: '$setup' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(711,9);
                    match(input,165,FOLLOW_165_in_system_timing_check5403); if (state.failed) return ;
                    dbg.location(711,18);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5405); if (state.failed) return ;
                    dbg.location(711,25);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5407);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(711,44);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5409); if (state.failed) return ;
                    dbg.location(711,50);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5411);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(711,69);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5413); if (state.failed) return ;
                    dbg.location(712,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5427);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(712,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:712:32: ( COMMA notify_register )?
                    int alt113=2;
                    try { dbg.enterSubRule(113);
                    try { dbg.enterDecision(113, decisionCanBacktrack[113]);

                    int LA113_0 = input.LA(1);

                    if ( (LA113_0==COMMA) ) {
                        alt113=1;
                    }
                    } finally {dbg.exitDecision(113);}

                    switch (alt113) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:712:34: COMMA notify_register
                            {
                            dbg.location(712,34);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5431); if (state.failed) return ;
                            dbg.location(712,40);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5433);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(113);}

                    dbg.location(712,59);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5438); if (state.failed) return ;
                    dbg.location(712,66);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5440); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:713:9: '$hold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(713,9);
                    match(input,166,FOLLOW_166_in_system_timing_check5452); if (state.failed) return ;
                    dbg.location(713,17);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5454); if (state.failed) return ;
                    dbg.location(713,24);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5456);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(713,43);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5458); if (state.failed) return ;
                    dbg.location(713,49);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5460);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(713,68);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5462); if (state.failed) return ;
                    dbg.location(714,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5476);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(714,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:714:32: ( COMMA notify_register )?
                    int alt114=2;
                    try { dbg.enterSubRule(114);
                    try { dbg.enterDecision(114, decisionCanBacktrack[114]);

                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==COMMA) ) {
                        alt114=1;
                    }
                    } finally {dbg.exitDecision(114);}

                    switch (alt114) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:714:34: COMMA notify_register
                            {
                            dbg.location(714,34);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5480); if (state.failed) return ;
                            dbg.location(714,40);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5482);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(114);}

                    dbg.location(714,59);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5487); if (state.failed) return ;
                    dbg.location(714,66);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5489); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:715:9: '$period' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(715,9);
                    match(input,167,FOLLOW_167_in_system_timing_check5501); if (state.failed) return ;
                    dbg.location(715,19);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5503); if (state.failed) return ;
                    dbg.location(715,26);
                    pushFollow(FOLLOW_controlled_timing_check_event_in_system_timing_check5505);
                    controlled_timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(715,56);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5507); if (state.failed) return ;
                    dbg.location(716,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5521);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(716,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:716:32: ( COMMA notify_register )?
                    int alt115=2;
                    try { dbg.enterSubRule(115);
                    try { dbg.enterDecision(115, decisionCanBacktrack[115]);

                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==COMMA) ) {
                        alt115=1;
                    }
                    } finally {dbg.exitDecision(115);}

                    switch (alt115) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:716:34: COMMA notify_register
                            {
                            dbg.location(716,34);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5525); if (state.failed) return ;
                            dbg.location(716,40);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5527);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(115);}

                    dbg.location(716,59);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5532); if (state.failed) return ;
                    dbg.location(716,66);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5534); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:717:9: '$width' LPAREN controlled_timing_check_event COMMA timing_check_limit ( COMMA expression COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(717,9);
                    match(input,168,FOLLOW_168_in_system_timing_check5546); if (state.failed) return ;
                    dbg.location(717,18);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5548); if (state.failed) return ;
                    dbg.location(717,25);
                    pushFollow(FOLLOW_controlled_timing_check_event_in_system_timing_check5550);
                    controlled_timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(717,55);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5552); if (state.failed) return ;
                    dbg.location(718,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5566);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(718,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:718:32: ( COMMA expression COMMA notify_register )?
                    int alt116=2;
                    try { dbg.enterSubRule(116);
                    try { dbg.enterDecision(116, decisionCanBacktrack[116]);

                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==COMMA) ) {
                        alt116=1;
                    }
                    } finally {dbg.exitDecision(116);}

                    switch (alt116) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:718:34: COMMA expression COMMA notify_register
                            {
                            dbg.location(718,34);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5570); if (state.failed) return ;
                            dbg.location(718,40);
                            pushFollow(FOLLOW_expression_in_system_timing_check5572);
                            expression();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(718,51);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5574); if (state.failed) return ;
                            dbg.location(718,57);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5576);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(116);}

                    dbg.location(719,6);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5586); if (state.failed) return ;
                    dbg.location(719,13);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5588); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:720:9: '$skew' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(720,9);
                    match(input,169,FOLLOW_169_in_system_timing_check5600); if (state.failed) return ;
                    dbg.location(720,17);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5602); if (state.failed) return ;
                    dbg.location(720,24);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5604);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(720,43);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5606); if (state.failed) return ;
                    dbg.location(720,49);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5608);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(720,68);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5610); if (state.failed) return ;
                    dbg.location(721,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5625);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(721,32);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:721:32: ( COMMA notify_register )?
                    int alt117=2;
                    try { dbg.enterSubRule(117);
                    try { dbg.enterDecision(117, decisionCanBacktrack[117]);

                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==COMMA) ) {
                        alt117=1;
                    }
                    } finally {dbg.exitDecision(117);}

                    switch (alt117) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:721:34: COMMA notify_register
                            {
                            dbg.location(721,34);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5629); if (state.failed) return ;
                            dbg.location(721,40);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5631);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(117);}

                    dbg.location(721,59);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5636); if (state.failed) return ;
                    dbg.location(721,66);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5638); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    dbg.enterAlt(6);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:722:9: '$recovery' LPAREN controlled_timing_check_event COMMA timing_check_event COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(722,9);
                    match(input,170,FOLLOW_170_in_system_timing_check5650); if (state.failed) return ;
                    dbg.location(722,21);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5652); if (state.failed) return ;
                    dbg.location(722,28);
                    pushFollow(FOLLOW_controlled_timing_check_event_in_system_timing_check5654);
                    controlled_timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(722,58);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5656); if (state.failed) return ;
                    dbg.location(723,13);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5670);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(723,32);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5672); if (state.failed) return ;
                    dbg.location(723,38);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5674);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(724,13);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:724:13: ( COMMA notify_register )?
                    int alt118=2;
                    try { dbg.enterSubRule(118);
                    try { dbg.enterDecision(118, decisionCanBacktrack[118]);

                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==COMMA) ) {
                        alt118=1;
                    }
                    } finally {dbg.exitDecision(118);}

                    switch (alt118) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:724:15: COMMA notify_register
                            {
                            dbg.location(724,15);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5690); if (state.failed) return ;
                            dbg.location(724,21);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5692);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(118);}

                    dbg.location(724,40);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5697); if (state.failed) return ;
                    dbg.location(724,47);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5699); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    dbg.enterAlt(7);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:725:9: '$setuphold' LPAREN timing_check_event COMMA timing_check_event COMMA timing_check_limit COMMA timing_check_limit ( COMMA notify_register )? RPAREN SEMI
                    {
                    dbg.location(725,9);
                    match(input,171,FOLLOW_171_in_system_timing_check5711); if (state.failed) return ;
                    dbg.location(725,22);
                    match(input,LPAREN,FOLLOW_LPAREN_in_system_timing_check5713); if (state.failed) return ;
                    dbg.location(725,29);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5715);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(725,48);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5717); if (state.failed) return ;
                    dbg.location(725,54);
                    pushFollow(FOLLOW_timing_check_event_in_system_timing_check5719);
                    timing_check_event();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(725,73);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5721); if (state.failed) return ;
                    dbg.location(726,13);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5735);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(726,32);
                    match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5737); if (state.failed) return ;
                    dbg.location(726,38);
                    pushFollow(FOLLOW_timing_check_limit_in_system_timing_check5739);
                    timing_check_limit();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(727,13);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:727:13: ( COMMA notify_register )?
                    int alt119=2;
                    try { dbg.enterSubRule(119);
                    try { dbg.enterDecision(119, decisionCanBacktrack[119]);

                    int LA119_0 = input.LA(1);

                    if ( (LA119_0==COMMA) ) {
                        alt119=1;
                    }
                    } finally {dbg.exitDecision(119);}

                    switch (alt119) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:727:15: COMMA notify_register
                            {
                            dbg.location(727,15);
                            match(input,COMMA,FOLLOW_COMMA_in_system_timing_check5755); if (state.failed) return ;
                            dbg.location(727,21);
                            pushFollow(FOLLOW_notify_register_in_system_timing_check5757);
                            notify_register();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(119);}

                    dbg.location(727,40);
                    match(input,RPAREN,FOLLOW_RPAREN_in_system_timing_check5762); if (state.failed) return ;
                    dbg.location(727,47);
                    match(input,SEMI,FOLLOW_SEMI_in_system_timing_check5764); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(728, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "system_timing_check");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "system_timing_check"


    // $ANTLR start "timing_check_event"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:730:1: timing_check_event : ( timing_check_event_control )? specify_terminal_descriptor ( '&&&' timing_check_condition )? ;
    public final void timing_check_event() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "timing_check_event");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(730, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:730:20: ( ( timing_check_event_control )? specify_terminal_descriptor ( '&&&' timing_check_condition )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:731:9: ( timing_check_event_control )? specify_terminal_descriptor ( '&&&' timing_check_condition )?
            {
            dbg.location(731,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:731:9: ( timing_check_event_control )?
            int alt121=2;
            try { dbg.enterSubRule(121);
            try { dbg.enterDecision(121, decisionCanBacktrack[121]);

            int LA121_0 = input.LA(1);

            if ( ((LA121_0>=173 && LA121_0<=175)) ) {
                alt121=1;
            }
            } finally {dbg.exitDecision(121);}

            switch (alt121) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:731:10: timing_check_event_control
                    {
                    dbg.location(731,10);
                    pushFollow(FOLLOW_timing_check_event_control_in_timing_check_event5790);
                    timing_check_event_control();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(121);}

            dbg.location(731,39);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_timing_check_event5794);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(732,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:732:9: ( '&&&' timing_check_condition )?
            int alt122=2;
            try { dbg.enterSubRule(122);
            try { dbg.enterDecision(122, decisionCanBacktrack[122]);

            int LA122_0 = input.LA(1);

            if ( (LA122_0==172) ) {
                alt122=1;
            }
            } finally {dbg.exitDecision(122);}

            switch (alt122) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:732:11: '&&&' timing_check_condition
                    {
                    dbg.location(732,11);
                    match(input,172,FOLLOW_172_in_timing_check_event5806); if (state.failed) return ;
                    dbg.location(732,17);
                    pushFollow(FOLLOW_timing_check_condition_in_timing_check_event5808);
                    timing_check_condition();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(122);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(733, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "timing_check_event");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "timing_check_event"


    // $ANTLR start "controlled_timing_check_event"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:735:1: controlled_timing_check_event : timing_check_event_control specify_terminal_descriptor ( '&&&' timing_check_condition )? ;
    public final void controlled_timing_check_event() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "controlled_timing_check_event");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(735, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:735:31: ( timing_check_event_control specify_terminal_descriptor ( '&&&' timing_check_condition )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:736:9: timing_check_event_control specify_terminal_descriptor ( '&&&' timing_check_condition )?
            {
            dbg.location(736,9);
            pushFollow(FOLLOW_timing_check_event_control_in_controlled_timing_check_event5836);
            timing_check_event_control();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(736,36);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_controlled_timing_check_event5838);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(737,9);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:737:9: ( '&&&' timing_check_condition )?
            int alt123=2;
            try { dbg.enterSubRule(123);
            try { dbg.enterDecision(123, decisionCanBacktrack[123]);

            int LA123_0 = input.LA(1);

            if ( (LA123_0==172) ) {
                alt123=1;
            }
            } finally {dbg.exitDecision(123);}

            switch (alt123) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:737:11: '&&&' timing_check_condition
                    {
                    dbg.location(737,11);
                    match(input,172,FOLLOW_172_in_controlled_timing_check_event5850); if (state.failed) return ;
                    dbg.location(737,17);
                    pushFollow(FOLLOW_timing_check_condition_in_controlled_timing_check_event5852);
                    timing_check_condition();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(123);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(738, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "controlled_timing_check_event");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "controlled_timing_check_event"


    // $ANTLR start "timing_check_event_control"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:740:1: timing_check_event_control : ( 'posedge' | 'negedge' | edge_control_specifier );
    public final void timing_check_event_control() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "timing_check_event_control");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(740, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:740:28: ( 'posedge' | 'negedge' | edge_control_specifier )
            int alt124=3;
            try { dbg.enterDecision(124, decisionCanBacktrack[124]);

            switch ( input.LA(1) ) {
            case 173:
                {
                alt124=1;
                }
                break;
            case 174:
                {
                alt124=2;
                }
                break;
            case 175:
                {
                alt124=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(124);}

            switch (alt124) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:741:9: 'posedge'
                    {
                    dbg.location(741,9);
                    match(input,173,FOLLOW_173_in_timing_check_event_control5880); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:742:9: 'negedge'
                    {
                    dbg.location(742,9);
                    match(input,174,FOLLOW_174_in_timing_check_event_control5892); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:743:9: edge_control_specifier
                    {
                    dbg.location(743,9);
                    pushFollow(FOLLOW_edge_control_specifier_in_timing_check_event_control5904);
                    edge_control_specifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(744, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "timing_check_event_control");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "timing_check_event_control"


    // $ANTLR start "edge_control_specifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:746:1: edge_control_specifier : 'edge' LBRACK edge_descriptor ( COMMA edge_descriptor )* RBRACK ;
    public final void edge_control_specifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "edge_control_specifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(746, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:746:24: ( 'edge' LBRACK edge_descriptor ( COMMA edge_descriptor )* RBRACK )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:747:9: 'edge' LBRACK edge_descriptor ( COMMA edge_descriptor )* RBRACK
            {
            dbg.location(747,9);
            match(input,175,FOLLOW_175_in_edge_control_specifier5929); if (state.failed) return ;
            dbg.location(747,16);
            match(input,LBRACK,FOLLOW_LBRACK_in_edge_control_specifier5931); if (state.failed) return ;
            dbg.location(747,23);
            pushFollow(FOLLOW_edge_descriptor_in_edge_control_specifier5933);
            edge_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(747,39);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:747:39: ( COMMA edge_descriptor )*
            try { dbg.enterSubRule(125);

            loop125:
            do {
                int alt125=2;
                try { dbg.enterDecision(125, decisionCanBacktrack[125]);

                int LA125_0 = input.LA(1);

                if ( (LA125_0==COMMA) ) {
                    alt125=1;
                }


                } finally {dbg.exitDecision(125);}

                switch (alt125) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:747:41: COMMA edge_descriptor
            	    {
            	    dbg.location(747,41);
            	    match(input,COMMA,FOLLOW_COMMA_in_edge_control_specifier5937); if (state.failed) return ;
            	    dbg.location(747,47);
            	    pushFollow(FOLLOW_edge_descriptor_in_edge_control_specifier5939);
            	    edge_descriptor();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop125;
                }
            } while (true);
            } finally {dbg.exitSubRule(125);}

            dbg.location(747,66);
            match(input,RBRACK,FOLLOW_RBRACK_in_edge_control_specifier5944); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(748, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "edge_control_specifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "edge_control_specifier"


    // $ANTLR start "edge_descriptor"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:754:1: edge_descriptor : ( '0x' | '1x' | n= NUMBER {...}? | i= IDENTIFIER {...}?);
    public final void edge_descriptor() throws RecognitionException {
        Token n=null;
        Token i=null;

        try { dbg.enterRule(getGrammarFileName(), "edge_descriptor");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(754, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:754:17: ( '0x' | '1x' | n= NUMBER {...}? | i= IDENTIFIER {...}?)
            int alt126=4;
            try { dbg.enterDecision(126, decisionCanBacktrack[126]);

            switch ( input.LA(1) ) {
            case 176:
                {
                alt126=1;
                }
                break;
            case 177:
                {
                alt126=2;
                }
                break;
            case NUMBER:
                {
                alt126=3;
                }
                break;
            case IDENTIFIER:
                {
                alt126=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(126);}

            switch (alt126) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:755:2: '0x'
                    {
                    dbg.location(755,2);
                    match(input,176,FOLLOW_176_in_edge_descriptor5978); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:755:9: '1x'
                    {
                    dbg.location(755,9);
                    match(input,177,FOLLOW_177_in_edge_descriptor5982); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:757:2: n= NUMBER {...}?
                    {
                    dbg.location(757,3);
                    n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_edge_descriptor5995); if (state.failed) return ;
                    dbg.location(758,2);
                    if ( !(evalPredicate( (n!=null?n.getText():null)=="01" || (n!=null?n.getText():null)=="10"," $n.text==\"01\" || $n.text==\"10\"")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "edge_descriptor", " $n.text==\"01\" || $n.text==\"10\"");
                    }

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:760:2: i= IDENTIFIER {...}?
                    {
                    dbg.location(760,3);
                    i=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_edge_descriptor6011); if (state.failed) return ;
                    dbg.location(761,2);
                    if ( !(evalPredicate( (i!=null?i.getText():null)=="x1" || (i!=null?i.getText():null)=="x0"," $i.text==\"x1\" || $i.text==\"x0\"")) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "edge_descriptor", " $i.text==\"x1\" || $i.text==\"x0\"");
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(762, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "edge_descriptor");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "edge_descriptor"


    // $ANTLR start "timing_check_condition"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:764:1: timing_check_condition : scalar_timing_check_condition ;
    public final void timing_check_condition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "timing_check_condition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(764, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:764:24: ( scalar_timing_check_condition )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:765:9: scalar_timing_check_condition
            {
            dbg.location(765,9);
            pushFollow(FOLLOW_scalar_timing_check_condition_in_timing_check_condition6032);
            scalar_timing_check_condition();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(766, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "timing_check_condition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "timing_check_condition"


    // $ANTLR start "scalar_timing_check_condition"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:767:1: scalar_timing_check_condition : expression ;
    public final void scalar_timing_check_condition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "scalar_timing_check_condition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(767, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:767:31: ( expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:768:9: expression
            {
            dbg.location(768,9);
            pushFollow(FOLLOW_expression_in_scalar_timing_check_condition6057);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(769, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "scalar_timing_check_condition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "scalar_timing_check_condition"


    // $ANTLR start "timing_check_limit"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:771:1: timing_check_limit : expression ;
    public final void timing_check_limit() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "timing_check_limit");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(771, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:771:20: ( expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:772:9: expression
            {
            dbg.location(772,9);
            pushFollow(FOLLOW_expression_in_timing_check_limit6082);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(773, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "timing_check_limit");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "timing_check_limit"


    // $ANTLR start "notify_register"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:775:1: notify_register : name_of_register ;
    public final void notify_register() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "notify_register");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(775, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:775:17: ( name_of_register )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:776:9: name_of_register
            {
            dbg.location(776,9);
            pushFollow(FOLLOW_name_of_register_in_notify_register6107);
            name_of_register();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(777, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "notify_register");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "notify_register"


    // $ANTLR start "level_sensitive_path_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:779:1: level_sensitive_path_declaration : ( ( parallel_level_sensitive_path_description )=> parallel_level_sensitive_path_description ASSIGN path_delay_value SEMI | full_level_sensitive_path_description ASSIGN path_delay_value SEMI );
    public final void level_sensitive_path_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "level_sensitive_path_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(779, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:779:34: ( ( parallel_level_sensitive_path_description )=> parallel_level_sensitive_path_description ASSIGN path_delay_value SEMI | full_level_sensitive_path_description ASSIGN path_delay_value SEMI )
            int alt127=2;
            try { dbg.enterDecision(127, decisionCanBacktrack[127]);

            try {
                isCyclicDecision = true;
                alt127 = dfa127.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(127);}

            switch (alt127) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:780:2: ( parallel_level_sensitive_path_description )=> parallel_level_sensitive_path_description ASSIGN path_delay_value SEMI
                    {
                    dbg.location(781,2);
                    pushFollow(FOLLOW_parallel_level_sensitive_path_description_in_level_sensitive_path_declaration6132);
                    parallel_level_sensitive_path_description();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(782,7);
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_level_sensitive_path_declaration6140); if (state.failed) return ;
                    dbg.location(782,14);
                    pushFollow(FOLLOW_path_delay_value_in_level_sensitive_path_declaration6142);
                    path_delay_value();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(782,31);
                    match(input,SEMI,FOLLOW_SEMI_in_level_sensitive_path_declaration6144); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:784:2: full_level_sensitive_path_description ASSIGN path_delay_value SEMI
                    {
                    dbg.location(784,2);
                    pushFollow(FOLLOW_full_level_sensitive_path_description_in_level_sensitive_path_declaration6155);
                    full_level_sensitive_path_description();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(785,7);
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_level_sensitive_path_declaration6163); if (state.failed) return ;
                    dbg.location(785,14);
                    pushFollow(FOLLOW_path_delay_value_in_level_sensitive_path_declaration6165);
                    path_delay_value();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(785,31);
                    match(input,SEMI,FOLLOW_SEMI_in_level_sensitive_path_declaration6167); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(786, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "level_sensitive_path_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "level_sensitive_path_declaration"


    // $ANTLR start "parallel_level_sensitive_path_description"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:788:1: parallel_level_sensitive_path_description : 'if' LPAREN expression RPAREN LPAREN specify_terminal_descriptor ( polarity_operator )? PPATH specify_terminal_descriptor RPAREN ;
    public final void parallel_level_sensitive_path_description() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "parallel_level_sensitive_path_description");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(788, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:788:43: ( 'if' LPAREN expression RPAREN LPAREN specify_terminal_descriptor ( polarity_operator )? PPATH specify_terminal_descriptor RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:789:9: 'if' LPAREN expression RPAREN LPAREN specify_terminal_descriptor ( polarity_operator )? PPATH specify_terminal_descriptor RPAREN
            {
            dbg.location(789,9);
            match(input,142,FOLLOW_142_in_parallel_level_sensitive_path_description6192); if (state.failed) return ;
            dbg.location(789,14);
            match(input,LPAREN,FOLLOW_LPAREN_in_parallel_level_sensitive_path_description6194); if (state.failed) return ;
            dbg.location(789,21);
            pushFollow(FOLLOW_expression_in_parallel_level_sensitive_path_description6196);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(789,32);
            match(input,RPAREN,FOLLOW_RPAREN_in_parallel_level_sensitive_path_description6198); if (state.failed) return ;
            dbg.location(790,5);
            match(input,LPAREN,FOLLOW_LPAREN_in_parallel_level_sensitive_path_description6204); if (state.failed) return ;
            dbg.location(790,12);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_parallel_level_sensitive_path_description6206);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(790,40);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:790:40: ( polarity_operator )?
            int alt128=2;
            try { dbg.enterSubRule(128);
            try { dbg.enterDecision(128, decisionCanBacktrack[128]);

            int LA128_0 = input.LA(1);

            if ( ((LA128_0>=PLUS && LA128_0<=MINUS)) ) {
                alt128=1;
            }
            } finally {dbg.exitDecision(128);}

            switch (alt128) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:790:41: polarity_operator
                    {
                    dbg.location(790,41);
                    pushFollow(FOLLOW_polarity_operator_in_parallel_level_sensitive_path_description6209);
                    polarity_operator();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(128);}

            dbg.location(791,12);
            match(input,PPATH,FOLLOW_PPATH_in_parallel_level_sensitive_path_description6224); if (state.failed) return ;
            dbg.location(791,18);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_parallel_level_sensitive_path_description6226);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(791,46);
            match(input,RPAREN,FOLLOW_RPAREN_in_parallel_level_sensitive_path_description6228); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(792, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "parallel_level_sensitive_path_description");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "parallel_level_sensitive_path_description"


    // $ANTLR start "full_level_sensitive_path_description"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:794:1: full_level_sensitive_path_description : 'if' LPAREN expression RPAREN LPAREN list_of_path_terminals ( polarity_operator )? FPATH list_of_path_terminals RPAREN ;
    public final void full_level_sensitive_path_description() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "full_level_sensitive_path_description");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(794, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:794:39: ( 'if' LPAREN expression RPAREN LPAREN list_of_path_terminals ( polarity_operator )? FPATH list_of_path_terminals RPAREN )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:795:9: 'if' LPAREN expression RPAREN LPAREN list_of_path_terminals ( polarity_operator )? FPATH list_of_path_terminals RPAREN
            {
            dbg.location(795,9);
            match(input,142,FOLLOW_142_in_full_level_sensitive_path_description6246); if (state.failed) return ;
            dbg.location(795,14);
            match(input,LPAREN,FOLLOW_LPAREN_in_full_level_sensitive_path_description6248); if (state.failed) return ;
            dbg.location(795,21);
            pushFollow(FOLLOW_expression_in_full_level_sensitive_path_description6250);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(795,32);
            match(input,RPAREN,FOLLOW_RPAREN_in_full_level_sensitive_path_description6252); if (state.failed) return ;
            dbg.location(796,5);
            match(input,LPAREN,FOLLOW_LPAREN_in_full_level_sensitive_path_description6258); if (state.failed) return ;
            dbg.location(796,12);
            pushFollow(FOLLOW_list_of_path_terminals_in_full_level_sensitive_path_description6260);
            list_of_path_terminals();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(796,35);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:796:35: ( polarity_operator )?
            int alt129=2;
            try { dbg.enterSubRule(129);
            try { dbg.enterDecision(129, decisionCanBacktrack[129]);

            int LA129_0 = input.LA(1);

            if ( ((LA129_0>=PLUS && LA129_0<=MINUS)) ) {
                alt129=1;
            }
            } finally {dbg.exitDecision(129);}

            switch (alt129) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:796:36: polarity_operator
                    {
                    dbg.location(796,36);
                    pushFollow(FOLLOW_polarity_operator_in_full_level_sensitive_path_description6263);
                    polarity_operator();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(129);}

            dbg.location(797,12);
            match(input,FPATH,FOLLOW_FPATH_in_full_level_sensitive_path_description6278); if (state.failed) return ;
            dbg.location(797,18);
            pushFollow(FOLLOW_list_of_path_terminals_in_full_level_sensitive_path_description6280);
            list_of_path_terminals();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(797,41);
            match(input,RPAREN,FOLLOW_RPAREN_in_full_level_sensitive_path_description6282); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(798, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "full_level_sensitive_path_description");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "full_level_sensitive_path_description"


    // $ANTLR start "polarity_operator"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:800:1: polarity_operator : ( PLUS | MINUS );
    public final void polarity_operator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "polarity_operator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(800, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:800:19: ( PLUS | MINUS )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(800,19);
            if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(803, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "polarity_operator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "polarity_operator"


    // $ANTLR start "edge_sensitive_path_declaration"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:805:1: edge_sensitive_path_declaration : ( 'if' LPAREN expression RPAREN )? LPAREN ( edge_identifier )? specify_terminal_descriptor ( PPATH | FPATH ) LPAREN ( ( list_of_path_terminals )=> list_of_path_terminals | specify_terminal_descriptor ) ( polarity_operator )? COLON data_source_expression RPAREN RPAREN ASSIGN path_delay_value SEMI ;
    public final void edge_sensitive_path_declaration() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "edge_sensitive_path_declaration");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(805, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:805:33: ( ( 'if' LPAREN expression RPAREN )? LPAREN ( edge_identifier )? specify_terminal_descriptor ( PPATH | FPATH ) LPAREN ( ( list_of_path_terminals )=> list_of_path_terminals | specify_terminal_descriptor ) ( polarity_operator )? COLON data_source_expression RPAREN RPAREN ASSIGN path_delay_value SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:806:2: ( 'if' LPAREN expression RPAREN )? LPAREN ( edge_identifier )? specify_terminal_descriptor ( PPATH | FPATH ) LPAREN ( ( list_of_path_terminals )=> list_of_path_terminals | specify_terminal_descriptor ) ( polarity_operator )? COLON data_source_expression RPAREN RPAREN ASSIGN path_delay_value SEMI
            {
            dbg.location(806,2);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:806:2: ( 'if' LPAREN expression RPAREN )?
            int alt130=2;
            try { dbg.enterSubRule(130);
            try { dbg.enterDecision(130, decisionCanBacktrack[130]);

            int LA130_0 = input.LA(1);

            if ( (LA130_0==142) ) {
                alt130=1;
            }
            } finally {dbg.exitDecision(130);}

            switch (alt130) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:806:4: 'if' LPAREN expression RPAREN
                    {
                    dbg.location(806,4);
                    match(input,142,FOLLOW_142_in_edge_sensitive_path_declaration6311); if (state.failed) return ;
                    dbg.location(806,9);
                    match(input,LPAREN,FOLLOW_LPAREN_in_edge_sensitive_path_declaration6313); if (state.failed) return ;
                    dbg.location(806,16);
                    pushFollow(FOLLOW_expression_in_edge_sensitive_path_declaration6315);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(806,27);
                    match(input,RPAREN,FOLLOW_RPAREN_in_edge_sensitive_path_declaration6317); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(130);}

            dbg.location(807,9);
            match(input,LPAREN,FOLLOW_LPAREN_in_edge_sensitive_path_declaration6330); if (state.failed) return ;
            dbg.location(807,16);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:807:16: ( edge_identifier )?
            int alt131=2;
            try { dbg.enterSubRule(131);
            try { dbg.enterDecision(131, decisionCanBacktrack[131]);

            int LA131_0 = input.LA(1);

            if ( ((LA131_0>=173 && LA131_0<=174)) ) {
                alt131=1;
            }
            } finally {dbg.exitDecision(131);}

            switch (alt131) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:807:17: edge_identifier
                    {
                    dbg.location(807,17);
                    pushFollow(FOLLOW_edge_identifier_in_edge_sensitive_path_declaration6333);
                    edge_identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(131);}

            dbg.location(807,35);
            pushFollow(FOLLOW_specify_terminal_descriptor_in_edge_sensitive_path_declaration6337);
            specify_terminal_descriptor();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(808,5);
            if ( (input.LA(1)>=PPATH && input.LA(1)<=FPATH) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }

            dbg.location(809,5);
            match(input,LPAREN,FOLLOW_LPAREN_in_edge_sensitive_path_declaration6357); if (state.failed) return ;
            dbg.location(809,12);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:809:12: ( ( list_of_path_terminals )=> list_of_path_terminals | specify_terminal_descriptor )
            int alt132=2;
            try { dbg.enterSubRule(132);
            try { dbg.enterDecision(132, decisionCanBacktrack[132]);

            int LA132_0 = input.LA(1);

            if ( (LA132_0==IDENTIFIER||LA132_0==ESCAPED_IDENTIFIER) ) {
                int LA132_1 = input.LA(2);

                if ( (synpred17_Verilog()) ) {
                    alt132=1;
                }
                else if ( (true) ) {
                    alt132=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 132, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(132);}

            switch (alt132) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:809:14: ( list_of_path_terminals )=> list_of_path_terminals
                    {
                    dbg.location(809,42);
                    pushFollow(FOLLOW_list_of_path_terminals_in_edge_sensitive_path_declaration6367);
                    list_of_path_terminals();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:810:14: specify_terminal_descriptor
                    {
                    dbg.location(810,14);
                    pushFollow(FOLLOW_specify_terminal_descriptor_in_edge_sensitive_path_declaration6384);
                    specify_terminal_descriptor();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(132);}

            dbg.location(811,8);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:811:8: ( polarity_operator )?
            int alt133=2;
            try { dbg.enterSubRule(133);
            try { dbg.enterDecision(133, decisionCanBacktrack[133]);

            int LA133_0 = input.LA(1);

            if ( ((LA133_0>=PLUS && LA133_0<=MINUS)) ) {
                alt133=1;
            }
            } finally {dbg.exitDecision(133);}

            switch (alt133) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:811:9: polarity_operator
                    {
                    dbg.location(811,9);
                    pushFollow(FOLLOW_polarity_operator_in_edge_sensitive_path_declaration6396);
                    polarity_operator();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(133);}

            dbg.location(811,29);
            match(input,COLON,FOLLOW_COLON_in_edge_sensitive_path_declaration6400); if (state.failed) return ;
            dbg.location(811,35);
            pushFollow(FOLLOW_data_source_expression_in_edge_sensitive_path_declaration6402);
            data_source_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(812,5);
            match(input,RPAREN,FOLLOW_RPAREN_in_edge_sensitive_path_declaration6408); if (state.failed) return ;
            dbg.location(813,2);
            match(input,RPAREN,FOLLOW_RPAREN_in_edge_sensitive_path_declaration6411); if (state.failed) return ;
            dbg.location(814,2);
            match(input,ASSIGN,FOLLOW_ASSIGN_in_edge_sensitive_path_declaration6414); if (state.failed) return ;
            dbg.location(814,9);
            pushFollow(FOLLOW_path_delay_value_in_edge_sensitive_path_declaration6416);
            path_delay_value();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(814,26);
            match(input,SEMI,FOLLOW_SEMI_in_edge_sensitive_path_declaration6418); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(815, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "edge_sensitive_path_declaration");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "edge_sensitive_path_declaration"


    // $ANTLR start "data_source_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:817:1: data_source_expression : expression ;
    public final void data_source_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "data_source_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(817, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:817:24: ( expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:818:9: expression
            {
            dbg.location(818,9);
            pushFollow(FOLLOW_expression_in_data_source_expression6443);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(819, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "data_source_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "data_source_expression"


    // $ANTLR start "edge_identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:821:1: edge_identifier : ( 'posedge' | 'negedge' );
    public final void edge_identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "edge_identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(821, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:821:17: ( 'posedge' | 'negedge' )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(821,17);
            if ( (input.LA(1)>=173 && input.LA(1)<=174) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(824, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "edge_identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "edge_identifier"


    // $ANTLR start "sdpd"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:826:1: sdpd : 'if' LPAREN expression RPAREN simple_path_declaration SEMI ;
    public final void sdpd() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sdpd");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(826, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:826:6: ( 'if' LPAREN expression RPAREN simple_path_declaration SEMI )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:827:2: 'if' LPAREN expression RPAREN simple_path_declaration SEMI
            {
            dbg.location(827,2);
            match(input,142,FOLLOW_142_in_sdpd6498); if (state.failed) return ;
            dbg.location(827,7);
            match(input,LPAREN,FOLLOW_LPAREN_in_sdpd6500); if (state.failed) return ;
            dbg.location(827,14);
            pushFollow(FOLLOW_expression_in_sdpd6502);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(827,25);
            match(input,RPAREN,FOLLOW_RPAREN_in_sdpd6504); if (state.failed) return ;
            dbg.location(828,2);
            pushFollow(FOLLOW_simple_path_declaration_in_sdpd6507);
            simple_path_declaration();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(829,2);
            match(input,SEMI,FOLLOW_SEMI_in_sdpd6510); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(830, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sdpd");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sdpd"


    // $ANTLR start "lvalue"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:836:1: lvalue : ( ( identifier range )=> identifier range | identifier | concatenation );
    public final void lvalue() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "lvalue");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(836, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:836:8: ( ( identifier range )=> identifier range | identifier | concatenation )
            int alt134=3;
            try { dbg.enterDecision(134, decisionCanBacktrack[134]);

            try {
                isCyclicDecision = true;
                alt134 = dfa134.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(134);}

            switch (alt134) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:837:2: ( identifier range )=> identifier range
                    {
                    dbg.location(838,9);
                    pushFollow(FOLLOW_identifier_in_lvalue6541);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(838,20);
                    pushFollow(FOLLOW_range_in_lvalue6543);
                    range();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:839:9: identifier
                    {
                    dbg.location(839,9);
                    pushFollow(FOLLOW_identifier_in_lvalue6555);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:840:9: concatenation
                    {
                    dbg.location(840,9);
                    pushFollow(FOLLOW_concatenation_in_lvalue6567);
                    concatenation();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(841, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "lvalue");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "lvalue"


    // $ANTLR start "concatenation"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:843:1: concatenation : ( ( LCURLY expression LCURLY )=> LCURLY expression LCURLY expression ( COMMA expression )* RCURLY RCURLY | LCURLY expression ( COMMA expression )* RCURLY );
    public final void concatenation() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "concatenation");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(843, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:843:15: ( ( LCURLY expression LCURLY )=> LCURLY expression LCURLY expression ( COMMA expression )* RCURLY RCURLY | LCURLY expression ( COMMA expression )* RCURLY )
            int alt137=2;
            try { dbg.enterDecision(137, decisionCanBacktrack[137]);

            try {
                isCyclicDecision = true;
                alt137 = dfa137.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(137);}

            switch (alt137) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:844:2: ( LCURLY expression LCURLY )=> LCURLY expression LCURLY expression ( COMMA expression )* RCURLY RCURLY
                    {
                    dbg.location(845,9);
                    match(input,LCURLY,FOLLOW_LCURLY_in_concatenation6603); if (state.failed) return ;
                    dbg.location(845,16);
                    pushFollow(FOLLOW_expression_in_concatenation6605);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(846,9);
                    match(input,LCURLY,FOLLOW_LCURLY_in_concatenation6615); if (state.failed) return ;
                    dbg.location(846,16);
                    pushFollow(FOLLOW_expression_in_concatenation6617);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(846,27);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:846:27: ( COMMA expression )*
                    try { dbg.enterSubRule(135);

                    loop135:
                    do {
                        int alt135=2;
                        try { dbg.enterDecision(135, decisionCanBacktrack[135]);

                        int LA135_0 = input.LA(1);

                        if ( (LA135_0==COMMA) ) {
                            alt135=1;
                        }


                        } finally {dbg.exitDecision(135);}

                        switch (alt135) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:846:29: COMMA expression
                    	    {
                    	    dbg.location(846,29);
                    	    match(input,COMMA,FOLLOW_COMMA_in_concatenation6621); if (state.failed) return ;
                    	    dbg.location(846,35);
                    	    pushFollow(FOLLOW_expression_in_concatenation6623);
                    	    expression();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop135;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(135);}

                    dbg.location(846,49);
                    match(input,RCURLY,FOLLOW_RCURLY_in_concatenation6628); if (state.failed) return ;
                    dbg.location(846,56);
                    match(input,RCURLY,FOLLOW_RCURLY_in_concatenation6630); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:847:9: LCURLY expression ( COMMA expression )* RCURLY
                    {
                    dbg.location(847,9);
                    match(input,LCURLY,FOLLOW_LCURLY_in_concatenation6642); if (state.failed) return ;
                    dbg.location(847,16);
                    pushFollow(FOLLOW_expression_in_concatenation6644);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(847,27);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:847:27: ( COMMA expression )*
                    try { dbg.enterSubRule(136);

                    loop136:
                    do {
                        int alt136=2;
                        try { dbg.enterDecision(136, decisionCanBacktrack[136]);

                        int LA136_0 = input.LA(1);

                        if ( (LA136_0==COMMA) ) {
                            alt136=1;
                        }


                        } finally {dbg.exitDecision(136);}

                        switch (alt136) {
                    	case 1 :
                    	    dbg.enterAlt(1);

                    	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:847:29: COMMA expression
                    	    {
                    	    dbg.location(847,29);
                    	    match(input,COMMA,FOLLOW_COMMA_in_concatenation6648); if (state.failed) return ;
                    	    dbg.location(847,35);
                    	    pushFollow(FOLLOW_expression_in_concatenation6650);
                    	    expression();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop136;
                        }
                    } while (true);
                    } finally {dbg.exitSubRule(136);}

                    dbg.location(847,49);
                    match(input,RCURLY,FOLLOW_RCURLY_in_concatenation6655); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(848, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "concatenation");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "concatenation"


    // $ANTLR start "mintypmax_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:850:1: mintypmax_expression : expression ( COLON expression COLON expression )? ;
    public final void mintypmax_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "mintypmax_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(850, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:850:22: ( expression ( COLON expression COLON expression )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:851:9: expression ( COLON expression COLON expression )?
            {
            dbg.location(851,9);
            pushFollow(FOLLOW_expression_in_mintypmax_expression6680);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(851,20);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:851:20: ( COLON expression COLON expression )?
            int alt138=2;
            try { dbg.enterSubRule(138);
            try { dbg.enterDecision(138, decisionCanBacktrack[138]);

            int LA138_0 = input.LA(1);

            if ( (LA138_0==COLON) ) {
                alt138=1;
            }
            } finally {dbg.exitDecision(138);}

            switch (alt138) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:851:22: COLON expression COLON expression
                    {
                    dbg.location(851,22);
                    match(input,COLON,FOLLOW_COLON_in_mintypmax_expression6684); if (state.failed) return ;
                    dbg.location(851,28);
                    pushFollow(FOLLOW_expression_in_mintypmax_expression6686);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(851,39);
                    match(input,COLON,FOLLOW_COLON_in_mintypmax_expression6688); if (state.failed) return ;
                    dbg.location(851,45);
                    pushFollow(FOLLOW_expression_in_mintypmax_expression6690);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(138);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(852, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "mintypmax_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "mintypmax_expression"


    // $ANTLR start "exp11"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:854:1: exp11 : ( STRING | NUMBER | ( function_call )=> function_call | lvalue | DEFINE );
    public final void exp11() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp11");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(854, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:854:7: ( STRING | NUMBER | ( function_call )=> function_call | lvalue | DEFINE )
            int alt139=5;
            try { dbg.enterDecision(139, decisionCanBacktrack[139]);

            try {
                isCyclicDecision = true;
                alt139 = dfa139.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(139);}

            switch (alt139) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:855:9: STRING
                    {
                    dbg.location(855,9);
                    match(input,STRING,FOLLOW_STRING_in_exp116718); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:856:2: NUMBER
                    {
                    dbg.location(856,2);
                    match(input,NUMBER,FOLLOW_NUMBER_in_exp116723); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:857:2: ( function_call )=> function_call
                    {
                    dbg.location(857,21);
                    pushFollow(FOLLOW_function_call_in_exp116734);
                    function_call();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:858:2: lvalue
                    {
                    dbg.location(858,2);
                    pushFollow(FOLLOW_lvalue_in_exp116739);
                    lvalue();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    dbg.enterAlt(5);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:859:2: DEFINE
                    {
                    dbg.location(859,2);
                    match(input,DEFINE,FOLLOW_DEFINE_in_exp116744); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(860, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp11");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp11"


    // $ANTLR start "exp10"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:862:1: exp10 : ( exp11 | LPAREN expression RPAREN );
    public final void exp10() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp10");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(862, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:862:7: ( exp11 | LPAREN expression RPAREN )
            int alt140=2;
            try { dbg.enterDecision(140, decisionCanBacktrack[140]);

            int LA140_0 = input.LA(1);

            if ( (LA140_0==LCURLY||LA140_0==NUMBER||LA140_0==IDENTIFIER||LA140_0==SYSTEM_TASK_NAME||(LA140_0>=STRING && LA140_0<=DEFINE)||LA140_0==ESCAPED_IDENTIFIER) ) {
                alt140=1;
            }
            else if ( (LA140_0==LPAREN) ) {
                alt140=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 140, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(140);}

            switch (alt140) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:863:9: exp11
                    {
                    dbg.location(863,9);
                    pushFollow(FOLLOW_exp11_in_exp106769);
                    exp11();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:863:17: LPAREN expression RPAREN
                    {
                    dbg.location(863,17);
                    match(input,LPAREN,FOLLOW_LPAREN_in_exp106773); if (state.failed) return ;
                    dbg.location(863,24);
                    pushFollow(FOLLOW_expression_in_exp106775);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(863,35);
                    match(input,RPAREN,FOLLOW_RPAREN_in_exp106777); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(864, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp10");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp10"


    // $ANTLR start "exp9"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:866:1: exp9 : ( exp10 | unary_operator exp9 );
    public final void exp9() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp9");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(866, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:866:6: ( exp10 | unary_operator exp9 )
            int alt141=2;
            try { dbg.enterDecision(141, decisionCanBacktrack[141]);

            int LA141_0 = input.LA(1);

            if ( (LA141_0==LPAREN||LA141_0==LCURLY||LA141_0==NUMBER||LA141_0==IDENTIFIER||LA141_0==SYSTEM_TASK_NAME||(LA141_0>=STRING && LA141_0<=DEFINE)||LA141_0==ESCAPED_IDENTIFIER) ) {
                alt141=1;
            }
            else if ( ((LA141_0>=PLUS && LA141_0<=MINUS)||(LA141_0>=LNOT && LA141_0<=BXNOR)) ) {
                alt141=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(141);}

            switch (alt141) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:867:9: exp10
                    {
                    dbg.location(867,9);
                    pushFollow(FOLLOW_exp10_in_exp96802);
                    exp10();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:867:17: unary_operator exp9
                    {
                    dbg.location(867,17);
                    pushFollow(FOLLOW_unary_operator_in_exp96806);
                    unary_operator();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(867,32);
                    pushFollow(FOLLOW_exp9_in_exp96808);
                    exp9();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(868, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp9");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp9"


    // $ANTLR start "exp8"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:870:1: exp8 : exp9 ( binary_operator exp9 )* ;
    public final void exp8() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp8");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(870, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:870:6: ( exp9 ( binary_operator exp9 )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:871:9: exp9 ( binary_operator exp9 )*
            {
            dbg.location(871,9);
            pushFollow(FOLLOW_exp9_in_exp86833);
            exp9();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(871,14);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:871:14: ( binary_operator exp9 )*
            try { dbg.enterSubRule(142);

            loop142:
            do {
                int alt142=2;
                try { dbg.enterDecision(142, decisionCanBacktrack[142]);

                try {
                    isCyclicDecision = true;
                    alt142 = dfa142.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(142);}

                switch (alt142) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:871:16: binary_operator exp9
            	    {
            	    dbg.location(871,16);
            	    pushFollow(FOLLOW_binary_operator_in_exp86837);
            	    binary_operator();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    dbg.location(871,32);
            	    pushFollow(FOLLOW_exp9_in_exp86839);
            	    exp9();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop142;
                }
            } while (true);
            } finally {dbg.exitSubRule(142);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(872, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp8");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp8"


    // $ANTLR start "exp7"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:874:1: exp7 : exp8 ( QUESTION exp7 COLON exp7 )? ;
    public final void exp7() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp7");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(874, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:874:6: ( exp8 ( QUESTION exp7 COLON exp7 )? )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:875:9: exp8 ( QUESTION exp7 COLON exp7 )?
            {
            dbg.location(875,9);
            pushFollow(FOLLOW_exp8_in_exp76867);
            exp8();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(875,14);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:875:14: ( QUESTION exp7 COLON exp7 )?
            int alt143=2;
            try { dbg.enterSubRule(143);
            try { dbg.enterDecision(143, decisionCanBacktrack[143]);

            try {
                isCyclicDecision = true;
                alt143 = dfa143.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(143);}

            switch (alt143) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:875:16: QUESTION exp7 COLON exp7
                    {
                    dbg.location(875,16);
                    match(input,QUESTION,FOLLOW_QUESTION_in_exp76871); if (state.failed) return ;
                    dbg.location(875,25);
                    pushFollow(FOLLOW_exp7_in_exp76873);
                    exp7();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(875,30);
                    match(input,COLON,FOLLOW_COLON_in_exp76875); if (state.failed) return ;
                    dbg.location(875,36);
                    pushFollow(FOLLOW_exp7_in_exp76877);
                    exp7();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(143);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(876, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp7");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp7"


    // $ANTLR start "exp0"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:878:1: exp0 : exp7 ;
    public final void exp0() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "exp0");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(878, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:878:6: ( exp7 )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:879:9: exp7
            {
            dbg.location(879,9);
            pushFollow(FOLLOW_exp7_in_exp06905);
            exp7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(880, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "exp0");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "exp0"


    // $ANTLR start "expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:882:1: expression : exp0 ;
    public final void expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(882, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:882:12: ( exp0 )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:883:9: exp0
            {
            dbg.location(883,9);
            pushFollow(FOLLOW_exp0_in_expression6930);
            exp0();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(884, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expression"


    // $ANTLR start "function_call"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:886:1: function_call : ( name_of_function LPAREN expression_list RPAREN | SYSTEM_TASK_NAME ( LPAREN expression_list RPAREN )? );
    public final void function_call() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "function_call");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(886, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:886:15: ( name_of_function LPAREN expression_list RPAREN | SYSTEM_TASK_NAME ( LPAREN expression_list RPAREN )? )
            int alt145=2;
            try { dbg.enterDecision(145, decisionCanBacktrack[145]);

            int LA145_0 = input.LA(1);

            if ( (LA145_0==IDENTIFIER||LA145_0==ESCAPED_IDENTIFIER) ) {
                alt145=1;
            }
            else if ( (LA145_0==SYSTEM_TASK_NAME) ) {
                alt145=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(145);}

            switch (alt145) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:887:9: name_of_function LPAREN expression_list RPAREN
                    {
                    dbg.location(887,9);
                    pushFollow(FOLLOW_name_of_function_in_function_call6955);
                    name_of_function();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(887,26);
                    match(input,LPAREN,FOLLOW_LPAREN_in_function_call6957); if (state.failed) return ;
                    dbg.location(887,33);
                    pushFollow(FOLLOW_expression_list_in_function_call6959);
                    expression_list();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(887,49);
                    match(input,RPAREN,FOLLOW_RPAREN_in_function_call6961); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:888:9: SYSTEM_TASK_NAME ( LPAREN expression_list RPAREN )?
                    {
                    dbg.location(888,9);
                    match(input,SYSTEM_TASK_NAME,FOLLOW_SYSTEM_TASK_NAME_in_function_call6973); if (state.failed) return ;
                    dbg.location(888,26);
                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:888:26: ( LPAREN expression_list RPAREN )?
                    int alt144=2;
                    try { dbg.enterSubRule(144);
                    try { dbg.enterDecision(144, decisionCanBacktrack[144]);

                    try {
                        isCyclicDecision = true;
                        alt144 = dfa144.predict(input);
                    }
                    catch (NoViableAltException nvae) {
                        dbg.recognitionException(nvae);
                        throw nvae;
                    }
                    } finally {dbg.exitDecision(144);}

                    switch (alt144) {
                        case 1 :
                            dbg.enterAlt(1);

                            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:888:28: LPAREN expression_list RPAREN
                            {
                            dbg.location(888,28);
                            match(input,LPAREN,FOLLOW_LPAREN_in_function_call6977); if (state.failed) return ;
                            dbg.location(888,35);
                            pushFollow(FOLLOW_expression_list_in_function_call6979);
                            expression_list();

                            state._fsp--;
                            if (state.failed) return ;
                            dbg.location(888,51);
                            match(input,RPAREN,FOLLOW_RPAREN_in_function_call6981); if (state.failed) return ;

                            }
                            break;

                    }
                    } finally {dbg.exitSubRule(144);}


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(889, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "function_call");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "function_call"


    // $ANTLR start "expression_list"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:891:1: expression_list : expression ( COMMA expression )* ;
    public final void expression_list() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expression_list");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(891, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:891:17: ( expression ( COMMA expression )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:892:9: expression ( COMMA expression )*
            {
            dbg.location(892,9);
            pushFollow(FOLLOW_expression_in_expression_list7009);
            expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(892,20);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:892:20: ( COMMA expression )*
            try { dbg.enterSubRule(146);

            loop146:
            do {
                int alt146=2;
                try { dbg.enterDecision(146, decisionCanBacktrack[146]);

                int LA146_0 = input.LA(1);

                if ( (LA146_0==COMMA) ) {
                    alt146=1;
                }


                } finally {dbg.exitDecision(146);}

                switch (alt146) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:892:22: COMMA expression
            	    {
            	    dbg.location(892,22);
            	    match(input,COMMA,FOLLOW_COMMA_in_expression_list7013); if (state.failed) return ;
            	    dbg.location(892,28);
            	    pushFollow(FOLLOW_expression_in_expression_list7015);
            	    expression();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop146;
                }
            } while (true);
            } finally {dbg.exitSubRule(146);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(893, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expression_list");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expression_list"


    // $ANTLR start "unary_operator"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:895:1: unary_operator : ( PLUS | MINUS | LNOT | BNOT | BAND | RNAND | BOR | RNOR | BXOR | BXNOR );
    public final void unary_operator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "unary_operator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(895, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:895:16: ( PLUS | MINUS | LNOT | BNOT | BAND | RNAND | BOR | RNOR | BXOR | BXNOR )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(895,16);
            if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||(input.LA(1)>=LNOT && input.LA(1)<=BXNOR) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(906, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "unary_operator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "unary_operator"


    // $ANTLR start "binary_operator"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:908:1: binary_operator : ( PLUS | MINUS | STAR | DIV | MOD | EQUAL | NOT_EQ | EQ_CASE | NOT_EQ_CASE | LAND | LOR | LT_ | LE | GT | GE | BAND | BOR | BXOR | BXNOR | SR | SL );
    public final void binary_operator() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "binary_operator");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(908, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:908:17: ( PLUS | MINUS | STAR | DIV | MOD | EQUAL | NOT_EQ | EQ_CASE | NOT_EQ_CASE | LAND | LOR | LT_ | LE | GT | GE | BAND | BOR | BXOR | BXNOR | SR | SL )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(908,17);
            if ( input.LA(1)==LE||(input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==BAND||input.LA(1)==BOR||(input.LA(1)>=BXOR && input.LA(1)<=SL) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(930, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "binary_operator");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "binary_operator"


    // $ANTLR start "name_of_module"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:940:1: name_of_module : local_identifier ;
    public final void name_of_module() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_module");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(940, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:940:16: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:940:29: local_identifier
            {
            dbg.location(940,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_module7635);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(940, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_module");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_module"


    // $ANTLR start "name_of_port"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:941:1: name_of_port : local_identifier ;
    public final void name_of_port() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_port");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(941, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:941:14: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:941:29: local_identifier
            {
            dbg.location(941,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_port7656);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(941, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_port");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_port"


    // $ANTLR start "name_of_variable"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:942:1: name_of_variable : local_identifier ;
    public final void name_of_variable() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_variable");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(942, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:942:18: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:942:29: local_identifier
            {
            dbg.location(942,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_variable7673);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(942, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_variable");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_variable"


    // $ANTLR start "name_of_UDP"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:943:1: name_of_UDP : local_identifier ;
    public final void name_of_UDP() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_UDP");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(943, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:943:13: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:943:29: local_identifier
            {
            dbg.location(943,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_UDP7695);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(943, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_UDP");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_UDP"


    // $ANTLR start "name_of_UDP_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:944:1: name_of_UDP_instance : local_identifier ;
    public final void name_of_UDP_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_UDP_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(944, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:944:22: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:944:29: local_identifier
            {
            dbg.location(944,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_UDP_instance7708);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(944, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_UDP_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_UDP_instance"


    // $ANTLR start "name_of_event"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:945:1: name_of_event : local_identifier ;
    public final void name_of_event() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_event");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(945, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:945:15: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:945:29: local_identifier
            {
            dbg.location(945,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_event7728);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(945, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_event");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_event"


    // $ANTLR start "name_of_task"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:946:1: name_of_task : local_identifier ;
    public final void name_of_task() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_task");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(946, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:946:14: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:946:29: local_identifier
            {
            dbg.location(946,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_task7749);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(946, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_task");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_task"


    // $ANTLR start "real_identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:947:1: real_identifier : identifier ;
    public final void real_identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "real_identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(947, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:947:17: ( identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:947:29: identifier
            {
            dbg.location(947,29);
            pushFollow(FOLLOW_identifier_in_real_identifier7767);
            identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(947, 40);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "real_identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "real_identifier"


    // $ANTLR start "name_of_memory"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:948:1: name_of_memory : local_identifier ;
    public final void name_of_memory() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_memory");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(948, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:948:16: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:948:29: local_identifier
            {
            dbg.location(948,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_memory7786);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(948, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_memory");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_memory"


    // $ANTLR start "net_identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:949:1: net_identifier : identifier ;
    public final void net_identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "net_identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(949, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:949:16: ( identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:949:29: identifier
            {
            dbg.location(949,29);
            pushFollow(FOLLOW_identifier_in_net_identifier7805);
            identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(949, 40);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "net_identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "net_identifier"


    // $ANTLR start "name_of_function"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:950:1: name_of_function : local_identifier ;
    public final void name_of_function() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_function");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(950, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:950:18: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:950:29: local_identifier
            {
            dbg.location(950,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_function7822);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(950, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_function");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_function"


    // $ANTLR start "specparam_identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:951:1: specparam_identifier : identifier ;
    public final void specparam_identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "specparam_identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(951, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:951:22: ( identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:951:29: identifier
            {
            dbg.location(951,29);
            pushFollow(FOLLOW_identifier_in_specparam_identifier7835);
            identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(951, 40);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "specparam_identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "specparam_identifier"


    // $ANTLR start "udp_name_of_port"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:952:1: udp_name_of_port : identifier ;
    public final void udp_name_of_port() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "udp_name_of_port");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(952, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:952:18: ( identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:952:29: identifier
            {
            dbg.location(952,29);
            pushFollow(FOLLOW_identifier_in_udp_name_of_port7852);
            identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(952, 40);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "udp_name_of_port");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "udp_name_of_port"


    // $ANTLR start "name_of_register"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:953:1: name_of_register : local_identifier ;
    public final void name_of_register() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_register");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(953, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:953:18: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:953:29: local_identifier
            {
            dbg.location(953,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_register7869);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(953, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_register");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_register"


    // $ANTLR start "name_of_gate_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:954:1: name_of_gate_instance : local_identifier ;
    public final void name_of_gate_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_gate_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(954, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:954:23: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:954:29: local_identifier
            {
            dbg.location(954,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_gate_instance7881);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(954, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_gate_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_gate_instance"


    // $ANTLR start "name_of_instance"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:955:1: name_of_instance : local_identifier ;
    public final void name_of_instance() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_instance");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(955, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:955:18: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:955:29: local_identifier
            {
            dbg.location(955,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_instance7898);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(955, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_instance");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_instance"


    // $ANTLR start "name_of_block"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:956:1: name_of_block : local_identifier ;
    public final void name_of_block() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "name_of_block");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(956, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:956:15: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:956:29: local_identifier
            {
            dbg.location(956,29);
            pushFollow(FOLLOW_local_identifier_in_name_of_block7918);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(956, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "name_of_block");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "name_of_block"


    // $ANTLR start "output_terminal_name"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:957:1: output_terminal_name : local_identifier ;
    public final void output_terminal_name() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "output_terminal_name");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(957, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:957:22: ( local_identifier )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:957:29: local_identifier
            {
            dbg.location(957,29);
            pushFollow(FOLLOW_local_identifier_in_output_terminal_name7931);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(957, 46);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "output_terminal_name");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "output_terminal_name"


    // $ANTLR start "identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:964:1: identifier : identifier_path ;
    public final void identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(964, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:964:12: ( identifier_path )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:965:9: identifier_path
            {
            dbg.location(965,9);
            pushFollow(FOLLOW_identifier_path_in_identifier7953);
            identifier_path();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(966, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "identifier"


    // $ANTLR start "identifier_path"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:968:1: identifier_path : local_identifier ( DOT local_identifier )* ;
    public final void identifier_path() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "identifier_path");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(968, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:968:17: ( local_identifier ( DOT local_identifier )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:969:9: local_identifier ( DOT local_identifier )*
            {
            dbg.location(969,9);
            pushFollow(FOLLOW_local_identifier_in_identifier_path7978);
            local_identifier();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(969,26);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:969:26: ( DOT local_identifier )*
            try { dbg.enterSubRule(147);

            loop147:
            do {
                int alt147=2;
                try { dbg.enterDecision(147, decisionCanBacktrack[147]);

                try {
                    isCyclicDecision = true;
                    alt147 = dfa147.predict(input);
                }
                catch (NoViableAltException nvae) {
                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                } finally {dbg.exitDecision(147);}

                switch (alt147) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:969:28: DOT local_identifier
            	    {
            	    dbg.location(969,28);
            	    match(input,DOT,FOLLOW_DOT_in_identifier_path7982); if (state.failed) return ;
            	    dbg.location(969,32);
            	    pushFollow(FOLLOW_local_identifier_in_identifier_path7984);
            	    local_identifier();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop147;
                }
            } while (true);
            } finally {dbg.exitSubRule(147);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(970, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "identifier_path");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "identifier_path"


    // $ANTLR start "local_identifier"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:972:1: local_identifier : ( IDENTIFIER | ESCAPED_IDENTIFIER );
    public final void local_identifier() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "local_identifier");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(972, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:972:18: ( IDENTIFIER | ESCAPED_IDENTIFIER )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:
            {
            dbg.location(972,18);
            if ( input.LA(1)==IDENTIFIER||input.LA(1)==ESCAPED_IDENTIFIER ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(975, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "local_identifier");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "local_identifier"


    // $ANTLR start "delay_control"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:977:1: delay_control : ( POUND NUMBER | POUND identifier | POUND LPAREN mintypmax_expression RPAREN );
    public final void delay_control() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "delay_control");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(977, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:977:15: ( POUND NUMBER | POUND identifier | POUND LPAREN mintypmax_expression RPAREN )
            int alt148=3;
            try { dbg.enterDecision(148, decisionCanBacktrack[148]);

            int LA148_0 = input.LA(1);

            if ( (LA148_0==POUND) ) {
                switch ( input.LA(2) ) {
                case NUMBER:
                    {
                    alt148=1;
                    }
                    break;
                case LPAREN:
                    {
                    alt148=3;
                    }
                    break;
                case IDENTIFIER:
                case ESCAPED_IDENTIFIER:
                    {
                    alt148=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 148, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 148, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(148);}

            switch (alt148) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:978:9: POUND NUMBER
                    {
                    dbg.location(978,9);
                    match(input,POUND,FOLLOW_POUND_in_delay_control8049); if (state.failed) return ;
                    dbg.location(978,15);
                    match(input,NUMBER,FOLLOW_NUMBER_in_delay_control8051); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:979:9: POUND identifier
                    {
                    dbg.location(979,9);
                    match(input,POUND,FOLLOW_POUND_in_delay_control8063); if (state.failed) return ;
                    dbg.location(979,15);
                    pushFollow(FOLLOW_identifier_in_delay_control8065);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:980:9: POUND LPAREN mintypmax_expression RPAREN
                    {
                    dbg.location(980,9);
                    match(input,POUND,FOLLOW_POUND_in_delay_control8077); if (state.failed) return ;
                    dbg.location(980,15);
                    match(input,LPAREN,FOLLOW_LPAREN_in_delay_control8079); if (state.failed) return ;
                    dbg.location(980,22);
                    pushFollow(FOLLOW_mintypmax_expression_in_delay_control8081);
                    mintypmax_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(980,43);
                    match(input,RPAREN,FOLLOW_RPAREN_in_delay_control8083); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(981, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "delay_control");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "delay_control"


    // $ANTLR start "event_control"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:983:1: event_control : ( AT identifier | AT LPAREN event_expression RPAREN );
    public final void event_control() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "event_control");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(983, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:983:15: ( AT identifier | AT LPAREN event_expression RPAREN )
            int alt149=2;
            try { dbg.enterDecision(149, decisionCanBacktrack[149]);

            int LA149_0 = input.LA(1);

            if ( (LA149_0==AT) ) {
                int LA149_1 = input.LA(2);

                if ( (LA149_1==LPAREN) ) {
                    alt149=2;
                }
                else if ( (LA149_1==IDENTIFIER||LA149_1==ESCAPED_IDENTIFIER) ) {
                    alt149=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 149, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 149, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(149);}

            switch (alt149) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:984:9: AT identifier
                    {
                    dbg.location(984,9);
                    match(input,AT,FOLLOW_AT_in_event_control8108); if (state.failed) return ;
                    dbg.location(984,12);
                    pushFollow(FOLLOW_identifier_in_event_control8110);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:985:9: AT LPAREN event_expression RPAREN
                    {
                    dbg.location(985,9);
                    match(input,AT,FOLLOW_AT_in_event_control8122); if (state.failed) return ;
                    dbg.location(985,12);
                    match(input,LPAREN,FOLLOW_LPAREN_in_event_control8124); if (state.failed) return ;
                    dbg.location(985,19);
                    pushFollow(FOLLOW_event_expression_in_event_control8126);
                    event_expression();

                    state._fsp--;
                    if (state.failed) return ;
                    dbg.location(985,36);
                    match(input,RPAREN,FOLLOW_RPAREN_in_event_control8128); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(986, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "event_control");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "event_control"


    // $ANTLR start "event_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:988:1: event_expression : sub_event_expression ( 'or' sub_event_expression )* ;
    public final void event_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "event_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(988, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:988:18: ( sub_event_expression ( 'or' sub_event_expression )* )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:989:9: sub_event_expression ( 'or' sub_event_expression )*
            {
            dbg.location(989,9);
            pushFollow(FOLLOW_sub_event_expression_in_event_expression8153);
            sub_event_expression();

            state._fsp--;
            if (state.failed) return ;
            dbg.location(989,30);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:989:30: ( 'or' sub_event_expression )*
            try { dbg.enterSubRule(150);

            loop150:
            do {
                int alt150=2;
                try { dbg.enterDecision(150, decisionCanBacktrack[150]);

                int LA150_0 = input.LA(1);

                if ( (LA150_0==117) ) {
                    alt150=1;
                }


                } finally {dbg.exitDecision(150);}

                switch (alt150) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:989:32: 'or' sub_event_expression
            	    {
            	    dbg.location(989,32);
            	    match(input,117,FOLLOW_117_in_event_expression8157); if (state.failed) return ;
            	    dbg.location(989,37);
            	    pushFollow(FOLLOW_sub_event_expression_in_event_expression8159);
            	    sub_event_expression();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop150;
                }
            } while (true);
            } finally {dbg.exitSubRule(150);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(990, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "event_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "event_expression"


    // $ANTLR start "sub_event_expression"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:992:1: sub_event_expression : ( expression | 'posedge' expression | 'negedge' expression );
    public final void sub_event_expression() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sub_event_expression");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(992, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:992:22: ( expression | 'posedge' expression | 'negedge' expression )
            int alt151=3;
            try { dbg.enterDecision(151, decisionCanBacktrack[151]);

            try {
                isCyclicDecision = true;
                alt151 = dfa151.predict(input);
            }
            catch (NoViableAltException nvae) {
                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(151);}

            switch (alt151) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:993:9: expression
                    {
                    dbg.location(993,9);
                    pushFollow(FOLLOW_expression_in_sub_event_expression8187);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:994:9: 'posedge' expression
                    {
                    dbg.location(994,9);
                    match(input,173,FOLLOW_173_in_sub_event_expression8199); if (state.failed) return ;
                    dbg.location(994,19);
                    pushFollow(FOLLOW_expression_in_sub_event_expression8201);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:995:9: 'negedge' expression
                    {
                    dbg.location(995,9);
                    match(input,174,FOLLOW_174_in_sub_event_expression8213); if (state.failed) return ;
                    dbg.location(995,19);
                    pushFollow(FOLLOW_expression_in_sub_event_expression8215);
                    expression();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(996, 9);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sub_event_expression");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sub_event_expression"


    // $ANTLR start "directive"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1002:1: directive : ( define_directive | include_directive );
    public final void directive() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "directive");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1002, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1002:10: ( define_directive | include_directive )
            int alt152=2;
            try { dbg.enterDecision(152, decisionCanBacktrack[152]);

            int LA152_0 = input.LA(1);

            if ( (LA152_0==178) ) {
                alt152=1;
            }
            else if ( (LA152_0==179) ) {
                alt152=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 152, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(152);}

            switch (alt152) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1003:2: define_directive
                    {
                    dbg.location(1003,2);
                    pushFollow(FOLLOW_define_directive_in_directive8236);
                    define_directive();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1004:2: include_directive
                    {
                    dbg.location(1004,2);
                    pushFollow(FOLLOW_include_directive_in_directive8241);
                    include_directive();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1005, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "directive");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "directive"


    // $ANTLR start "define_directive"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1007:1: define_directive : '`define' IDENTIFIER expression ;
    public final void define_directive() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "define_directive");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1007, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1007:18: ( '`define' IDENTIFIER expression )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1008:2: '`define' IDENTIFIER expression
            {
            dbg.location(1008,2);
            match(input,178,FOLLOW_178_in_define_directive8252); if (state.failed) return ;
            dbg.location(1008,12);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_define_directive8254); if (state.failed) return ;
            dbg.location(1008,23);
            pushFollow(FOLLOW_expression_in_define_directive8256);
            expression();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1009, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "define_directive");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "define_directive"


    // $ANTLR start "include_directive"
    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1011:1: include_directive : '`include' ( identifier | STRING ) ;
    public final void include_directive() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "include_directive");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(1011, 1);

        try {
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1011:19: ( '`include' ( identifier | STRING ) )
            dbg.enterAlt(1);

            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1012:2: '`include' ( identifier | STRING )
            {
            dbg.location(1012,2);
            match(input,179,FOLLOW_179_in_include_directive8267); if (state.failed) return ;
            dbg.location(1012,13);
            // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1012:13: ( identifier | STRING )
            int alt153=2;
            try { dbg.enterSubRule(153);
            try { dbg.enterDecision(153, decisionCanBacktrack[153]);

            int LA153_0 = input.LA(1);

            if ( (LA153_0==IDENTIFIER||LA153_0==ESCAPED_IDENTIFIER) ) {
                alt153=1;
            }
            else if ( (LA153_0==STRING) ) {
                alt153=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 153, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(153);}

            switch (alt153) {
                case 1 :
                    dbg.enterAlt(1);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1012:15: identifier
                    {
                    dbg.location(1012,15);
                    pushFollow(FOLLOW_identifier_in_include_directive8271);
                    identifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:1012:28: STRING
                    {
                    dbg.location(1012,28);
                    match(input,STRING,FOLLOW_STRING_in_include_directive8275); if (state.failed) return ;

                    }
                    break;

            }
            } finally {dbg.exitSubRule(153);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(1013, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "include_directive");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "include_directive"

    // $ANTLR start synpred1_Verilog
    public final void synpred1_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:53:9: ( name_of_variable LBRACK expression COLON )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:53:11: name_of_variable LBRACK expression COLON
        {
        dbg.location(53,11);
        pushFollow(FOLLOW_name_of_variable_in_synpred1_Verilog332);
        name_of_variable();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(53,28);
        match(input,LBRACK,FOLLOW_LBRACK_in_synpred1_Verilog334); if (state.failed) return ;
        dbg.location(53,35);
        pushFollow(FOLLOW_expression_in_synpred1_Verilog336);
        expression();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(53,46);
        match(input,COLON,FOLLOW_COLON_in_synpred1_Verilog338); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Verilog

    // $ANTLR start synpred2_Verilog
    public final void synpred2_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:55:9: ( name_of_variable LBRACK )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:55:11: name_of_variable LBRACK
        {
        dbg.location(55,11);
        pushFollow(FOLLOW_name_of_variable_in_synpred2_Verilog376);
        name_of_variable();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(55,28);
        match(input,LBRACK,FOLLOW_LBRACK_in_synpred2_Verilog378); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Verilog

    // $ANTLR start synpred3_Verilog
    public final void synpred3_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:86:2: ( module_instantiation )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:86:3: module_instantiation
        {
        dbg.location(86,3);
        pushFollow(FOLLOW_module_instantiation_in_synpred3_Verilog673);
        module_instantiation();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Verilog

    // $ANTLR start synpred4_Verilog
    public final void synpred4_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:285:9: ( net_type ( expandrange )? )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:285:11: net_type ( expandrange )?
        {
        dbg.location(285,11);
        pushFollow(FOLLOW_net_type_in_synpred4_Verilog1562);
        net_type();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(285,20);
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:285:20: ( expandrange )?
        int alt154=2;
        try { dbg.enterSubRule(154);
        try { dbg.enterDecision(154, decisionCanBacktrack[154]);

        int LA154_0 = input.LA(1);

        if ( (LA154_0==LBRACK||(LA154_0>=98 && LA154_0<=99)) ) {
            alt154=1;
        }
        } finally {dbg.exitDecision(154);}

        switch (alt154) {
            case 1 :
                dbg.enterAlt(1);

                // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:285:21: expandrange
                {
                dbg.location(285,21);
                pushFollow(FOLLOW_expandrange_in_synpred4_Verilog1565);
                expandrange();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }
        } finally {dbg.exitSubRule(154);}


        }
    }
    // $ANTLR end synpred4_Verilog

    // $ANTLR start synpred5_Verilog
    public final void synpred5_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:388:2: ( LBRACK expression COLON )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:388:3: LBRACK expression COLON
        {
        dbg.location(388,3);
        match(input,LBRACK,FOLLOW_LBRACK_in_synpred5_Verilog2534); if (state.failed) return ;
        dbg.location(388,10);
        pushFollow(FOLLOW_expression_in_synpred5_Verilog2536);
        expression();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(388,21);
        match(input,COLON,FOLLOW_COLON_in_synpred5_Verilog2538); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Verilog

    // $ANTLR start synpred6_Verilog
    public final void synpred6_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:512:9: ( statement )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:512:10: statement
        {
        dbg.location(512,10);
        pushFollow(FOLLOW_statement_in_synpred6_Verilog3571);
        statement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Verilog

    // $ANTLR start synpred7_Verilog
    public final void synpred7_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:517:9: ( lvalue ASSIGN )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:517:10: lvalue ASSIGN
        {
        dbg.location(517,10);
        pushFollow(FOLLOW_lvalue_in_synpred7_Verilog3607);
        lvalue();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(517,17);
        match(input,ASSIGN,FOLLOW_ASSIGN_in_synpred7_Verilog3609); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Verilog

    // $ANTLR start synpred8_Verilog
    public final void synpred8_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:518:9: ( lvalue LE )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:518:10: lvalue LE
        {
        dbg.location(518,10);
        pushFollow(FOLLOW_lvalue_in_synpred8_Verilog3629);
        lvalue();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(518,17);
        match(input,LE,FOLLOW_LE_in_synpred8_Verilog3631); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Verilog

    // $ANTLR start synpred9_Verilog
    public final void synpred9_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:643:9: ( path_declaration )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:643:10: path_declaration
        {
        dbg.location(643,10);
        pushFollow(FOLLOW_path_declaration_in_synpred9_Verilog4776);
        path_declaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Verilog

    // $ANTLR start synpred10_Verilog
    public final void synpred10_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:661:9: ( simple_path_declaration )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:661:10: simple_path_declaration
        {
        dbg.location(661,10);
        pushFollow(FOLLOW_simple_path_declaration_in_synpred10_Verilog4923);
        simple_path_declaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Verilog

    // $ANTLR start synpred11_Verilog
    public final void synpred11_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:663:9: ( level_sensitive_path_declaration )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:663:10: level_sensitive_path_declaration
        {
        dbg.location(663,10);
        pushFollow(FOLLOW_level_sensitive_path_declaration_in_synpred11_Verilog4947);
        level_sensitive_path_declaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Verilog

    // $ANTLR start synpred12_Verilog
    public final void synpred12_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:669:9: ( parallel_path_description )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:669:10: parallel_path_description
        {
        dbg.location(669,10);
        pushFollow(FOLLOW_parallel_path_description_in_synpred12_Verilog4998);
        parallel_path_description();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Verilog

    // $ANTLR start synpred13_Verilog
    public final void synpred13_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:687:2: ( identifier LBRACK expression COLON )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:687:3: identifier LBRACK expression COLON
        {
        dbg.location(687,3);
        pushFollow(FOLLOW_identifier_in_synpred13_Verilog5147);
        identifier();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(687,14);
        match(input,LBRACK,FOLLOW_LBRACK_in_synpred13_Verilog5149); if (state.failed) return ;
        dbg.location(687,21);
        pushFollow(FOLLOW_expression_in_synpred13_Verilog5151);
        expression();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(687,32);
        match(input,COLON,FOLLOW_COLON_in_synpred13_Verilog5153); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Verilog

    // $ANTLR start synpred14_Verilog
    public final void synpred14_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:689:9: ( identifier LBRACK )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:689:10: identifier LBRACK
        {
        dbg.location(689,10);
        pushFollow(FOLLOW_identifier_in_synpred14_Verilog5192);
        identifier();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(689,21);
        match(input,LBRACK,FOLLOW_LBRACK_in_synpred14_Verilog5194); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Verilog

    // $ANTLR start synpred15_Verilog
    public final void synpred15_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:695:9: ( path_delay_expression )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:695:10: path_delay_expression
        {
        dbg.location(695,10);
        pushFollow(FOLLOW_path_delay_expression_in_synpred15_Verilog5254);
        path_delay_expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Verilog

    // $ANTLR start synpred16_Verilog
    public final void synpred16_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:780:2: ( parallel_level_sensitive_path_description )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:780:3: parallel_level_sensitive_path_description
        {
        dbg.location(780,3);
        pushFollow(FOLLOW_parallel_level_sensitive_path_description_in_synpred16_Verilog6126);
        parallel_level_sensitive_path_description();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Verilog

    // $ANTLR start synpred17_Verilog
    public final void synpred17_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:809:14: ( list_of_path_terminals )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:809:15: list_of_path_terminals
        {
        dbg.location(809,15);
        pushFollow(FOLLOW_list_of_path_terminals_in_synpred17_Verilog6362);
        list_of_path_terminals();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Verilog

    // $ANTLR start synpred18_Verilog
    public final void synpred18_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:837:2: ( identifier range )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:837:3: identifier range
        {
        dbg.location(837,3);
        pushFollow(FOLLOW_identifier_in_synpred18_Verilog6526);
        identifier();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(837,14);
        pushFollow(FOLLOW_range_in_synpred18_Verilog6528);
        range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Verilog

    // $ANTLR start synpred19_Verilog
    public final void synpred19_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:844:2: ( LCURLY expression LCURLY )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:844:3: LCURLY expression LCURLY
        {
        dbg.location(844,3);
        match(input,LCURLY,FOLLOW_LCURLY_in_synpred19_Verilog6586); if (state.failed) return ;
        dbg.location(844,10);
        pushFollow(FOLLOW_expression_in_synpred19_Verilog6588);
        expression();

        state._fsp--;
        if (state.failed) return ;
        dbg.location(844,21);
        match(input,LCURLY,FOLLOW_LCURLY_in_synpred19_Verilog6590); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Verilog

    // $ANTLR start synpred20_Verilog
    public final void synpred20_Verilog_fragment() throws RecognitionException {   
        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:857:2: ( function_call )
        dbg.enterAlt(1);

        // C:\\Users\\rozagh\\Documents\\NetBeansProjects\\VerilogParser2\\Verilog.g:857:3: function_call
        {
        dbg.location(857,3);
        pushFollow(FOLLOW_function_call_in_synpred20_Verilog6729);
        function_call();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Verilog

    // Delegated rules

    public final boolean synpred9_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred9_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred18_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred14_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred1_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred6_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred10_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred19_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred16_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred7_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred2_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred11_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred13_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred17_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred4_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred12_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred5_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred20_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred15_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred8_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Verilog() {
        state.backtracking++;
        dbg.beginBacktrack(state.backtracking);
        int start = input.mark();
        try {
            synpred3_Verilog_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        dbg.endBacktrack(state.backtracking, success);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA57 dfa57 = new DFA57(this);
    protected DFA65 dfa65 = new DFA65(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA77 dfa77 = new DFA77(this);
    protected DFA78 dfa78 = new DFA78(this);
    protected DFA79 dfa79 = new DFA79(this);
    protected DFA80 dfa80 = new DFA80(this);
    protected DFA81 dfa81 = new DFA81(this);
    protected DFA82 dfa82 = new DFA82(this);
    protected DFA83 dfa83 = new DFA83(this);
    protected DFA86 dfa86 = new DFA86(this);
    protected DFA85 dfa85 = new DFA85(this);
    protected DFA89 dfa89 = new DFA89(this);
    protected DFA88 dfa88 = new DFA88(this);
    protected DFA90 dfa90 = new DFA90(this);
    protected DFA92 dfa92 = new DFA92(this);
    protected DFA91 dfa91 = new DFA91(this);
    protected DFA93 dfa93 = new DFA93(this);
    protected DFA95 dfa95 = new DFA95(this);
    protected DFA98 dfa98 = new DFA98(this);
    protected DFA103 dfa103 = new DFA103(this);
    protected DFA104 dfa104 = new DFA104(this);
    protected DFA106 dfa106 = new DFA106(this);
    protected DFA109 dfa109 = new DFA109(this);
    protected DFA110 dfa110 = new DFA110(this);
    protected DFA127 dfa127 = new DFA127(this);
    protected DFA134 dfa134 = new DFA134(this);
    protected DFA137 dfa137 = new DFA137(this);
    protected DFA139 dfa139 = new DFA139(this);
    protected DFA142 dfa142 = new DFA142(this);
    protected DFA143 dfa143 = new DFA143(this);
    protected DFA144 dfa144 = new DFA144(this);
    protected DFA147 dfa147 = new DFA147(this);
    protected DFA151 dfa151 = new DFA151(this);
    static final String DFA4_eotS =
        "\27\uffff";
    static final String DFA4_eofS =
        "\27\uffff";
    static final String DFA4_minS =
        "\1\20\26\uffff";
    static final String DFA4_maxS =
        "\1\u00b3\26\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\2\1\1\24\uffff";
    static final String DFA4_specialS =
        "\27\uffff}>";
    static final String[] DFA4_transitionS = {
            "\2\2\1\uffff\1\2\40\uffff\1\2\20\uffff\1\1\2\uffff\1\2\5\uffff"+
            "\1\2\1\uffff\1\2\1\uffff\20\2\2\uffff\4\2\13\uffff\33\2\24\uffff"+
            "\1\2\17\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "()* loopback of 34:2: ( module_item )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA11_eotS =
        "\20\uffff";
    static final String DFA11_eofS =
        "\20\uffff";
    static final String DFA11_minS =
        "\1\23\1\6\1\5\3\uffff\10\0\2\uffff";
    static final String DFA11_maxS =
        "\1\64\1\13\1\64\3\uffff\10\0\2\uffff";
    static final String DFA11_acceptS =
        "\3\uffff\1\3\12\uffff\1\1\1\2";
    static final String DFA11_specialS =
        "\6\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\2\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\1\40\uffff\1\1",
            "\2\3\2\uffff\1\3\1\2",
            "\1\14\3\uffff\1\12\5\uffff\1\7\3\uffff\1\10\2\uffff\1\11\2"+
            "\uffff\2\15\1\6\1\13\1\uffff\10\15\16\uffff\1\10",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "52:1: port_reference : ( ( name_of_variable LBRACK expression COLON )=> name_of_variable LBRACK expression COLON expression RBRACK | ( name_of_variable LBRACK )=> name_of_variable LBRACK expression RBRACK | name_of_variable );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_6 = input.LA(1);

                         
                        int index11_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA11_7 = input.LA(1);

                         
                        int index11_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA11_8 = input.LA(1);

                         
                        int index11_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA11_9 = input.LA(1);

                         
                        int index11_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_9);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA11_10 = input.LA(1);

                         
                        int index11_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA11_11 = input.LA(1);

                         
                        int index11_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA11_12 = input.LA(1);

                         
                        int index11_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA11_13 = input.LA(1);

                         
                        int index11_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Verilog()) ) {s = 14;}

                        else if ( (synpred2_Verilog()) ) {s = 15;}

                         
                        input.seek(index11_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA12_eotS =
        "\26\uffff";
    static final String DFA12_eofS =
        "\26\uffff";
    static final String DFA12_minS =
        "\1\20\25\uffff";
    static final String DFA12_maxS =
        "\1\u00b3\25\uffff";
    static final String DFA12_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\uffff\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff";
    static final String DFA12_specialS =
        "\26\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\15\1\uffff\1\13\40\uffff\1\13\23\uffff\1\20\5\uffff\1\22"+
            "\1\uffff\1\23\1\uffff\1\7\1\10\1\1\1\2\1\3\1\4\12\15\2\uffff"+
            "\1\5\1\6\1\11\1\14\13\uffff\32\12\1\21\24\uffff\1\17\17\uffff"+
            "\2\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "60:1: module_item : ( parameter_declaration | input_declaration | output_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration | gate_declaration | instantiation | parameter_override | continuous_assign | specify_block | initial_statement | always_statement | task | function | directive );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA13_eotS =
        "\12\uffff";
    static final String DFA13_eofS =
        "\12\uffff";
    static final String DFA13_minS =
        "\1\23\3\5\1\uffff\1\0\2\uffff\1\0\1\uffff";
    static final String DFA13_maxS =
        "\3\64\1\5\1\uffff\1\0\2\uffff\1\0\1\uffff";
    static final String DFA13_acceptS =
        "\4\uffff\1\2\4\uffff\1\1";
    static final String DFA13_specialS =
        "\5\uffff\1\0\2\uffff\1\1\1\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1\40\uffff\1\1",
            "\1\4\14\uffff\1\2\1\3\40\uffff\1\3",
            "\1\5\11\uffff\1\4\3\uffff\1\4\40\uffff\1\4",
            "\1\10",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "85:1: instantiation : ( ( module_instantiation )=> module_instantiation | udp_instantiation );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_5 = input.LA(1);

                         
                        int index13_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index13_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA13_8 = input.LA(1);

                         
                        int index13_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index13_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA22_eotS =
        "\37\uffff";
    static final String DFA22_eofS =
        "\37\uffff";
    static final String DFA22_minS =
        "\1\4\36\uffff";
    static final String DFA22_maxS =
        "\1\u00a1\36\uffff";
    static final String DFA22_acceptS =
        "\1\uffff\1\2\24\uffff\1\1\10\uffff";
    static final String DFA22_specialS =
        "\37\uffff}>";
    static final String[] DFA22_transitionS = {
            "\1\1\4\uffff\1\1\7\uffff\3\1\1\uffff\2\1\35\uffff\2\1\34\uffff"+
            "\6\26\14\uffff\3\26\47\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff"+
            "\1\1\1\uffff\3\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "()* loopback of 225:9: ( tf_declaration )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA24_eotS =
        "\36\uffff";
    static final String DFA24_eofS =
        "\36\uffff";
    static final String DFA24_minS =
        "\1\11\35\uffff";
    static final String DFA24_maxS =
        "\1\u00a1\35\uffff";
    static final String DFA24_acceptS =
        "\1\uffff\1\2\23\uffff\1\1\10\uffff";
    static final String DFA24_specialS =
        "\36\uffff}>";
    static final String[] DFA24_transitionS = {
            "\1\1\7\uffff\3\1\1\uffff\2\1\35\uffff\2\1\34\uffff\6\25\14"+
            "\uffff\3\25\47\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff\1\1"+
            "\1\uffff\3\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "()+ loopback of 232:9: ( tf_declaration )+";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA26_eotS =
        "\12\uffff";
    static final String DFA26_eofS =
        "\12\uffff";
    static final String DFA26_minS =
        "\1\122\11\uffff";
    static final String DFA26_maxS =
        "\1\146\11\uffff";
    static final String DFA26_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String DFA26_specialS =
        "\12\uffff}>";
    static final String[] DFA26_transitionS = {
            "\1\7\1\10\1\1\1\3\1\2\1\4\14\uffff\1\5\1\6\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "243:1: tf_declaration : ( parameter_declaration | output_declaration | input_declaration | inout_declaration | reg_declaration | time_declaration | integer_declaration | real_declaration | event_declaration );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA37_eotS =
        "\31\uffff";
    static final String DFA37_eofS =
        "\31\uffff";
    static final String DFA37_minS =
        "\1\20\1\5\1\uffff\2\13\2\5\1\4\1\uffff\15\0\1\uffff\2\0";
    static final String DFA37_maxS =
        "\1\141\1\143\1\uffff\2\13\2\64\1\16\1\uffff\15\0\1\uffff\2\0";
    static final String DFA37_acceptS =
        "\2\uffff\1\1\5\uffff\1\2\15\uffff\1\1\2\uffff";
    static final String DFA37_specialS =
        "\1\0\6\uffff\1\1\1\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
        "\1\13\1\14\1\15\1\16\1\uffff\1\17\1\20}>";
    static final String[] DFA37_transitionS = {
            "\1\1\107\uffff\12\2",
            "\1\10\5\uffff\1\5\6\uffff\1\6\1\7\40\uffff\1\7\55\uffff\1"+
            "\3\1\4",
            "",
            "\1\11",
            "\1\12",
            "\1\21\3\uffff\1\17\5\uffff\1\14\3\uffff\1\15\2\uffff\1\16"+
            "\2\uffff\2\22\1\13\1\20\1\uffff\10\22\16\uffff\1\15",
            "\1\24\11\uffff\1\23\3\uffff\1\25\40\uffff\1\25",
            "\1\30\1\uffff\1\27\7\uffff\1\26",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA37_eot = DFA.unpackEncodedString(DFA37_eotS);
    static final short[] DFA37_eof = DFA.unpackEncodedString(DFA37_eofS);
    static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars(DFA37_minS);
    static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars(DFA37_maxS);
    static final short[] DFA37_accept = DFA.unpackEncodedString(DFA37_acceptS);
    static final short[] DFA37_special = DFA.unpackEncodedString(DFA37_specialS);
    static final short[][] DFA37_transition;

    static {
        int numStates = DFA37_transitionS.length;
        DFA37_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA37_transition[i] = DFA.unpackEncodedString(DFA37_transitionS[i]);
        }
    }

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = DFA37_eot;
            this.eof = DFA37_eof;
            this.min = DFA37_min;
            this.max = DFA37_max;
            this.accept = DFA37_accept;
            this.special = DFA37_special;
            this.transition = DFA37_transition;
        }
        public String getDescription() {
            return "284:1: net_declaration : ( ( net_type ( expandrange )? )=> net_type ( expandrange )? ( delay )? list_of_assigned_variables SEMI | KW_TRIREG ( charge_strength )? ( expandrange )? ( delay )? list_of_variables SEMI );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_0 = input.LA(1);

                         
                        int index37_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_0==KW_TRIREG) ) {s = 1;}

                        else if ( ((LA37_0>=88 && LA37_0<=97)) && (synpred4_Verilog())) {s = 2;}

                         
                        input.seek(index37_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_7 = input.LA(1);

                         
                        int index37_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_7==ASSIGN) && (synpred4_Verilog())) {s = 22;}

                        else if ( (LA37_7==COMMA) ) {s = 23;}

                        else if ( (LA37_7==SEMI) ) {s = 24;}

                         
                        input.seek(index37_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_9 = input.LA(1);

                         
                        int index37_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_10 = input.LA(1);

                         
                        int index37_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_10);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_11 = input.LA(1);

                         
                        int index37_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_11);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_12 = input.LA(1);

                         
                        int index37_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA37_13 = input.LA(1);

                         
                        int index37_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_13);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA37_14 = input.LA(1);

                         
                        int index37_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_14);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA37_15 = input.LA(1);

                         
                        int index37_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_15);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA37_16 = input.LA(1);

                         
                        int index37_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_16);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA37_17 = input.LA(1);

                         
                        int index37_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_17);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA37_18 = input.LA(1);

                         
                        int index37_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_18);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA37_19 = input.LA(1);

                         
                        int index37_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_19);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA37_20 = input.LA(1);

                         
                        int index37_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_20);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA37_21 = input.LA(1);

                         
                        int index37_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_21);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA37_23 = input.LA(1);

                         
                        int index37_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_23);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA37_24 = input.LA(1);

                         
                        int index37_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index37_24);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA55_eotS =
        "\72\uffff";
    static final String DFA55_eofS =
        "\72\uffff";
    static final String DFA55_minS =
        "\1\13\1\5\2\14\3\5\1\14\2\5\2\0\2\uffff\2\0\2\uffff\5\0\2\uffff"+
        "\3\0\2\uffff\12\0\2\uffff\20\0";
    static final String DFA55_maxS =
        "\1\13\1\64\4\63\1\64\1\63\2\64\2\0\2\uffff\2\0\2\uffff\5\0\2\uffff"+
        "\3\0\2\uffff\12\0\2\uffff\20\0";
    static final String DFA55_acceptS =
        "\14\uffff\1\1\1\2\2\uffff\1\1\6\uffff\1\1\4\uffff\1\1\13\uffff"+
        "\1\1\21\uffff";
    static final String DFA55_specialS =
        "\2\uffff\1\0\1\1\1\2\1\3\1\uffff\1\4\2\uffff\1\5\1\6\2\uffff\1"+
        "\7\1\10\2\uffff\1\11\1\12\1\13\1\14\1\15\2\uffff\1\16\1\17\1\20"+
        "\2\uffff\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\2\uffff"+
        "\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
        "\1\50\1\51\1\52}>";
    static final String[] DFA55_transitionS = {
            "\1\1",
            "\1\10\3\uffff\1\6\5\uffff\1\3\3\uffff\1\4\2\uffff\1\5\2\uffff"+
            "\2\11\1\2\1\7\1\uffff\10\11\16\uffff\1\4",
            "\1\14\1\15\6\uffff\1\12\4\uffff\2\12\2\uffff\1\13\2\uffff"+
            "\1\12\1\uffff\1\12\1\uffff\20\12",
            "\1\20\1\15\6\uffff\1\16\4\uffff\2\16\2\uffff\1\17\2\uffff"+
            "\1\16\1\uffff\1\16\1\uffff\20\16",
            "\1\22\2\uffff\1\23\2\uffff\1\24\1\27\1\15\6\uffff\1\25\4\uffff"+
            "\2\25\2\uffff\1\26\2\uffff\1\25\1\uffff\1\25\1\uffff\20\25",
            "\1\31\6\uffff\1\34\1\15\6\uffff\1\32\4\uffff\2\32\2\uffff"+
            "\1\33\2\uffff\1\32\1\uffff\1\32\1\uffff\20\32",
            "\1\44\3\uffff\1\42\5\uffff\1\37\3\uffff\1\40\2\uffff\1\41"+
            "\2\uffff\2\45\1\36\1\43\1\uffff\10\45\16\uffff\1\40",
            "\1\50\1\15\6\uffff\1\46\4\uffff\2\46\2\uffff\1\47\2\uffff"+
            "\1\46\1\uffff\1\46\1\uffff\20\46",
            "\1\60\3\uffff\1\56\5\uffff\1\53\3\uffff\1\54\2\uffff\1\55"+
            "\2\uffff\2\61\1\52\1\57\1\uffff\10\61\16\uffff\1\54",
            "\1\70\3\uffff\1\66\5\uffff\1\63\3\uffff\1\64\2\uffff\1\65"+
            "\2\uffff\2\71\1\62\1\67\1\uffff\10\71\16\uffff\1\64",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA55_eot = DFA.unpackEncodedString(DFA55_eotS);
    static final short[] DFA55_eof = DFA.unpackEncodedString(DFA55_eofS);
    static final char[] DFA55_min = DFA.unpackEncodedStringToUnsignedChars(DFA55_minS);
    static final char[] DFA55_max = DFA.unpackEncodedStringToUnsignedChars(DFA55_maxS);
    static final short[] DFA55_accept = DFA.unpackEncodedString(DFA55_acceptS);
    static final short[] DFA55_special = DFA.unpackEncodedString(DFA55_specialS);
    static final short[][] DFA55_transition;

    static {
        int numStates = DFA55_transitionS.length;
        DFA55_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA55_transition[i] = DFA.unpackEncodedString(DFA55_transitionS[i]);
        }
    }

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = DFA55_eot;
            this.eof = DFA55_eof;
            this.min = DFA55_min;
            this.max = DFA55_max;
            this.accept = DFA55_accept;
            this.special = DFA55_special;
            this.transition = DFA55_transition;
        }
        public String getDescription() {
            return "387:1: range : ( ( LBRACK expression COLON )=> LBRACK expression COLON expression RBRACK | LBRACK expression RBRACK );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA55_2 = input.LA(1);

                         
                        int index55_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA55_2==LE||(LA55_2>=PLUS && LA55_2<=MINUS)||LA55_2==BAND||LA55_2==BOR||(LA55_2>=BXOR && LA55_2<=SL)) ) {s = 10;}

                        else if ( (LA55_2==QUESTION) ) {s = 11;}

                        else if ( (LA55_2==COLON) && (synpred5_Verilog())) {s = 12;}

                        else if ( (LA55_2==RBRACK) ) {s = 13;}

                         
                        input.seek(index55_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA55_3 = input.LA(1);

                         
                        int index55_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA55_3==LE||(LA55_3>=PLUS && LA55_3<=MINUS)||LA55_3==BAND||LA55_3==BOR||(LA55_3>=BXOR && LA55_3<=SL)) ) {s = 14;}

                        else if ( (LA55_3==QUESTION) ) {s = 15;}

                        else if ( (LA55_3==COLON) && (synpred5_Verilog())) {s = 16;}

                        else if ( (LA55_3==RBRACK) ) {s = 13;}

                         
                        input.seek(index55_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA55_4 = input.LA(1);

                         
                        int index55_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA55_4==LPAREN) ) {s = 18;}

                        else if ( (LA55_4==DOT) ) {s = 19;}

                        else if ( (LA55_4==LBRACK) ) {s = 20;}

                        else if ( (LA55_4==LE||(LA55_4>=PLUS && LA55_4<=MINUS)||LA55_4==BAND||LA55_4==BOR||(LA55_4>=BXOR && LA55_4<=SL)) ) {s = 21;}

                        else if ( (LA55_4==QUESTION) ) {s = 22;}

                        else if ( (LA55_4==COLON) && (synpred5_Verilog())) {s = 23;}

                        else if ( (LA55_4==RBRACK) ) {s = 13;}

                         
                        input.seek(index55_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA55_5 = input.LA(1);

                         
                        int index55_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA55_5==LPAREN) ) {s = 25;}

                        else if ( (LA55_5==LE||(LA55_5>=PLUS && LA55_5<=MINUS)||LA55_5==BAND||LA55_5==BOR||(LA55_5>=BXOR && LA55_5<=SL)) ) {s = 26;}

                        else if ( (LA55_5==QUESTION) ) {s = 27;}

                        else if ( (LA55_5==COLON) && (synpred5_Verilog())) {s = 28;}

                        else if ( (LA55_5==RBRACK) ) {s = 13;}

                         
                        input.seek(index55_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA55_7 = input.LA(1);

                         
                        int index55_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA55_7==LE||(LA55_7>=PLUS && LA55_7<=MINUS)||LA55_7==BAND||LA55_7==BOR||(LA55_7>=BXOR && LA55_7<=SL)) ) {s = 38;}

                        else if ( (LA55_7==QUESTION) ) {s = 39;}

                        else if ( (LA55_7==COLON) && (synpred5_Verilog())) {s = 40;}

                        else if ( (LA55_7==RBRACK) ) {s = 13;}

                         
                        input.seek(index55_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA55_10 = input.LA(1);

                         
                        int index55_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA55_11 = input.LA(1);

                         
                        int index55_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA55_14 = input.LA(1);

                         
                        int index55_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_14);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA55_15 = input.LA(1);

                         
                        int index55_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_15);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA55_18 = input.LA(1);

                         
                        int index55_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_18);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA55_19 = input.LA(1);

                         
                        int index55_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_19);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA55_20 = input.LA(1);

                         
                        int index55_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_20);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA55_21 = input.LA(1);

                         
                        int index55_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_21);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA55_22 = input.LA(1);

                         
                        int index55_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_22);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA55_25 = input.LA(1);

                         
                        int index55_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_25);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA55_26 = input.LA(1);

                         
                        int index55_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_26);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA55_27 = input.LA(1);

                         
                        int index55_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_27);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA55_30 = input.LA(1);

                         
                        int index55_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_30);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA55_31 = input.LA(1);

                         
                        int index55_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_31);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA55_32 = input.LA(1);

                         
                        int index55_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_32);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA55_33 = input.LA(1);

                         
                        int index55_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_33);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA55_34 = input.LA(1);

                         
                        int index55_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_34);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA55_35 = input.LA(1);

                         
                        int index55_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_35);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA55_36 = input.LA(1);

                         
                        int index55_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_36);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA55_37 = input.LA(1);

                         
                        int index55_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_37);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA55_38 = input.LA(1);

                         
                        int index55_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_38);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA55_39 = input.LA(1);

                         
                        int index55_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_39);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA55_42 = input.LA(1);

                         
                        int index55_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_42);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA55_43 = input.LA(1);

                         
                        int index55_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_43);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA55_44 = input.LA(1);

                         
                        int index55_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_44);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA55_45 = input.LA(1);

                         
                        int index55_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_45);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA55_46 = input.LA(1);

                         
                        int index55_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_46);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA55_47 = input.LA(1);

                         
                        int index55_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_47);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA55_48 = input.LA(1);

                         
                        int index55_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_48);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA55_49 = input.LA(1);

                         
                        int index55_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_49);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA55_50 = input.LA(1);

                         
                        int index55_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_50);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA55_51 = input.LA(1);

                         
                        int index55_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_51);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA55_52 = input.LA(1);

                         
                        int index55_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_52);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA55_53 = input.LA(1);

                         
                        int index55_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_53);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA55_54 = input.LA(1);

                         
                        int index55_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_54);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA55_55 = input.LA(1);

                         
                        int index55_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_55);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA55_56 = input.LA(1);

                         
                        int index55_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_56);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA55_57 = input.LA(1);

                         
                        int index55_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Verilog()) ) {s = 40;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index55_57);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 55, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA57_eotS =
        "\16\uffff";
    static final String DFA57_eofS =
        "\16\uffff";
    static final String DFA57_minS =
        "\2\5\14\uffff";
    static final String DFA57_maxS =
        "\1\64\1\162\14\uffff";
    static final String DFA57_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\11\uffff";
    static final String DFA57_specialS =
        "\16\uffff}>";
    static final String[] DFA57_transitionS = {
            "\1\1\14\uffff\2\2\40\uffff\1\2",
            "\1\2\3\uffff\1\2\5\uffff\1\2\3\uffff\1\2\2\uffff\1\2\2\uffff"+
            "\4\2\1\uffff\10\2\16\uffff\1\2\46\uffff\1\4\3\uffff\1\4\13\uffff"+
            "\10\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA57_eot = DFA.unpackEncodedString(DFA57_eotS);
    static final short[] DFA57_eof = DFA.unpackEncodedString(DFA57_eofS);
    static final char[] DFA57_min = DFA.unpackEncodedStringToUnsignedChars(DFA57_minS);
    static final char[] DFA57_max = DFA.unpackEncodedStringToUnsignedChars(DFA57_maxS);
    static final short[] DFA57_accept = DFA.unpackEncodedString(DFA57_acceptS);
    static final short[] DFA57_special = DFA.unpackEncodedString(DFA57_specialS);
    static final short[][] DFA57_transition;

    static {
        int numStates = DFA57_transitionS.length;
        DFA57_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA57_transition[i] = DFA.unpackEncodedString(DFA57_transitionS[i]);
        }
    }

    class DFA57 extends DFA {

        public DFA57(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 57;
            this.eot = DFA57_eot;
            this.eof = DFA57_eof;
            this.min = DFA57_min;
            this.max = DFA57_max;
            this.accept = DFA57_accept;
            this.special = DFA57_special;
            this.transition = DFA57_transition;
        }
        public String getDescription() {
            return "403:12: ( drive_strength )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA65_eotS =
        "\16\uffff";
    static final String DFA65_eofS =
        "\16\uffff";
    static final String DFA65_minS =
        "\2\5\14\uffff";
    static final String DFA65_maxS =
        "\1\64\1\162\14\uffff";
    static final String DFA65_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\11\uffff";
    static final String DFA65_specialS =
        "\16\uffff}>";
    static final String[] DFA65_transitionS = {
            "\1\1\14\uffff\2\2\40\uffff\1\2",
            "\1\2\3\uffff\1\2\5\uffff\1\2\3\uffff\1\2\2\uffff\1\2\2\uffff"+
            "\4\2\1\uffff\10\2\16\uffff\1\2\46\uffff\1\4\3\uffff\1\4\13\uffff"+
            "\10\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA65_eot = DFA.unpackEncodedString(DFA65_eotS);
    static final short[] DFA65_eof = DFA.unpackEncodedString(DFA65_eofS);
    static final char[] DFA65_min = DFA.unpackEncodedStringToUnsignedChars(DFA65_minS);
    static final char[] DFA65_max = DFA.unpackEncodedStringToUnsignedChars(DFA65_maxS);
    static final short[] DFA65_accept = DFA.unpackEncodedString(DFA65_acceptS);
    static final short[] DFA65_special = DFA.unpackEncodedString(DFA65_specialS);
    static final short[][] DFA65_transition;

    static {
        int numStates = DFA65_transitionS.length;
        DFA65_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA65_transition[i] = DFA.unpackEncodedString(DFA65_transitionS[i]);
        }
    }

    class DFA65 extends DFA {

        public DFA65(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 65;
            this.eot = DFA65_eot;
            this.eof = DFA65_eof;
            this.min = DFA65_min;
            this.max = DFA65_max;
            this.accept = DFA65_accept;
            this.special = DFA65_special;
            this.transition = DFA65_transition;
        }
        public String getDescription() {
            return "452:14: ( drive_strength )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA75_eotS =
        "\14\uffff";
    static final String DFA75_eofS =
        "\14\uffff";
    static final String DFA75_minS =
        "\1\5\13\uffff";
    static final String DFA75_maxS =
        "\1\64\13\uffff";
    static final String DFA75_acceptS =
        "\1\uffff\1\1\11\uffff\1\2";
    static final String DFA75_specialS =
        "\14\uffff}>";
    static final String[] DFA75_transitionS = {
            "\3\1\1\13\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2\uffff\4"+
            "\1\1\uffff\10\1\16\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA75_eot = DFA.unpackEncodedString(DFA75_eotS);
    static final short[] DFA75_eof = DFA.unpackEncodedString(DFA75_eofS);
    static final char[] DFA75_min = DFA.unpackEncodedStringToUnsignedChars(DFA75_minS);
    static final char[] DFA75_max = DFA.unpackEncodedStringToUnsignedChars(DFA75_maxS);
    static final short[] DFA75_accept = DFA.unpackEncodedString(DFA75_acceptS);
    static final short[] DFA75_special = DFA.unpackEncodedString(DFA75_specialS);
    static final short[][] DFA75_transition;

    static {
        int numStates = DFA75_transitionS.length;
        DFA75_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
        }
    }

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = DFA75_eot;
            this.eof = DFA75_eof;
            this.min = DFA75_min;
            this.max = DFA75_max;
            this.accept = DFA75_accept;
            this.special = DFA75_special;
            this.transition = DFA75_transition;
        }
        public String getDescription() {
            return "483:1: list_of_module_connections : ( module_port_connection ( COMMA module_port_connection )* | named_port_connection ( COMMA named_port_connection )* );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA76_eotS =
        "\13\uffff";
    static final String DFA76_eofS =
        "\13\uffff";
    static final String DFA76_minS =
        "\1\5\12\uffff";
    static final String DFA76_maxS =
        "\1\64\12\uffff";
    static final String DFA76_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\1\uffff";
    static final String DFA76_specialS =
        "\13\uffff}>";
    static final String[] DFA76_transitionS = {
            "\1\1\2\11\1\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2"+
            "\uffff\4\1\1\uffff\10\1\16\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA76_eot = DFA.unpackEncodedString(DFA76_eotS);
    static final short[] DFA76_eof = DFA.unpackEncodedString(DFA76_eofS);
    static final char[] DFA76_min = DFA.unpackEncodedStringToUnsignedChars(DFA76_minS);
    static final char[] DFA76_max = DFA.unpackEncodedStringToUnsignedChars(DFA76_maxS);
    static final short[] DFA76_accept = DFA.unpackEncodedString(DFA76_acceptS);
    static final short[] DFA76_special = DFA.unpackEncodedString(DFA76_specialS);
    static final short[][] DFA76_transition;

    static {
        int numStates = DFA76_transitionS.length;
        DFA76_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA76_transition[i] = DFA.unpackEncodedString(DFA76_transitionS[i]);
        }
    }

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = DFA76_eot;
            this.eof = DFA76_eof;
            this.min = DFA76_min;
            this.max = DFA76_max;
            this.accept = DFA76_accept;
            this.special = DFA76_special;
            this.transition = DFA76_transition;
        }
        public String getDescription() {
            return "488:1: module_port_connection : ( expression | );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA77_eotS =
        "\12\uffff";
    static final String DFA77_eofS =
        "\12\uffff";
    static final String DFA77_minS =
        "\1\5\11\uffff";
    static final String DFA77_maxS =
        "\1\64\11\uffff";
    static final String DFA77_acceptS =
        "\1\uffff\1\1\7\uffff\1\2";
    static final String DFA77_specialS =
        "\12\uffff}>";
    static final String[] DFA77_transitionS = {
            "\1\1\1\uffff\1\11\1\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff"+
            "\1\1\2\uffff\4\1\1\uffff\10\1\16\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA77_eot = DFA.unpackEncodedString(DFA77_eotS);
    static final short[] DFA77_eof = DFA.unpackEncodedString(DFA77_eofS);
    static final char[] DFA77_min = DFA.unpackEncodedStringToUnsignedChars(DFA77_minS);
    static final char[] DFA77_max = DFA.unpackEncodedStringToUnsignedChars(DFA77_maxS);
    static final short[] DFA77_accept = DFA.unpackEncodedString(DFA77_acceptS);
    static final short[] DFA77_special = DFA.unpackEncodedString(DFA77_specialS);
    static final short[][] DFA77_transition;

    static {
        int numStates = DFA77_transitionS.length;
        DFA77_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA77_transition[i] = DFA.unpackEncodedString(DFA77_transitionS[i]);
        }
    }

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = DFA77_eot;
            this.eof = DFA77_eof;
            this.min = DFA77_min;
            this.max = DFA77_max;
            this.accept = DFA77_accept;
            this.special = DFA77_special;
            this.transition = DFA77_transition;
        }
        public String getDescription() {
            return "496:31: ( expression )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA78_eotS =
        "\26\uffff";
    static final String DFA78_eofS =
        "\26\uffff";
    static final String DFA78_minS =
        "\1\4\25\uffff";
    static final String DFA78_maxS =
        "\1\u00a1\25\uffff";
    static final String DFA78_acceptS =
        "\1\uffff\24\1\1\2";
    static final String DFA78_specialS =
        "\1\0\25\uffff}>";
    static final String[] DFA78_transitionS = {
            "\1\25\4\uffff\1\2\7\uffff\1\21\1\11\1\1\1\uffff\1\14\1\17\35"+
            "\uffff\1\1\1\12\130\uffff\1\3\2\uffff\3\4\1\uffff\1\5\1\6\1"+
            "\7\1\10\1\13\1\20\1\15\1\uffff\1\16\1\uffff\1\22\1\23\1\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA78_eot = DFA.unpackEncodedString(DFA78_eotS);
    static final short[] DFA78_eof = DFA.unpackEncodedString(DFA78_eofS);
    static final char[] DFA78_min = DFA.unpackEncodedStringToUnsignedChars(DFA78_minS);
    static final char[] DFA78_max = DFA.unpackEncodedStringToUnsignedChars(DFA78_maxS);
    static final short[] DFA78_accept = DFA.unpackEncodedString(DFA78_acceptS);
    static final short[] DFA78_special = DFA.unpackEncodedString(DFA78_specialS);
    static final short[][] DFA78_transition;

    static {
        int numStates = DFA78_transitionS.length;
        DFA78_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA78_transition[i] = DFA.unpackEncodedString(DFA78_transitionS[i]);
        }
    }

    class DFA78 extends DFA {

        public DFA78(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 78;
            this.eot = DFA78_eot;
            this.eof = DFA78_eof;
            this.min = DFA78_min;
            this.max = DFA78_max;
            this.accept = DFA78_accept;
            this.special = DFA78_special;
            this.transition = DFA78_transition;
        }
        public String getDescription() {
            return "511:1: statement_or_null : ( ( statement )=> statement | SEMI );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA78_0 = input.LA(1);

                         
                        int index78_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA78_0==IDENTIFIER||LA78_0==ESCAPED_IDENTIFIER) && (synpred6_Verilog())) {s = 1;}

                        else if ( (LA78_0==LCURLY) && (synpred6_Verilog())) {s = 2;}

                        else if ( (LA78_0==142) && (synpred6_Verilog())) {s = 3;}

                        else if ( ((LA78_0>=145 && LA78_0<=147)) && (synpred6_Verilog())) {s = 4;}

                        else if ( (LA78_0==149) && (synpred6_Verilog())) {s = 5;}

                        else if ( (LA78_0==150) && (synpred6_Verilog())) {s = 6;}

                        else if ( (LA78_0==151) && (synpred6_Verilog())) {s = 7;}

                        else if ( (LA78_0==152) && (synpred6_Verilog())) {s = 8;}

                        else if ( (LA78_0==POUND) && (synpred6_Verilog())) {s = 9;}

                        else if ( (LA78_0==AT) && (synpred6_Verilog())) {s = 10;}

                        else if ( (LA78_0==153) && (synpred6_Verilog())) {s = 11;}

                        else if ( (LA78_0==TRIGGER) && (synpred6_Verilog())) {s = 12;}

                        else if ( (LA78_0==155) && (synpred6_Verilog())) {s = 13;}

                        else if ( (LA78_0==157) && (synpred6_Verilog())) {s = 14;}

                        else if ( (LA78_0==SYSTEM_TASK_NAME) && (synpred6_Verilog())) {s = 15;}

                        else if ( (LA78_0==154) && (synpred6_Verilog())) {s = 16;}

                        else if ( (LA78_0==KW_ASSIGN) && (synpred6_Verilog())) {s = 17;}

                        else if ( (LA78_0==159) && (synpred6_Verilog())) {s = 18;}

                        else if ( (LA78_0==160) && (synpred6_Verilog())) {s = 19;}

                        else if ( (LA78_0==161) && (synpred6_Verilog())) {s = 20;}

                        else if ( (LA78_0==SEMI) ) {s = 21;}

                         
                        input.seek(index78_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 78, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA79_eotS =
        "\141\uffff";
    static final String DFA79_eofS =
        "\141\uffff";
    static final String DFA79_minS =
        "\1\11\1\4\1\5\22\uffff\1\23\1\5\4\uffff\2\6\3\5\1\6\2\5\76\0";
    static final String DFA79_maxS =
        "\1\u00a1\1\24\1\64\22\uffff\2\64\4\uffff\4\63\1\64\1\63\2\64\76"+
        "\0";
    static final String DFA79_acceptS =
        "\3\uffff\1\3\1\4\1\5\3\uffff\1\6\1\uffff\1\7\1\10\1\11\1\12\1\14"+
        "\1\15\1\16\5\uffff\1\1\1\2\1\13\107\uffff";
    static final String DFA79_specialS =
        "\1\uffff\1\0\41\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
        "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
        "\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44"+
        "\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61"+
        "\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76}>";
    static final String[] DFA79_transitionS = {
            "\1\2\7\uffff\1\21\1\11\1\1\1\uffff\1\14\1\17\35\uffff\1\1\1"+
            "\11\130\uffff\1\3\2\uffff\3\4\1\uffff\4\5\1\13\1\20\1\15\1\uffff"+
            "\1\16\1\uffff\3\21",
            "\2\31\2\uffff\1\25\2\uffff\1\26\2\uffff\1\27\5\uffff\1\30",
            "\1\41\3\uffff\1\37\5\uffff\1\34\3\uffff\1\35\2\uffff\1\36"+
            "\2\uffff\2\42\1\33\1\40\1\uffff\10\42\16\uffff\1\35",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\43\40\uffff\1\43",
            "\1\52\3\uffff\1\50\5\uffff\1\45\3\uffff\1\46\2\uffff\1\47"+
            "\2\uffff\2\53\1\44\1\51\1\uffff\10\53\16\uffff\1\46",
            "",
            "",
            "",
            "",
            "\1\57\2\uffff\1\56\1\60\11\uffff\1\54\4\uffff\2\54\2\uffff"+
            "\1\55\2\uffff\1\54\1\uffff\1\54\1\uffff\20\54",
            "\1\64\2\uffff\1\63\1\65\11\uffff\1\61\4\uffff\2\61\2\uffff"+
            "\1\62\2\uffff\1\61\1\uffff\1\61\1\uffff\20\61",
            "\1\66\1\74\1\uffff\1\67\1\73\1\75\1\70\10\uffff\1\71\4\uffff"+
            "\2\71\2\uffff\1\72\2\uffff\1\71\1\uffff\1\71\1\uffff\20\71",
            "\1\76\1\102\2\uffff\1\101\1\103\11\uffff\1\77\4\uffff\2\77"+
            "\2\uffff\1\100\2\uffff\1\77\1\uffff\1\77\1\uffff\20\77",
            "\1\112\3\uffff\1\110\5\uffff\1\105\3\uffff\1\106\2\uffff\1"+
            "\107\2\uffff\2\113\1\104\1\111\1\uffff\10\113\16\uffff\1\106",
            "\1\117\2\uffff\1\116\1\120\11\uffff\1\114\4\uffff\2\114\2"+
            "\uffff\1\115\2\uffff\1\114\1\uffff\1\114\1\uffff\20\114",
            "\1\127\3\uffff\1\125\5\uffff\1\122\3\uffff\1\123\2\uffff\1"+
            "\124\2\uffff\2\130\1\121\1\126\1\uffff\10\130\16\uffff\1\123",
            "\1\137\3\uffff\1\135\5\uffff\1\132\3\uffff\1\133\2\uffff\1"+
            "\134\2\uffff\2\140\1\131\1\136\1\uffff\10\140\16\uffff\1\133",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA79_eot = DFA.unpackEncodedString(DFA79_eotS);
    static final short[] DFA79_eof = DFA.unpackEncodedString(DFA79_eofS);
    static final char[] DFA79_min = DFA.unpackEncodedStringToUnsignedChars(DFA79_minS);
    static final char[] DFA79_max = DFA.unpackEncodedStringToUnsignedChars(DFA79_maxS);
    static final short[] DFA79_accept = DFA.unpackEncodedString(DFA79_acceptS);
    static final short[] DFA79_special = DFA.unpackEncodedString(DFA79_specialS);
    static final short[][] DFA79_transition;

    static {
        int numStates = DFA79_transitionS.length;
        DFA79_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA79_transition[i] = DFA.unpackEncodedString(DFA79_transitionS[i]);
        }
    }

    class DFA79 extends DFA {

        public DFA79(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 79;
            this.eot = DFA79_eot;
            this.eof = DFA79_eof;
            this.min = DFA79_min;
            this.max = DFA79_max;
            this.accept = DFA79_accept;
            this.special = DFA79_special;
            this.transition = DFA79_transition;
        }
        public String getDescription() {
            return "516:1: statement : ( ( lvalue ASSIGN )=> blocking_assignment SEMI | ( lvalue LE )=> non_blocking_assignment SEMI | conditional_statement | case_statement | loop_statement | procedural_timing_control_statement | wait_statement | event_trigger | seq_block | par_block | task_enable | system_task_enable | disable_statement | procedural_continuous_assignment );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA79_1 = input.LA(1);

                         
                        int index79_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA79_1==DOT) ) {s = 21;}

                        else if ( (LA79_1==LBRACK) ) {s = 22;}

                        else if ( (LA79_1==ASSIGN) && (synpred7_Verilog())) {s = 23;}

                        else if ( (LA79_1==LE) && (synpred8_Verilog())) {s = 24;}

                        else if ( ((LA79_1>=SEMI && LA79_1<=LPAREN)) ) {s = 25;}

                         
                        input.seek(index79_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA79_35 = input.LA(1);

                         
                        int index79_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_35);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA79_36 = input.LA(1);

                         
                        int index79_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_36);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA79_37 = input.LA(1);

                         
                        int index79_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_37);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA79_38 = input.LA(1);

                         
                        int index79_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_38);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA79_39 = input.LA(1);

                         
                        int index79_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_39);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA79_40 = input.LA(1);

                         
                        int index79_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_40);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA79_41 = input.LA(1);

                         
                        int index79_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_41);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA79_42 = input.LA(1);

                         
                        int index79_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_42);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA79_43 = input.LA(1);

                         
                        int index79_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_43);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA79_44 = input.LA(1);

                         
                        int index79_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_44);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA79_45 = input.LA(1);

                         
                        int index79_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_45);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA79_46 = input.LA(1);

                         
                        int index79_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_46);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA79_47 = input.LA(1);

                         
                        int index79_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_47);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA79_48 = input.LA(1);

                         
                        int index79_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_48);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA79_49 = input.LA(1);

                         
                        int index79_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_49);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA79_50 = input.LA(1);

                         
                        int index79_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_50);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA79_51 = input.LA(1);

                         
                        int index79_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_51);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA79_52 = input.LA(1);

                         
                        int index79_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_52);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA79_53 = input.LA(1);

                         
                        int index79_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_53);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA79_54 = input.LA(1);

                         
                        int index79_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_54);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA79_55 = input.LA(1);

                         
                        int index79_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_55);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA79_56 = input.LA(1);

                         
                        int index79_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_56);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA79_57 = input.LA(1);

                         
                        int index79_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_57);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA79_58 = input.LA(1);

                         
                        int index79_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_58);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA79_59 = input.LA(1);

                         
                        int index79_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_59);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA79_60 = input.LA(1);

                         
                        int index79_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_60);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA79_61 = input.LA(1);

                         
                        int index79_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_61);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA79_62 = input.LA(1);

                         
                        int index79_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_62);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA79_63 = input.LA(1);

                         
                        int index79_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_63);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA79_64 = input.LA(1);

                         
                        int index79_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_64);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA79_65 = input.LA(1);

                         
                        int index79_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_65);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA79_66 = input.LA(1);

                         
                        int index79_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_66);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA79_67 = input.LA(1);

                         
                        int index79_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_67);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA79_68 = input.LA(1);

                         
                        int index79_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_68);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA79_69 = input.LA(1);

                         
                        int index79_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_69);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA79_70 = input.LA(1);

                         
                        int index79_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_70);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA79_71 = input.LA(1);

                         
                        int index79_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_71);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA79_72 = input.LA(1);

                         
                        int index79_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_72);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA79_73 = input.LA(1);

                         
                        int index79_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_73);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA79_74 = input.LA(1);

                         
                        int index79_74 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_74);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA79_75 = input.LA(1);

                         
                        int index79_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_75);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA79_76 = input.LA(1);

                         
                        int index79_76 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_76);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA79_77 = input.LA(1);

                         
                        int index79_77 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_77);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA79_78 = input.LA(1);

                         
                        int index79_78 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_78);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA79_79 = input.LA(1);

                         
                        int index79_79 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_79);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA79_80 = input.LA(1);

                         
                        int index79_80 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_80);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA79_81 = input.LA(1);

                         
                        int index79_81 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_81);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA79_82 = input.LA(1);

                         
                        int index79_82 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_82);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA79_83 = input.LA(1);

                         
                        int index79_83 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_83);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA79_84 = input.LA(1);

                         
                        int index79_84 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_84);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA79_85 = input.LA(1);

                         
                        int index79_85 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_85);
                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA79_86 = input.LA(1);

                         
                        int index79_86 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_86);
                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA79_87 = input.LA(1);

                         
                        int index79_87 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_87);
                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA79_88 = input.LA(1);

                         
                        int index79_88 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_88);
                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA79_89 = input.LA(1);

                         
                        int index79_89 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_89);
                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA79_90 = input.LA(1);

                         
                        int index79_90 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_90);
                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA79_91 = input.LA(1);

                         
                        int index79_91 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_91);
                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA79_92 = input.LA(1);

                         
                        int index79_92 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_92);
                        if ( s>=0 ) return s;
                        break;
                    case 59 : 
                        int LA79_93 = input.LA(1);

                         
                        int index79_93 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_93);
                        if ( s>=0 ) return s;
                        break;
                    case 60 : 
                        int LA79_94 = input.LA(1);

                         
                        int index79_94 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_94);
                        if ( s>=0 ) return s;
                        break;
                    case 61 : 
                        int LA79_95 = input.LA(1);

                         
                        int index79_95 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_95);
                        if ( s>=0 ) return s;
                        break;
                    case 62 : 
                        int LA79_96 = input.LA(1);

                         
                        int index79_96 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Verilog()) ) {s = 23;}

                        else if ( (synpred8_Verilog()) ) {s = 24;}

                         
                        input.seek(index79_96);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 79, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA80_eotS =
        "\13\uffff";
    static final String DFA80_eofS =
        "\13\uffff";
    static final String DFA80_minS =
        "\1\5\12\uffff";
    static final String DFA80_maxS =
        "\1\65\12\uffff";
    static final String DFA80_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\7\uffff";
    static final String DFA80_specialS =
        "\13\uffff}>";
    static final String[] DFA80_transitionS = {
            "\1\3\3\uffff\1\3\5\uffff\1\3\2\uffff\1\1\1\3\2\uffff\1\3\2"+
            "\uffff\4\3\1\uffff\10\3\16\uffff\1\3\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA80_eot = DFA.unpackEncodedString(DFA80_eotS);
    static final short[] DFA80_eof = DFA.unpackEncodedString(DFA80_eofS);
    static final char[] DFA80_min = DFA.unpackEncodedStringToUnsignedChars(DFA80_minS);
    static final char[] DFA80_max = DFA.unpackEncodedStringToUnsignedChars(DFA80_maxS);
    static final short[] DFA80_accept = DFA.unpackEncodedString(DFA80_acceptS);
    static final short[] DFA80_special = DFA.unpackEncodedString(DFA80_specialS);
    static final short[][] DFA80_transition;

    static {
        int numStates = DFA80_transitionS.length;
        DFA80_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA80_transition[i] = DFA.unpackEncodedString(DFA80_transitionS[i]);
        }
    }

    class DFA80 extends DFA {

        public DFA80(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 80;
            this.eot = DFA80_eot;
            this.eof = DFA80_eof;
            this.min = DFA80_min;
            this.max = DFA80_max;
            this.accept = DFA80_accept;
            this.special = DFA80_special;
            this.transition = DFA80_transition;
        }
        public String getDescription() {
            return "538:23: ( delay_or_event_control )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA81_eotS =
        "\13\uffff";
    static final String DFA81_eofS =
        "\13\uffff";
    static final String DFA81_minS =
        "\1\5\12\uffff";
    static final String DFA81_maxS =
        "\1\65\12\uffff";
    static final String DFA81_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\7\uffff";
    static final String DFA81_specialS =
        "\13\uffff}>";
    static final String[] DFA81_transitionS = {
            "\1\3\3\uffff\1\3\5\uffff\1\3\2\uffff\1\1\1\3\2\uffff\1\3\2"+
            "\uffff\4\3\1\uffff\10\3\16\uffff\1\3\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA81_eot = DFA.unpackEncodedString(DFA81_eotS);
    static final short[] DFA81_eof = DFA.unpackEncodedString(DFA81_eofS);
    static final char[] DFA81_min = DFA.unpackEncodedStringToUnsignedChars(DFA81_minS);
    static final char[] DFA81_max = DFA.unpackEncodedStringToUnsignedChars(DFA81_maxS);
    static final short[] DFA81_accept = DFA.unpackEncodedString(DFA81_acceptS);
    static final short[] DFA81_special = DFA.unpackEncodedString(DFA81_specialS);
    static final short[][] DFA81_transition;

    static {
        int numStates = DFA81_transitionS.length;
        DFA81_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA81_transition[i] = DFA.unpackEncodedString(DFA81_transitionS[i]);
        }
    }

    class DFA81 extends DFA {

        public DFA81(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 81;
            this.eot = DFA81_eot;
            this.eof = DFA81_eof;
            this.min = DFA81_min;
            this.max = DFA81_max;
            this.accept = DFA81_accept;
            this.special = DFA81_special;
            this.transition = DFA81_transition;
        }
        public String getDescription() {
            return "542:19: ( delay_or_event_control )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA82_eotS =
        "\66\uffff";
    static final String DFA82_eofS =
        "\1\2\65\uffff";
    static final String DFA82_minS =
        "\1\5\65\uffff";
    static final String DFA82_maxS =
        "\1\u00b3\65\uffff";
    static final String DFA82_acceptS =
        "\1\uffff\1\1\1\2\63\uffff";
    static final String DFA82_specialS =
        "\66\uffff}>";
    static final String[] DFA82_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\5\2\1\uffff\2\2\2\uffff\4\2\1\uffff"+
            "\10\2\16\uffff\2\2\17\uffff\1\2\2\uffff\1\2\5\uffff\24\2\2\uffff"+
            "\4\2\13\uffff\34\2\1\1\23\2\17\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA82_eot = DFA.unpackEncodedString(DFA82_eotS);
    static final short[] DFA82_eof = DFA.unpackEncodedString(DFA82_eofS);
    static final char[] DFA82_min = DFA.unpackEncodedStringToUnsignedChars(DFA82_minS);
    static final char[] DFA82_max = DFA.unpackEncodedStringToUnsignedChars(DFA82_maxS);
    static final short[] DFA82_accept = DFA.unpackEncodedString(DFA82_acceptS);
    static final short[] DFA82_special = DFA.unpackEncodedString(DFA82_specialS);
    static final short[][] DFA82_transition;

    static {
        int numStates = DFA82_transitionS.length;
        DFA82_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA82_transition[i] = DFA.unpackEncodedString(DFA82_transitionS[i]);
        }
    }

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = DFA82_eot;
            this.eof = DFA82_eof;
            this.min = DFA82_min;
            this.max = DFA82_max;
            this.accept = DFA82_accept;
            this.special = DFA82_special;
            this.transition = DFA82_transition;
        }
        public String getDescription() {
            return "549:9: ( 'else' statement_or_null )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA83_eotS =
        "\13\uffff";
    static final String DFA83_eofS =
        "\13\uffff";
    static final String DFA83_minS =
        "\1\5\12\uffff";
    static final String DFA83_maxS =
        "\1\u0094\12\uffff";
    static final String DFA83_acceptS =
        "\1\uffff\1\2\1\1\10\uffff";
    static final String DFA83_specialS =
        "\13\uffff}>";
    static final String[] DFA83_transitionS = {
            "\1\2\3\uffff\1\2\5\uffff\1\2\3\uffff\1\2\2\uffff\1\2\2\uffff"+
            "\4\2\1\uffff\10\2\16\uffff\1\2\133\uffff\1\1\3\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA83_eot = DFA.unpackEncodedString(DFA83_eotS);
    static final short[] DFA83_eof = DFA.unpackEncodedString(DFA83_eofS);
    static final char[] DFA83_min = DFA.unpackEncodedStringToUnsignedChars(DFA83_minS);
    static final char[] DFA83_max = DFA.unpackEncodedStringToUnsignedChars(DFA83_maxS);
    static final short[] DFA83_accept = DFA.unpackEncodedString(DFA83_acceptS);
    static final short[] DFA83_special = DFA.unpackEncodedString(DFA83_specialS);
    static final short[][] DFA83_transition;

    static {
        int numStates = DFA83_transitionS.length;
        DFA83_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA83_transition[i] = DFA.unpackEncodedString(DFA83_transitionS[i]);
        }
    }

    class DFA83 extends DFA {

        public DFA83(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 83;
            this.eot = DFA83_eot;
            this.eof = DFA83_eof;
            this.min = DFA83_min;
            this.max = DFA83_max;
            this.accept = DFA83_accept;
            this.special = DFA83_special;
            this.transition = DFA83_transition;
        }
        public String getDescription() {
            return "()+ loopback of 553:47: ( case_item )+";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA86_eotS =
        "\12\uffff";
    static final String DFA86_eofS =
        "\12\uffff";
    static final String DFA86_minS =
        "\1\5\11\uffff";
    static final String DFA86_maxS =
        "\1\u0094\11\uffff";
    static final String DFA86_acceptS =
        "\1\uffff\1\1\7\uffff\1\2";
    static final String DFA86_specialS =
        "\12\uffff}>";
    static final String[] DFA86_transitionS = {
            "\1\1\3\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2\uffff"+
            "\4\1\1\uffff\10\1\16\uffff\1\1\137\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA86_eot = DFA.unpackEncodedString(DFA86_eotS);
    static final short[] DFA86_eof = DFA.unpackEncodedString(DFA86_eofS);
    static final char[] DFA86_min = DFA.unpackEncodedStringToUnsignedChars(DFA86_minS);
    static final char[] DFA86_max = DFA.unpackEncodedStringToUnsignedChars(DFA86_maxS);
    static final short[] DFA86_accept = DFA.unpackEncodedString(DFA86_acceptS);
    static final short[] DFA86_special = DFA.unpackEncodedString(DFA86_specialS);
    static final short[][] DFA86_transition;

    static {
        int numStates = DFA86_transitionS.length;
        DFA86_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA86_transition[i] = DFA.unpackEncodedString(DFA86_transitionS[i]);
        }
    }

    class DFA86 extends DFA {

        public DFA86(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 86;
            this.eot = DFA86_eot;
            this.eof = DFA86_eof;
            this.min = DFA86_min;
            this.max = DFA86_max;
            this.accept = DFA86_accept;
            this.special = DFA86_special;
            this.transition = DFA86_transition;
        }
        public String getDescription() {
            return "560:1: case_item : ( expression ( COMMA expression )* COLON statement_or_null | 'default' ( COLON )? statement_or_null );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA85_eotS =
        "\27\uffff";
    static final String DFA85_eofS =
        "\27\uffff";
    static final String DFA85_minS =
        "\1\4\26\uffff";
    static final String DFA85_maxS =
        "\1\u00a1\26\uffff";
    static final String DFA85_acceptS =
        "\1\uffff\1\1\1\2\24\uffff";
    static final String DFA85_specialS =
        "\27\uffff}>";
    static final String[] DFA85_transitionS = {
            "\1\2\4\uffff\1\2\2\uffff\1\1\4\uffff\3\2\1\uffff\2\2\35\uffff"+
            "\2\2\130\uffff\1\2\2\uffff\3\2\1\uffff\7\2\1\uffff\1\2\1\uffff"+
            "\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA85_eot = DFA.unpackEncodedString(DFA85_eotS);
    static final short[] DFA85_eof = DFA.unpackEncodedString(DFA85_eofS);
    static final char[] DFA85_min = DFA.unpackEncodedStringToUnsignedChars(DFA85_minS);
    static final char[] DFA85_max = DFA.unpackEncodedStringToUnsignedChars(DFA85_maxS);
    static final short[] DFA85_accept = DFA.unpackEncodedString(DFA85_acceptS);
    static final short[] DFA85_special = DFA.unpackEncodedString(DFA85_specialS);
    static final short[][] DFA85_transition;

    static {
        int numStates = DFA85_transitionS.length;
        DFA85_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA85_transition[i] = DFA.unpackEncodedString(DFA85_transitionS[i]);
        }
    }

    class DFA85 extends DFA {

        public DFA85(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 85;
            this.eot = DFA85_eot;
            this.eof = DFA85_eof;
            this.min = DFA85_min;
            this.max = DFA85_max;
            this.accept = DFA85_accept;
            this.special = DFA85_special;
            this.transition = DFA85_transition;
        }
        public String getDescription() {
            return "562:19: ( COLON )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA89_eotS =
        "\27\uffff";
    static final String DFA89_eofS =
        "\27\uffff";
    static final String DFA89_minS =
        "\1\11\26\uffff";
    static final String DFA89_maxS =
        "\1\u00a1\26\uffff";
    static final String DFA89_acceptS =
        "\1\uffff\1\1\1\2\24\uffff";
    static final String DFA89_specialS =
        "\27\uffff}>";
    static final String[] DFA89_transitionS = {
            "\1\2\2\uffff\1\1\4\uffff\3\2\1\uffff\2\2\35\uffff\2\2\130\uffff"+
            "\1\2\2\uffff\3\2\1\uffff\11\2\1\uffff\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA89_eot = DFA.unpackEncodedString(DFA89_eotS);
    static final short[] DFA89_eof = DFA.unpackEncodedString(DFA89_eofS);
    static final char[] DFA89_min = DFA.unpackEncodedStringToUnsignedChars(DFA89_minS);
    static final char[] DFA89_max = DFA.unpackEncodedStringToUnsignedChars(DFA89_maxS);
    static final short[] DFA89_accept = DFA.unpackEncodedString(DFA89_acceptS);
    static final short[] DFA89_special = DFA.unpackEncodedString(DFA89_specialS);
    static final short[][] DFA89_transition;

    static {
        int numStates = DFA89_transitionS.length;
        DFA89_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA89_transition[i] = DFA.unpackEncodedString(DFA89_transitionS[i]);
        }
    }

    class DFA89 extends DFA {

        public DFA89(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 89;
            this.eot = DFA89_eot;
            this.eof = DFA89_eof;
            this.min = DFA89_min;
            this.max = DFA89_max;
            this.accept = DFA89_accept;
            this.special = DFA89_special;
            this.transition = DFA89_transition;
        }
        public String getDescription() {
            return "590:9: ( COLON name_of_block ( block_declaration )* )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA88_eotS =
        "\34\uffff";
    static final String DFA88_eofS =
        "\34\uffff";
    static final String DFA88_minS =
        "\1\11\33\uffff";
    static final String DFA88_maxS =
        "\1\u00a1\33\uffff";
    static final String DFA88_acceptS =
        "\1\uffff\1\2\24\uffff\1\1\5\uffff";
    static final String DFA88_specialS =
        "\34\uffff}>";
    static final String[] DFA88_transitionS = {
            "\1\1\7\uffff\3\1\1\uffff\2\1\35\uffff\2\1\34\uffff\3\26\17"+
            "\uffff\3\26\47\uffff\1\1\2\uffff\3\1\1\uffff\11\1\1\uffff\3"+
            "\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA88_eot = DFA.unpackEncodedString(DFA88_eotS);
    static final short[] DFA88_eof = DFA.unpackEncodedString(DFA88_eofS);
    static final char[] DFA88_min = DFA.unpackEncodedStringToUnsignedChars(DFA88_minS);
    static final char[] DFA88_max = DFA.unpackEncodedStringToUnsignedChars(DFA88_maxS);
    static final short[] DFA88_accept = DFA.unpackEncodedString(DFA88_acceptS);
    static final short[] DFA88_special = DFA.unpackEncodedString(DFA88_specialS);
    static final short[][] DFA88_transition;

    static {
        int numStates = DFA88_transitionS.length;
        DFA88_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA88_transition[i] = DFA.unpackEncodedString(DFA88_transitionS[i]);
        }
    }

    class DFA88 extends DFA {

        public DFA88(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 88;
            this.eot = DFA88_eot;
            this.eof = DFA88_eof;
            this.min = DFA88_min;
            this.max = DFA88_max;
            this.accept = DFA88_accept;
            this.special = DFA88_special;
            this.transition = DFA88_transition;
        }
        public String getDescription() {
            return "()* loopback of 590:31: ( block_declaration )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA90_eotS =
        "\26\uffff";
    static final String DFA90_eofS =
        "\26\uffff";
    static final String DFA90_minS =
        "\1\11\25\uffff";
    static final String DFA90_maxS =
        "\1\u00a1\25\uffff";
    static final String DFA90_acceptS =
        "\1\uffff\1\2\1\1\23\uffff";
    static final String DFA90_specialS =
        "\26\uffff}>";
    static final String[] DFA90_transitionS = {
            "\1\2\7\uffff\3\2\1\uffff\2\2\35\uffff\2\2\130\uffff\1\2\2\uffff"+
            "\3\2\1\uffff\7\2\1\1\1\2\1\uffff\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA90_eot = DFA.unpackEncodedString(DFA90_eotS);
    static final short[] DFA90_eof = DFA.unpackEncodedString(DFA90_eofS);
    static final char[] DFA90_min = DFA.unpackEncodedStringToUnsignedChars(DFA90_minS);
    static final char[] DFA90_max = DFA.unpackEncodedStringToUnsignedChars(DFA90_maxS);
    static final short[] DFA90_accept = DFA.unpackEncodedString(DFA90_acceptS);
    static final short[] DFA90_special = DFA.unpackEncodedString(DFA90_specialS);
    static final short[][] DFA90_transition;

    static {
        int numStates = DFA90_transitionS.length;
        DFA90_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA90_transition[i] = DFA.unpackEncodedString(DFA90_transitionS[i]);
        }
    }

    class DFA90 extends DFA {

        public DFA90(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 90;
            this.eot = DFA90_eot;
            this.eof = DFA90_eof;
            this.min = DFA90_min;
            this.max = DFA90_max;
            this.accept = DFA90_accept;
            this.special = DFA90_special;
            this.transition = DFA90_transition;
        }
        public String getDescription() {
            return "()* loopback of 591:9: ( statement )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA92_eotS =
        "\27\uffff";
    static final String DFA92_eofS =
        "\27\uffff";
    static final String DFA92_minS =
        "\1\11\26\uffff";
    static final String DFA92_maxS =
        "\1\u00a1\26\uffff";
    static final String DFA92_acceptS =
        "\1\uffff\1\1\1\2\24\uffff";
    static final String DFA92_specialS =
        "\27\uffff}>";
    static final String[] DFA92_transitionS = {
            "\1\2\2\uffff\1\1\4\uffff\3\2\1\uffff\2\2\35\uffff\2\2\130\uffff"+
            "\1\2\2\uffff\3\2\1\uffff\7\2\1\uffff\5\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA92_eot = DFA.unpackEncodedString(DFA92_eotS);
    static final short[] DFA92_eof = DFA.unpackEncodedString(DFA92_eofS);
    static final char[] DFA92_min = DFA.unpackEncodedStringToUnsignedChars(DFA92_minS);
    static final char[] DFA92_max = DFA.unpackEncodedStringToUnsignedChars(DFA92_maxS);
    static final short[] DFA92_accept = DFA.unpackEncodedString(DFA92_acceptS);
    static final short[] DFA92_special = DFA.unpackEncodedString(DFA92_specialS);
    static final short[][] DFA92_transition;

    static {
        int numStates = DFA92_transitionS.length;
        DFA92_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA92_transition[i] = DFA.unpackEncodedString(DFA92_transitionS[i]);
        }
    }

    class DFA92 extends DFA {

        public DFA92(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 92;
            this.eot = DFA92_eot;
            this.eof = DFA92_eof;
            this.min = DFA92_min;
            this.max = DFA92_max;
            this.accept = DFA92_accept;
            this.special = DFA92_special;
            this.transition = DFA92_transition;
        }
        public String getDescription() {
            return "597:9: ( COLON name_of_block ( block_declaration )* )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA91_eotS =
        "\34\uffff";
    static final String DFA91_eofS =
        "\34\uffff";
    static final String DFA91_minS =
        "\1\11\33\uffff";
    static final String DFA91_maxS =
        "\1\u00a1\33\uffff";
    static final String DFA91_acceptS =
        "\1\uffff\1\2\24\uffff\1\1\5\uffff";
    static final String DFA91_specialS =
        "\34\uffff}>";
    static final String[] DFA91_transitionS = {
            "\1\1\7\uffff\3\1\1\uffff\2\1\35\uffff\2\1\34\uffff\3\26\17"+
            "\uffff\3\26\47\uffff\1\1\2\uffff\3\1\1\uffff\7\1\1\uffff\5\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA91_eot = DFA.unpackEncodedString(DFA91_eotS);
    static final short[] DFA91_eof = DFA.unpackEncodedString(DFA91_eofS);
    static final char[] DFA91_min = DFA.unpackEncodedStringToUnsignedChars(DFA91_minS);
    static final char[] DFA91_max = DFA.unpackEncodedStringToUnsignedChars(DFA91_maxS);
    static final short[] DFA91_accept = DFA.unpackEncodedString(DFA91_acceptS);
    static final short[] DFA91_special = DFA.unpackEncodedString(DFA91_specialS);
    static final short[][] DFA91_transition;

    static {
        int numStates = DFA91_transitionS.length;
        DFA91_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA91_transition[i] = DFA.unpackEncodedString(DFA91_transitionS[i]);
        }
    }

    class DFA91 extends DFA {

        public DFA91(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 91;
            this.eot = DFA91_eot;
            this.eof = DFA91_eof;
            this.min = DFA91_min;
            this.max = DFA91_max;
            this.accept = DFA91_accept;
            this.special = DFA91_special;
            this.transition = DFA91_transition;
        }
        public String getDescription() {
            return "()* loopback of 597:31: ( block_declaration )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA93_eotS =
        "\26\uffff";
    static final String DFA93_eofS =
        "\26\uffff";
    static final String DFA93_minS =
        "\1\11\25\uffff";
    static final String DFA93_maxS =
        "\1\u00a1\25\uffff";
    static final String DFA93_acceptS =
        "\1\uffff\1\2\1\1\23\uffff";
    static final String DFA93_specialS =
        "\26\uffff}>";
    static final String[] DFA93_transitionS = {
            "\1\2\7\uffff\3\2\1\uffff\2\2\35\uffff\2\2\130\uffff\1\2\2\uffff"+
            "\3\2\1\uffff\7\2\1\uffff\1\2\1\1\3\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA93_eot = DFA.unpackEncodedString(DFA93_eotS);
    static final short[] DFA93_eof = DFA.unpackEncodedString(DFA93_eofS);
    static final char[] DFA93_min = DFA.unpackEncodedStringToUnsignedChars(DFA93_minS);
    static final char[] DFA93_max = DFA.unpackEncodedStringToUnsignedChars(DFA93_maxS);
    static final short[] DFA93_accept = DFA.unpackEncodedString(DFA93_acceptS);
    static final short[] DFA93_special = DFA.unpackEncodedString(DFA93_specialS);
    static final short[][] DFA93_transition;

    static {
        int numStates = DFA93_transitionS.length;
        DFA93_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA93_transition[i] = DFA.unpackEncodedString(DFA93_transitionS[i]);
        }
    }

    class DFA93 extends DFA {

        public DFA93(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 93;
            this.eot = DFA93_eot;
            this.eof = DFA93_eof;
            this.min = DFA93_min;
            this.max = DFA93_max;
            this.accept = DFA93_accept;
            this.special = DFA93_special;
            this.transition = DFA93_transition;
        }
        public String getDescription() {
            return "()* loopback of 598:9: ( statement )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA95_eotS =
        "\13\uffff";
    static final String DFA95_eofS =
        "\13\uffff";
    static final String DFA95_minS =
        "\1\5\12\uffff";
    static final String DFA95_maxS =
        "\1\64\12\uffff";
    static final String DFA95_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\1\uffff";
    static final String DFA95_specialS =
        "\13\uffff}>";
    static final String[] DFA95_transitionS = {
            "\1\1\2\11\1\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2"+
            "\uffff\4\1\1\uffff\10\1\16\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA95_eot = DFA.unpackEncodedString(DFA95_eotS);
    static final short[] DFA95_eof = DFA.unpackEncodedString(DFA95_eofS);
    static final char[] DFA95_min = DFA.unpackEncodedStringToUnsignedChars(DFA95_minS);
    static final char[] DFA95_max = DFA.unpackEncodedStringToUnsignedChars(DFA95_maxS);
    static final short[] DFA95_accept = DFA.unpackEncodedString(DFA95_acceptS);
    static final short[] DFA95_special = DFA.unpackEncodedString(DFA95_specialS);
    static final short[][] DFA95_transition;

    static {
        int numStates = DFA95_transitionS.length;
        DFA95_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA95_transition[i] = DFA.unpackEncodedString(DFA95_transitionS[i]);
        }
    }

    class DFA95 extends DFA {

        public DFA95(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 95;
            this.eot = DFA95_eot;
            this.eof = DFA95_eof;
            this.min = DFA95_min;
            this.max = DFA95_max;
            this.accept = DFA95_accept;
            this.special = DFA95_special;
            this.transition = DFA95_transition;
        }
        public String getDescription() {
            return "612:49: ( expression )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA98_eotS =
        "\13\uffff";
    static final String DFA98_eofS =
        "\13\uffff";
    static final String DFA98_minS =
        "\1\5\12\uffff";
    static final String DFA98_maxS =
        "\1\64\12\uffff";
    static final String DFA98_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\1\uffff";
    static final String DFA98_specialS =
        "\13\uffff}>";
    static final String[] DFA98_transitionS = {
            "\1\1\2\11\1\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2"+
            "\uffff\4\1\1\uffff\10\1\16\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA98_eot = DFA.unpackEncodedString(DFA98_eotS);
    static final short[] DFA98_eof = DFA.unpackEncodedString(DFA98_eofS);
    static final char[] DFA98_min = DFA.unpackEncodedStringToUnsignedChars(DFA98_minS);
    static final char[] DFA98_max = DFA.unpackEncodedStringToUnsignedChars(DFA98_maxS);
    static final short[] DFA98_accept = DFA.unpackEncodedString(DFA98_acceptS);
    static final short[] DFA98_special = DFA.unpackEncodedString(DFA98_specialS);
    static final short[][] DFA98_transition;

    static {
        int numStates = DFA98_transitionS.length;
        DFA98_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA98_transition[i] = DFA.unpackEncodedString(DFA98_transitionS[i]);
        }
    }

    class DFA98 extends DFA {

        public DFA98(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 98;
            this.eot = DFA98_eot;
            this.eof = DFA98_eof;
            this.min = DFA98_min;
            this.max = DFA98_max;
            this.accept = DFA98_accept;
            this.special = DFA98_special;
            this.transition = DFA98_transition;
        }
        public String getDescription() {
            return "617:53: ( expression )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA103_eotS =
        "\14\uffff";
    static final String DFA103_eofS =
        "\14\uffff";
    static final String DFA103_minS =
        "\1\5\13\uffff";
    static final String DFA103_maxS =
        "\1\u00ab\13\uffff";
    static final String DFA103_acceptS =
        "\1\uffff\1\2\1\1\11\uffff";
    static final String DFA103_specialS =
        "\14\uffff}>";
    static final String[] DFA103_transitionS = {
            "\1\2\u0088\uffff\1\2\24\uffff\1\1\10\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA103_eot = DFA.unpackEncodedString(DFA103_eotS);
    static final short[] DFA103_eof = DFA.unpackEncodedString(DFA103_eofS);
    static final char[] DFA103_min = DFA.unpackEncodedStringToUnsignedChars(DFA103_minS);
    static final char[] DFA103_max = DFA.unpackEncodedStringToUnsignedChars(DFA103_maxS);
    static final short[] DFA103_accept = DFA.unpackEncodedString(DFA103_acceptS);
    static final short[] DFA103_special = DFA.unpackEncodedString(DFA103_specialS);
    static final short[][] DFA103_transition;

    static {
        int numStates = DFA103_transitionS.length;
        DFA103_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA103_transition[i] = DFA.unpackEncodedString(DFA103_transitionS[i]);
        }
    }

    class DFA103 extends DFA {

        public DFA103(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 103;
            this.eot = DFA103_eot;
            this.eof = DFA103_eof;
            this.min = DFA103_min;
            this.max = DFA103_max;
            this.accept = DFA103_accept;
            this.special = DFA103_special;
            this.transition = DFA103_transition;
        }
        public String getDescription() {
            return "()* loopback of 638:19: ( specify_item )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA104_eotS =
        "\25\uffff";
    static final String DFA104_eofS =
        "\25\uffff";
    static final String DFA104_minS =
        "\1\5\2\uffff\1\5\7\uffff\1\5\10\0\1\uffff";
    static final String DFA104_maxS =
        "\1\u00ab\2\uffff\1\5\7\uffff\1\64\10\0\1\uffff";
    static final String DFA104_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\3\17\uffff\1\4";
    static final String DFA104_specialS =
        "\1\0\13\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff}>";
    static final String[] DFA104_transitionS = {
            "\1\2\u0088\uffff\1\3\25\uffff\1\1\7\4",
            "",
            "",
            "\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\3\uffff\1\20\5\uffff\1\15\3\uffff\1\16\2\uffff\1\17"+
            "\2\uffff\2\23\1\14\1\21\1\uffff\10\23\16\uffff\1\16",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA104_eot = DFA.unpackEncodedString(DFA104_eotS);
    static final short[] DFA104_eof = DFA.unpackEncodedString(DFA104_eofS);
    static final char[] DFA104_min = DFA.unpackEncodedStringToUnsignedChars(DFA104_minS);
    static final char[] DFA104_max = DFA.unpackEncodedStringToUnsignedChars(DFA104_maxS);
    static final short[] DFA104_accept = DFA.unpackEncodedString(DFA104_acceptS);
    static final short[] DFA104_special = DFA.unpackEncodedString(DFA104_specialS);
    static final short[][] DFA104_transition;

    static {
        int numStates = DFA104_transitionS.length;
        DFA104_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA104_transition[i] = DFA.unpackEncodedString(DFA104_transitionS[i]);
        }
    }

    class DFA104 extends DFA {

        public DFA104(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 104;
            this.eot = DFA104_eot;
            this.eof = DFA104_eof;
            this.min = DFA104_min;
            this.max = DFA104_max;
            this.accept = DFA104_accept;
            this.special = DFA104_special;
            this.transition = DFA104_transition;
        }
        public String getDescription() {
            return "641:1: specify_item : ( spec_param_declaration | ( path_declaration )=> path_declaration | system_timing_check | sdpd );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA104_0 = input.LA(1);

                         
                        int index104_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA104_0==164) ) {s = 1;}

                        else if ( (LA104_0==LPAREN) && (synpred9_Verilog())) {s = 2;}

                        else if ( (LA104_0==142) ) {s = 3;}

                        else if ( ((LA104_0>=165 && LA104_0<=171)) ) {s = 4;}

                         
                        input.seek(index104_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA104_12 = input.LA(1);

                         
                        int index104_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA104_13 = input.LA(1);

                         
                        int index104_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_13);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA104_14 = input.LA(1);

                         
                        int index104_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_14);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA104_15 = input.LA(1);

                         
                        int index104_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA104_16 = input.LA(1);

                         
                        int index104_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_16);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA104_17 = input.LA(1);

                         
                        int index104_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_17);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA104_18 = input.LA(1);

                         
                        int index104_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_18);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA104_19 = input.LA(1);

                         
                        int index104_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Verilog()) ) {s = 2;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index104_19);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 104, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA106_eotS =
        "\24\uffff";
    static final String DFA106_eofS =
        "\24\uffff";
    static final String DFA106_minS =
        "\1\5\1\23\1\5\1\6\1\uffff\1\5\3\0\1\uffff\11\0\1\uffff";
    static final String DFA106_maxS =
        "\1\u008e\1\u00ae\1\5\1\30\1\uffff\1\64\3\0\1\uffff\11\0\1\uffff";
    static final String DFA106_acceptS =
        "\4\uffff\1\3\4\uffff\1\1\11\uffff\1\2";
    static final String DFA106_specialS =
        "\3\uffff\1\0\2\uffff\1\1\1\2\1\3\1\uffff\1\4\1\5\1\6\1\7\1\10\1"+
        "\11\1\12\1\13\1\14\1\uffff}>";
    static final String[] DFA106_transitionS = {
            "\1\1\u0088\uffff\1\2",
            "\1\3\40\uffff\1\3\170\uffff\2\4",
            "\1\5",
            "\1\11\1\uffff\1\6\2\uffff\1\7\13\uffff\1\10\1\12",
            "",
            "\1\21\3\uffff\1\17\5\uffff\1\14\3\uffff\1\15\2\uffff\1\16"+
            "\2\uffff\2\22\1\13\1\20\1\uffff\10\22\16\uffff\1\15",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA106_eot = DFA.unpackEncodedString(DFA106_eotS);
    static final short[] DFA106_eof = DFA.unpackEncodedString(DFA106_eofS);
    static final char[] DFA106_min = DFA.unpackEncodedStringToUnsignedChars(DFA106_minS);
    static final char[] DFA106_max = DFA.unpackEncodedStringToUnsignedChars(DFA106_maxS);
    static final short[] DFA106_accept = DFA.unpackEncodedString(DFA106_acceptS);
    static final short[] DFA106_special = DFA.unpackEncodedString(DFA106_specialS);
    static final short[][] DFA106_transition;

    static {
        int numStates = DFA106_transitionS.length;
        DFA106_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA106_transition[i] = DFA.unpackEncodedString(DFA106_transitionS[i]);
        }
    }

    class DFA106 extends DFA {

        public DFA106(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 106;
            this.eot = DFA106_eot;
            this.eof = DFA106_eof;
            this.min = DFA106_min;
            this.max = DFA106_max;
            this.accept = DFA106_accept;
            this.special = DFA106_special;
            this.transition = DFA106_transition;
        }
        public String getDescription() {
            return "660:1: path_declaration : ( ( simple_path_declaration )=> simple_path_declaration SEMI | ( level_sensitive_path_declaration )=> level_sensitive_path_declaration SEMI | edge_sensitive_path_declaration SEMI );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA106_3 = input.LA(1);

                         
                        int index106_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA106_3==DOT) ) {s = 6;}

                        else if ( (LA106_3==LBRACK) ) {s = 7;}

                        else if ( (LA106_3==PPATH) ) {s = 8;}

                        else if ( (LA106_3==COMMA) && (synpred10_Verilog())) {s = 9;}

                        else if ( (LA106_3==FPATH) ) {s = 10;}

                         
                        input.seek(index106_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA106_6 = input.LA(1);

                         
                        int index106_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA106_7 = input.LA(1);

                         
                        int index106_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA106_8 = input.LA(1);

                         
                        int index106_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA106_10 = input.LA(1);

                         
                        int index106_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Verilog()) ) {s = 9;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA106_11 = input.LA(1);

                         
                        int index106_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_11);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA106_12 = input.LA(1);

                         
                        int index106_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA106_13 = input.LA(1);

                         
                        int index106_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA106_14 = input.LA(1);

                         
                        int index106_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA106_15 = input.LA(1);

                         
                        int index106_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA106_16 = input.LA(1);

                         
                        int index106_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_16);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA106_17 = input.LA(1);

                         
                        int index106_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA106_18 = input.LA(1);

                         
                        int index106_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Verilog()) ) {s = 19;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index106_18);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 106, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA109_eotS =
        "\27\uffff";
    static final String DFA109_eofS =
        "\1\uffff\1\4\25\uffff";
    static final String DFA109_minS =
        "\1\23\1\6\1\23\1\5\10\uffff\11\0\2\uffff";
    static final String DFA109_maxS =
        "\1\64\1\u00ac\2\64\10\uffff\11\0\2\uffff";
    static final String DFA109_acceptS =
        "\4\uffff\1\3\20\uffff\1\1\1\2";
    static final String DFA109_specialS =
        "\14\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\2\uffff}>";
    static final String[] DFA109_transitionS = {
            "\1\1\40\uffff\1\1",
            "\2\4\1\2\2\uffff\1\3\1\4\12\uffff\4\4\u0091\uffff\1\4",
            "\1\14\40\uffff\1\14",
            "\1\23\3\uffff\1\21\5\uffff\1\16\3\uffff\1\17\2\uffff\1\20"+
            "\2\uffff\2\24\1\15\1\22\1\uffff\10\24\16\uffff\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA109_eot = DFA.unpackEncodedString(DFA109_eotS);
    static final short[] DFA109_eof = DFA.unpackEncodedString(DFA109_eofS);
    static final char[] DFA109_min = DFA.unpackEncodedStringToUnsignedChars(DFA109_minS);
    static final char[] DFA109_max = DFA.unpackEncodedStringToUnsignedChars(DFA109_maxS);
    static final short[] DFA109_accept = DFA.unpackEncodedString(DFA109_acceptS);
    static final short[] DFA109_special = DFA.unpackEncodedString(DFA109_specialS);
    static final short[][] DFA109_transition;

    static {
        int numStates = DFA109_transitionS.length;
        DFA109_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA109_transition[i] = DFA.unpackEncodedString(DFA109_transitionS[i]);
        }
    }

    class DFA109 extends DFA {

        public DFA109(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 109;
            this.eot = DFA109_eot;
            this.eof = DFA109_eof;
            this.min = DFA109_min;
            this.max = DFA109_max;
            this.accept = DFA109_accept;
            this.special = DFA109_special;
            this.transition = DFA109_transition;
        }
        public String getDescription() {
            return "686:1: specify_terminal_descriptor : ( ( identifier LBRACK expression COLON )=> identifier LBRACK expression COLON expression RBRACK | ( identifier LBRACK )=> identifier LBRACK expression RBRACK | identifier );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA109_12 = input.LA(1);

                         
                        int index109_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index109_12);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA109_13 = input.LA(1);

                         
                        int index109_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_13);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA109_14 = input.LA(1);

                         
                        int index109_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_14);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA109_15 = input.LA(1);

                         
                        int index109_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_15);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA109_16 = input.LA(1);

                         
                        int index109_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_16);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA109_17 = input.LA(1);

                         
                        int index109_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_17);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA109_18 = input.LA(1);

                         
                        int index109_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_18);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA109_19 = input.LA(1);

                         
                        int index109_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_19);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA109_20 = input.LA(1);

                         
                        int index109_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Verilog()) ) {s = 21;}

                        else if ( (synpred14_Verilog()) ) {s = 22;}

                         
                        input.seek(index109_20);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 109, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA110_eotS =
        "\106\uffff";
    static final String DFA110_eofS =
        "\106\uffff";
    static final String DFA110_minS =
        "\1\5\6\uffff\1\5\1\uffff\2\6\3\5\1\6\2\5\2\0\3\uffff\2\0\3\uffff"+
        "\5\0\3\uffff\3\0\3\uffff\12\0\3\uffff\20\0";
    static final String DFA110_maxS =
        "\1\64\6\uffff\1\64\1\uffff\4\63\1\64\1\63\2\64\2\0\3\uffff\2\0"+
        "\3\uffff\5\0\3\uffff\3\0\3\uffff\12\0\3\uffff\20\0";
    static final String DFA110_acceptS =
        "\1\uffff\6\1\1\uffff\1\1\12\uffff\1\1\1\2\3\uffff\1\1\7\uffff\1"+
        "\1\5\uffff\1\1\14\uffff\1\1\22\uffff";
    static final String DFA110_specialS =
        "\1\0\10\uffff\1\1\1\2\1\3\1\4\1\uffff\1\5\2\uffff\1\6\1\7\3\uffff"+
        "\1\10\1\11\3\uffff\1\12\1\13\1\14\1\15\1\16\3\uffff\1\17\1\20\1"+
        "\21\3\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\3"+
        "\uffff\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1"+
        "\47\1\50\1\51\1\52\1\53}>";
    static final String[] DFA110_transitionS = {
            "\1\7\3\uffff\1\5\5\uffff\1\2\3\uffff\1\3\2\uffff\1\4\2\uffff"+
            "\2\10\1\1\1\6\1\uffff\10\10\16\uffff\1\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\17\3\uffff\1\15\5\uffff\1\12\3\uffff\1\13\2\uffff\1\14"+
            "\2\uffff\2\20\1\11\1\16\1\uffff\10\20\16\uffff\1\13",
            "",
            "\1\24\1\23\4\uffff\1\24\7\uffff\1\21\4\uffff\2\21\2\uffff"+
            "\1\22\2\uffff\1\21\1\uffff\1\21\1\uffff\20\21",
            "\1\24\1\30\4\uffff\1\24\7\uffff\1\26\4\uffff\2\26\2\uffff"+
            "\1\27\2\uffff\1\26\1\uffff\1\26\1\uffff\20\26",
            "\1\33\1\24\1\40\1\34\2\uffff\1\35\1\24\7\uffff\1\36\4\uffff"+
            "\2\36\2\uffff\1\37\2\uffff\1\36\1\uffff\1\36\1\uffff\20\36",
            "\1\43\1\24\1\46\4\uffff\1\24\7\uffff\1\44\4\uffff\2\44\2\uffff"+
            "\1\45\2\uffff\1\44\1\uffff\1\44\1\uffff\20\44",
            "\1\57\3\uffff\1\55\5\uffff\1\52\3\uffff\1\53\2\uffff\1\54"+
            "\2\uffff\2\60\1\51\1\56\1\uffff\10\60\16\uffff\1\53",
            "\1\24\1\63\4\uffff\1\24\7\uffff\1\61\4\uffff\2\61\2\uffff"+
            "\1\62\2\uffff\1\61\1\uffff\1\61\1\uffff\20\61",
            "\1\74\3\uffff\1\72\5\uffff\1\67\3\uffff\1\70\2\uffff\1\71"+
            "\2\uffff\2\75\1\66\1\73\1\uffff\10\75\16\uffff\1\70",
            "\1\104\3\uffff\1\102\5\uffff\1\77\3\uffff\1\100\2\uffff\1"+
            "\101\2\uffff\2\105\1\76\1\103\1\uffff\10\105\16\uffff\1\100",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA110_eot = DFA.unpackEncodedString(DFA110_eotS);
    static final short[] DFA110_eof = DFA.unpackEncodedString(DFA110_eofS);
    static final char[] DFA110_min = DFA.unpackEncodedStringToUnsignedChars(DFA110_minS);
    static final char[] DFA110_max = DFA.unpackEncodedStringToUnsignedChars(DFA110_maxS);
    static final short[] DFA110_accept = DFA.unpackEncodedString(DFA110_acceptS);
    static final short[] DFA110_special = DFA.unpackEncodedString(DFA110_specialS);
    static final short[][] DFA110_transition;

    static {
        int numStates = DFA110_transitionS.length;
        DFA110_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA110_transition[i] = DFA.unpackEncodedString(DFA110_transitionS[i]);
        }
    }

    class DFA110 extends DFA {

        public DFA110(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 110;
            this.eot = DFA110_eot;
            this.eof = DFA110_eof;
            this.min = DFA110_min;
            this.max = DFA110_max;
            this.accept = DFA110_accept;
            this.special = DFA110_special;
            this.transition = DFA110_transition;
        }
        public String getDescription() {
            return "694:1: path_delay_value : ( ( path_delay_expression )=> path_delay_expression | LPAREN list_of_path_delay_expressions RPAREN );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA110_0 = input.LA(1);

                         
                        int index110_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_0==STRING) && (synpred15_Verilog())) {s = 1;}

                        else if ( (LA110_0==NUMBER) && (synpred15_Verilog())) {s = 2;}

                        else if ( (LA110_0==IDENTIFIER||LA110_0==ESCAPED_IDENTIFIER) && (synpred15_Verilog())) {s = 3;}

                        else if ( (LA110_0==SYSTEM_TASK_NAME) && (synpred15_Verilog())) {s = 4;}

                        else if ( (LA110_0==LCURLY) && (synpred15_Verilog())) {s = 5;}

                        else if ( (LA110_0==DEFINE) && (synpred15_Verilog())) {s = 6;}

                        else if ( (LA110_0==LPAREN) ) {s = 7;}

                        else if ( ((LA110_0>=PLUS && LA110_0<=MINUS)||(LA110_0>=LNOT && LA110_0<=BXNOR)) && (synpred15_Verilog())) {s = 8;}

                         
                        input.seek(index110_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA110_9 = input.LA(1);

                         
                        int index110_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_9==LE||(LA110_9>=PLUS && LA110_9<=MINUS)||LA110_9==BAND||LA110_9==BOR||(LA110_9>=BXOR && LA110_9<=SL)) ) {s = 17;}

                        else if ( (LA110_9==QUESTION) ) {s = 18;}

                        else if ( (LA110_9==RPAREN) && (synpred15_Verilog())) {s = 19;}

                        else if ( (LA110_9==COMMA||LA110_9==COLON) ) {s = 20;}

                         
                        input.seek(index110_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA110_10 = input.LA(1);

                         
                        int index110_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_10==LE||(LA110_10>=PLUS && LA110_10<=MINUS)||LA110_10==BAND||LA110_10==BOR||(LA110_10>=BXOR && LA110_10<=SL)) ) {s = 22;}

                        else if ( (LA110_10==QUESTION) ) {s = 23;}

                        else if ( (LA110_10==RPAREN) && (synpred15_Verilog())) {s = 24;}

                        else if ( (LA110_10==COMMA||LA110_10==COLON) ) {s = 20;}

                         
                        input.seek(index110_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA110_11 = input.LA(1);

                         
                        int index110_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_11==LPAREN) ) {s = 27;}

                        else if ( (LA110_11==DOT) ) {s = 28;}

                        else if ( (LA110_11==LBRACK) ) {s = 29;}

                        else if ( (LA110_11==LE||(LA110_11>=PLUS && LA110_11<=MINUS)||LA110_11==BAND||LA110_11==BOR||(LA110_11>=BXOR && LA110_11<=SL)) ) {s = 30;}

                        else if ( (LA110_11==QUESTION) ) {s = 31;}

                        else if ( (LA110_11==RPAREN) && (synpred15_Verilog())) {s = 32;}

                        else if ( (LA110_11==COMMA||LA110_11==COLON) ) {s = 20;}

                         
                        input.seek(index110_11);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA110_12 = input.LA(1);

                         
                        int index110_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_12==LPAREN) ) {s = 35;}

                        else if ( (LA110_12==LE||(LA110_12>=PLUS && LA110_12<=MINUS)||LA110_12==BAND||LA110_12==BOR||(LA110_12>=BXOR && LA110_12<=SL)) ) {s = 36;}

                        else if ( (LA110_12==QUESTION) ) {s = 37;}

                        else if ( (LA110_12==RPAREN) && (synpred15_Verilog())) {s = 38;}

                        else if ( (LA110_12==COMMA||LA110_12==COLON) ) {s = 20;}

                         
                        input.seek(index110_12);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA110_14 = input.LA(1);

                         
                        int index110_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA110_14==LE||(LA110_14>=PLUS && LA110_14<=MINUS)||LA110_14==BAND||LA110_14==BOR||(LA110_14>=BXOR && LA110_14<=SL)) ) {s = 49;}

                        else if ( (LA110_14==QUESTION) ) {s = 50;}

                        else if ( (LA110_14==RPAREN) && (synpred15_Verilog())) {s = 51;}

                        else if ( (LA110_14==COMMA||LA110_14==COLON) ) {s = 20;}

                         
                        input.seek(index110_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA110_17 = input.LA(1);

                         
                        int index110_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_17);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA110_18 = input.LA(1);

                         
                        int index110_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_18);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA110_22 = input.LA(1);

                         
                        int index110_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_22);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA110_23 = input.LA(1);

                         
                        int index110_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_23);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA110_27 = input.LA(1);

                         
                        int index110_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_27);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA110_28 = input.LA(1);

                         
                        int index110_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_28);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA110_29 = input.LA(1);

                         
                        int index110_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_29);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA110_30 = input.LA(1);

                         
                        int index110_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_30);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA110_31 = input.LA(1);

                         
                        int index110_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_31);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA110_35 = input.LA(1);

                         
                        int index110_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_35);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA110_36 = input.LA(1);

                         
                        int index110_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_36);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA110_37 = input.LA(1);

                         
                        int index110_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_37);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA110_41 = input.LA(1);

                         
                        int index110_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_41);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA110_42 = input.LA(1);

                         
                        int index110_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_42);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA110_43 = input.LA(1);

                         
                        int index110_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_43);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA110_44 = input.LA(1);

                         
                        int index110_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_44);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA110_45 = input.LA(1);

                         
                        int index110_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_45);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA110_46 = input.LA(1);

                         
                        int index110_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_46);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA110_47 = input.LA(1);

                         
                        int index110_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_47);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA110_48 = input.LA(1);

                         
                        int index110_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_48);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA110_49 = input.LA(1);

                         
                        int index110_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_49);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA110_50 = input.LA(1);

                         
                        int index110_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_50);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA110_54 = input.LA(1);

                         
                        int index110_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_54);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA110_55 = input.LA(1);

                         
                        int index110_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_55);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA110_56 = input.LA(1);

                         
                        int index110_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_56);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA110_57 = input.LA(1);

                         
                        int index110_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_57);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA110_58 = input.LA(1);

                         
                        int index110_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_58);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA110_59 = input.LA(1);

                         
                        int index110_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_59);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA110_60 = input.LA(1);

                         
                        int index110_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_60);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA110_61 = input.LA(1);

                         
                        int index110_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_61);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA110_62 = input.LA(1);

                         
                        int index110_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_62);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA110_63 = input.LA(1);

                         
                        int index110_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_63);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA110_64 = input.LA(1);

                         
                        int index110_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_64);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA110_65 = input.LA(1);

                         
                        int index110_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_65);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA110_66 = input.LA(1);

                         
                        int index110_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_66);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA110_67 = input.LA(1);

                         
                        int index110_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_67);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA110_68 = input.LA(1);

                         
                        int index110_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_68);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA110_69 = input.LA(1);

                         
                        int index110_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Verilog()) ) {s = 51;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index110_69);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 110, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA127_eotS =
        "\15\uffff";
    static final String DFA127_eofS =
        "\15\uffff";
    static final String DFA127_minS =
        "\1\u008e\2\5\10\0\2\uffff";
    static final String DFA127_maxS =
        "\1\u008e\1\5\1\64\10\0\2\uffff";
    static final String DFA127_acceptS =
        "\13\uffff\1\1\1\2";
    static final String DFA127_specialS =
        "\3\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\2\uffff}>";
    static final String[] DFA127_transitionS = {
            "\1\1",
            "\1\2",
            "\1\11\3\uffff\1\7\5\uffff\1\4\3\uffff\1\5\2\uffff\1\6\2\uffff"+
            "\2\12\1\3\1\10\1\uffff\10\12\16\uffff\1\5",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA127_eot = DFA.unpackEncodedString(DFA127_eotS);
    static final short[] DFA127_eof = DFA.unpackEncodedString(DFA127_eofS);
    static final char[] DFA127_min = DFA.unpackEncodedStringToUnsignedChars(DFA127_minS);
    static final char[] DFA127_max = DFA.unpackEncodedStringToUnsignedChars(DFA127_maxS);
    static final short[] DFA127_accept = DFA.unpackEncodedString(DFA127_acceptS);
    static final short[] DFA127_special = DFA.unpackEncodedString(DFA127_specialS);
    static final short[][] DFA127_transition;

    static {
        int numStates = DFA127_transitionS.length;
        DFA127_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA127_transition[i] = DFA.unpackEncodedString(DFA127_transitionS[i]);
        }
    }

    class DFA127 extends DFA {

        public DFA127(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 127;
            this.eot = DFA127_eot;
            this.eof = DFA127_eof;
            this.min = DFA127_min;
            this.max = DFA127_max;
            this.accept = DFA127_accept;
            this.special = DFA127_special;
            this.transition = DFA127_transition;
        }
        public String getDescription() {
            return "779:1: level_sensitive_path_declaration : ( ( parallel_level_sensitive_path_description )=> parallel_level_sensitive_path_description ASSIGN path_delay_value SEMI | full_level_sensitive_path_description ASSIGN path_delay_value SEMI );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA127_3 = input.LA(1);

                         
                        int index127_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA127_4 = input.LA(1);

                         
                        int index127_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA127_5 = input.LA(1);

                         
                        int index127_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA127_6 = input.LA(1);

                         
                        int index127_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA127_7 = input.LA(1);

                         
                        int index127_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA127_8 = input.LA(1);

                         
                        int index127_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA127_9 = input.LA(1);

                         
                        int index127_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA127_10 = input.LA(1);

                         
                        int index127_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Verilog()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index127_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 127, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA134_eotS =
        "\53\uffff";
    static final String DFA134_eofS =
        "\1\uffff\1\5\51\uffff";
    static final String DFA134_minS =
        "\1\11\1\4\1\uffff\1\23\46\uffff\1\0";
    static final String DFA134_maxS =
        "\1\64\1\u00b3\1\uffff\1\64\46\uffff\1\0";
    static final String DFA134_acceptS =
        "\2\uffff\1\3\1\uffff\1\1\1\2\45\uffff";
    static final String DFA134_specialS =
        "\1\uffff\1\0\50\uffff\1\1}>";
    static final String[] DFA134_transitionS = {
            "\1\2\11\uffff\1\1\40\uffff\1\1",
            "\1\5\1\uffff\2\5\1\3\2\5\1\4\3\5\1\uffff\2\5\1\uffff\2\5\4"+
            "\uffff\2\5\2\uffff\1\5\2\uffff\1\5\1\uffff\1\5\1\uffff\21\5"+
            "\16\uffff\4\5\1\uffff\1\5\5\uffff\1\5\1\uffff\1\5\1\uffff\20"+
            "\5\2\uffff\4\5\13\uffff\33\5\24\uffff\1\5\17\uffff\2\5",
            "",
            "\1\52\40\uffff\1\52",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff"
    };

    static final short[] DFA134_eot = DFA.unpackEncodedString(DFA134_eotS);
    static final short[] DFA134_eof = DFA.unpackEncodedString(DFA134_eofS);
    static final char[] DFA134_min = DFA.unpackEncodedStringToUnsignedChars(DFA134_minS);
    static final char[] DFA134_max = DFA.unpackEncodedStringToUnsignedChars(DFA134_maxS);
    static final short[] DFA134_accept = DFA.unpackEncodedString(DFA134_acceptS);
    static final short[] DFA134_special = DFA.unpackEncodedString(DFA134_specialS);
    static final short[][] DFA134_transition;

    static {
        int numStates = DFA134_transitionS.length;
        DFA134_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA134_transition[i] = DFA.unpackEncodedString(DFA134_transitionS[i]);
        }
    }

    class DFA134 extends DFA {

        public DFA134(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 134;
            this.eot = DFA134_eot;
            this.eof = DFA134_eof;
            this.min = DFA134_min;
            this.max = DFA134_max;
            this.accept = DFA134_accept;
            this.special = DFA134_special;
            this.transition = DFA134_transition;
        }
        public String getDescription() {
            return "836:1: lvalue : ( ( identifier range )=> identifier range | identifier | concatenation );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA134_1 = input.LA(1);

                         
                        int index134_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA134_1==DOT) ) {s = 3;}

                        else if ( (LA134_1==LBRACK) && (synpred18_Verilog())) {s = 4;}

                        else if ( (LA134_1==EOF||LA134_1==SEMI||(LA134_1>=COMMA && LA134_1<=RPAREN)||(LA134_1>=LCURLY && LA134_1<=RCURLY)||(LA134_1>=COLON && LA134_1<=ASSIGN)||(LA134_1>=KW_TRIREG && LA134_1<=KW_ASSIGN)||(LA134_1>=IDENTIFIER && LA134_1<=LE)||(LA134_1>=PLUS && LA134_1<=MINUS)||LA134_1==QUESTION||LA134_1==BAND||LA134_1==BOR||(LA134_1>=BXOR && LA134_1<=ESCAPED_IDENTIFIER)||(LA134_1>=67 && LA134_1<=70)||LA134_1==72||LA134_1==78||LA134_1==80||(LA134_1>=82 && LA134_1<=97)||(LA134_1>=100 && LA134_1<=103)||(LA134_1>=115 && LA134_1<=141)||LA134_1==162||(LA134_1>=178 && LA134_1<=179)) ) {s = 5;}

                         
                        input.seek(index134_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA134_42 = input.LA(1);

                         
                        int index134_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_Verilog()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index134_42);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 134, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA137_eotS =
        "\77\uffff";
    static final String DFA137_eofS =
        "\77\uffff";
    static final String DFA137_minS =
        "\1\11\1\5\2\6\3\5\1\6\2\5\2\0\3\uffff\2\0\3\uffff\5\0\3\uffff\3"+
        "\0\3\uffff\12\0\3\uffff\20\0";
    static final String DFA137_maxS =
        "\1\11\1\64\4\63\1\64\1\63\2\64\2\0\3\uffff\2\0\3\uffff\5\0\3\uffff"+
        "\3\0\3\uffff\12\0\3\uffff\20\0";
    static final String DFA137_acceptS =
        "\14\uffff\1\1\1\2\3\uffff\1\1\7\uffff\1\1\5\uffff\1\1\14\uffff"+
        "\1\1\22\uffff";
    static final String DFA137_specialS =
        "\2\uffff\1\0\1\1\1\2\1\3\1\uffff\1\4\2\uffff\1\5\1\6\3\uffff\1"+
        "\7\1\10\3\uffff\1\11\1\12\1\13\1\14\1\15\3\uffff\1\16\1\17\1\20"+
        "\3\uffff\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\3\uffff"+
        "\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
        "\1\50\1\51\1\52}>";
    static final String[] DFA137_transitionS = {
            "\1\1",
            "\1\10\3\uffff\1\6\5\uffff\1\3\3\uffff\1\4\2\uffff\1\5\2\uffff"+
            "\2\11\1\2\1\7\1\uffff\10\11\16\uffff\1\4",
            "\1\15\2\uffff\1\14\1\15\11\uffff\1\12\4\uffff\2\12\2\uffff"+
            "\1\13\2\uffff\1\12\1\uffff\1\12\1\uffff\20\12",
            "\1\15\2\uffff\1\21\1\15\11\uffff\1\17\4\uffff\2\17\2\uffff"+
            "\1\20\2\uffff\1\17\1\uffff\1\17\1\uffff\20\17",
            "\1\24\1\15\1\uffff\1\25\1\31\1\15\1\26\10\uffff\1\27\4\uffff"+
            "\2\27\2\uffff\1\30\2\uffff\1\27\1\uffff\1\27\1\uffff\20\27",
            "\1\34\1\15\2\uffff\1\37\1\15\11\uffff\1\35\4\uffff\2\35\2"+
            "\uffff\1\36\2\uffff\1\35\1\uffff\1\35\1\uffff\20\35",
            "\1\50\3\uffff\1\46\5\uffff\1\43\3\uffff\1\44\2\uffff\1\45"+
            "\2\uffff\2\51\1\42\1\47\1\uffff\10\51\16\uffff\1\44",
            "\1\15\2\uffff\1\54\1\15\11\uffff\1\52\4\uffff\2\52\2\uffff"+
            "\1\53\2\uffff\1\52\1\uffff\1\52\1\uffff\20\52",
            "\1\65\3\uffff\1\63\5\uffff\1\60\3\uffff\1\61\2\uffff\1\62"+
            "\2\uffff\2\66\1\57\1\64\1\uffff\10\66\16\uffff\1\61",
            "\1\75\3\uffff\1\73\5\uffff\1\70\3\uffff\1\71\2\uffff\1\72"+
            "\2\uffff\2\76\1\67\1\74\1\uffff\10\76\16\uffff\1\71",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
    };

    static final short[] DFA137_eot = DFA.unpackEncodedString(DFA137_eotS);
    static final short[] DFA137_eof = DFA.unpackEncodedString(DFA137_eofS);
    static final char[] DFA137_min = DFA.unpackEncodedStringToUnsignedChars(DFA137_minS);
    static final char[] DFA137_max = DFA.unpackEncodedStringToUnsignedChars(DFA137_maxS);
    static final short[] DFA137_accept = DFA.unpackEncodedString(DFA137_acceptS);
    static final short[] DFA137_special = DFA.unpackEncodedString(DFA137_specialS);
    static final short[][] DFA137_transition;

    static {
        int numStates = DFA137_transitionS.length;
        DFA137_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA137_transition[i] = DFA.unpackEncodedString(DFA137_transitionS[i]);
        }
    }

    class DFA137 extends DFA {

        public DFA137(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 137;
            this.eot = DFA137_eot;
            this.eof = DFA137_eof;
            this.min = DFA137_min;
            this.max = DFA137_max;
            this.accept = DFA137_accept;
            this.special = DFA137_special;
            this.transition = DFA137_transition;
        }
        public String getDescription() {
            return "843:1: concatenation : ( ( LCURLY expression LCURLY )=> LCURLY expression LCURLY expression ( COMMA expression )* RCURLY RCURLY | LCURLY expression ( COMMA expression )* RCURLY );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA137_2 = input.LA(1);

                         
                        int index137_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA137_2==LE||(LA137_2>=PLUS && LA137_2<=MINUS)||LA137_2==BAND||LA137_2==BOR||(LA137_2>=BXOR && LA137_2<=SL)) ) {s = 10;}

                        else if ( (LA137_2==QUESTION) ) {s = 11;}

                        else if ( (LA137_2==LCURLY) && (synpred19_Verilog())) {s = 12;}

                        else if ( (LA137_2==COMMA||LA137_2==RCURLY) ) {s = 13;}

                         
                        input.seek(index137_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA137_3 = input.LA(1);

                         
                        int index137_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA137_3==LE||(LA137_3>=PLUS && LA137_3<=MINUS)||LA137_3==BAND||LA137_3==BOR||(LA137_3>=BXOR && LA137_3<=SL)) ) {s = 15;}

                        else if ( (LA137_3==QUESTION) ) {s = 16;}

                        else if ( (LA137_3==LCURLY) && (synpred19_Verilog())) {s = 17;}

                        else if ( (LA137_3==COMMA||LA137_3==RCURLY) ) {s = 13;}

                         
                        input.seek(index137_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA137_4 = input.LA(1);

                         
                        int index137_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA137_4==LPAREN) ) {s = 20;}

                        else if ( (LA137_4==DOT) ) {s = 21;}

                        else if ( (LA137_4==LBRACK) ) {s = 22;}

                        else if ( (LA137_4==LE||(LA137_4>=PLUS && LA137_4<=MINUS)||LA137_4==BAND||LA137_4==BOR||(LA137_4>=BXOR && LA137_4<=SL)) ) {s = 23;}

                        else if ( (LA137_4==QUESTION) ) {s = 24;}

                        else if ( (LA137_4==LCURLY) && (synpred19_Verilog())) {s = 25;}

                        else if ( (LA137_4==COMMA||LA137_4==RCURLY) ) {s = 13;}

                         
                        input.seek(index137_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA137_5 = input.LA(1);

                         
                        int index137_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA137_5==LPAREN) ) {s = 28;}

                        else if ( (LA137_5==LE||(LA137_5>=PLUS && LA137_5<=MINUS)||LA137_5==BAND||LA137_5==BOR||(LA137_5>=BXOR && LA137_5<=SL)) ) {s = 29;}

                        else if ( (LA137_5==QUESTION) ) {s = 30;}

                        else if ( (LA137_5==LCURLY) && (synpred19_Verilog())) {s = 31;}

                        else if ( (LA137_5==COMMA||LA137_5==RCURLY) ) {s = 13;}

                         
                        input.seek(index137_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA137_7 = input.LA(1);

                         
                        int index137_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA137_7==LE||(LA137_7>=PLUS && LA137_7<=MINUS)||LA137_7==BAND||LA137_7==BOR||(LA137_7>=BXOR && LA137_7<=SL)) ) {s = 42;}

                        else if ( (LA137_7==QUESTION) ) {s = 43;}

                        else if ( (LA137_7==LCURLY) && (synpred19_Verilog())) {s = 44;}

                        else if ( (LA137_7==COMMA||LA137_7==RCURLY) ) {s = 13;}

                         
                        input.seek(index137_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA137_10 = input.LA(1);

                         
                        int index137_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA137_11 = input.LA(1);

                         
                        int index137_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA137_15 = input.LA(1);

                         
                        int index137_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_15);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA137_16 = input.LA(1);

                         
                        int index137_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_16);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA137_20 = input.LA(1);

                         
                        int index137_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_20);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA137_21 = input.LA(1);

                         
                        int index137_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_21);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA137_22 = input.LA(1);

                         
                        int index137_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_22);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA137_23 = input.LA(1);

                         
                        int index137_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_23);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA137_24 = input.LA(1);

                         
                        int index137_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_24);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA137_28 = input.LA(1);

                         
                        int index137_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_28);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA137_29 = input.LA(1);

                         
                        int index137_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_29);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA137_30 = input.LA(1);

                         
                        int index137_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_30);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA137_34 = input.LA(1);

                         
                        int index137_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_34);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA137_35 = input.LA(1);

                         
                        int index137_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_35);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA137_36 = input.LA(1);

                         
                        int index137_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_36);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA137_37 = input.LA(1);

                         
                        int index137_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_37);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA137_38 = input.LA(1);

                         
                        int index137_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_38);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA137_39 = input.LA(1);

                         
                        int index137_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_39);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA137_40 = input.LA(1);

                         
                        int index137_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_40);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA137_41 = input.LA(1);

                         
                        int index137_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_41);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA137_42 = input.LA(1);

                         
                        int index137_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_42);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA137_43 = input.LA(1);

                         
                        int index137_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_43);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA137_47 = input.LA(1);

                         
                        int index137_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_47);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA137_48 = input.LA(1);

                         
                        int index137_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_48);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA137_49 = input.LA(1);

                         
                        int index137_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_49);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA137_50 = input.LA(1);

                         
                        int index137_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_50);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA137_51 = input.LA(1);

                         
                        int index137_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_51);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA137_52 = input.LA(1);

                         
                        int index137_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_52);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA137_53 = input.LA(1);

                         
                        int index137_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_53);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA137_54 = input.LA(1);

                         
                        int index137_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_54);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA137_55 = input.LA(1);

                         
                        int index137_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_55);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA137_56 = input.LA(1);

                         
                        int index137_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_56);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA137_57 = input.LA(1);

                         
                        int index137_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_57);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA137_58 = input.LA(1);

                         
                        int index137_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_58);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA137_59 = input.LA(1);

                         
                        int index137_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_59);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA137_60 = input.LA(1);

                         
                        int index137_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_60);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA137_61 = input.LA(1);

                         
                        int index137_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_61);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA137_62 = input.LA(1);

                         
                        int index137_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_Verilog()) ) {s = 44;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index137_62);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 137, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA139_eotS =
        "\55\uffff";
    static final String DFA139_eofS =
        "\3\uffff\1\5\51\uffff";
    static final String DFA139_minS =
        "\1\11\2\uffff\1\4\51\uffff";
    static final String DFA139_maxS =
        "\1\64\2\uffff\1\u00b3\51\uffff";
    static final String DFA139_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\3\45\uffff";
    static final String DFA139_specialS =
        "\1\0\2\uffff\1\1\51\uffff}>";
    static final String[] DFA139_transitionS = {
            "\1\5\5\uffff\1\2\3\uffff\1\3\2\uffff\1\4\4\uffff\1\1\1\6\27"+
            "\uffff\1\3",
            "",
            "",
            "\1\5\1\7\10\5\2\uffff\2\5\1\uffff\2\5\4\uffff\2\5\2\uffff"+
            "\1\5\2\uffff\1\5\1\uffff\1\5\1\uffff\21\5\16\uffff\4\5\1\uffff"+
            "\1\5\5\uffff\1\5\1\uffff\1\5\1\uffff\20\5\2\uffff\4\5\13\uffff"+
            "\33\5\24\uffff\1\5\17\uffff\2\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA139_eot = DFA.unpackEncodedString(DFA139_eotS);
    static final short[] DFA139_eof = DFA.unpackEncodedString(DFA139_eofS);
    static final char[] DFA139_min = DFA.unpackEncodedStringToUnsignedChars(DFA139_minS);
    static final char[] DFA139_max = DFA.unpackEncodedStringToUnsignedChars(DFA139_maxS);
    static final short[] DFA139_accept = DFA.unpackEncodedString(DFA139_acceptS);
    static final short[] DFA139_special = DFA.unpackEncodedString(DFA139_specialS);
    static final short[][] DFA139_transition;

    static {
        int numStates = DFA139_transitionS.length;
        DFA139_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA139_transition[i] = DFA.unpackEncodedString(DFA139_transitionS[i]);
        }
    }

    class DFA139 extends DFA {

        public DFA139(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 139;
            this.eot = DFA139_eot;
            this.eof = DFA139_eof;
            this.min = DFA139_min;
            this.max = DFA139_max;
            this.accept = DFA139_accept;
            this.special = DFA139_special;
            this.transition = DFA139_transition;
        }
        public String getDescription() {
            return "854:1: exp11 : ( STRING | NUMBER | ( function_call )=> function_call | lvalue | DEFINE );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA139_0 = input.LA(1);

                         
                        int index139_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA139_0==STRING) ) {s = 1;}

                        else if ( (LA139_0==NUMBER) ) {s = 2;}

                        else if ( (LA139_0==IDENTIFIER||LA139_0==ESCAPED_IDENTIFIER) ) {s = 3;}

                        else if ( (LA139_0==SYSTEM_TASK_NAME) && (synpred20_Verilog())) {s = 4;}

                        else if ( (LA139_0==LCURLY) ) {s = 5;}

                        else if ( (LA139_0==DEFINE) ) {s = 6;}

                         
                        input.seek(index139_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA139_3 = input.LA(1);

                         
                        int index139_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA139_3==LPAREN) && (synpred20_Verilog())) {s = 7;}

                        else if ( (LA139_3==EOF||LA139_3==SEMI||(LA139_3>=COMMA && LA139_3<=RBRACK)||(LA139_3>=KW_TRIREG && LA139_3<=KW_ASSIGN)||(LA139_3>=IDENTIFIER && LA139_3<=LE)||(LA139_3>=PLUS && LA139_3<=MINUS)||LA139_3==QUESTION||LA139_3==BAND||LA139_3==BOR||(LA139_3>=BXOR && LA139_3<=ESCAPED_IDENTIFIER)||(LA139_3>=67 && LA139_3<=70)||LA139_3==72||LA139_3==78||LA139_3==80||(LA139_3>=82 && LA139_3<=97)||(LA139_3>=100 && LA139_3<=103)||(LA139_3>=115 && LA139_3<=141)||LA139_3==162||(LA139_3>=178 && LA139_3<=179)) ) {s = 5;}

                         
                        input.seek(index139_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 139, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA142_eotS =
        "\44\uffff";
    static final String DFA142_eofS =
        "\1\1\43\uffff";
    static final String DFA142_minS =
        "\1\4\43\uffff";
    static final String DFA142_maxS =
        "\1\u00b3\43\uffff";
    static final String DFA142_acceptS =
        "\1\uffff\1\2\41\uffff\1\1";
    static final String DFA142_specialS =
        "\44\uffff}>";
    static final String[] DFA142_transitionS = {
            "\1\1\1\uffff\2\1\1\uffff\2\1\1\uffff\2\1\2\uffff\2\1\1\uffff"+
            "\1\1\1\43\4\uffff\2\43\2\uffff\1\1\2\uffff\1\43\1\uffff\1\43"+
            "\1\uffff\20\43\1\1\16\uffff\4\1\1\uffff\1\1\5\uffff\1\1\1\uffff"+
            "\1\1\1\uffff\20\1\2\uffff\4\1\13\uffff\33\1\24\uffff\1\1\17"+
            "\uffff\2\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA142_eot = DFA.unpackEncodedString(DFA142_eotS);
    static final short[] DFA142_eof = DFA.unpackEncodedString(DFA142_eofS);
    static final char[] DFA142_min = DFA.unpackEncodedStringToUnsignedChars(DFA142_minS);
    static final char[] DFA142_max = DFA.unpackEncodedStringToUnsignedChars(DFA142_maxS);
    static final short[] DFA142_accept = DFA.unpackEncodedString(DFA142_acceptS);
    static final short[] DFA142_special = DFA.unpackEncodedString(DFA142_specialS);
    static final short[][] DFA142_transition;

    static {
        int numStates = DFA142_transitionS.length;
        DFA142_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA142_transition[i] = DFA.unpackEncodedString(DFA142_transitionS[i]);
        }
    }

    class DFA142 extends DFA {

        public DFA142(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 142;
            this.eot = DFA142_eot;
            this.eof = DFA142_eof;
            this.min = DFA142_min;
            this.max = DFA142_max;
            this.accept = DFA142_accept;
            this.special = DFA142_special;
            this.transition = DFA142_transition;
        }
        public String getDescription() {
            return "()* loopback of 871:14: ( binary_operator exp9 )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA143_eotS =
        "\43\uffff";
    static final String DFA143_eofS =
        "\1\2\42\uffff";
    static final String DFA143_minS =
        "\1\4\42\uffff";
    static final String DFA143_maxS =
        "\1\u00b3\42\uffff";
    static final String DFA143_acceptS =
        "\1\uffff\1\1\1\2\40\uffff";
    static final String DFA143_specialS =
        "\43\uffff}>";
    static final String[] DFA143_transitionS = {
            "\1\2\1\uffff\2\2\1\uffff\2\2\1\uffff\2\2\2\uffff\2\2\1\uffff"+
            "\1\2\11\uffff\1\1\26\uffff\1\2\16\uffff\4\2\1\uffff\1\2\5\uffff"+
            "\1\2\1\uffff\1\2\1\uffff\20\2\2\uffff\4\2\13\uffff\33\2\24\uffff"+
            "\1\2\17\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA143_eot = DFA.unpackEncodedString(DFA143_eotS);
    static final short[] DFA143_eof = DFA.unpackEncodedString(DFA143_eofS);
    static final char[] DFA143_min = DFA.unpackEncodedStringToUnsignedChars(DFA143_minS);
    static final char[] DFA143_max = DFA.unpackEncodedStringToUnsignedChars(DFA143_maxS);
    static final short[] DFA143_accept = DFA.unpackEncodedString(DFA143_acceptS);
    static final short[] DFA143_special = DFA.unpackEncodedString(DFA143_specialS);
    static final short[][] DFA143_transition;

    static {
        int numStates = DFA143_transitionS.length;
        DFA143_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA143_transition[i] = DFA.unpackEncodedString(DFA143_transitionS[i]);
        }
    }

    class DFA143 extends DFA {

        public DFA143(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 143;
            this.eot = DFA143_eot;
            this.eof = DFA143_eof;
            this.min = DFA143_min;
            this.max = DFA143_max;
            this.accept = DFA143_accept;
            this.special = DFA143_special;
            this.transition = DFA143_transition;
        }
        public String getDescription() {
            return "875:14: ( QUESTION exp7 COLON exp7 )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA144_eotS =
        "\45\uffff";
    static final String DFA144_eofS =
        "\1\2\44\uffff";
    static final String DFA144_minS =
        "\1\4\44\uffff";
    static final String DFA144_maxS =
        "\1\u00b3\44\uffff";
    static final String DFA144_acceptS =
        "\1\uffff\1\1\1\2\42\uffff";
    static final String DFA144_specialS =
        "\45\uffff}>";
    static final String[] DFA144_transitionS = {
            "\1\2\1\1\2\2\1\uffff\2\2\1\uffff\2\2\2\uffff\2\2\1\uffff\2"+
            "\2\4\uffff\2\2\2\uffff\1\2\2\uffff\1\2\1\uffff\1\2\1\uffff\21"+
            "\2\16\uffff\4\2\1\uffff\1\2\5\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\20\2\2\uffff\4\2\13\uffff\33\2\24\uffff\1\2\17\uffff\2\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA144_eot = DFA.unpackEncodedString(DFA144_eotS);
    static final short[] DFA144_eof = DFA.unpackEncodedString(DFA144_eofS);
    static final char[] DFA144_min = DFA.unpackEncodedStringToUnsignedChars(DFA144_minS);
    static final char[] DFA144_max = DFA.unpackEncodedStringToUnsignedChars(DFA144_maxS);
    static final short[] DFA144_accept = DFA.unpackEncodedString(DFA144_acceptS);
    static final short[] DFA144_special = DFA.unpackEncodedString(DFA144_specialS);
    static final short[][] DFA144_transition;

    static {
        int numStates = DFA144_transitionS.length;
        DFA144_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA144_transition[i] = DFA.unpackEncodedString(DFA144_transitionS[i]);
        }
    }

    class DFA144 extends DFA {

        public DFA144(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 144;
            this.eot = DFA144_eot;
            this.eof = DFA144_eof;
            this.min = DFA144_min;
            this.max = DFA144_max;
            this.accept = DFA144_accept;
            this.special = DFA144_special;
            this.transition = DFA144_transition;
        }
        public String getDescription() {
            return "888:26: ( LPAREN expression_list RPAREN )?";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA147_eotS =
        "\103\uffff";
    static final String DFA147_eofS =
        "\1\1\102\uffff";
    static final String DFA147_minS =
        "\1\4\102\uffff";
    static final String DFA147_maxS =
        "\1\u00b3\102\uffff";
    static final String DFA147_acceptS =
        "\1\uffff\1\2\100\uffff\1\1";
    static final String DFA147_specialS =
        "\103\uffff}>";
    static final String[] DFA147_transitionS = {
            "\4\1\1\102\55\1\15\uffff\4\1\1\uffff\1\1\5\uffff\1\1\1\uffff"+
            "\1\1\1\uffff\20\1\2\uffff\4\1\13\uffff\34\1\2\uffff\3\1\1\uffff"+
            "\7\1\1\uffff\1\1\1\uffff\4\1\11\uffff\1\1\5\uffff\2\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA147_eot = DFA.unpackEncodedString(DFA147_eotS);
    static final short[] DFA147_eof = DFA.unpackEncodedString(DFA147_eofS);
    static final char[] DFA147_min = DFA.unpackEncodedStringToUnsignedChars(DFA147_minS);
    static final char[] DFA147_max = DFA.unpackEncodedStringToUnsignedChars(DFA147_maxS);
    static final short[] DFA147_accept = DFA.unpackEncodedString(DFA147_acceptS);
    static final short[] DFA147_special = DFA.unpackEncodedString(DFA147_specialS);
    static final short[][] DFA147_transition;

    static {
        int numStates = DFA147_transitionS.length;
        DFA147_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA147_transition[i] = DFA.unpackEncodedString(DFA147_transitionS[i]);
        }
    }

    class DFA147 extends DFA {

        public DFA147(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 147;
            this.eot = DFA147_eot;
            this.eof = DFA147_eof;
            this.min = DFA147_min;
            this.max = DFA147_max;
            this.accept = DFA147_accept;
            this.special = DFA147_special;
            this.transition = DFA147_transition;
        }
        public String getDescription() {
            return "()* loopback of 969:26: ( DOT local_identifier )*";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
    static final String DFA151_eotS =
        "\13\uffff";
    static final String DFA151_eofS =
        "\13\uffff";
    static final String DFA151_minS =
        "\1\5\12\uffff";
    static final String DFA151_maxS =
        "\1\u00ae\12\uffff";
    static final String DFA151_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\1\3";
    static final String DFA151_specialS =
        "\13\uffff}>";
    static final String[] DFA151_transitionS = {
            "\1\1\3\uffff\1\1\5\uffff\1\1\3\uffff\1\1\2\uffff\1\1\2\uffff"+
            "\4\1\1\uffff\10\1\16\uffff\1\1\170\uffff\1\11\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA151_eot = DFA.unpackEncodedString(DFA151_eotS);
    static final short[] DFA151_eof = DFA.unpackEncodedString(DFA151_eofS);
    static final char[] DFA151_min = DFA.unpackEncodedStringToUnsignedChars(DFA151_minS);
    static final char[] DFA151_max = DFA.unpackEncodedStringToUnsignedChars(DFA151_maxS);
    static final short[] DFA151_accept = DFA.unpackEncodedString(DFA151_acceptS);
    static final short[] DFA151_special = DFA.unpackEncodedString(DFA151_specialS);
    static final short[][] DFA151_transition;

    static {
        int numStates = DFA151_transitionS.length;
        DFA151_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA151_transition[i] = DFA.unpackEncodedString(DFA151_transitionS[i]);
        }
    }

    class DFA151 extends DFA {

        public DFA151(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 151;
            this.eot = DFA151_eot;
            this.eof = DFA151_eof;
            this.min = DFA151_min;
            this.max = DFA151_max;
            this.accept = DFA151_accept;
            this.special = DFA151_special;
            this.transition = DFA151_transition;
        }
        public String getDescription() {
            return "992:1: sub_event_expression : ( expression | 'posedge' expression | 'negedge' expression );";
        }
        public void error(NoViableAltException nvae) {
            dbg.recognitionException(nvae);
        }
    }
 

    public static final BitSet FOLLOW_description_in_source_text75 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000058L,0x000C000000000000L});
    public static final BitSet FOLLOW_EOF_in_source_text81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_in_description106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_udp_in_description118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directive_in_description123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_module148 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_module_in_module159 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_list_of_ports_in_module171 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_module176 = new BitSet(new long[]{0x00100000000B0000L,0xFFF800F3FFFD4178L,0x000C000400003FFFL});
    public static final BitSet FOLLOW_module_item_in_module181 = new BitSet(new long[]{0x00100000000B0000L,0xFFF800F3FFFD4178L,0x000C000400003FFFL});
    public static final BitSet FOLLOW_69_in_module187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_list_of_ports212 = new BitSet(new long[]{0x00100000000803C0L});
    public static final BitSet FOLLOW_port_in_list_of_ports214 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_list_of_ports218 = new BitSet(new long[]{0x00100000000803C0L});
    public static final BitSet FOLLOW_port_in_list_of_ports220 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_list_of_ports225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_port_expression_in_port244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_port258 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_port_in_port260 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_port262 = new BitSet(new long[]{0x0010000000080280L});
    public static final BitSet FOLLOW_port_expression_in_port265 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_port269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_port_reference_in_port_expression287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_port_expression292 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_port_reference_in_port_expression294 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_COMMA_in_port_expression298 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_port_reference_in_port_expression300 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_RCURLY_in_port_expression305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_port_reference352 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_port_reference354 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_port_reference356 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_port_reference358 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_port_reference360 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_port_reference362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_port_reference392 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_port_reference394 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_port_reference396 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_port_reference398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_port_reference410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_declaration_in_module_item442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_input_declaration_in_module_item454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_output_declaration_in_module_item466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inout_declaration_in_module_item478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reg_declaration_in_module_item499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_declaration_in_module_item511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_declaration_in_module_item523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_declaration_in_module_item535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_declaration_in_module_item547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_gate_declaration_in_module_item553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_instantiation_in_module_item565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_override_in_module_item577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continuous_assign_in_module_item589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specify_block_in_module_item601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initial_statement_in_module_item613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_always_statement_in_module_item625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_task_in_module_item637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_module_item649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directive_in_module_item655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_instantiation_in_instantiation678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_udp_instantiation_in_instantiation683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_udp705 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_UDP_in_udp707 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_udp710 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_variable_in_udp712 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_udp716 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_variable_in_udp718 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_udp723 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_udp725 = new BitSet(new long[]{0x0000000000000000L,0x0000001000600000L});
    public static final BitSet FOLLOW_udp_declaration_in_udp736 = new BitSet(new long[]{0x0000000000000000L,0x0000001000601100L});
    public static final BitSet FOLLOW_udp_initial_statement_in_udp742 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001100L});
    public static final BitSet FOLLOW_table_definition_in_udp747 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_udp757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_udp_name_of_port_in_udp_port_list782 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_udp_port_list786 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_udp_name_of_port_in_udp_port_list788 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_output_declaration_in_udp_declaration816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_input_declaration_in_udp_declaration828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reg_declaration_in_udp_declaration840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_udp_initial_statement865 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_output_terminal_name_in_udp_initial_statement867 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_udp_initial_statement869 = new BitSet(new long[]{0x0000000000008000L,0x0000000000000E00L});
    public static final BitSet FOLLOW_init_val_in_udp_initial_statement871 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_udp_initial_statement873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_init_val914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_init_val926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_init_val938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_init_val945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_table_definition966 = new BitSet(new long[]{0xFFFFFFFFFFFFFFE0L,0xFFFFFFFFFFFFFFFFL,0x000FFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_table_entries_in_table_definition968 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_table_definition970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_table_entries1019 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFDFFFL,0x000FFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_SEMI_in_table_entries1031 = new BitSet(new long[]{0xFFFFFFFFFFFFFFE2L,0xFFFFFFFFFFFFDFFFL,0x000FFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_78_in_task1056 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_task_in_task1058 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_task1060 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_tf_declaration_in_task1071 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_task1083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_task1093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_function1118 = new BitSet(new long[]{0x0010000000080800L,0x00000000000C0000L});
    public static final BitSet FOLLOW_range_or_type_in_function1121 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_function_in_function1125 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_function1127 = new BitSet(new long[]{0x0000000000000000L,0x0000007000FC0000L});
    public static final BitSet FOLLOW_tf_declaration_in_function1138 = new BitSet(new long[]{0x00300000006E0200L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_function1150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_function1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_range_in_range_or_type1185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_range_or_type1197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_range_or_type1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_declaration_in_tf_declaration1234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_output_declaration_in_tf_declaration1246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_input_declaration_in_tf_declaration1258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inout_declaration_in_tf_declaration1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reg_declaration_in_tf_declaration1282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_declaration_in_tf_declaration1294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_declaration_in_tf_declaration1306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_declaration_in_tf_declaration1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_declaration_in_tf_declaration1330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_parameter_declaration1361 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_range_in_parameter_declaration1364 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_param_assignments_in_parameter_declaration1368 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_parameter_declaration1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_assignment_in_list_of_param_assignments1395 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_param_assignments1399 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_param_assignment_in_list_of_param_assignments1401 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_identifier_in_param_assignment1429 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_param_assignment1431 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_param_assignment1433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_input_declaration1458 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_range_in_input_declaration1461 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_variables_in_input_declaration1465 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_input_declaration1467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_output_declaration1492 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_range_in_output_declaration1495 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_variables_in_output_declaration1499 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_output_declaration1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_inout_declaration1526 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_range_in_inout_declaration1529 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_variables_in_inout_declaration1533 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_inout_declaration1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_net_type_in_net_declaration1581 = new BitSet(new long[]{0x00100000000C0800L,0x0000000C00000000L});
    public static final BitSet FOLLOW_expandrange_in_net_declaration1584 = new BitSet(new long[]{0x00100000000C0800L,0x0000000C00000000L});
    public static final BitSet FOLLOW_delay_in_net_declaration1589 = new BitSet(new long[]{0x00100000000C0800L,0x0000000C00000000L});
    public static final BitSet FOLLOW_list_of_assigned_variables_in_net_declaration1604 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_net_declaration1606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KW_TRIREG_in_net_declaration1618 = new BitSet(new long[]{0x00100000000C0820L,0x0000000C00000000L});
    public static final BitSet FOLLOW_charge_strength_in_net_declaration1621 = new BitSet(new long[]{0x00100000000C0800L,0x0000000C00000000L});
    public static final BitSet FOLLOW_expandrange_in_net_declaration1626 = new BitSet(new long[]{0x00100000000C0800L});
    public static final BitSet FOLLOW_delay_in_net_declaration1631 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_variables_in_net_declaration1646 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_net_declaration1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_net_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_expandrange1816 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_range_in_expandrange1818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_expandrange1823 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_range_in_expandrange1825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_range_in_expandrange1830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_reg_declaration1855 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_range_in_reg_declaration1858 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_register_variables_in_reg_declaration1862 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_reg_declaration1864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_time_declaration1889 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_register_variables_in_time_declaration1891 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_time_declaration1893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_integer_declaration1918 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_register_variables_in_integer_declaration1920 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_integer_declaration1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_real_declaration1947 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_variables_in_real_declaration1949 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_real_declaration1951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_event_declaration1976 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_event_in_event_declaration1978 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_COMMA_in_event_declaration1982 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_event_in_event_declaration1984 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_SEMI_in_event_declaration1989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KW_ASSIGN_in_continuous_assign2014 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_drive_strength_in_continuous_assign2017 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_delay_in_continuous_assign2022 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_list_of_assignments_in_continuous_assign2026 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_continuous_assign2028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_net_type_in_continuous_assign2040 = new BitSet(new long[]{0x00100000000C0A20L,0x0000000C00000000L});
    public static final BitSet FOLLOW_drive_strength_in_continuous_assign2043 = new BitSet(new long[]{0x00100000000C0A20L,0x0000000C00000000L});
    public static final BitSet FOLLOW_expandrange_in_continuous_assign2048 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_delay_in_continuous_assign2053 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_list_of_assignments_in_continuous_assign2068 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_continuous_assign2070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_parameter_override2095 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_list_of_param_assignments_in_parameter_override2097 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_parameter_override2099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_list_of_variables2124 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_variables2128 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_variable_in_list_of_variables2130 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_name_of_variable_in_list_of_assigned_variables2158 = new BitSet(new long[]{0x0000000000004042L});
    public static final BitSet FOLLOW_ASSIGN_in_list_of_assigned_variables2162 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_list_of_assigned_variables2164 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_assigned_variables2172 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_variable_in_list_of_assigned_variables2174 = new BitSet(new long[]{0x0000000000004042L});
    public static final BitSet FOLLOW_ASSIGN_in_list_of_assigned_variables2178 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_list_of_assigned_variables2180 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_register_variable_in_list_of_register_variables2211 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_register_variables2215 = new BitSet(new long[]{0x0010000000080800L});
    public static final BitSet FOLLOW_register_variable_in_list_of_register_variables2217 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_name_of_register_in_register_variable2245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_memory_in_register_variable2257 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_register_variable2259 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_register_variable2261 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_register_variable2263 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_register_variable2265 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_register_variable2267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_charge_strength2292 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_charge_strength2294 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_charge_strength2297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_charge_strength2309 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_charge_strength2311 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_charge_strength2313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_charge_strength2325 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_charge_strength2327 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_charge_strength2330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_drive_strength2355 = new BitSet(new long[]{0x0000000000000000L,0x0000780008000000L});
    public static final BitSet FOLLOW_strength0_in_drive_strength2357 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_drive_strength2359 = new BitSet(new long[]{0x0000000000000000L,0x0007800080000000L});
    public static final BitSet FOLLOW_strength1_in_drive_strength2361 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_drive_strength2363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_drive_strength2375 = new BitSet(new long[]{0x0000000000000000L,0x0007800080000000L});
    public static final BitSet FOLLOW_strength1_in_drive_strength2377 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_drive_strength2379 = new BitSet(new long[]{0x0000000000000000L,0x0000780008000000L});
    public static final BitSet FOLLOW_strength0_in_drive_strength2381 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_drive_strength2383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_strength00 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_strength10 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_range2551 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_range2553 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_range2555 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_range2557 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_range2559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_range2571 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_range2573 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_range2575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_list_of_assignments2600 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_assignments2604 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_assignment_in_list_of_assignments2606 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_gate_type_in_gate_declaration2632 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_drive_strength_in_gate_declaration2635 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_delay_in_gate_declaration2640 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_gate_instance_in_gate_declaration2654 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_COMMA_in_gate_declaration2658 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_gate_instance_in_gate_declaration2660 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_SEMI_in_gate_declaration2665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_gate_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay3008 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_NUMBER_in_delay3010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay3016 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_identifier_in_delay3018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay3030 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_delay3032 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_mintypmax_expression_in_delay3034 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_delay3047 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_mintypmax_expression_in_delay3049 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_delay3057 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_mintypmax_expression_in_delay3059 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_delay3076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_gate_instance_in_gate_instance3102 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_gate_instance3107 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_terminal_in_gate_instance3109 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_gate_instance3113 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_terminal_in_gate_instance3115 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_gate_instance3120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_UDP_in_udp_instantiation3138 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_drive_strength_in_udp_instantiation3141 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_delay_in_udp_instantiation3146 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_udp_instance_in_udp_instantiation3160 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_COMMA_in_udp_instantiation3164 = new BitSet(new long[]{0x00100000000C0020L});
    public static final BitSet FOLLOW_udp_instance_in_udp_instantiation3166 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_SEMI_in_udp_instantiation3171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_UDP_instance_in_udp_instance3190 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_udp_instance3195 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_terminal_in_udp_instance3197 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_udp_instance3201 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_terminal_in_udp_instance3203 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_udp_instance3208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_terminal3233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_module_in_module_instantiation3266 = new BitSet(new long[]{0x00100000000C0000L});
    public static final BitSet FOLLOW_parameter_value_assignment_in_module_instantiation3269 = new BitSet(new long[]{0x00100000000C0000L});
    public static final BitSet FOLLOW_module_instance_in_module_instantiation3281 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_COMMA_in_module_instantiation3285 = new BitSet(new long[]{0x00100000000C0000L});
    public static final BitSet FOLLOW_module_instance_in_module_instantiation3287 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_SEMI_in_module_instantiation3292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_parameter_value_assignment3317 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_parameter_value_assignment3319 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_parameter_value_assignment3321 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_parameter_value_assignment3325 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_parameter_value_assignment3327 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_parameter_value_assignment3332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_instance_in_module_instance3357 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_module_instance3359 = new BitSet(new long[]{0x0010003FDE488360L});
    public static final BitSet FOLLOW_list_of_module_connections_in_module_instance3361 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_module_instance3363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_port_connection_in_list_of_module_connections3388 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_module_connections3392 = new BitSet(new long[]{0x0010003FDE488260L});
    public static final BitSet FOLLOW_module_port_connection_in_list_of_module_connections3394 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_named_port_connection_in_list_of_module_connections3409 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_module_connections3413 = new BitSet(new long[]{0x0010003FDE488360L});
    public static final BitSet FOLLOW_named_port_connection_in_list_of_module_connections3415 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_expression_in_module_port_connection3443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_named_port_connection3476 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_named_port_connection3478 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_named_port_connection3480 = new BitSet(new long[]{0x0010003FDE4882A0L});
    public static final BitSet FOLLOW_expression_in_named_port_connection3483 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_named_port_connection3487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_initial_statement3516 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_initial_statement3518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_always_statement3543 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_always_statement3545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statement_or_null3576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMI_in_statement_or_null3581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blocking_assignment_in_statement3614 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_statement3616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_non_blocking_assignment_in_statement3636 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_statement3638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditional_statement_in_statement3650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_case_statement_in_statement3662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_statement_in_statement3674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedural_timing_control_statement_in_statement3686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_wait_statement_in_statement3698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_trigger_in_statement3710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_seq_block_in_statement3722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_par_block_in_statement3734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_task_enable_in_statement3746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_system_task_enable_in_statement3758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_disable_statement_in_statement3770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedural_continuous_assignment_in_statement3782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_assignment3807 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment3809 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_assignment3811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_blocking_assignment3836 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_blocking_assignment3838 = new BitSet(new long[]{0x0030003FDE4C8220L});
    public static final BitSet FOLLOW_delay_or_event_control_in_blocking_assignment3842 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_blocking_assignment3848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_non_blocking_assignment3873 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_LE_in_non_blocking_assignment3875 = new BitSet(new long[]{0x0030003FDE4C8220L});
    public static final BitSet FOLLOW_delay_or_event_control_in_non_blocking_assignment3879 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_non_blocking_assignment3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_conditional_statement3918 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_conditional_statement3920 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_conditional_statement3922 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_conditional_statement3924 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_conditional_statement3926 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_143_in_conditional_statement3940 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_conditional_statement3942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_case_keyword_in_case_statement3969 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_case_statement3971 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_case_statement3973 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_case_statement3975 = new BitSet(new long[]{0x0010003FDE488220L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_case_item_in_case_statement3978 = new BitSet(new long[]{0x0010003FDE488220L,0x0000000000000000L,0x0000000000110000L});
    public static final BitSet FOLLOW_144_in_case_statement3982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_case_keyword0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_case_item4040 = new BitSet(new long[]{0x0000000000001040L});
    public static final BitSet FOLLOW_COMMA_in_case_item4044 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_case_item4046 = new BitSet(new long[]{0x0000000000001040L});
    public static final BitSet FOLLOW_COLON_in_case_item4051 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_case_item4053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_148_in_case_item4065 = new BitSet(new long[]{0x00300000006E1210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_COLON_in_case_item4068 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_case_item4072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_149_in_loop_statement4097 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_loop_statement4099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_150_in_loop_statement4111 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_loop_statement4113 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_loop_statement4115 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_loop_statement4117 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_loop_statement4119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_151_in_loop_statement4131 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_loop_statement4133 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_loop_statement4135 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_loop_statement4137 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_loop_statement4139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_152_in_loop_statement4151 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_loop_statement4153 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_assignment_in_loop_statement4155 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_loop_statement4157 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_loop_statement4159 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_loop_statement4161 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_assignment_in_loop_statement4163 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_loop_statement4165 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_in_loop_statement4167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delay_or_event_control_in_procedural_timing_control_statement4192 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_procedural_timing_control_statement4194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_153_in_wait_statement4219 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_wait_statement4221 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_wait_statement4223 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_wait_statement4225 = new BitSet(new long[]{0x00300000006E0210L,0x0000007000FC0000L,0x00000003AFEE4000L});
    public static final BitSet FOLLOW_statement_or_null_in_wait_statement4227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRIGGER_in_event_trigger4252 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_event_in_event_trigger4254 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_event_trigger4256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_154_in_disable_statement4281 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_disable_statement4283 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_disable_statement4285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_155_in_seq_block4310 = new BitSet(new long[]{0x00300000006E1200L,0x0000000000000000L,0x00000003BFEE4000L});
    public static final BitSet FOLLOW_COLON_in_seq_block4322 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_block_in_seq_block4324 = new BitSet(new long[]{0x00300000006E0200L,0x00000070001C0000L,0x00000003BFEE4000L});
    public static final BitSet FOLLOW_block_declaration_in_seq_block4327 = new BitSet(new long[]{0x00300000006E0200L,0x00000070001C0000L,0x00000003BFEE4000L});
    public static final BitSet FOLLOW_statement_in_seq_block4343 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003BFEE4000L});
    public static final BitSet FOLLOW_156_in_seq_block4348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_157_in_par_block4366 = new BitSet(new long[]{0x00300000006E1200L,0x0000000000000000L,0x00000003EFEE4000L});
    public static final BitSet FOLLOW_COLON_in_par_block4378 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_name_of_block_in_par_block4380 = new BitSet(new long[]{0x00300000006E0200L,0x00000070001C0000L,0x00000003EFEE4000L});
    public static final BitSet FOLLOW_block_declaration_in_par_block4383 = new BitSet(new long[]{0x00300000006E0200L,0x00000070001C0000L,0x00000003EFEE4000L});
    public static final BitSet FOLLOW_statement_in_par_block4399 = new BitSet(new long[]{0x00300000006E0200L,0x0000000000000000L,0x00000003EFEE4000L});
    public static final BitSet FOLLOW_158_in_par_block4404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_declaration_in_block_declaration4429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reg_declaration_in_block_declaration4441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_declaration_in_block_declaration4453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_real_declaration_in_block_declaration4465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_declaration_in_block_declaration4477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_declaration_in_block_declaration4489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_task_in_task_enable4515 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_LPAREN_in_task_enable4519 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_task_enable4521 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_task_enable4524 = new BitSet(new long[]{0x0010003FDE4882E0L});
    public static final BitSet FOLLOW_expression_in_task_enable4527 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_task_enable4533 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_task_enable4539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SYSTEM_TASK_NAME_in_system_task_enable4564 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_LPAREN_in_system_task_enable4568 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_system_task_enable4570 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_task_enable4573 = new BitSet(new long[]{0x0010003FDE4882E0L});
    public static final BitSet FOLLOW_expression_in_system_task_enable4576 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_RPAREN_in_system_task_enable4582 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_task_enable4588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KW_ASSIGN_in_procedural_continuous_assignment4613 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_assignment_in_procedural_continuous_assignment4615 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_procedural_continuous_assignment4617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_159_in_procedural_continuous_assignment4629 = new BitSet(new long[]{0x0010000000080200L});
    public static final BitSet FOLLOW_lvalue_in_procedural_continuous_assignment4631 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_procedural_continuous_assignment4633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_160_in_procedural_continuous_assignment4645 = new BitSet(new long[]{0x00100000000C0220L});
    public static final BitSet FOLLOW_assignment_in_procedural_continuous_assignment4647 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_procedural_continuous_assignment4649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_161_in_procedural_continuous_assignment4661 = new BitSet(new long[]{0x0010000000080200L});
    public static final BitSet FOLLOW_lvalue_in_procedural_continuous_assignment4663 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_procedural_continuous_assignment4665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delay_control_in_delay_or_event_control4690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_event_control_in_delay_or_event_control4702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_162_in_specify_block4731 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000000L,0x00000FF800004000L});
    public static final BitSet FOLLOW_specify_item_in_specify_block4734 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000000L,0x00000FF800004000L});
    public static final BitSet FOLLOW_163_in_specify_block4738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_spec_param_declaration_in_specify_item4763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_path_declaration_in_specify_item4781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_system_timing_check_in_specify_item4793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sdpd_in_specify_item4805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_164_in_spec_param_declaration4830 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_specparam_assignments_in_spec_param_declaration4832 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_spec_param_declaration4834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specparam_assignment_in_list_of_specparam_assignments4859 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_specparam_assignments4863 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specparam_assignment_in_list_of_specparam_assignments4865 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_identifier_in_specparam_assignment4893 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_specparam_assignment4895 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_specparam_assignment4897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_path_declaration_in_path_declaration4932 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_path_declaration4934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_level_sensitive_path_declaration_in_path_declaration4956 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_path_declaration4958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_edge_sensitive_path_declaration_in_path_declaration4970 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_path_declaration4972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_path_description_in_simple_path_declaration5007 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_simple_path_declaration5009 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_value_in_simple_path_declaration5011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_full_path_descriptor_in_simple_path_declaration5023 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_simple_path_declaration5025 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_value_in_simple_path_declaration5027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_parallel_path_description5052 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_parallel_path_description5054 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_PPATH_in_parallel_path_description5056 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_parallel_path_description5058 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_parallel_path_description5060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_full_path_descriptor5085 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_full_path_descriptor5087 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_FPATH_in_full_path_descriptor5090 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_full_path_descriptor5092 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_full_path_descriptor5094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_list_of_path_terminals5119 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_terminals5123 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_list_of_path_terminals5125 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_identifier_in_specify_terminal_descriptor5169 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_specify_terminal_descriptor5171 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_specify_terminal_descriptor5173 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_specify_terminal_descriptor5175 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_specify_terminal_descriptor5177 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_specify_terminal_descriptor5179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_specify_terminal_descriptor5210 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_specify_terminal_descriptor5212 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_specify_terminal_descriptor5214 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACK_in_specify_terminal_descriptor5216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_specify_terminal_descriptor5228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_path_delay_expression_in_path_delay_value5259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_path_delay_value5271 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_list_of_path_delay_expressions_in_path_delay_value5273 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_path_delay_value5275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5300 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_delay_expressions5302 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5304 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_delay_expressions5311 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5313 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_delay_expressions5322 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5324 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_delay_expressions5326 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5342 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_list_of_path_delay_expressions5344 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_expression_in_list_of_path_delay_expressions5346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mintypmax_expression_in_path_delay_expression5378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_system_timing_check5403 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5405 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5407 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5409 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5411 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5413 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5427 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5431 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5433 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5438 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_166_in_system_timing_check5452 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5454 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5456 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5458 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5460 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5462 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5476 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5480 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5482 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5487 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_167_in_system_timing_check5501 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5503 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_controlled_timing_check_event_in_system_timing_check5505 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5507 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5521 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5525 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5527 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5532 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_system_timing_check5546 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5548 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_controlled_timing_check_event_in_system_timing_check5550 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5552 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5566 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5570 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_system_timing_check5572 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5574 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5576 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5586 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_169_in_system_timing_check5600 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5602 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5604 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5606 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5608 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5610 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5625 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5629 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5631 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5636 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_170_in_system_timing_check5650 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5652 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_controlled_timing_check_event_in_system_timing_check5654 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5656 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5670 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5672 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5674 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5690 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5692 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5697 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_171_in_system_timing_check5711 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_system_timing_check5713 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5715 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5717 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000E00000000000L});
    public static final BitSet FOLLOW_timing_check_event_in_system_timing_check5719 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5721 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5735 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5737 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_limit_in_system_timing_check5739 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_COMMA_in_system_timing_check5755 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_notify_register_in_system_timing_check5757 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_system_timing_check5762 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_system_timing_check5764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timing_check_event_control_in_timing_check_event5790 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_timing_check_event5794 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_172_in_timing_check_event5806 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_condition_in_timing_check_event5808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timing_check_event_control_in_controlled_timing_check_event5836 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_controlled_timing_check_event5838 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_172_in_controlled_timing_check_event5850 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_timing_check_condition_in_controlled_timing_check_event5852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_173_in_timing_check_event_control5880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_174_in_timing_check_event_control5892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_edge_control_specifier_in_timing_check_event_control5904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_175_in_edge_control_specifier5929 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_edge_control_specifier5931 = new BitSet(new long[]{0x0000000000088000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_edge_descriptor_in_edge_control_specifier5933 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_edge_control_specifier5937 = new BitSet(new long[]{0x0000000000088000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_edge_descriptor_in_edge_control_specifier5939 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACK_in_edge_control_specifier5944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_176_in_edge_descriptor5978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_177_in_edge_descriptor5982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_edge_descriptor5995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_edge_descriptor6011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scalar_timing_check_condition_in_timing_check_condition6032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_scalar_timing_check_condition6057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_timing_check_limit6082 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_register_in_notify_register6107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_level_sensitive_path_description_in_level_sensitive_path_declaration6132 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_level_sensitive_path_declaration6140 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_value_in_level_sensitive_path_declaration6142 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_level_sensitive_path_declaration6144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_full_level_sensitive_path_description_in_level_sensitive_path_declaration6155 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_level_sensitive_path_declaration6163 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_value_in_level_sensitive_path_declaration6165 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_level_sensitive_path_declaration6167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_parallel_level_sensitive_path_description6192 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_parallel_level_sensitive_path_description6194 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_parallel_level_sensitive_path_description6196 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_parallel_level_sensitive_path_description6198 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_parallel_level_sensitive_path_description6204 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_parallel_level_sensitive_path_description6206 = new BitSet(new long[]{0x0000000006800000L});
    public static final BitSet FOLLOW_polarity_operator_in_parallel_level_sensitive_path_description6209 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_PPATH_in_parallel_level_sensitive_path_description6224 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_parallel_level_sensitive_path_description6226 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_parallel_level_sensitive_path_description6228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_full_level_sensitive_path_description6246 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_full_level_sensitive_path_description6248 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_full_level_sensitive_path_description6250 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_full_level_sensitive_path_description6252 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_full_level_sensitive_path_description6258 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_full_level_sensitive_path_description6260 = new BitSet(new long[]{0x0000000007000000L});
    public static final BitSet FOLLOW_polarity_operator_in_full_level_sensitive_path_description6263 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_FPATH_in_full_level_sensitive_path_description6278 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_full_level_sensitive_path_description6280 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_full_level_sensitive_path_description6282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_polarity_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_edge_sensitive_path_declaration6311 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_edge_sensitive_path_declaration6313 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_edge_sensitive_path_declaration6315 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_edge_sensitive_path_declaration6317 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_edge_sensitive_path_declaration6330 = new BitSet(new long[]{0x0010000000080000L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_edge_identifier_in_edge_sensitive_path_declaration6333 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_edge_sensitive_path_declaration6337 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_set_in_edge_sensitive_path_declaration6343 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_edge_sensitive_path_declaration6357 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_edge_sensitive_path_declaration6367 = new BitSet(new long[]{0x0000000006001000L});
    public static final BitSet FOLLOW_specify_terminal_descriptor_in_edge_sensitive_path_declaration6384 = new BitSet(new long[]{0x0000000006001000L});
    public static final BitSet FOLLOW_polarity_operator_in_edge_sensitive_path_declaration6396 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_edge_sensitive_path_declaration6400 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_data_source_expression_in_edge_sensitive_path_declaration6402 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_edge_sensitive_path_declaration6408 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_edge_sensitive_path_declaration6411 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_edge_sensitive_path_declaration6414 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_path_delay_value_in_edge_sensitive_path_declaration6416 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_edge_sensitive_path_declaration6418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_data_source_expression6443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_edge_identifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_sdpd6498 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_sdpd6500 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_sdpd6502 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_sdpd6504 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_simple_path_declaration_in_sdpd6507 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_sdpd6510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_lvalue6541 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_range_in_lvalue6543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_lvalue6555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_concatenation_in_lvalue6567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_concatenation6603 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_concatenation6605 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_LCURLY_in_concatenation6615 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_concatenation6617 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_COMMA_in_concatenation6621 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_concatenation6623 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_RCURLY_in_concatenation6628 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RCURLY_in_concatenation6630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_concatenation6642 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_concatenation6644 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_COMMA_in_concatenation6648 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_concatenation6650 = new BitSet(new long[]{0x0000000000000440L});
    public static final BitSet FOLLOW_RCURLY_in_concatenation6655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_mintypmax_expression6680 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COLON_in_mintypmax_expression6684 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_mintypmax_expression6686 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_mintypmax_expression6688 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_mintypmax_expression6690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_exp116718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_exp116723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_exp116734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_exp116739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFINE_in_exp116744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp11_in_exp106769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_exp106773 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_exp106775 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_exp106777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp10_in_exp96802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_operator_in_exp96806 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_exp9_in_exp96808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp9_in_exp86833 = new BitSet(new long[]{0x000FFFF506100002L});
    public static final BitSet FOLLOW_binary_operator_in_exp86837 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_exp9_in_exp86839 = new BitSet(new long[]{0x000FFFF506100002L});
    public static final BitSet FOLLOW_exp8_in_exp76867 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_QUESTION_in_exp76871 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_exp7_in_exp76873 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_exp76875 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_exp7_in_exp76877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp7_in_exp06905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp0_in_expression6930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_function_in_function_call6955 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_function_call6957 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_list_in_function_call6959 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_function_call6961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SYSTEM_TASK_NAME_in_function_call6973 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_function_call6977 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_list_in_function_call6979 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_function_call6981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expression_list7009 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_expression_list7013 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_expression_list7015 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_set_in_unary_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_binary_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_module7635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_port7656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_variable7673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_UDP7695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_UDP_instance7708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_event7728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_task7749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_real_identifier7767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_memory7786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_net_identifier7805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_function7822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_specparam_identifier7835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_udp_name_of_port7852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_register7869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_gate_instance7881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_instance7898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_name_of_block7918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_output_terminal_name7931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_path_in_identifier7953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_local_identifier_in_identifier_path7978 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_DOT_in_identifier_path7982 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_local_identifier_in_identifier_path7984 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_set_in_local_identifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay_control8049 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_NUMBER_in_delay_control8051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay_control8063 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_identifier_in_delay_control8065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POUND_in_delay_control8077 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_delay_control8079 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_mintypmax_expression_in_delay_control8081 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_delay_control8083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_event_control8108 = new BitSet(new long[]{0x0010000000080000L});
    public static final BitSet FOLLOW_identifier_in_event_control8110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_event_control8122 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_event_control8124 = new BitSet(new long[]{0x0010003FDE488220L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_event_expression_in_event_control8126 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_event_control8128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sub_event_expression_in_event_expression8153 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L});
    public static final BitSet FOLLOW_117_in_event_expression8157 = new BitSet(new long[]{0x0010003FDE488220L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_sub_event_expression_in_event_expression8159 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L});
    public static final BitSet FOLLOW_expression_in_sub_event_expression8187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_173_in_sub_event_expression8199 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_sub_event_expression8201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_174_in_sub_event_expression8213 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_sub_event_expression8215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_define_directive_in_directive8236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_include_directive_in_directive8241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_178_in_define_directive8252 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_define_directive8254 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_define_directive8256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_179_in_include_directive8267 = new BitSet(new long[]{0x0010000008080000L});
    public static final BitSet FOLLOW_identifier_in_include_directive8271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_include_directive8275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_synpred1_Verilog332 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_synpred1_Verilog334 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_synpred1_Verilog336 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_synpred1_Verilog338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_of_variable_in_synpred2_Verilog376 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_synpred2_Verilog378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_instantiation_in_synpred3_Verilog673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_net_type_in_synpred4_Verilog1562 = new BitSet(new long[]{0x0000000000000802L,0x0000000C00000000L});
    public static final BitSet FOLLOW_expandrange_in_synpred4_Verilog1565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_synpred5_Verilog2534 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_synpred5_Verilog2536 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_synpred5_Verilog2538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_synpred6_Verilog3571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_synpred7_Verilog3607 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ASSIGN_in_synpred7_Verilog3609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_synpred8_Verilog3629 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_LE_in_synpred8_Verilog3631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_path_declaration_in_synpred9_Verilog4776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_path_declaration_in_synpred10_Verilog4923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_level_sensitive_path_declaration_in_synpred11_Verilog4947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_path_description_in_synpred12_Verilog4998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred13_Verilog5147 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_synpred13_Verilog5149 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_synpred13_Verilog5151 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COLON_in_synpred13_Verilog5153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred14_Verilog5192 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_synpred14_Verilog5194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_path_delay_expression_in_synpred15_Verilog5254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_level_sensitive_path_description_in_synpred16_Verilog6126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_of_path_terminals_in_synpred17_Verilog6362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_synpred18_Verilog6526 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_range_in_synpred18_Verilog6528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_synpred19_Verilog6586 = new BitSet(new long[]{0x0010003FDE488220L});
    public static final BitSet FOLLOW_expression_in_synpred19_Verilog6588 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_LCURLY_in_synpred19_Verilog6590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_synpred20_Verilog6729 = new BitSet(new long[]{0x0000000000000002L});

}