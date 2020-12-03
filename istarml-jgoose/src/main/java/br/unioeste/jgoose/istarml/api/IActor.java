package br.unioeste.jgoose.istarml.api;

public abstract interface IActor
  extends IElement
{
  public abstract boolean connect(IActor paramIActor, ILink paramILink);
  
  public abstract boolean getIncomingLinks();
  
  public abstract boolean getOutcomingLinks();
  
  public abstract boolean getBoundaryElements();
  
  public abstract boolean getBoundaryLinks();
}

