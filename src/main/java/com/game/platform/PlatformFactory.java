package com.game.platform;

import java.util.HashMap;
import java.util.Map;

public class PlatformFactory {

	private Map<String, IPlatformFactoryCommand> mPlatformStrategyMap = new HashMap<>();

	private final int PLATFORM_CODE = 0;// this could be derived from config and stargergy can be initialized based on same

	public PlatformFactory(Object out) {
		
		if (PLATFORM_CODE == 0) {
			mPlatformStrategyMap.put(PlatformType.STANDALONE,
					new IPlatformFactoryCommand() {
						@Override
						public Stratergy execute() {
							return (new WordGameConsoleStratergy(out));
						}
					});
		}
	}

	public Stratergy makePlatformStrategy() {
		return (mPlatformStrategyMap.get(PlatformType.STANDALONE).execute());// TODO:: change it to return a singleton
	}
}
