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
		setA = new HashSet<ClassExpression>(Arrays.asList(a));
		b = new Singleton("b");
		setB = new HashSet<ClassExpression>(Arrays.asList(b));
		t.addVertex(a);
		t.addVertex(b);
		t.addEdge(a,  b);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void childrenOf() {
		assertEquals(setB, t.childrenOf(a));
		assert(t.childrenOf(b).isEmpty());
	}
	
	@Test
	public void descendantsOf() {
		assertEquals(setB, t.descendantsOf(a));
		assert(t.descendantsOf(b).isEmpty());
	}
	
	@Test
	public void parentsOf() {
		assertEquals(setA, t.parentsOf(b));
		assert(t.parentsOf(a).isEmpty());
	}
	
	@Test
	public void ancestorsOf() {
		assertEquals(setA, t.ancestorsOf(b));
		assert(t.ancestorsOf(a).isEmpty());
	}
	
	@Test
	public void testMultiParentChild() {
		assert(!t.multiParentChild().isPresent());
	}

}
