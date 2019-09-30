package io.opencaesar.oml2owl

import io.opencaesar.oml.Aspect
import io.opencaesar.oml.TermSpecializationAxiom
import io.opencaesar.oml2owl.utils.ClassExpression
import io.opencaesar.oml2owl.utils.Singleton
import io.opencaesar.oml2owl.utils.Taxonomy
import java.util.HashMap
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager

import static extension io.opencaesar.oml.Oml.*

class CloseBundle {
	
	protected val Resource resource
	protected val OWLOntology ontology
	protected val OWLOntologyManager ontologyManager
	
  	new(Resource resource, OWLOntology ontology, OWLOntologyManager ontologyManager) {
		this.resource = resource
		this.ontology = ontology
		this.ontologyManager = ontologyManager
	}
	
	private def Taxonomy omlTaxonomy(Resource resource) {

		val Taxonomy taxonomy = new Taxonomy

		resource.getAllContents.filter(TermSpecializationAxiom).forEach [ axiom |
			val specialized = axiom.specializedTerm
			val specializing = axiom.specializingTerm
			
			if (!(specialized instanceof Aspect) && !(specializing instanceof Aspect)) {
				val specializedSingleton = new Singleton(specialized)
				val specializingSingleton = new Singleton(specializing)
				taxonomy.addVertex(specializedSingleton)
				taxonomy.addVertex(specializingSingleton)
				taxonomy.addEdge(specializedSingleton, specializingSingleton)
			}
		]
		taxonomy
	}
	
	private def OWLOntology addDisjoints(HashMap<ClassExpression, Set<ClassExpression>> siblingMap,
		OWLOntology ontology,
		OWLOntologyManager ontologyManager
	) {		
	}
	
	def OWLOntology run() {
		
		val Singleton owlThing = new Singleton("owl:Thing")
		val Taxonomy taxonomy = omlTaxonomy(resource).rootAt(owlThing).treeify
		val siblingMap = taxonomy.siblingMap
		addDisjoints(siblingMap, ontology, ontologyManager)
	}
}