package io.opencaesar.oml2owl

import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.model.OWLOntology
import io.opencaesar.oml2owl.utils.Taxonomy
import java.util.HashMap
import io.opencaesar.oml2owl.utils.ClassExpression
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource

class CloseBundle {
	
	protected val Resource resource
	protected val OWLOntology ontology
	protected val OWLOntologyManager ontologyManager
	
  	new(Resource resource, OWLOntology ontology, OWLOntologyManager ontologyManager) {
		this.resource = resource
		this.ontology = ontology
		this.ontologyManager = ontologyManager
	}
	
	private def Taxonomy buildTaxonomy(Resource resource) {
	}
	
	private def Taxonomy treeifyTaxonomy(Taxonomy taxonomy) {
	}
	
	private def HashMap<ClassExpression, Set<ClassExpression>> getSiblingMap(Taxonomy tree) {		
	}
	
	private def OWLOntology addDisjoints(HashMap<ClassExpression, Set<ClassExpression>> siblingMap, OWLOntology ontology,
		OWLOntologyManager ontologyManager
	) {		
	}
	
	def OWLOntology run() {
		
		val Taxonomy taxonomy = buildTaxonomy(resource)
		val Taxonomy tree = treeifyTaxonomy(taxonomy)
		val siblingMap = getSiblingMap(tree)
		addDisjoints(siblingMap, ontology, ontologyManager)
	}
}