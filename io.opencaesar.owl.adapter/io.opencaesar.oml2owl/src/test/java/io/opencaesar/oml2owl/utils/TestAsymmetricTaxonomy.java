package io.opencaesar.oml2owl.utils;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAsymmetricTaxonomy {

	
	HashMap<String, ClassExpression> initialVertexMap = new HashMap<String, ClassExpression>();
	Taxonomy initialTaxonomy;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		List<String> initialEdges = Stream.of(
				"a", "b",
				"a", "c",
				"b", "d",
				"b", "e",
				"c", "f",
				"c", "g",
				"c", "i",
				"e", "h",
				"e", "i",
				"i", "j"
				).collect(Collectors.toList());
		Set<String> initialVertexNames = initialEdges.stream().collect(Collectors.toSet());
		HashMap<String, ClassExpression> initialVertexMap = new HashMap<String, ClassExpression>();
		initialVertexNames.forEach((String vn) -> { initialVertexMap.put(vn, new Singleton(vn)); } );
		HashMap<ClassExpression, ClassExpression> initialEdgeMap = new HashMap<ClassExpression, ClassExpression>();
		int i;
		for (i = 0; i < initialEdges.size() / 2; i += 2) {
			initialEdgeMap.put(initialVertexMap.get(initialEdges.get(i)), initialVertexMap.get(initialEdges.get(i + 1)));
		}
		initialTaxonomy = new Taxonomy(initialEdgeMap);
		
		List<String> afterBypassEdges = Stream.of(
				"a", "b",
				"a", "c",
				"a", "i",
				"b", "d",
				"b", "e",
				"b", "i",
				"c", "f",
				"c", "g",
				"e", "h",
				"i", "j"
				).collect(Collectors.toList());
		Set<String> afterBypassVertexNames = afterBypassEdges.stream().collect(Collectors.toSet());
}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testDescendantsOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testDirectChildrenOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testParentsOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testAncestorsOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testDirectParentsOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiParentChild() {
		Optional<ClassExpression> childOption = initialTaxonomy.multiParentChild();
		assertTrue(childOption.isPresent());
		assertEquals(initialVertexMap.get("i"), childOption.get());
	}

	@Test
	public void testBypass_parent() {
		fail("Not yet implemented");
	}

	@Test
	public void testBypass_parents() {
		fail("Not yet implemented");
	}

}
