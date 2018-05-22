package checkers;

import java.util.ArrayList;
import java.util.List;

public class TreeStructure<T> {
	private T data = null;
	private List<TreeStructure<T>> children = new ArrayList<>();
	private TreeStructure<T> parent = null;
	public TreeStructure(T data) {
	this.data = data;
	}
	public TreeStructure<T> addChild(TreeStructure<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}
	public void addChildren(List<TreeStructure<T>> children) {
		children.forEach(each -> each.setParent(this));
		this.children.addAll(children);
	}
	public List<TreeStructure<T>> getChildren() {
		return children;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	private void setParent(TreeStructure<T> parent) {
		this.parent = parent;
	}
	public TreeStructure<T> getParent() {
		return parent;
	}
}