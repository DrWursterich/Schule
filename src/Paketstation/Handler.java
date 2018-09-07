package Paketstation;

public abstract class Handler {
	protected UserOption[] menuOptions;

	public static class UserOption {
		private final String title;
		private final Runnable action;

		public UserOption(String title, Runnable action) {
			this.title = title;
			this.action = action;
		}

		public String getTitle() {
			return this.title;
		}

		public Runnable getAction() {
			return this.action;
		}

		protected void run() {
			this.action.run();
		}
	}

	public abstract void promptUser(UserOption... options);

	public abstract void handleOutput(String output);

	public abstract Package createPackage(int packageNumber);

	public abstract void removePackages(Package[] packages);

	public abstract void listPackages(Package... packages);

	public abstract void run();

	public void setMenuOptions(UserOption... menuOptions) {
		this.menuOptions = menuOptions;
	}
}
