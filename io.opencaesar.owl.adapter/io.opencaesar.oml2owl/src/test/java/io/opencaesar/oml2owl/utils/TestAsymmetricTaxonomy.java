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

	
	HashMap<String, ClassExpression> vertexMap = new HashMap<String, ClassExpression>();
	Taxonomy initialTaxonomy;
	
	Taxonomy afterBypassOneTaxonomy;
	Taxonomy afterBypassAllTaxonomy;
	Taxonomy afterReduceTaxonomy;
	Taxonomy afterIsolateOneTaxonomy;
	Taxonomy afterIsolateAllTaxonomy;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		// Initial Taxonomy
		
		List<String> initialEdgeSpec = Stream.of(
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
		
		Set<String> initialVertexNames = initialEdgeSpec.stream().collect(Collectors.toSet());
		
		initialVertexNames.forEach((String vn) -> { vertexMap.put(vn, new Singleton(vn)); } );
		
		List<ClassExpression> initialEdgeList = initialEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		initialTaxonomy = new Taxonomy(initialEdgeList);
		
		// After bypass(i, c)
		
		List<String> afterBypassOneEdgeSpec = Stream.of(
				"a", "b",
				"a", "c",
				"b", "d",
				"b", "e",
				"c", "f",
				"c", "g",
				"a", "i",
				"e", "h",
				"e", "i",
				"i", "j"
				).collect(Collectors.toList());

		List<ClassExpression> afterBypassOneEdgeList = afterBypassOneEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		afterBypassOneTaxonomy = new Taxonomy(afterBypassOneEdgeList);
		
		// After bypass(i, {c, e})
		
		List<String> afterBypassAllEdgeSpec = Stream.of(
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

		List<ClassExpression> afterBypassAllEdgeList = afterBypassAllEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		afterBypassAllTaxonomy = new Taxonomy(afterBypassAllEdgeList);
		// After bypass(i, {c, e})
		
		List<String> afterReduceEdgeSpec = Stream.of(
				"a", "b",
				"a", "c",
				"b", "d",
				"b", "e",
				"b", "i",
				"c", "f",
				"c", "g",
				"e", "h",
				"i", "j"
				).collect(Collectors.toList());

		List<ClassExpression> afterReduceEdgeList = afterReduceEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		afterReduceTaxonomy = new Taxonomy(afterReduceEdgeList);
		
		List<String> afterIsolateOneEdgeSpec = Stream.of(
				"a", "b",
				"a", "c\\i",
				"b", "d",
				"b", "e",
				"b", "i",
				"c\\i", "f",
				"c\\i", "g",
				"e", "h",
				"i", "j"
				).collect(Collectors.toList());

		vertexMap.put("c\\i", vertexMap.get("c").difference(vertexMap.get("i")));
		
		List<ClassExpression> afterIsolateOneEdgeList = afterIsolateOneEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		afterIsolateOneTaxonomy = new Taxonomy(afterIsolateOneEdgeList);

		List<String> afterIsolateAllEdgeSpec = Stream.of(
				"a", "b",
				"a", "c\\i",
				"b", "d",
				"b", "e\\i",
				"b", "i",
				"c\\i", "f",
				"c\\i", "g",
				"e\\i", "h",
				"i", "j"
				).collect(Collectors.toList());

		vertexMap.put("e\\i", vertexMap.get("e").difference(vertexMap.get("i")));
		
		List<ClassExpression> afterIsolateAllEdgeList = afterIsolateAllEdgeSpec.stream()
				.map((String e) -> vertexMap.get(e)).collect(Collectors.toList());
		
		afterIsolateAllTaxonomy = new Taxonomy(afterIsolateAllEdgeList);
}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildrenOf() {
		Set<ClassExpression> bc = Stream.of("b", "c")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(bc, initialTaxonomy.childrenOf(vertexMap.get("a")));
	}

	@Test
	public void testDescendantsOf() {
		Set<ClassExpression> bcdefghij = Stream.of("b", "c", "d", "e", "f", "g", "h", "i", "j")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(bcdefghij, initialTaxonomy.descendantsOf(vertexMap.get("a")));
	}

	@Test
	public void testDirectChildrenOf() {
		Set<ClassExpression> bc = Stream.of("b", "c")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(bc, initialTaxonomy.directChildrenOf(vertexMap.get("a")));
	}

	@Test
	public void testParentsOf() {
		Set<ClassExpression> ce = Stream.of("c", "e")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(ce, initialTaxonomy.parentsOf(vertexMap.get("i")));
	}

	@Test
	public void testAncestorsOf() {
		Set<ClassExpression> abce = Stream.of("a", "b", "c", "e")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(abce, initialTaxonomy.ancestorsOf(vertexMap.get("i")));
	}

	@Test
	public void testDirectParentsOf() {
		Set<ClassExpression> ce = Stream.of("c", "e")
				.map(vn -> vertexMap.get(vn)).collect(Collectors.toSet());
		assertEquals(ce, initialTaxonomy.directParentsOf(vertexMap.get("i")));
	}

	@Test
	public void testMultiParentChild() {
		Optional<ClassExpression> childOption = initialTaxonomy.multiParentChild();
		assertTrue(childOption.isPresent());
		assertEquals(vertexMap.get("i"), childOption.get());
	}

	@Test
	public void testBypassParent() {
		ClassExpression c = vertexMap.get("c");
		ClassExpression i = vertexMap.get("i");
		assertEquals(afterBypassOneTaxonomy, initialTaxonomy.bypassParent(i, c));
	}

	@Test
	public void testBypassParents() {
		ClassExpression i = vertexMap.get("i");
		assertEquals(afterBypassAllTaxonomy, initialTaxonomy.bypassParents(i, initialTaxonomy.parentsOf(i)));
	}

	@Test
	public void testReduce_child() {
		ClassExpression i = vertexMap.get("i");
		assertEquals(afterReduceTaxonomy, afterBypassAllTaxonomy.reduce_child(i));
	}

	@Test
	public void testIsolateChildFromOne() {
		ClassExpression c = vertexMap.get("c");
		ClassExpression i = vertexMap.get("i");
		assertEquals(afterIsolateOneTaxonomy, afterReduceTaxonomy.isolateChildFromOne(i, c));
	}

	@Test
	public void testIsolateChild() {
		ClassExpression i = vertexMap.get("i");
		assertEquals(afterIsolateAllTaxonomy, afterReduceTaxonomy.isolateChild(i, initialTaxonomy.parentsOf(i)));
	}

}
