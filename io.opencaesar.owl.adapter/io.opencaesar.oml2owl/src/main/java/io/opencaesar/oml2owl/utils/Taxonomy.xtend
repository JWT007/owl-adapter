package io.opencaesar.oml2owl.utils

import java.util.Optional
import java.util.Set
import java.util.Map
import java.util.stream.Collectors
import java.util.HashMap

import org.eclipse.xtext.util.Tuples

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DirectedAcyclicGraph

class Taxonomy extends DirectedAcyclicGraph<ClassExpression, DefaultEdge> {
	
	new() {
		super(DefaultEdge)
	}
	
	new(Map<String, String> edgeMap) {
		super(DefaultEdge)
		val Set<String> vertexNames = edgeMap.keySet
		vertexNames.addAll(edgeMap.values)
		val HashMap<String, Singleton> vertexMap = new HashMap<String, Singleton>
		vertexNames.forEach[ vn | vertexMap.put(vn, new Singleton(vn)) ]
		edgeMap.forEach[ pn, cn | addEdge(vertexMap.get(pn), vertexMap.get(cn)) ]
	}
	
	def Set<ClassExpression> childrenOf(ClassExpression v) {
		outgoingEdgesOf(v).stream.map[getEdgeTarget].collect(Collectors.toSet)
	}
	
	def Set<ClassExpression> descendantsOf(ClassExpression v) {
		getDescendants(v)
	}
	
	def Set<ClassExpression> directChildrenOf(ClassExpression v) {
		childrenOf(v).stream.filter[c | !descendantsOf(v).contains(c)].collect(Collectors.toSet)
	}
	
	def Set<ClassExpression> parentsOf(ClassExpression v) {
		incomingEdgesOf(v).stream.map[getEdgeSource].collect(Collectors.toSet)		
	}
	
	def Set<ClassExpression> ancestorsOf(ClassExpression v) {
		getAncestors(v)
	}
	
	def Set<ClassExpression> directParentsOf(ClassExpression v) {
		parentsOf(v).stream.filter[c | !ancestorsOf(v).contains(c)].collect(Collectors.toSet)
	}
	
	def Optional<ClassExpression> multiParentChild() {
		vertexSet.stream.filter[parentsOf.length > 1].findFirst
	}
	
	/**
	 * Bypass a single parent of a child.
	 * 
	 * @param	child ClassExpression
	 * @param	parent ClassExpression
	 * @return Taxonomy
	 */
	def Taxonomy bypass_parent(ClassExpression child, ClassExpression parent) {
		
		val Taxonomy g = new Taxonomy
		
		// Copy all vertices.
		
		vertexSet.forEach[ClassExpression v | g.addVertex(v)]
		
		// Copy all edges except that from parent to child.
		
		edgeSet.stream.map[DefaultEdge e | Tuples.pair(e.getEdgeSource, e.getEdgeTarget)]
			.filter[getFirst != parent && getSecond != child]
			.forEach[p | g.addEdge(p.getFirst, p.getSecond)]
		
		// Add edges from direct grandparents to child.
		
		directParentsOf(parent).forEach[ gp | g.addEdge(gp, child)]
		
		g
	}
	
	/*
	 * Recursively bypass parents of a child.
	 * 
	 * @param	child ClassExpression
	 * @param	parent ClassExpression
	 * @return Taxonomy
	 */
	def Taxonomy bypass_parents(ClassExpression child, Set<ClassExpression> parents) {
		
		if (parents.isEmpty)
			this
		else {
			val pl = parents.toList
			val first = pl.get(0)
			val rest = pl.drop(1).toSet
			bypass_parent(child, first).bypass_parents(child, rest)
		}
		
	}
	
}