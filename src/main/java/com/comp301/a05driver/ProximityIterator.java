package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ProximityIterator implements Iterator<Driver> {
  Iterable<Driver> driverPool;
  Iterator<Driver> driverIterator;
  Position clientPosition;
  int proximityRange;
  Driver nextDriver;
  int driversCounted;

  public ProximityIterator(
      Iterable<Driver> driverPool, Position clientPosition, int proximityRange) {
    if (clientPosition == null) {
      throw new IllegalArgumentException("Position cannot be null");
    } else {
      this.clientPosition = clientPosition;
    }
    if (driverPool == null) {
      throw new IllegalArgumentException("Pool cannot be null");
    } else {
      this.driverPool = driverPool;
    }
    this.driverIterator = driverPool.iterator();
    this.proximityRange = proximityRange;
    driversCounted = 0;
    nextDriver = null;
  }

  public boolean hasNext() {
    Driver driverHasNext = nextDriverMethod();
    return driverHasNext != null;
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

  private Driver nextDriverMethod() {
    while (nextDriver == null) {
      if (driverIterator.hasNext()) {
        Driver potentialDriver = driverIterator.next();
        int distance =
            potentialDriver.getVehicle().getPosition().getManhattanDistanceTo(clientPosition);
        if (distance <= proximityRange) {
          nextDriver = potentialDriver;
          break;
        }
      } else {
        break;
      }
    }
    return nextDriver;
  }
}
