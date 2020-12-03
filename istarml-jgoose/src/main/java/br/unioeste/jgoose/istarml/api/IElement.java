package br.unioeste.jgoose.istarml.api;

public abstract interface IElement
{
  public abstract boolean connect(IElement paramIElement, ILink paramILink);
  
  public abstract boolean connectTo(IElement paramIElement, ILink paramILink);
}


