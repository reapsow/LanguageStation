package languagestation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class GoProcess {
	
	public static String goProcess(Integer timeoutSec, String... s) {
		String result = "";
		try {
			ProcessBuilder bld = new ProcessBuilder(s);
			Process process = bld.start();

			if (timeoutSec != 0) {
				//timeout 으로 실패하면 2번 더 시도합니다. 
				for (int i = 0; i < 3; i++) {
					result = waitForOrKill(process, TimeUnit.SECONDS.toMillis(timeoutSec));
					if (!result.equals("%failed%")) {
						break;
					}
				}
			}
			else {
				InputStream is = process.getInputStream();

				byte b[] = new byte[8192000];
				int read = 0;

				while (( read = is.read(b)) > 0) {
					result = result + new String (b, 0, read);
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	protected static class ProcessRunner implements Runnable {
		Process process;
		private boolean finished;

		public ProcessRunner(Process process) {
			this.process = process;
		}

		public void run() {
			try {
				process.waitFor();
			} catch (InterruptedException e) {
			}
			synchronized (this) {
				notifyAll();
				finished = true;
			}
		}

		public synchronized String waitForOrKill(long millis) {
			String result = "";
			if (!finished) {
				try {
					wait(millis);
				} catch (InterruptedException e) {
				}
				if (!finished) {
					process.destroy();
					System.out.println("not finished");
					result = "%failed%";
				}
				else {
					InputStream is = process.getInputStream();

					byte b[] = new byte[8192000];
					int read = 0;

					try {
						while (( read = is.read(b)) > 0) {
							result = result + new String (b, 0, read);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
			}
			return result;
		}
	}

	public static String waitForOrKill(Process self, long numberOfMillis) {
		ProcessRunner runnable = new ProcessRunner(self);
		Thread thread = new Thread(runnable);
		thread.start();
		return runnable.waitForOrKill(numberOfMillis);
	}
	
}
