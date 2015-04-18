package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MeasurementHandler {
	private static MeasurementHandler mInstance;

	private MeasurementHandler() {
	}

	public static MeasurementHandler getInstance() {
		if (null == mInstance) {
			mInstance = new MeasurementHandler();
		}
		return mInstance;
	}

	private static final int DUMMY_COUNT = 10;

	private List<MeasurementItem> mMeasurementList;

	public List<MeasurementItem> getMathResult() {
		if (mMeasurementList == null) {

			List<MeasurementItem> list = new ArrayList<>();

			ResultStore resultStore = getDummyData();
			Iterator it = resultStore.getResult().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				list.add(new MeasurementItem((float) pair.getKey(),
						(float) pair.getValue()));
			}
			mMeasurementList = list;
		}
		return mMeasurementList;
	}

	private ResultStore getDummyData() {
		ResultStore resultStore = new ResultStore();

		for (int i = 1; i <= DUMMY_COUNT; i++) {
			resultStore.addPoint(i, i * 0.55f);
		}

		return resultStore;
	}

}
