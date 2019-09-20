package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSingleVertexTaxonomy {

	Taxonomy t;
	Singleton a;
	
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
		t.addVertex(a);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		assert(t.childrenOf(a).isEmpty());
	}
	
	public void testDirectChildrenOf() {
		assert(t.directChildrenOf(a).isEmpty());
	}
	
	@Test
	public void testDescendantsOf() {
		assert(t.descendantsOf(a).isEmpty());
	}
	
	@Test
	public void testParentsOf() {
		assert(t.parentsOf(a).isEmpty());
	}
	
	@Test
	public void testDirectParentsOf() {
		assert(t.directParentsOf(a).isEmpty());
	}
	
	@Test
	public void testAncestorsOf() {
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
