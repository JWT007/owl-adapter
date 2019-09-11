package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import io.opencaesar.oml2owl.utils.Singleton;
import io.opencaesar.oml2owl.utils.ClassExpression;
import io.opencaesar.oml2owl.utils.Complement;
import io.opencaesar.oml2owl.utils.Difference;
import io.opencaesar.oml2owl.utils.Intersection;
import io.opencaesar.oml2owl.utils.Union;

public class TestSingleton {

	private Singleton sa1;
	private Singleton sa2;
	private Singleton sb;
	
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertEquals(sa1.hashCode(), sa2.hashCode());
		assertNotEquals(sa1.hashCode(), sb.hashCode());
		assertNotEquals(sa2.hashCode(), sb.hashCode());
	}

	@Test
	public void testToAtom() {
		assertEquals("a", sa1.toAtom());
		assertEquals("a", sa2.toAtom());
		assertEquals("b", sb.toAtom());
	}

	@Test
	public void testSingleton() {
		assertNotNull(sa1);
		assertNotNull(sa2);
		assertNotNull(sb);
	}

	@Test
	public void testEqualsObject() {
		assertEquals(sa1, sa2);
		assertNotEquals(sa1, sb);
		assertNotEquals(sa2, sb);
	}

	@Test
	public void testToString() {
		assertEquals("a", sa1.toString());
		assertEquals("a", sa2.toString());
		assertEquals("b", sb.toString());
	}

	@Test
	public void testComplement() {
		Complement ca1 = new Complement(sa1);
		Complement ca2 = new Complement(sa2);
		assertEquals(ca1, sa1.complement());
		assertEquals(ca2, sa2.complement());
		assertEquals(ca1, sa2.complement());
		assertEquals(ca2, sa1.complement());
	}

	@Test
	public void testDifference() {
		Difference amb = new Difference(sa1, sb);
		Difference bma = new Difference(sb, sa1);
		assertEquals(amb, sa1.difference(sb));
		assertEquals(bma, sb.difference(sa1));
	}

	@Test
	public void testIntersection() {
		Singleton sl[] = {sa1, sb};
		HashSet<ClassExpression> s = new HashSet<ClassExpression>(Arrays.asList(sl));
		Intersection i = new Intersection(s);
		assertEquals(i, sa1.intersection(sb));
		assertEquals(i, sb.intersection(sa1));
	}

	@Test
	public void testUnion() {
		Singleton sl[] = {sa1, sb};
		HashSet<ClassExpression> s = new HashSet<ClassExpression>(Arrays.asList(sl));
		Union i = new Union(s);
		assertEquals(i, sa1.union(sb));
		assertEquals(i, sb.union(sa1));
	}

}
