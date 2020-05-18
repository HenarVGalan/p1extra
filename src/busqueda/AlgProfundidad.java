/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import problema.Accion;

public class AlgProfundidad extends AlgBusqueda {


    @Override
    public void busqueda() {
        // Inicializamos las variables
        costeTotal = 0;
        nodosExpandidos = 0;
        nodosGenerados = 0;
        longSol = 0;
        tiempoBusqueda = System.currentTimeMillis();

        // Crea el array para la secuencia de acciones
        secuenciaAcciones = new ArrayList<Accion>();

        // Crea las estructuras necesarias para implementar el algoritmo
       //Nodos abiertos:
        Stack<Nodo> abiertos = new Stack<Nodo>();
        //Nodos cerrados:
        ArrayList<Nodo> cerrados = new ArrayList<Nodo>();
        // Variables auxiliares
        Nodo elegido;
        ArrayList<Nodo> sucesores;

        // Creamos la raíz del árbol
        elegido = new Nodo(problema.estadoInicial());

        abiertos.push(elegido);
        // Bucle principal
        do {
            // Si no quedan nodos abiertos, retorna
            if (abiertos.size() == 0) {
                return;
            }

            elegido = abiertos.pop();

            sucesores = getSucesores(elegido);
            while (!sucesores.isEmpty()) {
                if (!cerrados.contains(sucesores.get(0))) {
                    abiertos.push(sucesores.get(0));
                    sucesores.remove(0);

                } else {
                    sucesores.remove(0);
                }
            }
            cerrados.add(elegido);

        } while (!problema.comprobarFinal(elegido.getEstado()));
        costeTotal = elegido.getCoste();
        while (!nodoInicial(elegido)) {
            secuenciaAcciones.add(elegido.getAccion());

            elegido = elegido.getPadre();
        }

        tiempoBusqueda = System.currentTimeMillis() - tiempoBusqueda;

         Collections.reverse(secuenciaAcciones);
     	System.out.println("****   SOLUCI�N ALGORITMO PROFUNDIDAD - DepthFirst *****");
        for(int i=0;i<secuenciaAcciones.size();i++){
            longSol++;
            System.out.println(secuenciaAcciones.get(i).toString());
        }
    }
}
