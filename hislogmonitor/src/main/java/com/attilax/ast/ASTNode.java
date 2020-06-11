package com.attilax.ast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * java.lang.Object
org.eclipse.jdt.core.dom.ASTNode
 * @author Administrator
 *Abstract superclass of all Abstract Syntax Tree (AST) node types.
 */
public class ASTNode {
	
	class NodeList extends AbstractList {

		/**
		 * The underlying list in which the nodes of this list are
		 * stored (element type: <code>ASTNode</code>).
		 * <p>
		 * Be stingy on storage - assume that list will be empty.
		 * </p>
		 * <p>
		 * This field declared default visibility (rather than private)
		 * so that accesses from <code>NodeList.Cursor</code> do not require
		 * a synthetic accessor method.
		 * </p>
		 */
		ArrayList store = new ArrayList(0);

		/**
		 * The property descriptor for this list.
		 */
//		ChildListPropertyDescriptor propertyDescriptor;

		/**
		 * A cursor for iterating over the elements of the list.
		 * Does not lose its position if the list is changed during
		 * the iteration.
		 */
		class Cursor implements Iterator {
			/**
			 * The position of the cursor between elements. If the value
			 * is N, then the cursor sits between the element at positions
			 * N-1 and N. Initially just before the first element of the
			 * list.
			 */
			private int position = 0;

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public boolean hasNext() {
				return this.position < NodeList.this.store.size();
			}

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public Object next() {
				Object result = NodeList.this.store.get(this.position);
				this.position++;
				return result;
		    }

			/* (non-Javadoc)
			 * Method declared on <code>Iterator</code>.
			 */
			public void remove() {
				throw new UnsupportedOperationException();
			}

			/**
			 * Adjusts this cursor to accomodate an add/remove at the given
			 * index.
			 *
			 * @param index the position at which the element was added
			 *    or removed
			 * @param delta +1 for add, and -1 for remove
			 */
			void update(int index, int delta) {
				if (this.position > index) {
					// the cursor has passed the added or removed element
					this.position += delta;
				}
			}
		}

		/**
		 * A list of currently active cursors (element type:
		 * <code>Cursor</code>), or <code>null</code> if there are no
		 * active cursors.
		 * <p>
		 * It is important for storage considerations to maintain the
		 * null-means-empty invariant; otherwise, every NodeList instance
		 * will waste a lot of space. A cursor is needed only for the duration
		 * of a visit to the child nodes. Under normal circumstances, only a
		 * single cursor is needed; multiple cursors are only required if there
		 * are multiple visits going on at the same time.
		 * </p>
		 */
		private List cursors = null;

		public NodeList(String argumentsProperty) {
			// TODO Auto-generated constructor stub
		}

		/**
		 * Creates a new empty list of nodes owned by this node.
		 * This node will be the common parent of all nodes added to
		 * this list.
		 *
		 * @param property the property descriptor
		 * @since 3.0
		 */
//		NodeList(ChildListPropertyDescriptor property) {
//			super();
//			this.propertyDescriptor = property;
//		}

		/* (non-javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		public int size() {
			return this.store.size();
		}

		/* (non-javadoc)
		 * @see AbstractList#get(int)
		 */
		public Object get(int index) {
			return this.store.get(index);
		}

		/* (non-javadoc)
		 * @see List#set(int, java.lang.Object)
		 */
		public Object set(int index, Object element) {
			return element;
//		    if (element == null) {
//		        throw new IllegalArgumentException();
//		    }
//			if ((ASTNode.this.typeAndFlags & PROTECT) != 0) {
//				// this node is protected => cannot gain or lose children
//				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//			}
//			// delink old child from parent, and link new child to parent
//			ASTNode newChild = (ASTNode) element;
//			ASTNode oldChild = (ASTNode) this.store.get(index);
//			if (oldChild == newChild) {
//				return oldChild;
//			}
//			if ((oldChild.typeAndFlags & PROTECT) != 0) {
//				// old child is protected => cannot be unparented
//				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//			}
//			ASTNode.checkNewChild(ASTNode.this, newChild, this.propertyDescriptor.cycleRisk, this.propertyDescriptor.elementType);
//			ASTNode.this.ast.preReplaceChildEvent(ASTNode.this, oldChild, newChild, this.propertyDescriptor);
//
//			Object result = this.store.set(index, newChild);
//			// n.b. setParent will call ast.modifying()
//			oldChild.setParent(null, null);
//			newChild.setParent(ASTNode.this, this.propertyDescriptor);
//			ASTNode.this.ast.postReplaceChildEvent(ASTNode.this, oldChild, newChild, this.propertyDescriptor);
//			return result;
		}

		/* (non-javadoc)
		 * @see List#add(int, java.lang.Object)
		 */
		public void add(int index, Object element) {
//		    if (element == null) {
//		        throw new IllegalArgumentException();
//		    }
//			if ((ASTNode.this.typeAndFlags & PROTECT) != 0) {
//				// this node is protected => cannot gain or lose children
//				throw new IllegalArgumentException("AST node cannot be modified"); //$NON-NLS-1$
//			}
//			// link new child to parent
//			ASTNode newChild = (ASTNode) element;
//			ASTNode.checkNewChild(ASTNode.this, newChild, this.propertyDescriptor.cycleRisk, this.propertyDescriptor.elementType);
//			ASTNode.this.ast.preAddChildEvent(ASTNode.this, newChild, this.propertyDescriptor);
//
//
//			this.store.add(index, element);
//			updateCursors(index, +1);
//			// n.b. setParent will call ast.modifying()
//			newChild.setParent(ASTNode.this, this.propertyDescriptor);
//			ASTNode.this.ast.postAddChildEvent(ASTNode.this, newChild, this.propertyDescriptor);
		}

		/* (non-javadoc)
		 * @see List#remove(int)
		 */
		public Object remove(int index) {
			return index;
			 

		}

		/**
		 * Allocate a cursor to use for a visit. The client must call
		 * <code>releaseCursor</code> when done.
		 * <p>
		 * This method is internally synchronized on this NodeList.
		 * It is thread-safe to create a cursor.
		 * </p>
		 *
		 * @return a new cursor positioned before the first element
		 *    of the list
		 */
	 

		/**
		 * Releases the given cursor at the end of a visit.
		 * <p>
		 * This method is internally synchronized on this NodeList.
		 * It is thread-safe to release a cursor.
		 * </p>
		 *
		 * @param cursor the cursor
		 */
		void releaseCursor(Cursor cursor) {
			synchronized (this) {
				// serialize cursor management on this NodeList
				this.cursors.remove(cursor);
				if (this.cursors.isEmpty()) {
					// important: convert empty list back to null
					// otherwise the node will hang on to needless junk
					this.cursors = null;
				}
			}
		}

	 

		/**
		 * Returns an estimate of the memory footprint of this node list
		 * instance in bytes.
	     * <ul>
	     * <li>1 object header for the NodeList instance</li>
	     * <li>5 4-byte fields of the NodeList instance</li>
	     * <li>0 for cursors since null unless walk in progress</li>
	     * <li>1 object header for the ArrayList instance</li>
	     * <li>2 4-byte fields of the ArrayList instance</li>
	     * <li>1 object header for an Object[] instance</li>
	     * <li>4 bytes in array for each element</li>
	     * </ul>
	 	 *
		 * @return the size of this node list in bytes
		 */
//		int memSize() {
////			int result = HEADERS + 5 * 4;
////			result += HEADERS + 2 * 4;
////			result += HEADERS + 4 * size();
////			return result;
//		}

		/**
		 * Returns an estimate of the memory footprint in bytes of this node
		 * list and all its subtrees.
		 *
		 * @return the size of this list of subtrees in bytes
		 */
//		int listSize() {
//			int result = memSize();
//			for (Iterator it = iterator(); it.hasNext(); ) {
//				ASTNode child = (ASTNode) it.next();
//				result += child.treeSize();
//			}
//			return result;
//		}
	}


}
