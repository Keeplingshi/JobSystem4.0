package com.temp;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class TestTable
extends JFrame
implements ActionListener
{
private JToolBar toolbar = new JToolBar();

private JButton findInTableBtn = new JButton("Find in Table");

private JButton findInTreeBtn = new JButton("Find in Tree");

private JTable table = null;

private JTree tree = null;

private JScrollPane tableSp = new JScrollPane();

private JScrollPane treeSp = new JScrollPane();

private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableSp, treeSp);

public TestTable()
{
super("TestTable");

table = new JTable(createTableModel());
tree = new JTree();

tableSp.setViewportView(table);
treeSp.setViewportView(tree);

toolbar.add(findInTableBtn);
toolbar.add(findInTreeBtn);

findInTableBtn.addActionListener(this);
findInTreeBtn.addActionListener(this);

getContentPane().add(splitPane, BorderLayout.CENTER);
getContentPane().add(toolbar, BorderLayout.NORTH);
}

private TableModel createTableModel()
{
String[] columnNames = { "colors", "sports", "food" };
String[][] data = { { "blue", "violet", "red", "yellow" }, { "basketball", "soccer", "football", "hockey" },
{ "hot dogs", "pizza", "ravioli", "bananas" } };

DefaultTableModel model = new DefaultTableModel(data, columnNames);
return model;
}

public void actionPerformed(ActionEvent e)
{
String str = JOptionPane.showInputDialog(this, "Find:", "Find", JOptionPane.QUESTION_MESSAGE);
if (str != null) {
if (e.getSource() == findInTableBtn) {
findInTable(str);
}
else {
findInTree(str);
}
}
}

private void findInTree(String str)
{
Object root = tree.getModel().getRoot();
TreePath treePath = new TreePath(root);
treePath = findInPath(treePath, str);
if (treePath != null) {
tree.setSelectionPath(treePath);
tree.scrollPathToVisible(treePath);
}
}

private TreePath findInPath(TreePath treePath, String str)
{
Object object = treePath.getLastPathComponent();
if (object == null) {
return null;
}

String value = object.toString();
if (str.equals(value)) {
return treePath;
}
else {
TreeModel model = tree.getModel();
int n = model.getChildCount(object);
for (int i = 0; i < n; i++) {
Object child = model.getChild(object, i);
TreePath path = treePath.pathByAddingChild(child);

path = findInPath(path, str);
if (path != null) {
return path;
}
}
return null;
}
}

private void findInTable(String str)
{
int rowCount = table.getRowCount();
int columnCount = table.getColumnCount();
for (int i = 0; i < rowCount; i++) {
for (int k = 0; k < columnCount; k++) {
Object value = table.getValueAt(i, k);
if (str.equals(value)) {
table.getSelectionModel().setSelectionInterval(i, i);
Rectangle cellRect = table.getCellRect(i, k, true);
table.scrollRectToVisible(cellRect);
return;
}
}
}
}

public static void main(String[] args)
{
	TestTable f = new TestTable();
f.pack();
f.setLocationRelativeTo(null);
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.setVisible(true);
f.splitPane.setDividerLocation(0.5);
}
}