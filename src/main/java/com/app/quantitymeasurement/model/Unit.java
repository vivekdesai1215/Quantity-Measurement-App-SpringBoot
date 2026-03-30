package com.app.quantitymeasurement.model;

import jakarta.persistence.Entity;

//@Entity
public enum Unit {

    // Length
    METER(MeasurementType.LENGTH, 1.0),
    CENTIMETER(MeasurementType.LENGTH, 0.01),
    MILLIMETER(MeasurementType.LENGTH, 0.001),
    INCH(MeasurementType.LENGTH, 0.0254),
    FOOT(MeasurementType.LENGTH, 0.3048),
    YARD(MeasurementType.LENGTH, 0.9144),
    KILOMETER(MeasurementType.LENGTH, 1000.0),
    MILE(MeasurementType.LENGTH, 1609.34),

    // Weight
    KILOGRAM(MeasurementType.WEIGHT, 1.0),
    GRAM(MeasurementType.WEIGHT, 0.001),
    MILLIGRAM(MeasurementType.WEIGHT, 0.000001),
    POUND(MeasurementType.WEIGHT, 0.453592),
    OUNCE(MeasurementType.WEIGHT, 0.0283495),

    // Volume
    LITER(MeasurementType.VOLUME, 1.0),
    MILLILITER(MeasurementType.VOLUME, 0.001),
    GALLON(MeasurementType.VOLUME, 3.78541),
    QUART(MeasurementType.VOLUME, 0.946353),
    PINT(MeasurementType.VOLUME, 0.473176),
    CUP(MeasurementType.VOLUME, 0.24),

    // Temperature (⚠️ handled via formulas)
    CELSIUS(MeasurementType.TEMPERATURE),
    FAHRENHEIT(MeasurementType.TEMPERATURE),
    KELVIN(MeasurementType.TEMPERATURE);

    private final MeasurementType type;
    private final double toBaseFactor;

    Unit(MeasurementType type, double toBaseFactor) {
        this.type = type;
        this.toBaseFactor = toBaseFactor;
    }

    Unit(MeasurementType type) {
        this.type = type;
        this.toBaseFactor = Double.NaN; // not used for temperature
    }

    public MeasurementType getType() { return type; }

    // Convert to base (Celsius)
    public double toBase(double value) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case CELSIUS: return value;
                case FAHRENHEIT: return (value - 32) * 5.0 / 9.0;
                case KELVIN: return value - 273.15;
                default: throw new IllegalArgumentException("Unsupported temperature unit");
            }
        }
        return value * toBaseFactor;
    }

    // Convert from base (Celsius)
    public double fromBase(double baseValue) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case CELSIUS: return baseValue;
                case FAHRENHEIT: return (baseValue * 9.0 / 5.0) + 32;
                case KELVIN: return baseValue + 273.15;
                default: throw new IllegalArgumentException("Unsupported temperature unit");
            }
        }
        return baseValue / toBaseFactor;
    }
}
