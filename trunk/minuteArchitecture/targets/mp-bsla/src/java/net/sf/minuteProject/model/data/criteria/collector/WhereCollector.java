package net.sf.minuteProject.model.data.criteria.collector;

import java.util.Stack;

import net.sf.minuteProject.model.data.criteria.holder.WhereHolder;

public class WhereCollector <E> extends CriteriaCollector<E> {

	private Stack<WhereHolder<E>> s;
	private String entityPath;
	
	public WhereCollector() {
		super();
		s = new Stack<WhereHolder<E>>();
	}
	
	public WhereCollector(String entityPath) {
		super();
		s = new Stack<WhereHolder<E>>();
		this.entityPath = entityPath;
	}

	public void addElement (String field, String operator, E... param){
		addElement (entityPath, field, operator, param);
	}
	
	private void addElement (String beanPath, String field, String operator, E... param){
		WhereHolder<E> wh = new WhereHolder<E>(beanPath, field, operator, param);
		s.add(wh);
	}
	
	public WhereHolder<E> getElements () {
		return s.pop();
	}

	public String getEntityPath() {
		return entityPath;
	}

	public void setEntityPath(String entityPath) {
		this.entityPath = entityPath;
	}
	
	
//	public void push(WhereHolder<E> w) {
//		stack.push(w);
//	}
}
