package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.opencaesar.oml2owl.utils.Singleton;
import io.opencaesar.oml2owl.utils.ClassExpression;
import io.opencaesar.oml2owl.utils.Complement;
import io.opencaesar.oml2owl.utils.Difference;
import io.opencaesar.oml2owl.utils.Intersection;
import io.opencaesar.oml2owl.utils.Union;

public class TestIntersection {

	private Singleton sa1;
	private Singleton sa2;
	private Singleton sb;
	private Singleton sc;
	
	private Intersection a1;
	private Intersection a1ia2;
	private Intersection a1ia2ib;
	private Intersection a1ia2ibic;
	
	private Intersection a1ib;
	private Intersection a1ibic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sa1 = new Singleton("a");
		sa2 = new Singleton("a");
		sb = new Singleton("b");
		sc = new Singleton("c");

		Singleton sl1[] = {sa1, sa2, sb, sc};
		HashSet<ClassExpression> a1l = new HashSet<ClassExpression>(Arrays.asList(sl1).subList(0, 1));
		a1 = new Intersection(a1l);
		HashSet<ClassExpression> a1ia2l = new HashSet<ClassExpression>(Arrays.asList(sl1).subList(0, 2));
		a1ia2 = new Intersection(a1ia2l);
		HashSet<ClassExpression> a1ia2ibl = new HashSet<ClassExpression>(Arrays.asList(sl1).subList(0, 3));
		a1ia2ib = new Intersection(a1ia2ibl);
		HashSet<ClassExpression> a1ia2ibicl = new HashSet<ClassExpression>(Arrays.asList(sl1).subList(0, 4));
		a1ia2ibic = new Intersection(a1ia2ibicl);

		Singleton sl2[] = {sa1, sb, sc};		
		HashSet<ClassExpression> a1ibl = new HashSet<ClassExpression>(Arrays.asList(sl2).subList(0, 2));
		a1ib = new Intersection(a1ibl);
		HashSet<ClassExpression> a1ibicl = new HashSet<ClassExpression>(Arrays.asList(sl2).subList(0, 3));
		a1ibic = new Intersection(a1ibicl);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertEquals(a1.hashCode(), a1ia2.hashCode());
		assertNotEquals(a1.hashCode(), a1ia2ib.hashCode());
		assertNotEquals(a1.hashCode(), a1ia2ibic.hashCode());
		assertNotEquals(a1.hashCode(), a1ib.hashCode());
		assertNotEquals(a1.hashCode(), a1ibic.hashCode());
		assertNotEquals(a1ia2.hashCode(), a1ia2ib.hashCode());
		assertNotEquals(a1ia2.hashCode(), a1ia2ibic.hashCode());
		assertNotEquals(a1ia2.hashCode(), a1ib.hashCode());
		assertNotEquals(a1ia2.hashCode(), a1ibic.hashCode());
		assertNotEquals(a1ia2ib.hashCode(), a1ia2ibic.hashCode());
		assertEquals(a1ia2ib.hashCode(), a1ib.hashCode());
		assertNotEquals(a1ia2ib.hashCode(), a1ibic.hashCode());
		assertNotEquals(a1ia2ibic.hashCode(), a1ib.hashCode());
		assertEquals(a1ia2ibic.hashCode(), a1ibic.hashCode());
		assertNotEquals(a1ib.hashCode(), a1ibic.hashCode());
	}

	@Test
	public void testIntersection() {
		assertNotNull(a1);
		assertNotNull(a1ia2);
		assertNotNull(a1ia2ib);
		assertNotNull(a1ia2ibic);
	}

	@Test
	public void testIntersection1() {
		assertEquals(a1ia2, a1.intersection(sa2));
		assertEquals(a1ia2ib, a1.intersection(sa2).intersection(sb));
		assertEquals(a1ia2ib, a1.intersection(sb).intersection(sa2));
		assertEquals(a1ia2ib, a1ia2.intersection(sb));
		assertEquals(a1ia2ibic, a1.intersection(sa2).intersection(sb).intersection(sc));
		assertEquals(a1ia2ibic, a1ia2.intersection(sb).intersection(sc));
		assertEquals(a1ia2ibic, a1ia2.intersection(sc).intersection(sb));
		assertEquals(a1ia2ibic, a1ia2ib.intersection(sc));
	}

	@Test
	public void testEqualsObject() {
		assertEquals(a1, a1ia2);
		assertNotEquals(a1, a1ia2ib);
		assertNotEquals(a1, a1ia2ibic);
		assertNotEquals(a1, a1ib);
		assertNotEquals(a1, a1ibic);
		assertNotEquals(a1ia2, a1ia2ib);
		assertNotEquals(a1ia2, a1ia2ibic);
		assertNotEquals(a1ia2, a1ib);
		assertNotEquals(a1ia2, a1ibic);
		assertNotEquals(a1ia2ib, a1ia2ibic);
		assertEquals(a1ia2ib, a1ib);
		assertNotEquals(a1ia2ib, a1ibic);
		assertNotEquals(a1ia2ibic, a1ib);
		assertEquals(a1ia2ibic, a1ibic);
		assertNotEquals(a1ib, a1ibic);
	}

	@Test
	public void testToString() {
		assertEquals("a", a1.toString());
		assertEquals("a", a1ia2.toString());
		assertEquals("a∩b", a1ia2ib.toString());
		assertEquals("a∩b", a1ib.toString());
		assertEquals("a∩b∩c", a1ia2ibic.toString());
		assertEquals("a∩b∩c", a1ibic.toString());
	}

	@Test
	public void testComplement() {
		Complement a1c = new Complement(a1);
		Complement a1ia2c = new Complement(a1ia2);
		Complement a1ia2ibc = new Complement(a1ia2ib);
		Complement a1ia2ibicc = new Complement(a1ia2ibic);
		assertEquals(a1c, a1.complement());
		assertEquals(a1ia2c, a1ia2.complement());
		assertEquals(a1ia2ibc, a1ia2ib.complement());
		assertEquals(a1ia2ibicc, a1ia2ibic.complement());
	}

	@Test
	public void testDifference() {
		Difference a1maa1ia2 = new Difference(a1, a1ia2);
		Difference a2ia2ibma1ia2ibic = new Difference(a1ia2ib, a1ia2ib);
		assertEquals(a1maa1ia2, a1.difference(a1ia2));
		assertEquals(a2ia2ibma1ia2ibic, a1ia2ib.difference(a1ia2ib));
	}

	@Test
	public void testUnion() {
		Intersection sl[] = {a1, a1ia2, a1ia2ib};
		HashSet<ClassExpression> s = new HashSet<ClassExpression>(Arrays.asList(sl));
		Union u = new Union(s);
		assertEquals(u, a1.union(a1ia2).union(a1ia2ib));
		assertEquals(u, a1ia2.union(a1ia2ib).union(a1));
	}

	@Test
	public void testToAtom() {
		assertEquals("a", a1.toAtom());
		assertEquals("a", a1ia2.toAtom());
		assertEquals("(a∩b)", a1ia2ib.toAtom());
		assertEquals("(a∩b)", a1ib.toAtom());
		assertEquals("(a∩b∩c)", a1ia2ibic.toAtom());
		assertEquals("(a∩b∩c)", a1ibic.toAtom());
	}

}
