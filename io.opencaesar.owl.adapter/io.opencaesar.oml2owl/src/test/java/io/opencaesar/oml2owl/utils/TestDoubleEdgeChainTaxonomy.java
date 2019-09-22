package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDoubleEdgeChainTaxonomy {

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
	public void testChildrenOf() {
		assertEquals(setB, t.childrenOf(a));
		assertEquals(setC, t.childrenOf(b));
		assertTrue(t.childrenOf(c).isEmpty());
	}
	
	@Test
	public void testDirectChildrenOf() {
		assertEquals(setB, t.directChildrenOf(a));
		assertEquals(setC, t.directChildrenOf(b));
		assertTrue(t.directChildrenOf(c).isEmpty());
	}
	
	@Test
	public void testDescendantsOf() {
		assertEquals(setBC, t.descendantsOf(a));
		assertEquals(setC, t.descendantsOf(b));
		assertTrue(t.descendantsOf(c).isEmpty());
	}
	
	@Test
	public void testParentsOf() {
		assert(t.parentsOf(a).isEmpty());
		assertEquals(setA, t.parentsOf(b));
		assertEquals(setB, t.parentsOf(c));
	}
	
	@Test
	public void testDirectParentsOf() {
		assert(t.directParentsOf(a).isEmpty());
		assertEquals(setA, t.directParentsOf(b));
		assertEquals(setB, t.directParentsOf(c));
	}
	
	@Test
	public void testAncestorsOf() {
		assert(t.ancestorsOf(a).isEmpty());
		assertEquals(setA, t.ancestorsOf(b));
		assertEquals(setAB, t.ancestorsOf(c));
	}
	
	@Test
	public void testMultiParentChild() {
		assertFalse(t.multiParentChild().isPresent());
	}

	@Test
	public void testTransitiveReduction() {
		Taxonomy t1 = (Taxonomy) t.clone();
		assertEquals(t, t.transitiveReduction());
		t1.addEdge(a, c);
		assertEquals(t, t1.transitiveReduction());
	}

	@Test
	public void testTreeify() {
		assertEquals(t, t.treeify());
	}

}
