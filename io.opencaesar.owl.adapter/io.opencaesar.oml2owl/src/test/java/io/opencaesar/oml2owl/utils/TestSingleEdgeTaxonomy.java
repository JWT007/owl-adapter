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

	Taxonomy t;
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
		t = new Taxonomy();
		a = new Singleton("a");
		b = new Singleton("b");
		setA = new HashSet<ClassExpression>(Arrays.asList(a));
		setB = new HashSet<ClassExpression>(Arrays.asList(b));
		t.addVertex(a);
		t.addVertex(b);
		t.addEdge(a,  b);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		assertEquals(setB, t.childrenOf(a));
		assert(t.childrenOf(b).isEmpty());
	}
	
	@Test
	public void testDirectChildrenOf() {
		assertEquals(setB, t.directChildrenOf(a));
		assert(t.directChildrenOf(b).isEmpty());
	}
	
	@Test
	public void testDescendantsOf() {
		assertEquals(setB, t.descendantsOf(a));
		assert(t.descendantsOf(b).isEmpty());
	}
	
	@Test
	public void testParentsOf() {
		assertEquals(setA, t.parentsOf(b));
		assert(t.parentsOf(a).isEmpty());
	}
	
	@Test
	public void testDirectParentsOf() {
		assertEquals(setA, t.directParentsOf(b));
		assert(t.directParentsOf(a).isEmpty());
	}
	
	@Test
	public void testAncestorsOf() {
		assertEquals(setA, t.ancestorsOf(b));
		assert(t.ancestorsOf(a).isEmpty());
	}
	
	@Test
	public void testMultiParentChild() {
		assertFalse(t.multiParentChild().isPresent());
	}

	@Test
	public void testTreeify() {
		assertEquals(t, t.treeify());
	}

}