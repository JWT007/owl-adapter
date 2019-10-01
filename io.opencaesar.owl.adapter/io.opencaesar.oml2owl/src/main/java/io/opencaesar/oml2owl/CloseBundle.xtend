package io.opencaesar.oml2owl

import io.opencaesar.oml.Aspect
import io.opencaesar.oml.Graph
import io.opencaesar.oml.TermSpecializationAxiom
import io.opencaesar.oml2owl.utils.Singleton
import io.opencaesar.oml2owl.utils.Taxonomy
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.semanticweb.owlapi.model.OWLOntology

import static extension io.opencaesar.oml.Oml.*
import io.opencaesar.oml.Entity

class CloseBundle {
	
	protected val Resource resource
	protected val OWLOntology ontology
	protected val OwlApi owlApi
		
  	new(Resource resource, OWLOntology ontology, OwlApi owlApi) {
		this.resource = resource
		this.ontology = ontology
		this.owlApi = owlApi
	}
	
	private def Taxonomy omlTaxonomy(List<Graph> allGraphs) {

		val Taxonomy taxonomy = new Taxonomy

		allGraphs.forEach [ g |
			g.eAllContents.filter(Entity).forEach [ Entity entity |
				System.out.println("addVertex(" + entity.toString + ")")
				taxonomy.addVertex(new Singleton(entity.iri))
			]
		]
		
		allGraphs.forEach [ g |
			g.eAllContents.filter(TermSpecializationAxiom).forEach [ axiom |
				val specialized = axiom.specializedTerm
				val specializing = axiom.specializingTerm

				if ((specialized instanceof Entity) &&
					!(specialized instanceof Aspect) &&
					(specializing instanceof Entity) &&
					!(specializing instanceof Aspect)) {
					val specializedSingleton = new Singleton(specialized.iri)
					val specializingSingleton = new Singleton(specializing.iri)
					System.out.println("addEdge(" + specializedSingleton.toString + ", " + specializingSingleton + ")")
					taxonomy.addEdge(specializedSingleton, specializingSingleton)
				}
			]
		]
		taxonomy
	}
	
	def void run() {
		
		val graph = resource.contents.filter(Graph).findFirst[true]
		val allGraphs = graph.allImports.map[importedGraph].toList
		allGraphs.add(graph)
		val Singleton owlThing = new Singleton("owl:Thing")
		val Taxonomy taxonomy = omlTaxonomy(allGraphs).rootAt(owlThing).treeify
		val siblingMap = taxonomy.siblingMap
		siblingMap.forEach[ k, v |
			owlApi.addDisjointClassesAxiom(ontology, v)
		]
		
	}
}