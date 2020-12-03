package br.unioeste.jgoose.istarml.api;

import java.util.Set;

public abstract interface FilterElements<T>
{
  public abstract boolean match(T paramT);
  
  public abstract Set<T> filter(Set<T> paramSet);
}

