package Paketstation;

import java.util.function.Consumer;

public abstract class Handler {
	public static final class UserOption<T extends Object> {
		private final String title;
		private final Consumer<T> action;
		private final T actionParameter;
		
		public UserOption(String title, Consumer<T> action, T actionParameter) {
			this.title = title;
			this.action = action;
			this.actionParameter = actionParameter;
		}
		
		public String getTitle() {
			return this.title;
		}
		
		public Consumer<T> getAction() {
			return this.action;
		}
		
		public T getActionParameter() {
			return this.actionParameter;
		}
		
		protected void run() {
			this.action.accept(this.actionParameter);
		}
	}
	
	public Handler() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void promptUser(UserOption<?>...options);
	
	public abstract void handleOutput(String output);
}
