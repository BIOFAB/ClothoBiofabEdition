/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.eugenescripter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.util.logging.Logger;
import java.util.logging.Level;

import jsyntaxpane.DefaultSyntaxKit;
import jsyntaxpane.syntaxkits.*;

public class eugeneFrame extends JFrame {

	JButton newFileButton, saveButton, openButton;
	JButton runScriptButton, clearOutputButton, addTabButton, closeTabButton;
	JTreeDirectory rootDirectory;
	JTree tree;
	JTabbedPane tabbedPane;

	final JFileChooser fc;

	public eugeneFrame()
	{
		super();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Eugene Frame");
		this.setSize(600, 600);
		this.setResizable(true);

		fc = new JFileChooser();

                DefaultSyntaxKit.initKit();
                
		initComponents();

                this.setVisible(true);
	}

	public void initComponents()
	{
		JPanel pane = new JPanel();
		this.setContentPane(pane);
                pane.setLayout(new BorderLayout());

		newFileButton = new JButton("New File");
		newFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// Not sure what to do here...
			}
		});

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JEditorPane topPane = topPane();
				if (topPane == null)
				{
					JOptionPane.showMessageDialog(eugeneFrame.this, "Please create a new tab before saving");
					return;
				}

				int returnVal = fc.showSaveDialog(eugeneFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();
					try {
						FileWriter fstream = new FileWriter(file);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(topPane.getText());
						out.close();
					} catch (Exception exception) { }
				}
			}
		});

		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = fc.showOpenDialog(eugeneFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					tabbedPane.addTab(file.getName(), createSplitPane());
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
					JEditorPane topPane = topPane();

					try {
						BufferedReader in = new BufferedReader(new FileReader(file));
						topPane.setText("");
						String str;
                                                String newString = "";
						while ((str = in.readLine()) != null)
						{
							//topPane.append(str + "\n");
                                                        newString += str;
                                                }
						in.close();
                                                topPane.setText(newString);
					} catch (IOException exception) { }
				}
			}
		});

		runScriptButton = new JButton("Run Script");
		runScriptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                            if (tabbedPane.getTabCount() != 0) {
                                    try {
                                        eugeneProcess.runEugene(topPane().getText(), bottomPane());
                                    } catch (IOException ex)
                                    {
                                        Logger.getLogger(eugeneFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }
			}
		});

		clearOutputButton = new JButton("Clear Output");
		clearOutputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JEditorPane bottomPane = bottomPane();
				if (bottomPane != null)
					bottomPane.setText("");
			}
		});

		addTabButton = new JButton("Add Tab");
		addTabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String tabName = JOptionPane.showInputDialog("Please enter a tab name");
		        tabbedPane.addTab(tabName, createSplitPane());
			}
		});

		closeTabButton = new JButton("Close Tab");
		closeTabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (tabbedPane.getTabCount() != 0)
		    		tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});

		JToolBar buttonBar = new JToolBar();
		//buttonBar.setPreferredSize(new Dimension(600, 25));
		buttonBar.setFloatable(false);
		buttonBar.add(newFileButton);
		buttonBar.add(saveButton);
		buttonBar.add(openButton);
		buttonBar.add(runScriptButton);
		buttonBar.add(clearOutputButton);
		buttonBar.add(addTabButton);
		buttonBar.add(closeTabButton);
		pane.add(buttonBar, BorderLayout.PAGE_START);

		rootDirectory = new JTreeDirectory(System.getProperty("user.home"), System.getProperty("user.home"));
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDirectory);
		tree = new JTree(rootNode);
		listAllFiles(new File(rootDirectory.getPath()), rootNode, true);

		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String path = ((JTreeDirectory)node.getUserObject()).getPath();
				File directory = new File(path);

				if (directory.isDirectory())
					listAllFiles(directory, node, true);
			}
		});
		tree.addTreeExpansionListener(new TreeExpansionListener()
		{
			public void treeCollapsed(TreeExpansionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			}

			public void treeExpanded(TreeExpansionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String path = ((JTreeDirectory)node.getUserObject()).getPath();
				File directory = new File(path);

				if (directory.isDirectory())
					listAllFiles(directory, node, true);
			}
		});
		tree.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2)
				{
					// open that file

					TreePath path = tree.getPathForLocation(e.getX(), e.getY());

					if (path == null)
						return;

					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

					try {
						File file = new File(((JTreeDirectory)node.getUserObject()).getPath());

						if (!file.isFile())
						{
							return;
						}

						tabbedPane.addTab(file.getName(), createSplitPane());
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						JEditorPane topPane = topPane();

						BufferedReader in = new BufferedReader(new FileReader(file));
						topPane.setText("");
						String str;
                                                String newString = "";
						while ((str = in.readLine()) != null)
						{
							//topPane.append(str + "\n");
                                                        newString += str;
                                                }
						in.close();
                                                topPane.setText(newString);
					} catch (IOException exception) { }
				}
			}
		});

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Tab1", createSplitPane());
		//tabbedPane.setPreferredSize(new Dimension(350, 525));

		JSplitPane horizontalSplitPane = new JSplitPane();
		JScrollPane treeScrollPane = new JScrollPane(tree);
		//treeScrollPane.setPreferredSize(new Dimension(225, 525));
		
                horizontalSplitPane.setLeftComponent(treeScrollPane);
                horizontalSplitPane.setRightComponent(tabbedPane);

		pane.add(horizontalSplitPane, BorderLayout.CENTER);
	}

	private JSplitPane splitPane()
	{
		return (JSplitPane) tabbedPane.getSelectedComponent();
	}

	private JEditorPane topPane()
	{
		JSplitPane splitPane = splitPane();
		JEditorPane topPane = null;
		if (splitPane != null)
		{
			JScrollPane scroll = (JScrollPane) splitPane.getTopComponent();
			topPane = (JEditorPane) scroll.getViewport().getComponent(0);
		}
		return topPane;
	}

	private JEditorPane bottomPane()
	{
		JSplitPane splitPane = splitPane();
		JEditorPane bottomPane = null;
		if (splitPane != null)
		{
			JScrollPane scroll = (JScrollPane) splitPane.getBottomComponent();
			bottomPane = (JEditorPane) scroll.getViewport().getComponent(0);
		}
		return bottomPane;
	}

	private JSplitPane createSplitPane()
	{
            JSplitPane splitPane = new JSplitPane();

            JEditorPane topPane = new JEditorPane();
            JEditorPane bottomPane = new JEditorPane();
            bottomPane.setEditable(false);

            JScrollPane topScroll = new JScrollPane();
            JScrollPane bottomScroll = new JScrollPane();

            topScroll.setViewportView(topPane);
            bottomScroll.setViewportView(bottomPane);

            DefaultSyntaxKit.registerContentType("text/eugene", EugeneSyntaxKit.class.getName());
            topPane.setContentType("text/eugene");

            splitPane.setDividerLocation(250);
            splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

            splitPane.setTopComponent(topScroll);
            splitPane.setBottomComponent(bottomScroll);

            return splitPane;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new eugeneFrame().setVisible(true);
			}
		});
	}

	public void listAllFiles(File directory, DefaultMutableTreeNode parent, Boolean recursive) {

		parent.removeAllChildren();

		File[] children = directory.listFiles();

        if (children == null)
        {
            return;
        }

        for (int i=0; i<children.length; i++)
        {
            JTreeDirectory nodeDirectory = new JTreeDirectory(directory.getPath() + File.separator + children[i].getName(),
                    children[i].getName());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeDirectory);

            parent.add(node);

            if (recursive)
            {
            	listAllFiles(children[i], node, false);
            }
        }
    }
}

class JTreeDirectory
{
    private String name;
    private String path;

    public JTreeDirectory(String path, String name)
    {
        this.name = name;
        this.path = path;
    }

    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return path;
    }

    public String toString()
    {
        return name;
    }
}
