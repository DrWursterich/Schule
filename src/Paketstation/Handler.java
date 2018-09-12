package Paketstation;

import javafx.scene.input.KeyCombination;

public abstract class Handler {
	protected UserOption[] menuOptions;
	protected Runnable onStart;
	protected Runnable onUpdate;

	public static class UserOption {
		private final String title;
		private final Runnable action;
		private KeyCombination accelerator;

		public UserOption(String title, Runnable action, String accelerator) {
			this(title, action);
			this.accelerator = KeyCombination.keyCombination(accelerator);
		}

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
		
		public KeyCombination getAccelerator() {
			return this.accelerator;
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
	
	public void setOnUpdate(Runnable action) {
		this.onUpdate = action;
	}
	
	public void setOnStart(Runnable action) {
		this.onStart = action;
	}

	public void setMenuOptions(UserOption... menuOptions) {
		this.menuOptions = menuOptions;
	}
}
