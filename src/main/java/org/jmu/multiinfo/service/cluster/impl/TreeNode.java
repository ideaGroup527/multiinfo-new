package org.jmu.multiinfo.service.cluster.impl;

import org.jmu.multiinfo.dto.cluster.StepNodeDTO;

public class TreeNode {
	 // 左节点
	 private TreeNode lefTreeNode;
	 // 右节点
	 private TreeNode rightNode;
	 
	 private StepNodeDTO value;

	public TreeNode getLefTreeNode() {
		return lefTreeNode;
	}

	public void setLefTreeNode(TreeNode lefTreeNode) {
		this.lefTreeNode = lefTreeNode;
	}

	public TreeNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(TreeNode rightNode) {
		this.rightNode = rightNode;
	}

	public StepNodeDTO getValue() {
		return value;
	}

	public void setValue(StepNodeDTO value) {
		this.value = value;
	}
	 
	 
}
