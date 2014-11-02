package BTreeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import BTree.BTree;
import Rules.Repeat;
import Rules.RepeatRule;

/*
 * Test BTree.find() function
 * Each test repeats REPEAT_TIMES times (1000 by default)
 * and every test doing on single object of BTree<..>.
 */
public class BTreeFindTest {
	private static final int REPEAT_TIMES = 1000;
	private static final int MAX_SIZE = 10000;

	private BTree<Integer, Integer> b = new BTree<Integer, Integer>();
	private HashMap<Integer, Integer> expectedKeyValue = new HashMap<Integer, Integer>();
	private int expectedSize = 0;
	private Random rand = new Random();

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	// The B-tree filled by random keys and values.
	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < rand.nextInt(MAX_SIZE); ++i) {
			int key = rand.nextInt();
			int value = rand.nextInt();
			if (!expectedKeyValue.containsKey(key)) {
				expectedKeyValue.put(key, value);
				b.add(key, value);
				++expectedSize;
			}
		}
	}

	@After
	public void setDown() throws Exception {
		b.clear();
		expectedSize = 0;
		expectedKeyValue.clear();
	}

	// Compared actual and expected size.
	@Test
	@Repeat(times = REPEAT_TIMES)
	public void sizeTest() {
		int actualSize = b.getSize();

		assertEquals(expectedSize, actualSize);
	}

	// Search each key that was inserted in setUp() method.
	@Test
	@Repeat(times = REPEAT_TIMES)
	public void findAllExistedKeysTest() {
		for (Entry<Integer, Integer> pair : expectedKeyValue.entrySet()) {
			Integer actualValue = b.find(pair.getKey());
			// Key was found in b-tree
			assertTrue(actualValue != null);
			// Key and value are equals
			assertEquals(actualValue, pair.getValue());
		}
	}

	// Search random keys and check result of BTree.find() function.
	@Test
	@Repeat(times = REPEAT_TIMES)
	public void findRandomKeysTest() {
		boolean expected, actual;
		for (int i = 0; i < rand.nextInt(MAX_SIZE); ++i) {
			int randKey = rand.nextInt();
			expected = expectedKeyValue.containsKey(randKey);
			actual = b.find(randKey) != null;

			assertEquals(actual, expected);
		}
	}

}
