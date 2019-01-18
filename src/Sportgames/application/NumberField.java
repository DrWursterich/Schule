package Sportgames.application;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class NumberField extends TextField {
	private IntegerProperty minValueProperty;
	private IntegerProperty maxValueProperty;
	private IntegerProperty valueProperty;

	public NumberField() {
		this(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public NumberField(int value) {
		this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public NumberField(int minValue, int maxValue) {
		this(0, minValue, maxValue);
	}

	public NumberField(int value, int minValue, int maxValue) {
		this.minValueProperty = new SimpleIntegerProperty(minValue);
		this.maxValueProperty = new SimpleIntegerProperty(maxValue);
		this.valueProperty = new SimpleIntegerProperty(value);
		Bindings.bindBidirectional(
				this.textProperty(),
				this.valueProperty,
				new StringConverter<Number>() {
			@Override public Integer fromString(String string) {
				try {
					return "".equals(string.trim()) ? 0 : Integer.parseInt(string);
				} catch (final NumberFormatException e) {
					return 0;
				}
			}

			@Override
			public String toString(Number number) {
				return number.toString();
			}
		});
		this.setTextFormatter(new TextFormatter<>(new StringConverter<String>() {
			@Override
			public String toString(String string) {
				return this.formatString(string);
			}

			@Override
			public String fromString(String string) {
				return this.formatString(string);
			}

			private String formatString(String string) {
				final int minValue = NumberField.this.getMinValue();
				final int maxValue = NumberField.this.getMaxValue();
				if ("".equals(string)) {
					return "0";
				}
				int ret = minValue;
				try {
					ret = Math.max(minValue, Integer.parseInt(string));
				} catch (NumberFormatException e) {
					return "0";
				}
				ret = Math.min(ret, maxValue);
				return "" + ret;
			}
		}));
	}

	public void setValue(int value) {
		this.valueProperty.set(value);
	}

	public int getValue() {
		return this.valueProperty.get();
	}

	public IntegerProperty valueProperty() {
		return this.valueProperty;
	}

	public void setMinValue(int value) {
		this.minValueProperty.set(value);
	}

	public int getMinValue() {
		return this.minValueProperty.get();
	}

	public IntegerProperty minValueProperty() {
		return this.minValueProperty;
	}

	public void setMaxValue(int value) {
		this.maxValueProperty.set(value);
	}

	public int getMaxValue() {
		return this.maxValueProperty.get();
	}

	public IntegerProperty maxValueProperty() {
		return this.maxValueProperty;
	}
}
