package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDoubleEdgeTaxonomy {

	Taxonomy t;
	Singleton a;
	Singleton b;
	Singleton c;
	HashSet<ClassExpression> setA;
	HashSet<ClassExpression> setAB;
	HashSet<ClassExpression> setB;
	HashSet<ClassExpression> setBC;
	HashSet<ClassExpression> setC;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		t = new Taxonomy();
		a = new Singleton("a");
		b = new Singleton("b");
		c = new Singleton("c");
		setA = new HashSet<ClassExpression>(Arrays.asList(a));
		setAB = new HashSet<ClassExpression>(Arrays.asList(a, b));
		setB = new HashSet<ClassExpression>(Arrays.asList(b));
		setBC = new HashSet<ClassExpression>(Arrays.asList(b, c));
		setC = new HashSet<ClassExpression>(Arrays.asList(c));
		t.addVertex(a);
		t.addVertex(b);
		t.addVertex(c);
		t.addEdge(a, b);
		t.addEdge(b, c);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void childrenOf() {
		assertEquals(setB, t.childrenOf(a));
		assertEquals(setC, t.childrenOf(b));
		assert(t.childrenOf(c).isEmpty());
	}
	
	@Test
	public void descendantsOf() {
		assertEquals(setBC, t.childrenOf(b));
		assertEquals(setB, t.descendantsOf(b));
		assert(t.descendantsOf(c).isEmpty());
	}
	
	@Test
	public void parentsOf() {
		assert(t.parentsOf(a).isEmpty());
		assertEquals(setA, t.parentsOf(b));
		assertEquals(setB, t.parentsOf(c));
	}
	
	@Test
	public void ancestorsOf() {
		assert(t.ancestorsOf(a).isEmpty());
		assertEquals(setA, t.ancestorsOf(b));
		assertEquals(setAB, t.ancestorsOf(c));
	}
	
	@Test
	public void testMultiParentChild() {
		assert(!t.multiParentChild().isPresent());
	}

}
