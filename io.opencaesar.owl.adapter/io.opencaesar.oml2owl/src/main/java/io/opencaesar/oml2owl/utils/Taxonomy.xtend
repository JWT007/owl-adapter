package io.opencaesar.oml2owl.utils

import java.util.List
import java.util.Set
import java.util.HashSet
import java.util.stream.Collectors
import java.util.Optional

import org.eclipse.xtext.util.Tuples

import org.jgrapht.graph.DirectedAcyclicGraph
import org.jgrapht.graph.DefaultEdge
import io.opencaesar.oml2owl.utils.ClassExpression
import java.util.List

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
	
	def Taxonomy bypass_parent(ClassExpression child, ClassExpression parent) {
		
		val Taxonomy g = new Taxonomy
		
		vertexSet.forEach[ClassExpression v | g.addVertex(v)]
		
		edgeSet.stream.map[DefaultEdge e | Tuples.pair(e.getEdgeSource, e.getEdgeTarget)]
			.filter[getFirst != parent && getSecond != child]
			.forEach[p | g.addEdge(p.getFirst, p.getSecond)]
		
		directParentsOf(parent).forEach[ gp | g.addEdge(gp, child)]
		
		g
	}
	
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