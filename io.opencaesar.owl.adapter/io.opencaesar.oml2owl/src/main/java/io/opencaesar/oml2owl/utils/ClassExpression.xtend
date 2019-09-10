package io.opencaesar.oml2owl.utils

import java.util.Set
import java.util.List
import java.util.HashSet

/**
 * ClassExpression implements methods for constructing class expressions,
 * including singleton expressions encapsulating a single class, complements, intersections,
 * and unions. While the library does not perform any mathematical reasoning, it makes
 * use of certain theorems to simplify expressions where possible:
 * 
 * Theorem 1: Set complement is idempotent. For any class A, (A′)′ = A.
 * 
 * Theorem 2: Set intersection is idempotent. For any class A, A ∩ A = A.
 * 
 * Theorem 3: Set intersection is commutative. For any classes A and B, A ∩ B = B ∩ A.
 * 
 * Theorem 4: Set intersection is associative. For any classes A, B, and C,
 * 				(A ∩ B) ∩ C = A ∩ (B ∩ C).
 * 
 * Theorem 5: Set union is idempotent. For any class A, A ∪ A = A.
 * 
 * Theorem 6: Set union is commutative. For any classes A and B, A ∪ B = B ∪ A.
 * 
 * Theorem 7: Set union is associative. For any classes A, B, and C,
 * 				(A ∪ B) ∪ C = A ∪ (B ∪ C).
 * 
 * Theorem 8: For any sets A, B, and C, (A\B)\C = A\(B∪C).
 * 
 * @author		Steven Jenkins <j.s.jenkins@jpl.nasa.gov>
 * @version		0.0.1
 * @since		0.0.1
 */
 abstract class ClassExpression {

	/*
	 * @return		ClassExpression The complement of this ClassExpression
	 */
	def ClassExpression complement() { new Complement(this) }
	
	/*
	 * @param		e ClassExpression 
	 * @return		ClassExpression The difference of this ClassExpression and another
	 * 				specified ClassExpression
	 */
	def ClassExpression difference(ClassExpression e) { new Difference(this, e) }
	
	/*
	 * @param		e ClassExpression 
	 * @return		ClassExpression The intersection of this ClassExpression and another
	 * 				specified ClassExpression
	 */	
	def ClassExpression intersection(ClassExpression e) {
		this.equals(e) ?
			// Theorem 2
			this :
				e.class.equals(Intersection) ?
					// Theorem 3
					e.intersection(this) : 
						new Intersection(new HashSet<ClassExpression>(#[this, e]))
	}
	
	/*
	 * @param		e ClassExpression 
	 * @return		ClassExpression The union of this ClassExpression and another
	 * 				specified ClassExpression
	 */
	def ClassExpression union(ClassExpression e) {
		this.equals(e) ?
			// Theorem 5
			this :
				e.class.equals(Union) ?
					// Theorem 6
					e.union(this) :
						new Union(new HashSet<ClassExpression>(#[this, e]))		
	}
	
	/*
	 * @param		o An arbitrary object
	 * @return		boolean true if and only if o denotes the same ClassExpression
	 */
	override boolean equals(Object o) {
		hashCode.equals(o.hashCode)
	}
	
	/*
	 * @return		String A string representation of this ClassExpression as an atom
	 */
	def String toAtom() { "(" + toString + ")"}
	
}

/*
 * Singleton implements methods for ClassExpressions that encapsulate an arbitrary
 * object representing a single class.

 * @author		Steven Jenkins <j.s.jenkins@jpl.nasa.gov>
 * @version		0.0.1
 * @since		0.0.1
 */
class Singleton extends ClassExpression {
	
	protected val Object encapsulatedClass
	
	/*
	 * @param		encapsulatedClass An arbitrary object representing a class
	 * @return		Singleton A Singleton encapsulating the specified class
	 */
	new(Object encapsulatedClass) {
		this.encapsulatedClass = encapsulatedClass
	}
	
	/*
	 * @return		int hash code of the Singleton
	 */
	override int hashCode() {
		#[Singleton, encapsulatedClass].hashCode
	}
	
	/*
	 * @return		String a string representation of the encapsulated class
	 */
	override String toString() {
		encapsulatedClass.toString
	}
	
	/*
	 * @return		String a string representation of the encapsulated class as an atom
	 */
	override String toAtom() {
		toString
	}
	
}

/*
 * Unary implements methods for ClassExpressions denoting an operation on
 * a single ClassExpression.

 * @author		Steven Jenkins <j.s.jenkins@jpl.nasa.gov>
 * @version		0.0.1
 * @since		0.0.1
 */
abstract class Unary extends ClassExpression {
	
	protected ClassExpression e
	
	/*
	 * @param		e a ClassExpression
	 * @return		Unary involving e
	 */
	new(ClassExpression e) {
		this.e = e
	}
	
	/*
	 * @return		int hash code of the Unary
	 */
	override int hashCode() {
		#[this.class, e].hashCode
	}
	
}

/*
 * Complement implements methods for ClassExpressions denoting complements.

 * @author		Steven Jenkins <j.s.jenkins@jpl.nasa.gov>
 * @version		0.0.1
 * @since		0.0.1
 */
class Complement extends Unary {	
	
	/*
	 * @param		e a Class Expression
	 * @return		ClassExpression the complement of e
	 */
	new(ClassExpression e) {
		super(e)
	}
	
	/*
	 * @return		String string denoting this Complement
	 */
	override String toString() {
		e.toAtom + "′"
	}
	
	/*
	 * @return		String string denoting this Complement as an atom
	 */
	override String toAtom() {
		toString
	}
	
	/*
	 * @return		ClassExpression the complement of this Complement (simplified)
	 */
	override ClassExpression complement() {
		// Theorem 1
		e
	}
	
}

/*
 * Binary implements methods for ClassExpressions denoting a operation on two
 * 		ClassExpressions.

 * @author		Steven Jenkins <j.s.jenkins@jpl.nasa.gov>
 * @version		0.0.1
 * @since		0.0.1
 */
abstract class Binary extends ClassExpression {
	
	protected ClassExpression a
	protected ClassExpression b
	
	/*
	 * @param		a a ClassExpression
	 * @param		b a ClassExpression
	 * @return		Binary involving a and b
	 */
	new(ClassExpression a, ClassExpression b) {
		this.a = a
		this.b = b
	}
	
	/*
	 * @return		int hash code of the Binary
	 */
	override int hashCode() {
		#[this.class, a, b].hashCode
	}
	
	/*
	 * @param		op String denoting binary operator
	 * @return		String denoting this Binary
	 */
	def String toString(String op) {
		a.toString + op + b.toString
	}
		
}

/*
 * Difference implements methods for ClassExpressions denoting set differences.
 */
class Difference extends Binary {
	
	/*
	 * @param		minuend a ClassExpression
	 * @param		subtrahend a ClassExpression
	 * @return		Difference denoting minuend minus subtrahend
	 */
	new(ClassExpression minuend, ClassExpression subtrahend) {
		super(minuend, subtrahend)
	}
	
	/*
	 * @return		String denoting this Difference
	 */
	override toString() {
		toString("\\")
	}
	
	/*
	 * @param		e ClassExpression 
	 * @return		ClassExpression The difference of this ClassExpression and another
	 * 				specified ClassExpression (simplified)
	 */
	override difference(ClassExpression e) {
		// Theorem 8
		new Difference(a, b.union(e))
	}
	
}

/*
 * Nary implements methods for ClassExpressions that denote an operation on a set of
 * ClassExpressions.
 */
abstract class Nary extends ClassExpression {
	
	protected Set<ClassExpression> s
	
	/*
	 * @param		s Set<ClassExpression>
	 * @return		Nary involving s
	 */
	new(Set<ClassExpression> s) {
		this.s = s
	}
	
	/*
	 * @return		int hash code of the Nary
	 */
	override int hashCode() {
		#[this.class, s].hashCode
	}
	
	/*
	 * @param		c String denoting the operation
	 * @return		String denoting this Nary
	 */
	def String toString(String c) {
		String.join(c, s.stream().map[toString] as List<String>)
	}
	
}

/*
 * Intersection implements methods for ClassExpressions that denote the intersection of a set of
 * ClassExpressions.
 */
class Intersection extends Nary {
	
	/*
	 * @param		s Set<ClassExpression>
	 * @return		Intersection of s 
	 */
	new(Set<ClassExpression> s) {
		super(s)
	}
	
	/*
	 * @return		String denoting this Intersection
	 */
	override String toString() {
		toString("∩")
	}
	
	/*
	 * @param		e ClassExpression
	 * @return		Intersection denoting intersection of this Intersection with e (simplified)
	 */
	override intersection(ClassExpression e) {		
		val newSet = new HashSet(s)		
		// Theorem 4
		if (e.class == Intersection)
			newSet.addAll((e as Intersection).s)
		else
			newSet.add(e)						
		new Intersection(newSet)
	}
}

/*
 * Union implements methods for ClassExpressions that denote the union of a set of
 * ClassExpressions.
 */
class Union extends Nary {
	
	/*
	 * @param		s Set<ClassExpression>
	 * @return		Union of s 
	 */
	new(Set<ClassExpression> s) {
		super(s)
	}
	
	/*
	 * @return		String denoting this Union
	 */
	override String toString() {
		toString("∪")
	}
	
	/*
	 * @param		e ClassExpression
	 * @return		Union denoting union of this Union with e (simplified)
	 */
	override intersection(ClassExpression e) {
		val newSet = new HashSet(s)
		// Theorem 6
		if (e.class == Union)
			newSet.addAll((e as Union).s)
		else
			newSet.add(e)			
		new Intersection(newSet)
	}
	
}