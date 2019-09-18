package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEmptyTaxonomy {

	Taxonomy t;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		t = new Taxonomy();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMultiParentChild() {
		assertFalse(t.multiParentChild().isPresent());
	}

}
