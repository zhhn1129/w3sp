package com.cernet.wechat;

import java.util.HashMap;
import java.util.List;

public class UserListMessage {
	//{"total":23,"count":23,"data":{"openid":["odS53xNqlWcNIuk3Atk1AxIeMuvg","odS53xILLap0OQft3o0mecPbvyuY","odS53xI1N01rEjjVsOhuACoqhdvw","odS53xLewOs2hW7DzL59AAH_Mj8g","odS53xCVwqZcI1sU9IT94sAuEohQ","odS53xI_7uOwDvSyWBYKaF25gT8E","odS53xJ37Omm6SKnyRIT9GfFZmQ4","odS53xB-2ACoGsN5m97HghcV2lG0","odS53xHDfdYSpgu1PsoLDTk5LoGE","odS53xCuItqKOvoxm7No9LQmwcBs","odS53xDlVlBd0HcNrvAxfA9vJjJg","odS53xGSyK-aXwtxej7iDDX_shLM","odS53xFJ7vz5pos5lJsnwCIJXPVc","odS53xKFUbHxYyaQYeODqhjWWb8A","odS53xHcV6M9y_MN6P6qh7jA_kAU","odS53xO9hd3LL_rZUhP9sSpe7jcE","odS53xKBu8dNtnYgvXPLKfUKp3IU","odS53xPy2ISDpchD3qSJOoi1dV70","odS53xOKtKCTmq8mufcUb3_C0EH4","odS53xHZtC1E3spoX_RoWyfznMN0","odS53xLgfuNtgIw-H0Y2rrv70pqI","odS53xJwuBmhGoJM2cjUlNuHGBSc","odS53xEuodDQXUbkrsdLeyt8ubRU"]},"next_openid":"odS53xEuodDQXUbkrsdLeyt8ubRU"}
	int total;
	int count;
	HashMap<String, Object> data;
	String next_openid;

	public int getTotal() {
		return total;
	}
	public int getCount() {
		return count;
	}
	public List<String> getOpenids() {
		@SuppressWarnings("unchecked")
		List<String> openids = (List<String>) data.get("openid");
		return openids;
	}
	public String getNextOpenid() {
		return next_openid;
	}
}
