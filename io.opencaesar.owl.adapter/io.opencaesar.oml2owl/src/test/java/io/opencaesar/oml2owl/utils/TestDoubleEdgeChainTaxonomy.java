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

	Taxonomy taxonomyABC;
	Taxonomy taxonomyBC;
	Taxonomy taxonomyAC;
	Taxonomy taxonomyAB;
	Taxonomy taxonomyA;
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
		taxonomyABC = new Taxonomy();
		taxonomyBC = new Taxonomy();
		taxonomyAC = new Taxonomy();
		taxonomyAB = new Taxonomy();
		taxonomyA = new Taxonomy();
		a = new Singleton("a");
		b = new Singleton("b");
		c = new Singleton("c");
		setA = new HashSet<ClassExpression>(Arrays.asList(a));
		setAB = new HashSet<ClassExpression>(Arrays.asList(a, b));
		setB = new HashSet<ClassExpression>(Arrays.asList(b));
		setBC = new HashSet<ClassExpression>(Arrays.asList(b, c));
		setC = new HashSet<ClassExpression>(Arrays.asList(c));
		taxonomyABC.addVertex(a);
		taxonomyABC.addVertex(b);
		taxonomyABC.addVertex(c);
		taxonomyABC.addEdge(a, b);
		taxonomyABC.addEdge(b, c);
		taxonomyBC.addVertex(b);
		taxonomyBC.addVertex(c);
		taxonomyBC.addEdge(b, c);
		taxonomyAC.addVertex(a);
		taxonomyAC.addVertex(c);
		taxonomyAC.addEdge(a, c);
		taxonomyAB.addVertex(a);
		taxonomyAB.addVertex(b);
		taxonomyAB.addEdge(a, b);
		taxonomyA.addVertex(a);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		assertEquals(setB, taxonomyABC.childrenOf(a));
		assertEquals(setC, taxonomyABC.childrenOf(b));
		assertTrue(taxonomyABC.childrenOf(c).isEmpty());
	}
	
	@Test
	public void testDirectChildrenOf() {
		assertEquals(setB, taxonomyABC.directChildrenOf(a));
		assertEquals(setC, taxonomyABC.directChildrenOf(b));
		assertTrue(taxonomyABC.directChildrenOf(c).isEmpty());
	}
	
	@Test
	public void testDescendantsOf() {
		assertEquals(setBC, taxonomyABC.descendantsOf(a));
		assertEquals(setC, taxonomyABC.descendantsOf(b));
		assertTrue(taxonomyABC.descendantsOf(c).isEmpty());
	}
	
	@Test
	public void testParentsOf() {
		assert(taxonomyABC.parentsOf(a).isEmpty());
		assertEquals(setA, taxonomyABC.parentsOf(b));
		assertEquals(setB, taxonomyABC.parentsOf(c));
	}
	
	@Test
	public void testDirectParentsOf() {
		assert(taxonomyABC.directParentsOf(a).isEmpty());
		assertEquals(setA, taxonomyABC.directParentsOf(b));
		assertEquals(setB, taxonomyABC.directParentsOf(c));
	}
	
	@Test
	public void testAncestorsOf() {
		assert(taxonomyABC.ancestorsOf(a).isEmpty());
		assertEquals(setA, taxonomyABC.ancestorsOf(b));
		assertEquals(setAB, taxonomyABC.ancestorsOf(c));
	}
	
	@Test
	public void testMultiParentChild() {
		assertFalse(taxonomyABC.multiParentChild().isPresent());
	}

	@Test
	public void testExciseVertex() {
		assertEquals(taxonomyBC, taxonomyABC.exciseVertex(a));
		assertEquals(taxonomyAC, taxonomyABC.exciseVertex(b));
		assertEquals(taxonomyAB, taxonomyABC.exciseVertex(c));
	}

	@Test
	public void testExciseVertices() {
		assertEquals(taxonomyA, taxonomyABC.exciseVertices(setBC));
	}

	@Test
	public void testTransitiveReduction() {
		Taxonomy t1 = (Taxonomy) taxonomyABC.clone();
		assertEquals(taxonomyABC, taxonomyABC.transitiveReduction());
		t1.addEdge(a, c);
		assertEquals(taxonomyABC, t1.transitiveReduction());
	}

	@Test
	public void testTreeify() {
		assertEquals(taxonomyABC, taxonomyABC.treeify());
	}

}
