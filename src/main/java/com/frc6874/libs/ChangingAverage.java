package com.frc6874.libs;

import java.util.ArrayList;
import java.util.Iterator;


public class ChangingAverage
{
  ArrayList<Double> numbers = new ArrayList();
  int maxSize;
  
  public ChangingAverage(int maxSize) {
    this.maxSize = maxSize;
  }
  
  public void addNumber(double newNumber) {
    numbers.add(Double.valueOf(newNumber));
    if (numbers.size() > maxSize) {
      numbers.remove(0);
    }
  }
  
  public double getAverage() {
    double total = 0.0D;
    
    for (Iterator localIterator = numbers.iterator(); localIterator.hasNext();) { double number = ((Double)localIterator.next()).doubleValue();
      total += number;
    }
    
    return total / numbers.size();
  }
  
  public int getSize() {
    return numbers.size();
  }
  
  public boolean isUnderMaxSize() {
    return getSize() < maxSize;
  }
  
  public void clear() {
    numbers.clear();
  }
}
