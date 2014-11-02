package BTreeTest;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import BTree.BTree;
import Rules.Repeat;
import Rules.RepeatRule;

/*
 * Test BTree.remove() function
 * Each test repeats REPEAT_TIMES times (1000 by default)
 * and every test doing on single object of BTree<..>.
 */
public class BTreeRemoveTest {
	private static final int REPEAT_TIMES = 1000;
	private static final int MAX_SIZE = 10000;

	private BTree<Integer, Integer> b = new BTree<Integer, Integer>();
	private HashMap<Integer, Integer> expectedKeyValue = new HashMap<Integer, Integer>();

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	@Before
	public void setUp() throws Exception {
		Random rand = new Random();
		for (int i = 0; i < rand.nextInt(MAX_SIZE); ++i) {
			int key = rand.nextInt();
			int value = rand.nextInt();
			if (!expectedKeyValue.containsKey(key)) {
				expectedKeyValue.put(key, value);
				b.add(key, value);
			}
		}
	}

	@After
	public void setDown() throws Exception {
		b.clear();
		expectedKeyValue.clear();
	}

	// All keys inserted in setUp() method removes one by one.
	@Test
	@Repeat(times = REPEAT_TIMES)
	public void removeTest() {
		for (Integer key : expectedKeyValue.keySet()) {
			boolean expected = b.remove(key);
			assertTrue(expected);
		}
	}
}
