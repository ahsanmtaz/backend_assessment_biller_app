package com.retail.store.biller.model;

public class Enums {
    public enum UserType {
        employee,
        affiliate,
        customer;
    }

    public enum DiscountPercentage {
        employee(0.30),
        affiliate(0.10),
        customer(0.05);

        DiscountPercentage(double value) {
            this.value = value;
        }

        private final double value;

        public double getValue() { return value; }
    }

    public enum DiscountValue {
        on100Dollars(5);

        DiscountValue(int value) {
            this.value = value;
        }

        private final int value;

        public int getValue() { return value; }
    }
}
