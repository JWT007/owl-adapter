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
	public void childrenOf() {
		assert(t.childrenOf(a).isEmpty());
	}
	
	@Test
	public void descendantsOf() {
		assert(t.descendantsOf(a).isEmpty());
	}
	
	@Test
	public void parentsOf() {
		assert(t.parentsOf(a).isEmpty());
	}
	
	@Test
	public void ancestorsOf() {
		assert(t.ancestorsOf(a).isEmpty());
	}
	
	@Test
	public void testMultiParentChild() {
		assertFalse(t.multiParentChild().isPresent());
	}

}
