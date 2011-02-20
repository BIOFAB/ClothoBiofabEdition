/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocad.algorithm.stringappend;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.RunStatus;
import org.clothocore.api.actor.data.StringToken;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class StringAppendAlgorithm extends Actor {

    public StringAppendAlgorithm() {
        _inp1 = new InputPort<StringToken>( this, StringToken.class );
        _inp2 = new InputPort<StringToken>( this, StringToken.class );
        _out = new OutputPort<StringToken>( this, StringToken.class );
    }

    @Override
    public RunStatus run() {
        if ( !isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        StringToken tok1 = _inp1.get();
        StringToken tok2 = _inp2.get();
        _inp1.clear();
        _inp2.clear();
        String result = tok1.getData() + tok2.getData();

        StringToken out = new StringToken( result );

        _out.put( out );

        return RunStatus.COMPLETE;
    }

    @Override
    public String getName() {
        return "String Appender";
    }
    /***** Variables *****/
    private InputPort<StringToken> _inp1, _inp2;
    private OutputPort<StringToken> _out;
}
