package io.lizardframework.data.loadbalance;

import io.lizardframework.data.enums.LoadBalanceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-09
 */
public class LoadBalanceAlgorithmTest {
	private String       name = "TestMixedDataSource";
	private List<String> slaveNameList;

	@Before
	public void before() {
		slaveNameList = new ArrayList<>(50);
		for (int i = 0; i < 50; i++) {
			slaveNameList.add("slave" + i);
		}
	}

	@Test
	public void randomTest() {
		LoadBalanceAlgorithm loadBalanceAlgorithm = LoadBalanceAlgorithmFactory.getAlgorithm(LoadBalanceType.RANDOM);

		for (int i = 0; i < 50; i++) {
			String result = loadBalanceAlgorithm.selector(name, slaveNameList);
			System.out.println(result);
		}
	}

	@Test
	public void roundRobinTest() {
		LoadBalanceAlgorithm loadBalanceAlgorithm = LoadBalanceAlgorithmFactory.getAlgorithm(LoadBalanceType.ROUND_ROBIN);

		for (int i = 0; i < 100; i++) {
			String result = loadBalanceAlgorithm.selector(name, slaveNameList);
			System.out.println(result);
		}
	}

	@Test
	public void roundRobinConcurrentTest() {

	}
}
