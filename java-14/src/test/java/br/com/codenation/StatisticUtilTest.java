package br.com.codenation;

public class StatisticUtilTest {

	@Test
	public void testAverage() {
		assertEquals(3, StatisticUtil.average(new int[] {1,2,3,4,5}));
	}

	private void assertEquals(int i, int average) {
	}

	@Test
	public void testMode() {
		assertEquals(3, StatisticUtil.mode(new int[] {1,2,3,3}));
	}
	
	@Test
	public void testMedian() {
		assertEquals(3, StatisticUtil.median(new int[] {1,2,3,4,5}));
	}
}
