package Paketstation;

import javafx.scene.input.KeyCombination;

public abstract class Handler {
	protected UserOption[] menuOptions;
	protected Runnable onStart;
	protected Runnable onUpdate;
	protected boolean enableOnStart = true;
	protected boolean enableOnUpdate = true;

	public class UserOption {
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
			if (Handler.this.enableOnUpdate && Handler.this.onUpdate != null) {
				Handler.this.onUpdate.run();
			}
		}
	}

	public abstract void handleOutput(String output);

	public abstract Package createPackage(int packageNumber);

	public abstract void removePackages(Slot[] slots);

	public abstract void listPackages(Slot... slots);

	public abstract void run();

	public void initialize() {}

	public final void runOnStart() {
		if (this.enableOnStart && this.onStart != null) {
			this.onStart.run();
		}
	}

	public void setOnUpdate(Runnable action) {
		this.onUpdate = action;
	}

	public Runnable getOnUpdate() {
		return this.onUpdate;
	}

	public void setOnStart(Runnable action) {
		this.onStart = action;
	}

	public Runnable getOnStart() {
		return this.onStart;
	}

	public void setMenuOptions(UserOption... menuOptions) {
		this.menuOptions = menuOptions;
	}
}
