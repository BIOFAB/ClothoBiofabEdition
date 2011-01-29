/*
 *
 * File: StudioViewport.ui.js
 *
 *
 */

StudioViewportUi = Ext.extend(Ext.Viewport, {
    layout: 'border',
    id: 'studioViewport',
    initComponent: function() {
        this.items = [
            {
                xtype: 'tabpanel',
                activeTab: 2,
                region: 'center',
                split: true,
                ref: 'infoTabPanel',
                id: 'infoTabPanel'
            },
            {
                xtype: 'panel',
                region: 'north',
                layout: 'vbox',
                height: 60,
                id: 'northPanel',
                items: [
                    {
                        xtype: 'toolbar',
                        height: 78,
                        ref: '../toolbar',
                        id: 'mainToolbar',
                        items: [
                            {
                                xtype: 'button',
                                text: 'Designer',
                                ref: '../../seqDesignerButton',
                                id: 'seqDesignerButton',
                                hidden: true
                            },
                            {
                                xtype: 'tbseparator',
                                hidden: true
                            },
                            {
                                xtype: 'button',
                                text: 'RBS Calculator',
                                ref: '../../rbsCalcButton',
                                id: 'rbsCalcButton',
                                hidden: true
                            },
                            {
                                xtype: 'tbseparator'
                                
                            },
                            {
                                xtype: 'button',
                                text: 'Assembler',
                                ref: '../../assemblerButton',
                                id: 'assemblerButton'
                            },
                            {
                                xtype: 'tbseparator'
                            },
                            {
                                xtype: 'button',
                                text: 'Checker',
                                ref: '../../checkerButton',
                                id: 'checkerButton'
                            }
                        ]
                    }
                ],
                tbar: {
                    xtype: 'toolbar',
                    height: 30,
                    ref: '../menuBar',
                    id: 'menuBar'
                }
            },
            {
                xtype: 'tabpanel',
                activeTab: 3,
                width: 400,
                style: '',
                collapsible: true,
                region: 'west',
                split: true,
                id: 'westTabPanel',
                items: [
                    {
                        xtype: 'grid',
                        title: 'Parts',
                        store: 'AbstractPartStore',
                        stripeRows: true,
                        width: 338,
                        ref: '../abstractPartGridPanel',
                        id: 'abstractPartGridPanel',
                        selModel: new Ext.grid.RowSelectionModel({
                            singleSelect: true
                        }),
                        columns: [
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'id',
                                header: 'identifier',
                                sortable: true,
                                width: 100,
                                editable: false,
                                id: 'idColumn'
                            },
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'biofunction',
                                header: 'Function',
                                sortable: true,
                                width: 100,
                                editable: false,
                                id: 'functionColumn'
                            },
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'description',
                                header: 'Description',
                                sortable: true,
                                width: 150,
                                editable: false,
                                id: 'descripColumn'
                            }
                        ]
                    },
                    {
                        xtype: 'grid',
                        title: 'Oligos',
                        store: 'OligoStore',
                        width: 400,
                        enableDragDrop: true,
                        ddGroup: 'oligoDD',
                        ddText: 'Oligo',
                        stripeRows: true,
                        ref: '../oligoGridPanel',
                        id: 'oligoGridPanel',
                        selModel: new Ext.grid.RowSelectionModel({
                            singleSelect: true
                        }),
                        columns: [
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'id',
                                header: 'Identifier',
                                sortable: true,
                                width: 75,
                                editable: false
                            },
                            {
                                xtype: 'gridcolumn',
                                header: 'Project',
                                sortable: true,
                                width: 100,
                                editable: false,
                                dataIndex: 'project',
                                id: 'projectColumn'
                            },
                            {
                                xtype: 'gridcolumn',
                                header: 'Description',
                                sortable: true,
                                width: 200,
                                dataIndex: 'description'
                            }
                        ]
                    },
                    {
                        xtype: 'grid',
                        title: 'Backbones',
                        store: 'backboneStore',
                        columnLines: true,
                        stripeRows: true,
                        enableDragDrop: true,
                        ddText: 'Backbone',
                        ref: '../backboneGridPanel',
                        id: 'backboneGridPanel',
                        columns: [
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'id',
                                header: 'Identifier',
                                sortable: true,
                                width: 100,
                                editable: false
                            },
                            {
                                xtype: 'gridcolumn',
                                header: 'Description',
                                sortable: true,
                                width: 300,
                                dataIndex: 'description',
                                editable: false
                            }
                        ]
                    },
                    {
                        xtype: 'grid',
                        title: 'Constructs',
                        store: 'constructStore',
                        stripeRows: true,
                        columnLines: true,
                        ref: '../constructGridPanel',
                        id: 'constructGridPanel',
                        selModel: new Ext.grid.RowSelectionModel({
                            singleSelect: true
                        }),
                        columns: [
                            {
                                xtype: 'gridcolumn',
                                dataIndex: 'biofabID',
                                header: 'Identifier',
                                sortable: true,
                                width: 100,
                                editable: false
                            },
                            {
                                xtype: 'gridcolumn',
                                header: 'Description',
                                sortable: true,
                                width: 300,
                                editable: false,
                                dataIndex: 'description'
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        title: 'Files',
                        layout: 'absolute',
                        ref: '../filesPanel',
                        id: 'filesPanel',
                        items: [
                            {
                                xtype: 'displayfield',
                                value: 'Under development...',
                                x: 140,
                                y: 270,
                                id: 'underDevDisplayField3'
                            }
                        ]
                    }
                ]
            }
        ];
        StudioViewportUi.superclass.initComponent.call(this);
    }
});
