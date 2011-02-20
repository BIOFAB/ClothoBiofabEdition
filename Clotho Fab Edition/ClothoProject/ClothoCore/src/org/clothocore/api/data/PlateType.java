/*
Copyright (c) 2009 The Regents of the University of California.
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
package org.clothocore.api.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import org.clothocore.api.core.Collector;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.openide.util.Exceptions;

/**
 *
 * @author J. Christopher Anderson
 */
public class PlateType extends ObjBase {

    /**
     * Constructor from raw data CHANGE THIS!!!!!!!!!!!!!!!!!!
     * @param name name of the PlateType
     * @param numrows number of rows in the plate
     * @param numcols number of columns in the plate
     * @param isfixed whether the containers in the plate can be moved or not
     * @param iconSetLocation
     */
    public PlateType( String name, int numrows, int numcols, boolean isfixed, String iconSetLocation ) {
        super();
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
    }

    public PlateType( PlateTypeDatum d ) {
        super( d );
        _pTypeDatum = d;

//        attachmentUUID = aatt;
//        _myName = aname;
//        _numRows = anumRows;
//        _numCols = anumColumns;
//        _isContainerFixed = aisContainerFixed;
//        _dateCreated = adateCreated;
//        wellPosX = awellPosX;
//        _lastModified = alastModified;
//        wellPosY = awellPosY;
//        wellDiameter = awellDiameter;
//        squareWells = asquareWells;
//        squareWellHeight = asquareWellHeight;
//        squareWellWidth = asquareWellWidth;
//        mmWidth = ammWidth;
//        mmHeight = ammHeight;
//        mmXOffset = ammXoffset;
//        mmYOffset = ammYoffset;
//        mmXWellSpacing = ammXwellSpacing;
//        mmYWellSpacing = ammYwellSpacing;
//        mmWellWidth = ammWellWidth;
//        mmWellHeight = ammWellHeight;
//        mmWellDiameter = ammWellDiameter;

    }

    @Override
    public ObjType getType() {
        return ObjType.PLATE_TYPE;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        int[][] awellPosX = parseIntString( (String) objHash.get( "wellPosX" ) );
        int[][] awellPosY = parseIntString( (String) objHash.get( "wellPosY" ) );

        String aatt = (String) objHash.get( "attachmentUUID" );
        boolean asquareWells = Boolean.parseBoolean( (String) objHash.get( "squareWells" ) );
        boolean aisContainerFixed = Boolean.parseBoolean( (String) objHash.get( "_isContainerFixed" ) );
        int awellDiameter = getInt( "wellDiameter", objHash );
        int asquareWellHeight = getInt( "squareWellHeight", objHash );
        int asquareWellWidth = getInt( "squareWellWidth", objHash );

        String snumRows = (String) objHash.get( "_numRows" );
        short anumRows = Short.parseShort( snumRows );
        String snumCols = (String) objHash.get( "_numCols" );
        short anumColumns = Short.parseShort( snumCols );

        double ammWidth = getDouble( "mmWidth", objHash );
        double ammHeight = getDouble( "mmHeight", objHash );
        double ammXoffset = getDouble( "mmXOffset", objHash );
        double ammYoffset = getDouble( "mmYOffset", objHash );
        double ammXwellSpacing = getDouble( "mmXWellSpacing", objHash );
        double ammYwellSpacing = getDouble( "mmYWellSpacing", objHash );
        double ammWellWidth = getDouble( "mmWellWidth", objHash );
        double ammWellHeight = getDouble( "mmWellHeight", objHash );
        double ammWellDiameter = getDouble( "mmWellDiameter", objHash );

        PlateTypeDatum d = new PlateTypeDatum();

        d.uuid = uuid;
        d.attachmentUUID = aatt;
        d.name = name;
        d._numRows = anumRows;
        d._numCols = anumColumns;
        d._isContainerFixed = aisContainerFixed;
        d.dateCreated = dateCreated;
        d.wellPosX = awellPosX;
        d.lastModified = lastModified;
        d.wellPosY = awellPosY;
        d.wellDiameter = awellDiameter;
        d.squareWells = asquareWells;
        d.squareWellHeight = asquareWellHeight;
        d.squareWellWidth = asquareWellWidth;
        d.mmWidth = ammWidth;
        d.mmHeight = ammHeight;
        d.mmXOffset = ammXoffset;
        d.mmYOffset = ammYoffset;
        d.mmXWellSpacing = ammXwellSpacing;
        d.mmYWellSpacing = ammYwellSpacing;
        d.mmWellWidth = ammWellWidth;
        d.mmWellHeight = ammWellHeight;
        d.mmWellDiameter = ammWellDiameter;


        return new PlateType( d );
    }

    private static int getInt( String tag, HashMap<String, Object> objHash ) {
        String str = (String) objHash.get( tag );
        int out = Integer.parseInt( str );
        return out;
    }

    private static double getDouble( String tag, HashMap<String, Object> objHash ) {
        String str = (String) objHash.get( tag );
        double out = Double.parseDouble( str );
        return out;
    }

    public String getWellPos( boolean isx ) {
        String out = "";
        for ( int i = 0; i < _pTypeDatum._numRows; i++ ) {
            for ( int j = 0; j < _pTypeDatum._numCols; j++ ) {
                if ( isx ) {
                    out += _pTypeDatum.wellPosX[i][j];
                } else {
                    out += _pTypeDatum.wellPosY[i][j];
                }
                if ( j < 11 ) {
                    out += ",";
                } else {
                    out += ".";
                }
            }
        }
        int len = out.length() - 1;
        return out.substring( 0, len );
    }

    public static int[][] parseIntString( String instring ) {
        String[] lines = instring.split( "\\." );
        int cols = lines[0].split( "\\," ).length;
        int[][] out = new int[ lines.length ][ cols ];
        for ( int i = 0; i < lines.length; i++ ) {
            String aline = lines[i];
            String[] arow = aline.split( "\\," );
            for ( int j = 0; j < cols; j++ ) {
                out[i][j] = Integer.parseInt( arow[j] );
            }
        }

        return out;
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> generateXml( HashMap<String, HashMap<String, Object>> allObjects ) {
        //If the hash already has the object, skip adding anything
        if ( allObjects.containsKey( getUUID() ) ) {
            return allObjects;
        }

        //Fill in the individual fields
        HashMap<String, Object> datahash = new HashMap<String, Object>();
        datahash.put( "objType", getType().toString() );
        datahash.put( "uuid", _pTypeDatum.uuid );
        datahash.put( "name", _pTypeDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "attachmentUUID", _pTypeDatum.attachmentUUID );
        datahash.put( "squareWells", Boolean.toString( _pTypeDatum.squareWells ) );
        datahash.put( "_isContainerFixed", Boolean.toString( _pTypeDatum._isContainerFixed ) );

        datahash.put( "wellDiameter", Integer.toString( _pTypeDatum.wellDiameter ) );
        datahash.put( "squareWellHeight", Integer.toString( _pTypeDatum.squareWellHeight ) );
        datahash.put( "squareWellWidth", Integer.toString( _pTypeDatum.squareWellWidth ) );

        datahash.put( "_numRows", Integer.toString( _pTypeDatum._numRows ) );
        datahash.put( "_numCols", Integer.toString( _pTypeDatum._numCols ) );

        datahash.put( "mmWidth", Double.toString( _pTypeDatum.mmWidth ) );
        datahash.put( "mmHeight", Double.toString( _pTypeDatum.mmHeight ) );
        datahash.put( "mmXOffset", Double.toString( _pTypeDatum.mmXOffset ) );
        datahash.put( "mmYOffset", Double.toString( _pTypeDatum.mmYOffset ) );
        datahash.put( "mmXWellSpacing", Double.toString( _pTypeDatum.mmXWellSpacing ) );
        datahash.put( "mmYWellSpacing", Double.toString( _pTypeDatum.mmYWellSpacing ) );
        datahash.put( "mmWellWidth", Double.toString( _pTypeDatum.mmWellWidth ) );
        datahash.put( "mmWellHeight", Double.toString( _pTypeDatum.mmWellHeight ) );
        datahash.put( "mmWellDiameter", Double.toString( _pTypeDatum.mmWellDiameter ) );

        datahash.put( "wellPosX", this.getWellPos( true ) );
        datahash.put( "wellPosY", this.getWellPos( false ) );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Return the datahash
        return allObjects;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    public short getNumRows() {
        return (short) _pTypeDatum._numRows;
    }

    public short getNumCols() {
        return (short) _pTypeDatum._numCols;
    }

    /* SETTERS
     * */

    /* PUTTERS
     * */

    /* GETTERS
     * */
    public boolean isContainerFixed() {
        return _pTypeDatum._isContainerFixed;
    }

    public int getRows() {
        return _pTypeDatum._numRows;
    }

    public int getColumns() {
        return _pTypeDatum._numCols;
    }

    public BufferedImage getPlateImage() {
        if ( _pTypeDatum.plateImage != null ) {
            return _pTypeDatum.plateImage;
        }
        Attachment img = Collector.getAttachment( _pTypeDatum.attachmentUUID );
        File imgfile = img.getFile();
        try {
            _pTypeDatum.plateImage = ImageIO.read( imgfile );
        } catch ( IOException ex ) {
            Exceptions.printStackTrace( ex );
        }
        return _pTypeDatum.plateImage;
    }

    public int[][] getWellPositionX() {
        return _pTypeDatum.wellPosX;
    }

    public int[][] getWellPositionY() {
        return _pTypeDatum.wellPosY;
    }

    public int getWellDiameter() {
        return _pTypeDatum.wellDiameter;
    }

    public boolean isSquareWells() {
        return _pTypeDatum.squareWells;
    }

    public int getSquareWellWidth() {
        return _pTypeDatum.squareWellWidth;
    }

    public int getSquareWellHeight() {
        return _pTypeDatum.squareWellHeight;
    }

    //Parameters for calculated dimensions of plate (in millimeters)
    public double getWidthInMillimeters() {
        return _pTypeDatum.mmWidth;
    }

    public double getHeightInMillimeters() {
        return _pTypeDatum.mmHeight;
    }

    public double getXOffsetInMillimeters() {
        return _pTypeDatum.mmXOffset;
    }

    public double getYOffsetInMillimeters() {
        return _pTypeDatum.mmYOffset;
    }

    public double getXWellSpacingInMillimeters() {
        return _pTypeDatum.mmXWellSpacing;
    }

    public double getYWellSpacingInMillimeters() {
        return _pTypeDatum.mmYWellSpacing;
    }

    public double getWellWidthInMillimeters() {
        return _pTypeDatum.mmWellWidth;
    }

    public double getWellHeightInMillimeters() {
        return _pTypeDatum.mmWellHeight;
    }

    public double getWellDiameterInMillimeters() {
        return _pTypeDatum.mmWellDiameter;
    }

    public static PlateType retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PLATE_TYPE );
        cq.eq( PlateType.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        PlateType p = (PlateType) l.get( 0 );
        return p;
    }

    public String getAttachmentUUID() {
        return _pTypeDatum.attachmentUUID;
    }
    /*-----------------
    variables
    -----------------*/
    private PlateTypeDatum _pTypeDatum;


    public static class PlateTypeDatum extends ObjBaseDatum {

        public int _numCols;
        public int _numRows;
        public BufferedImage plateImage;
        public String attachmentUUID;
        //Parameters for image of plate
        public int[][] wellPosX;
        public int[][] wellPosY;
        public int wellDiameter = 27;
        public boolean squareWells = false;
        public int squareWellHeight = 27;
        public int squareWellWidth = 27;
        //Parameters for calculated dimensions of plate (in millimeters)
        public double mmWidth = 124.28;
        public double mmHeight = 83.97;
        public double mmXOffset = 12.83 - 2.73;
        public double mmYOffset = 10.49 - 2.73;
        public double mmXWellSpacing = 9.0;
        public double mmYWellSpacing = 9.0;
        public double mmWellWidth = 4;
        public double mmWellHeight = 4;
        public double mmWellDiameter = 5.46;
        public boolean _isContainerFixed = true;

        @Override
        public ObjType getType() {
            return ObjType.PLATE_TYPE;
        }
    }
    //  public String _iconSetLocation;
    //  String _pluginDownloadLink;

    private void temporaryCalcWellPos() {
        _pTypeDatum.wellPosX = new int[ 8 ][ 12 ];
        _pTypeDatum.wellPosY = new int[ 8 ][ 12 ];
        _pTypeDatum.wellPosY[0][0] = 35;
        _pTypeDatum.wellPosY[0][1] = 35;
        _pTypeDatum.wellPosY[0][2] = 33;
        _pTypeDatum.wellPosY[0][3] = 33;
        _pTypeDatum.wellPosY[0][4] = 34;
        _pTypeDatum.wellPosY[0][5] = 34;
        _pTypeDatum.wellPosY[0][6] = 33;
        _pTypeDatum.wellPosY[0][7] = 34;
        _pTypeDatum.wellPosY[0][8] = 33;
        _pTypeDatum.wellPosY[0][9] = 33;
        _pTypeDatum.wellPosY[0][10] = 33;
        _pTypeDatum.wellPosY[0][11] = 34;
        _pTypeDatum.wellPosY[1][0] = 79;
        _pTypeDatum.wellPosY[1][1] = 79;
        _pTypeDatum.wellPosY[1][2] = 79;
        _pTypeDatum.wellPosY[1][3] = 78;
        _pTypeDatum.wellPosY[1][4] = 79;
        _pTypeDatum.wellPosY[1][5] = 79;
        _pTypeDatum.wellPosY[1][6] = 78;
        _pTypeDatum.wellPosY[1][7] = 77;
        _pTypeDatum.wellPosY[1][8] = 78;
        _pTypeDatum.wellPosY[1][9] = 78;
        _pTypeDatum.wellPosY[1][10] = 78;
        _pTypeDatum.wellPosY[1][11] = 78;
        _pTypeDatum.wellPosY[2][0] = 124;
        _pTypeDatum.wellPosY[2][1] = 124;
        _pTypeDatum.wellPosY[2][2] = 123;
        _pTypeDatum.wellPosY[2][3] = 124;
        _pTypeDatum.wellPosY[2][4] = 124;
        _pTypeDatum.wellPosY[2][5] = 124;
        _pTypeDatum.wellPosY[2][6] = 123;
        _pTypeDatum.wellPosY[2][7] = 123;
        _pTypeDatum.wellPosY[2][8] = 123;
        _pTypeDatum.wellPosY[2][9] = 123;
        _pTypeDatum.wellPosY[2][10] = 123;
        _pTypeDatum.wellPosY[2][11] = 123;
        _pTypeDatum.wellPosY[3][0] = 169;
        _pTypeDatum.wellPosY[3][1] = 169;
        _pTypeDatum.wellPosY[3][2] = 168;
        _pTypeDatum.wellPosY[3][3] = 168;
        _pTypeDatum.wellPosY[3][4] = 168;
        _pTypeDatum.wellPosY[3][5] = 168;
        _pTypeDatum.wellPosY[3][6] = 168;
        _pTypeDatum.wellPosY[3][7] = 168;
        _pTypeDatum.wellPosY[3][8] = 168;
        _pTypeDatum.wellPosY[3][9] = 168;
        _pTypeDatum.wellPosY[3][10] = 168;
        _pTypeDatum.wellPosY[3][11] = 168;
        _pTypeDatum.wellPosY[4][0] = 214;
        _pTypeDatum.wellPosY[4][1] = 214;
        _pTypeDatum.wellPosY[4][2] = 213;
        _pTypeDatum.wellPosY[4][3] = 213;
        _pTypeDatum.wellPosY[4][4] = 213;
        _pTypeDatum.wellPosY[4][5] = 213;
        _pTypeDatum.wellPosY[4][6] = 213;
        _pTypeDatum.wellPosY[4][7] = 213;
        _pTypeDatum.wellPosY[4][8] = 213;
        _pTypeDatum.wellPosY[4][9] = 213;
        _pTypeDatum.wellPosY[4][10] = 213;
        _pTypeDatum.wellPosY[4][11] = 212;
        _pTypeDatum.wellPosY[5][0] = 258;
        _pTypeDatum.wellPosY[5][1] = 258;
        _pTypeDatum.wellPosY[5][2] = 258;
        _pTypeDatum.wellPosY[5][3] = 257;
        _pTypeDatum.wellPosY[5][4] = 258;
        _pTypeDatum.wellPosY[5][5] = 257;
        _pTypeDatum.wellPosY[5][6] = 258;
        _pTypeDatum.wellPosY[5][7] = 257;
        _pTypeDatum.wellPosY[5][8] = 257;
        _pTypeDatum.wellPosY[5][9] = 258;
        _pTypeDatum.wellPosY[5][10] = 258;
        _pTypeDatum.wellPosY[5][11] = 258;
        _pTypeDatum.wellPosY[6][0] = 304;
        _pTypeDatum.wellPosY[6][1] = 304;
        _pTypeDatum.wellPosY[6][2] = 302;
        _pTypeDatum.wellPosY[6][3] = 303;
        _pTypeDatum.wellPosY[6][4] = 302;
        _pTypeDatum.wellPosY[6][5] = 302;
        _pTypeDatum.wellPosY[6][6] = 302;
        _pTypeDatum.wellPosY[6][7] = 302;
        _pTypeDatum.wellPosY[6][8] = 302;
        _pTypeDatum.wellPosY[6][9] = 303;
        _pTypeDatum.wellPosY[6][10] = 302;
        _pTypeDatum.wellPosY[6][11] = 303;
        _pTypeDatum.wellPosY[7][0] = 348;
        _pTypeDatum.wellPosY[7][1] = 348;
        _pTypeDatum.wellPosY[7][2] = 348;
        _pTypeDatum.wellPosY[7][3] = 347;
        _pTypeDatum.wellPosY[7][4] = 347;
        _pTypeDatum.wellPosY[7][5] = 347;
        _pTypeDatum.wellPosY[7][6] = 347;
        _pTypeDatum.wellPosY[7][7] = 347;
        _pTypeDatum.wellPosY[7][8] = 348;
        _pTypeDatum.wellPosY[7][9] = 348;
        _pTypeDatum.wellPosY[7][10] = 348;
        _pTypeDatum.wellPosY[7][11] = 348;

        _pTypeDatum.wellPosX[0][0] = 46;
        _pTypeDatum.wellPosX[0][1] = 89;
        _pTypeDatum.wellPosX[0][2] = 133;
        _pTypeDatum.wellPosX[0][3] = 176;
        _pTypeDatum.wellPosX[0][4] = 221;
        _pTypeDatum.wellPosX[0][5] = 265;
        _pTypeDatum.wellPosX[0][6] = 308;
        _pTypeDatum.wellPosX[0][7] = 353;
        _pTypeDatum.wellPosX[0][8] = 396;
        _pTypeDatum.wellPosX[0][9] = 440;
        _pTypeDatum.wellPosX[0][10] = 484;
        _pTypeDatum.wellPosX[0][11] = 528;
        _pTypeDatum.wellPosX[1][0] = 44;
        _pTypeDatum.wellPosX[1][1] = 88;
        _pTypeDatum.wellPosX[1][2] = 133;
        _pTypeDatum.wellPosX[1][3] = 176;
        _pTypeDatum.wellPosX[1][4] = 222;
        _pTypeDatum.wellPosX[1][5] = 266;
        _pTypeDatum.wellPosX[1][6] = 309;
        _pTypeDatum.wellPosX[1][7] = 352;
        _pTypeDatum.wellPosX[1][8] = 397;
        _pTypeDatum.wellPosX[1][9] = 440;
        _pTypeDatum.wellPosX[1][10] = 484;
        _pTypeDatum.wellPosX[1][11] = 529;
        _pTypeDatum.wellPosX[2][0] = 44;
        _pTypeDatum.wellPosX[2][1] = 88;
        _pTypeDatum.wellPosX[2][2] = 132;
        _pTypeDatum.wellPosX[2][3] = 176;
        _pTypeDatum.wellPosX[2][4] = 221;
        _pTypeDatum.wellPosX[2][5] = 265;
        _pTypeDatum.wellPosX[2][6] = 308;
        _pTypeDatum.wellPosX[2][7] = 353;
        _pTypeDatum.wellPosX[2][8] = 397;
        _pTypeDatum.wellPosX[2][9] = 441;
        _pTypeDatum.wellPosX[2][10] = 484;
        _pTypeDatum.wellPosX[2][11] = 529;
        _pTypeDatum.wellPosX[3][0] = 44;
        _pTypeDatum.wellPosX[3][1] = 88;
        _pTypeDatum.wellPosX[3][2] = 132;
        _pTypeDatum.wellPosX[3][3] = 176;
        _pTypeDatum.wellPosX[3][4] = 221;
        _pTypeDatum.wellPosX[3][5] = 265;
        _pTypeDatum.wellPosX[3][6] = 309;
        _pTypeDatum.wellPosX[3][7] = 353;
        _pTypeDatum.wellPosX[3][8] = 397;
        _pTypeDatum.wellPosX[3][9] = 441;
        _pTypeDatum.wellPosX[3][10] = 485;
        _pTypeDatum.wellPosX[3][11] = 530;
        _pTypeDatum.wellPosX[4][0] = 44;
        _pTypeDatum.wellPosX[4][1] = 87;
        _pTypeDatum.wellPosX[4][2] = 132;
        _pTypeDatum.wellPosX[4][3] = 176;
        _pTypeDatum.wellPosX[4][4] = 220;
        _pTypeDatum.wellPosX[4][5] = 264;
        _pTypeDatum.wellPosX[4][6] = 309;
        _pTypeDatum.wellPosX[4][7] = 353;
        _pTypeDatum.wellPosX[4][8] = 397;
        _pTypeDatum.wellPosX[4][9] = 441;
        _pTypeDatum.wellPosX[4][10] = 485;
        _pTypeDatum.wellPosX[4][11] = 530;
        _pTypeDatum.wellPosX[5][0] = 43;
        _pTypeDatum.wellPosX[5][1] = 87;
        _pTypeDatum.wellPosX[5][2] = 131;
        _pTypeDatum.wellPosX[5][3] = 176;
        _pTypeDatum.wellPosX[5][4] = 220;
        _pTypeDatum.wellPosX[5][5] = 264;
        _pTypeDatum.wellPosX[5][6] = 308;
        _pTypeDatum.wellPosX[5][7] = 353;
        _pTypeDatum.wellPosX[5][8] = 398;
        _pTypeDatum.wellPosX[5][9] = 442;
        _pTypeDatum.wellPosX[5][10] = 486;
        _pTypeDatum.wellPosX[5][11] = 530;
        _pTypeDatum.wellPosX[6][0] = 42;
        _pTypeDatum.wellPosX[6][1] = 87;
        _pTypeDatum.wellPosX[6][2] = 131;
        _pTypeDatum.wellPosX[6][3] = 176;
        _pTypeDatum.wellPosX[6][4] = 220;
        _pTypeDatum.wellPosX[6][5] = 264;
        _pTypeDatum.wellPosX[6][6] = 309;
        _pTypeDatum.wellPosX[6][7] = 353;
        _pTypeDatum.wellPosX[6][8] = 398;
        _pTypeDatum.wellPosX[6][9] = 442;
        _pTypeDatum.wellPosX[6][10] = 486;
        _pTypeDatum.wellPosX[6][11] = 531;
        _pTypeDatum.wellPosX[7][0] = 42;
        _pTypeDatum.wellPosX[7][1] = 87;
        _pTypeDatum.wellPosX[7][2] = 131;
        _pTypeDatum.wellPosX[7][3] = 175;
        _pTypeDatum.wellPosX[7][4] = 220;
        _pTypeDatum.wellPosX[7][5] = 264;
        _pTypeDatum.wellPosX[7][6] = 309;
        _pTypeDatum.wellPosX[7][7] = 353;
        _pTypeDatum.wellPosX[7][8] = 399;
        _pTypeDatum.wellPosX[7][9] = 442;
        _pTypeDatum.wellPosX[7][10] = 487;
        _pTypeDatum.wellPosX[7][11] = 532;
        try {
            // TODO: fix this image path!
            _pTypeDatum.plateImage = ImageIO.read( new File( "C:/Users/jcanderson/Documents/NetBeansProjects/clothoCore/data/icons/pcr96_bkg.png" ) );
        } catch ( IOException ex ) {
            Exceptions.printStackTrace( ex );
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        NUM_ROWS,
        NUM_COLS,
        WELL_RADIUS,
        HAS_SQUARE_WELLS,
        IS_CONTAINER_FIXED,
    }
}
