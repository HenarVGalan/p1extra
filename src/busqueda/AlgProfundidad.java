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
        
        costeTotal = 0;
        nodosExpandidos = 0;
        nodosGenerados = 0;
        longSol = 0;
        tiempoBusqueda = System.currentTimeMillis();

       
        secuenciaAcciones = new ArrayList<Accion>();       
        Stack<Nodo> abiertos = new Stack<Nodo>();        
        ArrayList<Nodo> cerrados = new ArrayList<Nodo>();
       
        Nodo elegido;
        ArrayList<Nodo> sucesores;

        elegido = new Nodo(problema.estadoInicial());

        abiertos.push(elegido);
        // Bucle principal
        do {
           
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
     	System.out.println("****   SOLUCIÓN ALGORITMO PROFUNDIDAD - DepthFirst *****");
        for(int i=0;i<secuenciaAcciones.size();i++){
            longSol++;
            System.out.println(secuenciaAcciones.get(i).toString());
        }
    }
}
