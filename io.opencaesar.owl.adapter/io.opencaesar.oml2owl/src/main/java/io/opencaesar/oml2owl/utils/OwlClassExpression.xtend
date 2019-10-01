package io.opencaesar.oml2owl.utils

import io.opencaesar.oml.Entity
import io.opencaesar.oml.Oml
import java.util.ArrayList
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectUnionOf

class OwlClassExpression {
	
	static dispatch def OWLClass toOwlClassExpression(Universal u, OWLDataFactory f) {
		f.getOWLThing
	}
	
	static dispatch def OWLClass toOwlClassExpression(Empty e, OWLDataFactory f) {
		f.getOWLNothing
	}
	
	static dispatch def OWLClass toOwlClassExpression(Singleton s, OWLDataFactory f) {
		f.getOWLClass(IRI.create(Oml.getIri(s.encapsulatedClass as Entity)))
	}
	
	static dispatch def OWLObjectComplementOf toOwlClassExpression(Complement c, OWLDataFactory f) {
		f.getOWLObjectComplementOf(c.e.toOwlClassExpression(f))
	}
	
	static dispatch def OWLObjectIntersectionOf toOwlClassExpression(Difference d, OWLDataFactory f) {
		val operands = new ArrayList<OWLClassExpression>
		operands.add(d.a.toOwlClassExpression(f))
		operands.add(d.b.complement.toOwlClassExpression(f))
		f.getOWLObjectIntersectionOf(operands)
	}

	static dispatch def OWLObjectIntersectionOf toOwlClassExpression(Intersection i, OWLDataFactory f) {
		val operands = i.s.map[toOwlClassExpression(f)].toList
		f.getOWLObjectIntersectionOf(operands)
	}

	static dispatch def OWLObjectUnionOf toOwlClassExpression(Union u, OWLDataFactory f) {
		val operands = u.s.map[toOwlClassExpression(f)].toList
		f.getOWLObjectUnionOf(operands)
	}

}