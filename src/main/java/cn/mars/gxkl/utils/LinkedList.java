package cn.mars.gxkl.utils;

public class LinkedList<T> {
	
	private int n,now;
	private Node<T> head,rear;
	
	public LinkedList(int n) {
		this.n = n;
		now = 0;
		head = new Node<T>(null);
		rear = head;
		for(int i=1;i<n;i++) {
			Node<T> tmp = new Node<T>(null);
			rear.setNext(tmp);
			rear = tmp;
		}
		rear.setNext(head);
		rear = head;
	}
	
	public void append(T content) {
		rear.setContent(content);
		rear = rear.getNext();
		if(now == n) {
			head = head.getNext();
		}
		else {
			now++;
		}
	}
	
	public void clear() {
		now = 0;
		rear = head;
	}
	
	public String getContent() {
		StringBuilder content = new StringBuilder();
		Node<T> tmp = head;
		int nowAt = 0;
		while(nowAt<now) {
			content.append(tmp.getContent()+"\r\n");
			tmp = tmp.getNext();
			nowAt++;
		}
		return content.toString();
	}
	
	class Node<V> {
		
		private V content;
		private Node<V> next;

		public Node(V content) {
			this.content = content;
			next = null;
		}
		
		public void setContent(V content) {
			this.content = content;
		}
		
		public void setNext(Node<V> next) {
			this.next = next;
		}
		
		public V getContent() {
			return content;
		}
		
		public Node<V> getNext() {
			return next;
		}
		
	}
	
}
