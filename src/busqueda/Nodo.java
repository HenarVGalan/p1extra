package busqueda;

import java.util.Comparator;
import java.util.PriorityQueue;
import problema.Accion;
import problema.Estado;

/**
 *  Implements a node of the search tree.     
 */

public class Nodo{
	
	/* 
	 * Different comparators. They are necessary to indicate the criteria by which
	 * nodes must be ordered. Example:
	 * 
	 * 		PriorityQueue<Node> open = new PriorityQueue<Node>(100, Node.BY_HEURISTIC);
	 */
	public static final Comparator<Nodo> BY_HEURISTIC = new HeuristicComparator();
	public static final Comparator<Nodo> BY_COST = new CostComparator();
	public static final Comparator<Nodo> BY_EVALUATION = new EvaluationComparator();
	
	/* Relevant information for the search algorithms. */
    private final Estado estado;	 		// State that the node corresponds to. 
    private int profundidad=0; 		    // Depth of the tree.
    private double coste=0; 	 		// Cost of the path from the root to the node. Corresponds to g(n).
    private double heuristica=0;  	// Value of the heuristic function for the node. Corresponds to h(n).
    private double evaluacion = 0;  // Evaluation of the node. Corresponds to f(n) = g(n) + h(n)
    private Nodo padre;		 	// Reference to the parent node in the search tree. 
    private Accion accion;   		// Action applied to the parent to obtain the node. 
    
    /**  Constructor. Takes as parameter an state and builds the corresponding node.*/
    public Nodo(Estado estado){
		this.estado =  estado;
    }

    
    // Access to the fields of the node.
    
    /** Returns the state represented by the nod. */
    public Estado getEstado(){ return estado; }
    /** Sets the depth of the node. */
    public void setProfundidad(int profundidad){ this.profundidad=profundidad; }
    /** Returns the depth of the node. */
    public int getProfundidad() { return profundidad; }
    
    /** Sets the cost of the node. */
    public void setCoste(double coste){
    	this.coste = coste;
    	evaluacion = this.coste + this.heuristica;
    }
    /** Returns the cost of the node. */
    public double getCoste() { return coste; }

    /** Fixes the value of the heuristic. */
    public void setHeuristica(double heuristica){ 
    	this.heuristica = heuristica;
    	evaluacion = this.coste + this.heuristica;
    }
    /** Returns the heuristic function for the node. */
    public double getHeuristica() { return heuristica; }
    
    /** Returns the value of the evaluation for the node */
    public double getEvaluacion() { return evaluacion; }
    
    /** Returns a reference to the parent node in the tree. */
    public Nodo getPadre() { return padre; }
    /** Fixes the reference to the parent in the tree. */
    void setPadre(Nodo nodo) {	padre=nodo; }
	
    /** Returns the action applied to the parent to create the node. */
    public Accion getAccion() { return accion;}
    /** Sets the action applied to the parent to create the node. */ 
    public void setAccion(Accion accion) { this.accion = accion; }
    
    // Auxiliar functions

	/** 
	 * Checks if two nodes are similar (represent the same state). The method overrides the one in
	 * the class Object so that it is used by the structures provided by Java, as for example HashSet.
	 */
	@Override
	public boolean equals(Object otroNodo){
		// If the object is not a Node, returns false, but reports the error.
		if (!(otroNodo instanceof Nodo)){
			System.out.println("Trying to compare two objects of different classes.");
			return false;
		}
		// If the object is a node, compares their states.
		return this.estado.equals(((Nodo) otroNodo).getEstado());
	}
	
    /** 
     *  Prints the node in a String. Determines what prints  System.out.println(node);
     *  It is simple because it is the format used by the debugger.
     */
    public String toString(){
    	return("Nodo("+estado.toString()+")");
    }
    
    /** 
     * Prints all the information of the node. 
     */
    public void print(){
    	System.out.println("Nodo ("+estado.toString()+"):" +"\n\tprofundidad:"+profundidad+" \n\tcoste:"+coste+"\n\th:"+heuristica+".");
    }
    
	// Implementation of the comparators
    
	/** 
     * Allows comparing nodes by cost
     */
	public static class CostComparator implements Comparator<Nodo> {
		public int compare(Nodo nodeA, Nodo nodeB) {
			double nodeACost = nodeA.getCoste();
			double nodeBCost = nodeB.getCoste();
			if (nodeACost<nodeBCost){
				return -1;
			}
			else if (nodeBCost<nodeACost){
				return 1;
			}
			else {
				return 0;
			}
		}
	}	
	
    /** 
     * Allows comparing nodes by heuristic.
     */
	public static class HeuristicComparator implements Comparator<Nodo> {
		public int compare(Nodo nodeA, Nodo nodeB) {
			double nodeAHeur = nodeA.getHeuristica();
			double nodeBHeur = nodeB.getHeuristica();
			if (nodeAHeur<nodeBHeur)
				return -1;
			else if (nodeBHeur<nodeAHeur)
				return 1;
			else 
				return 0;
		}
	}	
	
    /** 
     * Allows comparing nodes by evaluation
     */
	public static class EvaluationComparator implements Comparator<Nodo> {
		public int compare(Nodo nodeA, Nodo nodeB) {
			double nodeAEval = nodeA.getEvaluacion();
			double nodeBEval = nodeB.getEvaluacion();
			if (nodeAEval<nodeBEval)
				return -1;
			else if (nodeBEval<nodeAEval)
				return 1;
			else 
				return 0;
		}
	}
}
