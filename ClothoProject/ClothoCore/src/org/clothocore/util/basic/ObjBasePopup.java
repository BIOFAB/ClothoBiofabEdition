package org.clothocore.util.basic;

import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.data.*;
import org.clothocore.util.elements.AutoComboBox;




public class ObjBasePopup {

    /**
     * Create an ObjBase popup, but don't show it.  This constructor
     * will log the appropriate listeners onto the GUI such that the popup
     * shows up on right click.
     *
     * @param Container
     * @param o
     */
     public ObjBasePopup(Component container, ObjBase o) {
         _myObject = o;
         _guiComponent = container;

         initiatePopup(o);

         // define a MouseListener for the window that displays
         // a JPopupMenu when the popup trigger event occurs
         _guiComponent.addMouseListener( new MouseAdapter()
             {
            @Override
             public void mousePressed( MouseEvent e )
                 {
                 checkForTriggerEvent( e );
             }

            @Override
             public void mouseReleased( MouseEvent e )
                 {
                 checkForTriggerEvent( e );
             }

             private void checkForTriggerEvent( MouseEvent e )
                 {
                 if ( e.isPopupTrigger() && _isActive)
                 popupMenu.show( e.getComponent(), e.getX(), e.getY() );
             }
         } );
     }

     /**
      * Create and show an ObjBasePopup without the listeners on the calling
      * Component.  No listeners get logged in by this constructor, the popup
      * just appears and then will disappear when the user clicks elsewhere.
      * @param selObj
      */
    public ObjBasePopup(Component c, ObjBase o, Point p) {
         _myObject = o;
         _guiComponent = c;
         initiatePopup(o);
         popupMenu.show( c, p.x, p.y );
    }

    /**
     * Add a JMenuItem the end of the popup table with the display string
     * label and the actionlistener ahandler
     * @param label the label that will be displayed on the menu
     * @param ahandler the action performed when the menuitem is selected
     */
    public void addMenuItem(String label, ActionListener ahandler) {
        if(!this._hasPluginHeader) {
             //Add a "Special Actions" label:
             JLabel jumplabel = new JLabel();
             jumplabel.setText("Special actions:");
             jumplabel.setFont(new Font("Arial", Font.ITALIC, 10));
             popupMenu.add( jumplabel );

             //Add a separator
             JSeparator jsep2 = new JSeparator();
             popupMenu.add( jsep2 );

            _hasPluginHeader = true;
        }
         JMenuItem aMenu = new JMenuItem( label );
         menuOptions.add(aMenu);
         popupMenu.add( aMenu );
         aMenu.addActionListener( ahandler );
    }

    /**
     * Add a JMenu to the end of the popup table.  Use this if the
     * menu should branch out and do something fancy with its own
     * sub-JMenuItems
     * @param item
     */
    public void addMenuItem(JMenu item) {
         popupMenu.add( item );
    }

    /**
     * Add a JMenuItem to the end of the popup table
     * You need to have pre-added an ActionListener to the JMenuItem
     * for this to do anything
     * @param item
     */
    public void addMenuItem(JMenuItem item) {
         popupMenu.add( item );
    }

    private void initiatePopup(ObjBase o) {
         popupMenu = new JPopupMenu();
         constructPopup(o, popupMenu);
    }
    
    private void constructPopup(ObjBase o, JComponent pooper) {
         Object[] _viewers = Collator.getAvailableViewers(o.getType()).toArray();
         ItemHandler handler = new ItemHandler(o);

         JLabel headerlabel = new JLabel();
         headerlabel.setText(o.getType().toString() + " " + o.getName());
         headerlabel.setFont(new Font("Arial", Font.ITALIC, 10));
         pooper.add( headerlabel );

         //Add Save menu option
         JMenuItem saveMenu = new JMenuItem( "Save to database" );
         menuOptions.add(saveMenu);
         pooper.add( saveMenu );
         saveMenu.addActionListener( handler );

         //Add Delete menu option
         JMenuItem delMenu = new JMenuItem( "Delete" );
         menuOptions.add(delMenu);
      //   pooper.add( delMenu );   //since method isn't implemented, this is currently silenced
         delMenu.addActionListener( handler );

         //Add Update menu option
         JMenuItem updateMenu = new JMenuItem( "Update" );
         menuOptions.add(updateMenu);
         pooper.add( updateMenu );
         updateMenu.addActionListener( handler );

         //Add Delete menu option
         JMenuItem revMenu = new JMenuItem( "Revert" );
         menuOptions.add(revMenu);
         pooper.add( revMenu );
         revMenu.addActionListener( handler );

         //Add undo menu option
         JMenuItem undoMenu = new JMenuItem( "Undo" );
         menuOptions.add(undoMenu);
         pooper.add( undoMenu );
         undoMenu.addActionListener( handler );

         //Add redo menu option
         JMenuItem redoMenu = new JMenuItem( "Redo" );
         menuOptions.add(redoMenu);
         pooper.add( redoMenu );
         redoMenu.addActionListener( handler );

         //Add copy menu option
         JMenuItem copyMenu = new JMenuItem( "Copy to clipboard" );
         menuOptions.add(copyMenu);
         pooper.add( copyMenu );
         copyMenu.addActionListener( handler );

         //Add paste menu option
         JMenuItem pasteMenu = new JMenuItem( "Paste from clipboard" );
         menuOptions.add(pasteMenu);
         pooper.add( pasteMenu );
         pasteMenu.addActionListener( handler );

         //Add export to xml menu option
         JMenuItem xmlMenu = new JMenuItem( "Export to XML" );
         menuOptions.add(xmlMenu);
         pooper.add( xmlMenu );
         xmlMenu.addActionListener( handler );

         //Add export to xml menu option
         final subMenu searchTagMenu = new subMenu( "Search tags" );
         pooper.add( searchTagMenu );

             //Add add tag submenu
             JMenuItem addTagMenu = new JMenuItem( "Add search tag" );
             menuOptions.add(addTagMenu);
             searchTagMenu.add( addTagMenu );
             addTagMenu.addActionListener( handler );

             //Add list tags submenu
             final JMenu listTagMenu = new JMenu( "List tags" );
             searchTagMenu.add( listTagMenu );
             listTagMenu.addMenuListener(new MenuAdapter() {
                @Override
                public void menuSelected(MenuEvent e) {
                    if(searchTagMenu.isInitiated) {
                        return;
                    }
                    for(final String tag: _myObject.getSearchTags()) {
                        //List the tags
                        JMenu tagMenu = new JMenu( tag );
                        listTagMenu.add( tagMenu );

                        JMenuItem removeTagItem = new JMenuItem( "remove" );
                        tagMenu.add( removeTagItem );
                        removeTagItem.addActionListener( new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                _myObject.removeSearchTag(tag);
                            } });
                    }
                    searchTagMenu.isInitiated = true;
                }
             });

         //Add a "launch" label:
         JLabel launchlabel = new JLabel();
         launchlabel.setText("Launch Viewer:");
         launchlabel.setFont(new Font("Arial", Font.ITALIC, 10));
         pooper.add( launchlabel );

         //Add a separator
         JSeparator jsep1 = new JSeparator();
         pooper.add( jsep1 );

         //Add launch preferred viewer menu option
         JMenuItem launchMenu = new JMenuItem( "Preferred Viewer" );
         menuOptions.add(launchMenu);
         pooper.add( launchMenu );
         launchMenu.addActionListener( handler );

         //Add launch viewer JMenu to fill with options
         JMenu chooseViewerMenu = new JMenu( "Choose Viewer" );
         pooper.add( chooseViewerMenu );

         // construct each menu item and add to popup menu; also
         // enable event handling for each menu item
         for ( int i = 0; i < _viewers.length; i++ )
             {
             JMenuItem amenu = new JMenuItem( _viewers[i].toString() );
             menuOptions.add(amenu);
             chooseViewerMenu.add( amenu );
             amenu.addActionListener( handler );
             viewerHash.put(_viewers[i].toString(), (ViewerWrapper) _viewers[i]);
         }
         
         addItemSpecificFields(pooper, o);
    }

    private void addJumpToSeparator(JComponent pooper) {
         //Add a "Jump To" label:
         JLabel jumplabel = new JLabel();
         jumplabel.setText("Jump To:");
         jumplabel.setFont(new Font("Arial", Font.ITALIC, 10));
         pooper.add( jumplabel );

         //Add a separator
         JSeparator jsep2 = new JSeparator();
         pooper.add( jsep2 );
    }

    private void addSubField(final JComponent menu, String title, final ObjBase obj) {
                final subMenu menuitem1 = new subMenu( title );

                menuitem1.addMenuListener(new MenuAdapter() {
                    @Override
                    public void menuSelected(MenuEvent e) {
                        if(!menuitem1.isInitiated) {
                            constructPopup(obj, menuitem1);
                            menuitem1.isInitiated = true;
                        }
                    }
                });
                menu.add( menuitem1 );
    }

    /**
     * This is specific to Collection objBases to avoid retrieving everything
     * @param acoll
     * @param menu
     */
    private void addCollectionSubFields(final Collection acoll, JComponent menu) {
        final subMenu rootMenu = new subMenu("Contents");
        //For each ObjType create a header
        for(final ObjType type : ObjType.values()) {
                final subMenu menuitem1 = new subMenu(type.toString() );
                menuitem1.addMenuListener(new MenuAdapter() {
                    @Override
                    public void menuSelected(MenuEvent e) {
                        if(menuitem1.isInitiated) {
                            return;
                        }
                        final subMenu objtypeItems = new subMenu( "Retrieve all" );

                        objtypeItems.addMenuListener(new MenuAdapter() {
                            @Override
                            public void menuSelected(MenuEvent e) {
                                if(objtypeItems.isInitiated) {
                                    return;
                                }
                                @SuppressWarnings (value="unchecked")
                                ArrayList<ObjBase> objbases = (ArrayList<ObjBase>)acoll.getAll(type);
                                for( ObjBase obj : objbases) {
                                    if(obj!=null) {
                                        addSubField(objtypeItems, obj.getName(), obj);
                                    }
                                }
                                objtypeItems.isInitiated = true;
                            }
                        });
                        menuitem1.isInitiated=true;
                        menuitem1.add(objtypeItems);
                    }
                });
                rootMenu.add( menuitem1 );
                rootMenu.isInitiated = true;
        }
        menu.add(rootMenu);
    }

    public void disable() {
        _isActive = false;
    }

    public void enable() {
        _isActive = true;
    }

    private class MenuAdapter implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
        }

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        @Override
        public void menuCanceled(MenuEvent e) {
        }
    }

    private void addObjBaseList(final JComponent menu, String title, java.util.Collection objs) {
        final subMenu rootItem = new subMenu( title );

        for( Object objecto : objs) {
            ObjBase obj = (ObjBase) objecto;
            addSubField(rootItem, obj.getName(), obj);
        }

        menu.add(rootItem);
    }

    private class subMenu extends JMenu {
        public subMenu(String title) {
            super(title);
        }
        //VARIABLES//
        public boolean isInitiated=false;
    }


    private void addItemSpecificFields(JComponent pooper, ObjBase obj) {
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        final Point location = pooper.getLocation();
        switch(obj.getType()) {
            case COLLECTION:
                final Collection coll = (Collection) obj;
                addJumpToSeparator(pooper);
                if(coll.getAuthor()!=null) {
                    addSubField(pooper, "Author", coll.getAuthor());
                }
                addCollectionSubFields(coll, pooper);
                break;
            case PART:
                final Part p = (Part) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Sequence", p.getSeq());
                addSubField(pooper, "Author", p.getAuthor());
                addSubField(pooper, "Format", p.getFormat());
                break;
            case NUCSEQ:
                final NucSeq nuc = (NucSeq) obj;
                addJumpToSeparator(pooper);
                addObjBaseList(pooper, "Annotations", nuc.getAnnotations());
                break;
            case VECTOR:
                final Vector v = (Vector) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Sequence",  v.getSeq());
                addSubField(pooper, "Author",  v.getAuthor());
                addSubField(pooper, "Format",  v.getFormat());
                break;
            case PLASMID:
                final Plasmid pl = (Plasmid) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Sequence",  pl.getSeq());
                addSubField(pooper, "Author",  pl.getAuthor());
                addSubField(pooper, "Format",  pl.getFormat());
                addSubField(pooper, "Part",  pl.getPart());
                addSubField(pooper, "Vector",  pl.getVector());
                break;
            case PERSON:
                final Person pe = (Person) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Lab",  pe.getLab());
                addSubField(pooper, "Biography",  pe.getBiography());
                addSubField(pooper, "Collection",  pe.getHerCollection());
                break;
            case LAB:
                final Lab l = (Lab) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "PI",  l.getPI());
                addSubField(pooper, "Institution",  l.getInstitution());
                break;
            case CONTAINER:
                final Container acon = (Container) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Plate", acon.getPlate());
                Sample asam = acon.getSample();
                if(asam!=null) {
                    addSubField(pooper, "Sample", asam);
                }
                break;

            case PLATE:
                final Plate aplate = (Plate) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Plate Type", aplate.getPlateType());
                addSubField(pooper, "Author", aplate.getAuthor());
                break;

            case SAMPLE:
                final Sample sam = (Sample) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Container", sam.getContainer());
                addSubField(pooper, "Author", sam.getAuthor());
                switch(sam.getSampleType()) {
                    case PLASMID_SAMPLE:
                        //add Plasmid
                        final PlasmidSample ps = (PlasmidSample) sam;
                        final JMenuItem menuitem21 = new JMenuItem( "Plasmid" );
                        addSubField(pooper, "Plasmid", ps.getPlasmid());
                        break;
                    case CELL_SAMPLE:
                        //add Strain
                        break;
                    case OLIGO_SAMPLE:
                        //add Oligo
                        break;
                }
                addObjBaseList(pooper, "Data", sam.getData());
                break;

            case FEATURE:
                final Feature fe = (Feature) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Sequence", fe.getSeq());
                addSubField(pooper, "Author", fe.getAuthor());
                addObjBaseList(pooper, "Families", fe.getFamilies());
                addObjBaseList(pooper, "Notes", fe.getNotes());
                break;

            case FACTOID:
                final Factoid fact = (Factoid) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Author", fact.getAuthor());
                addObjBaseList(pooper, "Notes", fact.getNotes());
                break;

            case NOTE:
                final Note anote = (Note) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Author", anote.getAuthor());
                addObjBaseList(pooper, "Factoids", anote.getFactoids());
                break;

            case OLIGO:
                final Oligo ol = (Oligo) obj;
                addJumpToSeparator(pooper);
                addSubField(pooper, "Sequence", ol.getSeq());
                addSubField(pooper, "Author", ol.getAuthor());
                break;
        }
    }// </editor-fold>

    private void addSearchTag() {
        Object[] params = new Object[2];
        String message = "What keyword to you want to add?.";
        params[0] = message;

        ArrayList<String> listy = Collector.getAllSearchTags();
        listy.add("(Enter new Tag)");
        AutoComboBox acb = new AutoComboBox(listy);
        params[1] = acb;

        int n = JOptionPane.showConfirmDialog(null, params, "Save objects before quiting", JOptionPane.YES_NO_OPTION );
        if(n==0) {
            String tag = (String) acb.getSelectedItem();
            if(tag.equals("(Enter new Tag)")) {
                tag = JOptionPane.showInputDialog( "What tag do you wish to add?" );
                if(tag!=null) {
                    _myObject.addSearchTag(tag);
                }
            }
        }
    }

     private class ItemHandler implements ActionListener {
         private ObjBase handledObj;

         public ItemHandler(ObjBase obj) {
             super();
             handledObj = obj;
         }

         @Override
         public void actionPerformed( ActionEvent e )
             {
             // determine which menu item was selected
             for(JMenuItem jm : menuOptions) {
                 if ( e.getSource() == jm ) {
                     String selected = jm.getText();
                     
                     //Do the action appropriate to the string selected
                     if(selected.equals("Delete")) {
                         handledObj.deleteFromDatabase();
                     } else if(selected.equals("Update")) {
                         System.out.println("Objbasepopup calling update");
                         handledObj.update();
                     } else if(selected.equals("Undo")) {
                         handledObj.undo();
                     } else if(selected.equals("Redo")) {
                         handledObj.redo();
                     } else if(selected.equals("Revert")) {
                         handledObj.revert();
                     } else if(selected.equals("Export to XML")) {
                         handledObj.exportToXML();
                     } else if(selected.equals("Copy to clipboard")) {
                         Collector.copyToClipboard(handledObj);
                     } else if(selected.equals("Add search tag")) {
                         addSearchTag();
                     } else if(selected.equals("Paste from clipboard")) {
                         ObjBase obj = Collector.getFromClipboard();
                         if(obj!=null) {
                             handledObj.addObject(obj);
                         }
                     } else if(selected.equals("Preferred Viewer")) {
                         ViewerWrapper vw = Collator.getPreferredViewer(handledObj.getType());
                         if(vw!=null) {
                            vw.launch(handledObj);
                         }
                     } else if(selected.equals("Save to database")) {
                         handledObj.saveDefault();
                     } else {
                         if(viewerHash.containsKey(selected)) {
                             viewerHash.get(selected).launch(handledObj);
                         }
                     }
                     if(_guiComponent!=null) {
                        _guiComponent.repaint();
                     }
                     return;
                }
            }
        }
     }

/*-----------------
     variables
 -----------------*/
     private ArrayList<JMenuItem> menuOptions = new ArrayList<JMenuItem>();
     private Hashtable<String, ViewerWrapper> viewerHash = new Hashtable<String, ViewerWrapper>();
     private Component _guiComponent;
     private ObjBase _myObject;

     private boolean _hasJumpHeader = false;
     private boolean _hasPluginHeader = false;
     private boolean _isActive = true;
     JPopupMenu popupMenu;
}