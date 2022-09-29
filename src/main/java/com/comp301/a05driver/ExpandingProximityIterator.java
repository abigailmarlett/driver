package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandingProximityIterator implements Iterator<Driver> {
  private boolean flag;
  private int val;
  private Iterable<Driver> driverPool;
  private Position clientPosition;
  private int expansionStep;
  private Driver nextDriver;
  private Iterator<Driver> driverIterator;

  public ExpandingProximityIterator(
          Iterable<Driver> driverPool, Position clientPosition, int expansionStep) {

    if (driverPool == null) {
      throw new IllegalArgumentException("Error Occurred.");
    }
    if (clientPosition == null) {
      throw new IllegalArgumentException("Error Occurred.");
    }
    this.flag = false;
    this.val = 0;
    this.driverPool = driverPool;
    this.clientPosition = clientPosition;
    this.expansionStep = expansionStep;
    this.nextDriver = null;
    this.driverIterator = driverPool.iterator();
  }
  public boolean hasNext() {
    adeptDriverMethod();
    return this.nextDriver != null;
  }

  public Driver next() {
    if (this.hasNext()) {
      Driver temp = nextDriver;
      nextDriver = null;
      return temp;
    } else {
      throw new NoSuchElementException("Error occurred.");
    }
  }

  private void adeptDriverMethod() {
      while (this.nextDriver == null) {
        if (!driverIterator.hasNext()) {
          if (flag) {
            val++;
            flag = false;
            driverIterator = driverPool.iterator();
          } else {
            break;
          }
        } else {
          Driver potential = driverIterator.next();
          int driverDistance =
                  clientPosition.getManhattanDistanceTo(potential.getVehicle().getPosition());
          if (driverDistance > 1 + ((val - 1) * expansionStep)) {
            if (driverDistance > 1 + (val * expansionStep)) {
              flag = true;
            } else {
              nextDriver = potential;
            }
          }
        }
      }
  }
}