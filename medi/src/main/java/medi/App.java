package medi;

import medi.tools.AppLuncher;

/**
 *
 */
public class App {
	public static final String BASE_PKG="medi";
	public static void main(String[] args) throws Exception {

		DiContainer.bind(ILogger.class, new JsonLogger());
		DiContainer.bind(ITxtLogger.class, new TextLogger());

		AppLuncher.lunch(MyApp.class, new String[] { "Zuper", "PPZ" });
		AppLuncher.lunch(MyApp.class, new String[] { "HunteRER" });

		AppLuncher.lunch(MyServer.class, new String[] { "ZaPPIX" });
		AppLuncher.lunch(MyMachine.class, new String[] { "POWER_MACHINE" });

	}

}
