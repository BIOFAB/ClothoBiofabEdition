/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.inventorymanager;

import org.clothocore.api.data.ObjBase;
import org.clothocore.api.plugin.ClothoTool;

/**
 *
 * @author cesarr
 */
public class InventoryManagerTool implements ClothoTool
{
    @Override
    public void launch()
    {
        InventoryManagerTopComponent component = InventoryManagerTopComponent.findInstance();
    }

    @Override
    public void launch(ObjBase o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init()
    {
       //throw new UnsupportedOperationException("Not supported yet.");
    }
}
