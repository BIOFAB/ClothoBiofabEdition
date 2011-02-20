/*
 * Copyright Bing Xia (bxia@bxia.net)
 */
package org.clothocad.connection.configurableconnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.clothocad.hibernate.HibernateConnection;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjBase.ObjBaseDatum;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.plugin.ClothoConnection;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class ConfigurableConnection implements ClothoConnection {

    public ConfigurableConnection() {
        _mappings = new ArrayList<URL>();
        try {
            _conn = new HibernateConnection();
            FileObject configFile = FileUtil.getConfigFile( "data/private/org.clothocad.connection.configurableconnection/" ).getFileObject( "hibernate.cfg.xml" );
            _configFile = configFile.getURL();
            List<FileObject> defaultMappings = HibernateConnection.getDefaultMappings();
            for ( FileObject map : defaultMappings ) {
                _mappings.add( map.getURL() );
            }
        } catch ( Exception ex ) {
            Logger.getLogger( ConfigurableConnection.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    @Override
    public boolean connect() {
        return _conn.connect( _configFile, _mappings );
    }

    @Override
    public boolean isAClothoDatabase() {
        return true;
    }

    @Override
    public void init() {
    }

    @Override
    public boolean disconnect() {
        return _conn.disconnect();
    }

    @Override
    public boolean isConnected() {
        return _conn.isConnected();
    }

    @Override
    public boolean save( ObjBase obj ) {
        return _conn.save( obj );
    }

    @Override
    public int save( Collection<ObjBase> objs ) {
        return _conn.save( objs );
    }

    @Override
    public boolean delete( ObjBase obj ) {
        return _conn.delete( obj );
    }

    @Override
    public int delete( Collection<ObjBase> objs ) {
        return _conn.delete( objs );
    }

    @Override
    public Date getTimeModified( ObjBase obj ) {
        return _conn.getTimeModified( obj );
    }

    @Override
    public ObjBase get( ObjType type, String uuid ) {
        return _conn.get( type, uuid );
    }

    @Override
    public Collection<ObjBase> get( String hibQuery ) {
        return _conn.get( hibQuery );
    }

    @Override
    public String getObjectTranslation( ObjType type ) {
        return _conn.getObjectTranslation( type );
    }

    @Override
    public String getFieldTranslation( ObjType type, Enum field ) {
        return _conn.getFieldTranslation( type, field );
    }

    @Override
    public ArrayList<ObjLink> getAllLinks( ObjType type ) {
        return _conn.getAllLinks( type );
    }

    @Override
    public String[][] getTableAsArray( ObjType type ) {
        return _conn.getTableAsArray( type );
    }

    @Override
    public ClothoQuery createQuery( ObjType type ) {
        return _conn.createQuery( type );
    }
    private HibernateConnection _conn;
    private URL _configFile;
    private List<URL> _mappings;

    @Override
    public ObjBaseDatum getDatum(ObjType type, String uuid) {
        return _conn.getDatum( type, uuid );
    }
}
