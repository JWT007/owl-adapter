package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.opencaesar.oml2owl.utils.Singleton;
import io.opencaesar.oml2owl.utils.Complement;

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
		fail("Not yet implemented");
	}

	@Test
	public void testIntersection() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnion() {
		fail("Not yet implemented");
	}

}
