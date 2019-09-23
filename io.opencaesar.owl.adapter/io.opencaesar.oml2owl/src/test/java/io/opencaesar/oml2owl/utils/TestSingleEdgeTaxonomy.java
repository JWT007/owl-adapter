package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSingleEdgeTaxonomy {

	Taxonomy tAB;
	Taxonomy tB;
	Singleton a;
	Singleton b;
	HashSet<ClassExpression> setA;
	HashSet<ClassExpression> setB;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tAB = new Taxonomy();
		tB = new Taxonomy();
		a = new Singleton("a");
		b = new Singleton("b");
		setA = new HashSet<ClassExpression>(Arrays.asList(a));
		setB = new HashSet<ClassExpression>(Arrays.asList(b));
		tAB.addVertex(a);
		tAB.addVertex(b);
		tAB.addEdge(a, b);
		tB.addVertex(b);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		assertEquals(setB, tAB.childrenOf(a));
		assert(tAB.childrenOf(b).isEmpty());
	}
	
	@Test
	public void testDirectChildrenOf() {
		assertEquals(setB, tAB.directChildrenOf(a));
		assert(tAB.directChildrenOf(b).isEmpty());
	}
	
	@Test
	public void testDescendantsOf() {
		assertEquals(setB, tAB.descendantsOf(a));
		assert(tAB.descendantsOf(b).isEmpty());
	}
	
	@Test
	public void testParentsOf() {
		assertEquals(setA, tAB.parentsOf(b));
		assert(tAB.parentsOf(a).isEmpty());
	}
	
	@Test
	public void testDirectParentsOf() {
		assertEquals(setA, tAB.directParentsOf(b));
		assert(tAB.directParentsOf(a).isEmpty());
	}
	
	@Test
	public void testAncestorsOf() {
		assertEquals(setA, tAB.ancestorsOf(b));
		assert(tAB.ancestorsOf(a).isEmpty());
	}
	
	@Test
	public void testExciseVertex() {
		assertEquals(tB, tAB.exciseVertex(a));
	}

	@Test
	public void testExciseVertices() {
		assertEquals(tB, tAB.exciseVertices(setA));
	}

	@Test
	public void testExciseVerticesIf() {
		assertEquals(tB, tAB.exciseVerticesIf(v -> v == a));
	}

	@Test
	public void testRootAt() {
		assertEquals(tAB, tB.rootAt(a));
	}

	@Test
	public void testMultiParentChild() {
		assertFalse(tAB.multiParentChild().isPresent());
	}

	@Test
	public void testTreeify() {
		assertEquals(tAB, tAB.treeify());
	}

}
