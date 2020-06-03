package busqueda;

import java.util.Comparator;
import problema.Accion;
import problema.Estado;

public class Nodo{
	

	public static final Comparator<Nodo> BY_HEURISTIC = new HeuristicComparator();
    public static final Comparator<Nodo> BY_EVALUATION = new EvaluationComparator();
	
	private final Estado estado;	 		
    private int profundidad=0; 		   
    private double coste=0; 	 		// Cost of the path from the root to the node. Corresponds to g(n).
    private double heuristica=0;  	// Value of the heuristic function for the node. Corresponds to h(n).
    private double evaluacion = 0;  // Evaluation of the node. Corresponds to f(n) = g(n) + h(n)
    private Nodo padre;		 	
    private Accion accion;   		// Action applied to the parent to obtain the node. 
    
   
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
    
   
    public void setCoste(double coste){
    	this.coste = coste;
    	evaluacion = this.coste + this.heuristica;
    }
  
    public double getCoste() { return coste; }
   
    public void setHeuristica(double heuristica){ 
    	this.heuristica = heuristica;
    	evaluacion = this.coste + this.heuristica;
    }
  
    public double getHeuristica() { return heuristica; }    
   
    public double getEvaluacion() { return evaluacion; }    
   
    public Nodo getPadre() { return padre; }    
    
    void setPadre(Nodo nodo) {	padre=nodo; }
	
    public Accion getAccion() { return accion;}    
  
    public void setAccion(Accion accion) { this.accion = accion; }
    
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
