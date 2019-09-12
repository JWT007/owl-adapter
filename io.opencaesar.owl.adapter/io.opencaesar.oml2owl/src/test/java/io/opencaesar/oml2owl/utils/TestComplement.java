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

public class TestComplement {

	private Singleton sa1;
	private Singleton sa2;
	private Singleton sb;
	
	private Complement ca1;
	private Complement ca2;
	private Complement cb;
	
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
		ca1 = new Complement(sa1);
		ca2 = new Complement(sa2);
		cb = new Complement(sb);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertEquals(ca1.hashCode(), ca2.hashCode());
		assertNotEquals(ca1.hashCode(), cb.hashCode());
		assertNotEquals(ca2.hashCode(), cb.hashCode());
	}

	@Test
	public void testComplement() {
		assertNotNull(ca1);
		assertNotNull(ca2);
		assertNotNull(cb);
	}

	@Test
	public void testToAtom() {
		String caa = "a\u2032";
		String cba = "b\u2032";
		assertEquals(caa, ca1.toAtom());
		assertEquals(caa, ca2.toAtom());
		assertEquals(cba, cb.toAtom());
	}

	@Test
	public void testComplement1() {
		// Theorem 1
		assertEquals(sa1, ca1.complement());
		assertEquals(sa2, ca2.complement());
		assertEquals(sb, cb.complement());
	}

	@Test
	public void testEqualsObject() {
		assertEquals(ca1, ca2);
		assertNotEquals(ca1, cb);
		assertNotEquals(ca2, cb);
	}

	@Test
	public void testToString() {
		String caa = "a\u2032";
		String cba = "b\u2032";
		assertEquals(caa, ca1.toString());
		assertEquals(caa, ca2.toString());
		assertEquals(cba, cb.toString());
	}

	@Test
	public void testDifference() {
		Difference amb = new Difference(ca1, cb);
		Difference bma = new Difference(cb, ca1);
		assertEquals(amb, ca1.difference(cb));
		assertEquals(bma, cb.difference(ca1));
	}

	@Test
	public void testIntersection() {
		Complement sl[] = {ca1, cb};
		HashSet<ClassExpression> s = new HashSet<ClassExpression>(Arrays.asList(sl));
		Intersection i = new Intersection(s);
		assertEquals(i, ca1.intersection(cb));
		assertEquals(i, cb.intersection(ca1));
	}

	@Test
	public void testUnion() {
		Complement sl[] = {ca1, cb};
		HashSet<ClassExpression> s = new HashSet<ClassExpression>(Arrays.asList(sl));
		Union u = new Union(s);
		assertEquals(u, ca1.union(cb));
		assertEquals(u, cb.union(ca1));
	}

}
