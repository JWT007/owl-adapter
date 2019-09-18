package io.opencaesar.oml2owl.utils

import java.util.Set
import java.util.HashSet
import java.util.stream.Collectors
import java.util.Optional

import org.jgrapht.graph.DirectedAcyclicGraph
import org.jgrapht.graph.DefaultEdge
import io.opencaesar.oml2owl.utils.ClassExpression

class Taxonomy extends DirectedAcyclicGraph<ClassExpression, DefaultEdge> {
	
	new() {
		super(DefaultEdge)
	}
	
	def Set<ClassExpression> childrenOf(ClassExpression v) {
		outgoingEdgesOf(v).stream.map[getEdgeTarget].collect(Collectors.toSet)
	}
	
	def Set<ClassExpression> descendantsOf(ClassExpression v) {
		getDescendants(v)
	}
	
	def Set<ClassExpression> parentsOf(ClassExpression v) {
		incomingEdgesOf(v).stream.map[getEdgeSource].collect(Collectors.toSet)		
	}
	
	def Set<ClassExpression> ancestorsOf(ClassExpression v) {
		getAncestors(v)
	}
	
	def Optional<ClassExpression> multiParentChild() {
		vertexSet.stream.filter[parentsOf.length > 1].findFirst
	}
	
	def Taxonomy bypass_parent(ClassExpression child, ClassExpression parent) {
		
		val Taxonomy g = new Taxonomy
		
		vertexSet.forEach[ClassExpression v | g.addVertex(v)]
		
		edgeSet.stream.filter[getEdgeSource() != parent && getEdgeTarget != child].forEach[
			DefaultEdge e | g.addEdge(e.getEdgeSource)
		]
		
		g
	}
	
	def Taxonomy bypass_parents(ClassExpression child, Set<ClassExpression> parents) {
		this
	}
}