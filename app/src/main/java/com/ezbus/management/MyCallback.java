package com.ezbus.management;

/* Interfaccia necessaria per fare uscire stringhe dal metodo onDataChange */
@FunctionalInterface
interface MyCallback {
    void onCallback(String value);
}