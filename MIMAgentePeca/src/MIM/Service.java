/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MIM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class Service {
    
public class TreeNode<String> implements Iterable<TreeNode<String>> {

    String data;
    TreeNode<String> parent;
    List<TreeNode<String>> children;

    public TreeNode(String data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public TreeNode<String> addChild(String child) {
        TreeNode<String> childNode = new TreeNode<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

        @Override
        public Iterator<TreeNode<String>> iterator() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

TreeNode<String> root = new TreeNode<>("root");
{
    TreeNode<String> node0 = root.addChild("node0");
    TreeNode<String> node1 = root.addChild("node1");
    TreeNode<String> node2 = root.addChild("node2");
    {
        TreeNode<String> node20 = node2.addChild(null);
        TreeNode<String> node21 = node2.addChild("node21");
        {
            TreeNode<String> node210 = node20.addChild("node210");
        }
    }
}
    ArrayList<String> _startServicesArray = new ArrayList<>();

    ArrayList<String> _doneServicesArray = new ArrayList<>();

    ArrayList<String> _neededServicesArray = new ArrayList<>();
    
    public void SetServices(ArrayList<String> Services){}
}
