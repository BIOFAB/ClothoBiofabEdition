/*
 * 
Copyright (c) 2010 The Regents of the University of California.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS..
 */

package org.clothocad.tool.groovyconsoletc;

import org.clothocad.library.groovytranslator.*;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.plugin.ClothoTool;

/**
 *
 * @author jcanderson_Home
 */
public class Connect implements ClothoTool {

    @Override
    public void launch() {
        groovy.ui.Console console = new groovy.ui.Console();
        console.setVariable("ObjType", new groovyObjType());
        console.setVariable("Collector", new groovyCollector());
        console.setVariable("Collator", new groovyCollator());
        console.setVariable("ObjBase", new groovyObjBase());
        console.setVariable("Part", new groovyPart());
        console.setVariable("Format", new groovyFormat());
        console.setVariable("Vector", new groovyVector());
        console.setVariable("Strain", new groovyStrain());
        console.setVariable("Plasmid", new groovyPlasmid());
        console.setVariable("Person", new groovyPerson());
        console.setVariable("Port", new groovyPort());
        console.setVariable("Lab", new groovyLab());
        console.setVariable("Institution", new groovyInstitution());
        console.setVariable("Collection", new groovyCollection());
        console.setVariable("Feature", new groovyFeature());
        console.setVariable("Actor", new groovyActor());
        console.run();
        console.getInputArea().setText("import org.clothocore.api.actor.*\nimport org.clothocore.api.actor.data.*\nimport org.clothocore.api.actor.io.*\nimport org.clothocore.api.actor.workflow.*\nimport org.clothocore.api.core.*\nimport org.clothocore.api.core.wrapper.*\nimport org.clothocore.api.core.wrapper.support.*\nimport org.clothocore.api.data.*\nimport org.clothocore.api.dnd.*\nimport org.clothocore.core.*\nimport org.clothocore.images.*\nimport org.clothocore.api.plugin.*\nimport org.clothocore.util.basic.*\nimport org.clothocore.util.blast.*\nimport org.clothocore.util.buttons.*\nimport org.clothocore.util.chooser.*\nimport org.clothocore.util.def.*\nimport org.clothocore.util.dialog.*\nimport org.clothocore.util.elements.*\nimport org.clothocore.util.frames.*\nimport org.clothocore.util.misc.*\nimport org.clothocore.util.panels.*\nimport org.clothocore.util.xml.*\nimport org.clothocad.library.groovytranslator.*\n");
        console.runScript();
        console.getInputArea().setText("");
        console.clearOutput();
    }

    @Override
    public void launch(ObjBase o) {

    }

    @Override
    public void close() {

    }

    @Override
    public void init() {

    }



    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
}
