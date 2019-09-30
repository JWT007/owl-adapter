package io.opencaesar.oml2owl

import io.opencaesar.oml.Aspect
import io.opencaesar.oml.TermSpecializationAxiom
import io.opencaesar.oml2owl.utils.Singleton
import io.opencaesar.oml2owl.utils.Taxonomy
import org.eclipse.emf.ecore.resource.Resource
import org.semanticweb.owlapi.model.OWLOntology

import static extension io.opencaesar.oml.Oml.*

class CloseBundle {
	
	protected val Resource resource
	protected val OWLOntology ontology
	protected val OwlApi owlApi
		
  	new(Resource resource, OWLOntology ontology, OwlApi owlApi) {
		this.resource = resource
		this.ontology = ontology
		this.owlApi = owlApi
	}
	
	private def Taxonomy omlTaxonomy(Resource resource) {

		val Taxonomy taxonomy = new Taxonomy

		resource.getAllContents.filter(TermSpecializationAxiom).forEach [ axiom |
			val specialized = axiom.specializedTerm
			val specializing = axiom.specializingTerm
			
			if (!(specialized instanceof Aspect) && !(specializing instanceof Aspect)) {
				val specializedSingleton = new Singleton(specialized.iri)
				val specializingSingleton = new Singleton(specializing.iri)
				taxonomy.addVertex(specializedSingleton)
				taxonomy.addVertex(specializingSingleton)
				taxonomy.addEdge(specializedSingleton, specializingSingleton)
			}
		]
		taxonomy
	}
	
	def void run() {
		
		val Singleton owlThing = new Singleton("owl:Thing")
		val Taxonomy taxonomy = omlTaxonomy(resource).rootAt(owlThing).treeify
		val siblingMap = taxonomy.siblingMap
		siblingMap.forEach[ k, v |
			owlApi.addDisjointClassesAxiom(ontology, v)
		]
		
	}
}