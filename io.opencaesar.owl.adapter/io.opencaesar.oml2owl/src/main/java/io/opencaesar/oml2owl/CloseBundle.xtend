package io.opencaesar.oml2owl

import org.eclipse.xtext.resource.XtextResourceSet
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.model.OWLOntology
import io.opencaesar.oml2owl.utils.Taxonomy
import java.util.HashMap
import io.opencaesar.oml2owl.utils.ClassExpression
import java.util.Set

class CloseBundle {
	
	protected val XtextResourceSet resourceSet
	protected val OWLOntologyManager ontologyManager
	
  	new(XtextResourceSet rs, OWLOntologyManager om) {
		resourceSet = rs
		ontologyManager = om
	}
	
	private def Taxonomy buildTaxonomy(XtextResourceSet resourceSet) {
	}
	
	private def Taxonomy treeifyTaxonomy(Taxonomy taxonomy) {
	}
	
	private def HashMap<ClassExpression, Set<ClassExpression>> getSiblingMap(Taxonomy tree) {		
	}
	
	private def OWLOntology getClosureOntology(HashMap<ClassExpression, Set<ClassExpression>> siblingMap, OWLOntologyManager ontologyManager) {		
	}
	
	def OWLOntology run() {
		
		val Taxonomy taxonomy = buildTaxonomy(resourceSet)
		val Taxonomy tree = treeifyTaxonomy(taxonomy)
		val siblingMap = getSiblingMap(tree)
		val closure = getClosureOntology(siblingMap, ontologyManager)
		closure
		
	}
}