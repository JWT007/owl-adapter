package io.opencaesar.oml2owl.utils

import io.opencaesar.oml.Entity
import io.opencaesar.oml.util.OmlRead
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectUnionOf

class OwlClassExpression {
	
	static dispatch def OWLClass toOwlClassExpression(Universal u, OwlApi a) {
		a.getOWLThing
	}
	
	static dispatch def OWLClass toOwlClassExpression(Empty e, OwlApi a) {
		a.getOWLNothing
	}
	
	static dispatch def OWLClass toOwlClassExpression(Singleton s, OwlApi a) {
		a.getOWLClass(IRI.create(OmlRead.getIri(s.encapsulatedClass as Entity)))
	}
	
	static dispatch def OWLObjectComplementOf toOwlClassExpression(Complement c, OwlApi a) {
		a.getOWLObjectComplementOf(c.e.toOwlClassExpression(a))
	}
	
	static dispatch def OWLClassExpression toOwlClassExpression(Difference d, OwlApi a) {
		d.a.intersection(d.b.complement).toOwlClassExpression(a)
	}

	static dispatch def OWLObjectIntersectionOf toOwlClassExpression(Intersection i, OwlApi a) {
		a.getOWLObjectIntersectionOf(i.s.map[toOwlClassExpression(a)].toSet)
	}

	static dispatch def OWLObjectUnionOf toOwlClassExpression(Union u, OwlApi a) {
		a.getOWLObjectUnionOf(u.s.map[toOwlClassExpression(a)].toSet)
	}

}
